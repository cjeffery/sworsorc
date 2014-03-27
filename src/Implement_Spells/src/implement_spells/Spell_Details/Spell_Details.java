/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implement_spells.Spell_Details;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author 张涛
 */
public class Spell_Details extends JComponent{
    
        // default frame size
    static int frame_width  = 300;
    static int frame_height = 500;
    
    String Name;
    static String PL;
    static String Resistable;
    static String Type;
    static String MC;
    static String Range;
    static String Limits;
    static String Effects;

    
    public Spell_Details(String n){
        Name = n;
        System.out.println(Name);
        //=========================
        // find the spell in the .txt file
        spell_search ss = new spell_search();
        ss.search(n);
        Name    = ss.printName();
        PL      = ss.printPL();
        Resistable = ss.printResistable();
        Type    = ss.printType();
        MC      = ss.printMC();
        Range   = ss.printRange();
        Limits  = ss.printLimits();
        Effects = ss.printEffects();
        
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        drawFrameLines(g);
        drawDetail(g);
    }
    
    public void drawFrameLines(Graphics g){
        frame_width = getWidth();
        frame_height = getHeight();
        
        g.drawLine(100, 0, 100, frame_height-50);
        
        g.drawLine(0,30,frame_width,30);
        g.drawLine(0,60,frame_width,60);
        g.drawLine(0,90,frame_width,90);
        g.drawLine(0,120,frame_width,120);
        g.drawLine(0,150,frame_width,150);
        g.drawLine(0,180,frame_width,180);
        g.drawLine(0,240,frame_width,240);
        g.drawLine(0, frame_height-50, frame_width, frame_height-50);
        
        g.drawString("Name: ",6, 20);
        g.drawString("Power Level: ",6 ,50);
        g.drawString("Resistable: ",6,80);
        g.drawString("Type: ",6,110);        
        g.drawString("Mana Cost: ",6,140);
        g.drawString("Range: ",6,170);
        g.drawString("Limits: ",6,200);
        g.drawString("Effects: ",6,260);
    }
    
    public void drawDetail(Graphics g){
        g.drawString(Name,106, 20);
        g.drawString(PL,106, 50);
        g.drawString(Resistable,106, 80);
        g.drawString(Type,106, 110);
        g.drawString(MC,106, 140);
        g.drawString(Range,106, 170);
        g.drawString(Limits,106, 200);
        g.drawString(Effects,106, 260);    
    }
}
