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
 * Unit pool tracks all the units that have been created in the game.  
 * Unit pool is a singleton that is created by calling getInstance().
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
    /**
     * This creates or returns the unit pool singleton.
     * 
     * @return 
     */
    public static UnitPool getInstance(){
        if (INSTANCE == null)
            INSTANCE = new UnitPool();
        return INSTANCE;
    }
    
    /**
     
     * @param location : the hex the unit occupies. 
     */        
    public void addUnit(int playerID, ArmyUnit unit, String location){
        unit.setLocation(location);
        this.addUnit(playerID, unit);
        this.addToHex(hexList, unit);
        this.addToUnit(unitMove, unit);
    }
    
    /**
     * Used to add a new unit to the pool.
     * Example:
     * pool.addUnit (1, new LightSword, "0101");
     * 
     * @param playerID : the player number or unique ID
     * @param unit     : an instance of some army unit  
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
    
    /**
     * this adds a unit to the hex list tree.
     * 
     * @param tree : this should be hexList tree.
     * @param unit : the unit to be added
     */
    private void addToHex(TreeMap<String,ArrayList<String>> tree, ArmyUnit unit) {
        if (tree.containsKey(unit.getLocation())){
            if(!tree.get(unit.getLocation()).contains(unit.getID()))
                tree.get(unit.getLocation()).add(unit.getID());
        }
        else{
            ArrayList<String> list = new ArrayList();
            
            list.add(unit.getID());
            tree.put(unit.getLocation(), list);
        }
    }
    
    /**
     * this adds a unit to the hex move tree.
     * 
     * @param tree : this should be hex move tree.
     * @param unit : the unit whose new location is being added to 
     *               its array list.
     */
    
    private void addToUnit(TreeMap<String,ArrayList<String>> tree, ArmyUnit unit) {
        if (tree.containsKey(unit.getID())){
            if(!tree.get(unit.getID()).contains(unit.getLocation()))
                tree.get(unit.getID()).add(unit.getLocation());
        }
        else{
            ArrayList<String> list = new ArrayList();
            list.add(unit.getLocation());
            tree.put(unit.getID(), list);
        }
    }
    
    /**
     * Is used to update the unit location and all of its associated trees.
     * 
     * Example: 
     * addMove(getUnitsInHex("0101").get(0),"0102");
     * 
     * @param unit  : A valid instance of a army units class.
     * @param hexId : The hex the unit is moving into.
     */
    
    public void addMove(ArmyUnit unit, String hexId){
        
        // This horific looking line removes the unit from its current location.
        hexList.get(unit.getLocation()).remove(hexList.get(unit.getLocation()).indexOf(unit.getID()));
        
        unit.setLocation(hexId);
        this.addToHex(hexList, unit);
        this.addToUnit(unitMove, unit);
    
        
    }
    
    /**
     * Returns a list that contains all the units currently occupying a hex.
     * 
     * Example:
     * someList = getUnitsInHex("0101");
     * 
     * @param hexId      : The hex the lookup is needed on.
     * @return 
     */    
    public ArrayList<String> getUnitsInHex(String hexId){
        return hexList.get(hexId);
    }
    
    /**
     * Returns a list that contains all the moves again it has conducted in its 
     * current move phase or after a teleport.
     * 
     * Example:
     * someList = getUnitHexMoves(someUnit.getID()); someUnit is typically 
     * obtained from parsing a unit out of the getUnitsInHex method.
     * 
     * @param unitId             : The valid ID of a unit. Typically obtained 
     *                             by calling someUnit.getID().
     * @return 
     */
    public ArrayList<String> getUnitHexMoves(String unitId){
        return this.unitMove.get(unitId);
    }
    
    /**
     * Returns all the units that a player currently owns.
     * 
     * @param playerId : player one would be be 1 for example.
     * @return
     */
    public TreeMap<String,ArrayList<ArmyUnit>> getAllPlayerUnits(int playerId){
        return pool.get(playerId);
    }
    
    /**
     * Returns all units of a player specific units type. For instance if you 
     * wanted all of the players current light swords this function would return 
     * them to you in the form of an array list.
     * 
     * @param playerId
     * @param unitClassName
     * @return 
     */
    public ArrayList<ArmyUnit> getPlayerSpecificUnits(int playerId, String unitClassName){
        return pool.get(playerId).get(unitClassName);
    }
    
    /**
     * Given a unit ID, this method would get tat instance of a ArmyUnit class.
     * A unit ID is typically obtained from parsing a unit out of the 
     * getUnitsInHex method.
     * 
     * @param unitId : The unique ID of army unit.
     * @return 
     */
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
    
    /**
     * This method is only used for junit tests. It ensures a empty unit pool.
     * It should not be used in the game for any reason.
     */
    
    public void clearPool(){
        pool.clear();
    }
    
}
