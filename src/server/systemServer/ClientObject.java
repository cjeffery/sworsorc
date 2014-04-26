/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package systemServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Manages the connection for a client connected to server
 */
public class ClientObject {

    // User data
    final private int clientID; // Unique ID of client connection
    private String handle; // Username, may want to change later, so not final

    // Connection this object is attached to
    final private Socket socket;
    final private ArrayBlockingQueue<List<Object>> messageQueue;


    // Remote Send/Recieve threads
    final private ServerListenerThread listenerThread;
    final private ServerWriterThread writerThread;

    private List<String> file;

    // Lobby client is a part of, null if not in a lobby
    private Lobby currentLobby = null;

    // Server console output
    final private PrintWriter consoleOut; 
    
    private boolean clientKilled = false;
    
    /* CONSTRUCTOR */
    
    /**
     * ClientObject constructor
     * Call {@link #startClient startClient()} to activate the object
     * @param sock The Socket associated with a single client connection
     * @param clientID The unique ID of the Client
     */
    public ClientObject(Socket sock, int clientID ) {
        // Give each instance it's own ID
        this.clientID = clientID;
        
        // Default handle
        this.handle = "UnknownHandle";

        // Set socket to incoming connection
        this.socket = sock;

        // Initialize threads/streams
        this.consoleOut  = new PrintWriter(System.out, true);
        this.listenerThread = new ServerListenerThread();
        this.writerThread = new ServerWriterThread();
        this.messageQueue = new ArrayBlockingQueue<>( 30, true ); // 30 slots, FIFO access
    }
    
    /* PUBLIC METHODS */
   
    /**
     * Send a message to client
     * <p>
     * This is just a public wrapper for {@link #write write()}
     * @param message Message to send
     */
    public void send(List<String> message) {
        write(MessagePhoenix.stringToObject(message));
    }
    
    /**
     * Send a message to client
     * Generic parameters
     * @param message First parameter is assumed to be tag
     */
    public void send(Object... message) {
        write(MessagePhoenix.createMessage(message));
    }
    
    
    /* UTILITY METHODS */
    
    /**
     * Writes to socket outgoing connection, hides the protocol details
     * @param message Message to send
     */
    private void write(List<Object> message ) { 
        
        try {
            messageQueue.put(message);
        } catch (InterruptedException ex) {
            System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                    + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
            ex.printStackTrace();           
        }
    }    

    /**
     * Checks if client is connected to server
     * <p>
     * @author Christopher Goes
     * @return boolean True if connected, False if not
     */
    private boolean isConnected() {
        return this.socket != null && !(this.socket.isClosed()) && this.socket.isConnected();
    }
    
    /* LISTENERTHREAD */
    
    /**
     * Makes the blocking receive until a message arrives
     */
    private class ServerListenerThread extends Thread {

        private ObjectInputStream streamIn = null;

        private boolean killed = false;
        
        /**
         * Constructor for ListenerThread, creates stream and object
         * NOTE: Socket must be set before creating a ListenerThread object!
         */
        private ServerListenerThread() {
           createStream();
        }
        
        /**
         * Creates input stream, and connects to socket
         * <p>
         * NOTE: Must be called when creating thread!
         * <p>
         * @author Christopher Goes
         */
        private void createStream() {
            if (isConnected()) {
                try {
                    this.streamIn = new ObjectInputStream(socket.getInputStream());
                } catch (IOException ex) {
                    System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                            + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                    ex.printStackTrace();
                }
            } else {
                System.err.println("Error creating listen stream: socket isn't connected!");
            }
        }
        
        // wrapper
        private List<Object> recieveMessage() {
            return MessagePhoenix.recieveMessage(this.streamIn);
        }
        
        private void disconnect() {
            consoleOut.println("Client " + clientID + " (" + handle + "): disconnected");
            NetworkServer.clientDisconnected(clientID);
        }
        
        private void killThread() {
            this.killed = true;
        }
        
        @Override
        public void run() {
            
            // Temporary containers
            String TAG = null;
            List<Object> rawMessage;
            List<String> stringMessage = new ArrayList<String>();
            
            while (!killed) {
                
                rawMessage = recieveMessage();
                
                if (rawMessage == null ) { //connection broken (does NOT throw an exception)
                    killThread();                   
                }
                if ( rawMessage.get(0).getClass().equals(String.class)) {
                    TAG = (String) rawMessage.get(0);
                } else {
                    System.err.println("Non-String TAG recieved!");
                    killThread();
                }
                
                
                if( TAG == null ) {
                    System.err.println("Tag is null!");
                    killThread();
                } else if ( TAG.equals(MessagePhoenix.DISCONNECT_REQUEST) ) {
                   killThread();
                }
                
                rawMessage = rawMessage.subList(1, rawMessage.size());
                if ( rawMessage.getClass().equals(stringMessage.subList(0,0).getClass())) {
                    stringMessage = MessagePhoenix.objectToString(rawMessage);
                    for (String s : stringMessage){
                        System.out.println("Received strings: " + s);
                    }
          
                } else {
                    System.err.println("Unknown object recieved!");
                    killThread();
                }
                if (TAG.equals(MessagePhoenix.GLOBAL_CHAT)) {
                    consoleOut.println(stringMessage); // Server console print                   
                    NetworkServer.sendToAllClients(stringMessage); // Global broadcast
                } else if (TAG.equals(MessagePhoenix.FILE)) {
                    consoleOut.println("Receiving file " + rawMessage.get(0));
                    file = new ArrayList<String>();
                } else if (TAG.equals(MessagePhoenix.FILE_LINE)) {
                    if (file != null) {
                        file.add(stringMessage.get(1));
                        consoleOut.println(rawMessage.get(1));
                    } else {
                        System.err.println("No file created to receive!");
                    }
                } else if (TAG.equals(MessagePhoenix.PRINT_FILE)) {
                    if (file != null) {
                        for (String file1 : file) {
                            consoleOut.println(file1);
                        }
                    } else {
                        System.err.println("No file loaded!");
                    }
                } else if (TAG.equals(MessagePhoenix.REQUEST_GLOBAL_WHO)) {
                    //Client asked for list of current connected users
                    List<String> handles = NetworkServer.getAllUserNames();
                    send(MessagePhoenix.GLOBAL_WHO_LIST, handles);
                } else if (TAG.equals(MessagePhoenix.REQUEST_BEGIN_GAME)) {
                    //Client asked agree to start the game
                    //TODO: Voting here? Right now, we start when any single client requests
                    
                    if (currentLobby == null){
                        send(MessagePhoenix.NAG,
                                "You requested to start the game, but you aren't even in a lobby!");
                    }
                    else {
                        
                        consoleOut.println("Client " + handle +
                                " requested to start game in lobby " + currentLobby.getName());
                        
                        currentLobby.beginGame();
                        currentLobby.sendToEntireLobby(MessagePhoenix.GAME_BEGUN);
                    }
                    
                } else if (TAG.equals(MessagePhoenix.CREATE_NEW_LOBBY_REQUEST)) {
                    //client asks to create new lobby, and provided name
                    
                    String requestedLobbyName = stringMessage.get(0);
                    consoleOut.println("Received request to create lobby: "
                            + requestedLobbyName); 
                    
                    if (NetworkServer.createNewLobby(stringMessage.get(0))) {
                        
                        NetworkServer.joinLobby(stringMessage.get(0), ClientObject.this);
                        send(MessagePhoenix.APROVE_NEW_LOBBY_REQUEST);
                    } else {
                        send(MessagePhoenix.DENY_NEW_LOBBY_REQUEST);
                    }
                    
                } else if (TAG.equals(MessagePhoenix.REQUEST_LOBBY_INFO)) {
                    //client asks to create new lobby, and provided name
                    //Send current lobbies to client:
                    List<String> lobbyNames = NetworkServer.getLobbyNames();
                    for (String lobby : lobbyNames) {
                        send(MessagePhoenix.LOBBY_INFO,
                                NetworkServer.getLobbyUsers(lobby) );
                    }
                    
                } else if (TAG.equals(MessagePhoenix.JOIN_LOBBY_REQUEST)) {
                    //client requested to join lobby.
                    consoleOut.println("Received request to join lobby: " + rawMessage.get(1));
                    NetworkServer.joinLobby(stringMessage.get(0), ClientObject.this);
                    //TODO: implement/send an accept/reject message
                } else if (TAG.equals(MessagePhoenix.LEAVE_LOBBY_REQUEST)) {
                    consoleOut.println(handle + " has requested to leave lobby");
                    NetworkServer.leaveLobby(ClientObject.this);
                    currentLobby = null;
                    
                } else if (TAG.equals(MessagePhoenix.SEND_HANDLE)) {
                    //client has sent us their new handle:
                    handle = stringMessage.get(0);
                    consoleOut.println("Assigning handle " + handle
                            + " to client " + clientID);
                    stringMessage.add(0, MessagePhoenix.CONNECT_ANNOUNCEMENT); // TODO: makeshift for now
                    NetworkServer.sendToAllClients(stringMessage);
                    
                } else if (TAG.equals(MessagePhoenix.ADD_UNIT)) { // TODO: update these
                    NetworkServer.sendToAllClients(stringMessage);
                } else if (TAG.equals(MessagePhoenix.UPDATE_UNIT)) {
                    NetworkServer.sendToAllClients(stringMessage);
                } else if (TAG.equals(MessagePhoenix.YIELD_TURN)) {
                    consoleOut.println("Client " + handle
                            + " (id  " + clientID + " ) yielded turn");
                    
                    if (currentLobby == null) {
                        //client requested to change turns, but it's not their turn!
                        send(MessagePhoenix.NAG, "You requested to yield your turn, but you're not even in lobby!");
                    }
                    else if (currentLobby.current.getClientID() != clientID) {
                        //client requested to change turns, but it's not their turn!
                        send(MessagePhoenix.NAG, "Requested to yield turn, but it's not your turn!");
                    } else {
                        currentLobby.advanceGameTurn(); //tell lobby handler to advance game turn
                    }
                    
                } else {
                    // TODO: will add other protocols
                    System.err.println("Unknown tag! Printing message...");
                    System.out.println("Tag is : " + TAG);
                    for (String s : stringMessage ) {
                        System.err.println(s + " ");
                    }
                    System.err.println("End of unknown message");
                }
            }
            close();

        }

        public void close() {
            if( isConnected()) {
                disconnect();
            }
            try {
                if (!(socket.isClosed())) {
                    socket.close();
                }
                if (streamIn != null) {
                    streamIn.close();
                    streamIn = null;
                }
                
            } catch (IOException e) {
                System.err.println(e);
            }
        }

    } // end ServerListenerThread

    // WRITER THREAD //
    
    private class ServerWriterThread extends Thread {

        private ObjectOutputStream writer = null;
        
        private boolean killed = false;
               
        private ServerWriterThread() {
            setWriter();          
        }
 
        /**
         * Initializes writer with a new stream.
         * <p>
         * NOTE: Socket must be set before calling this!
         * <p>
         * @author Christopher Goes
         */
        private void setWriter() {
            if (isConnected()) {
                try {
                    this.writer = new ObjectOutputStream(socket.getOutputStream());
                    writer.flush();
                } catch (IOException ex) {
                    System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                            + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                    ex.printStackTrace();
                }
            }
        }
        
        /**
         * Marks thread for death, causing it to close, return, and die
         * <p>
         * @author Christopher Goes
         */
        private void killThread() {
            this.killed = true;
        }
        
        private void sendMessage(String tag, Object... message) {
            try {
                MessagePhoenix.sendMessage(this.writer, tag, message);
            } catch (IOException ex) {
                System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                        + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                ex.printStackTrace();
            }
        }
        
        @Override
        public void run() {
            List<Object> message = null;
            

            while (!killed) {
                try {
                    message = messageQueue.take();
                } catch (InterruptedException ex) {
                    System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                            + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                    ex.printStackTrace();
                    killThread();
                }
                if (message != null ) { // assume first object is tag
                    
                    sendMessage( message.get(0).toString(), new ArrayList(message.subList(1, message.size())));
                } else {
                    System.err.println("Null message!");
                }
            }
            close();
        }
        
        /**
         * Always run this before returning from {@link #run run}!
         */
        private void close() {
            try {
                if (!(isConnected())) {
                    if (writer != null) {
                        writer.close();
                        writer = null;
                    }
                } else {
                    // TODO: disconnect
                    if (writer != null) {
                        writer.close();
                        writer = null;
                    }

                }
            } catch (IOException ex) {
                System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                        + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                ex.printStackTrace();
            }
        }
        
    } // end ServerWriterThread  

    
    /**
     * Starts the ClientObject, opening the connection
     * 
     * @author Christopher Goes
     */
    public void start() {
        
        listenerThread.start();
        writerThread.start();
        consoleOut.println("Opened connection from client " + clientID + " at address " + socket.getInetAddress());
    }
    
    public void killClient() {
        this.clientKilled = true;
        listenerThread.killThread();
        writerThread.killThread();
    }
    /* GETTERS & SETTERS */
    
    /**
     * Gets the handle of client object
     * @return handle Username of client
     * @author Christopher Goes
    */
    public String getHandle() {
        return handle;
    }

    /**
     * Gets the clientID of client object
     * @return clientID Unique ID of client
     */
    public int getClientID() {
        return clientID;
    }
    
    /**
     * Sets current lobby of client object
     * @param lobby
     */
    public void setCurrentLobby(Lobby lobby) {
        this.currentLobby = lobby;
    }

} // end class
