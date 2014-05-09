/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package systemServer;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import javafx.application.Platform;
import mainswordsorcery.Game;

/**
 * The Infamous Network Client, handles communication to/from Network Server
 * Call initializeClient() to startup the client and establish connection with server
 * <p>
 * You may safely access the setters/getters without initialization
 * However, any other method calls will have unpredictable, and likely unstable, results if called
 * before the client has been initialized.
 * Check this with {@link #clientIsInitialized clientIsInitialized}
 * <p>
 * @author Networking Subteam
 */
final public class NetworkClient {

    // iNet variables
    private static Socket socket = null;
    private static int port = 25565; // standard SworSorc port
    private static String serverName = "127.0.0.1"; // default to loopback

    // Read/write streams
    private static BufferedReader consoleIn = null;
    private static PrintWriter consoleOut = null;

    // Client info
    private static String username = "default_user";
    private static String currentLobby = "";

    // Streams & Threads
    private static ClientReceivingThread listenerThread;
    private static ClientSendingThread writerThread;
    private static ClientCommandThread clientThread;

    private static ObjectOutputStream writer;
    private static ObjectInputStream streamIn;

    // File path defaults
    // TODO: change these in configureSettings()
    private static String helpfile = "commands.txt"; // TODO: update from settings file
    private static String dir = System.getProperty( "user.dir" );
    private static String networkDirectory = System.getProperty( "user.dir" )
            + File.separator + "src" + File.separator + "server" + File.separator;

    // Buffers
    private static ArrayBlockingQueue<NetworkPacket> messageQueue;
    private static ArrayBlockingQueue<String> commandQueue;
    private volatile static int uid;

    // Flags
    private static boolean clientInitialized = false;
    final private static boolean debug = MessagePhoenix.debugStatus();

    private static boolean phasing = false;

    /**
     * Default Startup of NetworkClient
     * Starts local streams, connects to server, and makes the connection live
     * <p>
     * NOTE: will use default settings file of "netclient_settings.txt"
     * <p>
     * @return True if started OK, False if connection or startup failed
     *
     * @author Christopher Goes
     */
    public static boolean initializeClient() { // default
        return initializeClient( "netclient_settings.txt" );
    }

    /**
     * Startup of NetworkClient
     * Starts local streams, connects to server, and makes the connection live
     * <p>
     * @param filename
     *                 Name of Network Settings file, no path necessary
     *                 Method will search for and find the file, as long in sworsorc directory
     * <p>
     * @return True if started OK, False if connection failed
     *
     * @author Christopher Goes
     */
    public static boolean initializeClient( String filename ) {
        configureSettings( filename );
        return startClient();
    }
    
    /**
     * Initialize client without going through file
     * @param username The username
     * @param ip_addr The IP address to connect to
     * @return whether it exploded or not (true is good)
     *
     * @author Colin Clifford
     */
    public static boolean initializeClient(String username, String ip_addr) {
        serverName = ip_addr;
        NetworkClient.username = username;
        return startClient();
    }
    
    /**
     * Start network client after settings have been set from file or whatever
     * @author Christopher Goes
     */
    private static boolean startClient() {
        // 30 slots, FIFO access
        messageQueue = new ArrayBlockingQueue<>( 30, true ); 
        commandQueue = new ArrayBlockingQueue<>( 30, true );
        startLocalStreams();
        startCommandProcessor();
        if ( connect() ) {
            clientInitialized = true;
            startRemoteConnection();
        } else {
            clientInitialized = false;
        }
        return clientInitialized;        
    }

    /**
     * Tagged message with client username as sender
     * Basically a "poke" to execute a command, send a request, etc
     * <p>
     * @param flag
     * @param tag
     *
     * @author Christopher Goes
     */
    public static void poke( Flag flag, Tag tag ) {
        send( flag, tag );
    }

    /**
     * Send a message to the currently connected server
     * This includes chat & game messages!
     * <p>
     * Generic parameters
     * <p>
     * @param flag
     * @param tag
     * @param message
     *
     * @author Christopher Goes
     */
    public static void send( Flag flag, Tag tag, Object... message ) {
        if ( !clientInitialized ) {
            return;
        }
        // Assume any outgoing messages from the client, are from the client
        writeToQueue( new NetworkPacket( flag, tag, username, MessagePhoenix.
                packMessageContents( message ) ) );
    }

    /**
     * Processes raw user input
     * This also allows for programattic execution of user console commands
     * <p>
     * @param command
     *
     * @author Christopher Goes
     */
    public static void userInput( String command ) {
        if ( !clientInitialized ) { // rough hack
            return;
        }
        try {
            commandQueue.put( command );
        } catch ( InterruptedException ex ) {
            ex.printStackTrace();
        }
    }

    /**
     * Send a chat message, server-determined privacy
     *
     * @param message
     *
     * @author Christopher Goes
     */
    public static void sendChatMessage( String message ) {
        send( Flag.CHAT, Tag.SEND_CHAT_MESSAGE, message );
        if ( !currentLobby.isEmpty() ) {
            flushToConsole( "(" + currentLobby + ") " + message );
        } else {
            flushToConsole( "(GLOBAL) " + message );
        }

    }

    /**
     * Send a private message to the specified user
     *
     * @param user
     * @param message
     *
     * @author Christopher Goes
     */
    public static void sendPrivateMessage( String user, String message ) {
        send( Flag.CHAT, Tag.PRIVATE, message );
        //todo: echo to console?

    }

    /**
     * Sends a message to other users indicating a phase change!
     * <p>
     * @param phase
     *
     * @author Game Pearhill
     * @param endphase
     */
    public static void sendPhaseChange( String phase, String endphase ) {
        send( Flag.GAME, Tag.PHASE_CHANGE, phase, endphase );
    }

    /**
     * Inform the server that this user has finished all phases of their current
     * player turn.
     * <p>
     * This shouldn't be called when it isn't the user's game turn.
     * <p>
     * For context: After this message is sent, the server may pass control to
     * the next user, or the next game turn may start, or the game may end.
     */
    public static void endTurn() {
        send( Flag.GAME, Tag.YIELD_TURN );
    }
    
    /**
     * Try to create a lobby
     * @param lobby
     */
    public static void createLobby(String lobby) {
        send( Flag.LOBBY, Tag.NEW_LOBBY, lobby );
    }

    /**
     * Attempt to join a lobby
     *
     * @param lobby
     *
     * @author Christopher Goes
     */
    public static void joinLobby(String lobby) {
        send( Flag.LOBBY, Tag.JOIN_LOBBY, lobby );
    }
    
    /**
     * request to start game
     */
    public static void startGame() {
        send( Flag.GAME, Tag.BEGIN_GAME );
    }
    
    public static boolean isPhasing() {
        return phasing;
    }

    /**
     * Generates a "globally" unique ID from the server
     *
     * @return
     *
     * @author Colin Clifford(hope i spelled that right), Christopher Goes
     */
    public static int generateUniqueID() {
        uid = -1;
        send( Flag.INFO, Tag.UID );
        while(uid == -1) {
            try { Thread.sleep(1); } catch(InterruptedException e) {}
        }
        return uid;
    }

    // ***THREADS*** //
    // TODO: if server dies, make sure client doesn't panic
    /**
     * Tests if threads are active
     *
     * @return
     *
     * @author Christopher Goes
     */
    private static boolean remoteConnectionIsAlive() {
        return ((listenerThread != null && listenerThread.isAlive())
                || (clientThread != null && clientThread.isAlive()));
    }

    /**
     * Processes user input and handles execution of user commands
     *
     * @author Christohper Goes
     */
    private static class ClientCommandThread extends Thread {

        private boolean killed = false;

        /**
         * Parses user input, executes commands, and sends messages to server
         * <p>
         * @param command
         *                <p>
         * @return boolean True if executed normally, False if quit or exception
         * @author Christopher Goes
         */
        private boolean processCommand( final String command ) {

            if ( command == null ) {
                System.err.println( "Null string passed to processInput!" );
                return false;
            }

            String[] parsedString;
            parsedString = command.split( "\\s+" ); //Split line by whitespace

            if ( parsedString.length > 2 ) {
                if ( "/msg".equals( parsedString[0] ) ) {
                    StringBuilder temp = new StringBuilder( parsedString.length );
                    for ( int i = 2; i < parsedString.length; i++ ) {
                        temp.append( " " ).append( parsedString[i] );
                    }
                    sendPrivateMessage( parsedString[1], temp.toString() );
                } else if ( isConnected() ) {
                    sendChatMessage( parsedString[0] );
                } else {
                    return false;
                }
            }
            if ( parsedString.length == 2 ) {
                if ( "/sendFile".equals( parsedString[0] ) ) {
                    sendFile( parsedString[1] );
                } else if ( "/newLobby".equals( parsedString[0] ) ) {
                    send( Flag.LOBBY, Tag.NEW_LOBBY, parsedString[1] );
                } else if ( "/joinLobby".equals( parsedString[0] ) ) {
                    send( Flag.LOBBY, Tag.JOIN_LOBBY, parsedString[1] );
                } else if ( isConnected() ) {
                    sendChatMessage( parsedString[0] );
                } else {
                    return false;
                }
            } else if ( parsedString.length == 1 ) {
                if ( "/globalWho".equals( parsedString[0] ) ) {
                    send( Flag.INFO, Tag.GLOBAL_WHO );
                } else if ( "/lobbyWho".equals( parsedString[0] ) ) {
                    send( Flag.INFO, Tag.LOBBY_WHO );
                } else if ( "/leaveLobby".equals( parsedString[0] ) ) {
                    currentLobby = "";
                    send( Flag.LOBBY, Tag.LEAVE_LOBBY );
                } else if ( "/showLobbies".equals( parsedString[0] ) ) {
                    send( Flag.INFO, Tag.LOBBY_INFO );
                } else if ( "/disconnect".equals( parsedString[0] ) ) { // Manual client disconnect
                    if ( remoteConnectionIsAlive() ) {
                        send( Flag.CONNECTION, Tag.DISCONNECT );
                    } else if ( isConnected() ) {
                        flushToConsole( "Error! connection isn't alive but socket is!" );
                        return false;
                    } else {
                        flushToConsole( "Can't disconnect when you're not connected!" );
                    }
                } else if ( "/yieldTurn".equals( parsedString[0] ) ) { // client turn over
                    endTurn();
                } else if ( "/beginGame".equals( parsedString[0] ) ) { // request to start game
                    send( Flag.GAME, Tag.BEGIN_GAME );
                } else if ( "/help".equals( parsedString[0] ) ) {
                    printCommandList();
                } else if ( "/printFile".equals( parsedString[0] ) ) {
                    send( Flag.FILE, Tag.GET_FILE );
                } else if ( "/reconnect".equals( parsedString[0] ) ) {
                    if ( remoteConnectionIsAlive() ) {
                        flushToConsole( "You're already connected!" );
                        return true;
                    } else if ( isConnected() ) {
                        flushToConsole( "Error! connection isn't alive but socket is!" );
                        return false;
                    }
                    flushToConsole( "Attempting to reconnect..." );
                    if ( connect() ) {
                        startRemoteConnection();
                        flushToConsole( "Successfully reconnected!" );
                    } else {
                        flushToConsole( "Reconnect failed" );
                    }

                } else if ( "/quit".equals( parsedString[0] ) ) {
                    flushToConsole( "Exiting client..." );
                    if ( isConnected() ) {
                        flushToConsole( "Still connected to server, disconnecting" );
                        if ( remoteConnectionIsAlive() ) {
                            send( Flag.CONNECTION, Tag.DISCONNECT );
                        }
                    }
                    return false;
                } else if ( parsedString[0].length() != 0 && "/".
                        equals( parsedString[0].substring( 0, 0 ) ) ) {
                    flushToConsole( "Invalid command, try again, or type /help for a list of commands." );
                } else if ( isConnected() ) {
                    sendChatMessage( parsedString[0] );
                } else {
                    return false;
                }
            } else {
                if ( isConnected() ) {
                    sendChatMessage( parsedString[0] );
                } else {
                    return false;
                }
            }
            return true;
        }

        /**
         * Marks thread for death, causing it to close, return, and die
         * (*cough cough "or die" *cough cough*)
         * <p>
         * @author Christopher Goes
         */
        public void killThread() {
            this.killed = true;
        }

        private String getCommand() {
            try {
                return commandQueue.take();
            } catch ( InterruptedException ex ) {
                ex.printStackTrace();
                return null;
            }
        }

        /**
         * Main execution thread for the Network Client
         * <p>
         * Reads from consoleIn(command line) and GUI chat window
         * <p>
         * @author Christopher Goes
         */
        @Override
        public void run() {
            String line = "";

            while ( !killed ) {
                line = getCommand();
                if ( line == null ) {
                    System.err.
                            println( "Null user input! Either default never got changed, or something bad happened!" );
                    stopClient();
                } else if ( line.isEmpty() ) {
                    System.err.println( "Empty line" );
                } else if ( !(processCommand( line )) ) {
                    //flushToConsole( "Later gator!" );
                    //stopClient(); // Shutdown everything
                    System.err.println( "Unable to process command!" );
                }
                // Continue execution
            }
        }

    }

    private static class ClientSendingThread extends Thread {

        private boolean killed = false;

        /**
         * Initializes writer with a new stream.
         * <p>
         * NOTE: Socket must be set before calling this!
         * <p>
         * @author Christopher Goes
         */
        private boolean setWriter() {
            if ( isConnected() ) {
                try {
                    writer = new ObjectOutputStream( socket.getOutputStream() );
                    writer.flush();
                    return true;
                } catch ( IOException ex ) {
                    ex.printStackTrace();
                    return false;
                }
            } else {
                System.err.println( "Error in setWriter: not connected!" );
                return false;
            }
        }

        /**
         * Marks thread for death, causing it to close, return, and die
         * <p>
         * @author Christopher Goes
         */
        public void killThread() {
            this.killed = true;
        }

        private void sendMessage( NetworkPacket message ) {
            MessagePhoenix.sendMessage( writer, message );
        }

        @Override
        public void run() {
            if ( !setWriter() ) {
                flushToConsole( "Unable to start writerThread!" );
                killThread();
            }
            NetworkPacket message = null;

            while ( !killed ) {
                try {
                    message = messageQueue.take();
                } catch ( InterruptedException ex ) {
                    ex.printStackTrace();
                    killThread();
                }
                if ( message != null || !isConnected() ) { // Don't want null messages
                    sendMessage( message );
                } else if ( !isConnected() ) {
                    flushToConsole( "Lost connection to server!" );
                    killThread();
                } else {
                    System.err.println( "Null message given to client writer thread!" );
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

    }

    /**
     * Listens for and handles incoming communications for Network Client
     */
    private static class ClientReceivingThread extends Thread {

        private boolean killed = false;

        /**
         * Creates input stream, and connects to socket
         * <p>
         * NOTE: Must be called when creating thread!
         * <p>
         * @author Christopher Goes
         */
        private boolean createStream() {
            if ( isConnected() ) {
                try {
                    streamIn = new ObjectInputStream( socket.getInputStream() );
                    return true;
                } catch ( IOException ex ) {
                    ex.printStackTrace();
                    return false;
                }
            } else {
                System.err.println( "Error creating listen stream: socket isn't connected!" );
                return false;
            }
        }

        /**
         * Marks thread for death, causing it to close, return, and die
         * <p>
         * @author Christopher Goes
         */
        public void killThread() {
            killed = true;
        }

        /**
         *
         * @return
         * @author Christopher Goes
         */
        private NetworkPacket recieveMessage() {
            if(streamIn == null || !isConnected()) {
                return null;
            }
            try {
                return MessagePhoenix.recieveMessage( streamIn );
                
            }
            catch ( IOException | ClassNotFoundException | 
                    NullPointerException ex)
            {
                ex.printStackTrace();
                return null;
            }
        }

        /**
         * Processes incoming message to NetworkClient
         * <p>
         * Uses nested switch statements to parse message flags and tags
         * <p>
         * @param incomingMessage
         *                        <p>
         * @return True unless critical/fatal error
         * <p>
         * @author Christopher Goes
         */
        private boolean processMessage( final NetworkPacket incomingMessage ) {
            NetworkPacket rawMessage = new NetworkPacket();
            String stringmessage = "";
            if ( incomingMessage != null ) {
                rawMessage = incomingMessage;
            } else {
                flushToConsole( "Null message processed by processMessage in the listener thread!" );
                return false;
            }

            List<Object> message;
            Tag tag;
            Flag flag;
            String sender;

            tag = rawMessage.getTAG();
            flag = rawMessage.getFlag();
            sender = rawMessage.getSender();

            message = rawMessage.getData();
            if ( message == null ) {
                System.out.println( "Null data!" );
                return false;
            } else if ( !message.isEmpty() && message.get( 0 ).getClass().equals( String.class ) ) {
                stringmessage = (String) message.get( 0 );
                message = new ArrayList<>( message.subList( 1, message.size() ) );
            }

            // Debugging
            if ( debug ) {
                System.err.println( "Process Message" );
                System.err.println( "Tag: " + tag );
                System.err.println( "Flag: " + flag );
                System.err.println( "Sender: " + sender );
                System.err.println( "Data: " + message.toString() );
                System.err.println( "stringmessage: " + stringmessage );
            }

            switch ( flag ) {
                // Tagged Chat Message ex: GLOBAL, LOBBY, PRIVATE, etc
                case CHAT:

                    switch ( tag ) {
                        case PRIVATE:
                            flushToConsole( "(Private) " + sender + ": " + stringmessage );
                            break;
                        case LOBBY:
                            flushToConsole( "(" + currentLobby + ") " + sender + ": " + stringmessage );
                            break;
                        case GLOBAL:
                            flushToConsole( "(GLOBAL) " + sender + ": " + stringmessage );
                            break;
                        default:
                            flushToConsole( "Unknown tag: " + tag );
                    }
                    break;
                // Server-Client Network command communication
                case INFO:

                    switch ( tag ) {
                        case SEND_HANDLE:
                        case MESSAGE_FROM_SERVER:
                        case GLOBAL_WHO:
                            flushToConsole( stringmessage );
                            break;
                        case LOBBY_WHO:
                            flushToConsole( stringmessage );
                            break;
                        case LOBBY_INFO:
                            flushToConsole( stringmessage );
                            break;
                        case UID:
                            uid = (Integer) message.get( 0 );
                            break;
                        default:
                            flushToConsole( "Unknown tag: " + tag );
                    }
                    break;
                // Error message
                case ERROR:

                    switch ( tag ) {
                        case INVALID_GAME_ACTION:
                        case GENERIC_ERROR:

                        default:
                            flushToConsole( "Unknown tag: " + tag );
                    }
                    break;
                // Game state update/message/command (Anything related to game)
                case GAME:
                    switch ( tag ) {
                        case NEXT_TURN_INFO:
                            if ( stringmessage.isEmpty() ) {
                                flushToConsole( "Its nobodys turn!" );
                            } else if ( username.equals( stringmessage ) ) {
                                flushToConsole( ("It is now my turn!") );
                                phasing = true;
                            } else {
                                flushToConsole( "It is now " + stringmessage + "'s turn!" );
                            }
                            break;
                        case YIELD_TURN:
                            if ( (boolean) message.get( 0 ) ) {
                                phasing = false;
                            } else {
                                flushToConsole( stringmessage );
                            }
                            break;
                        case INIT_GAME_PLEASE: //setup scenario
                            Platform.runLater( new Runnable() {
                                public void run() {
                                Game.getInstance().initScenarioCallback();
                            }});                            
                            break;
                        case BEGIN_GAME:
                            if ( stringmessage.isEmpty() ) {
                                //startGame();
                                // TODO: begin the game, not just send another message to loop through
                            } else {
                                flushToConsole( stringmessage );
                            }
                            break;
                        default:
                            Conductor.processMessage( tag, sender, message );
                        //flushToConsole( "Unknown tag: " + tag );
                    }
                    break;
                // Lobby management tags
                case LOBBY:
                    switch ( tag ) {
                        case NEW_LOBBY:
                            if ( (Boolean) message.get( 0 ) ) { // approved!
                                flushToConsole( "Lobby " + stringmessage + " has been created!" );
                                currentLobby = stringmessage;
                            } else {
                                // denied, server provides reason
                                flushToConsole( "Could not create lobby: " + stringmessage
                                        + "!\n" );
                            }
                            break;

                        case JOIN_LOBBY:
                            if ( (boolean) message.get( 0 ) ) {
                                currentLobby = stringmessage;
                                flushToConsole( "Successfully joined lobby " + currentLobby + "!" );
                            } else {
                                flushToConsole( stringmessage );
                            }
                            break;
                        case LEAVE_LOBBY: // Server booted the client
                            flushToConsole( "You've left lobby " + currentLobby + "!" );
                            currentLobby = "";
                            break;

                        default:
                            flushToConsole( "Unknown tag: " + tag );
                    }
                    break;
                // Connection messages/commands ex: DISCONNECT
                case CONNECTION:

                    switch ( tag ) {
                        case DISCONNECT:
                            if ( (boolean) message.get( 0 ) ) {
                                killThread();
                                return true;
                            } else {
                                flushToConsole( "Server refused request to disconnect!\nYou are still connected, try again in a little bit, server might be overloaded." );
                                return true;
                            }
                        default:
                            flushToConsole( "Unknown tag: " + tag );
                    }
                    break;
                // Anything related to file transfer, either for network or game state
                case FILE:

                    switch ( tag ) {
                        case GET_FILE:
                            // TODO: print file
                            break;
                        case SEND_FILE:
                            if ( (boolean) message.get( 0 ) ) {
                                flushToConsole( "File sent successfully!" );
                            } else {
                                flushToConsole( "Error: server did not recieve file!" );
                            }
                            break;
                        default:
                            flushToConsole( "Unknown tag: " + tag );
                    }
                    break;
                // Anything that doesn't fall into above categories ex: GENERIC
                case OTHER:
                    switch ( tag ) {
                        case GENERIC:
                        default:
                            flushToConsole( "Unknown tag: " + tag );
                    }
                    break;

                case NOTIFICATION: // Doing nothing than print for now
                    switch ( tag ) {
                        case GLOBAL: // Incoming Broadcast to all connected clients
                        case LOBBY: // lobby-wide broadcast
                        case PRIVATE: // direct to user, like if doing something not good
                            flushToConsole( stringmessage );
                            break;
                        default:
                            flushToConsole( "Unknown tag: " + tag );
                }
                break;
                default:
                    consoleOut.println( "Unknown flag: " + flag + "\nTag: " + tag );
                    break;
            } // end outer switch

            return true;
        }

        @Override
        public void run() {

            // Start the input stream. VERY IMPORTANT!
            if ( !createStream() ) {
                flushToConsole( "Unable to start listener thread: stream creation failure!" );
                killThread();
            }

            // incoming message from server. First string is message tag.
            NetworkPacket incomingMessage = new NetworkPacket(); // damn nulls are everywhere!

            // Main loop
            while ( !killed ) {
                if ( streamIn != null && isConnected() ) {
                    incomingMessage = recieveMessage(); // get the message         
                } else if ( !isConnected() ) {
                    flushToConsole( "Lost connection to server!" );
                    killThread();
                } else {
                    //continue; // Relax at the MoxieJava until message arrives
                    System.err.println( "streamIn is null!" );
                    killThread();
                }
                if ( incomingMessage == null ) {
                    System.err.println( "Incoming message is null: its all his fault!)" );
                    killThread();
                } else if ( incomingMessage.isEmpty() ) {
                    System.err.println( "Empty message from server!" );
                    killThread();
                } else if ( processMessage( incomingMessage ) ) {
                    // TODO: something important ( like a suprise party! )
                } else {
                    System.err.println( "processMessage failed!" );
                } // end else      
            } // end while
            close();
        } // end run

        /**
         * Always run this before returning from {@link #run run}!
         */
        private void close() {
            try {
                if ( streamIn != null ) {
                    streamIn.close();
                    streamIn = null;
                }
            } catch ( IOException ex ) {
                ex.printStackTrace();
            }
        }

    }

    // ***STREAMS*** //
    /**
     * Starts {@link ListenerThread listenerThread} and {@link ListenerThread listenerThread}
     * <p>
     * @author Christopher Goes
     */
    private static void startRemoteConnection() {
        // Start outgoing stream processor
        writerThread = new ClientSendingThread();
        writerThread.start();

        // Start incoming stream processor
        listenerThread = new ClientReceivingThread();
        listenerThread.start();

        // startup messages

        if ( isConnected() ) {
            flushToConsole( "We're connected! Exchanging information with server..." );
        } else {
            System.err.println( "Connection started, but we're not connected!" );
        }
        // TODO: hello message?
        // Send username
        send( Flag.INFO, Tag.SEND_HANDLE );
        // Request list of clients:
        send( Flag.INFO, Tag.GLOBAL_WHO );
        // Request list of lobbies:
        send( Flag.INFO, Tag.LOBBY_INFO );
    }

    /**
     * Kills ListenerThread and WriterThread
     * <p>
     * NOTE: Call this BEFORE restarting threads with new socket! WARNING: This
     * will close any associated sockets!
     * <p>
     * @author Christopher Goes
     */
    private static void killRemoteConnection() {
        if ( listenerThread != null && listenerThread.isAlive() ) {
            listenerThread.killThread();
        }
        if ( writerThread != null && writerThread.isAlive() ) {
            writerThread.killThread();
        }
        listenerThread = null;
        writerThread = null;
        closeSocket(); // shouldn't need this
    }

    /**
     * Initializes console Input/Output streams
     * <p>
     * @author Christopher Goes
     */
    private static void startLocalStreams() {
        consoleIn = new BufferedReader( new InputStreamReader( System.in ) );
        consoleOut = new PrintWriter( System.out, true );
    }

    /**
     * Closes local Input/Output streams
     * <p>
     * @author Christopher Goes
     */
    private static void killLocalStreams() {
        try {
            if ( consoleIn != null ) {
                consoleIn.close();
            }
            if ( consoleOut != null ) {
                consoleOut.close();
            }

        } catch ( IOException ex ) {
            ex.printStackTrace();
        } finally {
            consoleIn = null;
            consoleOut = null;
        }
    }

    /**
     * Starts clientThread
     */
    private static void startCommandProcessor() {
        clientThread = new ClientCommandThread();
        clientThread.start();
    }

    private static void killCommandProcessor() {
        if ( clientThread != null && clientThread.isAlive() ) {
            clientThread.killThread();
        }
        clientThread = null;
    }

    // ***COMMAND PROCESSING/EXECUTION METHODS*** //
    /**
     * Post the string "lastMessage" to the GUI chat box. The message has be
     * sent in this way so that the listener thread can communicate with
     * this JavaFX thread. Otherwise an exception will be thrown.
     * <p>
     * @author Gabe Pearhill
     */
    private static void postMessage( final String lastMessage ) {
        Platform.runLater( new Runnable() { // TODO: Get this working
            @Override
            public void run() {
                Game.getInstance().hudController.postMessage( lastMessage );
            }

        } );
    }

    /**
     * Prints message to command line & HUD consoles
     * Assume newline will be appended
     * <p>
     * @param lastMessage Message to print
     * <p>
     * @author Christopher Goes
     */
    private static void flushToConsole( final String lastMessage ) {
        consoleOut.println( lastMessage );
        consoleOut.flush();
        postMessage( lastMessage );
    }

    /**
     * Writes to socket outgoing connection, hides the protocol details
     * <p>
     * @param message Message to send
     */
    private static void writeToQueue( final NetworkPacket message ) {
        try {
            messageQueue.put( message );
        } catch ( InterruptedException ex ) {
            ex.printStackTrace();
        }
    }

    /**
     * Prints list of commands and what they do from a text file
     * <p>
     * @author Christopher Goes
     * @throws IOException
     */
    private static void printCommandList() {
        String inputline;
        String tempfile;

        // TODO: change src and server to variables when project is nearing completion
        tempfile = dir + File.separator + "src" + File.separator + "server" + File.separator + helpfile;
        try {
            BufferedReader input = new BufferedReader( new FileReader( tempfile ) );
                while ( (inputline = input.readLine()) != null ) {
                    flushToConsole( inputline );
                }
        } catch ( IOException e ) {
            System.err.println( "File not found: " + tempfile + "\nException: " + e );
        }

    }

    // ***CONNECTION***  //
    /**
     * Creates a new connection to a server
     * <p>
     * @return boolean True if successfully connected, False if failed to connect
     * <p>
     * @author Christopher Goes
     */
    private static boolean connect() {

        flushToConsole( "Connecting! Please Wait..." );
        socket = connectToServer( serverName, port );
        if ( socket != null ) {
            flushToConsole( "Connected successfully to " + socket.getInetAddress() + " through port " + socket.
                    getPort() + "!" );
            return true;
        } else {
            flushToConsole( "Failed to connect!" );
            return false;
        }
    }

    /**
     * Attempts connection to the server, and returns socket if successful
     * <p>
     * @author Christopher Goes
     * @param sName
     * @param serverPort
     *                   <p>
     * @return Socket
     * <p>
     * @throws IOException
     */
    private static Socket connectToServer( String sName, int serverPort ) {
        try {
            return (new Socket( sName, serverPort ));
        } catch ( UnknownHostException e ) {
            System.err.println( "Error : Unknown host!" );
        } catch ( ConnectException e ) {
            System.err.println( "Error : Connection Refused!" );
        } catch ( IOException e ) {
            System.err.println( "Error: IOException in connectToServer!" );
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks if client is connected to server
     * <p>
     * @author Christopher Goes
     * @return boolean True if connected, False if not
     */
    private static boolean isConnected() {
        return socket != null && !(socket.isClosed()) && socket.isConnected();
    }

    // ***UTILITY METHODS*** //
    /**
     * Set flags, variables, etc, from a file
     * <p>
     * @param filename Name of Network Settings file
     * <p>
     * @return True if successful, False if not
     */
    private static boolean configureSettings( final String filename ) {
        return true; // TODO: Stub method
    }

    /**
     * Closes and nulls socket
     * <p>
     * @author Christopher Goes
     */
    private static void closeSocket() {
        try {
            if ( socket != null && !(socket.isClosed()) ) {
                socket.close();
            }
        } catch ( IOException ex ) {
            ex.printStackTrace();
        } finally {
            socket = null;
        }
    }

    /**
     * Closes all open streams, and kills all active threads
     */
    private static void stopClient() {
        killRemoteConnection();
        killCommandProcessor();
        killLocalStreams();
    }

    /**
     * Send a file to the server
     * <p>
     * @param filename Name of file to be sent
     */
    private static void sendFile( final String filename ) {
        // TODO: this is text file, what about binary files? JSONs?
        List<String> temp; // What is our file size limit?
        try {
            temp = Files.readAllLines( Paths.get( networkDirectory + filename ), Charset.
                    forName( "UTF-8" ) );
            send( Flag.FILE, Tag.SEND_FILE, temp );
        } catch ( IOException e ) {
            System.err.println( "Could not open file! Error thrown: " + e );
        }
    }
    // ***GETTERS/SETTERS*** //
    /**
     * Sets IPv4 address of remote Server
     * <p>
     * @param sName
     */
    public static void setServerName( String sName ) {
        if ( sName == null || sName.isEmpty() ) {
            System.err.println( "Invalid server name!" );
        } else {
            serverName = sName;
        }
    }

    /**
     * Sets port of remote Server
     * <p>
     * @param sPort
     */
    public static void setServerPort( int sPort ) {
        if ( sPort < 1024 ) {
            System.err.println( "Invalid port!" );
        } else {
            port = sPort;
        }
    }

    /**
     * Sets user Handle/Username
     * <p>
     * @param uName
     */
    public static void setUsername( String uName ) {
        if ( uName == null || uName.isEmpty() ) {
            System.err.println( "Invalid server name!" );
        } else {
            username = uName;
        }
    }

    /**
     * Gets IPv4 address of remote Server
     * <p>
     * @return
     */
    public static String getServerName() {
        return serverName;
    }

    /**
     * Gets port of remote Server
     * <p>
     * @return
     */
    public static int getServerPort() {
        return port;
    }

    /**
     * Gets user Handle/Username
     * <p>
     * @return
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Checks if the client has been initialized yet
     *
     * @return
     */
    public static boolean clientIsInitialized() {
        return clientInitialized;
    }

    // ***MAIN*** //
    /**
     * For testing purposes
     * <p>
     * @param args
     */
    public static void main( String[] args ) {
        if ( initializeClient() ) {

        } else {
            System.err.println( "Client failed to start from main!" );
        }
    }

} // end class
