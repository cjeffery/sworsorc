/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package Units;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author David
 */
public class StackTest {
    UnitPool pool = UnitPool.getInstance();
    
    
    public StackTest() {

    }

   

    
    @Test
    public void testSomeMethod() {
        
        boolean test;
        pool.addUnit(1, new Bow(), "0101");
        pool.addUnit(1, new Bow(), "0101");
        
        test = Stack.overStackWaring(pool.getUnitsInHex("0101"),false);
        
        assertFalse(test);
        
        pool.addUnit(1, new Bow(), "0101");
        
        test = Stack.overStackWaring(pool.getUnitsInHex("0101"),false);
        assertTrue(test);
    }
    
}
