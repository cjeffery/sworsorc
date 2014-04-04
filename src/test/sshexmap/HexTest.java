package sshexmap;

import junit.framework.TestCase;

/**
 *
 * @author Colin Clifford
 */
public class HexTest extends TestCase {
    
    public HexTest(String testName) {
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
     * Test of SetID method, of class Hex.
     */
    public void testSetGetID() {
        Hex h = new Hex("1234");
        assertEquals(h.GetID(), "1234");
        h.SetID("2345");
        assertEquals(h.GetID(), "2345");       
    }

    /**
     * Test of GetNeighborID method, of class Hex.
     */
    public void testGetNeighborID_int() {
        //test odd row
        Hex h = new Hex("1337");
        assertEquals(h.GetNeighborID(0), "1436");
        assertEquals(h.GetNeighborID(1), "1336");
        assertEquals(h.GetNeighborID(2), "1236");
        assertEquals(h.GetNeighborID(3), "1237");
        assertEquals(h.GetNeighborID(4), "1338");
        assertEquals(h.GetNeighborID(5), "1437");
        
        //test even row
        h = new Hex("1437");
        assertEquals(h.GetNeighborID(0), "1537");
        assertEquals(h.GetNeighborID(1), "1436");
        assertEquals(h.GetNeighborID(2), "1337");
        assertEquals(h.GetNeighborID(3), "1338");
        assertEquals(h.GetNeighborID(4), "1438");
        assertEquals(h.GetNeighborID(5), "1538");
    }

    /**
     * Test of GetCoords method, of class Hex.
     */
    public void testGetCoords() {
        Hex h1 = new Hex("1337");
        Hex h2 = new Hex("1437");
        int[] c1 = h1.GetCoords();
        int[] c2 = h2.GetCoords();
        assertEquals(c1[0], 13);
        assertEquals(c1[1], 37);
        assertEquals(c2[0], 14);
        assertEquals(c2[1], 37);
    }

    /**
     * Test of GetIntID method, of class Hex.
     */
    public void testGetIntID() {
        Hex h1 = new Hex("0102");
        Hex h2 = new Hex("1001");
        assertEquals(h1.GetIntID(), 102);
        assertEquals(h2.GetIntID(), 1001);
    }
    
}
