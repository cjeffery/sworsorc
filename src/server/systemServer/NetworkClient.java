/**
 * The Network Client
 * <p>
 * Handles client-side communication with the server
 */
package systemServer;

import Units.MoveableUnit;
import Units.UnitPool;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * The Infamous Network Client, handles communication to/from Network Server
 * <p>
 * NOTE: This is a static singleton. 
 * Don't fool around trying to create instances, you hear now?
 */
public class NetworkClient {
// TODO: New messaging protocol, one that doesn't cause carpal tunnel syndrome from sheer amount of characters

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
    private static ListenerThread listenerThread = null;
    private static PrintWriter writer = null;

    // Set default help file
    final private static String helpfile = "commands.txt";
    final private static String dir = System.getProperty("user.dir");

    //private Conductor jarvis; // Our conductor object

    /* PUBLIC METHODS */
    /**
     * Creates a new connection to the server, call this before
     * {@link #startClient startClient()}
     * <p>
     * @return boolean True if successful, false if not
     * @author Christopher Goes
     */
    public static boolean connect() { // TODO: connect pass arguments, or no longer public?
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
     * Starts activity to server, including threads, and command input loop
     * <p>
     * @author Christopher Goes
     * @return boolean True if successful, false if runtime error/exception
     */
    public static boolean startClient() {

        startRemoteStreams();
        startLocalStreams();
        
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
    public static void runClient() {
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
                    consoleOut.print(username + ": ");
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

    /* GETTERS/SETTERS */
    public static void setServerName(String sName) {
        if (sName == null || sName.isEmpty()) {
            System.err.println("Invalid server name!");
        } else {
            serverName = sName;
        }
    }

    public static void setServerPort(int sPort) {
        if (sPort < 1024) {
            System.err.println("Invalid port!");
        } else {
            port = sPort;
        }
    }

    public static void setUsername(String uName) {
        if (uName == null || uName.isEmpty()) {
            System.err.println("Invalid server name!");
        } else {
            username = uName;
        }
    }

    public static String getServerName() {
        return serverName;
    }

    public static int getServerPort() {
        return port;
    }

    public static String getUsername() {
        return username;
    }

    /* THREAD N' STREAM STUFF */
    /**
     * Listens for and handles incoming communications for Network Client
     */
    private static class ListenerThread extends Thread {

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
                    } else if (isConnected() && streamIn != null) {
                        while (!streamIn.ready()) { // This fixed it!
                            if (killed) {
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
                        if (message.get(1).equals(username)) {
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
                    } else if (message.get(0).equals(MessageUtils.UPDATE_UNIT)) {
                        UnitPool pool = UnitPool.getInstance();
                        MoveableUnit unit = pool.getUnit(message.get(1));
                        String location = message.get(2);
                        pool.addMove(unit, location);
                    } else if (message.get(0).equals(MessageUtils.ADD_UNIT)) {
                        UnitPool pool = UnitPool.getInstance();
                        MoveableUnit unit = pool.getUnit(message.get(1));
                        unit.setRace(message.get(2));
                        unit.setUnitType(message.get(3));
                        String location = message.get(4);
                        //needs player ID
                        //pool.addUnit(port, unit);
                    } else if (message.get(0).equals(MessageUtils.JOINED_LOBBY)) {
                        consoleOut.println("Client " + message.get(2) + " has joined lobby "
                                + message.get(1));
                    } else if (message.get(0).equals(MessageUtils.LEFT_LOBBY)) {
                        consoleOut.println("Client " + message.get(2) + " has left lobby "
                                + message.get(1));
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
                    if (streamIn != null) {
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
     * Starts {@link ListenerThread listenerThread} and sets
     * {@link #setWriter() writer stream}
     * <p>
     * <p>
     * @author Christopher Goes
     */
    private static void startRemoteStreams() {

        setWriter();

         
        listenerThread = new ListenerThread();
        
        listenerThread.createStream();
        listenerThread.start();
    }

    /**
     * Kills ListenerThread and resets writer stream.
     * <p>
     * NOTE: Call this BEFORE restarting threads with new socket! WARNING: This
     * will close any associated sockets!
     * <p>
     * @author Christopher Goes
     */
    private static void killRemoteStreams() {
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
    private static void setWriter() {
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

    /**
     * Initializes console Input/Output streams
     * <p>
     * @author Christopher Goes
     */
    private static void startLocalStreams() {
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
            consoleIn.close();
            consoleIn = null;
            consoleOut.close();
            consoleOut = null;
        } catch (IOException ex) {
            System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                    + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
            ex.printStackTrace();
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
    private static boolean processInput(String command) {

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
            if ("/printFile".equals(parsedString[0])) {
                sendMessage(MessageUtils.PRINT_FILE);

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
                MessageUtils.sendMessage(writer, MessageUtils.makeYieldTurnMessage());

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
            } else if (isConnected()) {
                MessageUtils.sendMessage(writer, MessageUtils.makeGlobalChatMessage(username, command));

            } else {
                consoleOut.println("Invalid command, try again, or type /help for a list of commands.");
            }
        } else {
            if (isConnected()) {
                MessageUtils.sendMessage(writer, MessageUtils.makeGlobalChatMessage(username, command));
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
    private static void sendFile(String fileName) {
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
    private static void sendMessage(String message) {
        List<String> temp = new ArrayList<>();
        temp.add(message);
        MessageUtils.sendMessage(writer, temp);
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
                    consoleOut.println(inputline);
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
     * <p>
     * @author Christopher Goes
     * @return boolean True if connected, False if not
     */
    private static boolean isConnected() {
        return socket != null && !(socket.isClosed()) && socket.isConnected();
    }

    /**
     * Disconnects client from server
     * <p>
     * @author Christopher Goes
     */
    private static void disconnectFromServer() {
        consoleOut.print("Disconnecting from server...");
        if (isConnected()) {
            MessageUtils.sendMessage(writer, MessageUtils.makeDisconnectRequestMessage());
            writer.flush();
        }

        killRemoteStreams();
        killSocket();
        consoleOut.println("Disconnected!");
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
            //System.err.println("Error in killSocket!\nException: " + e);
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
        if (listenerThread != null || writer != null) {
            killRemoteStreams();
        }
        if (consoleIn != null || consoleOut != null ) {
            killLocalStreams();
        }
    }

    /* MAIN */
    /**
     * Opens dialog box(s), creates NetworkClient instance, and executes startup
     * methods
     * <p>
     * NOTE: Kill when client closes
     * <p>
     * @param args
     */
    public static void main(String[] args) {

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

        if (NetworkClient.connect()) {
            if (NetworkClient.startClient()) {
                NetworkClient.runClient();
            } else {
                System.err.println("Client failed to start from main!");
            }
        } else {
            System.err.println("Client failed to connect from main!");
        }
    } // end main

} // end class
