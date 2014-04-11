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
    private static int port = 25565; // anti-magicnumbers league
    private static String serverName;

    // Read/write streams
    private BufferedReader consoleIn = null;
    private PrintWriter consoleOut = new PrintWriter(System.out, true);

    // Client info
    private static String username;

    // Thread(s)
    private ListenerThread listenerThread;
    private PrintWriter writer = null;

    // set default help file
    private String helpfile = "commands.txt";
    private String dir = System.getProperty("user.dir"); // set every time method called

    private Conductor jarvis; // Our conductor object
    
    /**
     * Listens for and handles incoming communications for Network Client
     */
    private class ListenerThread extends Thread {

        private BufferedReader streamIn;
        private boolean killed = false;

        public ListenerThread() {
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
            streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
            
                while (true) {

                    if (killed) {
                        close();
                        return;
                    } else if (!(socket.isClosed()) && socket.isConnected()) {
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
                        jarvis.processMessage( message.subList(1, message.size()), message.get(0) );
                        //System.err.println("Unknown tag: " + message.get(0));
                    } // end else
                } // end while
        } // end method

        /**
         * Always run this before returning from {@link #run run}!
         */
        private void close() {
            try {
                if (streamIn != null) {
                    streamIn.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing listener! Error thrown: " + e);
            }
        }

    }

    /**
     * Initializes writer with a new stream. Socket must be set before calling
     * this!
     *
     * @author Christopher Goes
     * @throws IOException
     */
    private void setWriter() throws IOException {
        writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
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

    } // end method

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

        // sets server iNet info
        port = serverPort;
        serverName = sName;

        // Set client username
        username = uName;

        //Reads from stdin:
        consoleIn = new BufferedReader(new InputStreamReader(System.in));

    } // end constructor

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

    } // end method    

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
                if ((line = consoleIn.readLine()) == null) {
                    System.err.println("line == null! Eeek!");
                    stop();
                    break;
                }
                if (!(processInput(line))) {
                    stop();
                    break;
                }

            } catch (IOException e) {
                System.err.println("Error sending message! Error thrown: " + e);
                stop();
                break;
            } // end catch   
        } // end while
    } // end method  

    /**
     * Parses user input, executes commands, and sends messages to server
     *
     * @author Christopher Goes
     * @param command
     * @return boolean True if execute normally, False if quit or exception
     * @throws IOException
     */
    private boolean processInput(String command) throws IOException {

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
            }

        } else if (parsedString.length == 1) {
            if ("/printFile".equals(parsedString[0])) {
                write(MessageUtils.PRINT_FILE); //TODO: No "Done" string?

            } else if ("/globalWho".equals(parsedString[0])) {
                MessageUtils.sendMessage(writer, MessageUtils.makeGlobalWhoRequestMessage());

            } else if ("/leaveLobby".equals(parsedString[0])) {
                MessageUtils.sendMessage(writer, MessageUtils.makeLeaveLobbyMessage());

            } else if ("/showLobbies".equals(parsedString[0])) {
                MessageUtils.sendMessage(writer, MessageUtils.makeRequestLobbyInfoMessage()); // TODO: working lobby info request

            } else if ("/disconnect".equals(parsedString[0])) { // manual client disconnect
                MessageUtils.sendMessage(writer, MessageUtils.makeDisconnectRequestMessage());
                stopThreads();
                socket.close();
                consoleOut.println("Disconnected from server");
            } else if ("/yieldTurn".equals(parsedString[0])) { // client turn over
                MessageUtils.sendMessage(writer, MessageUtils.makeYieldTurnMessage());

            } else if ("/beginGame".equals(parsedString[0])) { // request to start game
                MessageUtils.sendMessage(writer, MessageUtils.makeBeginGameRequestMessage());

            } else if ("/help".equals(parsedString[0])) {
                printCommandList();

            } else if ("/reconnect".equals(parsedString[0])) {
                // MAKE SURE USER CANT CLONE THEMSELVES!
                consoleOut.println("Attempting to reconnect...");
                if (connect()) {
                    start();
                    setWriter();
                } else {
                    consoleOut.println("Reconnect failed");
                }

            } else if ("/quit".equals(parsedString[0])) {
                consoleOut.println("Exiting...");
                // add if still connected, calls to make softer quitting
                stop();
                return false;
            } else if (!(socket.isClosed()) && socket.isConnected()) {
                // sends chat message to server, which broadcasts to all clients
                // TODO: have client ignore a message it sent, so user doesn't see what he typed twice
                MessageUtils.sendMessage(writer, MessageUtils.makeGlobalChatMessage(username, command));

            } else {
                consoleOut.println("Invalid command, try again, or type /help for a list of commands.");
            }
        }
        return true;
    }

    /**
     * Creates a new connection to the server, call this before
     * {@link #start start()}
     *
     * @return boolean True if successful, false if not
     * @throws IOException
     * @author Christopher Goes
     */
    public boolean connect() throws IOException {
        try {
            socket = connectToServer(serverName, port);
            return true;
        } catch (NullPointerException e) {
            System.err.println("Error: null socket!");
            return false;
        }
    } // end method

    /**
     * Starts {@link WriterThread writerThread} and
     * {@link ListenerThread listenerThread}
     *
     * @author Christopher Goes
     * @throws Exception
     */
    private void startThreads() throws IOException { // assumes socket set
        /*if (writerThread = null) {
         writerThread.createStream();
         }
         writerThread.start();*/

        setWriter();

        listenerThread = new ListenerThread();
        listenerThread.createStream();
        listenerThread.start();
    } // end method

    /**
     * Kills thread(s) and resets writer stream
     * <p>
     * NOTE: Call this BEFORE restarting threads with new socket!
     * @author Christopher Goes
     */
    private void stopThreads() {
        writer = null;
        listenerThread.killThread();
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

        System.out.println("Connected: " + tempsock);
        return tempsock;
    }

    /**
     * Closes all open streams, and kills all active threads
     */
    private void stop() {
        try {
            if (consoleIn != null) {
                consoleIn.close();
            }
            if (socket != null) {
                socket.close();
            }
            if (listenerThread.isAlive() || writer != null) {
                stopThreads();
            }
        } catch (IOException e) {
            System.err.println("Error closing connection!\nException: " + e);
        }
    }

    /**
     * Used to test the Network Client
     *
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

        try {
            if (client.connect()) {
                client.start();
                client.runClient();
            } else {
                System.err.println("Client failed to connect! Oh noes!");
            }

        } catch (IOException e) {
            System.err.println("Error starting client from main!\nException thrown: " + e);
        }
    }

} // end class
