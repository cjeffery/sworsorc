/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implement_spells;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

/**
 *
 * @author 张涛
 */
public final class Implement_Spells {

    private JFrame mainFrame;
    private JScrollPane scrollpanel;
    
    // set default frame size;
    public int frame_width = 250;
    public int frame_height = 300;
    
    private JLabel PLZero;
    private JPanel PLOnePanel;
    private JPanel PLTwoPanel;
    private JPanel PLThreePanel;
    private JPanel PLFourPanel;
    private JPanel PLFivePanel;
    private JPanel PLSixPanel;
    private JPanel PLSevenPanel;
    private JPanel MainSpellListPanel;
    private JLabel statusLabel;
    
    public int PL;
    public int MagicPotential;
    
    // counter to count how many spells each power level this character can cast
    public int PL1_count;
    public int PL2_count;
    public int PL3_count;
    public int PL4_count;
    public int PL5_count;
    public int PL6_count;
    public int PL7_count;
    
    public Implement_Spells(){
        getCharacter();
        prepareGUI();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Implement_Spells is = new Implement_Spells();
        //is.getCharacter();
        int pl;
        pl = is.PL;
        is.getSpellBook(pl);
        //is.prepareGUI();
    }
    
    public void prepareGUI(){
      mainFrame = new JFrame("Spells");
      mainFrame.setSize(frame_width,frame_height);
      //mainFrame.setLayout(new GridLayout(2, 3));
      mainFrame.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });   
      
      
      MainSpellListPanel = new JPanel();
      MainSpellListPanel.setLayout(new BoxLayout(MainSpellListPanel,BoxLayout.Y_AXIS));
      TitledBorder title;
      
      if(PL == 0 && MagicPotential == 0){
          PLZero = new JLabel("This Character can't cast any spells");
          MainSpellListPanel.add(PLZero);
      }else{
          if(PL >= 1){
              PLOnePanel = new JPanel();
              title = BorderFactory.createTitledBorder("Power Level 1");
              PLOnePanel.setBorder(title);
              PLOnePanel.setLayout(new GridLayout(PL1_count,1));
              MainSpellListPanel.add(PLOnePanel); 
          
              PLTwoPanel = new JPanel();
              title = BorderFactory.createTitledBorder("Power Level 2");
              PLTwoPanel.setBorder(title);
              PLTwoPanel.setLayout(new GridLayout(PL2_count,1));
              MainSpellListPanel.add(PLTwoPanel);
          }
          
          if(PL >= 2){
              PLThreePanel = new JPanel();
              title = BorderFactory.createTitledBorder("Power Level 3");
              PLThreePanel.setBorder(title);
              PLThreePanel.setLayout(new GridLayout(PL3_count,1));
              MainSpellListPanel.add(PLThreePanel);
          }
          
          if(PL >= 3){
              PLFourPanel = new JPanel();
              title = BorderFactory.createTitledBorder("Power Level 4");
              PLFourPanel.setBorder(title);
              PLFourPanel.setLayout(new GridLayout(PL4_count,1));
              MainSpellListPanel.add(PLFourPanel);
          }
          
          if(PL >= 4){
              PLFivePanel = new JPanel();
              title = BorderFactory.createTitledBorder("Power Level 5");
              PLFivePanel.setBorder(title);
              PLFivePanel.setLayout(new GridLayout(PL5_count,1));
              MainSpellListPanel.add(PLFivePanel);
          }
          
          if(PL >= 5){
              PLSixPanel = new JPanel();
              title = BorderFactory.createTitledBorder("Power Level 6");
              PLSixPanel.setBorder(title);
              PLSixPanel.setLayout(new GridLayout(PL6_count,1));
              MainSpellListPanel.add(PLSixPanel);
          }
          
          if(PL >= 6){
              PLSevenPanel = new JPanel();
              title = BorderFactory.createTitledBorder("Power Level 7");
              PLSevenPanel.setBorder(title);
              PLSevenPanel.setLayout(new GridLayout(PL7_count,1));
              MainSpellListPanel.add(PLSevenPanel);
          }
      }
      scrollpanel = new JScrollPane();
      scrollpanel.setViewportView(MainSpellListPanel);
      mainFrame.add(scrollpanel);
      mainFrame.setVisible(true); 
    }
    
    public void getCharacter(){
        PL = 6;
        MagicPotential = 10;
    }
    
    public void getSpellBook(int pl){
        // get the spell book for this character
        Spell_Book sb = new Spell_Book();
        Spell[] myspells;
        myspells = sb.MySpells(pl);
        
        // initial spell counters
        PL1_count = 0;
        PL2_count = 0;
        PL3_count = 0;
        PL4_count = 0;
        PL5_count = 0;
        PL6_count = 0;
        PL7_count = 0;
        
        // search all the spells from the spell book
        // count how many is that for each lv
        for(Spell myspell: myspells){
            switch(myspell.Level){
                case 1: 
                    PLOnePanel.add(myspell.spellbutton);
                    PL1_count++;
                    break;
                case 2:
                    PLTwoPanel.add(myspell.spellbutton);
                    PL2_count++;
                    break;
                case 3:
                    PLThreePanel.add(myspell.spellbutton);
                    PL3_count++;
                    break;
                case 4:
                    PLFourPanel.add(myspell.spellbutton);
                    PL4_count++;
                    break;
                case 5:
                    PLFivePanel.add(myspell.spellbutton);
                    PL5_count++;
                    break;
                case 6:
                    PLSixPanel.add(myspell.spellbutton);
                    PL6_count++;
                    break;
                case 7:
                    PLSevenPanel.add(myspell.spellbutton);
                    PL7_count++;
                    break;
                default:
                    System.err.println("Error: Unknow spell power level");
            }
        }
        
        // reset all the panels' size
        PLOnePanel.setPreferredSize(new Dimension(200, PL1_count*25));
        PLTwoPanel.setPreferredSize(new Dimension(200, PL2_count*25));
        PLThreePanel.setPreferredSize(new Dimension(200, PL3_count*25));
        PLFourPanel.setPreferredSize(new Dimension(200, PL4_count*25));
        PLFivePanel.setPreferredSize(new Dimension(200, PL5_count*25)); 
        PLSixPanel.setPreferredSize(new Dimension(200, PL6_count*25));   
        PLSevenPanel.setPreferredSize(new Dimension(200, PL7_count*45));   
        
        // refresh the visible main frame
        mainFrame.setVisible(true); 
    }
    
}
