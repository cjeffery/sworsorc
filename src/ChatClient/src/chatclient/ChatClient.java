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
    private BufferedReader console = null;
    private DataOutputStream streamOut = null;
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
                    //Blocking read:
                    String messageIn = streamIn.readLine();
                    System.out.println("(from server) " + messageIn);
                
                }
            } catch (Exception e) {
                System.out.println("Client " + " error: " + e);
            } finally {
                //close();
            }
        }
    }
    
    class WriterThread extends Thread {
        
        PrintWriter writer;
        
        public WriterThread() {
            try {
                writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
            } catch (Exception e) {
                System.err.println("Error : Creating output stream for socket");
            }
 
        }
        
        public void write(String message){ //if we want to programmatically write something
            writer.println(message);
            writer.flush();
        }
        
        public void run() {
            
          while (true){
             try{
                String line = console.readLine();

                writer.println(line);
                writer.flush();
                
            }catch(IOException e){
                System.out.println("Error sending message!");
            }          

            }
           
        }
        
   
    }
    

    
    public ChatClient(String serverName, int serverPort) throws IOException{
        
        System.out.println("Connecting! Please Wait!");
        try{
            socket = new Socket(serverName, serverPort);
            System.out.println("Connected: " + socket);
            //start();
        }catch(UnknownHostException e){
            System.err.println("Error : Uknown host!");
        } catch (ConnectException e){
            System.err.println("Error : Connection Refused!");
        }
        
        
        //Reads from stdin:
        console = new BufferedReader(new InputStreamReader(System.in));
        
        listenerThread = new ListenerThread();
        writerThread = new WriterThread();
        
        //first message is handle:
        writerThread.write(username);
        
        writerThread.start();
        listenerThread.start();
      
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
        
        ClientData clientData = new ClientData();
        ClientDataForm clientDataForm = new ClientDataForm(clientData);
        
        System.out.println("Launching Login Dialog");
        
        clientDataForm.setVisible(true);
        
        System.out.println("Login Dialog Finished");
        
        System.out.flush(); // I was losing input is the JDialog crashed
        
        ipAddress = clientData.getIPAddress();
        username = clientData.getUsername();

        ChatClient client = null;
        if(args.length != 2){
            System.out.println("Usage: ChatClient host port");
            System.out.println("Attempting defualt connection!");

            client = new ChatClient(ipAddress, 25565);
        }else{
            client = new ChatClient(args[0], Integer.parseInt(args[1]));
        }
        
        
    }
    
}
