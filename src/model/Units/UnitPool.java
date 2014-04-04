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
            
    /**
     * Adds a unit to the pool. Setting it's location to the one specified
     * getUnitsInHex will return units added with this method.
     * 
     * @author David
     * @param playerID The ID of the player
     * @param unit The ArmyUnit to add to the pool
     * @param location The location to set the unit to
     */
    public void addUnit(int playerID, ArmyUnit unit, String location){
        unit.setLocation(location);
        this.addUnit(playerID, unit);
        this.addToHex(hexList, unitsInHex, unit);
        this.addToMove(unitMove, hexVisited, unit);
    }
    
    /**
     * Sets the unit's ID to player#name@hashcode
     * Then adds it.
     * NOTE that this will NOT index the unit by the units current position 
     * so getUnitsInHex will NOT return it. (cliff7786 - maybe this should be changed?)
     * 
     * @author David
     * @param playerId The ID of the player
     * @param unit The ArmyUnit to add to the pool
     */
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
    
    /**
     * This method will return an ArrayList of Units in the given hex.
     * NOTE: units must have been added with the method that has location as an
     *       argument.
     * 
     * @author David
     * @param hexId The Hex ID to look for units at
     * @return An ArrayList of the unit IDs
     */
    public ArrayList<String> getUnitsInHex(String hexId){
        return hexList.get(hexId);
    }
    
    public ArrayList<String> getUnitHexMoves(String unitId){
        return this.unitMove.get(unitId);
    }
    
    /**
     * Get all the units that belong to a player
     * The key of the returned map appears to be unit type (like "LightSword")
     * 
     * @author David
     * @param playerId The ID of the player to get units for
     * @return A Map of the units
     */
    public TreeMap<String,ArrayList<ArmyUnit>> getAllPlayerUnits(int playerId){
        return pool.get(playerId);
    }
    
    /**
     * Get all the units that belong to a player of a given type (like "LightSword")
     * 
     * @author
     * @param playerId The ID of the player to get units for
     * @return An ArrayList of the units
     */
    public ArrayList<ArmyUnit> getSpecificPlayerUnits(int playerId, String unitClassName){
        return pool.get(playerId).get(unitClassName);
    }
    
    /**
     * gets the unit with the given ID
     * 
     * @author David
     * @param unitId Unit ID takes the form 
     * @return the unit with the given ID string, if it exists
     */
    public ArmyUnit getUnit(String unitId){
        int playerId;
        String unitClass;
        ArmyUnit unit;
        
        playerId = java.lang.Character.getNumericValue(unitId.charAt(0));
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
    
    /**
     * Empty the pool
     * Does not empty hexList or unitMove, or hexVisited, or unitsInHex
     * (Is this correct behavior?)
     * 
     * @author David
     */
    public void clearPool(){
        pool.clear();
    }
    
}
