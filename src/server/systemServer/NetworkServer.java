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
 * The Network Server
 * Singleton that manages client connections, lobbies, and communication between clients
 * <p>
 * In theory, you should be able to run a client on same machine as the hosting server.
 * This is not recommended.
 * <p>
 * @author Networking Subteam
 */
final public class NetworkServer { // TODO: could this possibly a subclass of NetworkClient?

    // Clients
    private static List<ClientObject> clientObjects; // Connected clients
    private static int totalClients = 0; // Current Number of connected clients

    // Lobbies
    private static List<Lobby> lobbies; // Game lobbies
    private static int totalLobbies = 0; // Current Number of Game lobbies

    // Server
    final private static int DEFAULT_PORT = 25565; // Default SworSorc server port
    private static int uniqueID = 0; // Guaranteed(I would hope) Unique ID

    // Flags
    private static boolean stopped = false; // Stops server if set to true
    private static boolean debug = false;

    // MESSAGES//
    /**
     * Global Chat message
     * <p>
     * @param sender
     * @param message
     */
    protected static void sendToAllClients( String sender, String message ) {
        sendToAllClients( Flag.CHAT, Tag.GLOBAL, sender, MessagePhoenix.
                packMessageContents( message ) );
    }

    /**
     * Forward the message to all ClientObjects, which will send to their Clients
     * <p>
     * @param flag
     * @param tag
     * @param sender
     * @param message
     */
    protected static void sendToAllClients( Flag flag, Tag tag, String sender, List<Object> message ) {
        for ( ClientObject client : clientObjects ) {
            client.send( flag, tag, sender, message );
        }
    }

    /**
     * Send private message to a individual client
     * <p>
     * @param handle
     * @param flag
     * @param tag
     * @param sender
     * @param message
     *
     * @return
     */
    protected static boolean sendToClient( String handle, Flag flag, Tag tag, String sender,
                                           List<Object> message ) {
        for ( ClientObject client : NetworkServer.clientObjects ) {
            if ( client.getHandle().equals( handle ) ) {
                client.send( flag, tag, sender, message );
                return true;
            }
        }
        return false;
    }

    // CLIENT STUFF //
    /**
     * ClientObject will call this on a planned or unplanned disconnection
     * <p>
     * @param client
     */
    protected static void clientDisconnected( ClientObject client ) {

        if ( client == null ) {
            System.err.println( "null client passed to clientDisconnected!" );
            return;
        }
        leaveLobby( client ); // Leave any lobby client may be in
        clientObjects.remove( client ); // Remove from list of clients
        totalClients--; // Decrement total clients
        if ( debug ) {
            System.err.println( "totalClients after decrement: " + totalClients );
        }
        // Client should kill itself
        System.out.
                println( "Client " + client.getHandle() + " (" + client.getClientID() + ") has disconnected" );
        sendToAllClients( "Server", ("User" + client.getHandle() + " has disconnected!") );
    }

    /**
     * Returns all currently connected users
     * <p>
     * @return
     */
    protected static List<String> getAllUserNames() {
        List<String> handles = new ArrayList<>( 0 );
        for ( ClientObject obj : NetworkServer.clientObjects ) {
            handles.add( obj.getHandle() );
        }
        return handles;
    }

    /**
     * Current number of clients connected to this server
     *
     * @return
     */
    protected static int getTotalClients() {
        return totalClients;
    }

    // LOBBY STUFF //

    /**
     * Creates a new lobby
     * <p>
     * @param lobbyname Name of the lobby to be created
     * <p>
     * @return True if lobby created, False if lobby exists and/or could not be created
     */
    protected static boolean createNewLobby( String lobbyname ) {
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
        if ( lobbyName == null || client == null ) {
            System.err.println( "Null arg passed to joinLobby!" );
            return false;
        } else if ( lobbyName.isEmpty() ) {
            System.err.println( "Empty string passed to joinLobby!" );
            return false;
        }
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
    protected static void leaveLobby( ClientObject client ) { // TODO: boolean return?
        if ( client == null ) {
            return;
        }
        // Search all lobbies for client
        for ( Lobby l : lobbies ) {
            if ( l.lobbyClients.contains( client ) ) {
                l.leaveLobby( client );
                l.lobbyNotification( "Client " + client.getHandle() + " has left the lobby!" );
                if ( l.lobbyClients.isEmpty() ) {
                    lobbies.remove( l ); //For now, just kill lobbies when everyone leaves
                    return;
                }
                return;
            }
        }
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
    protected static List<String> getLobbyUsers( String lobbyName ) {
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

    /**
     * Gets names of all lobbies
     * <p>
     * @return
     *         <p>
     * @author Christopher Goes
     */
    protected static List<String> getLobbyNames() {
        List<String> temp = new ArrayList<>( 0 );
        for ( Lobby l : lobbies ) {
            temp.add( l.getName() );
        }
        return temp;
    }

    /**
     * Checks if lobby name is unique
     * <p>
     * @param lobbyname Name of the Lobby
     * <p>
     * @return True if unique, False if lobby with name already exists
     */
    protected static boolean lobbyExists( String lobbyname ) {
        for ( Lobby lobby : lobbies ) {
            if ( lobby.getName().equals( lobbyname ) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Current number of active game lobbies on this server
     *
     * @return
     */
    protected static int getTotalLobbies() {
        return totalLobbies;
    }

    // UTILITIES & STUBS //
    /**
     * Generates a unique ID across all clients
     *
     * @return
     */
    protected static int generateUniqueID() {
        uniqueID++;
        return uniqueID;
    }

    /**
     * Poll clients for a Yes/No response, process and return result of poll
     *
     * @param question Poll question
     *
     * @return Majority response
     */
    protected static boolean pollClients( String question ) {
        // TODO: STUB
        return false;
    }

    /**
     * Stops server execution
     * <p>
     * @author Christopher Goes
     */
    public static void stopServer() {
        stopped = true;
    }



    /**
     * Network Server main, primary execution happens here
     * <p>
     * @param args
     */
    public static void main( String args[] ) {

        debug = MessagePhoenix.debugStatus();
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
                tempclient = new ClientObject( tempsock, generateUniqueID() ); // generate new client with guaranteed unique ID
                totalClients++; // Increment total number of connected clients
                clientObjects.add( tempclient ); // add the active client to list
                tempclient.start(); // Start the connection
            } catch ( IOException e ) {
                System.err.println( "Server failed to accept client!\nException: " + e );
                return;
            }
        }

    }
    
    /**
     * start the server, fun
     */
    public static void startServer() {
        new Thread() {
            public void run() {
                systemServer.NetworkServer.main(new String[0]);
            }
        }.start();  
        //sleep to discourage immediate connection attempts from failing
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {}
    }

} // end NetworkServer
