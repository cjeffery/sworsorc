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
import org.testng.annotations.Test;
import sshexmap.MapHex;

/**
 *
 * @author keith
 */
public class MovementCalculatorTest extends TestCase {
    
    @Test
    public void testWrapper()
    {
        int playerID = 0;
        String hexID = "0606";
        MainMap map = MainMap.GetInstance();
        UnitPool pool = UnitPool.getInstance();
        HashMap<MapHex, Double> moves;
        ArrayList<MapHex> validMoves = new ArrayList<>();
        ArrayList<MapHex> fromHashMapMoves = new ArrayList<>();
        // Initialize test unit and add to unitpool
        ArmyUnit unit = new LightSword();
        unit.setRace(Race.Human);
        pool.addUnit(playerID, unit, hexID);
        
        
        
        // As of 4/27 I know getValidMoves returns the correct hexes. I'm using 
        // the method as a baseline for the wrapper test.
        MovementCalculator.getValidMoves(unit, map.GetHex(hexID), 
                unit.getMovement(), validMoves);
        // Get the hashmap with moves:costs for comparison
        moves = MovementCalculator.movementWrapper( unit, map.GetHex( hexID ) );
        // Compare the keys.
        
        System.out.println("***************HASHMAP****************");
        System.out.println(moves);
        System.out.println("***************ARRAYLIST****************");
        System.out.println(validMoves);
        
        moves.keySet().stream().forEach((keyHex) -> {
            fromHashMapMoves.add(keyHex);
        });

        assertEquals("Check the size of both lists is equal", validMoves.size(),
                fromHashMapMoves.size());
    }
    
}
