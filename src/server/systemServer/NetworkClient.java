/**
 * The Network Client
 * <p>
 * Handles client-side communication with the server
 */
package systemServer;

import Units.MoveableUnit;
import Units.UnitPool;
import com.sun.corba.se.pept.transport.ListenerThread;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import javafx.application.Platform;
import mainswordsorcery.Game;

/**
 * The Infamous Network Client, handles communication to/from Network Server
 * <p>
 * NOTE: This is a static singleton. 
 * Don't fool around trying to create instances, you hear now?
 */
public class NetworkClient {

    // iNet variables
    private static Socket socket = null;
    private static int port = 25565;
    private static String serverName = "127.0.0.1";

    // Read/write streams
    private static BufferedReader consoleIn = null;
    private static PrintWriter consoleOut = null;

    // Client info
    private static String username = "default_user";

    // Streams & Threads
    private static ClientListenerThread listenerThread = null;
    private static ClientWriterThread writerThread = null;
    private static ClientCommandThread clientThread = null;

    // Set default help file
    private static String helpfile = "commands.txt"; // TODO: update from settings file
    private static String dir = System.getProperty("user.dir");
    
    // Buffers
    private static ArrayBlockingQueue<List<Object>> messageQueue;
    private static ArrayBlockingQueue<String> commandQueue;
    
    // Flags

    // Conductor
    //private Conductor jarvis; // Our conductor object

    /* PUBLIC METHODS */
    
    /**
     * Default Startup of NetworkClient
     * Starts local streams, connects to server, and makes the connection live
     * <p>
     * NOTE: will use default settings file of "netclient_settings.txt"
     * 
     * @return True if started OK, False if connection failed
     */
    public static boolean initializeClient() { // default
        return initializeClient("netclient_settings.txt");      
    }
    
    /**
     * Startup of NetworkClient
     * Starts local streams, connects to server, and makes the connection live
     * 
     * @param filename 
     *  Name of Network Settings file, no path necessary
     *  Method will search for and find the file, as long in sworsorc directory
     * 
     * @return True if started OK, False if connection failed
     */
    public static boolean initializeClient( String filename ) {
        configureSettings(filename);
        messageQueue = new ArrayBlockingQueue<>(30, true ); // 30 slots, FIFO access
        startLocalStreams();
        if (connect()) {
            startRemoteConnection();
            startCommandProcessor();
            return true;
        } else {
            stopClient();
            return false;
        }
    }

    /**
     * Send a message to client
     * YOU MUST PUT A TAG AS THE FIRST ITEM!
     * <p>
     * This is just a public wrapper for {@link #write write()}
     * @param message Message to send
     */
    public static void send(List<String> message) {
        write(MessagePhoenix.stringToObject(message));
    }
    
    /**
     * Send a message to client
     * YOU MUST PUT A TAG AS THE FIRST ITEM!
     * <p>
     * Generic parameters
     * @param message First parameter is assumed to be tag
     */
    public static void send(Object... message) {
        write(MessagePhoenix.createMessage(message));
    }
    
    /**
     * Test a user command
     * @param command 
     * @author Christopher Goes
     */
    public static void testCommand( String command ) {
        executeCommand(command);
    }    
    
    /**
     * Sends a "chat" message to the other users. The received message will be
     * displayed (along with the sender's username) in the chat box of the other
     * connected players.
     * <p>
     * @param message The message to display to other users
     */
    public static void sendGlobalChatMessage(String message) {
        send(MessagePhoenix.GLOBAL_CHAT, message);
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
        send(MessagePhoenix.YIELD_TURN);
    }

    // ***THREADS*** //
    private static boolean remoteConnectionIsAlive() {
        return ((listenerThread != null && listenerThread.isAlive())
                || (clientThread != null && clientThread.isAlive()));
    }
    
    private static class ClientCommandThread extends Thread {

        private boolean killed = false;
        
        private ClientCommandThread() {                    
            // empty constructor
        }

        /**
         * Parses user input, executes commands, and sends messages to server
         * <p>
         * @author Christopher Goes
         * @param command
         * @return boolean True if executed normally, False if quit or exception
        */
        private boolean processCommand(String command) {

        if (command == null) {
            System.err.println("Null string passed to processInput!");
            return false;
        }

        String[] parsedString;
        parsedString = command.split("\\s+"); //Split line by whitespace

        if (parsedString.length == 2) {
            if ("/file".equals(parsedString[0])) {
                sendFile(parsedString[1]);

            } else if ("/newLobby".equals(parsedString[0])) {
                String lobbyName = parsedString[1];
                send(MessagePhoenix.CREATE_NEW_LOBBY_REQUEST, lobbyName );

            } else if ("/joinLobby".equals(parsedString[0])) {
                String lobbyName = parsedString[1];
                send(MessagePhoenix.JOIN_LOBBY_REQUEST,lobbyName);
            } else if (isConnected()) {
                // sends chat message to server, which broadcasts to all clients
                send( MessagePhoenix.GLOBAL_CHAT, username, command);
            }
        } else if (parsedString.length == 1) {
            if ("/printFile".equals(parsedString[0])) {
                send(MessagePhoenix.PRINT_FILE);

            } else if ("/globalWho".equals(parsedString[0])) {
                send( MessagePhoenix.GLOBAL_WHO_LIST);

            } else if ("/leaveLobby".equals(parsedString[0])) {
                send( MessagePhoenix.LEAVE_LOBBY_REQUEST);

            } else if ("/showLobbies".equals(parsedString[0])) { // TODO: message if no lobbies available, command to create lobby
                send( MessagePhoenix.LOBBY_INFO); // TODO: working lobby info request

            } else if ("/disconnect".equals(parsedString[0])) { // manual client disconnect
                if (isConnected()) {
                    disconnectFromServer();
                } else {
                    flushToConsole("Can't disconnect when you're not connected!");
                }

            } else if ("/yieldTurn".equals(parsedString[0])) { // client turn over
                endTurn();
            } else if ("/beginGame".equals(parsedString[0])) { // request to start game
                send( MessagePhoenix.REQUEST_BEGIN_GAME);

            } else if ("/help".equals(parsedString[0])) {
                printCommandList();

            } else if ("/reconnect".equals(parsedString[0])) {
                if (isConnected()) { // Anti-clone League of Uganda certified
                    flushToConsole("Already connected!");
                    return true;
                } else if( remoteConnectionIsAlive()) {
                    flushToConsole("Remote connection still alive!");
                    return true;
                }
                flushToConsole("Attempting to reconnect...");
                if (connect()) {
                    flushToConsole("Successfully reconnected!");
                    startRemoteConnection();

                } else {
                    flushToConsole("Reconnect failed");
                }

            } else if ("/quit".equals(parsedString[0])) {
                flushToConsole("Exiting client...");
                if (isConnected()) {
                    flushToConsole("Still connected to server, disconnecting");
                    disconnectFromServer();
                }
                return false;
            }  
            else if (parsedString[0].length() != 0 && "/".equals(parsedString[0].charAt(0))){
                flushToConsole("Invalid command, try again, or type /help for a list of commands.");
            }
            else {
                sendGlobalChatMessage(command);
            }
        } else {
            if (isConnected()) {
                    sendGlobalChatMessage(command);
            } else {
                return false;
            }
        }
        return true;
    }

        /**
         * Send a file to the server
         * <p>
         * @param filename Name of file to be sent
         */
        private void sendFile(String filename) {
            String line;
            try {
                BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(filename), Charset.forName("UTF-8")));
                send(MessagePhoenix.FILE, filename); // TODO: rewrite with single message sent
                while ((line = file.readLine()) != null) {
                    send(MessagePhoenix.FILE_LINE, filename, line);
                }
            } catch (IOException e) {
                System.err.println("Could not open file! Error thrown: " + e);
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
         * Main execution thread for the Network Client
         * <p>
         * Reads from consoleIn(command line) and GUI chat window
         * 
         * @author Christopher Goes
         */
        @Override
        public void run() {
            String line;
            flushToConsole(username + ": "); // TODO: append username/tags! (see flushToConsole)

            while (!killed) {
                try {
                    line = consoleIn.readLine();
                    if (line == null) {
                        System.err.println("line == null! Eeek!");
                        stopClient();
                        killThread();
                    } else if (!(processCommand(line))) {
                        flushToConsole("Later gator!");
                        stopClient(); // Shutdown everything
                        killThread(); // Kill command processing
                    } else {
                        flushToConsole(username + ": "); // TODO: append username/tags! (see flushToConsole)

                    }
                } catch (IOException ex) {
                    System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                            + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                    ex.printStackTrace();
                    killThread();
                }
            }

            close();
        }
        
        /**
         * Always run this before returning from {@link #run run}!
         */
        private void close() {
            // empty for now
        }
    }
    
    private static class ClientWriterThread extends Thread { // TODO: how kill

        private ObjectOutputStream writer;
        
        private boolean killed = false;
                
        private ClientWriterThread() {
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
            if (isConnected()) {
                try {
                    writer = new ObjectOutputStream(socket.getOutputStream());
                    writer.flush();
                } catch (IOException ex) {
                    System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                            + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                    ex.printStackTrace();
                }
            } else {
                System.err.println("Error in setWriter: not connected!");
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

        private void sendMessage(String tag, Object... message) {
            try {
                MessagePhoenix.sendMessage(writer, tag, message);
            } catch (IOException | NullPointerException ex) {
                //System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                //        + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                ex.printStackTrace();
        
            }
        }
        @Override
        public void run() {
            setWriter();          
            List<Object> message = null;
            
            while (!killed) {
                try {
                    message = messageQueue.take();
                } catch (InterruptedException ex) {
                    System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                            + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                    ex.printStackTrace();
                }
                if (message != null ) { // assume first object is tag
                    sendMessage( message.get(0).toString(), new ArrayList(message.subList(1, message.size())));
                } else {
                    System.err.println("Null message!");
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
                if (!(isConnected())) {
                    if (writer != null) {
                        writer.close();
                        writer = null;
                    }
                } else {
                    disconnectFromServer();
                    if (writer != null) {
                        writer.close();
                        writer = null;
                    }

                }
            } catch (IOException ex) {
                System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                        + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Listens for and handles incoming communications for Network Client
     */   
    private static class ClientListenerThread extends Thread {

        private ObjectInputStream streamIn;
        
        private boolean killed = false;

        private ClientListenerThread() {
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
            if (isConnected()) {
                try {
                    this.streamIn = new ObjectInputStream(socket.getInputStream());                   
                } catch (IOException ex) {
                    System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                            + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                    ex.printStackTrace();
                }
            } else {
                System.err.println("Error creating listen stream: socket isn't connected!");
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

        private List<Object> recieveMessage() {
            return MessagePhoenix.recieveMessage(streamIn);
        }
        
        private boolean processMessage( List<Object> incomingMessage ) {
            
            List<String> message = null; // more generic later on, as in POJOs
            String TAG = null;
            
            // Convert to String list
            // If we have need for objects from servers later, any branches would go here
            message = MessagePhoenix.objectToString(incomingMessage);

            // Read and strip the message TAG
            TAG = message.get(0);
            message = message.subList(1, message.size());
            
            // Dead Mans switch
            String tempMessage = message.toString();            

            // assuming message is a string
            // branch past here if its not
            if (TAG.equals(MessagePhoenix.GLOBAL_CHAT)) {
                if (message.get(0).equals(username)) {
                    // suppress message
                    flushToConsole(message.get(0) + ": " + message.get(1));
                } else {
                    // TODO: ADD CHAT "PRIVACY" TAG. EX: (Global), (<lobby>), etc
                    // TODO: ADD CONNECTION STATUS TAG. EX: (CONNECTED), (DISCONNECTED), other states
                    
                    flushToConsole(message.get(0) + ": " + message.get(1));
                }
            } else if (TAG.equals(MessagePhoenix.DISCONNECT_ANNOUNCEMENT)) {
                flushToConsole(("User: " + message.get(0) + " has disconnected."));
            } else if (TAG.equals(MessagePhoenix.CONNECT_ANNOUNCEMENT)) {
                // TODO: Joined what? the server? Really want to know outside of lobby connections?
                flushToConsole(("User: " + message.get(0) + " has joined!"));
            } else if (TAG.equals(MessagePhoenix.GLOBAL_WHO_LIST)) {
                if (message.isEmpty()) {
                    flushToConsole("No users online.");
                } else {
                    tempMessage = (message.size() + " users online: ");
                    for (String l : message) {
                        tempMessage += (" " + l);
                    }
                    flushToConsole(tempMessage);
                }
            } else if (TAG.equals(MessagePhoenix.LOBBY_INFO)) {
                tempMessage = ("Lobby " + message.get(0) + ", Users: ");
                for (String l : message ) {
                    tempMessage += (" " + l);
                }
                flushToConsole(tempMessage);
            } else if (TAG.equals(MessagePhoenix.APROVE_NEW_LOBBY_REQUEST)) {
                flushToConsole("Lobby created!");
            } else if (TAG.equals(MessagePhoenix.GAME_BEGUN)) {
                flushToConsole("Game has begun!");
            } else if (TAG.equals(MessagePhoenix.DENY_NEW_LOBBY_REQUEST)) {
                flushToConsole("Could not create lobby: (Duplicated name?)");
            } else if (TAG.equals(MessagePhoenix.NAG)) {
                System.err.println("NAG: " + message.get(1));
                flushToConsole("NAG: " + message.get(1));
            } else if (TAG.equals(MessagePhoenix.NEXT_TURN_INFO)) {
                if (username.equals(message.get(0))) {
                    flushToConsole(("It is now my turn!"));
                } else {
                    flushToConsole("It is now " + message.get(1) + "'s turn!");
                }
                //Added by John Goettsche (I hope this is where it goes
                // Sort of, if/elses handle Netclient-specific messages
                // While the Conductor is supposed to handle game communications
            } else if (TAG.equals(MessagePhoenix.UPDATE_UNIT)) { // TODO: these will need updating/moveing
                UnitPool pool = UnitPool.getInstance();
                MoveableUnit unit = pool.getUnit(message.get(0));
                String location = message.get(1);
                pool.addMove(unit, location);
            } else if (TAG.equals(MessagePhoenix.ADD_UNIT)) {
                UnitPool pool = UnitPool.getInstance();
                MoveableUnit unit = pool.getUnit(message.get(0));
                unit.setRace(message.get(1));
                unit.setUnitType(message.get(2));
                String location = message.get(3);
            } else if (TAG.equals(MessagePhoenix.JOINED_LOBBY)) {
                flushToConsole("Client " + message.get(2) + " has joined lobby "
                        + message.get(1));
            } else if (TAG.equals(MessagePhoenix.LEFT_LOBBY)) {
                flushToConsole("Client " + message.get(2) + " has left lobby "
                        + message.get(1));
            } else { // Game communication
                // its not a network message, therefore NC doesn't care, and has Jarvis take out the trash
                //jarvis.processMessage( message.subList(1, message.size()), TAG );
                flushToConsole("Unknown tag: " + TAG);
            } // end else
            return true;
        }
        
        @Override
        public void run() {
            createStream();
            // Temporary containers
            List<Object> incomingMessage = null; // incoming message from server. First string is message tag.
            List<String> message = null;
            String TAG = null;
            
            // Main loop
            while (!killed) {
                if (killed) { // Thread killed
                    break;
                } else if (isConnected() && streamIn != null) {
                    incomingMessage = recieveMessage(); // get the message                    
                } else {
                    continue; // Relax at the MoxieJava until message arrives
                }

                if (incomingMessage == null) {
                    System.err.println("Null message from server, or default never changed!");
                    break;
                } else if (incomingMessage.isEmpty()) {
                    System.err.println("Empty message from server!");
                    // TODO: handle empty messages?
                } else if( processMessage(incomingMessage)) {
                    // TODO: something important
                } else {
                    System.err.println("processMessage failed!");
                    // TODO: critical error?
                } // end else      
            } // end while
            close();
        } // end run

        /**
         * Always run this before returning from {@link #run run}!
         */
        private void close() {
            try {
                if (!(isConnected())) {
                    if (streamIn != null) {
                        streamIn.close();
                        streamIn = null;
                    }
                } else {
                    disconnectFromServer();
                    if (streamIn != null) {
                        streamIn.close();
                        streamIn = null;
                    }

                }
            } catch (IOException ex) {
                System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                        + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                ex.printStackTrace();
            }
        }

    }

    // ***STREAMS*** //
    
    /**
     * Starts {@link ListenerThread listenerThread} and sets
     * {@link #setWriter() writer stream}
     * <p>
     * <p>
     * @author Christopher Goes
     */
    private static void startRemoteConnection() {
        // Start outgoing stream processor
        writerThread = new ClientWriterThread();
        writerThread.start();

        // Start incoming stream processor
        listenerThread = new ClientListenerThread();      
        listenerThread.start();
        
        // startup messages
        // TODO: hello message?

        // Send username
        send(MessagePhoenix.SEND_HANDLE, username);
        // Request list of clients:
        send(MessagePhoenix.GLOBAL_WHO_LIST);
        // Request list of lobbies:
        send(MessagePhoenix.LOBBY_INFO);    
    }

    /**
     * Kills ListenerThread and resets writer stream.
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
        if ( writerThread != null && writerThread.isAlive()) {
            writerThread.killThread();
        }
        listenerThread = null;
        writerThread = null;
    }

    /**
     * Initializes console Input/Output streams
     * <p>
     * @author Christopher Goes
     */
    private static void startLocalStreams() {
        
        // Start I/O streams
        consoleIn = new BufferedReader(new InputStreamReader(System.in));
        consoleOut = new PrintWriter(System.out, true);
       

    }

    /**
     * Kills local Input/Output streams
     * 
     * @author Christopher Goes
     */
    private static void killLocalStreams() {
        try {
        if (consoleIn != null ) {
            consoleIn.close();
            consoleIn = null;
        }
        if( consoleOut != null ) {
            consoleOut.close();
            consoleOut = null;
        }
        
        } catch (IOException ex) {
            System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                    + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
            ex.printStackTrace();
        }

    }
    
    private static void startCommandProcessor() {
        // Start command processor
        clientThread = new ClientCommandThread();       
        clientThread.start();
    }
    
    private static void killCommandProcessor() {
        if( clientThread != null && clientThread.isAlive() ) {
            clientThread.killThread();
        }
        clientThread = null;
    }
    
    // ***COMMAND PROCESSING/EXECUTION METHODS*** //
    
    /**
     * Post the string "lastMessage" to the GUI chat box. The message has be
     * sent in this way so that the listener thread can communicate with
     * this JavaFX thread. Otherwise an exception will be thrown.
     * 
     * @author Gabe Pearhill
     */
    private static void postMessage( String lastMessage) {
        Platform.runLater(new Runnable() { // TODO: Get this working
            @Override
            public void run() {
                Game.getInstance().hudController.postMessage(lastMessage);
            }
        });
        //Game.getInstance().hudController.postMessage(lastMessage);

    }
    
    /**
     * Prints message to command line & HUD consoles
     * Assume newline will be appended
     * @param lastMessage Message to print
     * @author Christopher Goes
     */
    private static void flushToConsole( String lastMessage ) {
        consoleOut.println(lastMessage); // TODO: append username/tags!
        consoleOut.flush();
        postMessage(lastMessage);
    }
    
    /**
     * Writes to socket outgoing connection, hides the protocol details
     * @param message Message to send
     */
    private static void write(List<Object> message ) { 
        
        try {
            messageQueue.put(message);
        } catch (InterruptedException ex) {
            System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                    + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
            ex.printStackTrace();           
        }
    }    
    
    /**
     * Executes a Network Client command
     * @param command 
     * @author Christopher Goes
     */
    private static void executeCommand( String command ) {
        try {
            commandQueue.put(command);
        } catch (InterruptedException ex) {
            System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                    + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
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
            BufferedReader input = new BufferedReader(new FileReader(tempfile));

            try {
                while ((inputline = input.readLine()) != null) {
                    flushToConsole(inputline);
                }
            } catch (IOException ex) {
                System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                        + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                ex.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + tempfile + "\nException: " + e);
        }

    }
    

    
    // ***CONNECTION***  //

    /**
     * Creates a new connection to the server, call this before
     * {@link #startClient startClient()}
     * <p>
     * @return boolean True if successful, false if not
     * @author Christopher Goes
     */
    private static boolean connect() { // TODO: connect pass arguments, or no longer public?
        try {
            socket = connectToServer(serverName, port);
            return true;
        } catch (NullPointerException | IOException ex) {
            System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                    + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
            ex.printStackTrace();
            return false;
        }
    } 
    
    /**
     * Attempts connection to the server, and returns socket if successful
     * <p>
     * @author Christopher Goes
     * @param sName
     * @param serverPort
     * @return Socket
     * @throws IOException
     */
    private static Socket connectToServer(String sName, int serverPort) throws IOException {
        Socket tempsock = null;
        consoleOut.print("Connecting! Please Wait...");
        try {
            tempsock = new Socket(sName, serverPort);
        } catch (UnknownHostException e) {
            System.err.println("Error : Unknown host!\nException: " + e);
        } catch (ConnectException e) {
            System.err.println("Error : Connection Refused!\nException: " + e);
        }
        flushToConsole("Connected successfully to " + tempsock.getInetAddress() + " through port " + tempsock.getPort() + "!" );
        return tempsock;
    }
    
    /**
     * Disconnects client from server
     * <p>
     * @author Christopher Goes
     */
    private static void disconnectFromServer() {
        consoleOut.print("Disconnecting from server...");
        
        if (isConnected()) {
            send( MessagePhoenix.DISCONNECT_REQUEST);
        }

        killRemoteConnection();
        killSocket();
        flushToConsole("Disconnected!");
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
     * @param filename Name of Network Settings file
     * @return True if successful, False if not
     */
    private static boolean configureSettings( String filename ) {
        return true; // TODO: Stub method
    }
    
    /**
     * Closes and nulls socket
     * <p>
     * @author Christopher Goes
     */
    private static void killSocket() {
        try {
            if (socket != null && !(socket.isClosed())) {
                socket.close();
                socket = null;
            } else if (socket != null) {
                socket = null;
            }
        } catch (IOException ex) {
            System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                    + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
            ex.printStackTrace();
        }
    }

    /**
     * Closes all open streams, and kills all active threads
     */
    private static void stopClient() {
        killSocket();
        killRemoteConnection();
        killLocalStreams();
    }

    // ***GETTERS/SETTERS*** //
    
    /**
     * Sets IPv4 address of remote Server
     * @param sName 
     */
    public static void setServerName(String sName) {
        if (sName == null || sName.isEmpty()) {
            System.err.println("Invalid server name!");
        } else {
            serverName = sName;
        }
    }

    /**
     * Sets port of remote Server
     * @param sPort 
     */
    public static void setServerPort(int sPort) {
        if (sPort < 1024) {
            System.err.println("Invalid port!");
        } else {
            port = sPort;
        }
    }

    /**
     * Sets user Handle/Username
     * @param uName 
     */
    public static void setUsername(String uName) {
        if (uName == null || uName.isEmpty()) {
            System.err.println("Invalid server name!");
        } else {
            username = uName;
        }
    }

    /**
     * Gets IPv4 address of remote Server
     * @return 
     */
    public static String getServerName() {
        return serverName;
    }

    /**
     * Gets port of remote Server
     * @return 
     */
    public static int getServerPort() {
        return port;
    }

    /**
     * Gets user Handle/Username
     * @return 
     */
    public static String getUsername() {
        return username;
    }
    
    // ***MAIN*** //
    /**
     * Opens dialog box(s), creates NetworkClient instance, and executes startup
     * methods
     * <p>
     * NOTE: Kill when client closes
     * <p>
     * @param args
     */
    public static void main(String[] args) {

        // TODO: we still need these?
        ClientData clientData = new ClientData();
        ClientDataForm clientDataForm = new ClientDataForm(clientData);

        System.out.println("Launching Login Dialog");

        clientDataForm.setVisible(true);

        System.out.println("Login Dialog Finished");

        System.out.flush();

        String sName = clientData.getIPAddress();
        String uName = clientData.getUsername();

        // 25565 is sworsorc default server port
        NetworkClient.setServerName(sName);
        NetworkClient.setServerPort(25565);
        NetworkClient.setUsername(uName);

        if ( NetworkClient.initializeClient() ) {
            
        } else {
            System.err.println("Client failed to start from main!");
        }
    } // end main

} // end class
