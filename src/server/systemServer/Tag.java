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
    // Tried to do order from client to server
    PRIVATE, LOBBY, GLOBAL, SEND_CHAT_MESSAGE, // server determines locatlity of reference
    GLOBAL_WHO_REQUEST, GLOBAL_WHO_RESPONSE,
    LOBBY_INFO_REQUEST, LOBBY_INFO_RESPONSE,
    NEW_LOBBY_REQUEST, NEW_LOBBY_RESPONSE,
    JOIN_LOBBY_REQUEST, JOIN_LOBBY_RESPONSE,
    LEAVE_LOBBY_REQUEST, LEAVE_LOBBY_RESPONSE,
    UID_REQUEST, UID_RESPONSE,
    MESSAGE_TO_SERVER, MESSAGE_FROM_SERVER,
     // TODO: Still needed?
    BEGIN_GAME_REQUEST, BEGIN_GAME_RESPONSE,
    NEXT_TURN_INFO, // TODO: deprecated(?)
    DISCONNECT_REQUEST, DISCONNECT_RESPONSE,
    GENERIC,
    INVALID_GAME_ACTION, GENERIC_ERROR,
    SEND_HANDLE,
    YIELD_TURN_REQUEST, YIELD_TURN_RESPONSE,
    SEND_FILE_REQUEST,
    GET_FILE_REQUEST,
    PHASE_CHANGE, // Gabe's phase changing
    CREATE_LOBBY_REQUEST, CREATE_LOBBY_RESPONSE,
    VOTE_RESPONSE, VOTE_REQUEST, BEGIN_GAME,
    UPDATE_HANDLE_REQUEST, UPDATE_HANDLE_RESPONSE,
    ADD_UNIT, // add unit to unitpool
    REMOVE_UNIT, // remove unit from unitpool
    NULL_TAG,
    
    //Colin was here. This was a bit of a mess.
    INIT_GAME_PLEASE, //send to client that's in charge of loading scenario
    PLACE_UNIT //send to have all clients place a unit
    



}
