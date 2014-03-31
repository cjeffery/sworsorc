/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package armycombatresultstable;

/**
 *
 * @author Matthew Brown
 * This program is used to simulate the Army Combat Results Table From swords
 * and sorcery. On the table there are number values as well as '-''s and 'E''s 
 * the '-' will be represented with a 0 and the 'E' with a -1.
 */

import java.util.Random;
import java.util.ArrayList;
import ssterrain.*;
import Units.*;

        
public class ArmyCombatResultsTable {
    
    
    public static int [] PrepareAttackResults(ArrayList<ArmyUnit> defencers, ArrayList<ArmyUnit> defenders, 
            Hex defHex){
        int atk = 0;
        int def = 0;
        int after_def = 0;
        int index;
        double ratio;
        
        for (ArmyUnit attacker : defencers) {
            atk += attacker.getStrength();
        }
        
        System.out.println("Total Attackers Strength: " + atk);
        
        
        for (ArmyUnit defender : defenders){
            def += defender.getStrength();
        }
        after_def = def;
        System.out.println("Total Defenders Strength before terrain bonus: " + def);
        
        after_def *= defHex.getCombatMultiplier(defenders.get(0));
        
        System.out.println("Total Defenders Strength after terrain bonus: " + after_def);
        
        ratio = (double)atk/(double)after_def;
        
        System.out.println("Ratio: " + atk + "/" + after_def);
        System.out.println("Ratio: " + ratio);
        
        Show(atk, def, after_def,ratio);
        
        index = 12;
        if(ratio < 6)
            index--;
        if(ratio < 5)
            index--;
        if(ratio < 4)
            index--;
        if(ratio < 3)
            index--;
        if(ratio < 2)
            index--;
        if(ratio < 1.5)
            index--;
        if(ratio < 1)
            index--;
        if(ratio < 2/3)
            index--;
        if(ratio < 1/2)
            index--;
        if(ratio < 1/3)
            index--;
        if(ratio < 1/4)
            index--;
        
        System.out.println("Index: " + index);       
        
        return TableLookup(index);
        
        /*
        ratio = index
        0.20  = 1
        0.25  = 2
        0.33  = 3
        0.50  = 4
        0.67  = 5
        1.00  = 6
        1.50  = 7
        2.00  = 8
        3.00  = 9
        4.00  = 10
        5.00  = 11
        6.00  = 12
        */
    }

    public static void Show(int atk, int def, int after_def,double ratio) {
        javax.swing.JOptionPane.showMessageDialog(null,"Total Attackers Strength: " + atk + 
                         "\nTotal Defenders Strength before terrain bonus: " + def +
                         "\nTotal Defenders Strength after terrain bonus: " + after_def + 
                         "\nRatio: " + atk + "/" + after_def +
                         "\nRatio: " + ratio);
    }
    
    public static int [] TableLookup(int index){
        int [] results = new int[2];
        
        Random rand = new Random();
        
        int diceRoll = rand.nextInt(5) + 1;
        
        
        System.out.println("Dice Roll: " + diceRoll);
        
        switch(index){
            case 1:
                switch(diceRoll){
                    case 1:
                        results[0] = 2;
                        results[1] = 0;
                        return results;
                    case 2:
                        results[0] = 3;
                        results[1] = 0;
                        return results;
                    case 3:
                        results[0] = 4;
                        results[1] = 0;
                        return results;
                    case 4:
                        results[0] = -1;
                        results[1] = 0;
                        return results;
                    case 5:
                        results[0] = -1;
                        results[1] = 0;
                        return results;
                    case 6:
                        results[0] = -1;
                        results[0] = 0;
                        return results;
                }
            break;
            case 2:
                switch(diceRoll){
                    case 1:
                        results[0] = 1;
                        results[1] = 0;
                        return results;
                    case 2:
                        results[0] = 2;
                        results[1] = 0;
                        return results;
                    case 3:
                        results[0] = 3;
                        results[1] = 0;
                        return results;
                    case 4:
                        results[0] = 4;
                        results[1] = 0;
                        return results;
                    case 5:
                        results[0] = -1;
                        results[1] = 0;
                        return results;
                    case 6:
                        results[0] = -1;
                        results[0] = 0;
                        return results;
                }
            case 3:
                switch(diceRoll){
                    case 1:
                        results[0] = 1;
                        results[1] = 1;
                        return results;
                    case 2:
                        results[0] = 2;
                        results[1] = 0;
                        return results;
                    case 3:
                        results[0] = 3;
                        results[1] = 0;
                        return results;
                    case 4:
                        results[0] = 4;
                        results[1] = 0;
                        return results;
                    case 5:
                        results[0] = -1;
                        results[1] = 0;
                        return results;
                    case 6:
                        results[0] = -1;
                        results[0] = 0;
                        return results;
                }
            case 4:
                switch(diceRoll){
                    case 1:
                        results[0] = 1;
                        results[1] = 1;
                        return results;
                    case 2:
                        results[0] = 1;
                        results[1] = 1;
                        return results;
                    case 3:
                        results[0] = 1;
                        results[1] = 0;
                        return results;
                    case 4:
                        results[0] = 1;
                        results[1] = 0;
                        return results;
                    case 5:
                        results[0] = 2;
                        results[1] = 0;
                        return results;
                    case 6:
                        results[0] = 3;
                        results[0] = 0;
                        return results;
                }
            case 5:
                switch(diceRoll){
                    case 1:
                        results[0] = 0;
                        results[1] = 1;
                        return results;
                    case 2:
                        results[0] = 1;
                        results[1] = 1;
                        return results;
                    case 3:
                        results[0] = 1;
                        results[1] = 0;
                        return results;
                    case 4:
                        results[0] = 1;
                        results[1] = 0;
                        return results;
                    case 5:
                        results[0] = 2;
                        results[1] = 0;
                        return results;
                    case 6:
                        results[0] = 2;
                        results[0] = 0;
                        return results;
                }
            case 6:
                switch(diceRoll){
                    case 1:
                        results[0] = 2;
                        results[1] = 1;
                        return results;
                    case 2:
                        results[0] = 0;
                        results[1] = 1;
                        return results;
                    case 3:
                        results[0] = 1;
                        results[1] = 1;
                        return results;
                    case 4:
                        results[0] = 1;
                        results[1] = 1;
                        return results;
                    case 5:
                        results[0] = 1;
                        results[1] = 0;
                        return results;
                    case 6:
                        results[0] = 2;
                        results[0] = 0;
                        return results;
                }
            case 7:
                switch(diceRoll){
                    case 1:
                        results[0] = 0;
                        results[1] = 2;
                        return results;
                    case 2:
                        results[0] = 2;
                        results[1] = 1;
                        return results;
                    case 3:
                        results[0] = 0;
                        results[1] = 1;
                        return results;
                    case 4:
                        results[0] = 1;
                        results[1] = 1;
                        return results;
                    case 5:
                        results[0] = 1;
                        results[1] = 1;
                        return results;
                    case 6:
                        results[0] = 1;
                        results[0] = 0;
                        return results;
                }
            case 8:
                switch(diceRoll){
                    case 1:
                        results[0] = 0;
                        results[1] = 3;
                        return results;
                    case 2:
                        results[0] = 0;
                        results[1] = 2;
                        return results;
                    case 3:
                        results[0] = 2;
                        results[1] = 1;
                        return results;
                    case 4:
                        results[0] = 0;
                        results[1] = 1;
                        return results;
                    case 5:
                        results[0] = 1;
                        results[1] = 1;
                        return results;
                    case 6:
                        results[0] = 1;
                        results[0] = 1;
                        return results;
                }
            case 9:
                switch(diceRoll){
                    case 1:
                        results[0] = 0;
                        results[1] = 4;
                        return results;
                    case 2:
                        results[0] = 0;
                        results[1] = 3;
                        return results;
                    case 3:
                        results[0] = 0;
                        results[1] = 3;
                        return results;
                    case 4:
                        results[0] = 1;
                        results[1] = 2;
                        return results;
                    case 5:
                        results[0] = 0;
                        results[1] = 1;
                        return results;
                    case 6:
                        results[0] = 1;
                        results[0] = 1;
                        return results;
                }
            case 10:
                switch(diceRoll){
                    case 1:
                        results[0] = 0;
                        results[1] = -1;
                        return results;
                    case 2:
                        results[0] = 0;
                        results[1] = 4;
                        return results;
                    case 3:
                        results[0] = 0;
                        results[1] = 3;
                        return results;
                    case 4:
                        results[0] = 0;
                        results[1] = 2;
                        return results;
                    case 5:
                        results[0] = 1;
                        results[1] = 2;
                        return results;
                    case 6:
                        results[0] = 0;
                        results[0] = 1;
                        return results;
                }
            case 11:
                switch(diceRoll){
                    case 1:
                        results[0] = 0;
                        results[1] = -1;
                        return results;
                    case 2:
                        results[0] = 0;
                        results[1] = -1;
                        return results;
                    case 3:
                        results[0] = 0;
                        results[1] = 4;
                        return results;
                    case 4:
                        results[0] = 0;
                        results[1] = 3;
                        return results;
                    case 5:
                        results[0] = 0;
                        results[1] = 2;
                        return results;
                    case 6:
                        results[0] = 1;
                        results[0] = 2;
                        return results;
                }
        }
        
        if (index >= 12){
            switch(diceRoll){
                    case 1:
                        results[0] = 0;
                        results[1] = -1;
                        return results;
                    case 2:
                        results[0] = 0;
                        results[1] = -1;
                        return results;
                    case 3:
                        results[0] = 0;
                        results[1] = -1;
                        return results;
                    case 4:
                        results[0] = 0;
                        results[1] = 4;
                        return results;
                    case 5:
                        results[0] = 0;
                        results[1] = 3;
                        return results;
                    case 6:
                        results[0] = 0;
                        results[0] = 2;
                        return results;
                }
        }
        return results;
    }    
}
