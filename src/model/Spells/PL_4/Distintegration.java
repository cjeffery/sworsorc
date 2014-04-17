/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Spells.PL_4;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Tao Zhang & Cameron Simon
 */
public final class Distintegration {
    JFrame frame;
    
    public Distintegration(){
        prepareGUI();
    }
    
    public void prepareGUI(){
        frame = new JFrame("Distintegration");
        frame.setSize(100,100);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  System.exit(0); 

            }
        });
        
        JLabel notice = new JLabel("This is Distintegration");
        
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
    
    public int getChoice(){
        
        /* Present All options
            A. Destroy any one Wall of Force Wall hexside with 5 hexes.  
                The wall ceases to exist, and the hexside becomes passable
            B. Create an entrance to a Dragon's Tunnel COmplex.  THe entrance 
                may be created in any Tunnel hex within 5 hexes of the caster.    
            C. Destroy a bridge within 5 hexes.  The bridge ceases to exist and 
                the River hex becomes impassable.    
           
        if(//option "A")
            return 1;
        if(//option "B")
            return 2;
        if(//option "C")
            return 3;
        */
        return 0;
    }
    
    public void performSpellEffects(){
        // this function is used to perform the spell effects
        // like cost mana, or the real effects described in rules
        if(checkMana() == true){
            // perform
            int choice = 0;
            choice = getChoice();
            getTarget();
            if(getDistance() <= getRange()){
                //peform spell
                if(choice  == 0){
                    //cancel spell cast
                }
                if(choice  == 1){
                    //Perform affects of A on target
                }
                if(choice  == 2){
                    //Perform affects of B on target
                }
                if(choice  == 3){
                    //Perform affects of C on target
                }
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