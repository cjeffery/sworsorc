/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package armycombatresultstable;

import static armycombatresultstable.ArmyCombatResultsTable.*;
import Units.*;
import ssterrain.*;
import java.util.ArrayList;


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
        Hex hex1 = new Hex();
        TerrainType tt1 = new TTWoods();
        hex1.setTerrainType(tt1);
        HeavyHorse unit1 = new HeavyHorse();
        Pikeman unit2 = new Pikeman();
        HeavySword unit3 = new HeavySword();
        
        ArrayList<Unit> attackers = new ArrayList<>();
        ArrayList<Unit> defenders = new ArrayList<>();
        
        unit1.setRace(Race.Human);
        unit2.setRace(Race.Cronk);
        unit3.setRace(Race.Human);
        
        attackers.add(unit1);
        attackers.add(unit2);
        
        defenders.add(unit3);
        
        int [] results;
        
        results = PrepareAttackResults(attackers, defenders, hex1);
        
        System.out.println("Attackers: " + results[0]);
        System.out.println("Defenders: " + results[1]);
    }
}
