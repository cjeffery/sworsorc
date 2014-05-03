/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//**\UnitRender*,**\NetworkClientT*,**\Hex*Test*,**\Map*Test*,**\Scen*Test*,**\Movement*Test*
package Units;


import Units.Stack; // used on line 213
import java.util.*;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import sshexmap.MapView;
import systemServer.Flag;
import systemServer.NetworkClient;
import systemServer.Tag;

/**
 * Unit pool tracks all the units that have been created in the game.  
 * Unit pool is a singleton that is created by calling getInstance().
 * 
 * In UnitPoolTest are some example cast for anyone who needs ArmyUnit.
 * 
 * Example
 * void someMeathod (MovibalUnit mUnit){
 *
 * unit.setLocation("0534")
 *     if( unit instanceof ArmyUnit){
 *         ArmyUnit aUnit = (ArmyUnit)mUnit;
 *         aUnit.attack(); // Now you can call attack.
 *     }
 * }
 * 
 * @author David Klingenberg
 */

public class UnitPool {   
    private final SortedMap<Integer, TreeMap<String,ArrayList<MoveableUnit>>> 
            pool = Collections.synchronizedSortedMap(new TreeMap<Integer, 
                    TreeMap<String,ArrayList<MoveableUnit>>>());
    private final SortedMap<String, ArrayList<String>> hexList = 
            Collections.synchronizedSortedMap(new TreeMap<String, ArrayList<String>>());
    private final SortedMap<String, ArrayList<String>> unitMove = 
            Collections.synchronizedSortedMap(new TreeMap<String, ArrayList<String>>());
    private final Object[] options = {"Yes","No",};
    private final SortedMap<String, Integer> portalNum = 
            Collections.synchronizedSortedMap(new TreeMap<String, Integer>());
    private final List<String> safeTeleport = 
            Collections.synchronizedList(new ArrayList<String>());
    private final SortedMap<String, ArrayList<MoveableUnit>> overStackMap = 
            Collections.synchronizedSortedMap(new TreeMap<String, ArrayList<MoveableUnit>>());
    
    private int portNum = 0; 
    private static UnitPool INSTANCE;
    
    private HexStack stack = new HexStack();
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
     * Used with the teleport spells;
     * 
     * @return 
     */
    public int getTeleportDestination(String unitID){
        return this.portalNum.get(unitID);
        
    }
    
    /**
     * Used with the teleport spells;
     * 
     * @param unitID
     * @param portal 
     */
    public void setTeleportDestination(ArrayList<String> unitID, int portal){
        for(String temp : unitID)
            this.portalNum.put(temp, portal); 
    }
    
    /**
     * Used with the teleport spells;
     * 
     * @return 
     */
    public boolean getSafeTeleport(String unitID){
        if (this.safeTeleport.contains(unitID))
            return true;
        else
            return false;
    }
    
    /**
     * Used in unit move. 
     * 
     * @param unitID
     * @param teleportIsSafe 
     */
    public void setSafeTeleport(ArrayList<String> unitID, boolean teleportIsSafe){
        for (String temp : unitID)
            this.safeTeleport.add(temp);
    }
    
    
    /**
     * Used to add a new unit to the pool with an initial location.*
     * 
     * @param playerID
     * @param unit
     * @param location : the hex the unit occupies. 
     */        
    public void addUnit(int playerID, MoveableUnit unit, String location){
        unit.ResetWorkingMovement();
        unit.setLocation(location);
        this.addUnit(playerID, unit);
        this.addToHex(hexList, unit);
        this.addToUnit(unitMove, unit);
        MapView.getMapView().repaint();
    }
    
    /**
     * Removes a MoveableUnit from the UnitPool.
     * 
     * Example:
     * 
     *  pool.removeUnit(getUnitsInHex("1010").get(0);
     * 
     * @param unit : the MoveableUnit to be removed.  
     */
    public void removeUnit(MoveableUnit unit){
        int playerID, positionAtChar, positionHashChar, listSize;
        String MovableUnitClassName;
        ArrayList<MoveableUnit> unitList; 
        boolean unitFound = false;
        
        playerID = Integer.parseInt(unit.getID().substring(0, 1));
        positionAtChar = unit.getID().indexOf("@");
        positionHashChar = unit.getID().indexOf("#");
        MovableUnitClassName = unit.getID().substring(++positionHashChar, positionAtChar);
        
        unitList = pool.get(playerID).get(MovableUnitClassName);
        listSize = unitList.size() - 1;
        
        for (int i = 0; i <= listSize && !unitFound; i++){
            if (unitList.get(i).getID().equals(unit.getID())){
                this.hexList.get(unit.getLocation()).remove(unit.getID());
                this.unitMove.remove(unit.getID());
                unitList.remove(i);
                
                unitFound = true;
            }
                
        }

        // Removes unit from the pools of all connected players
        NetworkClient.send( Flag.GAME, Tag.REMOVE_UNIT, unit );
        
    }
    
    /**
     * Used to add a new unit to the pool.
     * Example:
     * pool.addUnit (1, new LightSword, "0101");
     * 
     * @param playerId
     * @param unit     : an instance of some army unit  
     */
    public void addUnit(int playerId, MoveableUnit unit){
       unit.setID(Integer.toString(playerId) + "#" + unit.toString() + 
               "@" + Integer.toString(unit.hashCode()));
       
       
        
       if (pool.containsKey(playerId)){
           if(pool.get(playerId). containsKey(unit.toString()))
               pool.get(playerId).get(unit.toString()).add(unit);
            else{
               ArrayList<MoveableUnit> unitList = new ArrayList();
                
               unitList.add(unit);
               pool.get(playerId).put(unit.toString(),unitList);
            }
       }
       else{ 
           ArrayList<MoveableUnit> unitList = new ArrayList();
           TreeMap<String, ArrayList<MoveableUnit>> unitMap = new TreeMap();
            
           unitList.add(unit);
           unitMap.put(unit.toString(), unitList);
           pool.put(playerId, unitMap);
        }

        // Send unit being added to the rest of the players
        // Does we need to send playerID as well? Seems to be included in the units information
        NetworkClient.send( Flag.GAME, Tag.ADD_UNIT, unit );
    }
    
    /**
     * This adds a unit to the hex list tree.
     * 
     * @param tree : this should be hexList tree.
     * @param unit : the unit to be added
     */
    private void addToHex(SortedMap<String, ArrayList<String>> tree, MoveableUnit unit) {
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
     * This adds a unit to the hex move tree.
     * 
     * @param tree : this should be hex move tree.
     * @param unit : the unit whose new location is being added to 
     *               its array list.
     */
    
    private void addToUnit(SortedMap<String, ArrayList<String>> tree, MoveableUnit unit) {
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
     * @param destinationHexID
     */
    
    
    
    
    public void addMove(MoveableUnit unit, String destinationHexID){
        
       
        // This horific looking line removes the unit from its current location.
        hexList.get(unit.getLocation()).remove(hexList.get(unit.getLocation()).indexOf(unit.getID()));
        
        unit.setLocation(destinationHexID);
        this.addToHex(hexList, unit);
        this.addToUnit(unitMove, unit);
        HexStack.overStackWaring(this.getUnitsInHex(unit.getLocation()));
        
        ArrayList<MoveableUnit> temp = new ArrayList<MoveableUnit>();
        ArrayList<String> hold = new ArrayList<String>();
        hold = this.getUnitsInHex(destinationHexID);                
        for(String s : hold)
            temp.add(this.getUnit(s));
        
        //stack.removeOverStack(temp);
        
        if ( "2004".equals(unit.getLocation()) || 
             "0912".equals(unit.getLocation()) || 
             "0627".equals(unit.getLocation()) || 
             "3427".equals(unit.getLocation()) || 
             "1044".equals(unit.getLocation()) || 
             "3542".equals(unit.getLocation()) ){
            
            Action response = Dialogs.create()
                .title("Teleport")
                .masthead("Just Checkin'")
                .message("Would you like to teleport?")
                .actions(Dialog.Actions.YES,Dialog.Actions.CANCEL)    
                .showConfirm()
                ;
            

            
            
            
                if ( response == Dialog.Actions.YES )
                    if (this.teleport(unit))
                        Dialogs.create()
                        .title("Teleport")
                        .masthead("Lucky Dog!")
                        .message("Unit Teleported to hex " + unit.getLocation() + ".")
                        .actions(Dialog.Actions.OK)
                        .showConfirm();

                    else
                        Dialogs.create()
                        .title("Teleport")
                        .masthead("In the infamous words of Homer Simpson.  Doh!")
                        .message("A heard of rampaging "
                                + "ethereal cows trampled your unit to death.  You "
                                + "should inform the next of kin.")
                        .actions(Dialog.Actions.OK)
                        .showConfirm();
        }
    }
    /**
     * Only used by the teleport method. 
     * @param unit
     * @param destinationHexID
     * @param Teleport 
     */
    private void addMove(MoveableUnit unit, String destinationHexID, boolean Teleport){
        
        // This horific looking line removes the unit from its current location.
        hexList.get(unit.getLocation()).remove(hexList.get(unit.getLocation()).indexOf(unit.getID()));
        
        unit.setLocation(destinationHexID);
        this.addToHex(hexList, unit);
        this.addToUnit(unitMove, unit);
    
    }
    
    /**
     * Is used to update the location of all the units in a hex.
     * 
     * Example:
     * addMoveStack("0101","2019");
     * 
     * @param StartHexID
     * @param destinationHexID 
     */
    
    public void addMoveStack(String StartHexID,String destinationHexID){
        MoveableUnit unit;
        ArrayList<String> list = this.getUnitsInHex(StartHexID);
        
        unit = this.getUnit(list.get(0));
        
        hexList.get(unit.getLocation()).remove(hexList.get(unit.getLocation()).indexOf(unit.getID()));
        unit.setLocation(destinationHexID);
        this.addToHex(hexList, unit);
        this.addToUnit(unitMove, unit);
        
        if (list.size() > 0)
            addMoveStack(StartHexID,destinationHexID);
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
    public TreeMap<String,ArrayList<MoveableUnit>> getAllPlayerUnits(int playerId){
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
    public ArrayList<MoveableUnit> getPlayerSpecificUnits(int playerId, 
            String unitClassName){
        return pool.get(playerId).get(unitClassName);
    }
    
    /**
     * Given a unit ID, this method would get the instance of a MoveableUnit class.
     * A unit ID is typically obtained from parsing a unit out of the 
     * getUnitsInHex method.
     * 
     * @param unitId : The unique ID of army unit.
     * @return 
     */
    public MoveableUnit getUnit(String unitId){
        int playerId;
        String unitClass;
        MoveableUnit unit;
        
        playerId = Character.getNumericValue(unitId.charAt(0));
        unitClass = unitId.substring(unitId.indexOf("#") + 1, unitId.indexOf("@"));
        
        boolean test = false;
        int i = 0;
        
        do {
                    unit = pool.get(playerId).get(unitClass).get(i);
                    test = unit.getID().equals(unitId);
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
    public void clear(){
        this.pool.clear();
        this.hexList.clear();
        this.unitMove.clear();
        this.portalNum.clear();
        this.safeTeleport.clear();
    }
    
    /**
     * This function sets up the units for their next movement phase. It sets 
     * the only hex in the list to the last hex entered.
     */
    public void endMovementPhase(){
        
        for (Map.Entry<String, ArrayList<String>> entry : this.unitMove.entrySet()){
            if (!overStackMap.containsKey(this.getUnit(entry.getKey()).getLocation())){
                for(Map.Entry<String, ArrayList<String>> entry2 : this.hexList.entrySet()){
                    if (entry2.getValue().size() > 2){
                        ArrayList<MoveableUnit> units = new ArrayList<MoveableUnit>();

                        for(String temp : entry2.getValue()){
                            units.add(this.getUnit(temp));

                        }
                        this.overStackMap.put(units.get(0).getLocation(), units);
                    }    
                }
            }
            if (entry.getValue().size() > 1){
               entry.getValue().remove(0);
               endMovementPhase();
            }
            this.getUnit(entry.getKey()).ResetWorkingMovement();
        }
    }
    
    
    public SortedMap<String, ArrayList<MoveableUnit>> getOverStack(){
        return this.overStackMap;
    }
    
    /**
     * Return the unit to its starting position.
     * 
     * 
     * @param unitID
     * @return 
     */
    public String undoMove(String unitID){
        
        if (this.unitMove.get(unitID).size() > 1){
            this.getUnit(unitID).setLocation(unitMove.get(unitID).get(0));
            this.unitMove.get(unitID).clear();
            this.unitMove.get(unitID).add(getUnit(unitID).getLocation());
        }
          this.getUnit(unitID).ResetWorkingMovement();
        
        return this.unitMove.get(unitID).get(0);
    }
    
    /**
     * Unsafe teleport. Risk of unit destruction.
     * 
     * Example:
     * successfulTeleport = teleport(SomeUnit);
     * 
     * @param unit
     * @return 
     */
    //public boolean teleport(MoveableUnit unit){
    //    return this.teleport(unit, false, 7);
    //}
    
    /**
     * Safe teleport used with the teleport spell.
     * 
     * Example:
     *   successfulTeleport = teleport(someUnit, true, destinationPortal);
     * (optional) or  teleport(someUnit, true, destinationPortal);
     * 
     * @param unit
     * @param safeTeleport
     * @param portalNum
     * @return 
     */
    public boolean teleport(MoveableUnit unit){
        String destinationHex;
        Random rNum = new Random();
        
        if (!this.safeTeleport.contains(unit.getID())){
            
            //portNum = rNum.nextInt(6);
            //portalNum = 1;
            
            destinationHex = teleportDestinationLogic(rNum.nextInt(6));
            
            if (destinationHex.equals(unit.getLocation())){
                removeUnit(unit);
                return false;
            }
            this.addMove(unit, destinationHex, true);
            return true;
        
        }
        else{
            if (this.portalNum.containsKey(unit.getID())){
                destinationHex = this.teleportDestinationLogic(portalNum.get(unit.getID()));
                    if (unit.getLocation().equals(destinationHex) )
                        return false;
            
                    else{
                        this.addMove(unit, destinationHex, true);
                        return true;
                    }
                }
            else{
                destinationHex = teleportDestinationLogic(rNum.nextInt(6));
                this.addMove(unit, destinationHex,true);
                return true;
            }
        }        
    }

    /**
     * Used to get hexID from portal Number for the teleport methods.
     * @param destinationPortal
     * @return 
     */
    
    private String teleportDestinationLogic(int destinationPortal) {
        String destinationHex = "";
        switch (destinationPortal){
            case 0 :
                destinationHex = "2004";
                break;
            case 1 :
                destinationHex = "0912";
                break;
            case 2 :
                destinationHex = "0627";
                break;
            case 3 :
                destinationHex = "3427";
                break;
            case 4 :
                destinationHex = "1044";
                break;
            case 5 :
                destinationHex = "3542";
                break;
        }
        return destinationHex;
    
    
    }
    
}
