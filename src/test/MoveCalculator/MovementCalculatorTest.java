/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MoveCalculator;

import Units.MoveableUnit;
import Units.UnitPool;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import junit.framework.TestCase;
import sshexmap.MainMap;
import sshexmap.MapHex;

/**
 *
 * @author keith
 */
public class MovementCalculatorTest extends TestCase {
    
    public MovementCalculatorTest(String testName) {
        super(testName);
    }

    public void testCalculator() 
    {
        // Create singltons - DStructures and initialize unitLocation string to 
        //  test hex in question.
        MainMap testMap = MainMap.GetInstance();
        UnitPool testUnitPool = UnitPool.getInstance();
        String unitLocation = "0101";
        MoveableUnit testUnit = new MoveableUnit();
        
        //testUnitPool.addUnit(playerID, null, null);

        // Get all unit strings from the hex.
        ArrayList<String> unitStringList = new ArrayList();
        unitStringList = testUnitPool.getUnitsInHex(unitLocation);
        testUnit = testUnitPool.getUnit(unitStringList.get(0));
        
        ArrayList<MapHex> validMoves = new ArrayList();
        MovementCalculator.getValidMoves(testUnit, testMap.GetHex(unitLocation),
                testUnit.getMovement(), validMoves );
    }
}
