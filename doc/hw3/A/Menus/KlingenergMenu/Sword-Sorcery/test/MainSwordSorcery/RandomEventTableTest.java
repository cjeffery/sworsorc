/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainSwordSorcery;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David
 */
public class RandomEventTableTest {
    private RandomEventTable rEvent;
   
    
    public RandomEventTableTest() {
          rEvent = new RandomEventTable(0);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
       
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of EventKey method, of class RandomEventTable.
     */
    @Test
    public void testRandEvent() {
        
        int d1 = 0;
        int d2 = 0;
        int result;
        String result1;
        
        
        System.out.println("EventKey");
        result = rEvent.GetEventKey();
        System.out.println(result);
        
        System.out.println("EventLength");
        result = rEvent.GetEventLength();
        System.out.println(result);
        
        System.out.println("Event Description");
        result1 = rEvent.GetEventDescription();
        System.out.println(result1 + "\n");
        
        while (d1 <= 5){
            while (d2 <= 5){
                rEvent.RandomEventTableNew(0, d1, d2);
                
                
                System.out.println("EventKey");
                result = rEvent.GetEventKey();
                System.out.println(result);
        
                System.out.println("EventLength");
                result = rEvent.GetEventLength();
                System.out.println(result);
        
                System.out.println("Event Description");
                result1 = rEvent.GetEventDescription();
                System.out.println(result1 + "\n");
               
                d2 = d2 + 1;
            }
            
            d1 = d1 + 1;
            d2 = 0;
        }
        
         rEvent.RandomEventTableNew(13, 5, 21);
                
                
                System.out.println("EventKey");
                result = rEvent.GetEventKey();
                System.out.println(result);
        
                System.out.println("EventLength");
                result = rEvent.GetEventLength();
                System.out.println(result);
        
                System.out.println("Event Description");
                result1 = rEvent.GetEventDescription();
                System.out.println(result1 + "\n");
               
                d2 = d2 + 1;
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

   
    
}
