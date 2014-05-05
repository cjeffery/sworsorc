/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package systemServer;

/**
 * Specific message type being sent
 *
 * @author Christopher Goes <GhostOfGoes@gmail.com>
 */
public enum Tag {

    //TODO: REMOVE REQUEST/RESPONSE FORMAT
    PRIVATE, LOBBY, GLOBAL, SEND_CHAT_MESSAGE, // server determines locatlity of reference
    GLOBAL_WHO_REQUEST, GLOBAL_WHO_RESPONSE,
    LOBBY_WHO_REQUEST, LOBBY_WHO_RESPONSE,
    LOBBY_INFO_REQUEST, LOBBY_INFO_RESPONSE,
    NEW_LOBBY_REQUEST, NEW_LOBBY_RESPONSE,
    JOIN_LOBBY_REQUEST, JOIN_LOBBY_RESPONSE,
    LEAVE_LOBBY_REQUEST, LEAVE_LOBBY_RESPONSE,
    UID_REQUEST, UID_RESPONSE,
    MESSAGE_TO_SERVER, MESSAGE_FROM_SERVER,
    BEGIN_GAME_REQUEST, BEGIN_GAME_RESPONSE,
    NEXT_TURN_INFO,
    DISCONNECT_REQUEST, DISCONNECT_RESPONSE,
    GENERIC,
    INVALID_GAME_ACTION, GENERIC_ERROR,
    SEND_HANDLE,
    YIELD_TURN_REQUEST, YIELD_TURN_RESPONSE,
    SEND_FILE_REQUEST,
    GET_FILE_REQUEST,
    PHASE_CHANGE, // Gabe's phase changing
    VOTE_RESPONSE, VOTE_REQUEST, BEGIN_GAME,
    UPDATE_HANDLE_REQUEST, UPDATE_HANDLE_RESPONSE,
    ADD_UNIT, // Add unit to unitpool
    REMOVE_UNIT, // Remove unit from unitpool
    MOVE_UNIT, MOVE_UNIT_TELEPORT, // Move a unit from one location to another, this is network update
    NULL_TAG,
    INIT_GAME_PLEASE, // Send to client that's in charge of loading scenario
    PLACE_UNIT // Send to have all clients place a unit
    



}
