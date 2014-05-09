/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Spells.PL_4;

import Character.Characters;
import Units.UnitPool;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import mainswordsorcery.Game;
import Units.MoveableUnit;


/**
 *
 * @author Tao Zhang & Cameron Simon
 */
public final class Teleportation_Control {
    int TP_MANNACOST = 4;
    
    Characters caster;
    
    UnitPool unitpool = new UnitPool();
    
    JFrame frame;
    String target_name;
    String target_hex;
    
    public Teleportation_Control(Characters character){
        caster = character;
       // prepareGUI();
        performSpellEffects();
    }
    
    public void prepareGUI(){
        frame = new JFrame("Teleportation_Control");
        frame.setSize(400,400);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  //System.exit(0); 

            }
        });
        
        JLabel notice = new JLabel("This is Teleportation_Control");
        
        frame.add(notice);
        frame.setVisible(true);
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
         while(target_hex == null){
            target_hex = Game.getInstance().hudController.target_unit.location;
        }
    }
    
    public void performSpellEffects(){
        final UnitPool pool = UnitPool.getInstance();
        // this function is used to perform the spell effects
        // like cost mana, or the real effects described in rules
        if(checkMana() == true){
            // perform
            getTarget();
            if(true){
                System.out.println("test teleport");
                //peform spell, move caster to target unit hex
                pool.addMove((MoveableUnit)caster, target_hex);
                Game.getInstance().hudController.hmapContent.repaint();
                
                
                /* David's code, spell is being changed for now
                unitpool.setSafeTeleport(unitpool.getUnitsInHex(caster.getLocation()), true);
                // need functions to select the portal
                int portal = 1;
                
                unitpool.setTeleportDestination(unitpool.getUnitsInHex(caster.getLocation()),
                            portal);
                
                    -Decrease mana 4 points
                    -Affects all characters and/or units in the caster's hex.
                    -Affects last until the end of the Game-Turn
                    -If during the friendly movement phase in which the character
                        casts the spell, the units/characters on which it is cast 
                        move through a Portal, the Player who owns the casting
                        character may choose which portal becomes the moveing 
                        units/characters' destination portal.
                    -Die roll to determine whether the characters/units eliminated 
                        by teleportation.
                    -This die-roll may be dispensed with if Teleportation Protection 
                        is cast.  
                    -The spell must be cast in the friendly movement phase.
                */      
                
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
       
    public boolean successCast(){
        return true;
    }
    
    public void CostManna(){
        if(successCast()){
            caster.CostManna(4);
        }else{
            // do nothing
        }
    }
    
    
}