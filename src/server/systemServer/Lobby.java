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

    protected List<ClientObject> lobbyClients;

    private static int lobbyCounter = 0; //used to assign unique lobbyId's
    final private int lobbyId;

    private String name;

    protected ClientObject current; //Whose turn is it? See: advanceTurn()

    public void beginGame() {
        //start with the first player in list:
        current = lobbyClients.get(0);
        sendToEntireLobby( MessagePhoenix.createStringList(MessagePhoenix.NEXT_TURN_INFO, 
                current.getHandle(), current.getClientID())); // TODO: another patch in the netcode quilt...
    }

    /** 
     * Lobby Constructor
     * @param name Name of the lobby being created
     */
    public Lobby(String name) {
        lobbyClients = new ArrayList<>();

        this.name = name;
        this.lobbyId = lobbyCounter++;
    }

    public void sendToEntireLobby(Object... message) {
        for (ClientObject client : lobbyClients) {
            client.send(message);
        }
    }

    public List<String> getUserNames() {
        List<String> handles = new ArrayList<>();
        for (ClientObject client : lobbyClients) {
            handles.add(client.getHandle());
        }
        return handles;
    }

    public void join(ClientObject client) {
        lobbyClients.add(client);
        client.setCurrentLobby(this);
        //TODO: make join messages lobbycast instead of broadcast
    }

    public String getName() {
        return name;
    }
    
    public boolean isInLobby( String handle ) {
        for (ClientObject client : lobbyClients ) {
            if (client.getHandle().equals(handle)) {
                return true;
            }
        }
        return false;
    }
    
    public void leave(ClientObject client) {
        lobbyClients.remove(client);
    }

    public void advanceGameTurn() {
        
        //Moves the game turn in a "cycle" using the lobbyClients list
        //TODO: we can set the order of turn by rearranging the order of the lobbyClient list
        int nextIndex = lobbyClients.indexOf(current) + 1;
        if (nextIndex == lobbyClients.size()) {
            nextIndex = 0; //TODO: This means we have a finished an entire "game pass"! Do something special?
        }

        current = lobbyClients.get(nextIndex);
        sendToEntireLobby(MessagePhoenix.createStringList(MessagePhoenix.NEXT_TURN_INFO, 
                current.getHandle(), current.getClientID())); // TODO: more stuff to fix
    }
    
} // end class
