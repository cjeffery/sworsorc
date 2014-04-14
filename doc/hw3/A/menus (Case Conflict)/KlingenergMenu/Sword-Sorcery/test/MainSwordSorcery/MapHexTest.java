/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainSwordSorcery;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David
 */
public class MapHexTest {
    MapHex testHex;
    
    public MapHexTest() {
        
    }
    
    @Test
    public void testGetTerrainKey() {
        HashMap<String,ArrayList<String>> hexEdges = new HashMap();
        
        testHex = new MapHex("0404","0403","0504","0505","0405","0305","0304","R",hexEdges);
        
        boolean result = "R".equals(testHex.GetTerrainKey());
        assertTrue(result);
        
    }
    
    @Test
    public void testGetTerrainDescription() {
        HashMap<String,ArrayList<String>> hexEdges = new HashMap();
    
        testHex = new MapHex("0404","0403","0504","0505","0405","0305","0304","R",hexEdges);
        boolean result = "Rough".equals(testHex.GetTerrainDescription());
        assertTrue(result);
    }
    
    @Test
    public void testGetIsCityHex() {
        HashMap<String,ArrayList<String>> hexEdges = new HashMap();
        testHex = new MapHex("0404","0403","0504","0505","0405","0305","0304","R",hexEdges,true,"Moscow");
        
        boolean result = testHex.GetIsCityHex();
        assertTrue(result);
        
        result = "Moscow".equals(testHex.GetCityName());
        assertTrue(result);
    }
    
    
    
    
     @Test
    public void testGetIsVortexHex() {
        HashMap<String,ArrayList<String>> hexEdges = new HashMap();
        testHex = new MapHex("0404","0403","0504","0505","0405","0305","0304","R",hexEdges,true);
        boolean result = testHex.GetIsVortexHex();
        assertTrue(result);
    }
    
    @Test
    public void testGetIsPortalHex() {
        HashMap<String,ArrayList<String>> hexEdges = new HashMap();
        testHex = new MapHex("0404","0403","0504","0505","0405","0305","0304","R",hexEdges,0);
        boolean result = testHex.GetIsPortalHex();
        assertFalse(result);
    }
    
  
    @Test
    public void testGetNorthHexEdgeListTwo() {
        HashMap<String,ArrayList<String>> hexEdges = new HashMap();
        ArrayList<String> edgeOfHex = new ArrayList();
        ArrayList<String> testEdgeOfHex = new ArrayList();
        
        edgeOfHex.add("Ga");
        edgeOfHex.add("Br");
        
        hexEdges.put("northWestEdge",edgeOfHex);
        hexEdges.put("southEdge",edgeOfHex);
        
        
        
        testHex = new MapHex("0404","0403","0504","0505","0405","0305","0304","R",hexEdges);
    
        
        testEdgeOfHex = testHex.getNorthWestHexEdgeCodes();
        
        boolean result = "Ga".equals(testEdgeOfHex.get(0));
        assertTrue(result);
        
        result = "Br".equals(testEdgeOfHex.get(1));
        assertTrue(result);
    }
    
}
