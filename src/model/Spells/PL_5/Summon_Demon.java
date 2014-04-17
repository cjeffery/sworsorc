/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Spells.PL_5;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Tao Zhang & Cameron Simon
 */
public final class Summon_Demon {
    JFrame frame;
    
    public Summon_Demon(){
        prepareGUI();
    }
    
    public void prepareGUI(){
        frame = new JFrame("Summon_Demon");
        frame.setSize(100,100);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  System.exit(0); 

            }
        });
        
        JLabel notice = new JLabel("This is Summon_Demon");
        
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
    
    public void performSpellEffects(){
        // this function is used to perform the spell effects
        // like cost mana, or the real effects described in rules
        if(checkMana() == true){
            // perform
           /*
                -Decrease mana by 8
                -Caster summons a demon (note that unless caster casta a Bind 
                    Demon Spell in the same Phase, he will not control the Demon
                    which may therefore attack him.
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