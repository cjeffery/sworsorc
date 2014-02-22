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
public class DiplomacyMapHexTest {
    public DiplomacyMapHex  hexagon1 = new DiplomacyMapHex(101,102,103,104,105,106,107);
    public DiplomacyMapHex  hexagon2 = new DiplomacyMapHex(101,102,103,104,105,106,107,true,1);
    public DiplomacyMapHex  hexagon3 = new DiplomacyMapHex(101,102,103,104,105,106,107,true,2);
    //101,102,103,104,105,106,107

    public DiplomacyMapHexTest() {
    }

    @Test
    public void testGetID() {
       
       // hexagon1
        
        boolean result = hexagon1.GetID() == 101;
        assertTrue(result);
        
        result = hexagon1.GetIDofNorthHexagon() == 102;
        assertTrue(result); 
        
        result = hexagon1.GetIDofNorthEastHexagon() == 103;
        assertTrue(result); 
        
        result = hexagon1.GetIDofSouthEastHexagon() == 104;
        assertTrue(result); 
        
        result = hexagon1.GetIDofSouthHexagon() == 105;
        assertTrue(result); 
        
        result = hexagon1.GetIDofSouthWestHexagon() == 106;
        assertTrue(result); 
        
        result = hexagon1.GetIDofNorthWestHexagon() == 107;
        assertTrue(result); 
       
        result = hexagon1.GetIsPlayerHex();
        assertFalse(result);
        
        result = hexagon1.GetIsNeturalHex();
        assertFalse(result);
                
        // hexagon2
        
        result = hexagon2.GetID() == 101;
        assertTrue(result);
        
        result = hexagon2.GetIDofNorthHexagon() == 102;
        assertTrue(result); 
        
        result = hexagon2.GetIDofNorthEastHexagon() == 103;
        assertTrue(result); 
        
        result = hexagon2.GetIDofSouthEastHexagon() == 104;
        assertTrue(result); 
        
        result = hexagon2.GetIDofSouthHexagon() == 105;
        assertTrue(result); 
        
        result = hexagon2.GetIDofSouthWestHexagon() == 106;
        assertTrue(result); 
        
        result = hexagon2.GetIDofNorthWestHexagon() == 107;
        assertTrue(result); 
       
        result = hexagon2.GetIsPlayerHex();
        assertTrue(result);
        
        result = hexagon2.GetIsNeturalHex();
        assertFalse(result);
        
        //hexagon3
        
        result = hexagon3.GetID() == 101;
        assertTrue(result);
        
        result = hexagon3.GetIDofNorthHexagon() == 102;
        assertTrue(result); 
        
        result = hexagon3.GetIDofNorthEastHexagon() == 103;
        assertTrue(result); 
        
        result = hexagon3.GetIDofSouthEastHexagon() == 104;
        assertTrue(result); 
        
        result = hexagon3.GetIDofSouthHexagon() == 105;
        assertTrue(result); 
        
        result = hexagon3.GetIDofSouthWestHexagon() == 106;
        assertTrue(result); 
        
        result = hexagon3.GetIDofNorthWestHexagon() == 107;
        assertTrue(result); 
       
        result = hexagon3.GetIsPlayerHex();
        assertFalse(result);
        
        result = hexagon3.GetIsNeturalHex();
        assertTrue(result);        
        
        System.out.println("DiplomacyMapHexTest Fineshed");
    }
    
}
