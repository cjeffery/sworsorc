/*
    1. Add a dialog to ask for an IP and Username (Handle).
    2. Correctly echo back recieved messages to connect clients.
    3. Make chat log.
    4. Display user handle on echoed message!
*/
package chatclient;

import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;

public class ChatClient {
    private Socket socket = null;
    private DataInputStream console = null;
    private DataOutputStream streamOut = null;
    private static String ipAddress;
    private static String username;
    
    public ChatClient(String serverName, int serverPort) throws IOException{
        System.out.println("Connecting! Please Wait!");
        try{
            socket = new Socket(serverName, serverPort);
            System.out.println("Connected: " + socket);
            start();
        }catch(UnknownHostException e){
            System.out.println("Uknown host!");
        }
        //String username = "Blarg";
        String line = "";
        try{
            streamOut.writeUTF(username);
        }catch(IOException e){
            // ERRROR
        }
        while(!line.equals("/close")){
            try{
                line = console.readLine();
                streamOut.writeUTF(line);
                streamOut.flush();
            }catch(IOException e){
                System.out.println("Error sending message!");
            }
        }
    }
    
    public void start() throws IOException{
        console = new DataInputStream(System.in);
        streamOut = new DataOutputStream(socket.getOutputStream());
    }
    public void stop()
    {
        try{
            if (console != null) console.close();
            if (streamOut != null) console.close();
            if (socket != null) socket.close();
        }catch(IOException e){
            System.out.println("Error closing connection!");
        }
    }
    public static void main(String[] args) throws IOException{
        //JFrame frame;
        Object[] possibilities = null;
        ipAddress = (String)JOptionPane.showInputDialog(
                    null,
                    "IP Address of server: ",
                    "IP Address",
                    JOptionPane.CANCEL_OPTION,
                    null,
                    possibilities, "127.0.0.1");
        username = (String)JOptionPane.showInputDialog(
                null,
                "Username",
                "Username",
                JOptionPane.CANCEL_OPTION);
        
        ChatClient client = null;
        if(args.length != 2){
            System.out.println("Usage: ChatClient host port");
            System.out.println("Attempting defualt connection!");
            //client = new ChatClient("76.178.139.129", 25565);
            client = new ChatClient(ipAddress, 25565);
        }else{
            client = new ChatClient(args[0], Integer.parseInt(args[1]));
        }
    }
    
}
