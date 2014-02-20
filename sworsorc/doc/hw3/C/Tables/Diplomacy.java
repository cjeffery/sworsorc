// Sean Shepherd

public class Diplomacy {

    public int player;
    public int neutral;
    public int interest;
    
    public int calculateInterest(int pRace, int nRace)
    {
        interest = 0;
        if( pRace == 1)
        {
            if(nRace == 1 || nRace == 2)
            {
                interest = interest + 1;
            }
            if(nRace == 3 || nRace == 4)
            {
                // nothing happens
            }
            if(nRace == 5)
            {
                interest = interest - 1;
            } 
            if(nRace > 5)
            {
                interest = interest - 2;
            } 
        }
        
        if( pRace == 2 )
        {
            if(nRace == 1)
            {
                interest = interest + 1;
            }
            if(nRace == 2)
            {
                interest = interest + 3;
            }
            if(nRace > 4)
            {
                interest = interest - 1;
            } 
            if(nRace == 3 || nRace == 4)
            {
                interest = interest - 2;
            } 
        }
        
        if( pRace == 3 || pRace == 4)
        {
            if(nRace == 2)
            {
                interest = interest + 1;
            }
            if(nRace == 3 || nRace == 4)
            {
                interest = interest + 2;
            }
            if(nRace > 5 && nRace < 9)
            {
                interest = interest - 1;
            } 
            if(nRace == 2)
            {
                interest = interest - 2;
            } 
            if(nRace == 9)
            {
                interest = interest - 3;
            }
        }
                
        if( pRace == 5)
        {
            if(nRace == 1 || nRace == 5)
            {
                interest = interest - 2;
            }
            if(nRace == 2)
            {
                interest = interest - 1;
            }
            if(nRace == 3 || nRace == 4)
            {
                // nothing happens
            }
            if(nRace == 9)
            {
                interest = interest - 1;
            } 
            if(nRace > 5 && nRace < 9)
            {
                interest = interest + 2;
            } 
        }
        
        if( pRace > 5 && pRace < 9)
        {
            if(pRace == 9)
            {
                interest = interest + 2;
            }
            if(pRace > 4 && pRace < 9)
            {
                interest = interest + 1;
            }
            if(nRace == 2)
            {
                // nothing happens
            }
            if(nRace == 3 || nRace == 4)
            {
                interest = interest - 1;
            } 
            if(nRace == 1)
            {
                interest = interest - 2;
            } 
        }
        if( pRace == 9)
        {
            if(pRace == 9)
            {
                interest = interest + 3;
            }
            if(pRace > 5 && pRace < 9)
            {
                interest = interest + 2;
            }
            if(nRace == 5)
            {
                interest = interest + 1;
            }
            if(nRace == 1 || nRace == 2)
            {
                interest = interest - 1;
            } 
            if(nRace == 3 || nRace == 4)
            {
                interest = interest - 2;
            } 
        }
        
        return interest;
    }
    
}
