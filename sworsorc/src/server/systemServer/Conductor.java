/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package systemServer;

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
        
    }
    
    // For each major part of the game, put a different method that calls those object's methods
    // The method is passed the message recieved, which is a list of strings List<String>
    
    
    
}

