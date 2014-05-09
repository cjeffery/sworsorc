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
public final class Building {
    JFrame frame;
    
    public Building(){
        prepareGUI();
    }
    
    public void prepareGUI(){
        frame = new JFrame("Building");
        frame.setSize(100,100);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  //System.exit(0); 
                frame.dispose();
            }
        });
        
        JLabel notice = new JLabel("This is Building");
        
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
        // choose hexside to build wall on 
    }
    
    public int getChoice(){
        
        /* Present All options
            A. Create a Wall hexside on some hexside that is adjacent to a hex
                within 5 hexes of the caster.
            B. Seal an entrance to a Dragon's Tunnel Complex    
            C. Build a bridge on some River hex within 5 hexes.   
           
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