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
public class HexTest {
    public Hex hexagon1 = new Hex("101","102","103","104","105","106","107");
   
    //"101","102","103",104,"105","106","107"

    public HexTest() {
    }

    @Test
    public void testGetID() {
       
       
        
        boolean result = hexagon1.GetID() == "101";
        assertTrue(result);
        
        result = hexagon1.GetIDofNorthHexagon() == "102";
        assertTrue(result); 
        
        result = hexagon1.GetIDofNorthEastHexagon() == "103";
        assertTrue(result); 
        
        result = hexagon1.GetIDofSouthEastHexagon() == "104";
        assertTrue(result); 
        
        result = hexagon1.GetIDofSouthHexagon() == "105";
        assertTrue(result); 
        
        result = hexagon1.GetIDofSouthWestHexagon() == "106";
        assertTrue(result); 
        
        result = hexagon1.GetIDofNorthWestHexagon() == "107";
        assertTrue(result); 
    }
    
}
