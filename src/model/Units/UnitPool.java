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
public class UnitPool {
    TreeMap<Integer, HashMap<String,ArrayList<Object>>> pool = new TreeMap(); 
   
    private static UnitPool INSTANCE;
    
    private UnitPool() {
    
}
    
    public static UnitPool getInstance(){
        if (INSTANCE == null)
            INSTANCE = new UnitPool();
        return INSTANCE;
    }
            
    public void addUnit(int playerNumber, Object unit){
       
    
        
        if (pool.containsKey(playerNumber)){
            if(pool.get(playerNumber).containsKey(unit.toString())){
                pool.get(playerNumber).get(unit.toString()).add(unit);
            }
            else{
                ArrayList<Object> unitList = new ArrayList();
                
                unitList.add(unit);
                pool.get(playerNumber).put(unit.toString(),unitList);
            }
        }
        else{
            ArrayList<Object> unitList = new ArrayList();
            HashMap<String, ArrayList<Object>> unitMap = new HashMap();
            
            unitList.add(unit);
            unitMap.put(unit.toString(), unitList);
            pool.put(playerNumber, unitMap);
        }     
    }
    
}
