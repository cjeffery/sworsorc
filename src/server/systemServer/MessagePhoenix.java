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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christopher Goes
 */
public class MessagePhoenix {
    // TODO: would this be better as a method interface, then implement in classes?
    //End of message (last element of any array):
    static String DONE = "donesignalthingy";

    static String NAG = "nag"; //Server will send this to the client if the client does something
    //that the client code shouldn't allow given the current game state
    //(like requests to change game turn when it's not their turn, start game but not in lobby, etc) 

    static String GENERIC = "genericMessage";
    //Opening string to tell receiver how to interpret message contents.
    //This will be the first element of every message:
    static String GLOBAL_CHAT = "globalChat"; //Normal chat message:
    static String CHAT = "chat"; // preparing for tagged chat messages
    
    /* CONNECTION THINGS */
    static String DISCONNECT_ANNOUNCEMENT = "disconnectAnnounce"; //Announcement message
    static String CONNECT_ANNOUNCEMENT = "connect"; //Announcement message
    static String DISCONNECT_REQUEST = "disconnectrequest"; // client requests soft disconnect (Breakup is sitll a breakup, but this is nicer)

    /* FILE THINGS */
    static String FILE = "file";
    static String FILE_LINE = "fileLine";
    static String PRINT_FILE = "printFile";

    /* LOBBY THINGS */
    static String CREATE_NEW_LOBBY_REQUEST = "createNewLobbyRequest"; //client requests a new lobby
    static String APROVE_NEW_LOBBY_REQUEST = "approveNewLobbyRequest"; //server has created lobby
    static String DENY_NEW_LOBBY_REQUEST = "denyNewLobbyRequest"; //request denied (e.g. duplicate name)
    static String JOIN_LOBBY_REQUEST = "joinLobby"; //client requests to join an existing lobby
    static String LEAVE_LOBBY_REQUEST = "leaveLobby"; //client requests to leave lobby
    static String LOBBY_INFO = "lobbyNameAndUsers"; //send info about a lobby
    static String REQUEST_LOBBY_INFO = "requestLobbyInfo";
    static String JOINED_LOBBY = "joinedLobby"; // a client joined a lobby
    static String LEFT_LOBBY = "leftLobby"; // a client left a lobby
    // TODO: localized broadcasts for lobby joins/leaves within lobby?

    static String SEND_HANDLE = "sendHandle"; //client sending handle to server

    /* WHO REQUEST THINGYS */
    static String GLOBAL_WHO_LIST = "globalwholist"; //send list of all online users
    static String REQUEST_GLOBAL_WHO = "globalwhoreqest"; //ask for all online usernames
    //static String LOBBY_WHO_REQUEST = "lobbywho"; //ask for user names in same lobby

    /* TURN STUFF */
    static String YIELD_TURN = "yieldTurn"; //Sent by client when yielding game turn
    static String NEXT_TURN_INFO = "nextTurnInfo"; //Sent by server when turn changes

    /* GAME STATE STUFF */
    static String REQUEST_BEGIN_GAME = "requestBeginGame"; //sent by client, votes to start game
    static String GAME_BEGUN = "gameBegun"; //sent by server when game is started
    //static String WAITING_TO_BEGIN_GAME = "waitingForVotesBeforeStarting"; //wait for everyone to agree to start

    /* GAMEY ACTIONS */
    static String ADD_UNIT = "addunit";
    static String UPDATE_UNIT = "updateunit"; // client sends a unit update
    static String UPDATE_HEX = "updatehex"; // client sends a hex update

    /* KILL THE BUGS! */
    static boolean debug = false; //Print everything!
    static String ERROR_MESSAGE = "errorMessage"; // generic error message, communicate things client does wrong
    
    /* UTILITY METHODS */
    
    private static void send( ObjectOutputStream writer, List<Object> message ) {
        if( writer != null ) {
            try {
                writer.writeObject(message);
                writer.flush();
            } catch (IOException ex) {
            System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                    + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
            ex.printStackTrace();            }
       } else {
            System.err.println();
        }
    }

    public static List<Object> createMessage( Object... message ) {
        List<Object> temp = new ArrayList<>();
        temp.addAll(Arrays.asList(message));
        return temp;
    }
    
    public static List<Object> createMessage( String tag, Object... message ) {
        List<Object> temp = new ArrayList<>();
        temp.add(tag);
        temp.addAll(Arrays.asList(message));
        return temp;
    }
    
    public static List<String> createStringList( Object... items ) {
        List<String> temp = new ArrayList<>();
        for( Object o : items ) {
            temp.add(o.toString());
        }
        return temp;
    }
    
    public static void sendMessage ( ObjectOutputStream writer, String tag, Object... message ) throws IOException {
        send( writer,  createMessage( tag, message) );
    }
    
    public static void sendMessage (ObjectOutputStream writer, Object... message ) throws IOException {
        send(writer, createMessage( message )); // pass it along, assume first entry is tag
    }

    // TODO: tag stripping done in MessagePhoenix
    @SuppressWarnings("unchecked")
    public static List<Object> recieveMessage( ObjectInputStream reader ) {
        try {
            return reader != null ? (List<Object>) reader.readObject() : null;
        } catch (IOException | ClassNotFoundException | NullPointerException ex ) {
            System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                    + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
            ex.printStackTrace();
        }
        return null;
    }
    
    public static List<String> objectToString( List<Object> list ) {
        List<String> temp = new ArrayList<>();
        for (Object o : list) {
            temp.add(o != null ? o.toString() : null);
        }
        return temp;
    }
    
    public static List<Object> stringToObject( List<String> list ) {
        List<Object> temp = new ArrayList<>();
        for (String s : list ) {
            temp.add( s != null ? s : null );
        }
        return temp;
    }
    
}
