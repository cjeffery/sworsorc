/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Spells.PL_6;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Tao Zhang & Cameron Simon
 */
public final class Firestorm {
    JFrame frame;
    
    public Firestorm(){
        prepareGUI();
    }
    
    public void prepareGUI(){
        frame = new JFrame("Firestorm");
        frame.setSize(100,100);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  System.exit(0); 

            }
        });
        
        JLabel notice = new JLabel("This is Firestorm");
        
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
        int range = 5;
        // get spells range
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
            getTarget();
            if(getDistance() <= getRange()){
                //peform spell
                /*
                    -Decrease mana by 10
                    -Spell cast at a single hex.
                    -All units in target hex destroyed.
                    -All characters in the hex must remain in the hex without 
                        moving, attacking or casting Spells for one full Game-Turn 
                        (until the caster's next Friendly Magic Phase)
                    -During this Game_turn no Enemy CHaracters of units may move 
                        into the Blasted hex.
                    -Hex becomes a Blasted terrain hex unless it was a River or Lake hex.
                    -Place a Blasted counter in the hex. 
                */
            }
            else{
                //out of range
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