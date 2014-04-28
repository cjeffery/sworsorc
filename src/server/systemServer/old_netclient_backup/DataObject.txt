package systemServer;

import java.util.ArrayList;
import systemServer.Unit;


/**
 *
 * @author John Goettsche
 */
        
public class DataObject {
    /*
        used to enforce a basic grammar for the client-server to communicate
        the actions taken in the course of the game.  The grammar is simmilar to 
        SQL where there are some key words that identify the actions to be 
        performed and textural references to identify what specific items are
        to be affected or manipulated by the command.  Some commands, such as 
        attack will have results commands that follow.
    */

    public DataObject(){   
    }
    
    public String gamePhase(String phase){
        //an enumeration of the game phases would help maintain uniformity
        //in the program
        return "PHASE " + phase;
    }
    
    public String randomEvent(){
        //an enumeration of the random events would help maintain uniformity
        //in the program
        String eventString = "";
        return eventString;
    }
    
    public String playerTurn(Player player){
        return "PLAYERTURN " + Integer.toString(player.getID());
    }
        
    public String move(Unit unit, String from, String to){
        String moveString = "MOVE " + Integer.toString(unit.getID()) 
                + " FROM " + from
                + " TO " + to;
        return moveString;
    }
    
    public String attack(ArrayList<Unit> attacker, ArrayList<Unit> defender){
        String attackString = "ATTACK ";
        String defendingLocation = defender.get(0).getLocation();
        for(int a = 0; a < attacker.size(); a++)
            attackString += "\n\t" + attacker.get(a).getID()
                    + " FROM " + attacker.get(a).getLocation()
                    + " TO " + defendingLocation;
        return attackString;
    }
    
    public String combatResults(){
        String resultsString = "COMBATRESULTS ";
        
        return resultsString;
    }
    
    public String castSpell(){
        String spellString = "CAST ";
        return spellString;
    }
}
