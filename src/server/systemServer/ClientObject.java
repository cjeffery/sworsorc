/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */
package systemServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Manages the connection for a client connected to server
 */
public class ClientObject {

// User data
    final private int clientID; // Unique ID of client connection
    private String handle; // Username, may want to change later, so not final

// Connection this object is attached to
    final private Socket socket;

// The Writer Thread's food source
    final private ArrayBlockingQueue<List<Object>> messageQueue;

// Remote Send/Recieve threads
    final private ServerListenerThread listenerThread;
    final private ServerWriterThread writerThread;

// Lobby client is a part of, null if not in a lobby
    private Lobby currentLobby = null;

// Server console output
    final private PrintWriter consoleOut;

// File associated with this object
    private List<String> file;

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
    }

    /*
     * PUBLIC METHODS
     */
    /**
     * Send a message to client
     * First Object MUST be the TAG!
     * No pre-packaging required thanks to varargs
     * <p>
     * This is just a public wrapper for {@link #write write()}
     * <p>
     * @param message First parameter is assumed to be tag
     */
    public void send( Object... message ) {
        write( MessagePhoenix.packMessageContents( message ) );
    }

    /*
     * UTILITY METHODS
     */
    /**
     * Writes to socket outgoing connection, hides the protocol details
     * <p>
     * @param message Message to send
     */
    private void write( final List<Object> message ) {
        if ( message == null ) {
            System.err.println( "Attempted to write null message to queue!" );
        } else {
            try {
                messageQueue.put( message );
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

    /*
     * LISTENERTHREAD
     */
    /**
     * Handles incoming messages from the client connection it is associated with
     * Makes the blocking receive until a message arrives
     */
    private class ServerListenerThread extends Thread {

        private ObjectInputStream streamIn = null;

        private boolean killed = false;

        /**
         * Constructor for ListenerThread, creates stream and object
         * NOTE: Socket must be set before creating a ListenerThread object!
         */
        private ServerListenerThread() {
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
                    this.streamIn = new ObjectInputStream( ClientObject.this.socket.getInputStream() );
                } catch ( IOException ex ) {
                    ex.printStackTrace();
                }
            } else {
                System.err.println( "Error creating listen stream: socket isn't connected!" );
            }
        }

        /**
         * Wrapper for {@link MessagePhoenix#recieveMessage(java.io.ObjectInputStream) }
         * <p>
         * @return List received, or null if not connected
         */
        private List<Object> recieveMessage() { // breaking my own null rule
            return isConnected() ? MessagePhoenix.recieveMessage( this.streamIn ) : null;
        }

        private void disconnect() {
            consoleOut.println( "Client " + clientID + " (" + handle + "): disconnected" );
            NetworkServer.clientDisconnected( clientID );
        }

        private void killThread() {
            this.killed = true;
            close();
        }

        @Override
        public void run() {
            createStream(); // start the stream!

            // Temporary containers
            String TAG = null;
            List<Object> rawMessage = null;
            List<String> stringMessage = new ArrayList<>( 0 );
            // TODO: process message method

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
                    continue;
                } else if ( rawMessage.get( 0 ) instanceof String ) {
                    TAG = rawMessage.get( 0 ).toString();
                } else {
                    System.err.println( "Non-String TAG recieved!" );
                    System.err.println( "Class: " + rawMessage.getClass().getName() );
                    //killThread();
                    continue;
                }

                if ( TAG == null ) {
                    System.err.println( "Tag is null!" );
                    killThread();
                    continue;
                } else if ( TAG.equals( MessagePhoenix.DISCONNECT_REQUEST ) ) {
                    killThread(); // This disconnects
                    continue;
                }

                rawMessage = rawMessage.subList( 1, rawMessage.size() );

                if ( rawMessage.getClass().equals( stringMessage.subList( 0, 0 ).getClass() ) ) {
                    stringMessage = MessagePhoenix.objectToString( rawMessage );
                    for ( String s : stringMessage ) {
                        System.out.println( "Received strings: " + s );
                    }

                } else {
                    System.err.println( "Unknown object recieved!" );
                    continue; // non-critical error?
                }
                if ( TAG.equals( MessagePhoenix.GLOBAL_CHAT ) ) {
                    consoleOut.println( stringMessage ); // Server console print
                    NetworkServer.sendToAllClients( stringMessage ); // Global broadcast
                } else if ( TAG.equals( MessagePhoenix.FILE ) ) { // TODO: rewrite file handling
                    consoleOut.println( "Receiving file " + rawMessage.get( 0 ) );
                    file = new ArrayList<>( 0 );
                } else if ( TAG.equals( MessagePhoenix.FILE_LINE ) ) {
                    if ( file != null ) {
                        file.add( stringMessage.get( 1 ) );
                        consoleOut.println( rawMessage.get( 1 ) );
                    } else {
                        System.err.println( "No file created to receive!" );
                    }
                } else if ( TAG.equals( MessagePhoenix.PRINT_FILE ) ) {
                    if ( file != null ) {
                        for ( String file1 : file ) {
                            consoleOut.println( file1 );
                        }
                    } else {
                        System.err.println( "No file loaded!" );
                    }
                } else if ( TAG.equals( MessagePhoenix.REQUEST_ID ) ) {
                    send( MessagePhoenix.ID, NetworkServer.generateID() );
                } else if ( TAG.equals( MessagePhoenix.REQUEST_GLOBAL_WHO ) ) {
                    //Client asked for list of current connected users
                    List<String> handles = NetworkServer.getAllUserNames();
                    send( MessagePhoenix.GLOBAL_WHO_LIST, handles );
                } else if ( TAG.equals( MessagePhoenix.REQUEST_BEGIN_GAME ) ) {
                    //Client asked agree to start the game
                    //TODO: Voting here? Right now, we start when any single client requests

                    if ( currentLobby == null ) {
                        send( MessagePhoenix.NAG,
                              "You requested to start the game, but you aren't even in a lobby!" );
                    } else {

                        consoleOut.println( "Client " + handle
                                + " requested to start game in lobby " + currentLobby.getName() );

                        currentLobby.beginGame();
                        currentLobby.sendToEntireLobby( MessagePhoenix.GAME_BEGUN );
                    }

                } else if ( TAG.equals( MessagePhoenix.CREATE_NEW_LOBBY_REQUEST ) ) {
                    //client asks to create new lobby, and provided name

                    String requestedLobbyName = stringMessage.get( 0 );
                    consoleOut.println( "Received request to create lobby: "
                            + requestedLobbyName );

                    if ( NetworkServer.createNewLobby( stringMessage.get( 0 ) ) ) {

                        NetworkServer.joinLobby( stringMessage.get( 0 ), ClientObject.this );
                        send( MessagePhoenix.APROVE_NEW_LOBBY_REQUEST );
                    } else {
                        send( MessagePhoenix.DENY_NEW_LOBBY_REQUEST );
                    }

                } else if ( TAG.equals( MessagePhoenix.REQUEST_LOBBY_INFO ) ) {
                    //client asks to create new lobby, and provided name
                    //Send current lobbies to client:
                    List<String> lobbyNames = NetworkServer.getLobbyNames();
                    for ( String lobby : lobbyNames ) {
                        send( MessagePhoenix.LOBBY_INFO,
                              NetworkServer.getLobbyUsers( lobby ) );
                    }

                } else if ( TAG.equals( MessagePhoenix.JOIN_LOBBY_REQUEST ) ) {
                    //client requested to join lobby.
                    consoleOut.println( "Received request to join lobby: " + rawMessage.get( 1 ) );
                    NetworkServer.joinLobby( stringMessage.get( 0 ), ClientObject.this );
                    //TODO: implement/send an accept/reject message
                } else if ( TAG.equals( MessagePhoenix.LEAVE_LOBBY_REQUEST ) ) {
                    consoleOut.println( handle + " has requested to leave lobby" );
                    NetworkServer.leaveLobby( ClientObject.this );
                    currentLobby = null;

                } else if ( TAG.equals( MessagePhoenix.SEND_HANDLE ) ) {
                    //client has sent us their new handle:
                    handle = stringMessage.get( 0 );
                    consoleOut.println( "Assigning handle " + handle
                            + " to client " + clientID );
                    stringMessage.add( 0, MessagePhoenix.CONNECT_ANNOUNCEMENT ); // TODO: makeshift for now
                    NetworkServer.sendToAllClients( stringMessage );

                } else if ( TAG.equals( MessagePhoenix.ADD_UNIT ) ) { // TODO: update these
                    NetworkServer.sendToAllClients( stringMessage );
                } else if ( TAG.equals( MessagePhoenix.UPDATE_UNIT ) ) {
                    NetworkServer.sendToAllClients( stringMessage );
                } else if ( TAG.equals( MessagePhoenix.YIELD_TURN ) ) {
                    consoleOut.println( "Client " + handle
                            + " (id  " + clientID + " ) yielded turn" );

                    if ( currentLobby == null ) {
                        //client requested to change turns, but it's not their turn!
                        send( MessagePhoenix.NAG, "You requested to yield your turn, but you're not even in lobby!" );
                    } else if ( currentLobby.current.getClientID() != clientID ) {
                        //client requested to change turns, but it's not their turn!
                        send( MessagePhoenix.NAG, "Requested to yield turn, but it's not your turn!" );
                    } else {
                        currentLobby.advanceGameTurn(); //tell lobby handler to advance game turn
                    }

                } else {
                    // TODO: will add other protocols
                    System.err.println( "Unknown tag! Printing message..." );
                    System.out.println( "Tag is : " + TAG );
                    for ( String s : stringMessage ) {
                        System.err.println( s + " " );
                    }
                    System.err.println( "End of unknown message" );
                }
            }
            close();

        }

        public void close() {
            if ( isConnected() ) {
                disconnect();
            }
            try {
                if ( !(socket.isClosed()) ) {
                    socket.close();
                }
                if ( streamIn != null ) {
                    streamIn.close();
                    streamIn = null;
                }

            } catch ( IOException e ) {
                System.err.println( e );
            }
        }

    } // end ServerListenerThread

    // WRITER THREAD //
    /**
     * Sends messages to the client connection thread is associated with
     * Created & Destroyed with their associated superclass ClientObject
     */
    private class ServerWriterThread extends Thread {

        private ObjectOutputStream writer = null;

        private boolean killed = false;

        private ServerWriterThread() {
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
                    this.writer = new ObjectOutputStream( ClientObject.this.socket.getOutputStream() );
                    this.writer.flush();
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

        private void sendMessage( final List<Object> message ) {
            try {
                MessagePhoenix.sendMessage( this.writer, message );
            } catch ( IOException ex ) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            setWriter(); // starts stream!

            List<Object> message = new ArrayList<>( 0 );

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
                    System.err.println( "Null message!" );
                    killThread();
                }
            }
            close();
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
                System.err.println( "Error in " + ex.getClass().getEnclosingMethod().getName()
                        + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause() );
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
            System.err.println( "Tried to set null handle!" );
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
