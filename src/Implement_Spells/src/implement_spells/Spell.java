/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implement_spells;

import implement_spells.Spell_Details.*;
import implement_spells.spells.CastSpell;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author 张涛
 */
public class Spell {
    String  Name;
    int     Level;
    double  ManaCost;
    
    public JButton spellbutton;
    private JFrame spellFrame;
    private JPanel controlPanel;
    
    Spell(String n, int lv, double mc) {
        Name  = n;
        Level = lv;
        ManaCost  = mc;   
        spellbutton = new JButton(n);
        
        spellbutton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
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
                CastSpell cs = new CastSpell(Name);
                cs.call_spell();
            }
        });
        
        cancel_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                spellFrame.dispose();
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
    
    
}
