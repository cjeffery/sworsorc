import java.util.*;
import diplomacy.Diplomacy;

public class EmissaryResults {
    
    public int roll;

    public int result;
    
    public int getEmissaryResults(int pRace, int nRace)
    {
        
        calculateResults(pRace, nRace);
        return result;
    }
    
    public void calculateResults(int pRace, int nRace)
    {
        Random generator = new Random();
        
        roll = generator.nextInt(12) + 1;
        
        Diplomacy dipl = new Diplomacy();
        
        result = dipl.calculateInterest(pRace, nRace);
        
    }
}
