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
public class UnitHashMapTest extends TestCase {
    
    public UnitHashMapTest(String testName) {
        super(testName);
    }

    
    // 
    public void testSomeMethod() {
        UnitHashMap pool = UnitHashMap.getInstance();
        assertTrue(pool != null);
        pool.addUnit(1,new LightSword());
        boolean test = pool.pool.get(1).get("LightSword").isEmpty(); 
        assertTrue(!test);
    }
    
}
