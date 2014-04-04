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
 * A pool of all units in play during this game. 
 * 
 * @author David
 */
public class UnitPool {
    TreeMap<Integer, TreeMap<String,ArrayList<ArmyUnit>>> pool = new TreeMap(); 
    private TreeMap<String, ArrayList<String>> hexList = new TreeMap();
    private TreeMap<String, ArrayList<String>> unitMove = new TreeMap();
    private ArrayList<String> hexVisited = new ArrayList();
    private ArrayList<String> unitsInHex = new ArrayList();
    private static UnitPool INSTANCE;
    
    private UnitPool() {
    
}
    
    public static UnitPool getInstance(){
        if (INSTANCE == null)
            INSTANCE = new UnitPool();
        return INSTANCE;
    }
            
    public void addUnit(int playerID, ArmyUnit unit, String location){
        unit.setLocation(location);
        this.addUnit(playerID, unit);
        this.addToHex(hexList, unitsInHex, unit);
       this.addToMove(unitMove, hexVisited, unit);
    }
    
    public void addUnit(int playerId, ArmyUnit unit){
       unit.setID(Integer.toString(playerId) + "#" + unit.toString() + "@" + Integer.toString(unit.hashCode()));
       
       
        
       if (pool.containsKey(playerId)){
           if(pool.get(playerId).containsKey(unit.toString()))
               pool.get(playerId).get(unit.toString()).add(unit);
            else{
               ArrayList<ArmyUnit> unitList = new ArrayList();
                
               unitList.add(unit);
               pool.get(playerId).put(unit.toString(),unitList);
            }
       }
       else{ 
           ArrayList<ArmyUnit> unitList = new ArrayList();
           TreeMap<String, ArrayList<ArmyUnit>> unitMap = new TreeMap();
            
           unitList.add(unit);
           unitMap.put(unit.toString(), unitList);
           pool.put(playerId, unitMap);
       }     
    }

    private void addToHex(TreeMap<String,ArrayList<String>> tree, ArrayList<String> list, ArmyUnit unit) {
        if (tree.containsKey(unit.getLocation())){
            if(!tree.get(unit.getLocation()).contains(unit.getID()))
                tree.get(unit.getLocation()).add(unit.getID());
        }
        else{
            list.add(unit.getID());
            tree.put(unit.getLocation(), list);
        }
    }
    
    private void addToMove(TreeMap<String,ArrayList<String>> tree, ArrayList<String> list, ArmyUnit unit) {
        if (tree.containsKey(unit.getID())){
            if(!tree.get(unit.getID()).contains(unit.getLocation()))
                tree.get(unit.getID()).add(unit.getLocation());
        }
        else{
            list.add(unit.getLocation());
            tree.put(unit.getID(), list);
        }
    }
    
    public void addMove(ArmyUnit unit, String hexId){
        this.unitMove.get(unit.getID()).add(hexId);
        //To Do!!!!!
    }
    
    public ArrayList<String> getUnitsInHex(String hexId){
        return hexList.get(hexId);
    }
    
    public ArrayList<String> getUnitHexMoves(String unitId){
        return this.unitMove.get(unitId);
    }
    
    public TreeMap<String,ArrayList<ArmyUnit>> getAllPlayerUnits(int playerId){
        return pool.get(playerId);
    }
    
    public ArrayList<ArmyUnit> getSpecificPlayerUnits(int playerId, String unitClassName){
        return pool.get(playerId).get(unitClassName);
    }
    
    public ArmyUnit getUnit(String unitId){
        int playerId;
        String unitClass;
        ArmyUnit unit;
        
        playerId = Character.getNumericValue(unitId.charAt(0));
        unitClass = unitId.substring(unitId.indexOf("#") + 1, unitId.indexOf("@"));
        
        boolean test = false;
        int i = 0;
        
        do {
                    unit = pool.get(playerId).get(unitClass).get(i);
                    test = unit.getID() == unitId;
                    i++;
                 } while (i < pool.get(playerId).get(unitClass).size() && !test );
        if (test)
            return unit;
        else
            return null;
    }
    
    public void clearPool(){
        pool.clear();
    }
    
}
