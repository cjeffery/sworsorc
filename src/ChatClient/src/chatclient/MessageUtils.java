package chatclient;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * This is an experiment with packing/unpacking messages
 *
 * Right now, this literally sends stuff in an array one string at a time.
 *
 * It is not efficient, but it's very easy to make new message types.
 *
 * If we make sure to pack/unpack messages here, we can easy switch out the
 * logic and make it more efficient later!
 */
public class MessageUtils {

    //End of message (last element of any array):
    static String DONE = "donesignalthingy";

    //Opening string to tell receiver how to interpret message contents.
    //This will be the first element of every message:
    static String GLOBAL_CHAT = "chat"; //Normal chat message:
    static String DISCONNECT_ANNOUNCEMENT = "disconnectAnnounce"; //Announcement message
    static String CONNECT_ANNOUNCEMENT = "connect"; //Announcement message

    static String FILE = "file";
    static String FILE_LINE = "fileLine";
    static String PRINT_FILE = "printFile";

    static String CREATE_NEW_LOBBY = "createNewLobby"; //client requests a new lobby
    static String JOIN_LOBBY_REQUEST = "joinLobby"; //client requests to join an existing lobby

    static String LEAVE_LOBBY_REQUEST = "leaveLobby"; //client requests to leave lobby
    static String LOBBY_INFO = "lobbyNameAndUsers"; //send info about a lobby
    static String REQUEST_LOBBY_INFO = "requestLobbyInfo";
    //static String ANNOUNCE_NEW_LOBBY = "announceNewLobby"; //tell your friends!

    static String SEND_HANDLE = "sendHandle"; //client sending handle to server

    static String GLOBAL_WHO_LIST = "globalwholist"; //send list of all online users
    static String REQUEST_GLOBAL_WHO = "globalwhoreqest"; //ask for all online usernames
    //static String LOBBY_WHO_REQUEST = "lobbywho"; //ask for user names in same lobby

    static boolean debug = false; //Print everything!

    //client asks to create new lobby:
    public static List<String> makeNewLobbyMessage(String lobbyName) {
        List<String> message = new ArrayList<>();
        message.add(CREATE_NEW_LOBBY);
        message.add(lobbyName);
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
