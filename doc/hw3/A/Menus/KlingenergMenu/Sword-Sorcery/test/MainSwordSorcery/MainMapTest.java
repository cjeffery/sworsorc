/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainSwordSorcery;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David
 */
public class MainMapTest {
    MainMap testMap =  MainMap.GetMainMap();
    
    

    /**
     * Test of GetMapHex method, of class MainMap.
     */
    @Test
    public void testGetMapHex() {
        MapHex testHex = testMap.GetMapHex("1932");
        boolean result = testHex.GetIsCityHex();
        assertFalse(result);
        
        result = testHex.GetIsVortexHex();
        assertFalse(result);
        
        result = testHex.GetIsPortalHex();
        assertFalse(result);
        
        result = "Br".equals(testHex.GetTerrainKey());
        assertTrue(result);
        
        result = "Br".equals(testHex.getNorthWestHexEdgeCodes().get(0));
        assertTrue(result);
        
        result = "Pb".equals(testHex.getNorthWestHexEdgeCodes().get(1));
        assertTrue(result);
        
        System.out.println(testHex.GetIDofSouthHexagon());
        result = "1933".equals(testHex.GetIDofSouthHexagon());
        assertTrue(result);
        
    }
    
    @Test
    public void testGetMapHex2() {
        MapHex testHex = testMap.GetMapHex("1931");
        boolean result = testHex.GetIsCityHex();
        assertFalse(result);
        
        result = testHex.GetIsVortexHex();
        assertFalse(result);
        
        result = testHex.GetIsPortalHex();
        assertFalse(result);
        
        result = "Bro".equals(testHex.GetTerrainKey());
        assertTrue(result);
        
        result = "Pb".equals(testHex.getSouthHexEdgeCodes().get(0));
        assertTrue(result);
        
        System.out.println(testHex.GetIDofSouthHexagon());
        result = "1932".equals(testHex.GetIDofSouthHexagon());
        assertTrue(result);
        
    }
    
}
