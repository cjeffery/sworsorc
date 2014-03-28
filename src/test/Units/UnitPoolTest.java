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
        assertTrue(pool != null);
        pool.addUnit(1,new LightSword());
        boolean test = pool.pool.get(1).get("LightSword").isEmpty(); 
        assertTrue(!test);
    }
    
        // testing multable additions.
    public void test02() {
        UnitPool pool = UnitPool.getInstance();
        assertTrue(pool != null);
        
        pool.addUnit(1,new LightSword());
        boolean test = pool.pool.get(1).get("LightSword").isEmpty(); 
        assertFalse(test);
        
        pool.addUnit(1,new LightSword());
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
}
