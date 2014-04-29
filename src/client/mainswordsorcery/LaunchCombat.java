/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package mainswordsorcery;

import Units.ArmyUnit;
import Units.MoveableUnit;
import Units.UnitType;
import java.util.ArrayList;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;
import static sscharts.ArmyCombatResultsTable.PrepareAttackResults;
import sshexmap.MapHex;
import ssterrain.TTSwamp;
import ssterrain.TerrainType;

/**
 *
 * @author Shaung
 */
public class LaunchCombat {
    

    public static void LaunchBotton(MoveableUnit selected_unit, MoveableUnit target_unit) {
    System.out.println(target_unit.getRace());
        System.out.println(target_unit.getNation());
        
        MoveableUnit selected_combat_unit = new MoveableUnit();
        MoveableUnit target_combat_unit = new MoveableUnit();
        selected_combat_unit = selected_unit;
        target_combat_unit = target_unit;
        selected_combat_unit.setUnitType(UnitType.ArmyUnit);
        target_combat_unit.setUnitType(UnitType.ArmyUnit);
        
        ArrayList<ArmyUnit> attackers = new ArrayList<>();
        ArrayList<ArmyUnit> defenders = new ArrayList<>();
 
        attackers.add((ArmyUnit) selected_combat_unit);
        attackers.add((ArmyUnit) selected_combat_unit);
        defenders.add((ArmyUnit) target_combat_unit);
        defenders.add((ArmyUnit) target_combat_unit);
        MapHex hex1 = new MapHex();
        TerrainType tt1 = null;
        tt1 = new TTSwamp();
        hex1.setTerrainType(tt1);  
        target_combat_unit.getLocation();
        ArrayList result = new ArrayList();
        result = PrepareAttackResults(attackers, defenders, hex1);
        int[] index = (int[])result.get(0);
        int atk = (int)result.get(1);
        int def = (int)result.get(2);
        int after_def = (int)result.get(3);
        double ratio = (double)result.get(4);

        Notifications.create()
              .title("Details of Combat")
              .text("attacker: " + atk + "\ndefender: " + def + "\nAfter Def: " + after_def)
              .showWarning();
 
        
        Action result_of_combat = Dialogs.create()
          .title("Combat Comfirmation")
          .message( "Are you sure to enforce the combat?")
          .showConfirm();

        
        Notifications.create()
        .title("Result of Combat")
        .text("Attackets: Stay.\nDefenders: Retreat " + index[1] + " Units.")
        .showWarning();
    }
}
