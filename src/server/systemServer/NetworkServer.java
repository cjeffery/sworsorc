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

    // Clients
    private static List<ClientObject> clientObjects;
    private static int totalClients = 0;
    // Lobbies
    private static List<Lobby> lobbies;

    private static boolean stopped = false;

    private static final int DEFAULT_PORT = 25565;
    //private static final String DEFAULT_IP = "76.178.139.129";

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
     * @param name Name of the Lobby
     * <p>
     * @return True if unique, False if lobby with name already exists
     */
    private static boolean canCreateNewLobby( String name ) {
        for ( Lobby lobby : lobbies ) {
            if ( lobby.getName().equals( name ) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a new lobby
     * <p>
     * @param lobbyName Name of the lobby to be created
     * <p>
     * @return
     *         True if lobby created, False if lobby exists and/or could not be created
     */
    public static boolean createNewLobby( String lobbyName ) {
        if ( lobbyName != null && !lobbyName.isEmpty() && canCreateNewLobby( lobbyName ) ) {
            Lobby lobby = new Lobby( lobbyName );
            lobbies.add( lobby );
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds a client to a lobby
     * <p>
     * @param lobbyName Name of the lobby
     * @param client    Name of the client
     */
    public static void joinLobby( String lobbyName, ClientObject client ) {
        // TODO: add null check
        for ( Lobby l : lobbies ) {
            if ( l.getName().equals( lobbyName ) ) {
                if ( l.isInLobby( client.getHandle() ) ) {
                    client.
                            send( MessagePhoenix.ERROR_MESSAGE, "Cannot join lobby, you're already in it!" );
                } else {
                    leaveLobby( client );
                    l.join( client );
                    sendToAllClients( MessagePhoenix.JOINED_LOBBY, lobbyName, client.getHandle() );
                }
            }
        }
        //If we're here, we didn't find the name!
        //TODO: make a canJoinLobby() function or request/deny messages
        //(e.g. what if the game is in session?)
        System.err.println( "Error: Couldn't find lobby: " + lobbyName + " to join." );
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
                l.leave( client );
                sendToAllClients( MessagePhoenix.LEFT_LOBBY, l.getName(), client.getHandle() );
                if ( l.lobbyClients.isEmpty() ) {
                    lobbies.remove( l ); //For now, just kill lobbies when everyone leaves
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
     * Forward the message to all ClientObjects, which will send to their Clients
     * <p>
     * @param tag
     * @param message
     */
    public static void sendToAllClients( String tag, List<String> message ) {
        for ( ClientObject client : clientObjects ) {
            client.send( message.toArray( new Object[message.size()] ) );
        }
    }

    /**
     * Forward the message to all ClientObjects, which will send to their
     * Clients
     * <p>
     * @param message
     */
    public static void sendToAllClients( Object... message ) {
        for ( ClientObject client : clientObjects ) {
            client.send( message );
        }
    }

    /**
     * clientObject will call this on a planned or unplanned disconnection
     * <p>
     * @param clientId
     */
    public static void clientDisconnected( int clientId ) {

        // TODO: verify that this is operating properly, and check clientID
        ClientObject dearlyDeparted = null;

        for ( ClientObject clientObject : clientObjects ) {
            if ( clientObject.getClientID() == clientId ) {
                //dearlyDeparted = clientObject;
                leaveLobby( clientObject );
                clientObjects.remove( clientObject );
                totalClients--; // Decrement total clients, we weren't doing this before
                clientObject.killClient();
                sendToAllClients( MessagePhoenix.DISCONNECT_ANNOUNCEMENT, clientObject.getHandle() );
                return;
            }
        }
        System.err.println( "Could not find client to disconnect!" );
        /*
         * if (dearlyDeparted != null ) {
         * leaveLobby(dearlyDeparted);
         * clientObjects.remove(dearlyDeparted);
         * sendToAllClients( MessagePhoenix.DISCONNECT_ANNOUNCEMENT, dearlyDeparted.getHandle());
         * } else {
         * System.err.println("Tried to disconnect null client!");
         * }
         */
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
