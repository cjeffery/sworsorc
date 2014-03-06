package chatclient;

import java.net.*;
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ChatServer {

    protected static List<ClientObject> clientObjects; //"Packaged sockets"
    protected static int DEFAULT_PORT = 25565;

    public static void sendToAllClients(String handle, String message) {
        for (ClientObject client : clientObjects) {
            if (!client.handle.equals(handle)) {
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
        } catch (Exception e) {
            System.out.println(e);
        }

        ServerSocket listen = null;
        try {
            listen = new ServerSocket(DEFAULT_PORT);
        } catch (Exception e) {
            System.err.println("Error : While creating serversocket");
        }

        //Spins off new client connections:
        while (true) {
            try {
                System.err.println("Waiting for next client...");
                Socket socket = listen.accept(); //Get socket (blocking)

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

    public void send(String handle, String message) {
        writerThread.write(handle + ": " + message);
    }

    public String getHandle() {
        return handle;
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

        public void write(String message) {
            writer.println(message);
            writer.flush();
        }

        public void sendConnectionList() {
            List<String> handles = new ArrayList<>();
            for (ClientObject obj : ChatServer.clientObjects) {
                handles.add(obj.getHandle());
            }

            MessageUtils.sendArray(writer, handles);
        }

        public void run() {
            //This is a "pretend" thread for now, since we will always just
            //call the "write()" method directly at the moment.

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

        BufferedReader streamIn;

        public ListenerThread() {
            try {
                streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception e) {
                System.err.println("Error : Creating streamIn for client: " + clientID);
            }
        }

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

            while (true) {
                try {
                    //Blocking read:
                    String messageIn = null;
                    messageIn = streamIn.readLine();

                    if (messageIn == null) {
                        //connection broken (NOT an exception)
                        System.out.println("Client " + clientID + " (" + handle + "): disconnected" );
                        ChatServer.sendToAllClients(handle, "(disconnected)");
                        close();
                        break;
                    }
                    //Prints to server console:
                    System.out.println(handle + ": " + messageIn);

                    if (echo) { //repeat message to client:
                        send("(echo)" + handle, messageIn);

                    }

                    //Send to all connected clients:
                    ChatServer.sendToAllClients(handle, messageIn);

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
        this.handle = listenerThread.getHandle();

        //next: send list of handles to client:
        writerThread.sendConnectionList();

        ChatServer.sendToAllClients(handle, "(Joined)");

        //Start threads:
        writerThread.start();
        listenerThread.start();

        System.out.println("Opened connection from: " + handle);

    }
}
