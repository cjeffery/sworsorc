package chatclient;

import java.net.*;
import java.io.*;

public class ChatServerThread extends Thread{
    private Socket socket = null;
    private ChatServer server = null;
    private int ID = -1;
    private DataInputStream streamIn = null;
    
    public ChatServerThread(ChatServer _server, Socket _socket){
        server = _server; 
        socket = _socket; 
        ID = socket.getPort();
    }
    public void run(){
        System.out.println("Server Thread " + ID + " active!");
        while(true){
            try{
                System.out.println(streamIn.readUTF());
            }catch (IOException e){
            }
            }
    }
    public void open() throws IOException{
        streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }
    public void close() throws IOException{
        if(socket != null) socket.close();
        if(streamIn != null) streamIn.close();
    }
}
