/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Units;

import java.util.ArrayList;
import java.util.Objects;
import junit.framework.TestCase;

/**
 * In UnitPool.java you need to make the pool variable nonprivate in order to 
 * successfully run the following tests.
 * 
 * 
 * @author David
 */
public class UnitPoolTest extends TestCase {
    
    public UnitPoolTest(String testName) {
        super(testName);
    }

    
    // test adding a unit to a null UnitPool.
    public void test01() {
        UnitPool pool = UnitPool.getInstance();
        pool.clearPool();
        pool.addUnit(1,new LightSword());
        boolean test = pool.pool.get(1).get("LightSword").isEmpty(); 
        assertFalse(test);
    }
    
        // testing multable additions.
    public void test02() {
        UnitPool pool = UnitPool.getInstance();
        pool.clearPool();
        
        
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
    
    //Update a unit position
    public void test03() {
        ArrayList<String> list1, list2;
        ArmyUnit unit;
        
        UnitPool pool = UnitPool.getInstance();
        pool.clearPool();
        assertTrue(pool != null);
        
        pool.addUnit(1,new LightSword(),"0101");
        
        
        
        list1 = pool.getUnitsInHex("0101");
        list2 = pool.getUnitHexMoves(list1.get(list1.size() - 1));
        unit = pool.getUnit(list1.get(0));// If null, it would've thrown a null pointer exception.
        assertTrue(1 == list1.size());
        assertTrue(1 == list2.size());
        
        pool.addMove(unit, "0102");
        
}
    
}
