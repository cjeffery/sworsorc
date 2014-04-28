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

    // TODO: or should this be a subclass of NetworkClient/server? jeez chris get it together
    // User data
    final private int clientID; // Unique ID of client connection
    private String handle; // Username, may want to change later, so not final

    // Connection this object is attached to
    final private Socket socket;

    // The Writer Thread's food source
    final private ArrayBlockingQueue<NetworkPacket> messageQueue;

    // Remote Send/Recieve threads
    final private ServerListenerThread listenerThread;
    final private ServerWriterThread writerThread;

    private ObjectOutputStream writer = null;
    private ObjectInputStream streamIn = null;

    // Lobby client is a part of, null if not in a lobby
    private Lobby currentLobby = null;

    // Server console output
    final private PrintWriter consoleOut;
    final private PrintWriter errorOut;

    // File associated with this object
    private List<String> file;

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
    public ClientObject( Socket sock, int clientID ) {

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
    }

    /**
     * Writes to socket outgoing connection, hides the protocol details
     *
     * @param flag
     * @param tag
     */
    private void send( final Flag flag, final Tag tag ) {
        write( flag, tag, null, null );
    }

    /**
     * Writes to socket outgoing connection, hides the protocol details
     *
     * @param flag
     * @param tag
     */
    private void send( final Flag flag, final Tag tag, List<Object> message ) {
        write( flag, tag, null, message );
    }

    /**
     * Send a message to client
     * First Object MUST be the TAG!
     * No pre-packaging required thanks to varargs
     * <p>
     * This is just a public wrapper for {@link #write write()}
     * <p>
     * @param flag
     * @param tag
     * @param sender
     * @param message First parameter is assumed to be tag
     */
    public void send( Flag flag, Tag tag, String sender, Object... message ) {
        write( flag, tag, sender, MessagePhoenix.packMessageContents( message ) );
    }

    public void send( Flag flag, Tag tag, Object... message ) {
        write( flag, tag, null, MessagePhoenix.packMessageContents( message ) );

    }

    /**
     * Writes to socket outgoing connection, hides the protocol details
     *
     * @param flag
     * @param tag
     * @param message
     */
    private void write( final Flag flag, final Tag tag, final String sender,
                        final List<Object> message ) {
        writeToQueue( new NetworkPacket( flag, tag, message, (sender == null || sender.isEmpty()) ? "Server" : sender ) );
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
        private NetworkPacket recieveMessage() { // breaking my own null rule
            return MessagePhoenix.recieveMessage( ClientObject.this.streamIn );
        }

        private void killThread() {
            this.killed = true;
            close();
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

            Tag tag = rawMessage.getTAG();
            Flag flag = rawMessage.getFlag();
            String sender = rawMessage.getSender();
            List<Object> message = rawMessage.getData();

            if ( debug ) {
                errorOut.println( "Process Message" );
                errorOut.println( "Tag: " + tag );
                errorOut.println( "Flag: " + flag );
                errorOut.print( "Sender: " + sender );
                errorOut.print( "Data: " );
                for ( Object s : message ) {
                    errorOut.println( "Object " + s.getClass() + ": " + s + " " );
                }
            }

            switch ( flag ) {
                // Tagged Chat Message ex: GLOBAL, LOBBY, PRIVATE, etc
                case CHAT:

                    switch ( tag ) {
                        case PRIVATE:

                            NetworkServer.sendToClient( sender, flag, tag, message );
                            break;
                        case LOBBY:
                            currentLobby.sendToEntireLobby( flag, tag, message );
                            break;
                        case GLOBAL:
                            NetworkServer.sendToAllClients( flag, tag, message );
                            break;
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;
                // Client stuff?
                case CLIENT:

                    switch ( tag ) {
                        case SEND_HANDLE:
                            consoleOut.println( "Assigning handle " + handle + " to client " + clientID );
                            handle = sender;
                            NetworkServer.
                                    sendToAllClients( handle + "has just connected to the server!" );
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
                    //jarvis.processMessage( message.subList(1, message.size()), TAG );
                    // TODO: conductor will go in here somewhere
                    switch ( tag ) {

                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;
                // Request for inforation ex: REQUEST_LOBBY_INFO
                case REQUEST: // client shouldn't recieve these!!!
                    flag = Flag.RESPONSE; // make life easier
                    switch ( tag ) {

                        case GLOBAL_WHO_REQUEST:
                            send( flag, Tag.GLOBAL_WHO_RESPONSE, NetworkServer.getAllUserNames() );
                            break;
                        case LOBBY_INFO_REQUEST:
                            // TODO: possible List<String> to List<Object> conversion issue
                            if ( currentLobby == null ) {
                                List<String> lobbyinfo = new ArrayList<>( 0 );
                                lobbyinfo.add( "Lobbies: " );
                                // this can be written in a much more verbose manner
                                // just wanna see if this works. gotta "cowboy code" every once in a while for kicks...
                                for ( String lobby : NetworkServer.getLobbyNames() ) {
                                    lobbyinfo.add( lobby + Arrays.toString( NetworkServer.
                                            getLobbyUsers( lobby ).toString().
                                            split( " " ) ) );
                                } // could use a functional as well...hmm...
                                send( flag, Tag.LOBBY_INFO_RESPONSE, lobbyinfo );
                            } else {
                                send( flag, Tag.LOBBY_INFO_RESPONSE, currentLobby.getUserNames() );
                            }
                            break;
                        case NEW_LOBBY_REQUEST:
                            consoleOut.println( "Received request to create lobby: " + message.
                                    get( 0 ) );

                            if ( NetworkServer.createNewLobby( (String) message.get( 0 ) ) ) {
                                NetworkServer.
                                        joinLobby( (String) message.get( 0 ), ClientObject.this );
                                send( flag, Tag.NEW_LOBBY_RESPONSE, true, "Lobby " + message.
                                        get( 0 ) + " has been created!" );
                            } else {
                                send( flag, Tag.NEW_LOBBY_RESPONSE, false, "Could not create lobby, it probably already exists!" );
                            }
                            break;
                        case JOIN_LOBBY_REQUEST:
                            String lobby = (String) message.get( 0 );
                            consoleOut.
                                    println( "Received request to join lobby: " + lobby + " from client " + handle );
                            if ( currentLobby != null && currentLobby.getName().
                                    equals( lobby ) ) {
                                send( flag, Tag.JOIN_LOBBY_RESPONSE, "Cannot join lobby, you're already in it!" );

                            } else if ( NetworkServer.joinLobby( handle, ClientObject.this ) ) {
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
                            send( flag, Tag.UID_RESPONSE, NetworkServer.generateID() );
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
                            }
                            break;
                        case SEND_FILE_REQUEST:
                        case GET_FILE_REQUEST:
                        case CREATE_LOBBY_REQUEST:
                        case YIELD_TURN_REQUEST:
                            // TODO: implement this!
                            consoleOut.
                                    println( "Client " + handle + " (id  " + clientID + " ) yielded turn" );

                            if ( currentLobby == null ) {
                                //client requested to change turns, but it's not their turn!
                                //send( MessagePhoenix.NAG, "You requested to yield your turn, but you're not even in lobby!" );
                            } else if ( currentLobby.current.getClientID() != clientID ) {
                                //client requested to change turns, but it's not their turn!
                                //send( MessagePhoenix.NAG, "Requested to yield turn, but it's not your turn!" );
                            } else {
                                currentLobby.advanceGameTurn(); //tell lobby handler to advance game turn
                            }
                            break;
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;
                // Response to information request ex: LOBBY_INFO
                case RESPONSE: // Not used much on the server side (sorry if it seems backwards, remember, there is no spoon)
                    flag = Flag.REQUEST; // MAKING my life eAsiEr
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
                            // TODO: disconnect stuff
                            return false;
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;
                // Anything related to file transfer, either for network or game state(for now)
                case FILE:

                    switch ( tag ) {
                        case GET_FILE_REQUEST:
                            // send file
                            break;
                        case SEND_FILE_REQUEST:
                            // prepare to recieve file
                            break;
                        default:
                            consoleOut.println( "Unknown tag: " + tag );
                    }
                    break;
                // Anything that doesn't fall into above categories ex: GENERIC
                case OTHER:

                    switch ( tag ) {

                        case NAG: // What purpose does this serve now?
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

                if ( isConnected() ) {
                    rawMessage = recieveMessage();
                } else {
                    consoleOut.
                            print( "Client " + ClientObject.this.getClientID() + " lost connection!" );
                    killThread();
                    continue;
                }

                if ( rawMessage == null ) { //connection broken (does NOT throw an exception)
                    killThread();
                } else if ( processMessage( rawMessage ) ) {
                    // suprise party fiddlesticks lives here
                } else {
                    // do someting like dis the connect
                }
            }
            close();
        }

        private void disconnect() {
            // TODO: KILL CONNECTION
            NetworkServer.clientDisconnected( ClientObject.this );
        }
        public void close() {
            if ( isConnected() ) {
                try {
                    if ( streamIn != null ) {
                        streamIn.close();
                        streamIn = null;
                    }

                } catch ( IOException e ) {
                    errorOut.println( e );
                }
            }
        }

    } // end ServerListenerThread

    /**
     * Sends messages to the client connection thread is associated with
     * Created & Destroyed with their associated superclass ClientObject
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

            while ( !killed ) {
                try {
                    message = messageQueue.take();
                } catch ( InterruptedException ex ) {
                    ex.printStackTrace();
                    killThread();
                }
                if ( message != null ) {
                    // assume first object is tag
                    sendMessage( message );
                } else {
                    errorOut.println( "Null message!" );
                    killThread();
                }
            }
            close(); // TODO: make sure closing  in proper order!
        }

        /**
         * Always run this before returning from {@link #run run}!
         */
        private void close() {
            try {
                if ( !(isConnected()) ) {
                    if ( writer != null ) {
                        writer.close();
                        writer = null;
                    }
                } else {
                    // TODO: disconnect
                    if ( writer != null ) {
                        writer.close();
                        writer = null;
                    }

                }
            } catch ( IOException ex ) {
                ex.printStackTrace();
            }
        }

    } // end ServerWriterThread

    /**
     * Starts the ClientObject, opening the connection
     * <p>
     * @author Christopher Goes
     */
    public void start() {

        listenerThread.start();
        writerThread.start();
        consoleOut.println( "Opened connection from client " + clientID + " at address " + socket.
                getInetAddress() );
    }

    public void killClient() {
        listenerThread.killThread();
        writerThread.killThread();
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
    public String getHandle() {
        return this.handle;
    }

    /**
     * Gets the clientID of client object
     * <p>
     * @return clientID Unique ID of client
     * <p>
     * @author Christopher Goes
     */
    public int getClientID() {
        return this.clientID;
    }

    /**
     * Sets the handle (username) of the ClientObject
     * <p>
     * @param hand Handle to set
     * <p>
     * @author Christopher Goes
     */
    public void setHandle( String hand ) {
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
    public void setCurrentLobby( Lobby lobby ) {
        this.currentLobby = lobby;
    }

} // end class
