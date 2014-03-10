/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implement_spells;

import implement_spells.Spell_Details.*;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private JFrame selectF;
    private JPanel controlPanel;
    
    Spell(String n, int lv, double mc) {
        Name  = n;
        Level = lv;
        ManaCost  = mc;   
        spellbutton = new JButton(n);
        
        spellbutton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                prepareSelectionGUI();
                showButtonDemo();
            }
        });
    }
    
    public void prepareSelectionGUI(){
        selectF = new JFrame("Selection");
        selectF.setSize(350,80);
        selectF.setLayout(new GridLayout(1,3));
        selectF.addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
           }        
        }); 
        
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        
        selectF.add(controlPanel);
        selectF.setVisible(true);
    }
    
    public void showButtonDemo(){
        JButton detailButton = new JButton("Show Details");
        JButton castButton = new JButton("Cast spell");
        JButton cancelButton = new JButton("Cancel");
        
        detailButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //=========================
                JFrame spell_window = new JFrame("Spell Details");
                int sframe_width  = 300;
                int sframe_height = 500;
        
                spell_window.addWindowListener( new WindowAdapter() {
                    @Override
                    public void windowClosing( WindowEvent e )
                    {  System.exit(0); }
                });
        
                spell_window.setSize(sframe_width, sframe_height);
                spell_window.getContentPane().add( new Spell_Details(Name) );
                spell_window.setVisible( true );
                
            }
        });
        
        castButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //JFrame spell_cast_window = new JFrame(Name);
                
            }
        });
        
        cancelButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
            }
        });
        
        controlPanel.add(detailButton);
        controlPanel.add(castButton);
        controlPanel.add(cancelButton);
        
        selectF.setVisible(true);
    }
}
