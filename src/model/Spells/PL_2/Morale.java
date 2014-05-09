/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Spells.PL_2;

import Character.Characters;
import mainswordsorcery.Game;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javafx.scene.image.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import mainswordsorcery.Game;
import Units.ArmyUnit;
import java.io.Serializable;
import sshexmap.HexMap;

/**
 *
 * @author Tao Zhang
 */
public final class Morale implements Serializable{
    int MoraleRange = 7;
    int MoraleMannaCost = 3;
    
    JFrame frame;
    
    Characters character;
    
    String target_name;
    String target_hex;
    
    public Morale(Characters c){
        character = c;
        getTarget();
    }
    
    public void getTarget(){
        while(target_hex == null){
            target_hex = Game.getInstance().hudController.target_unit.location;
        }
        if(((ArmyUnit)Game.getInstance().hudController.target_unit).isDemoralized() == true){
            //System.out.println("**********************cast Spell");
            //performSpellEffects();
            checkRange();
        }
        else{
            //System.out.println("***************cast fail");
            Game.getInstance().hudController.chat_box.appendText(
                    "Target is not demoralized.\n" + " Select another target\n");
        }
    }
    
    public boolean checkLimits(){
        boolean limit = true;
        
        //limit = checkRange();
        //if( fit all the limits ){
          //  limit = true;
        //}
        
        return limit; 
    }
    
    public void checkRange(){
        int distance = HexMap.distance(character.location, target_hex);
        if(distance <= MoraleRange){
            performSpellEffects();
        }else{
            Game.getInstance().hudController.chat_box.appendText(
                    "Target is not in range.\n" + " Select another target\n");
        }
    }
    
    public void performSpellEffects(){
        ((ArmyUnit)Game.getInstance().hudController.target_unit).SetDemoralized(false);
        Game.getInstance().hudController.hmapContent.repaint();

        prepareGUI();
            
        CostManna();
        
    }
    
    
    public void prepareGUI(){
        frame = new JFrame("Morale");
        frame.setSize(600,500);
        frame.setLayout(new BorderLayout());
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  
                
            }
        });
        
        JLabel notice = new JLabel("You succeed to cast Morale");
        JButton ok_button = new JButton("Great");
        ok_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        
        JButton again_button = new JButton("Cast again");
        again_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        
        JPanel selection = new JPanel();
        selection.setLayout(new FlowLayout());
        selection.add(ok_button);
        selection.add(again_button);
        
        ImageIcon morale_image = new ImageIcon("resources/images/Spells/Morale.jpg");
        java.awt.Image img = morale_image.getImage();
        JPanel image_panel = new JPanel();
        //image_panel.setLayout(new BorderLayout());
        JLabel image_lable = new JLabel("",morale_image, JLabel.CENTER);
        
        image_panel.add(image_lable);
        frame.add(image_panel, BorderLayout.CENTER);
        frame.add(selection, BorderLayout.SOUTH);
        frame.add(notice, BorderLayout.NORTH);
        frame.setVisible(true);
    }   
    
    public boolean successCast(){
        return true;
    }
    
    public void CostManna(){
        if(successCast()){
            character.CostManna(3);
        }else{
            // do nothing
        }
    }
    
}
