
package chatclient;

import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import javax.swing.JOptionPane;

public class ChatClient {

    private Socket socket = null;
    private BufferedReader consoleIn = null;
    private PrintWriter consoleOut = new PrintWriter(System.out, true);
    
    private static String ipAddress;
    private static String username;

    ListenerThread listenerThread;
    WriterThread writerThread;

    class ListenerThread extends Thread {

        BufferedReader streamIn;

        public ListenerThread() {
            try {
                streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception e) {
                System.err.println("Error : Opening reading stream from socket");
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
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        //TODO: make standard message protocol function:
        private void receiveConnectionList() {
            System.out.println("Users online:");
            List<String> connections = MessageUtils.receiveMessage(streamIn);
            for (String s : connections) {
                System.out.println(s);
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
            }catch(Exception e){
                System.out.println("Could not open file!");
            }
        }

        public void run() {
            String[] parsedString;
            while (true) {
                try {
                    String line = consoleIn.readLine();
                    if (line == null){ //connection broken (NOT an exception)
                        close();
                        break;
                    }
                    parsedString = line.split("\\s+"); //Split line by whitespace
                    if(parsedString.length == 2){
                        if("/file".equals(parsedString[0])){
                            sendFile(parsedString[1]);
                        }
                        else if ("/newLobby".equals(parsedString[0])){
                            String lobbyName = parsedString[1];
                            MessageUtils.sendMessage(writer, MessageUtils.makeNewLobbyMessage(lobbyName));
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
                        if("/globalWho".equals(parsedString[0])){
                            MessageUtils.sendMessage(writer, MessageUtils.makeGlobalWhoRequestMessage());
                        }
                        else if ("/leaveLobby".equals(parsedString[0])){
                            MessageUtils.sendMessage(writer, MessageUtils.makeLeaveLobbyMessage());
                        }
                        else if ("/showLobbies".equals(parsedString[0])){
                            MessageUtils.sendMessage(writer, MessageUtils.makeRequestLobbyInfoMessage());
                        }
                        else if ("/disconnect".equals(parsedString[0])){
                            //TODO: Disconnect
                        }
                    }
                    
                    //We should keep this for now:
                    MessageUtils.sendMessage(writer, MessageUtils.makeGlobalChatMessage(username, line));
                    
                } catch (IOException e) {
                    System.out.println("Error sending message!");
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
            } catch (Exception e) {
                System.err.println(e);
            }
        }

    }

    public ChatClient(String serverName, int serverPort) throws IOException {

        System.out.println("Connecting! Please Wait!");
        try {
            socket = new Socket(serverName, serverPort);
            System.out.println("Connected: " + socket);
            //start();
        } catch (UnknownHostException e) {
            System.err.println("Error : Uknown host!");
        } catch (ConnectException e) {
            System.err.println("Error : Connection Refused!");
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

    public void stop() {
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

        ChatClient client = null;
        client = new ChatClient(ipAddress, 25565);
    }

}
