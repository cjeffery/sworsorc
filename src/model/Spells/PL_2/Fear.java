/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Spells.PL_2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Tao Zhang & Cameron Simon
 */
public final class Fear {
    JFrame frame;
    
    public Fear(){
        prepareGUI();
    }
    
    public void prepareGUI(){
        frame = new JFrame("Fear");
        frame.setSize(400,400);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  //System.exit(0); 

            }
        });
        
        
        ImageIcon morale_image = new ImageIcon("resources/images/Spells/fear.gif");
        JPanel image_panel = new JPanel();
        //image_panel.setLayout(new BorderLayout());
        JLabel image_lable = new JLabel("",morale_image, JLabel.CENTER);
        
        image_panel.add(image_lable);
        frame.add(image_panel, BorderLayout.CENTER);
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
        int range = 7;
        // get spells range
        return range;
    }
    
    //get desired range
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
        
        //if( fit all the limits ){
          //  limit = true;
        //}
        
        return limit; 
    }
    
    public void selectUnits(){
        //of the units in the desired range specify which units you
        //want demoralized
    }
    
    public void performSpellEffects(){
        // this function is used to perform the spell effects
        // like cost mana, or the real effects described in rules
        if(checkLimits() == true && checkMana() == true){
            // perform
            if(getDistance() <= getRange()){
                //peform spell
                selectUnits();
                //demoralize selected units
            }
            else{
                //not in range
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