/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package Spells;

import Character.Characters;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.Serializable;
import javafx.stage.Stage;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Tyler & Tao Zhang
 */
public final class Spell_Book implements Serializable {
    private static final long serialVersionUID = 1L;
    Spell[] list;

    // Find a different way to do this please
    // Serialze information for the dialog, and then display the dialog in the calling class
    //Stage popup = new Stage();
    
    /** ==========================
     *      Character info
     *  ==========================
     */
    Characters character;
    public int PL;
    public int MagicPotential;
    public double CurrentManna;
    
    /**===========================
     *      GUI Values
     * ===========================
     */
    public  JFrame mainFrame;
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

    // counter to count how many spells each power level this character can cast
    public int PL1_count;
    public int PL2_count;
    public int PL3_count;
    public int PL4_count;
    public int PL5_count;
    public int PL6_count;
    public int PL7_count;
 
    
    
    public Spell_Book(Characters c) {
        character = c;
       
        prepareGUI();
        
        list = new Spell[32];
        list[0]  = new Spell("Teleportation Protection", 1, 2, mainFrame,c);
        list[1]  = new Spell("Force Wall", 1, 2, mainFrame,c);
        list[2]  = new Spell("Conjure Zombie Infantry", 1, 1, mainFrame, c);
        
        list[3]  = new Spell("Manna Transfer", 2, 2, mainFrame, c);
        list[4]  = new Spell("River Crossing", 2, 2, mainFrame, c);
        list[5]  = new Spell("Morale", 2, 3, mainFrame,c);
        list[6]  = new Spell("Fear", 2, 3, mainFrame, c);
        list[7]  = new Spell("Conjure Centauroid Cavalry", 2, 1, mainFrame, c);
        
        list[8]  = new Spell("Monsoon", 3, 5, mainFrame, c);
        list[9]  = new Spell("Enhance Stature", 3, 1, mainFrame, c);
        list[10] = new Spell("Forest", 3, 2, mainFrame, c);
        list[11] = new Spell("Vortex Suppression", 3, 2, mainFrame, c);
        list[12] = new Spell("Immobilization", 3, 1, mainFrame, c);
        list[13] = new Spell("Conjure Wyvern Airtroops", 3, 1.5, mainFrame, c);
        list[14] = new Spell("Dispell Magicks", 3, 3, mainFrame, c);
        
        list[15] = new Spell("Disintegration", 4, 6, mainFrame, c);
        list[16] = new Spell("Building", 4, 5, mainFrame, c);
        list[17] = new Spell("Vortex Creation", 4, 2, mainFrame, c);
        list[18] = new Spell("Ersatz Winter", 4, 8, mainFrame, c);
        list[19] = new Spell("Teleportation Control", 4, 4, mainFrame, c);
        list[20] = new Spell("Conjure Koboldic Infantry", 4, 1, mainFrame, c);
        
        list[21] = new Spell("Planar Return", 5, 6, mainFrame, c);
        list[22] = new Spell("Summon Demon", 5, 8, mainFrame, c);
        list[23] = new Spell("Banish Conjured Troops", 5, 3, mainFrame, c);
        list[24] = new Spell("Conjure Wraith Troops", 5, 1.5, mainFrame, c);
        
        list[25] = new Spell("Bind Demon", 6, 4, mainFrame, c);
        list[26] = new Spell("Banish Demon", 6, 6, mainFrame, c);
        list[27] = new Spell("Summon Force", 6, 5, mainFrame, c);
        list[28] = new Spell("Firestorm", 6, 10, mainFrame, c);
        list[29] = new Spell("Berserkergang", 6, 2, mainFrame, c);
        list[30] = new Spell("Conjure Demonic Infantry", 6, 2, mainFrame, c);
        
        list[31] = new Spell("Wizard Wheel", 7, 6, mainFrame, c);
        
        
    }
    
    public Spell[] MySpells(Characters character){
        Spell[] myList;
        int nSpells = 0;
        if(character.getMagicPL() == 0){
            myList = new Spell[1];
            myList[0] = new Spell("void", 0, 0, mainFrame, character);
        } else {
            for(int i = 0; i < 32; i++) {
                if(list[i].Level <= character.getMagicPL()+1 
                        && list[i].ManaCost <= character.getCurrentManna()){
                    nSpells++;
                }
            }
            myList = new Spell[nSpells];
            int j = 0;
            for(int i = 0; i < 32; i++) {
                if(list[i].Level <= character.getMagicPL()+1 
                        && list[i].ManaCost <= character.getCurrentManna()){
                    myList[j] = list[i];
                    j++;
                }
            }
        }
        return myList;
    }
    
    
    /** =======================================
     *      GUI functions
     *  =======================================
     */
    private void prepareGUI(){
      //mainFrame = new JFrame(character.getName());
      //mainFrame.setSize(frame_width,frame_height);
      //mainFrame.setLayout(new GridLayout(2, 3));
      //mainFrame.addWindowListener(new WindowAdapter() {
         //@Override
        // public void windowClosing(WindowEvent windowEvent){
            //System.exit(0);
         //}      
      //});   
      
      
      MainSpellListPanel = new JPanel();
      MainSpellListPanel.setLayout(new BoxLayout(MainSpellListPanel,BoxLayout.Y_AXIS));
      TitledBorder title;
      
      if(character.getMagicPL() == 0 && character.getCurrentManna() == 0){
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
      //mainFrame.add(scrollpanel);
      //mainFrame.setVisible(false); 
    }
    
    public void hideWindow() {
        //mainFrame.setVisible(false);
        //popup.close();
    }

    public Stage getSpellBook() {
        getSpellBookOld();
        // This is public, it shouldn't be setting a class member anyway
        // TODO: Stage can't be a class data member
        return JFXpopup.getJFXPopup( MainSpellListPanel );
    }
    
    private void getSpellBookOld(){
        prepareGUI();
        //mainFrame.setVisible(true);
        // get the spell book for this character
        //Spell_Book sb = new Spell_Book(character);
        Spell[] myspells;
        //myspells = sb.MySpells(character);
        myspells = MySpells(character);
        
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
        //mainFrame.setVisible(true); 
    }
    
}
