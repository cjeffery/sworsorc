/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package systemServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 *
 * @author Christopher Goes
 */
public class MessagePhoenix {
    
    //End of message (last element of any array):
    static String DONE = "donesignalthingy";

    static String NAG = "nag"; //Server will send this to the client if the client does something
    //that the client code shouldn't allow given the current game state
    //(like requests to change game turn when it's not their turn, start game but not in lobby, etc) 

    //Opening string to tell receiver how to interpret message contents.
    //This will be the first element of every message:
    static String GLOBAL_CHAT = "chat"; //Normal chat message:
    
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
    
    private static void send( ObjectOutputStream writer, List<Object> message ) throws IOException {
        writer.writeObject(message);
    }
    
    private static List<Object> createMessage( String tag, Object... message ) {
        List<Object> temp = new ArrayList<>();
        temp.add(tag);
        temp.addAll(Arrays.asList(message));
        temp.add(DONE);
        return temp;
    }
    
    public static void sendMessage ( ObjectOutputStream writer, String tag, Object... message ) throws IOException {
        send( writer, createMessage( tag, message ));
    }
    //Send the array, ending with the DONE string:
    public static void sendMessage(PrintWriter write, List<String> message) {
        if (debug) {
            System.out.println("S!ending array: " + message);
        }
        for (String s : message) {
            write.println(s);
            write.flush();

            if (debug) {
                System.out.println("!Sent array element:" + s);
            }
        }
        write.println(DONE);
        write.flush();
        if (debug) {
            System.out.println("!Done sending array");
        }
    }

    //Fill an array, until we read the DONE string
    public static List<String> receiveMessage(BufferedReader reader) {
        List<String> array = new ArrayList<>();
        String next = null;
        if (debug) {
            System.out.println("!Receiving array");
        }
        try {
            next = reader.readLine();
            if (next == null) {
                return null;
            }
            while (!next.equals(DONE)) {
                array.add(next);
                if (debug) {
                    System.out.println("!Received array element:" + next);
                }
                next = reader.readLine();
                if (next == null) {
                    return null;
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading array:" + e);
        }

        if (debug) {
            System.out.println("!Done receiving array");
        }
        return array;
    }
    
}
