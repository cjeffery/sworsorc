/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package systemServer;

/**
 * General category of message being sent
 *
 * @author Christopher Goes
 */
public enum Flag {
    CHAT, CLIENT, ERROR, GAME, REQUEST, RESPONSE, CONNECTION, FILE, OTHER,
    NULL_FLAG,

}
