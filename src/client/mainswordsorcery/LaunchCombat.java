/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package mainswordsorcery;

import MoveCalculator.MovementCalculator;
import Units.ArmyUnit;
import Units.MoveableUnit;
import Units.UnitPool;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.embed.swing.SwingNode;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import sshexmap.MapHex;
import sshexmap.MapView;


import static sscharts.ArmyCombatResultsTable.PrepareAttackResults;


/**
 *
 * @author Shaung
 * 
 * 
 */
public class LaunchCombat {
        static MoveableUnit selected_combat_unit = new MoveableUnit();
        static MoveableUnit target_combat_unit = new MoveableUnit();
        static MoveableUnit selected_combat_unit1 = new MoveableUnit();
        static MoveableUnit target_combat_unit1 = new MoveableUnit();
        static ArrayList<ArmyUnit> attackers = new ArrayList<>();
        static ArrayList<ArmyUnit> defenders = new ArrayList<>();
        static MapHex Defender_Terrain = new MapHex();
        
    public static void LaunchBotton(List selected_stack, List target_stack, MapView hmapContent) {
        
        
        
        
        selected_combat_unit = (MoveableUnit) selected_stack.get(0);
        if (selected_stack.size() > 1)
        selected_combat_unit1 = (MoveableUnit) selected_stack.get(1);
        target_combat_unit = (MoveableUnit) target_stack.get(0);
        if (target_stack.size() > 1)
        target_combat_unit1 = (MoveableUnit) target_stack.get(1);

        
 
        attackers.add((ArmyUnit) selected_combat_unit);
        if (selected_stack.size() > 1)
        attackers.add((ArmyUnit) selected_combat_unit1);
        defenders.add((ArmyUnit) target_combat_unit);
        if (target_stack.size() > 1)
        defenders.add((ArmyUnit) target_combat_unit1);
        
        
        // Set up Defender's TerrainType
        MapHex Atk_Terrain = new MapHex();
        MapView temp = MapView.getMapView();
        Defender_Terrain = (MapHex)temp.GetHexMap().GetHex(target_combat_unit.getLocation());
        Atk_Terrain = (MapHex)temp.GetHexMap().GetHex(selected_combat_unit.getLocation());
        // Add Friendly Unit into Attackers List
        ArrayList<MapHex> FriendlyCombatList = new ArrayList<>();
        FriendlyCombatList = getFriendlyList(Defender_Terrain, Atk_Terrain);
        System.out.println("Numbers of friendly units: " + FriendlyCombatList.size());
        
        for (MapHex FriendlyCombatList1 : FriendlyCombatList) {
            Action Add_into_Attacker_List = Dialogs.create()
            .title("Adding Friendly Units into Combat")
            .message("Do you Want to add friendly unit: " + FriendlyCombatList1.getUnits().get(0).getID().split("[#|@]")[1] + " to the attackers list?")
            .showConfirm();
            
            if (Add_into_Attacker_List == Dialog.Actions.YES) {
                Notifications.create()
                        .title("Add into List")
                        .text("Unit added into Attackers List")
                        .showWarning();
                attackers.add((ArmyUnit) FriendlyCombatList1.getUnits().get(0));
            } else {
                Notifications.create()
                        .title("Discard from List")
                        .text("Unit discard from the Attackers List")
                        .showWarning();
            }
        }

 
        // Get Combat Result Here
        ArrayList result = new ArrayList();
        result = PrepareAttackResults(attackers, defenders, Defender_Terrain);
        int[] index = (int[])result.get(0);
        int atk = (int)result.get(1);
        int def = (int)result.get(2);
        int after_def = (int)result.get(3);
        double ratio = (double)result.get(4);
        
        

        

        // Pop up Combat Result
        Action result_of_combat = Dialogs.create()
          .title("Strategy")
          .message( "Are you sure to enforce this combat?" +
                    "\nAttackers Strength: " + atk + 
                    "\nDefenders Strength: " + def + 
                    "\nDefenders' Terrain Type is: " + Defender_Terrain.getTerrainType() +
                    "\nAfter Terrain Bonus: " + after_def
                  )
          .showConfirm();

        if (result_of_combat == Dialog.Actions.YES) {

            //Confirm(index,Defender_Terrain,target_stack);
            Action ATT = Dialogs.create()
            .title("Attacker")
            .message("Attacker's Decision")
            .showWarning();
            ResultCase(index[0], attackers, target_stack, hmapContent);
            
            
            Action DEF = Dialogs.create()
           .title("Defender")
           .message("Defender's Decision")
           .showWarning();
            ResultCase(index[1], defenders, target_stack, hmapContent);
        }
        
        else {
            

        }

        attackers.clear();
        defenders.clear();
        FriendlyCombatList.clear();
      
    }
    

    
    
    /**
     * @author Shaung
     * @param result 
     */
    public static void ResultCase(int result, ArrayList<ArmyUnit> Units_stack, List target_stack, MapView hmapContent) {
        SwingNode hmap = new SwingNode();
        HashMap<MapHex, Double> moves;
        //MapView hmapContent;
        //hmapContent = MapView.getMapView();
        //hmap.setContent(hmapContent);
        
        // Elimination
        if (result < 0) {
            Notifications.create()
            .title("Elimination Required")
            .text("You have to eliminate " + result + "units.")           
            .showWarning();
            // Call Elimination Function
            // Elimination(List<MoveableUnit> Units_stack, int elimination_amount);
        }
        
        // Nothing Chnage
        else if (result == 0) {
            Notifications.create()
            .title("Stay in current Status")
            .text("You don't need to do any changes from this battle.")           
            .showWarning();
        }
        
        // Retreat or Elimination
        else {
           final UnitPool pool = UnitPool.getInstance();
           Action result_of_combat = Dialogs.create()
          .title("Decision")
          .message( "Result of Combat is: " + result
                  + "\nDo you want to eliminate any Units?")
          .showConfirm();
           
            if (result_of_combat == Dialog.Actions.YES) {
               
                for (int i = 0; i < Units_stack.size(); i++) {
                    Action Edecision = Dialogs.create()
                    .title("Decision")
                    .message( "Result of Combat is: " + result
                           + "\nDo you want to eliminate This Units: " + Units_stack.get(i).getID().split("[#|@]")[1])
                    .showConfirm();
                    if (Edecision == Dialog.Actions.YES) {
                        for (ArmyUnit Units_stack1 : Units_stack) {
                            pool.removeUnit(Units_stack1);
                        } 
                    }
                }
            }
            ArrayList<MapHex> canMoveTo;
            if (!Units_stack.isEmpty()) {
                
                for (int i = 0; i < Units_stack.size(); i++) {
                    // Retreat(Units_stack.get(i));
                    moves = new HashMap<>();
                    moves.clear();
                    
                    
                    canMoveTo = new ArrayList<MapHex>();
                    canMoveTo = MovementCalculator.getRetreatMoves(Defender_Terrain, 
                                  (MoveableUnit)target_stack.get(i), (double)result);
                    
                    hmapContent.clearHighlights();      
                    ((ArmyUnit)Units_stack.get(i)).SetDemoralized(true);
                    pool.addMove(Units_stack.get(i), canMoveTo.get(1).GetID());
                    
                for (MapHex canMoveTo1 : canMoveTo) {
                System.out.println("Can move to: " + (String)canMoveTo1.GetID() + "\n");
                }
                            
                }
            }
            hmapContent.repaint();
        }
           
           
           
           
           
          
           
           
        }
        
        
    
    
    /**
     * 
     * @param currHex
     * @author Shaung & Kidth
     * @return ArrayList<MapHex>
     * 
     */
    public static ArrayList<MapHex> getFriendlyList( MapHex currHex, MapHex atk_terrain )
    {
        ArrayList<MapHex> neighbors = new ArrayList<>();
        //For each hex edge, 0-5, get the neighboring hex, if it's valid
        for( int i = 0; i < 6; i++ )
        {
            // Make sure the neighbor exists
            if( currHex.getNeighbor( i ) != null && currHex.getNeighbor(i).getUnits() != null 
                    && currHex.getNeighbor(i).GetID() != atk_terrain.GetID())
                
                    neighbors.add( currHex.getNeighbor(i));
                
        }
        
        
        return neighbors;
    }
    
    
    
    
}
