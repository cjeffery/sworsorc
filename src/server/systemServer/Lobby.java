/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */
package systemServer;

import java.util.ArrayList;
import java.util.List;

/**
 * The Infamous Lobby Class
 * If you are not in the network team, do not touch this!
 * Public != PettingZoo
 */
public class Lobby {

    // TODO: private?
    protected List<ClientObject> lobbyClients;
    final private Integer lobbyID; // TODO: this should probably be used
    final private String name; // TODO: Ability to change lobby name?

    // Client who's turn it currently is
    protected ClientObject current;

    /**
     * Lobby Constructor
     *
     * @param name    Name of the lobby being created
     * @param lobbyID
     */
    public Lobby( String name, Integer lobbyID ) {
        this.lobbyClients = new ArrayList<>( 0 );
        this.name = name;
        this.lobbyID = lobbyID;
    }

    /**
     *
     * @param handle
     *
     * @return
     */
    protected boolean isInLobby( String handle ) {
        for ( ClientObject client : lobbyClients ) {
            if ( client.getHandle().equals( handle ) ) {
                return true;
            }
        }
        return false;
        // TODO: test out Netbeans fancy-pants functional way of doing these list searches
        //return lobbyClients.stream().
        //anyMatch( (client) -> (client.getHandle().equals( user )) );
    }

    /**
     *
     * @param flag
     * @param tag
     * @param message
     */
    protected void sendToEntireLobby( Flag flag, Tag tag, Object... message ) {
        for ( ClientObject client : lobbyClients ) {
            client.send( flag, tag, message );
        }
    }

    /**
     *
     * @param message
     */
    protected void sendToEntireLobby( Object... message ) {
        for ( ClientObject client : lobbyClients ) {
            client.send( message );
        }
    }

    /**
     *
     * @return
     */
    protected List<String> getUserNames() {
        List<String> handles = new ArrayList<>( 0 );
        for ( ClientObject client : lobbyClients ) {
            handles.add( client.getHandle() );
        }
        return handles;
    }

    /**
     *
     * @param client
     */
    public void join( ClientObject client ) {
        lobbyClients.add( client );
        client.setCurrentLobby( this );
        //TODO: make join messages lobbycast instead of broadcast
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public Integer getLobbyID() {
        return lobbyID;
    }

    /**
     *
     * @param client
     */
    protected void leaveLobby( ClientObject client ) {
        if ( client != null && isInLobby( client.getHandle() ) ) {
            lobbyClients.remove( client );
        }
    }

    /**
     *
     */
    public void beginGame() {
        //start with the first player in list:
        current = lobbyClients.get( 0 );
        sendToEntireLobby( Flag.GAME, Tag.NEXT_TURN_INFO,
                           current.getHandle(), current.getClientID() );
    }

    /**
     * Moves the game turn in a "cycle" using the lobbyClients list
     */
    public void advanceGameTurn() {
        //TODO: Set order of turn by rearranging the order of the lobbyClient list

        int nextIndex = lobbyClients.indexOf( current ) + 1;
        if ( nextIndex == lobbyClients.size() ) {
            nextIndex = 0;
            //TODO: This means we have a finished an entire "game pass"! Do something special?
        }

        current = lobbyClients.get( nextIndex );
        sendToEntireLobby( Flag.GAME, Tag.NEXT_TURN_INFO, current.getHandle(), current.getClientID() );
    }

} // end class
