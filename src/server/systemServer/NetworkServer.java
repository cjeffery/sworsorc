/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */
package systemServer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Network Server, a singleton class that is your lord and master
 * Also handles all client-to-client communication as a side-job
 * <p>
 * In theory, you should be able to run a client on same machine as the hosting server
 */
final public class NetworkServer {

    // TODO: could this possibly a subclass of NetworkClient?
    // Clients
    private static List<ClientObject> clientObjects;
    private static int totalClients = 0;
    // Lobbies
    private static List<Lobby> lobbies;
    private static Integer totalLobbies = 0; //used to assign unique lobbyId's

    private static boolean stopped = false;

    private static final int DEFAULT_PORT = 25565;
    //private static final String DEFAULT_IP = "76.178.139.129";
    // TODO: clean up server privacy, methods, vars, etc
    private static int idFactory = 0;

    /**
     * Stops server execution
     * <p>
     * @author Christopher Goes
     */
    public static void stopServer() {
        stopped = true;
    }

    /**
     * Checks if lobby name is unique
     * <p>
     * @param lobbyname Name of the Lobby
     * <p>
     * @return True if unique, False if lobby with name already exists
     */
    private static boolean lobbyExists( String lobbyname ) {
        for ( Lobby lobby : lobbies ) {
            if ( lobby.getName().equals( lobbyname ) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a new lobby
     * <p>
     * @param lobbyname Name of the lobby to be created
     * <p>
     * @return True if lobby created, False if lobby exists and/or could not be created
     */
    public static boolean createNewLobby( String lobbyname ) {
        // TODO: this should be in Lobby. should it send message responses?
        if ( (lobbyname == null || lobbyname.isEmpty()) || lobbyExists( lobbyname ) ) {
            return false;
        } else {
            Lobby lobby = new Lobby( lobbyname, totalLobbies );
            lobbies.add( lobby );
            totalLobbies++;
            return true;
        }
    }

    /**
     * Adds a client to a lobby
     * <p>
     * @param lobbyName Name of the lobby
     * @param client    Client to add to lobby
     *
     * @return False if client is already in lobby or an Error occurred
     */
    protected static boolean joinLobby( String lobbyName, ClientObject client ) {
        // TODO: add null check
        // TODO: game status check
        for ( Lobby l : lobbies ) {
            if ( l.getName().equals( lobbyName ) ) {
                if ( l.isInLobby( client.getHandle() ) ) {
                    return false;
                } else {
                    leaveLobby( client ); // bit dangerous...
                    l.join( client );
                    return true;
                }
            }
        }

        System.err.println( "Error: Couldn't find lobby: " + lobbyName + " to join." );
        return false;
    }

    /**
     * Removes a client from a lobby
     * <p>
     * @param client
     */
    public static void leaveLobby( ClientObject client ) {
        // TODO: add null check
        for ( Lobby l : lobbies ) {
            if ( l.lobbyClients.contains( client ) ) {
                l.leaveLobby( client );
                l.sendToEntireLobby( "Client " + client.getHandle() + " has left the lobby!" );
                if ( l.lobbyClients.isEmpty() ) {
                    lobbies.remove( l ); //For now, just kill lobbies when everyone leaves
                    return;
                }
                return;
            }
        }
        //If we're here, we didn't find the name!
        // TODO: solve why its hitting this so often
        System.err.println( "Requested to leave lobby from client not in lobby" );
    }

    /**
     * Gets current users in the specified Lobby
     * <p>
     * @param lobbyName Name of the Lobby
     * <p>
     * @return List of users
     * <p>
     * @author Christopher Goes
     */
    public static List<String> getLobbyUsers( String lobbyName ) {
        List<String> temp = new ArrayList<>( 0 ); // Empty list instead of null
        if ( lobbyName != null && !lobbyName.isEmpty() ) {
            for ( Lobby l : lobbies ) {
                if ( l.getName() != null && l.getName().equals( lobbyName ) ) {
                    return l.getUserNames();
                }
            }
        }
        return temp;
    }

    // TODO: concurrent lists with lobby names, user names, etc. Only if performance becomes issue.
    /**
     * Gets names of all lobbies
     * <p>
     * @return
     *         <p>
     * @author Christopher Goes
     */
    public static List<String> getLobbyNames() {
        List<String> temp = new ArrayList<>( 0 );
        for ( Lobby l : lobbies ) {
            temp.add( l.getName() );
        }
        return temp;
    }

    /**
     * Returns all currently connected users
     * <p>
     * @return
     */
    public static List<String> getAllUserNames() {
        List<String> handles = new ArrayList<>( 0 );
        for ( ClientObject obj : NetworkServer.clientObjects ) {
            handles.add( obj.getHandle() );
        }
        return handles;
    }

    public static int generateID() {
        idFactory++;
        return idFactory;
    }

    /**
     * Send private message to a individual client
     * <p>
     * @param handle
     * @param flag
     * @param tag
     * @param message
     *
     * @return
     */
    public static boolean sendToClient( String handle, Flag flag, Tag tag, Object... message ) {
        for ( ClientObject client : NetworkServer.clientObjects ) {
            if ( client.getHandle().equals( handle ) ) {
                client.send( flag, tag, message );
                return true;
            }
        }
        return false;
    }

    public static boolean pollClients() {
        // TODO: STUB
        return false;
    }

    /**
     * Global Chat message
     * <p>
     * @param message
     */
    public static void sendToAllClients( String message ) {
        sendToAllClients( Flag.CHAT, Tag.GLOBAL, message );
    }

    /**
     * Forward the message to all ClientObjects, which will send to their Clients
     * <p>
     * @param flag
     * @param tag
     * @param message
     */
    public static void sendToAllClients( Flag flag, Tag tag, String message ) {
        for ( ClientObject client : clientObjects ) {
            client.send( flag, tag, message );
        }
    }

    /**
     * Forward the message to all ClientObjects, which will send to their Clients
     * <p>
     * @param flag
     * @param tag
     * @param message
     */
    public static void sendToAllClients( Flag flag, Tag tag, Object... message ) {
        for ( ClientObject client : clientObjects ) {
            client.send( flag, tag, message );
        }
    }

    /**
     * ClientObject will call this on a planned or unplanned disconnection
     * <p>
     * @param client
     */
    public static void clientDisconnected( ClientObject client ) {

        // TODO: verify that this is operating properly
        // TODO: null check

        leaveLobby( client );
        clientObjects.remove( client );
        totalClients--; // Decrement total clients, we weren't doing this before
        // Client should kill itself (like a true warrior) client.killClient();
        System.out.
                println( "Client " + client.getHandle() + " (" + client.getClientID() + ") has disconnected" );
        sendToAllClients( "User " + client.getHandle() + " has disconnected!" );
    }

    /**
     * Network Server main, primary execution happens here
     * <p>
     * @param args
     */
    public static void main( String args[] ) {
        clientObjects = new ArrayList<>( 0 );
        lobbies = new ArrayList<>( 0 );

        System.out.println( "Server starting. . ." );

        System.out.println( "Binding port " + DEFAULT_PORT + " . . ." );

        try {
            System.out.println( "Server started (" + InetAddress.getLocalHost() + ")" );
        } catch ( UnknownHostException e ) {
            System.err.println( "Error when starting server!\nException: " + e );
        }

        // TODO: SSLServerSocket()?
        // TODO: method to restart server on new port
        ServerSocket listen;
        try {
            listen = new ServerSocket( DEFAULT_PORT );
        } catch ( IOException e ) {
            System.err.println( "Error : While creating ServerSocket\n" + e );
            return; // No need to kill the caller with a exit
        }

        Socket tempsock;
        ClientObject tempclient;
        //Spins off new client connections
        while ( !stopped ) {
            try {
                System.err.println( "Waiting for next client..." );
                tempsock = listen.accept(); //Get socket (blocking)
                tempclient = new ClientObject( tempsock, totalClients ); // generate new client
                totalClients++; // Increment total number of connected clients
                clientObjects.add( tempclient ); // add the active client to list
                tempclient.start(); // Start the connection
            } catch ( IOException e ) {
                System.err.println( "Server failed to accept client!\nException: " + e );
                return;
            }
        }

    }

} // end NetworkServer
