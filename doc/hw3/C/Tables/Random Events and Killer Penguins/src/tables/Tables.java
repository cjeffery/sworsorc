/* Cameron Simon
 * Random Events and Killer Penguins tables
 */

package tables;

import java.util.Arrays;
import java.util.Random;

public class Tables {
    
    public static void main(String[] args) {
        //Get random event
        String event = randomEvents();
        System.out.println("Random Event: " + event);
        
        //Get number of killer penguins entering and location
        System.out.println();
        String penguin = killerPenguins();
        System.out.println(penguin);
    }
 
//***********RANDOM EVENTS*******************************    
    public static String randomEvents(){
        //Populate table
        String randomEvents[][] = 
        {
/*            
            {"YF", "-", "A", "-", "B", "C"},
            {"-", "YD", "-", "D", "-", "E"},
            {"F", "-", "BF", "-", "G", "H"},
            {"-", "I", "-", "BD", "-", "J"},
            {"K", "-", "L", "-", "RF", "M"},
            {"-", "N", "-", "O", "-", "RD"}
*/       
            {"Yellow Sun Manna Flux", "No Random Event Occurs", 
                "Elven High Holy Day", "No Random Event Occurs", 
                "Season of the Midnight Sun", "Church Communion"},
            {"No Random Event Occurs", "Yellow Sun Manna Drought", 
                "No Random Event Occurs", "Dwarven Folkmoot", 
                "No Random Event Occurs", "Conjunction of Fear"},
            {"Corflu Cultist Collation", "No Random Event Occurs", 
                "Blue Sun Manna Flux", "No Random Event Occurs", 
                "Drought", "Flooding"},
            {"No Random Event Occurs", "Earthquake", 
                "No Random Event Occurs", "Blue Sun Manna Drought", 
                "No Random Event Occurs", "Windstorm"},
            {"Eruption of the Mountains", "No Random Event Occurs", 
                "The Mistral", "No Random Event Occurs", 
                "Red Sun Manna Flux", "Vortex Storm"},
            {"No Random Event Occurs", "Killer Penguin Migration", 
                "No Random Event Occurs", "Poisonous Piranha Infestation", 
                "No Random Event Occurs", "Red Sun Manna Drought"}
        };        
        
        //2 random die rolls and lookup and print event
        int die1 = 1 + (int)(Math.random() * ((6 - 1) + 1));
        int die2 = 1 + (int)(Math.random() * ((6 - 1) + 1));
        String result = randomEvents[die1-1][die2-1];
//        System.out.println("die1: " + die1);
//        System.out.println("die2: " + die2);
        
        return result;
    }
    
 //**********KILLER PENGUINS**********************************
    public static String killerPenguins(){
        String location[] = {"West", "West", "1/2 West; 1/2 East", 
                             "1/2 East; 1/2 West", "East", "East"};
        
        //Random dice roll to find number entering
        int numberEntering = 0;
        int die1 = 1 + (int)(Math.random() * ((6 - 1) + 1));
        int die2 = 1 + (int)(Math.random() * ((6 - 1) + 1));
        int total = die1 + die2;
        if(total < 4){
            numberEntering = 0;
            return numberEntering + " Killer Penguins entering map.";
        }
        if(total >= 4 && total < 8){
            numberEntering = 1;
        }
        if(total >= 8 && total < 10){
            numberEntering = 2;
        } 
        if(total == 10){
            numberEntering = 3;
        }        
        if(total == 11){
            numberEntering = 4;
        } 
        if(total == 12){
            numberEntering = 5;
        }  

        //Roll for location entry point
        int die = 1 + (int)(Math.random() * ((6 - 1) + 1));

        //Check to see if number entering odd for options 3 or 4
        if(die == 3 || die == 4){
            if(numberEntering == 3 || numberEntering == 5){
                System.out.println("EXTRA Killer Penguin enters on glacier listed first!");
            }
            if(numberEntering == 1){
                return "1 Killer Penguin enters. \nLocation: On glacier listed first!";
            }
        }
        
        return numberEntering + " Killer Penguins entering. \nLocation: " + location[die-1];
    }
}
