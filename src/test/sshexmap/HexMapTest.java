/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sshexmap;

import junit.framework.TestCase;
import org.w3c.dom.Node;

/**
 *
 * @author zoe
 */
public class HexMapTest extends TestCase {
    
    public HexMapTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of GetIDFromCoords method, of class HexMap.
     */
    public void testGetIDFromCoords() {
        assertEquals(HexMap.GetIDFromCoords(1, 2), "0102");
        assertEquals(HexMap.GetIDFromCoords(12, 34), "1234");        
    }

    /**
     * Test of GetCoordsFromID method, of class HexMap.
     */
    public void testGetCoordsFromID() {
        assertEquals(HexMap.GetCoordsFromID("1234")[0], 12 );
        assertEquals(HexMap.GetCoordsFromID("1234")[1], 34 );
        assertEquals(HexMap.GetCoordsFromID("0102")[0], 1 );
        assertEquals(HexMap.GetCoordsFromID("0102")[1], 2 );
    }

    
}
