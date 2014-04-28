/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */
package systemServer;

/**
 *
 * @author Christopher Goes <GhostOfGoes@gmail.com>
 */
public enum Tag {
    // Tried to do order from client to server
    PRIVATE, LOBBY, GLOBAL, SEND_CHAT_MESSAGE, // server determines locatlity of reference
    GLOBAL_WHO_REQUEST, GLOBAL_WHO_RESPONSE,
    LOBBY_INFO_REQUEST, LOBBY_INFO_RESPONSE,
    NEW_LOBBY_REQUEST, NEW_LOBBY_RESPONSE,
    JOIN_LOBBY_REQUEST, JOIN_LOBBY_RESPONSE,
    LEAVE_LOBBY_REQUEST, LEAVE_LOBBY_RESPONSE,
    UID_REQUEST, UID_RESPONSE,
    MESSAGE_TO_SERVER, MESSAGE_FROM_SERVER,
    NAG, // Still needed?
    BEGIN_GAME_REQUEST, BEGIN_GAME_RESPONSE,
    NEXT_TURN_INFO, // TODO: deprecated(?)
    DISCONNECT_REQUEST, DISCONNECT_RESPONSE,
    GENERIC,
    INVALID_GAME_ACTION, GENERIC_ERROR,
    SEND_HANDLE,
    YIELD_TURN_REQUEST, YIELD_TURN_RESPONSE,
    SEND_FILE_REQUEST,
    GET_FILE_REQUEST,
    CREATE_LOBBY_REQUEST, CREATE_LOBBY_RESPONSE,
    VOTE_RESPONSE, VOTE_REQUEST, BEGIN_GAME,
    UPDATE_HANDLE_REQUEST, UPDATE_HANDLE_RESPONSE, // TODO: implement this



}
