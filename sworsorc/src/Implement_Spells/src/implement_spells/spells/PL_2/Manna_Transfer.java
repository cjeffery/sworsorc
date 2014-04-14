/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implement_spells.spells.PL_2;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author TaoZhang & Cameron Simon
 */
public final class Manna_Transfer {
    JFrame frame;
    
    public Manna_Transfer(){
        prepareGUI();
    }
    
    public void prepareGUI(){
        frame = new JFrame("Manna Transfer");
        frame.setSize(100,100);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  System.exit(0); 

            }
        });
        
        JLabel notice = new JLabel("This is Manna Transfer");
        
        frame.add(notice);
        frame.setVisible(true);
    }   
    
    public void selectTarget(){
        // need more detail about how to get the target
    }
    
    public int selectNuOfPoints(){
        int points = 0;
        // ask player to enter the number how many manna he want the target get
        // mention player the max manna that target can get from current spell casting char
        
        // check if the player enter the proper number
        // if not, reask
        // if good, return
        return points;
    }
    
    public void performEffects(){
        // decrease the magic potential of spell casting char
        
        // increase the target's magic potential
    }
    
    public void refresh(){
        // communicate the spell effects to other players
        
        // need networking
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
    
    public boolean checkLimits(){
        boolean limit = false;
        
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

