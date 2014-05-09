/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */
package systemServer;

import java.io.*;
import java.net.Socket;
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
    final private ServerReceivingThread listenerThread;
    final private ServerSendingThread writerThread;

    private ObjectOutputStream writer = null;
    private ObjectInputStream streamIn = null;

    // Server console output
    final private PrintWriter consoleOut;
    final private PrintWriter errorOut;

    // Debugging
    final private boolean debug;

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
        this.listenerThread = new ServerReceivingThread();
        this.writerThread = new ServerSendingThread();
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
        send( null, flag, tag, (Object[]) null );
    }

    /**
     * Send a lmessage to client, no pre-packaging required
 Must ensure any collections are either split up, or handled on the receiving end!
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
     * Send a lmessage to client, no pre-packaging required
 Must ensure any collections are either split up, or handled on the receiving end!
     * <p>
     * Writes to socket outgoing connection, hides the protocol details
     * <p>
     * @param flag
     * @param tag
     * @param sender
     * @param message First parameter is assumed to be tag
     */
    protected void send( String sender, Flag flag, Tag tag, Object... message ) {
        send( flag, tag, sender, MessagePhoenix.packMessageContents( message ) );
    }

    /**
     * default sender
     *
     * @param flag
     * @param tag
     * @param message
     */
    protected void send( Flag flag, Tag tag, Object... message ) {
        send( flag, tag, null, MessagePhoenix.packMessageContents( message ) );
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
 Makes the blocking receive until a lmessage arrives
     */
    private class ServerReceivingThread extends Thread {

        private boolean killed = false;

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
        private NetworkPacket recieveMessage() {
            NetworkPacket res;
            try {
                res = MessagePhoenix.recieveMessage( ClientObject.this.streamIn );
                return res;
            } catch ( IOException | ClassNotFoundException |
                      NullPointerException ex ) { // the lack of this was the cause of a lot of issues...watch in future
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
 Uses nested switch statements to parse lmessage flags and tags
         *
         * @param incomingMessage
         *
         * @return False if disconnected or a error occurred
         *
         * @author Christopher Goes
         */
        private boolean processMessage( final NetworkPacket incomingMessage ) {

            NetworkPacket rawMessage = incomingMessage;
            String smessage = "";

            Tag tag = rawMessage.getTAG();
            Flag flag = rawMessage.getFlag();
            String sender = rawMessage.getSender();
            List<Object> lmessage = rawMessage.getData();

            if ( lmessage == null ) {
                errorOut.println( "Null data!" );
                return false;
            } else if ( !lmessage.isEmpty()
                    && lmessage.get( 0 ).getClass().equals( String.class ) ) {
                smessage = (String) lmessage.get( 0 );
                lmessage = new ArrayList<>( lmessage.subList( 1, lmessage.size() ) );
            } else {
                if ( debug ) {
                    errorOut.println( "This is a poke message" );
                }
            }

            if ( debug ) {
                errorOut.println( "Process Message" );
                errorOut.println( "Tag: " + tag );
                errorOut.println( "Flag: " + flag );
                errorOut.println( "Sender: " + sender );
                errorOut.println( "Data: " + lmessage.toString() );
                errorOut.println( "stringmessage: " + smessage );
            }

            switch ( flag ) {
                // Tagged Chat Message ex: GLOBAL, LOBBY, PRIVATE, etc
                case CHAT:

                    switch ( tag ) { // assume lmessage is a single string object
                        case PRIVATE:
                            NetworkServer.
                                    sendToClient( smessage, flag, tag, sender, lmessage );
                            break;
                        case LOBBY:
                            currentLobby.sendToEntireLobby( flag, tag, sender, smessage );
                            break;
                        case GLOBAL:
                            NetworkServer.sendToAllClients( flag, tag, sender, lmessage );
                            break;
                        case SEND_CHAT_MESSAGE:
                            if ( currentLobby != null ) {
                                currentLobby.
                                        sendToEntireLobby( flag, tag, sender, smessage );
                            } else {
                                NetworkServer.sendToAllClients( flag, tag, sender, lmessage );
                            }
                            break;
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;
                // Client stuff
                case INFO:

                    switch ( tag ) {
                        case VOTE:
                        // TODO: voting!
                        case SEND_HANDLE:
                            handle = sender;
                            consoleOut.
                                    println( "Assigning handle " + handle + " to client " + clientID );
                            NetworkServer.
                                    sendToAllClients( "Server", handle + " has just connected to the server!" );

                            break;
                        case MESSAGE_TO_SERVER:
                            // empty
                            break;
                        case GLOBAL_WHO:
                            if ( NetworkServer.getTotalClients() == 0 ) {
                                send( flag, Tag.GLOBAL_WHO, "No users online." );

                            } else {
                                // All currently connected users in all lobbies
                                StringBuilder temp = new StringBuilder( (NetworkServer.
                                                                         getTotalClients() + " users online: ") );
                                for ( String s : NetworkServer.getAllUserNames() ) {
                                    temp.append( " " ).append( s );
                                }
                                send( flag, Tag.GLOBAL_WHO, temp.toString() );
                            }
                            break;
                        case LOBBY_WHO:
                            if ( currentLobby != null ) {
                                StringBuilder temp = new StringBuilder( (currentLobby.lobbyClients.
                                                                         size() + " users online: ") );
                                for ( String s : currentLobby.getUserNames() ) {
                                    temp.append( " " ).append( s );
                                }
                                send( flag, Tag.LOBBY_WHO, temp.toString() ); // TODO: may need formatting
                            } else {
                                send( flag, Tag.LOBBY_WHO, "You're not in a lobby!" );
                            }
                            break;
                        case LOBBY_INFO:
                            StringBuilder temp = new StringBuilder( "There are " + NetworkServer.
                                    getTotalLobbies() + " lobbies online: " );
                            for ( String s : NetworkServer.getLobbyNames() ) {
                                temp.append( " " ).append( s );
                            }
                            send( flag, Tag.LOBBY_INFO, temp.toString() );
                            break;
                        case UID:
                            send( flag, Tag.UID, NetworkServer.generateUniqueID() );
                            break;
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;

                // Error message
                case ERROR:

                    switch ( tag ) {
                        case INVALID_GAME_ACTION:
                            errorOut.println( "Invalid game action: " + smessage );
                            break;
                        case ERROR:
                            errorOut.println( "Generic error message: " + smessage );
                            break;
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;

                // Game state update/message/command (Anything related to game)
                case GAME:
                    switch ( tag ) {

                        case ADD_UNIT:
                            currentLobby.sendToEntireLobby(flag, tag, "Server", lmessage);
                            break;
                        case PHASE_CHANGE:
                            currentLobby.sendToEntireLobby( flag, tag, "Server", smessage );
                            break;
                        case NEXT_TURN_INFO:
                            send( flag, tag, currentLobby != null ? currentLobby.current : "" );
                            break;
                        case BEGIN_GAME:
                            if ( currentLobby == null ) {
                                send( flag, tag,
                                        "You requested to start the game, but you aren't even in a lobby!" );
                            } else {
                                currentLobby.lobbyNotification( "Client " + sender + " has requested to start a game!" );
                                // TODO: VOTING
                                send( Flag.GAME, Tag.INIT_GAME_PLEASE );  //selected client in charge of game setup
                                currentLobby.beginGame();
                                currentLobby.pokeEntireLobby( flag, Tag.BEGIN_GAME );
                                currentLobby.lobbyNotification( "The Game has begun!" );
                            }
                            break;
                        case YIELD_TURN:
                            consoleOut.
                                    println( "Client " + handle + " (id  " + clientID + " ) yielded turn" );
                            if ( currentLobby == null ) {
                                send( flag, tag, "You requested to yield your turn, but you're not even in a lobby!", false );
                            } else if ( currentLobby.current == null ? handle != null : !currentLobby.current.
                                    equals( handle ) ) {
                                send( flag, tag, "You requested to yield your turn, but it's not currently your turn!", false );
                            } else {
                                send( flag, tag, true );
                                currentLobby.advanceGameTurn(); //tell lobby handler to advance game turn
                            }
                            break;
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;

                case LOBBY:
                    switch ( tag ) {

                        case NEW_LOBBY:
                            consoleOut.
                                    println( "Received request to create lobby: " + smessage );

                            if ( NetworkServer.createNewLobby( smessage) )  {
                                NetworkServer.
                                        joinLobby( smessage, ClientObject.this );
                                send( flag, Tag.NEW_LOBBY, smessage, true );
                            } else {
                                send( flag, Tag.NEW_LOBBY, "Could not create lobby, it probably already exists!", false );
                            }
                            break;
                        case JOIN_LOBBY:
                            consoleOut.
                                    println( "Received request to join lobby: " + smessage + " from client " + handle );
                            if ( currentLobby != null && currentLobby.getName().
                                    equals( smessage ) ) {
                                send( flag, Tag.JOIN_LOBBY, "Cannot join lobby, you're already in it!", false );

                            } else if ( NetworkServer.
                                    joinLobby( smessage, ClientObject.this ) ) {
                                send( flag, Tag.JOIN_LOBBY, currentLobby.getName(), true );
                                currentLobby.
                                        lobbyNotification( "Client " + handle + " has joined the lobby!" );
                            } else {
                                send( flag, Tag.JOIN_LOBBY, "Failed to join lobby " + smessage + "! It probably doesn't exist!", false );
                            }
                            break;
                        case LEAVE_LOBBY:
                            consoleOut.
                                    println( "Client " + handle + " has requested to leave lobby" );
                            NetworkServer.leaveLobby( getHandle() );
                            currentLobby = null;
                            break;




                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;
                // Connection messages/commands ex: DISCONNECT_REQUEST
                case CONNECTION:

                    switch ( tag ) {
                        case DISCONNECT:
                            // Notify client that server recieved request, and is closing its side of the connection
                            send( Flag.CONNECTION, Tag.DISCONNECT, true ); // true always for now
                            return false;
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;
                // Anything related to file transfer, either for network or game state(for now)
                case FILE:

                    switch ( tag ) {
                        case GET_FILE: // TODO: this is not implemented to any semblance of working
                            send( Flag.FILE, Tag.GET_FILE, file.toString() );
                            break;
                        case SEND_FILE: // this also needs work
                            file = (List<String>) lmessage.get( 0 );
                            break;
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;

                // Anything that doesn't fall into above categories ex: GENERIC
                case OTHER:                   
                default:
                    consoleOut.println( "Unknown flag: " + flag + "\nTag: " + tag );
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
                        killThread();
                        NetworkServer.clientDisconnected( ClientObject.this );
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

        private void close() {
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

    } // end ServerReceivingThread

    /**
     * Sends messages to the connected client
     * <p>
     * Dies when listenerThread died
     */
    private class ServerSendingThread extends Thread {

        private boolean killed = false;

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

        private void sendMessage( NetworkPacket message ) {
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

    } // end ServerSendingThread

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
