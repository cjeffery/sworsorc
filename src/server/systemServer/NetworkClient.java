
package systemServer;

import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public class NetworkClient {

    // Command list. For those of us who forget commands. Also reference.
    // Better way to do this? Go ahead!
    static String HELP = "/disconnect \n/newLobby \n/joinLobby <lobbyName> \n/leaveLobby" +
                            "\n/showLobbies \n/newLobby <lobbyName> \n/file <filename> \n/yieldTurn" + 
            "\n/beginGame \n/globalWho";
    
    private Socket socket = null;
    private BufferedReader consoleIn = null;
    private PrintWriter consoleOut = new PrintWriter(System.out, true);
    
    private static String ipAddress;
    final private static int port = 25565; // anti-magicnumbers league
    private static String username;

    ListenerThread listenerThread;
    WriterThread writerThread;

    class ListenerThread extends Thread {

        BufferedReader streamIn;

        public ListenerThread() {
            try {
                streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                System.err.println("Error : Opening reading stream from socket. Error thrown: " + e );
            }
        }

        public void run() {
            try {
                while (true) {
                    
                    List<String> message = MessageUtils.receiveMessage(streamIn);
                    if (message == null){
                        break;
                    }
                    //first element of the parsed message array will tell us
                    //what type of message it is:
                    if (message.get(0).equals(MessageUtils.GLOBAL_CHAT)){
                        //Printing methods can be centralized!
                        MessageUtils.printChat(consoleOut, message);
                    }
                    else  if (message.get(0).equals(MessageUtils.DISCONNECT_ANNOUNCEMENT)){                       
                        MessageUtils.printDisconnect(consoleOut, message);
                    }
                    else  if (message.get(0).equals(MessageUtils.CONNECT_ANNOUNCEMENT)){                       
                        MessageUtils.printConnectionMessage(consoleOut, message);
                    }
                    else  if (message.get(0).equals(MessageUtils.GLOBAL_WHO_LIST)){                       
                        MessageUtils.printGlobalWhoList(consoleOut, message);
                    }
                    else  if (message.get(0).equals(MessageUtils.LOBBY_INFO)){                       
                        MessageUtils.printLobbyInfo(consoleOut, message);
                    }
                    else  if (message.get(0).equals(MessageUtils.APROVE_NEW_LOBBY_REQUEST)){                       
                        consoleOut.println("Lobby created!");
                    }
                    else  if (message.get(0).equals(MessageUtils.GAME_BEGUN)){                       
                        consoleOut.println("Game has begun!");
                    }
                    else  if (message.get(0).equals(MessageUtils.DENY_NEW_LOBBY_REQUEST)){                       
                        consoleOut.print(("Could not create lobby: (Duplicated name?)"));
                    }
                    else  if (message.get(0).equals(MessageUtils.NAG)){                       
                        System.err.println("NAG: " + message.get(1));
                    }
                    else  if (message.get(0).equals(MessageUtils.NEXT_TURN_INFO)){
                        if (username.equals(message.get(1))){
                            //it's my turn!
                            consoleOut.println("It is now my turn!");
                        }
                        consoleOut.println("It is now " + message.get(1) + "'s turn!");
                    }
                    else {
                        //This shouldn't ever happen!
                        System.err.println("Unknown tag: " + message.get(0));
                    }
                    

                }
            } catch (Exception e) {
                System.out.println("Client " + " error: " + e);
            } finally {
                close();
            }
        }

        public void close() {
            try {
                if (socket != null) {
                    socket.close();
                }
                if (streamIn != null) {
                    streamIn.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing listener! Error thrown: " + e );
            }
        }

    }

    class WriterThread extends Thread {

        PrintWriter writer;

        public WriterThread() {
            try {
                writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
            } catch (IOException e) {
                System.err.println("Error : Creating output stream for socket");
            }

        }

        public void write(String message) { //if we want to programmatically write something
            writer.println(message);
            writer.flush();
        }
        
        public void sendFile(String fileName){
            String line;
            try{
                BufferedReader file = new BufferedReader(new InputStreamReader
                    (new FileInputStream(fileName), Charset.forName("UTF-8")));
                MessageUtils.sendMessage(writer, MessageUtils.makeIncomingFileMessage(fileName));
                while ((line = file.readLine()) != null){
                    MessageUtils.sendMessage(writer, MessageUtils.makeFileLineMessage(fileName, line));
                }
            }catch(IOException e){
                System.out.println("Could not open file! Error thrown: " + e );
            }
        }

        public void run() {
            String[] parsedString;
            while (true) {
                try {
                    String line = consoleIn.readLine();
                    
                    // Connection terminated
                    // Note: Will not terminate writer on broken connection, ONLY on user-specified exit or exception!
                    if (line == null){
                        System.err.println("line == null...");
                    }
                    
                    parsedString = line.split("\\s+"); //Split line by whitespace
                    
                    if(parsedString.length == 2){
                        if("/file".equals(parsedString[0])){
                            sendFile(parsedString[1]);
                        }
                        else if ("/newLobby".equals(parsedString[0])){
                            String lobbyName = parsedString[1];
                            MessageUtils.sendMessage(writer, MessageUtils.makeNewLobbyRequestMessage(lobbyName));
                        }
                        else if ("/joinLobby".equals(parsedString[0])){
                            String lobbyName = parsedString[1];
                            MessageUtils.sendMessage(writer, MessageUtils.makeJoinLobbyRequestMessage(lobbyName));
                        }
                    }
                    else if(parsedString.length == 1){
                        if("/printFile".equals(parsedString[0])){
                            write(MessageUtils.PRINT_FILE); //TODO: No "Done" string?
                        }
                        else if("/globalWho".equals(parsedString[0])){
                            MessageUtils.sendMessage(writer, MessageUtils.makeGlobalWhoRequestMessage());
                        }
                        else if ("/leaveLobby".equals(parsedString[0])){
                            MessageUtils.sendMessage(writer, MessageUtils.makeLeaveLobbyMessage());
                        }
                        else if ("/showLobbies".equals(parsedString[0])){
                            MessageUtils.sendMessage(writer, MessageUtils.makeRequestLobbyInfoMessage()); // TODO: working lobby info request
                        }
                        else if ("/disconnect".equals(parsedString[0])){ // manual client disconnect
                            MessageUtils.sendMessage(writer, MessageUtils.makeDisconnectRequestMessage());
                        }
                        else if ("/yieldTurn".equals(parsedString[0])){ // client turn over
                            MessageUtils.sendMessage(writer, MessageUtils.makeYieldTurnMessage());
                        }
                        else if ("/beginGame".equals(parsedString[0])){ // request to start game
                            MessageUtils.sendMessage(writer, MessageUtils.makeBeginGameRequestMessage());
                        }
                        else if ("/help".equals(parsedString[0])) {
                            System.out.println(HELP);
                        }
                    }
                    
                    if(socket.isConnected()) {
                        //System.out.println("Its connected");
                        MessageUtils.sendMessage(writer, MessageUtils.makeGlobalChatMessage(username, line));
                    }              
                    
                    // Finally realized how many streams socket is connected to
                    // Reconnection is going to require a lot more work...
                    /*else {
                        System.out.println("Connection terminated. Would you like to reconnect? (Yes/No)");
                        line = consoleIn.readLine();
                        if( line.equals("Yes")) {
                            socket = connect( ipAddress, port );
                        }
                        else {
                            System.out.println("Would you like to connect to a different server? (Yes/No ");
                            line = consoleIn.readLine();
                            if( line.equals("Yes")) {
                                System.out.println("IP address: ");
                                ipAddress = consoleIn.readLine();
                                socket = connect( ipAddress, port );
                            }
                            else {
                                close();
                                break;
                            }
                        }                        
                    }*/
                    
                    
                } catch (IOException e) {
                    System.out.println("Error sending message! Error thrown: " + e );
                    close();
                    break;
                }
            }


        }

        public void close() {
            try {
                if (socket != null) {
                    socket.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing writer! Error thrown: " + e );
            }
        }

    }

    public NetworkClient(String serverName, int serverPort) throws IOException {

        try {
            socket = connect( serverName, serverPort );
        } catch ( NullPointerException e) {
            System.err.println("Error: null socket!");
        }
        
        //Reads from stdin:
        consoleIn = new BufferedReader(new InputStreamReader(System.in));

        listenerThread = new ListenerThread();
        writerThread = new WriterThread();

        //first message is handle:
        //writerThread.write(username);
        MessageUtils.sendMessage(writerThread.writer, MessageUtils.makeSendHandleMessage(username));

        //request list of clients:
        MessageUtils.sendMessage(writerThread.writer, MessageUtils.makeGlobalWhoRequestMessage());

        //request list of lobbies:
        MessageUtils.sendMessage(writerThread.writer, MessageUtils.makeRequestLobbyInfoMessage());

        
        writerThread.start();
        listenerThread.start();
    }

    // moved socket creation into method, so can call outside of constructor, mainly for reconnect.
    public static Socket connect(String serverName, int serverPort ) throws IOException {
        Socket tempsock = null;
        System.out.println("Connecting! Please Wait!");
        try {
            tempsock = new Socket(serverName, serverPort);
        } catch (UnknownHostException e) {
            System.err.println("Error : Unknown host!");
        } catch (ConnectException e) {
            System.err.println("Error : Connection Refused!");
        }
        
        System.out.println("Connected: " + tempsock);
        return tempsock;
        
    }
    
    public void stop() { // cleanup: is this needed? where?
        try {
            if (consoleIn != null) {
                consoleIn.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing connection!");
        }
    }

    public static void main(String[] args) throws IOException {

        ClientData clientData = new ClientData();
        ClientDataForm clientDataForm = new ClientDataForm(clientData);

        System.out.println("Launching Login Dialog");

        clientDataForm.setVisible(true);

        System.out.println("Login Dialog Finished");

        System.out.flush(); // I was losing input is the JDialog crashed

        ipAddress = clientData.getIPAddress();
        username = clientData.getUsername();

        NetworkClient client = null;
        client = new NetworkClient(ipAddress, port);
    }

}
