/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implement_spells;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author 张涛
 */
public class Implement_Spells {

    private JFrame mainFrame;
    
    public int frame_width = 700;
    public int frame_height = 500;
    
    private JPanel PLOnePanel;
    private JPanel PLTwoPanel;
    private JPanel PLThreePanel;
    private JPanel PLFourPanel;
    private JPanel PLFivePanel;
    private JPanel PLSixPanel;
    private JPanel PLSevenPanel;
    
    private JLabel statusLabel;
    
    public Implement_Spells(){
        prepareGUI();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Implement_Spells is = new Implement_Spells();
        is.getCharacter();
        int pl = 7;
        is.getSpellBook(pl);
    }
    
    public void prepareGUI(){
      mainFrame = new JFrame("Spells");
      mainFrame.setSize(frame_width,frame_height);
      mainFrame.setLayout(new GridLayout(2, 3));
      mainFrame.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });   
      
      
      
      PLOnePanel = new JPanel();
      PLOnePanel.setPreferredSize(new Dimension(frame_width/7, frame_height));
      PLOnePanel.setLayout(new BoxLayout(PLOnePanel, BoxLayout.Y_AXIS));
      
      PLTwoPanel = new JPanel();
      PLTwoPanel.setPreferredSize(new Dimension(frame_width/7, frame_height));
      PLTwoPanel.setLayout(new BoxLayout(PLTwoPanel, BoxLayout.Y_AXIS));
      
      PLThreePanel = new JPanel();
      PLThreePanel.setPreferredSize(new Dimension(frame_width/7, frame_height));
      PLThreePanel.setLayout(new BoxLayout(PLThreePanel, BoxLayout.Y_AXIS));
      
      PLFourPanel = new JPanel();
      PLFourPanel.setPreferredSize(new Dimension(frame_width/7, frame_height));
      PLFourPanel.setLayout(new BoxLayout(PLFourPanel, BoxLayout.Y_AXIS));
      
      PLFivePanel = new JPanel();
      PLFivePanel.setPreferredSize(new Dimension(frame_width/7, frame_height));
      PLFivePanel.setLayout(new BoxLayout(PLFivePanel, BoxLayout.Y_AXIS));
      
      PLSixPanel = new JPanel();
      PLSixPanel.setPreferredSize(new Dimension(frame_width/7, frame_height));
      PLSixPanel.setLayout(new BoxLayout(PLSixPanel, BoxLayout.Y_AXIS));
      
      PLSevenPanel = new JPanel();
      PLSevenPanel.setPreferredSize(new Dimension(frame_width/7, frame_height));
      PLSevenPanel.setLayout(new BoxLayout(PLSevenPanel, BoxLayout.Y_AXIS));

      mainFrame.add(PLOnePanel);
      mainFrame.add(PLTwoPanel);
      mainFrame.add(PLThreePanel);
      mainFrame.add(PLFourPanel);
      mainFrame.add(PLFivePanel);
      mainFrame.add(PLSixPanel);
      mainFrame.add(PLSevenPanel);
      mainFrame.setVisible(true); 
    }
    
    public void getCharacter(){
        
    }
    
    public void getSpellBook(int pl){
        Spell_Book sb = new Spell_Book();
        Spell[] myspells;
        myspells = sb.MySpells(pl);
        
        for(Spell myspell: myspells){
            switch(myspell.Level){
                case 1: 
                    PLOnePanel.add(myspell.spellbutton);
                    break;
                case 2:
                    PLTwoPanel.add(myspell.spellbutton);
                    break;
                case 3:
                    PLThreePanel.add(myspell.spellbutton);
                    break;
                case 4:
                    PLFourPanel.add(myspell.spellbutton);
                    break;
                case 5:
                    PLFivePanel.add(myspell.spellbutton);
                    break;
                case 6:
                    PLSixPanel.add(myspell.spellbutton);
                    break;
                case 7:
                    PLSevenPanel.add(myspell.spellbutton);
                    break;
                default:
                    System.err.println("Error: Unknow spell power level");
            }
        }
        mainFrame.setVisible(true); 
    }
    
}
