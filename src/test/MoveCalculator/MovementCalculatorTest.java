/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MoveCalculator;

import Units.UnitPool;
import junit.framework.TestCase;
import sshexmap.MainMap;
import Units.*;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.testng.annotations.Test;
import sshexmap.MapHex;

/**
 *
 * @author keith
 */
public class MovementCalculatorTest extends TestCase {
    
    @Test
    /**
     * This test checks to see if the movement calculator's method 
     * getValidMoves(...) returns the same valid MapHexes in the ArrayList 
     * as the wrapper class, movementWrapper(), does in the HashMap.
     * @author Keith Drew
     */
    public void testWrapper()
    {
        int playerID = 0;
        String hexID = "0606";
        MainMap map = MainMap.GetInstance();
        UnitPool pool = UnitPool.getInstance();
        
        // Initialize needed data structures
        HashMap<MapHex, Double> moves;
        ArrayList<MapHex> validMoves = new ArrayList<>();
        ArrayList<MapHex> illegalMoves = new ArrayList<>();

        // Initialize test unit and add to unitpool
        ArmyUnit unit = new LightSword();
        unit.setRace(Race.Human);
        // Call method to initialize working movement on unit - must be done or
        // will not work.
        unit.ResetWorkingMovement();
        pool.addUnit(playerID, unit, hexID);
                
        // As of 4/27 I know getValidMoves returns the correct hexes. I'm using 
        // the method as a baseline for the wrapper test.
        MovementCalculator.getValidMoves(unit, map.GetHex(hexID), 
                unit.getMovement(), validMoves);
        // Get the hashmap with moves:costs for comparison
        moves = MovementCalculator.movementWrapper( unit, map.GetHex( hexID ) );
         
        // print to see hexes/costs - uncomment if needed.
        /*
        moves.keySet().stream().forEach((key) -> {
            String HexId = key.GetID();
            System.out.println("ID, cost");
            System.out.println(HexId + ", " + moves.get(key));
        });*/
        // Check that sizes are equal - same number of valid moves from both
        //  sources.

        System.out.println("Size 1 and 2: " + validMoves.size() + " " + moves.size());

        assertEquals("Check the size of both lists is equal", validMoves.size(),
                moves.size());
    }
    
    @Test
    /**
     * This test will check the validity of results from different types of 
     * units moving via getValidMoves(...). Edit the initialization code at the
     * top of the test method to check different units.
     * @author Keith Drew
     */
    public void testGetValidMoves()
    {
        int playerID = 0; // arbitrary number for player ID
        String hexID = "0606"; // Starting hex location
        MainMap map = MainMap.GetInstance(); // Initialize and get the map
        UnitPool pool = UnitPool.getInstance();// Initialize and get unitpool
        // Initialize EZ Data Structures
        ArrayList<MapHex> validMoves = new ArrayList<>();
        // Initialize test unit and add to unitpool
        ArmyUnit unit = new LightSword(); // Edit to change unit types
        unit.setRace(Race.Human); // Edit to change unit race.
        pool.addUnit(playerID, unit, hexID); // Add the new unit to the unitpool
        
        // Initialize hardcoded list of available hexes for comparison.
        
        
        // *****  Begin Test Code *****
        
    }
}
