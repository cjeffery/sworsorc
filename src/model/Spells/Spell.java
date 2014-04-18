/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Spells;

import Character.Character;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Tyler & Tao Zhang
 */
public class Spell {
    Character character;
    
    String  Name;
    int     Level;
    double  ManaCost;
    
    double  TotalManaCost;
    
    public  JButton spellbutton;
    private JFrame spellFrame;
    
    public JFrame BookFrame;
    
    public Spell(String n, int lv, double mc, JFrame mainf, Character c) {
        Name  = n;
        Level = lv;
        ManaCost  = mc;   
        BookFrame = mainf;
        spellbutton = new JButton(n);
        character = c;
        
        spellbutton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                BookFrame.dispose();
                showSpellDetails();
                showButtonDemo();
            }
        });
    }
    
    public void showSpellDetails(){
        spellFrame = new JFrame(Name);
        spellFrame.setSize(300,500);
        
        spellFrame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  //System.exit(0); 
                BookFrame.setVisible(true);
            }
        });
        
        spellFrame.getContentPane().add( new Spell_Details(Name));
        spellFrame.setVisible( true );
    }
    
    public void showButtonDemo(){
        JButton cast_button = new JButton("Cast");
        JButton cancel_button = new JButton("Cancel");
        
        cast_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //JFrame spell_cast_window = new JFrame(Name);
                spellFrame.dispose();
                CastSpell cs = new CastSpell(character);
                cs.call_spell(Name);
            }
        });
        
        cancel_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                spellFrame.dispose();
                BookFrame.setVisible(true);
            }
        });
        
        JPanel selection = new JPanel();// panel for cast and cancel buttons
        selection.setLayout(new FlowLayout());
        selection.add(cast_button);
        selection.add(cancel_button);
        
        spellFrame.setLayout(new BorderLayout());
        spellFrame.add(selection, BorderLayout.SOUTH);
        spellFrame.setVisible(true);
    }
    
    
    /** ==================================
     *      Spell details
     *  ==================================
     */
    
}
