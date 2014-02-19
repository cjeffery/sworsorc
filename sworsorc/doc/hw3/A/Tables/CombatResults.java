/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Random;

// package combatresults;

/**
 *
 * @author Tyler
 */
public class CombatResults {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] results = new int[2];
        
        System.out.println("Attacking with strength 11, leadership 2 vs. strength 6 leadership 5");
        results = GetCombatResults(11,6,2,5);
        System.out.printf("Attacker undergoes loss of %d; Defender loses %d\n", results[0], results[1]);
        
        System.out.println("Attacking with strength 12, leadership 1 vs. strength 6 leadership 1");
        results = GetCombatResults(12,6,1,1);
        System.out.printf("Attacker undergoes loss of %d; Defender loses %d\n", results[0], results[1]);
        
        System.out.println("Attacking with strength 4, leadership 6 vs. strength 9 leadership 1");
        results = GetCombatResults(4,9,6,1);
        System.out.printf("Attacker undergoes loss of %d; Defender loses %d\n", results[0], results[1]);
    }
    
    @SuppressWarnings("empty-statement")
    // This function returns a two-member array giving the results for the attacker and the defender
    // a value of 8 means Elimination.
    public static int[] GetCombatResults(int attackStrength, int defenseStrength, int attackLeadership, int defenseLeadership) {
        // change this flag however you like
        int debug = 0;
        
        // a few tables containing the combat result data
        int[][] AttackResults = {
            {2, 3, 4, 8, 8, 8},
            {1, 2, 3, 4, 8, 8},
            {1, 1, 2, 3, 3, 8},
            {1, 1, 1, 2, 2, 3},
            {0, 1, 1, 1, 2, 2},
            {2, 0, 1, 1, 1, 2},
            {0, 2, 0, 1, 1, 1},
            {0, 0, 2, 0, 1, 1},
            {0, 0, 0, 1, 0, 1},
            {0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0}
        };
        int [][] DefenseResults = {
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 0},
            {1, 1, 1, 0, 0, 0},
            {1, 1, 1, 1, 0, 0},
            {2, 1, 1, 1, 1, 0},
            {3, 2, 1, 1, 1, 1},
            {4, 3, 3, 2, 1, 1},
            {8, 4, 3, 2, 2, 1},
            {8, 8, 4, 3, 2, 2},
            {8, 8, 8, 4, 3, 2}
        };
        
        // find the index of of the closest ratio on the table to the actual ratio of attackers to defenders
        double ratio;
        ratio = (double)attackStrength/defenseStrength;
        int ratioIndex = closestRatio(ratio);
        if (debug != 0) { System.out.printf("Ratio Index: %d\n", ratioIndex); }
        
        // get the leadership shift and accommodate
        int leaderShift = attackLeadership - defenseLeadership;
        ratioIndex += leaderShift;
        if (ratioIndex > 11) {
            ratioIndex = 11;
        }
        if (debug != 0) { System.out.printf("Leadership shift brought the ratio index to %d\n", ratioIndex); }
        
        // get the index of the die roll
        Random rnd = new Random();
        int dieRoll = rnd.nextInt(6);
        if (debug != 0) { System.out.printf("Die roll: %d\n", dieRoll); }
        
        // use this indices to look up resutls in table
        if (debug != 0) { System.out.printf("[%d][%d]", ratioIndex, dieRoll); };
        int AttackResult = AttackResults[ratioIndex][dieRoll];
        int DefenseResult = DefenseResults[ratioIndex][dieRoll];
        int[] Results = new int[2];
        Results[0] = AttackResult;
        Results[1] = DefenseResult;
        return Results;        
    }
    
    // gives the closest table ratio based on the given ratio
    public static int closestRatio(double given) {
        double[] ratio = {0.2f, 0.25f, 0.33f, 0.5f, 0.66f, 1f, 1.5f, 2f, 3f, 4f, 5f, 6f};
        int i, index=0;
        for(i=0; i < 12; i++) {
            // if exact match found, return it
            if (ratio[i] == given) {
                index = i;
                break;
            }
            // otherwise, find the first ratio greater than the given, and return the one below it
            if (ratio[i] > given) {
                index = i-1; // round down in favor of defense
                break;
            }
        }
        return index;
    }
    
    
}
