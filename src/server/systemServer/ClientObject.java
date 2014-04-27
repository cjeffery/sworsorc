/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package systemServer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the connection for a client connected to server
 */
public class ClientObject {

    private static int totalClients = 0;
    final private int clientID;

    private String handle; //username

    final private Socket socket;

    final private ListenerThread listenerThread;

    private PrintWriter writer = null;
    private List<String> file;

    private Lobby currentLobby = null; //Clients need to be able to talk just to their lobbies

    //We need a PrintWriter to standardize the printMessage functions:
    private PrintWriter consoleOut = null;
    
    /* CONSTRUCTOR */
    
    /**
     * ClientObject constructor
     * Call {@link #startClient startClient()} to activate the object
     * @param sock The Socket associated with a single client connection
     */
    public ClientObject(Socket sock) {
        //Given instance it's own ID:
        clientID = totalClients;
        totalClients++;
        handle = "UnknownHandle"; //client will tell us by message

        this.socket = sock;

        consoleOut  = new PrintWriter(System.out, true);
        listenerThread = new ListenerThread();
    }
    
    /* PUBLIC METHODS */
    
    /**
     * Starts the ClientObject, opening the connection
     * 
     * @author Christopher Goes
     */
    public void startClient() {
        setWriter();
        listenerThread.start();
        System.out.println("Opened connection from client " + clientID + " at address " + socket.getInetAddress());
    }
    
    /**
     * Send a message to client
     * <p>
     * This is just a public wrapper for {@link #write write()}
     * @param message Message to send
     */
    public void send(List<String> message) {
        write(message);
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

    /**
     * Creates a new writer stream off of the socket
     * NOTE: Socket must be set before calling this method!
     * @author Christopher Goes
     */
    public void setWriter() {
        if( socket == null ) {
            System.err.println("Error in setWriter: null socket!");
            return;
        }
        try {
            writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            System.err.println("Error : Creating output writer for client:" 
                    + clientID + "\nException: " + e );
        }
    }
    
    /* UTILITY METHODS */
    
    /**
     * Writes to socket outgoing connection, hides the protocol details
     * @param message Message to send
     */
    private void write(List<String> message) {    
        MessageUtils.sendMessage(writer, message);
    }    
    
    /* LISTENERTHREAD */
    
    /**
     * Makes the blocking receive until a message arrives
     */
    private class ListenerThread extends Thread {

        BufferedReader streamIn; //socket incoming

        /**
         * Constructor for ListenerThread, creates stream and object
         * NOTE: Socket must be set before creating a ListenerThread object!
         */
        private ListenerThread() {
            if( socket == null ) {
                System.err.println("Error in ListenerThread constructor: null socket!");
                return;
            }
            try {
                streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                System.err.println("Error : Creating streamIn for client: " + clientID);
            }
        }

        @Override
        public void run() {
            List<String> message;
            while (true) {
                try {
                    //Blocking read: (messageUtil will return null if socket closed)
                    message = MessageUtils.receiveMessage(streamIn);

                    // Socket closed OR Client requested disconnect
                    if (message == null || message.get(0).equals(MessageUtils.DISCONNECT_REQUEST)) {
                        //connection broken (does NOT throw an exception)
                        System.out.println("Client " + clientID + " (" + handle + "): disconnected");

                        NetworkServer.clientDisconnected(clientID);

                        close();
                        return;

                    }

                    //what type of message did we get?
                    //For now, the first element of the parsed array (from messageUtils),
                    //will tell us:
                    String TAG = message.get(0);
                    if (TAG.equals(MessageUtils.GLOBAL_CHAT)) {
                        //Prints to _server_ console:
                        MessageUtils.printChat(consoleOut, message);

                        //Send to all connected clients:
                        NetworkServer.sendToAllClients(message);
                    } else if (TAG.equals(MessageUtils.FILE)) {
                        System.out.println("Receiving file " + message.get(1));
                        file = new ArrayList<String>();
                    } else if (TAG.equals(MessageUtils.FILE_LINE)) {
                        if (file != null) {
                            file.add(message.get(2));
                            System.out.println(message.get(2));
                        } else {
                            System.err.println("No file created to receive!");
                        }
                    } else if (TAG.equals(MessageUtils.PRINT_FILE)) {
                        if (file != null) {
                            for (String file1 : file) {
                                System.out.println(file1);
                            }
                        } else {
                            System.err.println("No file loaded!");
                        }
                    } else if (TAG.equals(MessageUtils.REQUEST_GLOBAL_WHO)) {
                        //Client asked for list of current connected users
                        List<String> handles = NetworkServer.getAllUserNames();
                        write(MessageUtils.makeGlobalWhoListMessage(handles));
                    } else if (TAG.equals(MessageUtils.REQUEST_BEGIN_GAME)) {
                        //Client asked agree to start the game
                        //TODO: Voting here? Right now, we start when any single client requests

                        if (currentLobby == null){
                            MessageUtils.sendMessage(writer, 
                              MessageUtils.makeNagMessage("You requested to start the game, but you aren't even in a lobby!"));
                        }
                        else {

                            System.out.println("Client " + handle + 
                                    " requested to start game in lobby " + currentLobby.getName());

                            currentLobby.beginGame();
                            currentLobby.sendToEntireLobby(MessageUtils.makeGameBegunMessage());
                        }

                    } else if (TAG.equals(MessageUtils.CREATE_NEW_LOBBY_REQUEST)) {
                        //client asks to create new lobby, and provided name

                        String requestedLobbyName = message.get(1);
                        System.out.println("Received request to create lobby: " 
                                + requestedLobbyName);

                        if (NetworkServer.createNewLobby(message.get(1))) {
                            
                            NetworkServer.joinLobby(message.get(1), ClientObject.this);
                            MessageUtils.sendMessage(writer,
                                    MessageUtils.makeNewLobbyRequestAcceptedMessage());
                        } else {
                            MessageUtils.sendMessage(writer,
                                    MessageUtils.makeNewLobbyRequestDeniedMessage());
                        }

                    } else if (TAG.equals(MessageUtils.REQUEST_LOBBY_INFO)) {
                        //client asks to create new lobby, and provided name
                        //Send current lobbies to client:
                        // TODO: more specific info, sepearate messages
                        List<String> lobbyNames = NetworkServer.getLobbyNames();
                        for (String lobby : lobbyNames) {
                            MessageUtils.sendMessage(writer,
                                    MessageUtils.makeLobbyInfoMessage(lobby, NetworkServer.getLobbyUsers(lobby) ));
                        }

                    } else if (TAG.equals(MessageUtils.JOIN_LOBBY_REQUEST)) {
                        //client requested to join lobby.
                        System.out.println("Received request to join lobby: " + message.get(1));
                        NetworkServer.joinLobby(message.get(1), ClientObject.this);
                        //TODO: implement/send an accept/reject message
                    } else if (TAG.equals(MessageUtils.LEAVE_LOBBY_REQUEST)) {
                        System.out.println(handle + " has requested to leave lobby");
                        NetworkServer.leaveLobby(ClientObject.this);
                        currentLobby = null;

                    } else if (TAG.equals(MessageUtils.SEND_HANDLE)) {
                        //client has sent us their new handle:
                        handle = message.get(1);
                        System.out.println("Assigning handle " + handle 
                                + " to client " + clientID);

                        NetworkServer.sendToAllClients(MessageUtils.makeConnectionMessage(handle));

                    } else if (TAG.equals(MessageUtils.ADD_UNIT)) {
                        NetworkServer.sendToAllClients(message);
                    } else if (TAG.equals(MessageUtils.UPDATE_UNIT)) {
                         NetworkServer.sendToAllClients(message);
                    } else if (TAG.equals(MessageUtils.YIELD_TURN)) {
                        //client has sent us their new handle:
                        //handle = message.get(1);
                        System.out.println("Client " + handle 
                                + " (id  " + clientID + " ) yielded turn");

                        
                        if (currentLobby == null) {
                            //client requested to change turns, but it's not their turn!
                            MessageUtils.sendMessage(writer,
                                    MessageUtils.makeNagMessage("You requested to yield your turn, but you're not even in lobby!"));
                        } 
                        else if (currentLobby.current.getClientID() != clientID) {
                            //client requested to change turns, but it's not their turn!
                            MessageUtils.sendMessage(writer,
                                    MessageUtils.makeNagMessage("Requested to yield turn, but it's not your turn!"));
                        } else {
                            currentLobby.advanceGameTurn(); //tell lobby handler to advance game turn
                        }
                        //NetworkServer.sendToAllClients(MessageUtils.makeConnectionMessage(handle));

                    } else if (TAG.equals(MessageUtils.CHANGE_PHASE)){
                        NetworkServer.sendToAllClients(message);
                    } else{
                        //will add other protocols 
                        System.err.println("Unknown tag! Printing message...");
                        for (String s : message) {
                            System.err.println(s + " ");
                        }
                        System.err.println("End of unknown message");
                    }

                } catch (Exception e) {
                    System.out.println("Client " + clientID + " error: " + e);
                    close();
                    return;

                }
            }

        }

        public void close() {
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

    } // end class


} // end class
