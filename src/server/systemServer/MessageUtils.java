package systemServer;

import Units.MoveableUnit;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import sshexmap.MapHex;

public class MessageUtils {

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
    //static String ANNOUNCE_NEW_LOBBY = "announceNewLobby"; //tell your friends!

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
    
    
    //yell at the client:
    public static List<String> makeNagMessage(String message) {
        List<String> innerMessage = new ArrayList<>();
        innerMessage.add(NAG);
        innerMessage.add(message);
        return innerMessage;
    }

    /* TURN METHODS */
    public static List<String> makeNextTurnMessage(String nextHandle, int nextId) {
        List<String> message = new ArrayList<>();
        message.add(NEXT_TURN_INFO);
        message.add(nextHandle);
        message.add(Integer.toString(nextId));
        return message;
    }

    public static List<String> makeYieldTurnMessage() {
        List<String> message = new ArrayList<>();
        message.add(YIELD_TURN);
        return message;
    }

    /* GAME METHODS */
    public static List<String> makeBeginGameRequestMessage() {
        List<String> message = new ArrayList<>();
        message.add(REQUEST_BEGIN_GAME);
        return message;
    }

    public static List<String> makeGameBegunMessage() {
        List<String> message = new ArrayList<>();
        message.add(GAME_BEGUN);
        return message;
    }

    /* LOBBY METHODS */
    //client asks to create new lobby:
    public static List<String> makeNewLobbyRequestMessage(String lobbyName) {
        List<String> message = new ArrayList<>();
        message.add(CREATE_NEW_LOBBY_REQUEST);
        message.add(lobbyName);
        return message;
    }

    //client asks to create new lobby:
    public static List<String> makeNewLobbyRequestDeniedMessage() {
        List<String> message = new ArrayList<>();
        message.add(DENY_NEW_LOBBY_REQUEST);
        return message;
    }

    //client asks to create new lobby:
    public static List<String> makeNewLobbyRequestAcceptedMessage() {
        List<String> message = new ArrayList<>();
        message.add(APROVE_NEW_LOBBY_REQUEST);
        return message;
    }

    //Request to join lobby
    public static List<String> makeJoinLobbyRequestMessage(String lobbyName) {
        List<String> message = new ArrayList<>();
        message.add(JOIN_LOBBY_REQUEST);
        message.add(lobbyName);
        return message;
    }

    //client request to leave lobby
    public static List<String> makeLeaveLobbyMessage() {
        List<String> message = new ArrayList<>();
        message.add(LEAVE_LOBBY_REQUEST);
        return message;
    }

    //request current list of lobbies and info
    public static List<String> makeRequestLobbyInfoMessage() {
        List<String> message = new ArrayList<>();
        message.add(REQUEST_LOBBY_INFO);
        return message;
    }

    //send info about a lobby to a client
    public static List<String> makeLobbyInfoMessage(String lobbyName, List<String> handles) {
        List<String> message = new ArrayList<>();
        message.add(LOBBY_INFO);
        message.add(lobbyName);
        for (String handle : handles) {
            message.add(handle);
        }
        return message;
    }

    public static void printLobbyInfo(PrintWriter write, List<String> message) {
        String build = "Lobby " + message.get(1) + ", Users: ";

        for (int i = 2; i < message.size() - 1; i++) {
            build += message.get(i) + ", ";
        }
        build += message.get(message.size() - 1);
        write.println(build);
    }

    public static List<String> makeJoinedLobbyMessage(String lobbyName, String handle ) {
        List<String> message = new ArrayList<>();
        message.add(JOINED_LOBBY);
        message.add(lobbyName);
        message.add(handle);
        return message;
    }
    
    public static List<String> makeLeftLobbyMessage( String lobbyName, String handle ) {
        List<String> message = new ArrayList<>();
        message.add(LOBBY_INFO);
        message.add(lobbyName);
        message.add(handle);
        return message;
    }
    
    /* WHO REQUEST METHODS */
    //Request a list of all online users:
    public static List<String> makeGlobalWhoRequestMessage() {
        List<String> message = new ArrayList<>();
        message.add(REQUEST_GLOBAL_WHO);
        return message;
    }

    //Client sends handle to the server:
    public static List<String> makeSendHandleMessage(String handle) {
        List<String> message = new ArrayList<>();
        message.add(SEND_HANDLE);
        message.add(handle);
        return message;
    }

    // Client requests disconnect from server
    public static List<String> makeDisconnectRequestMessage() {
        List<String> message = new ArrayList<>();
        message.add(DISCONNECT_REQUEST);
        return message;
    }

    //Send a list (from server to client) of all online users:
    public static List<String> makeGlobalWhoListMessage(List<String> users) {
        List<String> message = new ArrayList<>();
        message.add(GLOBAL_WHO_LIST);
        for (String s : users) {
            message.add(s);
        }
        return message;
    }

    public static void printGlobalWhoList(PrintWriter write, List<String> message) {
        if (message.size() == 1) { //first element is message-type tag:
            write.println("No users online.");
        } else {
            String build = "";
            for (int i = 1; i < message.size() - 1; i++) {
                build += message.get(i) + ", ";
            }
            build += message.get(message.size() - 1);
            write.println("Online Users: " + build);
        }

    }

    //Announce that user joined system:
    public static List<String> makeConnectionMessage(String handle) {
        List<String> message = new ArrayList<>();
        message.add(CONNECT_ANNOUNCEMENT);
        message.add(handle);
        return message;
    }

    public static void printConnectionMessage(PrintWriter write, List<String> array) {
        write.println("User: " + array.get(1) + " has joined ");
    }

    //Announce that a file is about to be sent:
    public static List<String> makeIncomingFileMessage(String fileName) {
        List<String> message = new ArrayList<>();
        message.add(FILE);
        message.add(fileName);
        return message;
    }

    public static List<String> makeFileLineMessage(String fileName, String line) {
        List<String> message = new ArrayList<>();
        message.add(FILE_LINE);
        message.add(fileName);
        message.add(line);
        return message;
    }

    //Normal chat message, just forward and print handle:
    public static List<String> makeGlobalChatMessage(String handle, String text) {
        List<String> message = new ArrayList<>();
        message.add(GLOBAL_CHAT);
        message.add(handle);
        message.add(text);

        return message;
    }

    public static List<String> makeMoveableUnitUpdateMessage(MoveableUnit unit) {
        List<String> message = new ArrayList<>();
        message.add(UPDATE_UNIT);
        message.add(unit.getID());
        message.add(unit.getLocation());
        //message.add()

        return message;
    }
    
    public static List<String> makeAddUnitMessage(MoveableUnit unit) {
        List<String> message = new ArrayList<>();
        message.add(ADD_UNIT);
        message.add(unit.getID());
        message.add(unit.getRace().toString());
        message.add(unit.getUnitType().toString());
        message.add(unit.getLocation());
        //message.add()

        return message;
    }

    public static List<String> makeMapHexUpdateMessage(MapHex mapHex) {
        List<String> message = new ArrayList<>();
        message.add(UPDATE_HEX);
        message.add(mapHex.GetID());
        for (int i = 0; i < mapHex.getImprovements().size(); i++) {
            message.add(mapHex.getImprovements().get(i).getClass().toString());
        }

        return message;
    }

    public static void printChat(PrintWriter write, List<String> array) {
        write.println(array.get(1) + ": " + array.get(2));

    }

    //Announce that user has disconnected (this isn't the message that says to disconnect)
    public static List<String> makeDisconnectAnnouncementMessage(String handle) {
        List<String> message = new ArrayList<>();
        message.add(DISCONNECT_ANNOUNCEMENT);
        message.add(handle);
        return message;
    }

    public static void printDisconnect(PrintWriter write, List<String> array) {
        write.println("User: " + array.get(1) + " has disconnected.");
    }

    public static List<String> makeErrorMessage( String errormessage ) {
        List<String> message = new ArrayList<>();
        message.add(ERROR_MESSAGE);
        message.add(errormessage);
        return message;
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
