/**
 *
 * @author Matthew Brown
 *
 * This program is used to simulate the Army Combat Results Table From swords
 * and sorcery. On the table there are number values as well as '-''s and 'E''s 
 * the '-' will be represented with a 0 and the 'E' with a -1.
 */

import java.util.Random;
        
public class ArmyCombatResultsTable {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int [] results;
        results = TableLookup(4,1);
        
        System.out.println("Attacker: " + results[0] + " Defender: " + results[1]);
    }
    
    public static int [] TableLookup(int Attacker, int Defender){
        int [] results = new int[2];
        int ratio = (int)Math.round(Attacker/(double)Defender * 100);
        
        Random rand = new Random();
        int diceRoll = rand.nextInt(5) + 1;
        
        System.out.println("Ratio: " + ratio);
        System.out.println("Dice Roll: " + diceRoll);
        
        switch(ratio){
            case 20:
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
            case 25:
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
            case 33:
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
            case 50:
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
            case 67:
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
            case 100:
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
            case 150:
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
            case 200:
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
            case 300:
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
            case 400:
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
            case 500:
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
        
        if (ratio >= 600){
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
