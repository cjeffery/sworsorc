/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package character;

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
 * @author TaoZhang
 */
public class GetInfo {
    
    public boolean found_target = false;
    public String target_n;
    public int target_h;
    
    public GetInfo(){
        
    }
    
    public void getCharacter(){
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
                String name = char_name_field.getText();
                int PL = Integer.parseInt(char_PL_field.getText());
                double currentManna = Double.parseDouble(char_CM_field.getText());
                int hex = Integer.parseInt(char_hex_field.getText());
                
                char_info.dispose();
                
                Character ch = new Character(name, PL, currentManna, hex);
                ch.CastSpell(ch);
            }
        });
        
        char_info.setVisible(true);
    }
    
    
    
    
    
}
