/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package mainswordsorcery;

import Units.ArmyUnit;
import Units.MoveableUnit;
import java.util.ArrayList;
import java.util.List;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import static sscharts.ArmyCombatResultsTable.PrepareAttackResults;
import sshexmap.MapHex;
import sshexmap.MapView;


/**
 *
 * @author Shaung
 * 
 */
public class LaunchCombat {
    

    public static void LaunchBotton(List selected_stack, List target_stack) {

  
        MoveableUnit selected_combat_unit = new MoveableUnit();
        MoveableUnit target_combat_unit = new MoveableUnit();
        MoveableUnit selected_combat_unit1 = new MoveableUnit();
        MoveableUnit target_combat_unit1 = new MoveableUnit();
        
        selected_combat_unit = (MoveableUnit) selected_stack.get(0);
        if (selected_stack.size() > 1)
        selected_combat_unit1 = (MoveableUnit) selected_stack.get(1);
        target_combat_unit = (MoveableUnit) target_stack.get(0);
        if (target_stack.size() > 1)
        target_combat_unit1 = (MoveableUnit) target_stack.get(1);

        ArrayList<ArmyUnit> attackers = new ArrayList<>();
        ArrayList<ArmyUnit> defenders = new ArrayList<>();
 
        attackers.add((ArmyUnit) selected_combat_unit);
        if (selected_stack.size() > 1)
        attackers.add((ArmyUnit) selected_combat_unit1);
        defenders.add((ArmyUnit) target_combat_unit);
        if (target_stack.size() > 1)
        defenders.add((ArmyUnit) target_combat_unit1);
        
        
        // Set up Defender's TerrainType
        MapHex Defender_Terrain = new MapHex();
        MapView temp = MapView.getMapView();
        Defender_Terrain = (MapHex)temp.GetHexMap().GetHex(selected_combat_unit.getLocation());
        
        // Add Friendly Unit into Attackers List
        ArrayList<MapHex> FriendlyCombatList = new ArrayList<>();
        FriendlyCombatList = getFriendlyList(Defender_Terrain);
        System.out.println("Numbers of friendly units: " + FriendlyCombatList.size());
        
        for (int i = 0; i < FriendlyCombatList.size(); i++) {
            Action Add_into_Attacker_List = Dialogs.create()
            .title("Adding Friendly Units into Combat")
            .message( "Do you Want to Add" + FriendlyCombatList.get(i).getUnits().get(0).getID() + " to the attackers list?")
            .showConfirm();
 
        
            if (Add_into_Attacker_List == Dialog.Actions.YES) {
                Notifications.create()
                .title("Add into List")
                .text("Unit added into Attackers List")           
                .showWarning();
                attackers.add((ArmyUnit)FriendlyCombatList.get(i).getUnits().get(0));
            } 
            else {
                Notifications.create()
                .title("Discard from List")
                .text("Unit discard from the Attackers List")           
                .showWarning();
            }
            
        
        }

        
        // Details of the Attackers and Defenders List
        Notifications.create()
        .title("Details of List")
        .text("Attacker 1 Race: " + selected_combat_unit.getRace() + "\nAttacker 2 Race: " + selected_combat_unit1.getRace() +
              "\nAttacker 1 ID: " + selected_combat_unit.getID() + "\nAttacker 2 ID: " + selected_combat_unit1.getID()        
             )
        .showWarning();
        
        
        
        

        
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
        
        
        
        
        if (index[0] == 0 && index[1] == 0) {
            Notifications.create()
            .title("Result of Combat: Draw")
            .text("Love and Peace")
            .showWarning();
        }
        
        else {
            if (index[0] == 0) {
            Notifications.create()
            .title("Result of Combat: Attacker")
            .text("Victory!")
            .showWarning();
            }
            if (index[0] < 0) {
            Notifications.create()
            .title("Result of Combat: Attacker")
            .text("Attacker eliminates " + index[0] + " unit.")
            .showWarning();
            }
            if (index[0] > 0) {
            Notifications.create()
            .title("Result of Combat: Attacker")
            .text("Attacker retreat " + index[0] + " unit.")
            .showWarning();
            }
            if (index[1] == 0) {
            Notifications.create()
            .title("Result of Combat: Defender")
            .text("Victory!")
            .showWarning();
            }
            if (index[1] < 0) {
            Notifications.create()
            .title("Result of Combat: Defender")
            .text("Defender eliminates " + index[1] + " unit.")
            .showWarning();
            }
            if (index[1] > 0) {
            Notifications.create()
            .title("Result of Combat: Defender")
            .text("Defender retreat " + index[1] + " unit.")
            .showWarning();
            }
        }
        
        }
        else {
            // Do nothing
        }
        
    }
    /**
     * 
     * @param currHex
     * @author Shaung & Kidth
     * @return ArrayList<MapHex>
     * 
     */
    public static ArrayList<MapHex> getFriendlyList( MapHex currHex )
    {
        ArrayList<MapHex> neighbors = new ArrayList<>();
        //For each hex edge, 0-5, get the neighboring hex, if it's valid
        for( int i = 0; i < 6; i++ )
        {
            // Make sure the neighbor exists
            if( currHex.getNeighbor( i ) != null && currHex.getNeighbor(i).getUnits() != null)
                neighbors.add( currHex.getNeighbor(i));
        }
        
        
        return neighbors;
    }
    
    
    
    
}
