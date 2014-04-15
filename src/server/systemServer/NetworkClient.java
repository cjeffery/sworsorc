/**
 * NetworkClient manages a client computer's connection with the server,
 * and provides an interface for sending messages over the network.
 * <p>
 * 
 * Can we get a "what you need to know" overview here?
 * 
 */
package systemServer;

import Units.MoveableUnit;
import Units.UnitPool;
import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import Units.Race;

/**
 * The singleton class for the Network Client, handles data and communication
 * thread(s)
 * <p>
 * NOTE: This is a singleton, DO NOT MAKE MORE THAN ONE NetworkClient!
 * TODO: We can enforce this if we want with a static self-reference pattern?
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

    // Streams & Threads
    private ListenerThread listenerThread = null;
    private PrintWriter writer = null;

    // Set default help file
    final private String helpfile = "commands.txt";
    final private String dir = System.getProperty("user.dir");

    //private Conductor jarvis; // Our conductor object
    // TODO: MAKE THIS WHOLE ENCHALADA STATIC! Maybe...
    /* CONSTRUCTOR */
    
    /**
     * Create a new ChatClient object, the interface for all network operations
     * client-side
     * <p>
     * @param sName      Name of the server AKA the IP Address
     * @param serverPort Port number of the server (ex: 25565)
     * @param uName      Username of client
     */
    public NetworkClient(String sName, int serverPort, String uName) {

        if (sName == null || serverPort < 1024 || uName == null) {
            System.err.println("Invalid parameters, please pass correct ones!\nsName: "
                    + sName + "\nserverPort (Must be > 1023): " + serverPort + "\nuName: " + uName);
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
     * {@link #startClient startClient()}
     * <p>
     * @return boolean True if successful, false if not
     * @author Christopher Goes
     */
    public boolean connect() {
        try {
            socket = connectToServer(serverName, port);
            return true;
        } catch (NullPointerException | IOException ex) {
            //System.err.println("Error in connect!\nException: " + e);
            System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                    + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Starts activity to server, including threads, and command input loop
     * <p>
     * @author Christopher Goes
     * @return boolean True if successful, false if runtime error/exception
     */
    public boolean startClient() {

        startThreads();

        //first message is handle:
        MessageUtils.sendMessage(writer, MessageUtils.makeSendHandleMessage(username));

        //request list of clients:
        MessageUtils.sendMessage(writer, MessageUtils.makeGlobalWhoRequestMessage());

        //request list of lobbies:
        MessageUtils.sendMessage(writer, MessageUtils.makeRequestLobbyInfoMessage());

        return true;
    }

    /**
     * Main execution thread for the Network Client
     * <p>
     * This reads from consoleIn, connect user input to that stream
     * <p>
     * @author Christopher Goes
     */
     public void runClient() { //Does this need to be public?
        String line;

        while (true) {
            try {
                line = consoleIn.readLine();
                if (line == null) {
                    System.err.println("line == null! Eeek!");
                    stopClient();
                    break;
                }
                if (!(processInput(line))) {
                    stopClient();
                    consoleOut.println("Later gator!");
                    return;
                } else {
                    //consoleOut.print(username + ": "); This was confusing me when I was working with echoed message. 
                                                        //Feel free to switch this back on.
                    consoleOut.flush();
                }

            } catch (IOException ex) {
                //System.err.println("Error sending message!\nException: " + e);
                System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                        + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                ex.printStackTrace();
                stopClient();
                return;
            } // end catch   
        } // end while

    }
    
    /**
     * Sends a "chat" message to the other users. The received message will
     * be displayed (along with the sender's username) in the chat box of the 
     * other connected players.
     * 
     * @param message The message to display to other users
     */
    
    public void sendChatMessage(String message){
      MessageUtils.sendMessage(writer, MessageUtils.makeGlobalChatMessage(username, message));
      //TODO: World-wide or lobby-wide?
    }
    
    /**
     * Inform the server that this user has finished all phases of their current player turn.
     * <p>
     * This shouldn't be called when it isn't the user's game turn.
     * <p>
     * For context: After this message is sent, the server may pass control to the next user, or
     * the next game turn may start, or the game may end.
     */
    public void endTurn(){
        MessageUtils.sendMessage(writer, MessageUtils.makeYieldTurnMessage());
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
         * <p>
         * @author Christopher Goes
         */
        private void createStream() {
            if (isConnected()) {
                try {
                    streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
        private void killThread() {
            killed = true;
        }

        @Override
        public void run() {
            List<String> message = null; // incoming message from server. First string is message tag.
            try {
                while (!killed) {

                    if (killed) {
                        close();
                        return;
                    } else if (isConnected() && streamIn != null ) {
                        while (!streamIn.ready()) { // This fixed it!
                            if(killed) {
                                close();
                                return;
                            }
                        }
                        message = MessageUtils.receiveMessage(streamIn);
                    } else {
                        continue;
                    }

                    if (message == null) {
                        System.err.println("Null message from server, or default never changed!");
                        close();
                        return;
                    } else if (message.isEmpty()) {
                        System.err.println("Empty message from server!");
                        continue; // since this isn't a critical error, no reason to kill the thread yet...
                    }

                    //first element of the parsed message array will tell us
                    //what type of message it is:
                    if (message.get(0).equals(MessageUtils.GLOBAL_CHAT)) {
                        if ( message.get(1).equals(username) ) {
                            // suppress message
                        } else {
                            // TODO: ADD CHAT "PRIVACY" TAG. EX: (Global), (<lobby>), etc
                            // TODO: ADD CONNECTION STATUS TAG. EX: (CONNECTED), (DISCONNECTED), other states
                            MessageUtils.printChat(consoleOut, message);
                        }
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
                    //Added by John Goettsche (I hope this is where it goes    
                    } else if (message.get(0).equals(MessageUtils.UPDATE_UNIT)){
                        UnitPool pool = UnitPool.getInstance();
                        MoveableUnit unit = pool.getUnit(message.get(1));
                        String location = message.get(2);
                        pool.addMove(unit, location);
                    } else if (message.get(0).equals(MessageUtils.ADD_UNIT)){
                        UnitPool pool = UnitPool.getInstance();
                        MoveableUnit unit = pool.getUnit(message.get(1));
                        unit.setRace(message.get(2));
                        unit.setUnitType(message.get(3));            
                        String location = message.get(4);
                        //needs player ID
                        //pool.addUnit(port, unit);
                    } else if (message.get(0).equals(MessageUtils.JOINED_LOBBY) ) {
                            consoleOut.println("Client " + message.get(2) + " has joined lobby "
                            + message.get(1) );
                    } else if (message.get(0).equals(MessageUtils.LEFT_LOBBY) ) {
                            consoleOut.println("Client " + message.get(2) + " has left lobby "
                            + message.get(1) );                       
                    } else {
                        // its not a network message, therefore NC doesn't care, and has Jarvis take out the trash
                        //jarvis.processMessage( message.subList(1, message.size()), message.get(0) );
                        System.err.println("Unknown tag: " + message.get(0));
                    } // end else
                } // end while
            } catch (IOException ex) {
                System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                        + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                ex.printStackTrace();
                close();
                return;
            } // end catch

            close();
        }

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
                    if( streamIn != null) {
                        streamIn.close();
                        streamIn = null;
                    }
                    
                }
            } catch (IOException ex) {
                //System.err.println("Error closing listener! Error thrown: " + e);
                System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                        + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                ex.printStackTrace();
            }
        }

    }

    /**
     * Starts {@link WriterThread writerThread} and
     * {@link ListenerThread listenerThread}
     * <p>
     * @author Christopher Goes
     */
    private void startThreads() {

        setWriter();

        listenerThread = new ListenerThread();
        listenerThread.createStream();
        listenerThread.start();
    }

    /**
     * Kills thread(s) and resets writer stream.
     * <p>
     * NOTE: Call this BEFORE restarting threads with new socket! WARNING: This
     * will close any associated sockets!
     * <p>
     * @author Christopher Goes
     */
    private void stopThreads() {
        if (listenerThread != null) {
            listenerThread.killThread();
        }
        listenerThread = null;
        writer = null;
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
                writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
            } catch (IOException ex) {
                System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                        + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                ex.printStackTrace();
            }
        }
    }

    /* COMMAND PROCESSING/EXECUTION METHODS */
    
    /**
     * Parses user input, executes commands, and sends messages to server
     * <p>
     * @author Christopher Goes
     * @param command
     * @return boolean True if executed normally, False if quit or exception
     */
    private boolean processInput(String command) {

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
                MessageUtils.sendMessage(writer, MessageUtils.makeNewLobbyRequestMessage(lobbyName));

            } else if ("/joinLobby".equals(parsedString[0])) {
                String lobbyName = parsedString[1];
                MessageUtils.sendMessage(writer, MessageUtils.makeJoinLobbyRequestMessage(lobbyName));
            } else if (isConnected()) {
                // sends chat message to server, which broadcasts to all clients
                MessageUtils.sendMessage(writer, MessageUtils.makeGlobalChatMessage(username, command));
            }
        } else if (parsedString.length == 1) {
            if ("/printFile".equals(parsedString[0])) { // TODO: What does this do?
                write(MessageUtils.PRINT_FILE); //TODO: No "Done" string?

            } else if ("/globalWho".equals(parsedString[0])) {
                MessageUtils.sendMessage(writer, MessageUtils.makeGlobalWhoRequestMessage());

            } else if ("/leaveLobby".equals(parsedString[0])) {
                MessageUtils.sendMessage(writer, MessageUtils.makeLeaveLobbyMessage());

            } else if ("/showLobbies".equals(parsedString[0])) { // TODO: message if no lobbies available, command to create lobby
                MessageUtils.sendMessage(writer, MessageUtils.makeRequestLobbyInfoMessage()); // TODO: working lobby info request

            } else if ("/disconnect".equals(parsedString[0])) { // manual client disconnect
                if (isConnected()) {
                    disconnectFromServer();
                } else {
                    consoleOut.println("Can't disconnect when you're not connected!");
                }

            } else if ("/yieldTurn".equals(parsedString[0])) { // client turn over
                endTurn();
                

            } else if ("/beginGame".equals(parsedString[0])) { // request to start game
                MessageUtils.sendMessage(writer, MessageUtils.makeBeginGameRequestMessage());

            } else if ("/help".equals(parsedString[0])) {
                printCommandList();

            } else if ("/reconnect".equals(parsedString[0])) {
                if (isConnected()) { // Anti-clone League of Uganda certified
                    consoleOut.println("Already connected!");
                    return true;
                }
                consoleOut.print("Attempting to reconnect...");
                if (connect()) {
                    consoleOut.println("Successfully reconnected!");
                    startClient();

                } else {
                    consoleOut.println("Reconnect failed");
                }

            } else if ("/quit".equals(parsedString[0])) {
                consoleOut.print("Exiting client...");
                if (isConnected()) {
                    consoleOut.println("Still connected to server, disconnecting");
                    disconnectFromServer();
                }
                return false;
            }
            else {
                consoleOut.println("Invalid command, try again, or type /help for a list of commands.");
            }
        } else {
            if (isConnected()) {
                sendChatMessage(command);
                
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Send a file to the server
     * <p>
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
     * <p>
     * @param message Message to write
     */
    private void write(String message) { //
        writer.println(message);
        writer.flush();
    }

    /**
     * Prints list of commands and what they do from a text file
     * <p>
     * @author Christopher Goes
     * @throws IOException
     */
    private void printCommandList() {
        String inputline;
        String tempfile;

        // change src and server to variables when project is nearing completion
        tempfile = dir + File.separator + "src" + File.separator + "server" + File.separator + helpfile;

        try {
            BufferedReader input = new BufferedReader(new FileReader(tempfile));

            try {
                while ((inputline = input.readLine()) != null) {
                    System.out.println(inputline);
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

    /**
     * Attempts connection to the server, and returns socket if successful
     * <p>
     * @author Christopher Goes
     * @param sName
     * @param serverPort
     * @return Socket
     * @throws IOException
     */
    private Socket connectToServer(String sName, int serverPort) throws IOException {
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
     * <p>
     * @author Christopher Goes
     * @return boolean True if connected, False if not
     */
    private boolean isConnected() {
        return socket != null && !(socket.isClosed()) && socket.isConnected();
    }

    /**
     * Disconnects client from server
     * <p>
     * @author Christopher Goes
     */
    private void disconnectFromServer() {
        consoleOut.print("Disconnecting from server...");
        if (isConnected()) {
            MessageUtils.sendMessage(writer, MessageUtils.makeDisconnectRequestMessage());
            writer.flush();
        }

        stopThreads();
        killSocket();
        consoleOut.println("Disconnected!");
    }

    /**
     * Closes and nulls socket
     * <p>
     * @author Christopher Goes
     */
    private void killSocket() {
        try {
            if (socket != null && !(socket.isClosed())) {
                socket.close();
                socket = null;
            } else if (socket != null) {
                socket = null;
            }
        } catch (IOException ex) {
            //System.err.println("Error in killSocket!\nException: " + e);
            System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                    + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
            ex.printStackTrace();
        }
    }

    /**
     * Closes all open streams, and kills all active threads
     */
    private void stopClient() {
        killSocket();
        if (listenerThread != null || writer != null) {
            stopThreads();
        }
    }

    /* MAIN */
    /**
     * Opens dialog box(s), creates NetworkClient instance, and executes public
     * methods
     * <p>
     * NOTE: This is mainly for testing purposes, should not be in final build
     * <p>
     * @param args
     */
    public static void main(String[] args) {

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
            if (client.startClient()) {
                client.runClient();
            } else {
                System.err.println("Client failed to start from main!");
            }
        } else {
            System.err.println("Client failed to connect from main!");
        }
    } // end main

} // end class
