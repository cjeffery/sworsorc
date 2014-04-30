/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Spells.PL_4;

import Character.Characters;
import Units.*;
import static Spells.Spell.conjured;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Tao Zhang & Cameron Simon
 */
public final class C_K_I {
    JFrame frame;
    Characters caster;
    
    public C_K_I(Characters c){
        caster = c;
        prepareGUI();
    }
    
    public void prepareGUI(){
        frame = new JFrame("C_K_I");
        frame.setSize(100,100);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  System.exit(0); 

            }
        });
        
        JLabel notice = new JLabel("This is C_K_I");
        
        frame.add(notice);
        frame.setVisible(true);
    }   
    
    //check mana available
    public boolean checkMana(){
        return caster.checkManna(1);
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
    
    public void performSpellEffects(){
        // this function is used to perform the spell effects
        // like cost mana, or the real effects described in rules
        if(checkMana() == true){
            caster.CostManna(1);
            ArmyUnit unit1 = new KoboldicInfantry(caster, 1);
            unit1.SetLifeSpan(99);
            unit1.setLocation(caster.getLocation());
            
            conjured = unit1;
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