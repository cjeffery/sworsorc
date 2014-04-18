/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Spells.PL_6;

import java.awt.BorderLayout;
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
 * @author Tao Zhang & Cameron Simon
 */
public final class Summon_Force {
    JFrame frame;
    
    public Summon_Force(){
        prepareGUI();
        performSpellEffects();
    }
    
    public void prepareGUI(){
        frame = new JFrame("Summon_Force");
        frame.setSize(400,200);
        frame.setLayout(null);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  System.exit(0); 

            }
        });
        
        //JLabel notice = new JLabel("This is Summon_Force");
        
        //frame.add(notice);
        //frame.setVisible(true);
    }   
    
    //check mana available
    public boolean checkMana(){
        boolean mana = true;
        
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
        boolean limit = true;
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
                tempPhaseCheck tempChecker = new tempPhaseCheck();
        
                //check current phase
                if(tempChecker.movement() == true && tempChecker.combat() == false
                && tempChecker.spell() == false){
                    JButton ok_button = new JButton("Roll Dice For Force Selection");
                    ok_button.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JLabel note1 = new JLabel("Continental Siege Machine (3 Game-Turns)");
                        note1.setBounds(10, 20, 300, 20);
                        note1.setVisible(false);
                        JLabel note2 = new JLabel("SS Wiking Division (4 Game-Turns)");
                        note2.setBounds(10, 20, 300, 20);
                        note2.setVisible(false);
                        JLabel note3 = new JLabel("Legions of the Dead (6 Game-Turns");
                        note3.setBounds(10, 20, 300, 20);
                        note3.setVisible(false);
                        JLabel note4 = new JLabel("Demon (5 Game-Turns)");
                        note4.setBounds(10, 20, 300, 20);
                        note4.setVisible(false);
                        JLabel note5 = new JLabel("No effect");
                        note5.setBounds(10, 20, 300, 20);
                        note5.setVisible(false);
                        JLabel note6 = new JLabel("Caster Struck dead; remove from play");
                        note6.setBounds(10, 20, 300, 20);
                        note6.setVisible(false);
                        
                    
                        frame.add(note1);
                        frame.add(note2);
                        frame.add(note3);
                        frame.add(note4);
                        frame.add(note5);
                        frame.add(note6);
                    
                        int x = 1 + (int)(Math.random() * ((6 - 1) + 1));
                        System.out.println(x);
                    
                        if(x == 1){
                            note2.setVisible(false);
                            note3.setVisible(false);
                            note4.setVisible(false);
                            note5.setVisible(false);
                            note6.setVisible(false);
                            note1.setVisible(true);
                        }
                        if(x == 2){
                            note1.setVisible(false);
                            note3.setVisible(false);
                            note4.setVisible(false);
                            note5.setVisible(false);
                            note6.setVisible(false);
                            note2.setVisible(true);
                        }
                        if(x == 3){
                            note2.setVisible(false);
                            note1.setVisible(false);
                            note4.setVisible(false);
                            note5.setVisible(false);
                            note6.setVisible(false);
                            note3.setVisible(true);
                        }
                        if(x == 4){
                            note2.setVisible(false);
                            note3.setVisible(false);
                            note1.setVisible(false);
                            note5.setVisible(false);
                            note6.setVisible(false);
                            note4.setVisible(true);
                        }
                        if(x == 5){
                            note2.setVisible(false);
                            note3.setVisible(false);
                            note4.setVisible(false);
                            note1.setVisible(false);
                            note6.setVisible(false);
                            note5.setVisible(true);
                        }
                        if(x == 6){
                            note2.setVisible(false);
                            note3.setVisible(false);
                            note4.setVisible(false);
                            note5.setVisible(false);
                            note1.setVisible(false);
                            note6.setVisible(true);
                        }
                           
                    }
                    
                    });
                    ok_button.setBounds(0,0,200,20);
                    frame.add(ok_button);
                    frame.setVisible(true);
                }
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