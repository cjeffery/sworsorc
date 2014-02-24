/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainSwordSorcery;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David
 */
public class MapHexEdgeTest {
    String temp;
    
    public MapHexEdgeTest() {
    }

    /**
     * Test of main method, of class MapHexEdge.
     */
    @Test
    public void testMain() {
         
        ArrayList< String> temp_array = new ArrayList();
        ArrayList< String> temp_array_two = new ArrayList();
        String ID = "10";
        
        
        
        temp_array.add("River of no return");
        temp_array.add ("Crystal Sea");
        temp_array.add ("Klamath Falls");
        
        MapHexEdge temp_map = new MapHexEdge(ID, temp_array);
        
        temp_array_two = temp_map.GetEdgeList(ID);
        
        boolean result = (temp_array_two.size() == 3);
        assertTrue (result);
     
        System.out.println("end map hex edge test");
    }
    
}
