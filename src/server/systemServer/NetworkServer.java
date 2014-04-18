package systemServer;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Network Server, a singleton class that is your lord and master
 * Also handles all client-to-client communication as a side-job
 */
public class NetworkServer {

    private static List<ClientObject> clientObjects; //"Packaged sockets"
    protected static List<Lobby> lobbies;

    private static boolean stopped = false;
    
    private static final int DEFAULT_PORT = 25565;
    private static final String DEFAULT_IP = "76.178.139.129";

    /**
     * Stops server execution
     * @author Christopher Goes
     */
    public static void stopServer() {
        stopped = true;
    }
    
    public static boolean canCreateNewLobby(String name) {
        //check if lobby name is unique.
        //we might have to add other conditions?

        for (Lobby lobby : lobbies) {
            if (lobby.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public static void createNewLobby(String lobbyName) {
            Lobby lobby = new Lobby(lobbyName);
            lobbies.add(lobby); 
    }
    
    public static void joinLobby(String lobbyName, ClientObject client) {
        for (Lobby l : lobbies) {
            if (l.getName().equals(lobbyName)) {
                if (l.isInLobby(client.getHandle())) {
                    client.send(MessageUtils.makeErrorMessage("Cannot join lobby, you're already in it!"));
                    return;
                } else {
                    leaveLobby(client);
                    l.join(client);
                    sendToAllClients(MessageUtils.makeJoinedLobbyMessage(lobbyName, client.getHandle()));
                    
                    return;
                }
            }
        }
        //If we're here, we didn't find the name!
        //TODO: make a canJoinLobby() function or request/deny messages
        //(e.g. what if the game is in session?)
        System.err.println("Error: Couldn't find lobby: " + lobbyName + " to join.");
    }

    /**
     * Removes a client from a lobby
     * @param client 
     */
    public static void leaveLobby(ClientObject client) {

        for (Lobby l : lobbies) {
            if (l.lobbyClients.contains(client)) {
                l.leave(client);
                sendToAllClients(MessageUtils.makeLeftLobbyMessage(l.getName(), client.getHandle()));
                if (l.lobbyClients.isEmpty()) {
                    lobbies.remove(l); //For now, just kill lobbies when everyone leaves
                }
                return;
            }
        }
        //If we're here, we didn't find the name!
        // TODO: solve why its hitting this so often
        System.err.println("Requested to leave lobby from client not in lobby");
    }

    public static List<String> getAllUserNames() {
        List<String> handles = new ArrayList<>();
        for (ClientObject obj : NetworkServer.clientObjects) {
            handles.add(obj.getHandle());
        }
        return handles;
    }

    /**
     * Forward the message to all ClientObjects, which will send to their Clients
     * @param message 
     */
    public static void sendToAllClients(List<String> message) {
        for (ClientObject client : clientObjects) {
            client.send(message);
        }
    }

    /**
     * clientObject will call this on a planned or unplanned disconnection
     * @param clientId 
     */
    public static void clientDisconnected(int clientId) {

        ClientObject dearlyDeparted = null;

        for (ClientObject clientObject : clientObjects) {
            if (clientObject.getClientID() == clientId) {
                dearlyDeparted = clientObject;
                break;
            }
        }

        leaveLobby(dearlyDeparted);
        sendToAllClients(MessageUtils.makeDisconnectAnnouncementMessage(dearlyDeparted.getHandle()));
        clientObjects.remove(dearlyDeparted);

    }

    /**
     * Network Server main, primary execution happens here
     * @param args
     */
    public static void main(String args[]) {
        clientObjects = new ArrayList<>();
        lobbies = new ArrayList<>();

        System.out.println("Server starting. . .");

        System.out.println("Binding port " + DEFAULT_PORT + " . . .");

        try {
            System.out.println("Server started (" + InetAddress.getLocalHost() + ")");
        } catch (UnknownHostException e) {
            System.err.println("Error when starting server!\nException: " + e );
        }

        // TODO: SSLServerSocket()?
        ServerSocket listen = null;
        try {
            listen = new ServerSocket(DEFAULT_PORT);
        } catch (IOException e) {
            System.err.println("Error : While creating ServerSocket\n" + e);
            System.exit(-1);
        }
        
        Socket tempsock;
        ClientObject tempclient;
        //Spins off new client connections
        // TODO: we need a way to break out without exception or manual termination
        while (true) {
            try {
                System.err.println("Waiting for next client...");
                tempsock = listen.accept(); //Get socket (blocking)
                tempclient = new ClientObject(tempsock); // generate new client
                tempclient.startClient(); // Start the connection
                clientObjects.add(tempclient); // add the active client to list
            } catch (IOException e) {
                System.err.println("Server failed to accept client!\nException: " + e );
                return;
            }
            if( stopped ) {
                return;
            }
        }

    }

} // end NetworkServer