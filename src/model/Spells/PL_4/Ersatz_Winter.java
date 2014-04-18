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
public final class Ersatz_Winter {
    JFrame frame;
    
    public Ersatz_Winter(){
        prepareGUI();
    }
    
    public void prepareGUI(){
        frame = new JFrame("Ersatz_Winter");
        frame.setSize(100,100);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  System.exit(0); 

            }
        });
        
        JLabel notice = new JLabel("This is Ersatz_Winter");
        
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
           /*
                -Triple movement point cost for all terrain for next Game-Turn
                -No dragons, spiders or swamp creatures units or characters may 
                    move or attack until the end of the next game-turn; units in 
                    such hexes may not move, no flying units may move.
                -All lake and moat hexes north of hexrow xx27 become passable as 
                    clear terrain (but units may not end their movement in such hexes).
                -All stream hexsides have no effect
                -Killer penguins pay normal movement costs, and have their combat 
                    strength doubled until the end of the next game-turn.
                   
           */        
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