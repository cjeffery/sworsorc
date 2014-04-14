/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sshexmap;

import junit.framework.TestCase;
import sshexmap.*;
import ssterrain.EdgeElement;
import ssterrain.HexEdgeType;
import ssterrain.TTForest;
import ssterrain.TTWoods;

/**
 *
 * @author Colin Clifford
 */
public class MapHexTest extends TestCase {
    public MapHexTest(String testName) {
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
     * Test of getNeighbor method, of class MapHex.
     */
    public void test_GetNeighbor_GetNeighborID_GetHex() {
        MainMap map = MainMap.GetInstance();
        MapHex h = map.GetHex("0337");
        assertNotNull(h);
        assertEquals(h.GetNeighborID(0), "0436");
        assertEquals(h.GetNeighborID(1), "0336");
        assertEquals(h.GetNeighborID(2), "0236");
        assertEquals(h.GetNeighborID(3), "0237");
        assertEquals(h.GetNeighborID(4), "0338");
        assertEquals(h.GetNeighborID(5), "0437");
        assertEquals(h.getNeighbor(0), map.GetHex("0436") );
        assertEquals(h.getNeighbor(1), map.GetHex("0336") );
        assertEquals(h.getNeighbor(2), map.GetHex("0236") );
        assertEquals(h.getNeighbor(3), map.GetHex("0237") );
        assertEquals(h.getNeighbor(4), map.GetHex("0338") );
        assertEquals(h.getNeighbor(5), map.GetHex("0437") );
    }
    
    /**
     * This one won't pass until the map data is done-ish
     */
    public void testBottomRightHex() {
        MainMap map = MainMap.GetInstance();
        assertNotNull( map.GetHex("3952"));
    }

    /**
     * Test of addEdgeElement method, of class MapHex.
     */
    public void testAddGetEdgeElement() {
        MainMap map = MainMap.GetInstance();
        MapHex h = map.GetHex("0337");
        h.addEdgeElement( EdgeElement.makeEdgeElement(HexEdgeType.Bridge), 0);
        h.addEdgeElement( EdgeElement.makeEdgeElement(HexEdgeType.Wall), 1);
        h.addEdgeElement( EdgeElement.makeEdgeElement(HexEdgeType.Gate), 2);
        h.addEdgeElement( EdgeElement.makeEdgeElement(HexEdgeType.ProvinceBorder), 3);
        h.addEdgeElement( EdgeElement.makeEdgeElement(HexEdgeType.Road), 4);
        h.addEdgeElement( EdgeElement.makeEdgeElement(HexEdgeType.Stream), 5);
        h.addEdgeElement( EdgeElement.makeEdgeElement(HexEdgeType.Trail), 0);
        assertTrue( h.getEdge(0).contains(HexEdgeType.Bridge) );
        assertTrue( h.getEdge(1).contains(HexEdgeType.Wall) );
        assertTrue( h.getEdge(2).contains(HexEdgeType.Gate) );
        assertTrue( h.getEdge(3).contains(HexEdgeType.ProvinceBorder) );
        assertTrue( h.getEdge(4).contains(HexEdgeType.Road) );
        assertTrue( h.getEdge(5).contains(HexEdgeType.Stream) );
        assertTrue( h.getEdge(0).contains(HexEdgeType.Trail) );
    }

    /**
     * Test of setTerrainType method, of class MapHex.
     */
    public void testSetGetTerrainType() {
        MainMap map = MainMap.GetInstance();
        MapHex h = map.GetHex("0337");
        assertTrue( h.getTerrainType() instanceof TTWoods );
        h.setTerrainType( new TTForest() );
        assertTrue( h.getTerrainType() instanceof TTForest );
    }

    /*
    public void testAddImprovement() {
    }
    public void testRemoveImprovement() {
    }
    public void testGetImprovements() {
    }*/

    /**
     * Test of GetProvinceName method, of class MapHex.
     */
    public void testGetProvinceName() {
        /* TODO:
        0402 "Kelgarnth"
        0511 "Intas"
        1015 "Outer Krasnia"
        0415 "Vynar"
        0921 "Krasnia"
        0431 "Nattily Woods"
        0736 "Minotaurus"
        1042 "The Swamps"
        1045 "Capella"
        0652 "The Empire"
        1650 "Aardvark Wallow"
        1944 "N'Dardia"
        2234 "Endore"
        2129 "Neitherwold"
        1722 "Dwarfhaven"
        1613 "Ithilgil"
        1910 "Graumthog"
        3110 "Convivia"
        3513 "Sorcerak"
        3422 "Evalyn Woods"
        3535 "Rhiannon"
        3252 "Ka-Chunk"
        */
        MainMap map = MainMap.GetInstance();
        assertEquals( map.GetHex("0402").GetProvinceName(), "Kelgarnth" );
    }

    /*public void testGetPortalNumber() {
    }

    public void testIsPortalHex() {
    }

    public void testIsCastleHex() {
    }

    public void testIsCapitalHex() {
    }

    public void testIsTownHex() {
    }

    public void testIsCityTownCastle() {
    }

    public void testIsVortexHex() {
    }

    public void testIsCityHex() {
    }*/

    /**
     * Test of GetHexName method, of class MapHex.
     */
    public void testGetHexName() {
        MainMap map = MainMap.GetInstance();
        assertEquals( map.GetHex("0202").GetHexName(), null);        
        assertEquals( map.GetHex("0203").GetHexName(), "Mount Greymoor");
        //TODO add rest of special hexes
    }
    /*
    public void testGetMovementCost() {
    }

    public void testGetValidMoves() {
    }

    public void testGetMoveCost() {
    }

    public void testGetEdgeCost() {
    }

    public void testIsEnemyOccupiedHex() {
    }

    public void testGetCombatMultiplier() {
    }

    public void testGetUnitIDs() {
    }

    public void testGetUnits() {
    }
    */
    
}
