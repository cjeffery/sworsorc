/**
 * The Chat Client
 * <p>
 * Handles client-side communication
 */
package systemServer;

import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Handles communication to server from client
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

    // Threads
    ListenerThread listenerThread;
    //WriterThread writerThread;
    private PrintWriter writer = null;

    /**
     * Listens for and handles incoming communications for Network Client
     */
    class ListenerThread extends Thread {

        private BufferedReader streamIn;
        private boolean killed = false;
        
        public ListenerThread() {
            // empty constructor
        }

        protected void createStream() throws IOException {
            streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        protected void killThread() {
            killed = true;
        }
        public void run() {
            try {
                while (true) {

                    if( killed ) {
                        close();
                        return;
                    }
                    List<String> message = MessageUtils.receiveMessage(streamIn);
                    if (message == null) {
                        break;
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
                        //This shouldn't ever happen!
                        System.err.println("Unknown tag: " + message.get(0));
                    }

                }
            } catch (Exception e) {
                System.err.println("Client " + " error: " + e);
            } finally {
                close();
            }
        }

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

    /**class WriterThread extends Thread {

        private PrintWriter writer;

        public WriterThread() {
            // empty constructor
        }

        protected void createStream() throws IOException {
            writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
        }

        protected void write(String message) { //if we want to programmatically write something
            writer.println(message);
            writer.flush();
        }

        protected void sendFile(String fileName) {
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

        public void run() {

        }

        public void close() {
            try {
                
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                System.err.println("Error closing writer! Error thrown: " + e);
            }
        }

        public PrintWriter getWriter() {
            return writer;
        }

    } // end class 
    */

    protected void setWriter() throws IOException {
        writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
    }

    protected void sendFile(String fileName) {
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

    protected void write(String message) { //if we want to programmatically write something
        writer.println(message);
        writer.flush();
    }

    /**
     * Prints list of commands from commands.txt file
     */
    private void printCommandList() throws IOException {
        String inputline;
        String filename = "commands.txt";
        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));

            while ((inputline = input.readLine()) != null) {
                System.out.println(inputline);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename + "\nException: " + e);
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

        username = uName;
        //Reads from stdin:
        consoleIn = new BufferedReader(new InputStreamReader(System.in));

        // sets Read/Write threads
        //listenerThread = new ListenerThread();
       // writerThread = new WriterThread();

    } // end constructor

    /**
     * Starts activity to server, including threads, and command input loop
     *
     * @author Christopher Goes
     * @throws Exception
     */
    public void start() throws Exception {

        startThreads();
        //first message is handle:
        MessageUtils.sendMessage(writer, MessageUtils.makeSendHandleMessage(username));

        //request list of clients:
        MessageUtils.sendMessage(writer, MessageUtils.makeGlobalWhoRequestMessage());

        //request list of lobbies:
        MessageUtils.sendMessage(writer, MessageUtils.makeRequestLobbyInfoMessage());

        //startThreads(); // why are messages sent before threads started?
    } // end method    

    /**
     * Main execution thread for the Network Client, handles user commands and
     * messages
     *
     * @author Christopher Goes
     * @throws Exception
     */
    public void runClient() throws Exception {
        String[] parsedString;
        String line;
        //PrintWriter writer = writerThread.getWriter();

        while (true) {
            if (!(socket.isClosed())) {
                try {

                    if ((line = consoleIn.readLine()) == null) {
                        System.err.println("line == null! Eeek!");
                        stop();
                        break;
                    }

                    parsedString = line.split("\\s+"); //Split line by whitespace

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

                        } else if ("/quit".equals(parsedString[0])) {
                            consoleOut.println("Exiting...");
                            stop();
                            break;
                        }
                    } // end if

                    if (socket.isConnected() && !(socket.isClosed())) {
                        MessageUtils.sendMessage(writer, MessageUtils.makeGlobalChatMessage(username, line));
                    }

                } catch (IOException e) {
                    System.err.println("Error sending message! Error thrown: " + e);
                    stopThreads();
                    stop();
                    break;
                } // end catch
            } // end if
            else { // This runs when client is disconnected. Its ugly, but works.
                if ((line = consoleIn.readLine()) == null) {
                    System.err.println("line == null! Eeek!");
                    stop();
                    break;
                }

                parsedString = line.split("\\s+"); //Split line by whitespace

                if ("/reconnect".equals(parsedString[0])) {
                    consoleOut.println("Attempting to reconnect...");
                    if (connect()) {
                        start();
                        setWriter();
                        //writer = writerThread.getWriter();
                    } else {
                        consoleOut.println("Reconnect failed");
                    }

                } else if ("/help".equals(parsedString[0])) {
                    printCommandList();
                } else if ("/quit".equals(parsedString[0])) {
                    consoleOut.println("Exiting...");
                    stop();
                    break;
                }
            }
        } // end while

    } // end method  

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
    private void startThreads() throws Exception { // assumes socket set
        /*if (writerThread = null) {
            writerThread.createStream();
        }
        writerThread.start();*/

        setWriter();
        
        listenerThread = new ListenerThread();
        listenerThread.createStream();
        listenerThread.start();
    } // end method

    private void stopThreads() {
        //writerThread.close();
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

        System.out.println("Connected: " + tempsock);
        return tempsock;

    }

    protected void stop() { // private?
        try {
            if (consoleIn != null) {
                consoleIn.close();
            }
            if (socket != null) {
                socket.close();
            }
            /*if (writerThread.isAlive() || listenerThread.isAlive()) {
                stopThreads();
            }*/
            if( listenerThread.isAlive()) {
                stopThreads();
            }
        } catch (IOException e) {
            System.err.println("Error closing connection!\nException: " + e);
        }
    }

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

        } catch (Exception e) {
            System.err.println("Error starting client from main!\nException thrown: " + e);
        }
    }

}
