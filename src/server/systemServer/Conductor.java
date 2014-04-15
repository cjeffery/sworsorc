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

/**
 * Receives messages passed from NetworkClient and executes the necessary methods
 * @author Christopher Goes
 */
public class Conductor { // Should this be a static singleton?
    
    // Put singletons and other objects here, so conductor can access their methods
    
    /**
     * Constructor
     * @author Christopher Goes
     */
    public Conductor() {
    
    }
    
    /**
     * Processes all messages passed from NetworkClient
     * <p>
     * This will read the tag passed from NetworkClient, and call the relevant method
     * NOTE: This tag is really message.get(0), this just simplifies things a bit, and makes a bit more extensible
     * @author Christopher Goes
     * @param message The message contents, minus the tag
     * @param tag The message tag
     */
    public void processMessage( List<String> message, String tag ) {
        if (message.get(0).equals(MessageUtils.UPDATE_UNIT)){
            UnitPool pool = UnitPool.getInstance();
            MoveableUnit unit = pool.getUnit(message.get(1));
            String location = message.get(2);
            pool.addMove(unit, location);
        } else if (message.get(0).equals(MessageUtils.ADD_UNIT)){
            UnitPool pool = UnitPool.getInstance();
            MoveableUnit unit = pool.getUnit(message.get(1));
            unit.setRace(message.get(2));
            unit.setUnitType(message.get(3));            
            String location = message.get(4);
            //needs player ID
            //pool.addUnit(port, unit);
        }
    }
    
    // For each major part of the game, put a different method that calls those object's methods
    // The method is passed the message recieved, which is a list of strings List<String>
    
    
    
}

