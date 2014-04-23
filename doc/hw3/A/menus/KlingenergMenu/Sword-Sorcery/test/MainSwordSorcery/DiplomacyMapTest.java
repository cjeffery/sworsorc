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
public class DiplomacyMapTest {
    DiplomacyMap testMap =  DiplomacyMap.GetDiplomacyMap();
    
    public DiplomacyMapTest() {
        
    }
    
    /**
     * Test of BuildDiplomacyMap method, of class DiplomacyMap.
     */
    @Test
    public void test1() { 
       boolean result = testMap.GetIsNeutralHex("0606");
       assertTrue(result);
    }
    @Test
    public void test2() {
       boolean result = testMap.GetIsPlayerHex("0104");
       assertTrue(result);
    }
    
    @Test
    public void test3() {
       boolean result = testMap.GetIsPlayerHex("0109");
       assertTrue(result);
    }
    
    @Test
    public void test4() {
       boolean result = testMap.GetIsPlayerHex("0601");
       assertTrue(result);
    }
    
    @Test
    public void test5() {
       boolean result = testMap.GetIsPlayerHex("0611");
       assertTrue(result);
    }
    
    @Test
    public void test6() {
       boolean result = testMap.GetIsPlayerHex("1104");
       assertTrue(result);
    }
    
    @Test
    public void test7() {
       boolean result = testMap.GetIsPlayerHex("1109");
       assertTrue(result);
    }
    
    @Test
    public void test8() {
       boolean result = "0706".equals(this.testMap.GetNorthEastNeighborID("0606"));
       assertTrue(result);
    }
    
   
    @Test
    public void test9() {
       System.out.println(testMap.GetSouthEastNeighborID("0606"));
       boolean result = "0707".equals(this.testMap.GetSouthEastNeighborID("0606"));
       assertTrue(result);
    }
    
    @Test
    public void test10() {
       boolean result = "0607".equals(this.testMap.GetSouthNeighborID("0606"));
       assertTrue(result);
    }
 
    @Test
    public void test11() {
       boolean result = "0507".equals(this.testMap.GetSouthWestNeighborID("0606"));
       assertTrue(result);
    }
    
    @Test
    public void test12() {
       boolean result = "0506".equals(this.testMap.GetNorthWestNeighborID("0606"));
       assertTrue(result);
    }
    
    @Test
    public void test13() {
       boolean result = "0".equals(this.testMap.GetNorthNeighborID("0104"));
       assertTrue(result);
    }
    
    
    
    
       
    
}
