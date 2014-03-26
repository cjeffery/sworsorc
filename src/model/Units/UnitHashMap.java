/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Units;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 *
 * @author David
 */
public class UnitHashMap {
    TreeMap<Integer, HashMap<String,ArrayList<Object>>> pool = new TreeMap(); 
   
    private static UnitHashMap INSTANCE;
    
    private UnitHashMap() {
    
}
    
    public static UnitHashMap getInstance(){
        if (INSTANCE == null)
            INSTANCE = new UnitHashMap();
        return INSTANCE;
    }
            
    public void addUnit(int playerNumber, Object unit){
       
    
        
        if (pool.containsKey(playerNumber)){
            //pool.get(playerNumber).put(unit.toString(), unit);
        }
        else{
            HashMap<String, ArrayList<Object>> unitMap = new HashMap();
            ArrayList<Object> unitList = new ArrayList();
            
            unitList.add(unit);
            unitMap.put(unit.toString(), unitList);
            pool.put(playerNumber, unitMap);
        }     
    }
    
}
