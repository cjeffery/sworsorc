
// EmissaryResultsTable.java
// Christopher Goes 2-17-14
//package cs383;
import java.util.Random;

/**
 *
 * @author Tehlizard
 */
public class EmissaryResultsTable {
    
    public boolean death;
    
    public int getEmissaryResults()
    {
        Random die = new Random();
        int roll = 2 + die.nextInt( 22 );
        if( death )
            roll -= 2;
        
        if( roll >= 17 )
            return 3;
        else if( roll >= 14 && roll <= 16 )
            return 2;
        else if( roll >= 10 && roll <= 13 )
            return 1;
        else if( roll == 9 )
            return 0;
        else if( roll >= 6 && roll <= 8 )
            return -1;
        else if( roll == 5 )
            return -2;
        else if( roll == 3 || roll == 4 )
            return -3;
        else if( roll <= 2 )
        {
            death = true;
            return -999;
          
        }
        System.err.println("Error: getEmisaryResults returned past IF statements!");
        return -99;
    }
    
}
