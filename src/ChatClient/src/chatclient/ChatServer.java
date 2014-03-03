package chatclient;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ChatServer implements Runnable {
    private ServerSocket server = null;
    private Thread thread = null;
    private ChatServerThread client = null;
    private ArrayList<String> usernames;
    
    public ChatServer(int port)
    {
        try{
            System.out.println("Binding port " + port + " . . .");
            server = new ServerSocket(port);
            System.out.println("Server started (" + InetAddress.getLocalHost() + ")");
            System.out.println("Server started ( " + server + " )");
            usernames = new ArrayList<>();
        }catch(IOException e){
            System.out.println(e);
        }
    }
    public void run(){
        while (thread != null)
        {
            try{
                System.out.println("Waiting for client . . .");
                addThread(server.accept());
            }catch(IOException e){
                System.out.println(e);
            }
        }
    }
    public void addThread(Socket socket){
        System.out.println("Client jioned: " + socket);
        client = new ChatServerThread(this, socket);
        try{
            client.open();
            client.start();
        }catch(IOException e){
            System.out.println(e);
        }
    }
    public void start(){
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
        this.run();
    }
    public void stop(){ 
        if(thread != null){
            thread = new Thread(this);
            thread.stop();
            thread = null;
        }
    }
    public static void main(String args[]){ 
        ChatServer server = new ChatServer(25565);
        server.start();
    }
}
