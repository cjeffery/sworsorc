/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */
package systemServer;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Manages the connection for a client connected to server
 */
public class ClientObject {

    // User data
    final private int clientID; // Unique ID of client connection
    private String handle; // Username, can change
    final private Socket socket;     // Connection this object is attached to
    private Lobby currentLobby = null;     // Lobby client is a part of, null if not in a lobby
    private List<String> file;     // File associated with this object

    // The Writer Thread's food source
    final private ArrayBlockingQueue<NetworkPacket> messageQueue;

    // Remote Send/Recieve threads
    final private ServerListenerThread listenerThread;
    final private ServerWriterThread writerThread;

    private ObjectOutputStream writer = null;
    private ObjectInputStream streamIn = null;

    // Server console output
    final private PrintWriter consoleOut;
    final private PrintWriter errorOut;

    // Debugging
    final private boolean debug;

    /*
     * CONSTRUCTOR
     */
    /**
     * ClientObject constructor
     * Call {@link #start start()} to activate the object
     * <p>
     * @param sock     The Socket associated with a single client connection
     * @param clientID The unique ID of the Client
     */
    protected ClientObject( Socket sock, int clientID ) {

        // Data member initializers
        this.clientID = clientID; // Unique client ID
        this.handle = "UnknownHandle"; // Default handle
        this.socket = sock; // Set socket to client connection

        // Initialize threads/streams
        this.consoleOut = new PrintWriter( System.out, true );
        this.listenerThread = new ServerListenerThread();
        this.writerThread = new ServerWriterThread();
        this.messageQueue = new ArrayBlockingQueue<>( 30, true ); // 30 slots, FIFO access

        this.debug = MessagePhoenix.debugStatus();
        this.errorOut = new PrintWriter( System.err, true );
        this.file = Collections.emptyList();
    }

    /**
     * Poke from server
     * <p>
     * If need a client to client poke, use send( flag, tag, client, null )
     *
     * @param flag
     * @param tag
     */
    protected void send( Flag flag, Tag tag ) {
        send( flag, tag, null, (Object[]) null );
    }

    /**
     * Send a message to client, no pre-packaging required
     * Must ensure any collections are either split up, or handled on the receiving end!
     * <p>
     * Writes to socket outgoing connection, hides the protocol details
     * <p>
     * @param flag
     * @param tag
     * @param sender
     * @param message First parameter is assumed to be tag
     */
    protected void send( Flag flag, Tag tag, String sender, List<Object> message ) {
        writeToQueue( new NetworkPacket( flag, tag, ((sender == null || sender.isEmpty()) ? "Server" : sender), message ) );
    }

    /**
     * Send a message to client, no pre-packaging required
     * Must ensure any collections are either split up, or handled on the receiving end!
     * <p>
     * Writes to socket outgoing connection, hides the protocol details
     * <p>
     * @param flag
     * @param tag
     * @param sender
     * @param message First parameter is assumed to be tag
     */
    protected void send( Flag flag, Tag tag, String sender, Object... message ) {
        send( flag, tag, sender, MessagePhoenix.packMessageContents( message ) );
    }

    /**
     *
     * @param packet
     */
    private void writeToQueue( final NetworkPacket packet ) {
        if ( packet == null ) {
            errorOut.println( "Attempted to write null packet to queue!" );
        } else {
            try {
                messageQueue.put( packet );
            } catch ( InterruptedException ex ) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Checks if client is connected to server
     * <p>
     * @author Christopher Goes
     * @return boolean True if connected, False if not
     */
    private boolean isConnected() {
        return this.socket != null && !(this.socket.isClosed()) && this.socket.isConnected();
    }

    /**
     * Handles incoming messages from the client connection it is associated with
     * Makes the blocking receive until a message arrives
     */
    private class ServerListenerThread extends Thread {

        private boolean killed = false;

        protected ServerListenerThread() {
            // empty constructor
        }

        /**
         * Creates input stream, and connects to socket
         * <p>
         * NOTE: Must be called when creating thread!
         * <p>
         * @author Christopher Goes
         */
        private void createStream() {
            if ( isConnected() ) {
                try {
                    ClientObject.this.streamIn = new ObjectInputStream( ClientObject.this.socket.
                            getInputStream() );
                } catch ( IOException ex ) {
                    ex.printStackTrace();
                }
            } else {
                errorOut.println( "Error creating listen stream: socket isn't connected!" );
            }
        }

        /**
         * Wrapper for {@link MessagePhoenix#recieveMessage(java.io.ObjectInputStream) }
         * <p>
         * @return List received, or null if not connected
         */
        private NetworkPacket recieveMessage()
        {
            NetworkPacket res;
            try {
                res = MessagePhoenix.recieveMessage(ClientObject.this.streamIn);
                return res;
            }
            catch ( IOException | ClassNotFoundException | 
                    NullPointerException ex)
            {
                ex.printStackTrace();
                killThread();
                return null;
            }
        }

        private void killThread() {
            this.killed = true;
        }

        /**
         * Processes incoming messages from the client
         * <p>
         * Uses nested switch statements to parse message flags and tags
         *
         * @param incomingMessage
         *
         * @return False if disconnected or a error occurred
         *
         * @author Christopher Goes
         */
        private boolean processMessage( final NetworkPacket incomingMessage ) {

            NetworkPacket rawMessage = incomingMessage;
            String stringmessage = "";

            Tag tag = rawMessage.getTAG();
            Flag flag = rawMessage.getFlag();
            String sender = rawMessage.getSender();
            List<Object> message = rawMessage.getData();

            if ( message == null ) {
                errorOut.println( "Null data!" );
                return false;
            } 
            else if (!message.isEmpty() &&
                     message.get( 0 ).getClass().equals( String.class ) )
            {
                stringmessage = (String) message.get( 0 );
            }

            if ( debug ) {
                errorOut.println( "Process Message" );
                errorOut.println( "Tag: " + tag );
                errorOut.println( "Flag: " + flag );
                errorOut.println( "Sender: " + sender );
                errorOut.println( "Data: " + message.toString() );
                errorOut.println( "stringmessage: " + stringmessage );
            }

            switch ( flag ) {
                // Tagged Chat Message ex: GLOBAL, LOBBY, PRIVATE, etc
                case CHAT:

                    switch ( tag ) {
                        case PRIVATE:
                            NetworkServer.
                                    sendToClient( stringmessage, flag, tag, sender, MessagePhoenix.
                                            packMessageContents( message.
                                                    get( 1 ) ) ); // assume message is a single string object
                            break;
                        case LOBBY:
                            currentLobby.sendToEntireLobby( flag, tag, sender, message );
                            break;
                        case GLOBAL:
                            NetworkServer.sendToAllClients( flag, tag, sender, message );
                            break;
                        case SEND_CHAT_MESSAGE:
                            if ( currentLobby != null ) {
                                currentLobby.sendToEntireLobby( flag, Tag.LOBBY, sender, message );
                            } else {
                                NetworkServer.sendToAllClients( flag, Tag.GLOBAL, sender, message );
                            }
                            break;
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;
                // Client stuff
                case CLIENT:

                    switch ( tag ) {
                        case SEND_HANDLE:
                            handle = sender;
                            consoleOut.
                                    println( "Assigning handle " + handle + " to client " + clientID );
                            NetworkServer.
                                    sendToAllClients( "Server", handle + " has just connected to the server!" );

                            break;
                        case MESSAGE_TO_SERVER:
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;
                // Error message
                case ERROR:

                    switch ( tag ) {
                        case INVALID_GAME_ACTION:
                        case GENERIC_ERROR:

                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;
                // Game state update/message/command (Anything related to game)
                case GAME:
                    switch ( tag ) {

                        case PHASE_CHANGE:
                            NetworkServer.
                                    sendToAllClients( flag, tag, "Server", message );
                            break;
                        case YIELD_TURN_REQUEST:
                            send( Flag.RESPONSE, Tag.YIELD_TURN_RESPONSE );

                            break;
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;
                // Request for inforation ex: REQUEST_LOBBY_INFO
                case REQUEST:
                    flag = Flag.RESPONSE; // make life easier
                    switch ( tag ) {

                        case GLOBAL_WHO_REQUEST:
                            if ( NetworkServer.getTotalClients() == 0 ) {
                                send( flag, Tag.GLOBAL_WHO_RESPONSE, null, "No users online." );

                            } else {
                                // All currently connected users in all lobbies
                                StringBuilder temp = new StringBuilder( (NetworkServer.
                                                                         getTotalClients() + " users online: ") );
                                for ( String s : NetworkServer.getAllUserNames() ) {
                                    temp.append( " " ).append( s );
                                }
                                send( flag, Tag.GLOBAL_WHO_RESPONSE, null, temp.toString() );
                            }
                            break;

                        case LOBBY_INFO_REQUEST:
                            if ( currentLobby == null ) {
                                // Gets all usernames of every lobby on the server, and adds to List
                                StringBuilder lobbyinfo = new StringBuilder( "Lobbies: " );
                                for ( String lobby : NetworkServer.getLobbyNames() ) {
                                    lobbyinfo.append( lobby ).append( Arrays.
                                            toString( NetworkServer.
                                                    getLobbyUsers( lobby ).toString().
                                                    split( " " ) ) );
                                }
                                send( flag, Tag.LOBBY_INFO_RESPONSE, null, lobbyinfo );
                            } else {
                                send( flag, Tag.LOBBY_INFO_RESPONSE, null, currentLobby.
                                        getUserNames() );
                            }
                            break;
                        case NEW_LOBBY_REQUEST:
                            consoleOut.println( "Received request to create lobby: " + message.
                                    get( 0 ) );

                            if ( NetworkServer.createNewLobby( (String) message.get( 0 ) ) ) {
                                NetworkServer.
                                        joinLobby( (String) message.get( 0 ), ClientObject.this );
                                send( flag, Tag.NEW_LOBBY_RESPONSE, null, true, "Lobby " + message.
                                        get( 0 ) + " has been created!" );
                            } else {
                                send( flag, Tag.NEW_LOBBY_RESPONSE, null, false, "Could not create lobby, it probably already exists!" );
                            }
                            break;
                        case JOIN_LOBBY_REQUEST:
                            String lobby = (String) message.get( 0 );
                            consoleOut.
                                    println( "Received request to join lobby: " + lobby + " from client " + handle );
                            if ( currentLobby != null && currentLobby.getName().
                                    equals( lobby ) ) {
                                send( flag, Tag.JOIN_LOBBY_RESPONSE, "Cannot join lobby, you're already in it!" );

                            } else if ( NetworkServer.joinLobby( (String) message.get( 0 ), ClientObject.this ) ) {
                                send( flag, Tag.JOIN_LOBBY_RESPONSE, "Successfully joined lobby " + currentLobby + "!" );
                                currentLobby.
                                        sendToEntireLobby( flag, Tag.JOIN_LOBBY_RESPONSE, "Client " + handle + " has joined the lobby!" );
                            } else {
                                send( flag, Tag.JOIN_LOBBY_RESPONSE, "Failed to join lobby " + lobby + "! It probably doesn't exist!" );
                            }
                            break;
                        case LEAVE_LOBBY_REQUEST: // TODO: improve on this
                            consoleOut.
                                    println( "Client " + handle + " has requested to leave lobby" );
                            NetworkServer.leaveLobby( ClientObject.this );
                            send( flag, Tag.LEAVE_LOBBY_RESPONSE, "You have successfully left lobby " + message.
                                    get( 0 ) );
                            currentLobby = null;
                            break;
                        case UID_REQUEST:
                            send( flag, Tag.UID_RESPONSE, null, NetworkServer.generateUniqueID() );
                            break;
                        case BEGIN_GAME_REQUEST:
                            if ( currentLobby == null ) {
                                send( flag, Tag.BEGIN_GAME_RESPONSE,
                                        "You requested to start the game, but you aren't even in a lobby!" );
                            } else {
                                currentLobby.
                                        sendToEntireLobby( flag, Tag.BEGIN_GAME_RESPONSE,
                                                ("Client " + sender + " has requested to start a game in lobby " + currentLobby.
                                                 getName()) );
                                // TODO: VOTING
                                currentLobby.beginGame();
                                currentLobby.sendToEntireLobby( flag, Tag.BEGIN_GAME );
                                
                                //selected client in charge of game setup
                                send( Flag.GAME, Tag.INIT_GAME_PLEASE);
                            }
                            break;
                        case SEND_FILE_REQUEST:
                            System.out.println("SEND_FILE_REQUEST unhandled");
                            break;
                        case GET_FILE_REQUEST:
                            System.out.println("GET_FILE_REQUEST unhandled");
                            break;
                        //How is this different from NEW_LOBBY_REQUEST
                        case CREATE_LOBBY_REQUEST:
                            System.out.println("CREATE_FILE_REQUEST unhandled");
                            break;
                        case YIELD_TURN_REQUEST:
                            consoleOut.
                                    println( "Client " + handle + " (id  " + clientID + " ) yielded turn" );
                            if ( currentLobby == null ) {
                                send( Flag.GAME, Tag.YIELD_TURN_RESPONSE, "", "You requested to yield your turn, but you're not even in a lobby!" );
                            } else if ( currentLobby.current.getClientID() != clientID ) {
                                send( Flag.GAME, Tag.YIELD_TURN_RESPONSE, "", "You requested to yield your turn, but it's not currently your turn!" );
                            } else {
                                currentLobby.advanceGameTurn(); //tell lobby handler to advance game turn
                            }
                            break;
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;
                // Response to information request ex: LOBBY_INFO
                case RESPONSE:
                    flag = Flag.REQUEST;
                    switch ( tag ) {

                        case VOTE_RESPONSE:
                        // TODO: voting!
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;
                // Connection messages/commands ex: DISCONNECT_REQUEST
                case CONNECTION:

                    switch ( tag ) {
                        case DISCONNECT_REQUEST:
                            // Notify client that server recieved request, and is closing its side of the connection
                            send( Flag.RESPONSE, Tag.DISCONNECT_RESPONSE, null, true ); // true always for now
                            return false;
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;
                // Anything related to file transfer, either for network or game state(for now)
                case FILE:

                    switch ( tag ) {
                        case GET_FILE_REQUEST:
                            // TODO: send file
                            break;
                        case SEND_FILE_REQUEST:
                            // TODO: prepare to recieve file
                            break;
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;
                // Anything that doesn't fall into above categories ex: GENERIC
                case OTHER:

                    switch ( tag ) {

                        case NAG: // TODO: What purpose does this serve now?
                            errorOut.println( "NAG: " + message.get( 0 ) );
                            break;
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;

                default:
                    consoleOut.println( "Unknown flag: " + flag );
                    break;
            } // end outer switch

            return true;
        }

        @Override
        public void run() {
            createStream(); // start the stream!
            NetworkPacket rawMessage = new NetworkPacket();

            while ( !killed ) {

                if ( ClientObject.this.streamIn != null && isConnected() ) {
                    rawMessage = recieveMessage();
                    if ( rawMessage == null ) {
                        killThread();
                    } else if ( processMessage( rawMessage ) ) {
                        // Suprise Party Fiddlesticks lives here
                    } else {
                        disconnect();
                    }
                } else if ( !isConnected() ) {
                    errorOut.
                            println( "Client " + ClientObject.this.getClientID() + " lost connection!" );
                    killThread();
                } else {
                    errorOut.println( "streamIn is null!" );
                }
            }
            close();
        }

        private void disconnect() {
            killThread();
            NetworkServer.clientDisconnected( ClientObject.this );
        }

        protected void close() {
            if ( isConnected() ) {
                try {
                    if ( streamIn != null ) {
                        streamIn.close();
                    }

                } catch ( IOException e ) {
                    errorOut.println( e );
                } finally {
                    streamIn = null;
                    writerThread.killThread();

                }
            }
            streamIn = null;
            if ( writerThread != null && writerThread.isAlive() ) {
                writerThread.killThread();
            }
        }

    } // end ServerListenerThread

    /**
     * Sends messages to the connected client
     * <p>
     * Dies when listenerThread died
     */
    private class ServerWriterThread extends Thread {

        private boolean killed = false;

        protected ServerWriterThread() {
            // empty constructor
        }

        /**
         * Initializes writer with a new stream.
         * <p>
         * NOTE: Socket must be set before calling this!
         * <p>
         * @author Christopher Goes
         */
        private void setWriter() {
            if ( isConnected() ) {
                try {
                    ClientObject.this.writer = new ObjectOutputStream( ClientObject.this.socket.
                            getOutputStream() );
                    ClientObject.this.writer.flush();
                } catch ( IOException ex ) {
                    ex.printStackTrace();
                }
            }
        }

        /**
         * Marks thread for death, causing it to close, return, and die
         * <p>
         * @author Christopher Goes
         */
        private void killThread() {
            this.killed = true;
        }

        private void sendMessage( final NetworkPacket message ) {
            MessagePhoenix.sendMessage( ClientObject.this.writer, message );
        }

        @Override
        public void run() {
            setWriter(); // starts stream!
            NetworkPacket message = null;
            if ( messageQueue == null ) {
                errorOut.println( "messageQueue was never initialized!" );
                killThread();
            }

            while ( !killed ) {
                if ( isConnected() ) {
                    try {
                        message = messageQueue.take();
                    } catch ( InterruptedException ex ) {
                        ex.printStackTrace();
                        killThread();
                    }
                    if ( message != null && writer != null ) {
                        sendMessage( message );
                    } else if ( message == null ) {
                        errorOut.println( "Null message!" );
                        killThread();
                    } else {
                        errorOut.println( "Writer is null!" );
                        killThread();
                    }
                } else {
                    errorOut.
                            println( "Lost connection to client " + clientID + " in writerThread" );
                    killThread();
                }
            }
            close();
        }

        /**
         * Always run this before returning from {@link #run run}!
         */
        private void close() {
            writer = null;
        }

    } // end ServerWriterThread

    /**
     * Starts the ClientObject, opening the connection
     * <p>
     * @author Christopher Goes
     */
    protected void start() {

        listenerThread.start();
        writerThread.start();
        consoleOut.println( "Opened connection from client " + clientID + " at address " + socket.
                getInetAddress() );
    }

    /*
     * GETTERS & SETTERS
     */
    /**
     * Gets the handle of client object
     * <p>
     * @return handle Username of client
     * <p>
     * @author Christopher Goes
     */
    protected String getHandle() {
        return this.handle;
    }

    /**
     * Gets the clientID of client object
     * <p>
     * @return clientID Unique ID of client
     * <p>
     * @author Christopher Goes
     */
    protected int getClientID() {
        return this.clientID;
    }

    /**
     * Sets the handle (username) of the ClientObject
     * <p>
     * @param hand Handle to set
     * <p>
     * @author Christopher Goes
     */
    protected void setHandle( String hand ) {
        if ( hand != null && !hand.isEmpty() ) {
            this.handle = hand;
        } else {
            errorOut.println( "Tried to set null or empty handle!" );
        }
    }

    /**
     * Sets current lobby of client object
     * <p>
     * @param lobby
     */
    protected void setCurrentLobby( Lobby lobby ) {
        this.currentLobby = lobby;
    }

} // end class
