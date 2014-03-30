/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package armycombatresultstable;

import Units.Race;
import static armycombatresultstable.ArmyCombatResultsTable.*;
import Units.*;
import ssterrain.*;
import sshexmap.*;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author matt
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        HashMap units = new HashMap();
        MapHex hex1 = new MapHex();
        TerrainType tt1 = new TTWoods();
        hex1.setTerrainType(tt1);
        HeavyHorse unit1 = new HeavyHorse();
        PikeMan unit2 = new PikeMan();
        HeavySword unit3 = new HeavySword();
        
        ArrayList<ArmyUnit> attackers = new ArrayList<>();
        ArrayList<ArmyUnit> defenders = new ArrayList<>();
        
        unit1.setRace(Race.Human);
        unit2.setRace(Race.Cronk);
        unit3.setRace(Race.Human);
        
        units.put("HHH01", unit1);
        units.put("CPM01", unit2);
        units.put("HHS01", unit3);
        
        attackers.add(unit1);
        attackers.add(unit2);
        
        defenders.add(unit3);
        
        int [] results;
        
        results = PrepareAttackResults(attackers, defenders, hex1);
        
        System.out.println("Attackers: " + results[0]);
        System.out.println("Defenders: " + results[1]);
        
        
    }
}
