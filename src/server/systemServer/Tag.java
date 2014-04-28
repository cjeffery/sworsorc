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
    DIRECT, LOBBY, GLOBAL,
    GLOBAL_WHO_REQUEST, GLOBAL_WHO_RESPONSE,
    LOBBY_INFO_REQUEST, LOBBY_INFO_RESPONSE,
    NEW_LOBBY_REQUEST, NEW_LOBBY_RESPONSE,
    JOIN_LOBBY_REQUEST, JOIN_LOBBY_RESPONSE,
    LEAVE_LOBBY_REQUEST, LEAVE_LOBBY_RESPONSE,
    UID_REQUEST, UID_RESPONSE,
    NAG, // Still needed?
    BEGIN_GAME_REQUEST, BEGIN_GAME_RESPONSE,
    NEXT_TURN_INFO, // TODO: deprecated(?)
    DISCONNECT,
    GENERIC,
    INVALID_GAME_ACTION, GENERIC_ERROR,
    DISCONNECT_REQUEST,
    SEND_HANDLE,
    YIELD_TURN,
    SEND_FILE_REQUEST,
    GET_FILE_REQUEST,
    CREATE_LOBBY_REQUEST, CREATE_LOBBY_RESPONSE,

}
