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
    protected int clientID; // TODO: put into a get method

    private String handle; //username

    private Socket socket;

    private ListenerThread listenerThread;
    private WriterThread writerThread; //this is "pretend" for now. 

    List<String> file;

    private Lobby currentLobby = null; //Clients need to be able to talk just to their lobbies

    //We need a PrintWriter to standardize the printMessage functions:
    private PrintWriter consoleOut = new PrintWriter(System.out, true);

    /**
     * ClientObject constructor
     * @param socket The Socket associated with a single client connection
     */
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
        // TODO: remove thread starting from constructor?
        writerThread.start(); // TODO: evaluate whether writerThread is needed
        listenerThread.start(); 

        //System console message:
        System.out.println("Opened connection from: " + handle);

    }
    
    //Inform the writer thread that it's time to do his job:
    public void send(List<String> message) {
        writerThread.write(message); //"Pretend"... 
    }

    public String getHandle() {
        return handle;
    }

    public void setCurrentLobby(Lobby lobby) {
        this.currentLobby = lobby;
    }

    private class WriterThread extends Thread {
        //Writer thread waits around until it has something to write
        //If we need it, we can use a synchronized message queue to handle requests to write messages
        PrintWriter writer; //Connection to socket

        private WriterThread() {
            try {
                writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
            } catch (IOException e) {
                System.err.println("Error : Creating output writer for client:" + clientID);
            }
        }

        public void write(List<String> message) {
            //Write to socket outoing connection, hide the protocol details:
            MessageUtils.sendMessage(writer, message);
        }

        @Override
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
            } catch (IOException e) {
                System.err.println("Error closing writerthread!\nException: " + e );
            }
        }
    }

    private class ListenerThread extends Thread {
        //Makes the blocking receive until a message arrives

        BufferedReader streamIn; //socket incoming

        private ListenerThread() {
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

                        //TODO: Exit and die
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
                        writerThread.write(MessageUtils.makeGlobalWhoListMessage(handles));
                    } else if (TAG.equals(MessageUtils.REQUEST_BEGIN_GAME)) {
                        //Client asked agree to start the game
                        //TODO: Voting here? Right now, we start when any single client requests

                        if (currentLobby == null){
                            MessageUtils.sendMessage(writerThread.writer, 
                              MessageUtils.makeNagMessage("You requested to start the game, but you aren't even in a lobby!"));
                        }
                        else {

                            System.out.println("Client " + handle + " requested to start game in lobby " + currentLobby.name);

                            currentLobby.beginGame();
                            currentLobby.sendToEntireLobby(MessageUtils.makeGameBegunMessage());
                        }

                    } else if (TAG.equals(MessageUtils.CREATE_NEW_LOBBY_REQUEST)) {
                        //client asks to create new lobby, and provided name

                        String requestedLobbyName = message.get(1);
                        System.out.println("Received request to create lobby: " + requestedLobbyName);

                        if (NetworkServer.canCreateNewLobby(requestedLobbyName)) {
                            NetworkServer.createNewLobby(message.get(1));
                            NetworkServer.joinLobby(message.get(1), ClientObject.this);
                            MessageUtils.sendMessage(writerThread.writer,
                                    MessageUtils.makeNewLobbyRequestAcceptedMessage());
                        } else {
                            MessageUtils.sendMessage(writerThread.writer,
                                    MessageUtils.makeNewLobbyRequestDeniedMessage());
                        }

                    } else if (TAG.equals(MessageUtils.REQUEST_LOBBY_INFO)) {
                        //client asks to create new lobby, and provided name
                        //System.out.println("Received request to create lobby: "+ message.get(1));
                        //Send current lobbies to client:
                        for (Lobby lobby : NetworkServer.lobbies) {
                            MessageUtils.sendMessage(writerThread.writer,
                                    MessageUtils.makeLobbyInfoMessage(lobby.name, lobby.getUserNames()));
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
                        System.out.println("Assigning handle " + handle + " to client " + clientID);

                        NetworkServer.sendToAllClients(MessageUtils.makeConnectionMessage(handle));

                    } else if (TAG.equals(MessageUtils.YIELD_TURN)) {
                        //client has sent us their new handle:
                        //handle = message.get(1);
                        System.out.println("Client " + handle + " (id  " + clientID + " ) yielded turn");

                        
                        if (currentLobby == null) {
                            //client requested to change turns, but it's not their turn!
                            MessageUtils.sendMessage(writerThread.writer,
                                    MessageUtils.makeNagMessage("You requested to yield your turn, but you're not even in lobby!"));
                        } 
                        else if (currentLobby.current.clientID != clientID) {
                            //client requested to change turns, but it's not their turn!
                            MessageUtils.sendMessage(writerThread.writer,
                                    MessageUtils.makeNagMessage("Requested to yield turn, but it's not your turn!"));
                        } else {
                            currentLobby.advanceGameTurn(); //tell lobby handler to advance game turn
                        }
                        //NetworkServer.sendToAllClients(MessageUtils.makeConnectionMessage(handle));

                    } else {
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


}
