/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package randomevents;
import java.util.Random;
/**
 *
 * @author Johnathan Flake
 **/
public class RandomEvents {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Random rand = new Random();
        int Die1 = rand.nextInt(6)+1;
        int Die2 = rand.nextInt(6)+1;
        System.out.println("Die1: " + Die1);
        System.out.println("Die2: " + Die2);
        
        String Result;
        Result = "No Random Event";
        if (Die1 == 1){
            if (Die2 == 1){
                    Result = "Yellow Sun Manna Flux";
            }
            if (Die2 == 3){
                Result = "Elven High Holy Day";
            }
            if (Die2 == 5){
                Result = "Season of the Midnight Sun";
            }
            if (Die2 == 6){
                Result = "Church Communion";
            }
        }
        if (Die1 == 2){
            if (Die2 == 2){
                Result = "Yellow Sun Manna Drought";
            }
            if (Die2 == 4){
                Result = "Dwarven Folkmoot";
            }
            if (Die2 == 6){
                Result = "Conjunction of Fear";
            }
        }
        if (Die1 == 3){
            if (Die2 == 1){
                Result = "Corflu Cultist Collation";
            }
            if (Die2 == 3){
                Result = "Blue Sun Manna Flux";
            }
            if (Die2 == 5){
                Result = "Drought";
            }
            if (Die2 == 6){
                Result = "Flooding";
            }
        }
        if (Die1 == 4){
            if (Die2 == 2){
                Result = "Earthquake";
            }
            if (Die2 == 4){
                Result = "Blue Sun Manna Drought";
            }
            if (Die2 == 6){
                Result = "Windstorm";
            }
        }
        if (Die1 == 5){
            if (Die2 == 1){
                Result = "Eruption of the Mountains";
            }
            if (Die2 == 3){
                Result = "Earthquake";
            }
            if (Die2 == 5){
                Result = "Red Sun Manna Flux";
            }
            if (Die2 == 6){
                Result = "Vortex Storm";
            }
        }
        if (Die1 == 6){
            if (Die2 == 2){
                Result = "Killer Penguin Migration";
            }
            if (Die2 == 4){
                Result = "Poisonous Piranha Infestation";
            }
            if (Die2 == 6){
                Result = "Red Sun Manna Drought";
            }
        }
        System.out.println("Result: " + Result);
    }
}
