/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Spells.PL_3;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Tao Zhang & Cameron Simon
 */
public final class Dispell_Magicks {
    JFrame frame;
    
    public Dispell_Magicks(){
        prepareGUI();
    }
    
    public void prepareGUI(){
        frame = new JFrame("Dispell_Magicks");
        frame.setSize(100,100);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  //System.exit(0); 

            }
        });
        
        JLabel notice = new JLabel("This is Dispell_Magicks");
        
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
    
    public boolean checkLimits(){
        boolean limit = false;
        
        //if( fit all the limits ){
          //  limit = true;
        //}
        
        return limit; 
    }
    
    //get power level of spell being dispelled
    public int getPowerLevel(){
        int pwrLevel = 0;
        
        return pwrLevel;
    }
    
    public void performSpellEffects(){
        // this function is used to perform the spell effects
        // like cost mana, or the real effects described in rules
        if(checkLimits() == true && checkMana() == true){
            // perform
            int powerLevel = getPowerLevel();
            if(powerLevel == 1){
                //automatically dispell
            }
            if(powerLevel == 2){
                int x = 1 + (int)(Math.random() * ((6 - 1) + 1));
                if(x <= 5){
                    //dispell magicks
                }
            }
            if(powerLevel == 3){
                int x = 1 + (int)(Math.random() * ((6 - 1) + 1));
                if(x <= 4){
                    //dispell magicks
                }
            }
            if(powerLevel == 4){
                int x = 1 + (int)(Math.random() * ((6 - 1) + 1));
                if(x <= 3){
                    //dispell magicks
                }
            }
            if(powerLevel == 5){
                int x = 1 + (int)(Math.random() * ((6 - 1) + 1));
                if(x <= 2){
                    //dispell magicks
                }
            }
            if(powerLevel == 6){
                int x = 1 + (int)(Math.random() * ((6 - 1) + 1));
                if(x <= 1){
                    //dispell magicks
                }
            }
            if(powerLevel == 7){
                int x = 1 + (int)(Math.random() * ((6 - 1) + 1));
                if(x == 0){
                    //can't dispell magicks
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