/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Spells.PL_2;

import Character.Characters;
import Units.ArmyUnit;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import mainswordsorcery.Game;
import sshexmap.HexMap;

/**
 *
 * @author Tao Zhang & Cameron Simon
 */
public final class Fear implements Serializable{
    JFrame frame;
    
    int FearRange = 7;
    
    Characters caster;
    
    String target_hex;
    
    public Fear(Characters c){
        caster = c;
        getTarget();
    }
    
    public void getTarget(){
        // this function is used to get the target to cast spell
        while(target_hex == null){
            target_hex = Game.getInstance().hudController.target_unit.location;
        }
        if(((ArmyUnit)Game.getInstance().hudController.target_unit).isDemoralized() != true){
            checkRange();
        }else{
            //System.out.println("***************cast fail");
            Game.getInstance().hudController.chat_box.appendText(
                    "Target is already demoralized.\n" + " Select another target\n");
        }
    }
        
    public void prepareGUI(){
        frame = new JFrame("Fear");
        frame.setSize(400,400);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  //System.exit(0); 
                frame.dispose();
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
    public void checkRange(){
        int distance = HexMap.distance(caster.location, target_hex);
        if(distance <= FearRange){
            System.out.println("Cast succeed!!!!!!!!!!!!!!");
            performSpellEffects();
        }else{
            Game.getInstance().hudController.chat_box.appendText(
                    "Target is not in range.\n" + " Select another target\n");
        }
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
        ((ArmyUnit)Game.getInstance().hudController.target_unit).SetDemoralized(true);
        Game.getInstance().hudController.hmapContent.repaint();

        prepareGUI();
            
        CostManna();        
    }
     
    public boolean successCast(){
        return true;
    }
    
    public void CostManna(){
        if(successCast()){
            caster.CostManna(3);
        }else{
            // do nothing
        }
    }
}