/**
 * The Network Client
 * <p>
 * Handles client-side communication with the server
 */
package systemServer;

import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

/**
 * The primary class for the Network Client, handles data and communication
 * thread(s)
 * <p>
 * Don't create more than one of these on a single client unless you now what
 * you're doing
 */
public class NetworkClient {

    // iNet variables
    private Socket socket = null;
    private static int port = 25565; 
    private static String serverName = "127.0.0.1";

    // Read/write streams
    private BufferedReader consoleIn = null;
    private PrintWriter consoleOut = null;

    // Client info
    private static String username = "default_user";

    // Thread(s)
    private ListenerThread listenerThread = null;
    private PrintWriter writer = null;

    // set default help file
    final private String helpfile = "commands.txt";
    final private String dir = System.getProperty("user.dir");

    //private Conductor jarvis; // Our conductor object
    
    /* CONSTRUCTOR */
    
     /**
     * Create a new ChatClient object, the interface for all network operations
     * client-side
     *
     * @param sName Name of the server AKA the IP Address
     * @param serverPort Port number of the server (ex: 25565)
     * @param uName Username of client
     * @throws IOException
     */
    public NetworkClient(String sName, int serverPort, String uName) throws IOException {

        if( sName == null || serverPort < 1024 || uName == null ) {
            System.err.println("Invalid parameters, please pass correct ones!\nsName: " + 
                    sName + "\nserverPort (Must be > 1023): " + serverPort + "\nuName: " + uName );
        } else {
            
            // sets server iNet info
            port = serverPort;
            serverName = sName;

            // Set client username
            username = uName;
        }
        
        // Standard In/Out
        consoleIn = new BufferedReader(new InputStreamReader(System.in));
        consoleOut = new PrintWriter(System.out, true);

    } // end constructor
    
    /* PUBLIC METHODS */
    
    /**
     * Creates a new connection to the server, call this before
     * {@link #start start()}
     *
     * @return boolean True if successful, false if not
     * @author Christopher Goes
     */
    public boolean connect() {
        try {
            socket = connectToServer(serverName, port);
            return true;
        } catch (NullPointerException | IOException e) {
            System.err.println("Error in connect!\nException: " + e);
            return false;
        }
    }
    
    /**
     * Starts activity to server, including threads, and command input loop
     *
     * @author Christopher Goes
     * @throws IOException
     */
    public void start() throws IOException {

        // Start thread(s)
        startThreads();

        //first message is handle:
        MessageUtils.sendMessage(writer, MessageUtils.makeSendHandleMessage(username));

        //request list of clients:
        MessageUtils.sendMessage(writer, MessageUtils.makeGlobalWhoRequestMessage());

        //request list of lobbies:
        MessageUtils.sendMessage(writer, MessageUtils.makeRequestLobbyInfoMessage());

    }
    
    /**
     * Main execution thread for the Network Client
     * <p>
     * This reads from consoleIn, connect user input to that stream
     *
     * @author Christopher Goes
     */
    public void runClient() {
        String line;
        
        while (true) {
            try {
                line = consoleIn.readLine();
                if ( line == null) {
                    System.err.println("line == null! Eeek!");
                    stop();
                    break;
                }
                if (!(processInput(line))) {
                    stop();
                    break;
                }

            } catch (IOException e) {
                System.err.println("Error sending message!\nException: " + e);
                stop();
                break;
            } // end catch   
        } // end while
        
    }  
    
    /* THREAD N' STREAM STUFF */
    
    /**
     * Listens for and handles incoming communications for Network Client
     */
    private class ListenerThread extends Thread {

        private BufferedReader streamIn;
        private boolean killed = false;

        private ListenerThread() {
            // empty constructor
        }

        /**
         * Creates input stream, and connects to socket
         * <p>
         * NOTE: Must be called when creating thread!
         * @author Christopher Goes
         * @throws IOException 
         */
        private void createStream() throws IOException {
            if( isConnected() ) {
                streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } else {
                System.err.println("Error creating ListenerThread stream!");
            }
        }    

        /**
         * Marks thread for death, causing it to close, return, and die
         * 
         * @author Christopher Goes
         */
        private void killThread() {
            killed = true;
        }

        @Override
        public void run() {
            List<String> message; // incoming message from server. First string is message tag.
            
                while (!killed) {

                    if (killed) {
                        close();
                        return;
                    } else if ( isConnected() ) {
                        message = MessageUtils.receiveMessage(streamIn);                   
                    } else {
                        continue;
                    }
                                       
                    if (message == null) {
                        System.err.println("Null message from server!");
                        close();
                        return;
                    } else if ( message.isEmpty() ) {
                        System.err.println("Empty message from server!");
                        continue; // since this isn't a critical error, no reason to kill the thread yet...
                    }
                    
                    //first element of the parsed message array will tell us
                    //what type of message it is:
                    if (message.get(0).equals(MessageUtils.GLOBAL_CHAT)) {
                        //Printing methods can be centralized!
                        MessageUtils.printChat(consoleOut, message);
                    } else if (message.get(0).equals(MessageUtils.DISCONNECT_ANNOUNCEMENT)) {
                        MessageUtils.printDisconnect(consoleOut, message);
                    } else if (message.get(0).equals(MessageUtils.CONNECT_ANNOUNCEMENT)) {
                        MessageUtils.printConnectionMessage(consoleOut, message);
                    } else if (message.get(0).equals(MessageUtils.GLOBAL_WHO_LIST)) {
                        MessageUtils.printGlobalWhoList(consoleOut, message);
                    } else if (message.get(0).equals(MessageUtils.LOBBY_INFO)) {
                        MessageUtils.printLobbyInfo(consoleOut, message);
                    } else if (message.get(0).equals(MessageUtils.APROVE_NEW_LOBBY_REQUEST)) {
                        consoleOut.println("Lobby created!");
                    } else if (message.get(0).equals(MessageUtils.GAME_BEGUN)) {
                        consoleOut.println("Game has begun!");
                    } else if (message.get(0).equals(MessageUtils.DENY_NEW_LOBBY_REQUEST)) {
                        consoleOut.print(("Could not create lobby: (Duplicated name?)"));
                    } else if (message.get(0).equals(MessageUtils.NAG)) {
                        System.err.println("NAG: " + message.get(1));
                    } else if (message.get(0).equals(MessageUtils.NEXT_TURN_INFO)) {
                        if (username.equals(message.get(1))) {
                            //it's my turn!
                            consoleOut.println("It is now my turn!");
                        }
                        consoleOut.println("It is now " + message.get(1) + "'s turn!");
                    } else {
                        // its not a network message, therefore NC doesn't care, and has Jarvis take out the trash
                        //jarvis.processMessage( message.subList(1, message.size()), message.get(0) );
                        System.err.println("Unknown tag: " + message.get(0));
                    } // end else
                } // end while
                close();
        } // end method

        /**
         * Always run this before returning from {@link #run run}!
         */
        private void close() {
            try {
                if( !(isConnected()) ) {
                    if (streamIn != null) {
                        streamIn.close();
                        streamIn = null;
                    }
                }
                else {
                    System.err.println("Error in ListenerThread close, socket still connected!");
                }
            } catch (IOException e) {
                System.err.println("Error closing listener! Error thrown: " + e);
            }
        }

    }  
    
    /**
     * Starts {@link WriterThread writerThread} and
     * {@link ListenerThread listenerThread}
     *
     * @author Christopher Goes
     * @throws Exception
     */
    private void startThreads() throws IOException { // assumes socket set

        setWriter();

        listenerThread = new ListenerThread();
        listenerThread.createStream();
        listenerThread.start();
    }
    
    /**
     * Kills thread(s) and resets writer stream.
     * <p>
     * NOTE: Call this BEFORE restarting threads with new socket!
     * WARNING: This will close any associated sockets!
     * @author Christopher Goes
     */
    private void stopThreads() {   
        listenerThread.killThread();
        listenerThread = null;
        writer = null;
    }
    
    /**
     * Initializes writer with a new stream. 
     * <p>
     * NOTE: Socket must be set before calling this!
     *
     * @author Christopher Goes
     * @throws IOException
     */
    private void setWriter() throws IOException {
        if( isConnected() ) {
            writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
        }
    }
    
    /* COMMAND PROCESSING/EXECUTION METHODS */

    /**
     * Parses user input, executes commands, and sends messages to server
     *
     * @author Christopher Goes
     * @param command
     * @return boolean True if execute normally, False if quit or exception
     * @throws IOException
     */
    private boolean processInput(String command) throws IOException {

        if( command == null ) {
            System.err.println("Null string passed to processInput!");
            return false;
        }
        
        String[] parsedString;
        parsedString = command.split("\\s+"); //Split line by whitespace

        if (parsedString.length == 2) {
            if ("/file".equals(parsedString[0])) {
                sendFile(parsedString[1]);

            } else if ("/newLobby".equals(parsedString[0])) { // TODO: remove user from current lobby if they create a new lobby
                String lobbyName = parsedString[1];
                MessageUtils.sendMessage(writer, MessageUtils.makeNewLobbyRequestMessage(lobbyName));

            } else if ("/joinLobby".equals(parsedString[0])) { // TODO: stop a user for "cloning" themselves by rejoining the same lobby
                String lobbyName = parsedString[1];
                MessageUtils.sendMessage(writer, MessageUtils.makeJoinLobbyRequestMessage(lobbyName));
            } else if ( isConnected()) {
                // sends chat message to server, which broadcasts to all clients
                // TODO: have client ignore a message it sent, so user doesn't see what they typed twice
                MessageUtils.sendMessage(writer, MessageUtils.makeGlobalChatMessage(username, command));
            }

        } else if (parsedString.length == 1) {
            if ("/printFile".equals(parsedString[0])) {
                write(MessageUtils.PRINT_FILE); //TODO: No "Done" string?

            } else if ("/globalWho".equals(parsedString[0])) {
                MessageUtils.sendMessage(writer, MessageUtils.makeGlobalWhoRequestMessage());

            } else if ("/leaveLobby".equals(parsedString[0])) {
                MessageUtils.sendMessage(writer, MessageUtils.makeLeaveLobbyMessage());

            } else if ("/showLobbies".equals(parsedString[0])) { // TODO: message if no lobbies available, command to create lobby
                MessageUtils.sendMessage(writer, MessageUtils.makeRequestLobbyInfoMessage()); // TODO: working lobby info request

            } else if ("/disconnect".equals(parsedString[0])) { // manual client disconnect
                if( isConnected() ) {
                    disconnectFromServer();
                } else {
                    consoleOut.println("Can't disconnect when you're not connected!");
                }             
                
            } else if ("/yieldTurn".equals(parsedString[0])) { // client turn over
                MessageUtils.sendMessage(writer, MessageUtils.makeYieldTurnMessage());

            } else if ("/beginGame".equals(parsedString[0])) { // request to start game
                MessageUtils.sendMessage(writer, MessageUtils.makeBeginGameRequestMessage());

            } else if ("/help".equals(parsedString[0])) {
                printCommandList();

            } else if ("/reconnect".equals(parsedString[0])) {
                // MAKE SURE USER CANT CLONE THEMSELVES!
                if ( isConnected() ) {
                    consoleOut.println("Already connected!");
                    return true;
                }
                consoleOut.println("Attempting to reconnect...");
                if (connect()) {
                    start();
                    
                } else {
                    consoleOut.println("Reconnect failed");
                }

            } else if ("/quit".equals(parsedString[0])) {
                consoleOut.println("Exiting...");
                if ( isConnected() ) {
                    disconnectFromServer();
                }
                stop();
                return false;
            } else if ( isConnected()) {
                // sends chat message to server, which broadcasts to all clients
                // TODO: have client ignore a message it sent, so user doesn't see what they typed twice
                MessageUtils.sendMessage(writer, MessageUtils.makeGlobalChatMessage(username, command));

            } else {
                consoleOut.println("Invalid command, try again, or type /help for a list of commands.");
            }
        } else {
            if ( isConnected()) {
                // sends chat message to server, which broadcasts to all clients
                // TODO: have client ignore a message it sent, so user doesn't see what they typed twice
                MessageUtils.sendMessage(writer, MessageUtils.makeGlobalChatMessage(username, command));
            }
        }
        return true;
    }

    /**
     * Send a file to the server
     *
     * @param fileName Name of file to be sent
     */
    private void sendFile(String fileName) {
        String line;
        try {
            BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), Charset.forName("UTF-8")));
            MessageUtils.sendMessage(writer, MessageUtils.makeIncomingFileMessage(fileName));
            while ((line = file.readLine()) != null) {
                MessageUtils.sendMessage(writer, MessageUtils.makeFileLineMessage(fileName, line));
            }
        } catch (IOException e) {
            System.err.println("Could not open file! Error thrown: " + e);
        }
    }

    /**
     * If we want to programmatically write something
     * 
     * @param message Message to write
     */
    private void write(String message) { //
        writer.println(message);
        writer.flush();
    }

    /**
     * Prints list of commands and what they do from a text file
     * 
     * @author Christopher Goes
     * @throws IOException
     */
    private void printCommandList() throws IOException {
        String inputline;
        String tempfile;

        // change src and server to variables when project is nearing completion
        tempfile = dir + File.separator + "src" + File.separator + "server" + File.separator + helpfile;

        try {
            BufferedReader input = new BufferedReader(new FileReader(tempfile));

            while ((inputline = input.readLine()) != null) {
                System.out.println(inputline);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + tempfile + "\nException: " + e);
        }

    }
    
    /**
     * Attempts connection to the server, and returns socket if successful
     *
     * @author Christopher Goes
     * @param sName
     * @param serverPort
     * @return Socket
     * @throws IOException
     */
    private static Socket connectToServer(String sName, int serverPort) throws IOException {
        Socket tempsock = null;
        System.out.println("Connecting! Please Wait!");
        try {
            tempsock = new Socket(sName, serverPort);
        } catch (UnknownHostException e) {
            System.err.println("Error : Unknown host!\nException: " + e);
        } catch (ConnectException e) {
            System.err.println("Error : Connection Refused!\nException: " + e);
        }

        System.out.println("Connected successfully to " + tempsock.getInetAddress() + " through port " + tempsock.getPort());
        return tempsock;
    }
    
    /* UTILITY METHODS */

    /**
     * Checks if client is connected to server
     * 
     * @author Christopher Goes
     * @return boolean True if connected, False if not
     */
    private boolean isConnected() {
        return socket != null && !(socket.isClosed()) && socket.isConnected();
    }
    
    /**
     * Disconnects client from server
     * 
     * @author Christopher Goes
     */
    private void disconnectFromServer() {
        consoleOut.println("Disconnecting from server.");
        MessageUtils.sendMessage(writer, MessageUtils.makeDisconnectRequestMessage());
        writer.flush();
        stopThreads();
        killSocket();
    }
    
    /**
     * Closes and nulls socket
     * 
     * @author Christopher Goes
     */
    private void killSocket() {
        try { 
            if( socket != null && !(socket.isClosed())) {
                socket.close();
                socket = null;
            } else if( socket != null ) {
                socket = null;
            }
        } catch (IOException e ){
            System.err.println("Error in killSocket!\nException: " + e );
        }
    }
    
    /**
     * Closes all open streams, and kills all active threads
     */
    private void stop() {
        try {
            if (consoleIn != null) {
                consoleIn.close();
            }
            
            killSocket();
            
            if (listenerThread.isAlive() || writer != null) {
                stopThreads();
            }
        } catch (IOException e) {
            System.err.println("Error closing connection!\nException: " + e);
        }
    }

    /* MAIN */
    
    /**
     * Opens dialog box(s), creates NetworkClient instance, and executes public methods
     * <p>
     * NOTE: This is mainly for testing purposes, should not be in final build
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        ClientData clientData = new ClientData();
        ClientDataForm clientDataForm = new ClientDataForm(clientData);

        System.out.println("Launching Login Dialog");

        clientDataForm.setVisible(true);

        System.out.println("Login Dialog Finished");

        System.out.flush(); // I was losing input is the JDialog crashed

        String sName = clientData.getIPAddress();
        String uName = clientData.getUsername(); // this needs to be set in constructor

        NetworkClient client = new NetworkClient(sName, 25565, uName);
    
        if (client.connect()) {
            client.start();
            client.runClient();
        } else {
             System.err.println("Client failed to connect! Oh noes!");
        }
    } // end main

} // end class
