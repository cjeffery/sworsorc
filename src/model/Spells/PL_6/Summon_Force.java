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
public final class Summon_Force {
    JFrame frame;
    
    public Summon_Force(){
        prepareGUI();
    }
    
    public void prepareGUI(){
        frame = new JFrame("Summon_Force");
        frame.setSize(100,100);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  System.exit(0); 

            }
        });
        
        JLabel notice = new JLabel("This is Summon_Force");
        
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
    
    public boolean checkLimits(){
        boolean limit = false;
        //Spell may only be cast when the casting character is in hex 0606 (the gateway of evil)
        //if( fit all the limits ){
          //  limit = true;
        //}
        
        return limit; 
    }
    
    public void performSpellEffects(){
        // this function is used to perform the spell effects
        // like cost mana, or the real effects described in rules
        if(checkLimits() == true && checkMana() == true){
            // perform
            if(getDistance() <= getRange()){
                //peform spell
                /*
                    -Costs all mana points (min of 5)
                    int x = 1 + (int)(Math.random() * ((6 - 1) + 1));
                    if(x == 1){
                        //Continental Siege Machine (3 Game-Turns)
                    }
                    if(x == 2){
                        //SS Wiking Division (4 Game-Turns)
                    }
                    if(x == 3){
                        //Legions of the Dead (6 Game-Turns)
                    }
                    if(x == 4){
                        //Demon (5 Game-Turns)
                    }
                    if(x == 5){
                        //No effect
                    }
                    if(x == 6){
                        //Caster struck dead; remove from play
                    }
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