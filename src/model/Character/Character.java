/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Character;

import Spells.Spell_Book;
import Units.MoveableUnit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author 张涛
 */
public final class Character extends MoveableUnit{
    public String  Name;
    // a character's magic power level may differ from his average MagicPL
    public int     MagicPL;
    public int     MagicPotential;
    public double  CurrentManna;
    
    //public int CurrentHex;
    
    public Character(){
        //GetInfo gi = new GetInfo();
        //gi.getCharacter();
        //Name = gi.printName();
        //MagicPL = gi.printPL();
        //CurrentManna = gi.printCM();
        //MagicPotential = 20;
        
        //CurrentHex = 1007;
        //getCharacter();
    }
    
    
    public Character(String a, int b, double c, /*int h*/ String h) {
        Name        = a;
        MagicPL     = b;
        //MagicPotential  = c;
        CurrentManna = c; //(double)MagicPotential;
        
        location = h;
    }
    
    
    
    public void CastSpell(Character c){
        Spell_Book sb = new Spell_Book(c);
        sb.getSpellBook();
    }
    
    public void CostManna(double mc){
        CurrentManna -= mc;
    }
    
    /** ===========================
     *      Temp GUI function for get character info
     *  ===========================
     * @param c
     */
    public void getCharacter(final Character c){
        final JFrame char_info = new JFrame("Character Info");
        char_info.setSize(300,300);
        
        char_info.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { 
                System.exit(0);
            }

        });
        
        char_info.setLayout(null);
        char_info.setLocation(10,50);
        
        JLabel char_name = new JLabel("Character name: ");
        char_name.setBounds(10, 20, 150, 20);
        char_info.add(char_name);
        // Textfield character name
        final JTextField char_name_field = new JTextField();
        char_name_field.setBounds(200, 20, 100, 20);
        char_info.add(char_name_field);
        
        JLabel char_PL = new JLabel("Power Level:");
        char_PL.setBounds(10, 60, 150, 20);
        char_info.add(char_PL);
        // Textfield character PL
        final JTextField char_PL_field = new JTextField();
        char_PL_field.setBounds(200,60, 100, 20);
        char_info.add(char_PL_field);
        
        JLabel char_CM = new JLabel("Current Manna:");
        char_CM.setBounds(10,100,150,20);
        char_info.add(char_CM);
        // Textfield character current manna
        final JTextField char_CM_field = new JTextField();
        char_CM_field.setBounds(200,100,100,20);
        char_info.add(char_CM_field);
        
        // Hex info
        JLabel char_hex = new JLabel("Current Hex number:");
        char_hex.setBounds(10,140,150,20);
        char_info.add(char_hex);
        // Textfield character hex
        final JTextField char_hex_field = new JTextField();
        char_hex_field.setBounds(200, 140, 100, 20);
        char_info.add(char_hex_field);
        
        JButton get_c = new JButton("Get");
        get_c.setBounds(150, 200, 50, 20);
        char_info.add(get_c);
        
        get_c.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                c.Name = char_name_field.getText();
                c.MagicPL = Integer.parseInt(char_PL_field.getText());
                c.CurrentManna = Double.parseDouble(char_CM_field.getText());
                c.location = char_hex_field.getText();
                //c.CurrentHex = Integer.parseInt(char_hex_field.getText());
                
                char_info.dispose();
                
                CastSpell(c);
            }
        });
        
        char_info.setVisible(true);
    }
}
