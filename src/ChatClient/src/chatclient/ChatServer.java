package chatclient;

import java.net.*;
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ChatServer  {
    
    private static List<ClientObject> clientObjects; //"Packaged sockets"
    private static int DEFAULT_PORT = 25565;

    public static void sendToAllClients(String handle, String message){
        for (ClientObject client : clientObjects){
            if (!client.handle.equals(handle)){
                client.send(handle, message);
            }
        }
    }
    
    
    public static void main(String args[]) {
        clientObjects = new ArrayList<>();
        
        
        System.out.println("Server starting. . .");

        System.out.println("Binding port " + DEFAULT_PORT + " . . .");
    
        try {
            System.out.println("Server started (" + InetAddress.getLocalHost() + ")");
        }
        catch (Exception e){
            System.out.println(e);
        }
        
        ServerSocket listen = null;
        try {
            listen = new ServerSocket(DEFAULT_PORT);
        } catch (Exception e) {
            System.err.println("Error : While creating serversocket");
        }

        //This thread spins off new client connections:
        while (true) {
            try {
                System.err.println("Waiting for next client...");
                Socket socket = listen.accept(); //Get socket
                
                //The constructor of ClientObject will create the new threads:
                clientObjects.add(new ClientObject(socket));
            } catch (Exception e) {

            }
        }

    }

}

class ClientObject {

    static int totalClients = 0;
    int clientID; 
    
    String handle; //username
    
    Socket socket;
    
    ListenerThread listenerThread; 
    WriterThread writerThread; //this is "pretend" for now. 
    
    boolean echo = true; //Should we echo back to client?
    
    public void send(String handle, String message){
            writerThread.write(handle + ": " + message);
    }

    class WriterThread extends Thread {
        
        PrintWriter writer;
        public WriterThread() {
            try {
                writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
            } catch (Exception e) {
                System.err.println("Error : Creating output writer for client:" + clientID);
            }
        }
        
        public void write(String message){
            writer.println(message);
            writer.flush();         
        }
        
        public void run() {
            //This is a "pretend" thread for now, since we will always just
            //call the "write()" method directly at the moment.
           
        }
        
   
    }
    
    class ListenerThread extends Thread {
        BufferedReader streamIn; 
        
        public ListenerThread() {
            try {
                streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception e) {
                System.err.println("Error : Creating streamIn for client: " + clientID);
            }
        }
        
        public String getHandle(){
            //For now, just read a string and assume it's the handle!
            String handle = null;
            try {
                 handle = streamIn.readLine();
                } catch (Exception e) {
                     System.err.println("Error : Reading client handle");
                }
                
            return handle;
        }

        public void run() {
            try {
                while (true) {
                    //Blocking read:
                    String messageIn = streamIn.readLine();
                    
                    //Prints to server display:
                    System.out.println(handle + ": " + messageIn);
                    
                    if (echo){ //repeat message to client:
                        send("(echo)" + handle, messageIn);
                                 
                    }
                    
                    //Send to all connected clients:
                    ChatServer.sendToAllClients(handle, messageIn);
                   
                }
            } catch (Exception e) {
                System.out.println("Client " + clientID + " error: " + e);
            } finally {
                //close();
            }
        }
    }

    public ClientObject(Socket socket) {
        //Given instance it's own ID:
        clientID = totalClients;
        totalClients++;
        
        this.socket = socket;
        
        
        listenerThread = new ListenerThread();
        writerThread = new WriterThread();
        
        //For now, assume first message is handle:
        this.handle = listenerThread.getHandle();
        
        //Start threads:
        
        writerThread.start();    
        listenerThread.start();
        
        System.out.println("Opened connection from: " + handle);

    }
}
