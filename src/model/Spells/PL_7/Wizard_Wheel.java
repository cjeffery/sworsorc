/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Spells.PL_7;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Tao Zhang & Cameron Simon
 */
public final class Wizard_Wheel {
    JFrame frame;
    
    public Wizard_Wheel(){
        prepareGUI();
    }
    
    public void prepareGUI(){
        frame = new JFrame("Wizard_Wheel");
        frame.setSize(100,100);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  System.exit(0); 

            }
        });
        
        JLabel notice = new JLabel("This is Wizard_Wheel");
        
        frame.add(notice);
        frame.setVisible(true);
    }   
    
    //check mana available
    public boolean checkMana(){
        boolean mana = false;
        
        //if( enough mana ){
          //  mana = true;
        //}
        //else{ print message that not enough mana};
        
        return mana;
    }
    
    //return spell range
    public int getRange(){
        int range = 0;
        // get spells range
        // One-half as many hexes as manna Points expended(round down).
        return range;
    }
    
    //get distance to selected unit
    public int getDistance(){
        int distance = 0;
        //get distance
        return distance;        
    }
    
    public void getTarget(){
        // this function is used to get the target to cast spell
    }
    
    public void performSpellEffects(){
        // this function is used to perform the spell effects
        // like cost mana, or the real effects described in rules
        if(checkMana() == true){
            // perform
            if(getDistance() <= getRange()){
                //peform spell
                /*
                    -Costs all manna points (min of 6)
                    -All characters (including the caster) within range of the caster
                        immediately lose all their Manna Points). 
                    -All conjured units and Demons within range of the Spell are 
                        immediately removed from the Game-Map.
                    -All Talismans of Orb within range of the Spell cease to function
                        for the next Game-Turn.
                    -All magically created Bridges, Walls, and terrain types with
                        the range of the Spell cease to exist and are removed and
                        are remoed from the Game-Map.  
                    -No characters that begin the Game-Turn within range of the Spell
                        may cast Spells for the rest of the Game-Turn.
                    -The player who owns the casting character may determine the range
                        of the Spell; the minimum range is 3, and the max is one-half 
                        as many Manna points as the casting Character has.
                */
            }
            else{
                //not in range
            }
            // what I am thinking about performing some data effects
            // is that we can make a tmp data file that stores all the
            // char or unit info, 
            // then we can just go into that file and change the data
            // then we read the file again for refresh the game data
            
            
        }else{
            // show warning that it desn't fit all the limitations
        }
        
        
        
    }
        
}