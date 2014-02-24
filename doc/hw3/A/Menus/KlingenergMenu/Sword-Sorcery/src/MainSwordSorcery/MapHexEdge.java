/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainSwordSorcery;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author David
*/

public class MapHexEdge {
    Map<String, ArrayList<String>> hexEdgeMap;
    ArrayList<String> hexArrayList;
    
    public MapHexEdge(String hexID, ArrayList<String> edgeList){ 
        hexEdgeMap = new  HashMap<>();   
        hexArrayList = edgeList;
        
        hexEdgeMap.put(hexID, hexArrayList);        
    }
    
    public ArrayList GetEdgeList (String hexID){
        return hexEdgeMap.get(hexID);
    }
}
