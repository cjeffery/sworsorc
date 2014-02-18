/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainSwordSorcery;

import java.util.Random;

/**
 * 
 * @author David
 */
public class RandomEventTable {
    private int eventKey;
    private int eventLength;
    private String eventDescription;
    private int currentEventKey; 
    Random RandomGenerator = new Random();
    
        
    private void KeyLookup(int sixSidedDieOne, int sixSidedDieTwo){
        if (ValidaedDiceRangeForRandomEventTable(sixSidedDieOne, sixSidedDieTwo))
        switch (sixSidedDieOne) {
            case 0 : 
                if (sixSidedDieTwo == 0) {SetEventKey(1); break;}
                if (sixSidedDieTwo == 2) {SetEventKey(7); break;}
                if (sixSidedDieTwo == 4) {SetEventKey(8); break;}
                if (sixSidedDieTwo == 5) {SetEventKey(9); break;} 
                SetEventKey(0);
                break;
                
            case 1 :
                if (sixSidedDieTwo == 1) {SetEventKey(2); break;}
                if (sixSidedDieTwo == 3) {SetEventKey(10); break;}
                if (sixSidedDieTwo == 5) {SetEventKey(11); break;} 
                SetEventKey(0);
                break;
                
            case 2 :
                if (sixSidedDieTwo == 0) {SetEventKey(12); break;}
                if (sixSidedDieTwo == 2) {SetEventKey(3); break;}
                if (sixSidedDieTwo == 4) {SetEventKey(13); break;}
                if (sixSidedDieTwo == 5) {SetEventKey(14); break;} 
                SetEventKey(0);
                break;
                
            case 3 :
                if (sixSidedDieTwo == 1) {SetEventKey(15); break;}
                if (sixSidedDieTwo == 3) {SetEventKey(4); break;}
                if (sixSidedDieTwo == 5) {SetEventKey(16); break;} 
                SetEventKey(0);
                break;
                
            case 4 :
                if (sixSidedDieTwo == 0) {SetEventKey(17); break;}
                if (sixSidedDieTwo == 2) {SetEventKey(18); break;} 
                if (sixSidedDieTwo == 4) {SetEventKey(5); break;}
                if (sixSidedDieTwo == 5) {SetEventKey(19); break;} 
                SetEventKey(0);
                break;
                
            case 5 :
                if (sixSidedDieTwo == 1) {if (GetCurrentEvent() == 13) SetEventKey(0); else SetEventKey(20); break;}
                if (sixSidedDieTwo == 3) {SetEventKey(21); break;}
                if (sixSidedDieTwo == 5) {SetEventKey(6); break;}
                SetEventKey(0);
                break;
                
            }//switch
                          
    }//KeyLookUp

    private boolean ValidaedDiceRangeForRandomEventTable(int sixSidedDieOne, int sixSidedDieTwo){
        
        if ((sixSidedDieOne >= 0 && sixSidedDieOne <= 5) && (sixSidedDieTwo >= 0 && sixSidedDieTwo <= 5))
                return true;
        else{//alpha else
            if (sixSidedDieOne >= 6 || sixSidedDieOne < 0){ 
                    System.out.println("Die1 has an invaled value of " + sixSidedDieOne);
                    
                    if (sixSidedDieTwo >= 6 || sixSidedDieTwo < 0)
                        System.out.println("Die2 has an invaled value of " + sixSidedDieTwo);
                }//if
                else 
                    if (sixSidedDieTwo >= 6 || sixSidedDieTwo < 0)
                        System.out.println("Die2 has an invaled value of " + sixSidedDieTwo);
                        
        }//alpha else
            return false;
    }

    private void LookupDescription(int lookUpEventKey){
        if (ValidateEventKeyRange(lookUpEventKey))
          switch (lookUpEventKey) {
            
            case 0:
                SetEventDescription("No random event occurs.");
                break;
                
            case 1:
                SetEventDescription("Yellow sun manna flux.");
                break;
                
            case 2:
                SetEventDescription("Yellow sun manna drought.");
                break;
                
            case 3:
                SetEventDescription("Blue sun manna flux.");
                break;
                
            case 4:
                SetEventDescription("Blue son manna drought");
                break;
                
            case 5:
                SetEventDescription("Red sun manna flux");
                break;
            
            case 6:
                SetEventDescription("Red sun manna drought.");
                break;
                
            case 7:
                SetEventDescription("Elven high holy day.");
                break;
               
            case 8:
                SetEventDescription("Season of the midnight sun.");
                break;
                
            case 9:
                SetEventDescription("Church declares a special communion as a tribute to the Emperor.");
                break;
                
            case 10:
                SetEventDescription("Dwarrows hold a folk moot.");
                break;
                
            case 11:
                SetEventDescription("Conjunction of fear.");
                break;
            
            case 12:
                SetEventDescription("Corfluite collation.");
                break;
                
            case 13:
                SetEventDescription("Drought.");
                break;
                
            case 14:
                SetEventDescription("Flooding.");
                break;
                
            case 15:
                SetEventDescription("Earthquake.");
                break;
                
            case 16:
                SetEventDescription("Windstorm.");
                break;
                
            case 17:               
                SetEventDescription("Mount GreyMoore and mount Gerlod erupt.");
                break;
                
            case 18:
                SetEventDescription("The Mistral is blowing.");
                break;
                
            case 19:
                SetEventDescription("Vortex storm.");
                break;
                
            case 20:
                    SetEventDescription("Killer penguin migration");
                break;
                
            case 21:               
                SetEventDescription("Poisonous piranha infestation.");
                break;
                
            default:
                break;
        }//switch 
     
            
    }//DescriptionLookUp

    private boolean ValidateEventKeyRange(int lookUpEventKey) {
        if (lookUpEventKey >=0 || lookUpEventKey <=21)
            return true;
        else
            System.out.println("EventKey has an invaled value of " + lookUpEventKey);
        
        return false;
    }
    
    private void LookUpLength(int KeyOfEvent){
        if (ValidateEventKeyRange(KeyOfEvent))
            if (KeyOfEvent == 13)  SetEventLength(3);
            else SetEventLength(1);
        else 
            System.out.println("EventKey has an invaled value of " + KeyOfEvent);
    }//LookUpLength
    
            
    public int GetEventKey() {
        
        return eventKey;
        
    }//EventKey
    
    public int GetEventLength() {
        
        return eventLength;
        
    }//EventLength
    
    public String GetEventDescription(){
        
        return eventDescription;
        
    }//EventDescription
    
    private int GetCurrentEvent(){
        return currentEventKey;
    }
    
    private void SetEventKey( int keyOfEvent){
        eventKey = keyOfEvent;
    }
    
    private void SetEventLength(int lengthOfEvent){
        eventLength = lengthOfEvent;
    }
    
    private void SetEventDescription(String descriptionOfEvent){
        eventDescription = descriptionOfEvent;
    }
    
    private void SetCurrentEvent(int keyOfEvent){
        currentEventKey = keyOfEvent;
    }
    
    public RandomEventTable() {
          
        SetCurrentEvent(0);
        
        this.KeyLookup(RandomSixSideDie(), RandomSixSideDie());
        this.LookupDescription(GetEventKey());
        this.LookUpLength(GetEventKey());
        
    }//RandomEventTable()

    private int RandomSixSideDie() {
        return RandomGenerator.nextInt(6);
    }
       
    public RandomEventTable(int inEvent) {
        
        SetCurrentEvent(inEvent);
        
        this.KeyLookup(RandomSixSideDie(), RandomSixSideDie());
        this.LookupDescription(GetEventKey());
        this.LookUpLength(GetEventKey());
        
    }//RandomEventTable(int inEvent)
        
    public RandomEventTable(int inEvent, int sixSidedDieOne, int sixSidedDieTwo){
        
        SetCurrentEvent(inEvent);
        
        this.KeyLookup(sixSidedDieOne, sixSidedDieTwo);
        this.LookupDescription(GetEventKey());
        this.LookUpLength(GetEventKey());
        
    }//RandomTable(int inEvent, int firstSixSideDie, int secondSixSideDie)
    
    public void RandomEventTableNew(){
        
        SetCurrentEvent(0);
        
        this.KeyLookup(RandomSixSideDie(), RandomSixSideDie());
        this.LookupDescription(GetEventKey());
        this.LookUpLength(GetEventKey());        
        
    }//NewRandomEvent()
    
    public void RandomEventTableNew(int inEvent){

        SetCurrentEvent(inEvent);
        
        this.KeyLookup(RandomSixSideDie(), RandomSixSideDie());
        this.LookupDescription(GetEventKey());
        this.LookUpLength(GetEventKey());        
        
    }//NewRandomEvent(int inEvent){
    
    public void RandomEventTableNew(int inEvent, int sixSidedDieOne, int sixSidedDieTwo){
        
        SetCurrentEvent(inEvent);

        this.KeyLookup(sixSidedDieOne, sixSidedDieTwo);
        this.LookupDescription(GetEventKey());
        this.LookUpLength(GetEventKey());
        
    }//NewRandomEvent(int inEvent, int Die1, int secondSixSideDie){
            
}//class
