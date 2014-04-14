/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Units;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import junit.framework.TestCase;

/**
 * In UnitPool.java you need to make the pool variable nonprivate in order to 
 * successfully run the junit tests.  Add some example cast for anyone who may
 * need to cast into ArmyUnit.
 * 
 * 
 * @author David
 */
public class UnitPoolTest extends TestCase {
    
    public UnitPoolTest(String testName) {
        super(testName);
    }

    
  /*  // test adding a unit to a null UnitPool.
    public void test01() {
        UnitPool pool = UnitPool.getInstance();
        ArmyUnit aUnit;
        MoveableUnit mUnit;
        
        pool.clear();
        pool.addUnit(1,new LightSword());
        boolean test = pool.pool.get(1).get("LightSword").isEmpty(); 
        assertFalse(test);
        
        
        //Example 1 cast.
        mUnit = pool.pool.get(1).get("LightSword").get(0);
        
        if (mUnit instanceof ArmyUnit)
            aUnit = (ArmyUnit)mUnit;
        else
            aUnit = null;
        
        assertTrue(aUnit.getStrength() == 3);
        
        //Example 2 cast.
        assertTrue(((ArmyUnit)mUnit).getStrength() == 3);
        
        
    }
    
    // testing multable additions.
    public void test02() {
        UnitPool pool = UnitPool.getInstance();
        pool.clear();
        
        
        pool.addUnit(1,new LightSword());
        boolean test = pool.pool.get(1).get("LightSword").isEmpty(); 
        assertFalse(test);
        
        pool.addUnit(1,new LightSword());
        
        System.out.println(Integer.toString(pool.pool.get(1).get("LightSword").size()));
        test = pool.pool.get(1).get("LightSword").size() == 2;
        assertTrue(test);
        
        pool.addUnit(1, new LightSpear());
        pool.addUnit(1,new LightSword());
        pool.addUnit(2, new LightSpear());
        pool.addUnit(2,new LightSword());
        
        test = pool.pool.size() == 2;
        assertTrue(test);
        
        test = pool.pool.get(1).get("LightSword").size() == 3;
        assertTrue(test);
        
        test = pool.pool.get(2).get("LightSpear").size() == 1;
        assertTrue(test);
        
    }
    */
    //Tests update a unit position, test end of movement phase. 
    public void test03() {
        ArrayList<String> list1, list2, list3, list4;
        MoveableUnit unit;
        
        UnitPool pool = UnitPool.getInstance();
        pool.clear();
        assertTrue(pool != null);
        
        pool.addUnit(1,new LightSword(),"0101");
        
        
        
        list1 = pool.getUnitsInHex("0101");
        list2 = pool.getUnitHexMoves(list1.get(list1.size() - 1));
        unit = pool.getUnit(list1.get(0));// If null, it would've thrown a null pointer exception.
        assertTrue(1 == list1.size());
        assertTrue(1 == list2.size());
        
        pool.addMove(unit, "0102");
        pool.addMove(unit, "0103");
        
        list1 = pool.getUnitsInHex("0103");
        list2 = pool.getUnitHexMoves(list1.get(list1.size() - 1));
        assertTrue(1 == list1.size());
        assertTrue(3 == list2.size());
        
        pool.addUnit(2,new Bow(),"0101");
        list1 = pool.getUnitsInHex("0101");
        
        //John look at this bit of code
        pool.addUnit(1,new Zeppelin(),"0102");
        list1 = pool.getUnitsInHex("0102");
        unit = pool.getUnit(list1.get(0));// If null, it would've thrown a null pointer exception.
        
        pool.addMove(unit, "0103");
        
        list1 = pool.getUnitsInHex("0103");
        list2 = pool.getUnitHexMoves(list1.get(0));
        list3 = pool.getUnitHexMoves(list1.get(1));
        
        //unit = pool.getUnit(list1.get(0));// If null, it would've thrown a null pointer exception.
        assertTrue(2 == list1.size());
        assertTrue(3 == list2.size());
        assertTrue(2 == list3.size());
        
        list1 = pool.getUnitsInHex("0101");
        list4 = pool.getUnitHexMoves(list1.get(0));
        
        pool.endMovementPhase();
        
        assertTrue(1 == list2.size());
        assertTrue(1 == list3.size());
        assertTrue(1 == list4.size());
        
    }
    /*
    //Tests undo move.
    public void test04(){
        ArrayList<String> list1, list2, list3, list4;
        MoveableUnit unit;
       
        
        UnitPool pool = UnitPool.getInstance();
        pool.clear();
        assertTrue(pool != null);
        
        pool.addUnit(1,new LightSword(),"0101");
        
        list1 = pool.getUnitsInHex("0101");
        list2 = pool.getUnitHexMoves(list1.get(list1.size() - 1));
        unit = pool.getUnit(list1.get(0));// If null, it would've thrown a null pointer exception.
        assertTrue(1 == list1.size());
        assertTrue(1 == list2.size());
        
        pool.addMove(unit, "0102");
        pool.addMove(unit, "0103");
        pool.addMove(unit, "0104");
        
        assertTrue(4 == list2.size());
        list1 = pool.getUnitsInHex("0104");
        pool.undoMove(list1.get(0));
        list2 = list2 = pool.getUnitHexMoves(list1.get(list1.size() - 1));
        
        assertTrue(3 == list2.size());
        
    }
  

    /**
     * John's example code.  READ ALL // COMENTS IN CODE.
     */
    
    public void test05(){//JohnGoettscheExample
        //This is just some starting data.
        ArrayList<String> list1, list2 = new ArrayList(), list3;
        MoveableUnit unit;
        UnitPool pool = UnitPool.getInstance();
        pool.clear();// YOU WILL NOT NEED THIS JOHN AS IT IS ONLY NEED FOR JUNIT FOR THIS TEST.
        
        pool.addUnit(1,new Bow(), "1010");//addUnit(playerNum,a MovableUnit class, hex number)
        pool.addUnit(1,new Bow(), "1010");
        pool.addUnit(1,new LightSword(), "1010");
        
        pool.addUnit(2,new Bow(), "2010");
        pool.addUnit(2,new Bow(), "2010");
        pool.addUnit(2,new LightSword(), "2010");
        
        pool.addUnit(3,new Bow(), "5010");
        pool.addUnit(3,new Bow(), "5010");
        pool.addUnit(3,new LightSword(), "5010");
        
        //The meat of the matter.
        
        list2.add("1010"); 
        list2.add("5010");
        
        
        //Demorilising everyone in a stack or in this case 2 stacks.
        for (String z : list2){
            list1 = pool.getUnitsInHex(z);
            for (String i : list1){
                unit = pool.getUnit(i);
                ((ArmyUnit)unit).SetDemoralized(true);
                assertTrue(((ArmyUnit)unit).isDemoralized());
            }
        }    
        
        // Moves the stack to a new hex.
        
        pool.addMoveStack("2010", "2311");
        
        assertTrue(pool.getUnitsInHex("2010").size() == 0);
        assertTrue(pool.getUnitsInHex("2311").size() == 3);
        
        
        //for (String i : list3){
        //    unit = pool.getUnit(i);
        //    pool.addMove(unit, "2311");
        //    assertTrue("2311".equals(unit.getLocation()));
        //}
        pool.endMovementPhase(); //This is importan to stop anyone from trying to undo moves after they have been commited.
                                 //Should be last step at the end of any player turn or server update to a client. 
            
        
    }
    
}
