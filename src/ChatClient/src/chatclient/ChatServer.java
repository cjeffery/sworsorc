package chatclient;

import java.net.*;
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ChatServer {

    //TODO: have clientObjects remove themselves on disconnect:
    protected static List<ClientObject> clientObjects; //"Packaged sockets"
    protected static List<Lobby> lobbies;
    
    
    protected static int DEFAULT_PORT = 25565;
    protected static String DEFAULT_IP = "76.178.139.129";

    public static void createNewLobby(String name){
        //TODO: Enforce unique names?
       Lobby lobby = new Lobby(name);
       lobbies.add(lobby);
    }
    
    public static void joinLobby(String lobbyName, ClientObject client){
        for (Lobby l : lobbies){
            if (l.name.equals(lobbyName)){
                l.join(client);
                return;
            }
        }
        //If we're here, we didn't find the name!
        System.err.println("Error: Couldn't find lobby: " + lobbyName + " to join.");
    }
    
    public static void leaveLobby(ClientObject client){
        
        for (Lobby l : lobbies){
            if (l.lobbyClients.contains(client)){
                l.leave(client);
                if (l.lobbyClients.isEmpty()){
                    //For now, just kill lobbies when everyone leaves
                    lobbies.remove(l);
                }
                return;
            }
        }
        //If we're here, we didn't find the name!
        System.err.println("Requested to leave lobby from client not in lobby");
    }
    
    public static List<String> getAllUserNames(){
        List<String> handles = new ArrayList<>();
        for (ClientObject obj : ChatServer.clientObjects) {
             handles.add(obj.getHandle());
        }
        return handles;
    }
    
    //Forward the message to all ClientObjects, which will send to their Clients:
    public static void sendToAllClients(List<String> message) {
        for (ClientObject client : clientObjects) {
             client.send(message);           
        }
    }
    
    public static void clientDisconnected(int clientId){
        //clientObject will call this on a planned or unplanned disconnection
        
        ClientObject dearlyDeparted = null;
        
        for (int i = 0; i < clientObjects.size(); i++){
            if (clientObjects.get(i).clientID == clientId){
                dearlyDeparted = clientObjects.get(i);
                break;
            }
        }
        
        leaveLobby(dearlyDeparted);
        clientObjects.remove(dearlyDeparted);
        sendToAllClients(MessageUtils.makeDisconnectAnnouncementMessage(dearlyDeparted.getHandle()));
        
        
    }

    public static void main(String args[]) {
        clientObjects = new ArrayList<>();
        lobbies = new ArrayList<>();

        System.out.println("Server starting. . .");

        System.out.println("Binding port " + DEFAULT_PORT + " . . .");

        try {
            System.out.println("Server started (" + InetAddress.getLocalHost() + ")");
        } catch (Exception e) {
            System.out.println(e);
        }

        ServerSocket listen = null;
        try {
            listen = new ServerSocket(DEFAULT_PORT);
            //listen = new ServerSocket(DEFAULT_PORT, 0, InetAddress.getByName(DEFAULT_IP));
        } catch (Exception e) {
            System.err.println("Error : While creating ServerSocket\n" + e);
            System.exit(-1);
        }

        //Spins off new client connections:
        while (true) {
            try {
                System.err.println("Waiting for next client...");
                Socket socket = listen.accept(); //Get socket (blocking)

                //The constructor of ClientObject will create the new threads:
                clientObjects.add(new ClientObject(socket));
            } catch (Exception e) {
                System.out.println("Server failed to accept client!");
                break;
            }
        }

    }

}

class Lobby {
    List<ClientObject> lobbyClients;
    static int lobbyCounter = 0;
    int lobbyId;
    String name;
    
    public Lobby(String name){
        lobbyClients = new ArrayList<>();
        this.name = name;
        this.lobbyId = lobbyCounter++;
    }
    
    public void sendToEntireLobby(List<String> message){
        for (ClientObject client: lobbyClients){
            client.send(message);
        }
    }
    
    public List<String> getUserNames(){
        List<String> handles = new ArrayList<>();
        for (ClientObject client : lobbyClients){
            handles.add(client.getHandle());
        }
        return handles;
    }
    
    public void join(ClientObject client){
        lobbyClients.add(client);
        //TODO: announce join to other connected clients
    }
    
    public void leave(ClientObject client){
        lobbyClients.remove(client);
        //TODO: announce leave to other connected clients
    }
}

class ClientObject {

    static int totalClients = 0;
    int clientID;

    String handle; //username

    Socket socket;

    ListenerThread listenerThread;
    WriterThread writerThread; //this is "pretend" for now. 
    
    List<String> file;
    
    //We need a PrintWriter to standardize the printMessage functions:
    PrintWriter consoleOut = new PrintWriter(System.out, true);

    //Inform the writer thread that it's time to do his job:
    public void send(List<String> message) {
        writerThread.write(message); //"Pretend"... 
    }

    public String getHandle() {
        return handle;
    }

    class WriterThread extends Thread {
        //Writer thread waits around until it has something to write
        
        PrintWriter writer; //Connection to socket

        public WriterThread() {
            try {
                writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
            } catch (Exception e) {
                System.err.println("Error : Creating output writer for client:" + clientID);
            }
        }

        public void write(String message) {
            writer.println(message);
            writer.flush();
        }
 
        public void write(List<String> message) {
            //Write to socket outoing connection, hide the protocol details:
            MessageUtils.sendMessage(writer, message);
        }

        public void run() {
            //This is a "pretend" thread for now, since we will always just
            //call the "write()" method directly at the moment.
            //idk if we really need this or not?
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

    class ListenerThread extends Thread {
        //Makes the blocking receive until a message arrives
        
        BufferedReader streamIn; //socket incoming

        public ListenerThread() {
            try {
                streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                System.err.println("Error : Creating streamIn for client: " + clientID);
            }
        }

        
        public void run() {
            //This run method DOES matter
            while (true) {
                try {
                    //Blocking read: (messageUtil will return null if socket closed)
                    List<String> message = MessageUtils.receiveMessage(streamIn);
                    
                    // Socket closed OR Client requested disconnect
                    if (message == null || message.get(0).equals(MessageUtils.DISCONNECT_REQUEST)) {
                        //connection broken (does NOT throw an exception)
                        System.out.println("Client " + clientID + " (" + handle + "): disconnected" );
                        
                        ChatServer.clientDisconnected(clientID);
                        
                        //TODO: Exit and die
                        close();
                        break;
                        
                    }
                    
                    //what type of message did we get?
                    //For now, the first element of the parsed array (from messageUtils),
                    //will tell us:
                    String TAG = message.get(0);
                    if (TAG.equals(MessageUtils.GLOBAL_CHAT)){
                        //Prints to _server_ console:
                        MessageUtils.printChat(consoleOut, message);
   
                        //Send to all connected clients:
                        ChatServer.sendToAllClients(message);
                    }
                   
                    else if (TAG.equals(MessageUtils.FILE)){
                        System.out.println("Receiving file " + message.get(1));
                        file = new ArrayList<String>();
                    }
                    else if (TAG.equals(MessageUtils.FILE_LINE)){
                        if(file != null){
                            file.add(message.get(2));
                            System.out.println(message.get(2));
                        } else {
                            System.err.println("No file created to receive!");
                        }
                    }
                    else if (TAG.equals(MessageUtils.PRINT_FILE)){
                        if (file != null){
                            for (int i = 0; i < file.size(); i++){
                                System.out.println(file.get(i));
                            }
                        } else {
                            System.err.println("No file loaded!");
                        }
                    }
                    else if (TAG.equals(MessageUtils.REQUEST_GLOBAL_WHO)){
                        //Client asked for list of current connected users
                        List<String> handles = ChatServer.getAllUserNames();   
                        writerThread.write(MessageUtils.makeGlobalWhoListMessage(handles));
                    }
                    else if (TAG.equals(MessageUtils.CREATE_NEW_LOBBY)){
                        //client asks to create new lobby, and provided name
                        System.out.println("Received request to create lobby: "+ message.get(1));
                        ChatServer.createNewLobby(message.get(1));
                        ChatServer.joinLobby(message.get(1), ClientObject.this);
                        
                    }
                    else if (TAG.equals(MessageUtils.REQUEST_LOBBY_INFO)){
                        //client asks to create new lobby, and provided name
                        //System.out.println("Received request to create lobby: "+ message.get(1));
                               //Send current lobbies to client:
                      for (Lobby lobby: ChatServer.lobbies){
                        MessageUtils.sendMessage(writerThread.writer, 
                        MessageUtils.makeLobbyInfoMessage(lobby.name, lobby.getUserNames()));
                      }
                        
                    }
                    else if (TAG.equals(MessageUtils.JOIN_LOBBY_REQUEST)){
                        //client requested to join lobby.
                        System.out.println("Received request to join lobby: "+ message.get(1));
                        ChatServer.joinLobby(message.get(1), ClientObject.this);
                        //TODO: Send a accept/reject message
                    }
                    else if (TAG.equals(MessageUtils.LEAVE_LOBBY_REQUEST)){
                        System.out.println(handle + " has requested to leave lobby");
                        ChatServer.leaveLobby(ClientObject.this);
                        
                        
                    }
                    else if (TAG.equals(MessageUtils.SEND_HANDLE)){
                        //client has sent us their new handle:
                        handle = message.get(1);
                        System.out.println("Assigning handle " + handle + " to client " + clientID);
                        
                        ChatServer.sendToAllClients(MessageUtils.makeConnectionMessage(handle));
                        
                    }
                    else {
                        //will add other protocols 
                        System.err.println("Unknown tag! Printing message...");
                        for (String s : message){
                            System.err.println(s + " ");
                        }
                        System.err.println("End of unknown message");
                    }

                } catch (Exception e) {
                    System.out.println("Client " + clientID + " error: " + e);
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
                if (streamIn != null) {
                    streamIn.close();
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        
    }

    public ClientObject(Socket socket) {
        //Given instance it's own ID:
        clientID = totalClients;
        totalClients++;
        handle = "UnknownHandle"; //client will tell us by message

        this.socket = socket;

        listenerThread = new ListenerThread();
        writerThread = new WriterThread();

        //Send the list of online users to the newly connected client:
        //List<String> handles = ChatServer.getAllUserNames();                  
        //MessageUtils.sendMessage(writerThread.writer, MessageUtils.makeGlobalWhoListMessage(handles));
        
 

        //Start threads:
        writerThread.start();
        listenerThread.start();

        //System console message:
        System.out.println("Opened connection from: " + handle);

    }
}
