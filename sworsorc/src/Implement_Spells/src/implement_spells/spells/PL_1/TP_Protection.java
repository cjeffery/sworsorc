/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implement_spells.spells.PL_1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import phase.tempPhaseCheck;

/**
 *
 * @author 张涛 & Cameron Simon
 */
public final class TP_Protection {
    JFrame frame;
    
    public TP_Protection(){
        prepareGUI();
        
        checkLimits();
    }
    
    public void prepareGUI(){
        frame = new JFrame("TP_Protection");
        frame.setLayout(null);
        frame.setSize(200,100);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  
                System.exit(0); 
            }
        });
        
        //JLabel notice = new JLabel("This is TP_Protection");
        
        //frame.add(notice);
        //frame.setVisible(true);
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
        tempPhaseCheck tempChecker = new tempPhaseCheck();
        
        //check current phase
        if(tempChecker.movement() == true && tempChecker.combat() == false
                && tempChecker.spell() == false){
             JButton ok_button = new JButton("Roll Dice");
             ok_button.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    JLabel note = new JLabel("Characters Not Destroyed!!");
                    note.setBounds(10, 20, 150, 20);
                    note.setVisible(false);
                    JLabel note2 = new JLabel("Characters Destroyed!!");
                    note2.setBounds(10, 20, 150, 20);
                    note2.setVisible(false);
                    
                    frame.add(note);
                    frame.add(note2);
                    
                    int x = 1 + (int)(Math.random() * ((6 - 1) + 1));
                    
                    if(x == 1 || x == 2){
                        note2.setVisible(false);
                        note.setVisible(true);
                    }
                    else{
                        note.setVisible(false);
                        note2.setVisible(true);
                    }    
                }
            });
            ok_button.setBounds(0,0,150,20);
            frame.add(ok_button);
            frame.setVisible(true);
          //if( fit all the limits ){
          //  limit = true;
        //}
        }
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