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
    protected static int DEFAULT_PORT = 25565;
    protected static String DEFAULT_IP = "76.178.139.129";

    //Legacy method, I think this will be replaced soon:
    public static void sendToAllClients(String handle, String message) {
        for (ClientObject client : clientObjects) {
            if (!client.handle.equals(handle)) {
                client.send(handle, message);
            }
        }
    }
    
    //Forward the message to all ClientObjects, which will send to their Clients:
    public static void sendToAllClients(List<String> message) {
        for (ClientObject client : clientObjects) {
             client.send(message);           
        }
    }

    public static void main(String args[]) {
        clientObjects = new ArrayList<>();

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

class ClientObject {

    static int totalClients = 0;
    int clientID;

    String handle; //username

    Socket socket;

    ListenerThread listenerThread;
    WriterThread writerThread; //this is "pretend" for now. 
    
    //We need a PrintWriter to standardize the printMessage functions:
    PrintWriter consoleOut = new PrintWriter(System.out, true);

    //Legacy method: should be replaced with protocol:
    public void send(String handle, String message) {
        //write the literal string:
        writerThread.write(handle + ": " + message);
    }
    
    //Inform the writer thread that it's time to do his job:
    public void send(List<String> message) {
        writerThread.write(message);
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

        
        //Legacy method: should be replaced with a standard protocol:
        public void sendConnectionList() {
            List<String> handles = new ArrayList<>();
            for (ClientObject obj : ChatServer.clientObjects) {
                handles.add(obj.getHandle());
            }
            MessageUtils.sendMessage(writer, handles);
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
            } catch (Exception e) {
                System.err.println("Error : Creating streamIn for client: " + clientID);
            }
        }

        //legacy: replace with protocol:
        public String getHandle() {
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
            //This run method DOES matter
            while (true) {
                try {
                    //Blocking read: (messageUtil will return null is socket closed)
                    List<String> message = MessageUtils.receiveMessage(streamIn);
                    
                    if (message == null) {
                        //connection broken (NOT an exception)
                        System.out.println("Client " + clientID + " (" + handle + "): disconnected" );
                        
                        ChatServer.sendToAllClients(MessageUtils.makeDisconnectMessage(handle));
                        
                        close();
                        break;
                        
                    }
                    
                    //what type of message did we get?
                    //For now, the first element of the parsed array (from messageUtils),
                    //will tell us:
                    if (message.get(0).equals(MessageUtils.CHAT)){
                        //Prints to _server_ console:
                        MessageUtils.printChat(consoleOut, message);
   
                        //Send to all connected clients:
                        ChatServer.sendToAllClients(message);
                    }
                    else {
                        //will add other protocols 
                        System.err.println("Unknown tag!");
                    }

                } catch (Exception e) {
                    System.out.println("Client " + clientID + " error: " + e);
                    close();
                    break;

                } 

            }

            //close();
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

    }

    public ClientObject(Socket socket) {
        //Given instance it's own ID:
        clientID = totalClients;
        totalClients++;

        this.socket = socket;

        listenerThread = new ListenerThread();
        writerThread = new WriterThread();

        //For now, assume first message is handle (blocking):
        this.handle = listenerThread.getHandle(); //Legacy! replace with protocol!

        //next: send list of handles to client:
        writerThread.sendConnectionList(); //Legacy! replace with protocol!

        //announce the new client's connection to all old clients:
        ChatServer.sendToAllClients(MessageUtils.makeConnectionMessage(handle));
        //ChatServer.sendToAllClients(handle, "(Joined)");

        //Start threads:
        writerThread.start();
        listenerThread.start();

        //System console message:
        System.out.println("Opened connection from: " + handle);

    }
}
