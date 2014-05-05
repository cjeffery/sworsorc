/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */
package systemServer;

import Units.MoveableUnit;
import Units.UnitPool;
import java.util.List;
import java.util.Map;

/**
 * Receives messages passed from NetworkClient and executes the necessary methods
 * <p>
 *
 * Big picture ("comforting lie"):
 *
 * When another user does something important, an appropriate function in this class magically runs.
 *
 * For example, someone else moved a unit, and so the unitMoved(...) function is called
 * here, letting you update your copy of the map and other stuff.
 *
 * <p>
 *
 * More detail:
 *
 * NetworkClient has a thread that sits around waiting for messages to arrive from the server.
 * When something shows up, the thread checks what type of message it is.
 * Based on the type of message, the thread will automatically call one
 * of the functions in this class.
 *
 * <p>
 *
 * Other potential traps:
 *
 * Some of the messages we send over the network are just networking related, and
 * some of the functions in this class might actually be called after a "back and forth"
 * with the server. This is handled internally by NetworkClient. So there's not
 * always nice "one-to-one" relationship between messages send by NetworkClient
 * and functions received here.
 *
 * @author Jarvis the Unknown
 */
final public class Conductor { // Should this be a static singleton? I think it should be (chris)

    private final static UnitPool pool = UnitPool.getInstance();

    // Put singletons and other objects here, so conductor can access their methods
    // Can we do "register" methods? We have access to singletons everywhere already
    /**
     * Processes all messages passed from NetworkClient
     * <p>
     * This will read the tag passed from NetworkClient, and call the relevant method
     *
     * @author Christopher Goes
     * @param tag    The message tag
     * @param sender
     * @param data
     */
    public static void processMessage( Tag tag, String sender, List<Object> data ) {

        switch ( tag ) {
            case ADD_UNIT:
                pool.addUnit( 0, (MoveableUnit) data.get( 0 ) ); // TODO: (playerID??)
                break;
            case REMOVE_UNIT:
                pool.removeUnit( (MoveableUnit) data.get( 0 ) );
                break;
            case MOVE_UNIT:
                pool.
                        addMoveNetwork( (MoveableUnit) data.get( 0 ) );
                break;
            case MOVE_UNIT_TELEPORT:
                pool.addMove( (MoveableUnit) data.get( 0 ), (String) data.get( 1 ), (boolean) data.
                        get( 2 ) );
                break;
            default:
                System.err.println( "(Conductor) Unknown tag: " + tag );
        }
    }

    // For each major part of the game, put a different method that calls those object's methods
    // The method is passed the message recieved, which is a list of strings List<String>
    /**
     * Called at the start of the user's Player-Turn.
     * <p>
     * The server is informing you that it is now your turn.
     * This function is NOT called when some other user begins their player-turn.
     * Don't confuse Player-Turn with Game-Turn!
     *
     * @see #otherPlayerBeganTurn(java.lang.String)
     */
    public static void beginUserPlayerTurn() {
        //call movement phase...
        //call attack phase...
    }

    /**
     * Called when some other user has started their game turn.
     * <p>
     *
     * The server is letting you know that some other player (who
     * is not YOU), has started their turn.
     *
     * @param otherUsername
     *
     * @see #beginUserPlayerTurn()
     */
    public static void otherPlayerBeganTurn( String otherUsername ) {
        //Announcement?
    }

    /**
     * Called at the start of the manna regeneration inter-phase.
     *
     */
    public static void beginMannaRegenerationPhase() {

    }

    /**
     * Called when all users have been assigned a nation.
     *
     * <p>
     *
     * At the start of a game, each player will be assigned a "nation".
     *
     * A "nation" is something like "Dark Lord's Forces". By looking at the
     * scenario file, this gives information about what troops you get,
     * what provinces, etc.
     *
     * All players get this message at the same time. TODO on actual setup handling...
     *
     * @param playerNation
     * @param playerRoles  A map from usernames to nations
     * @param userNation   For convenience, this is the user's nation.
     */
    public static void nationsAssigned( Map<String, String> playerNation, String userNation ) {

    }

    /**
     * Called at the start of each Game-Turn
     * <p>
     * All users in the game will execute this function at the start o
     * each new Game-Turn (before any inter-phase is executed).
     *
     * Note that a Game-Turn is NOT the same as a Player-Turn. This function
     * does not imply that it's a particular user's turn.
     *
     * @param turnNumber The current turn number (the first turn is turn 1)
     * @param totalTurns The total number of turns in the scenario
     */
    public static void beginNewGameTurn( int turnNumber, int totalTurns ) {

    }

    /**
     * Called after the Player-Order Determination Inter-Phase has finished
     * <p>
     *
     * The server is informing you of the turn-orders for the next game-turn.
     * The player with turn-order 1 will move first, and so on.
     *
     * This function is NOT a signal to start doing turn stuff, even if you
     * see that you'll be moving first.
     *
     * @param playerOrders A map from usernames to turn orders
     * @param yourOrder    For convenience, this is your player order.
     *
     * @see #beginUserPlayerTurn()
     */
    public static void newPlayerOrdersAssigned( Map<String, Integer> playerOrders, int yourOrder ) {

    }

    /**
     * Called after the last Game-Turn has concluded.
     * <p>
     *
     * Someday, this might have parameters saying who won or lost.
     */
    public static void gameFinished() {

    }

    /**
     * This stub is a placeholder for any random events we end up supporting.
     * Not sure how many separate methods or handlers we'll need.
     *
     * @param eventCode Some sort of identifier for the event
     */
    public static void randomEventOccurence( String eventCode ) {

    }

}
