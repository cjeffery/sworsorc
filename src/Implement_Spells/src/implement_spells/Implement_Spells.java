/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implement_spells;

import character.Character;
import character.GetInfo;

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
public final class Implement_Spells{

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
    
    Character character;
    public int PL;
    public int MagicPotential;
    public double CurrentManna;
    
    // counter to count how many spells each power level this character can cast
    public int PL1_count;
    public int PL2_count;
    public int PL3_count;
    public int PL4_count;
    public int PL5_count;
    public int PL6_count;
    public int PL7_count;
    
    public Implement_Spells(Character c){
        //getCharacter();
        character = c;
        prepareGUI();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Implement_Spells is = new Implement_Spells();
        //is.getCharacter();
        //is.getSpellBook(pl, mp);
        //is.prepareGUI();
        
        //Character ch = new Character("Tao", 3, 3);
        //ch.CastSpell(ch);
        //System.out.println("Character CurrentManna: "+ ch.CurrentManna);
    
        GetInfo gi = new GetInfo();
        gi.getCharacter();
    }
    
    public void prepareGUI(){
      mainFrame = new JFrame(character.Name);
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
      
      if(character.MagicPL == 0 && character.CurrentManna == 0){
          PLZero = new JLabel("This Character can't cast any spells");
          MainSpellListPanel.add(PLZero);
      }else{
              PLOnePanel = new JPanel();
              title = BorderFactory.createTitledBorder("Power Level 1");
              PLOnePanel.setBorder(title);
              
          
              PLTwoPanel = new JPanel();
              title = BorderFactory.createTitledBorder("Power Level 2");
              PLTwoPanel.setBorder(title);
              
          
              PLThreePanel = new JPanel();
              title = BorderFactory.createTitledBorder("Power Level 3");
              PLThreePanel.setBorder(title);
              
              
              PLFourPanel = new JPanel();
              title = BorderFactory.createTitledBorder("Power Level 4");
              PLFourPanel.setBorder(title);
              
              
              PLFivePanel = new JPanel();
              title = BorderFactory.createTitledBorder("Power Level 5");
              PLFivePanel.setBorder(title);
              
              
              PLSixPanel = new JPanel();
              title = BorderFactory.createTitledBorder("Power Level 6");
              PLSixPanel.setBorder(title);
              
              
              PLSevenPanel = new JPanel();
              title = BorderFactory.createTitledBorder("Power Level 7");
              PLSevenPanel.setBorder(title);
              
      }
      scrollpanel = new JScrollPane();
      scrollpanel.setViewportView(MainSpellListPanel);
      mainFrame.add(scrollpanel);
      mainFrame.setVisible(true); 
    }
    
    public void getSpellBook(){
        // get the spell book for this character
        Spell_Book sb = new Spell_Book(mainFrame, character);
        Spell[] myspells;
        myspells = sb.MySpells(character);
        
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
        // make sure if the panel have any spells 
        if(PL1_count > 0){
            PLOnePanel.setPreferredSize(new Dimension(200, (PL1_count+1)*25));
            PLOnePanel.setLayout(new GridLayout(PL1_count,1));
            MainSpellListPanel.add(PLOnePanel); 
        }
        
        if(PL2_count > 0){
            PLTwoPanel.setPreferredSize(new Dimension(200, (PL2_count+1)*25));
            PLTwoPanel.setLayout(new GridLayout(PL2_count,1));
            MainSpellListPanel.add(PLTwoPanel);
        }
        if(PL3_count > 0){
            PLThreePanel.setPreferredSize(new Dimension(200, (PL3_count+1)*25));
            PLThreePanel.setLayout(new GridLayout(PL3_count,1));
            MainSpellListPanel.add(PLThreePanel);
        }
        if(PL4_count > 0){
            PLFourPanel.setPreferredSize(new Dimension(200, (PL4_count+1)*25));
            PLFourPanel.setLayout(new GridLayout(PL4_count,1));
            MainSpellListPanel.add(PLFourPanel);
        }
        if(PL5_count > 0){
            PLFivePanel.setPreferredSize(new Dimension(200, (PL5_count+1)*25)); 
            PLFivePanel.setLayout(new GridLayout(PL5_count,1));
            MainSpellListPanel.add(PLFivePanel);
        }
        if(PL6_count > 0){
            PLSixPanel.setPreferredSize(new Dimension(200, (PL6_count+1)*25)); 
            PLSixPanel.setLayout(new GridLayout(PL6_count,1));
            MainSpellListPanel.add(PLSixPanel);
        }
        if(PL7_count > 0){
            PLSevenPanel.setPreferredSize(new Dimension(200,(PL7_count+1)*25));
            PLSevenPanel.setLayout(new GridLayout(PL7_count,1));
            MainSpellListPanel.add(PLSevenPanel);
        }  
        if(PL1_count == 0 && PL2_count == 0 && PL3_count == 0 && PL4_count == 0 &&
                PL5_count == 0 && PL6_count == 0 && PL7_count == 0 ){
            JLabel nonespell = new JLabel("You can't cast anyspell so far!");
            MainSpellListPanel.add(nonespell);
        }
        
        // refresh the visible main frame
        mainFrame.setVisible(true); 
    }
    
}
