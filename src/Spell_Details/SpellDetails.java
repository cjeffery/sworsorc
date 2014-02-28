/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spelldetails;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author 张涛
 */
public class SpellDetails extends JComponent{

    static int frame_width  = 300;
    static int frame_height = 500;
    
    static String Name;
    static String PL;
    static String Resistable;
    static String Type;
    static String MC;
    static String Range;
    static String Limits;
    static String Effects;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //====================
        // read the name from input
        spell_read sr = new spell_read();
        sr.getName();
        String s = sr.printName();
        //=========================
        // find the spell in the .txt file
        spell_search ss = new spell_search();
        ss.search(s);
        Name    = ss.printName();
        PL      = ss.printPL();
        Resistable = ss.printResistable();
        Type    = ss.printType();
        MC      = ss.printMC();
        Range   = ss.printRange();
        Limits  = ss.printLimits();
        Effects = ss.printEffects();
        
        //=========================
        JFrame spell_window = new JFrame("Spell Details");
        
        spell_window.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  System.exit(0); }
            }
        );
        
        spell_window.setSize(frame_width, frame_height);
        spell_window.getContentPane().add( new SpellDetails() );
        spell_window.setVisible( true );
        
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        drawFrameLines(g);
        drawDetail(g);
    }
    
    public void drawFrameLines(Graphics g){
        frame_width = getWidth();
        frame_height = getHeight();
        
        g.drawLine(35*frame_width/100, 0, 35*frame_width/100, frame_height);
        
        g.drawLine(0,30*frame_height/500,frame_width,30*frame_height/500);
        g.drawLine(0,60*frame_height/500,frame_width,60*frame_height/500);
        g.drawLine(0,90*frame_height/500,frame_width,90*frame_height/500);
        g.drawLine(0,120*frame_height/500,frame_width,120*frame_height/500);
        g.drawLine(0,150*frame_height/500,frame_width,150*frame_height/500);
        g.drawLine(0,180*frame_height/500,frame_width,180*frame_height/500);
        g.drawLine(0,240*frame_height/500,frame_width,240*frame_height/500);
        
        g.drawString("Name: ",6*frame_width/300, 20*frame_height/500);
        g.drawString("Power Level: ",6*frame_width/300 ,50*frame_height/500);
        g.drawString("Resistable: ",6*frame_width/300 ,80*frame_height/500);
        g.drawString("Type: ",6*frame_width/300 ,110*frame_height/500);        
        g.drawString("Mana Cost: ",6*frame_width/300 ,140*frame_height/500);
        g.drawString("Range: ",6*frame_width/300 ,170*frame_height/500);
        g.drawString("Limits: ",6*frame_width/300 ,200*frame_height/500);
        g.drawString("Effects: ",6*frame_width/300 ,260*frame_height/500);
    }
    
    public void drawDetail(Graphics g){
        g.drawString(Name,6*frame_width/300 + 35*frame_width/100, 20*frame_height/500);
        g.drawString(PL,6*frame_width/300 + 35*frame_width/100, 50*frame_height/500);
        g.drawString(Resistable,6*frame_width/300 + 35*frame_width/100, 80*frame_height/500);
        g.drawString(Type,6*frame_width/300 + 35*frame_width/100, 110*frame_height/500);
        g.drawString(MC,6*frame_width/300 + 35*frame_width/100, 140*frame_height/500);
        g.drawString(Range,6*frame_width/300 + 35*frame_width/100, 170*frame_height/500);
        g.drawString(Limits,6*frame_width/300 + 35*frame_width/100, 200*frame_height/500);
        g.drawString(Effects,6*frame_width/300 + 35*frame_width/100, 260*frame_height/500);    
    }
    
}
