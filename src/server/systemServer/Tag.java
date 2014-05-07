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

    // Chat
    PRIVATE, LOBBY, GLOBAL, SEND_CHAT_MESSAGE, // server determines locatlity of reference

    // Connected user(s) info
    GLOBAL_WHO, LOBBY_WHO,
    SEND_HANDLE, UPDATE_HANDLE, // Username/handle

    // Lobby stuff
    LOBBY_INFO, NEW_LOBBY, JOIN_LOBBY, LEAVE_LOBBY,
    // Utilities
    UID, VOTE, // Unique ID, Lobby voting
    MESSAGE_TO_SERVER, MESSAGE_FROM_SERVER, // Any special "under the hood" message
    DISCONNECT, GENERIC, GENERIC_ERROR, NULL_TAG, 
    SEND_FILE, GET_FILE, // File transfer

    // Game stuff
    BEGIN_GAME, NEXT_TURN_INFO, YIELD_TURN, PHASE_CHANGE, INVALID_GAME_ACTION,
    ADD_UNIT, // Add unit to unitpool
    REMOVE_UNIT, // Remove unit from unitpool
    MOVE_UNIT, MOVE_UNIT_TELEPORT, // Move a unit from one location to another, this is network update
    PLACE_UNIT, // Send to have all clients place a unit
    INIT_GAME_PLEASE, // Send to client that's in charge of loading scenario
    



}
