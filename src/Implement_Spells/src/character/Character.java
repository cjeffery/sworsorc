/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package character;

import implement_spells.Implement_Spells;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author 张涛
 */
public class Character {
    public String  Name;
    // a character's magic power level may differ from his average MagicPL
    public int     MagicPL;
    public int     MagicPotential;
    public double  CurrentManna;
    
    public int  CurrentHex;
    
    public Character(){
        //GetInfo gi = new GetInfo();
        //gi.getCharacter();
        //Name = gi.printName();
        //MagicPL = gi.printPL();
        //CurrentManna = gi.printCM();
        //MagicPotential = 20;
        
        //CurrentHex = 1007;
    }
    
    
    public Character(String a, int b, double c, int h) {
        Name        = a;
        MagicPL     = b;
        //MagicPotential  = c;
        CurrentManna = c; //(double)MagicPotential;
        
        CurrentHex = h;
    }
    
    
    
    public void CastSpell(Character c){
        Implement_Spells is = new Implement_Spells(c);
        is.getSpellBook();
    }
    
    public void CostManna(double mc){
        CurrentManna -= mc;
    }
}
