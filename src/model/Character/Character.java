/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Character;

import Spells.Spell_Book;
import Units.MoveableUnit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Tao and Cameron
 */
public final class Character extends MoveableUnit{
    protected String  Name;
    // a character's magic power level may differ from his average MagicPL
    protected int MagicPL;
    protected int MagicPotential;
    protected int magicProfile;
    protected int magicResistance;
    protected String homeHex;
    protected double  CurrentManna;
    protected Spell_Book spellBook;
    protected MagicColor magicColor;
    protected int leadership;
    protected int diplomacy;
    
 
    
    //public int CurrentHex;
    
    public Character(){
        super();
        this.UnitType = UnitType.Character;
    }
    
    public static Character createCharacter(String name){
        Character newChar = new Character();
        switch (name){
            case "Emperor Coron III the Unconquerable" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "0552";
                newChar.magicProfile = 6;
                newChar.magicResistance = 4;
                newChar.leadership = 5;
                newChar.diplomacy = 3;
                break;
            case "The Paladin Glade" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "0452";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 3;
                newChar.diplomacy = 4;
                break;  
            case "Stephen the Paladin" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "0950";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 2;
                newChar.diplomacy = 2;
                break;   
            case "Lord Dil" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "0451";
                newChar.magicProfile = 4;
                newChar.magicResistance = 4;
                newChar.leadership = 2;
                newChar.diplomacy = 3;
                break;
            case "Tim the Enchanter" : 
                newChar.Name = name;
                newChar.MagicPL = 4;
                newChar.MagicPotential = 16;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "0245";
                newChar.magicProfile = 6;
                newChar.magicResistance = 4;
                newChar.leadership = 0;
                newChar.diplomacy = 1;
                break; 
            case "Eodred the Sorceress" : 
                newChar.Name = name;
                newChar.MagicPL = 4;
                newChar.MagicPotential = 12;
                newChar.magicColor = MagicColor.blue;
                newChar.homeHex = "0540";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 0;
                newChar.diplomacy = 3;
                break;
            case "Larraka" : 
                newChar.Name = name;
                newChar.MagicPL = 5;
                newChar.MagicPotential = 17;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "3713";
                newChar.magicProfile = 5;
                newChar.magicResistance = 4;
                newChar.leadership = 1;
                newChar.diplomacy = 0;
                break;
            case "Talerren the Not-So-Brave" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "0803";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 4;
                newChar.diplomacy = 3;
                break;  
            case "Weldron" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "2433";
                newChar.magicProfile = 1;
                newChar.magicResistance = 3;
                newChar.leadership = 3;
                newChar.diplomacy = 2;
                break;  
            case "Almuric" : 
                newChar.Name = name;
                newChar.MagicPL = 1;
                newChar.MagicPotential = 6;
                newChar.magicColor = MagicColor.blue;
                newChar.homeHex = "2341";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 3;
                newChar.diplomacy = 1;
                break; 
            case "Dierdra" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "3638";
                newChar.magicProfile = 3;
                newChar.magicResistance = 2;
                newChar.leadership = 3;
                newChar.diplomacy = 2;
                break; 
            case "Wendolyn the Wary" : 
                newChar.Name = name;
                newChar.MagicPL = 3;
                newChar.MagicPotential = 15;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "1528";
                newChar.magicProfile = 2;
                newChar.magicResistance = 3;
                newChar.leadership = 0;
                newChar.diplomacy = -1;
                break;
            case "Curvenol" : 
                newChar.Name = name;
                newChar.MagicPL = 5;
                newChar.MagicPotential = 16;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "0352";
                newChar.magicProfile = 5;
                newChar.magicResistance = 2;
                newChar.leadership = 1;
                newChar.diplomacy = 0;
                break; 
            case "Zareth" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "1246";
                newChar.magicProfile = 5;
                newChar.magicResistance = 5;
                newChar.leadership = 1;
                newChar.diplomacy = 2;
                break; 
            case "Loki Hellsson" : 
                newChar.Name = name;
                newChar.MagicPL = 6;
                newChar.MagicPotential = 20;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "0606";
                newChar.magicProfile = 6;
                newChar.magicResistance = 5;
                newChar.leadership = 5;
                newChar.diplomacy = 4;
                break; 
            case "Alric" : 
                newChar.Name = name;
                newChar.MagicPL = 3;
                newChar.MagicPotential = 11;
                newChar.magicColor = MagicColor.blue;
                newChar.homeHex = "0509";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 0;
                newChar.diplomacy = 1;
                break; 
            case "X the Unknown (Roc Deathsinger)" : 
                newChar.Name = name;
                newChar.MagicPL = 6;
                newChar.MagicPotential = 20;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "0904";
                newChar.magicProfile = 4;
                newChar.magicResistance = 5;
                newChar.leadership = 1;
                newChar.diplomacy = 2;
                break;
            case "Gygax Dragonlord" : 
                newChar.Name = name;
                newChar.MagicPL = 2;
                newChar.MagicPotential = 6;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "0302";
                newChar.magicProfile = 6;
                newChar.magicResistance = 5;
                newChar.leadership = 3;
                newChar.diplomacy = 3;
                break;
            case "Gislan the Rock" : 
                newChar.Name = name;
                newChar.MagicPL = 4;
                newChar.MagicPotential = 8;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "0818";
                newChar.magicProfile = 3;
                newChar.magicResistance = 4;
                newChar.leadership = 2;
                newChar.diplomacy = 4;
                break;  
            case "Gerudirr Dragonslayer" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "2808";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 3;
                newChar.diplomacy = 3;
                break; 
            case "Zurik Bladebreaker" : 
                newChar.Name = name;
                newChar.MagicPL = 4;
                newChar.MagicPotential = 12;
                newChar.magicColor = MagicColor.blue;
                newChar.homeHex = "2517";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 2;
                newChar.diplomacy = 2;
                break;  
            case "Narklath" : 
                newChar.Name = name;
                newChar.MagicPL = 2;
                newChar.MagicPotential = 13;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "3645";
                newChar.magicProfile = 1;
                newChar.magicResistance = 4;
                newChar.leadership = 2;
                newChar.diplomacy = 1;
                break;    
            case "Gwaigilion Elengal" : 
                newChar.Name = name;
                newChar.MagicPL = 3;
                newChar.MagicPotential = 10;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "0630";
                newChar.magicProfile = 4;
                newChar.magicResistance = 4;
                newChar.leadership = 3;
                newChar.diplomacy = 6;
                break; 
            case "Maytwist" : 
                newChar.Name = name;
                newChar.MagicPL = 3;
                newChar.MagicPotential = 10;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "3333";
                newChar.magicProfile = 2;
                newChar.magicResistance = 3;
                newChar.leadership = 0;
                newChar.diplomacy = 3;
                break;   
            case "Linfalas" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "3426";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 4;
                newChar.diplomacy = 4;
                break; 
            case "Dalmilandril" : 
                newChar.Name = name;
                newChar.MagicPL = 4;
                newChar.MagicPotential = 12;
                newChar.magicColor = MagicColor.blue;
                newChar.homeHex = "0319";
                newChar.magicProfile = 4;
                newChar.magicResistance = 4;
                newChar.leadership = 1;
                newChar.diplomacy = 2;
                break; 
            case "Sliggoth" : 
                newChar.Name = name;
                newChar.MagicPL = 2;
                newChar.MagicPotential = 8;
                newChar.magicColor = MagicColor.blue;
                newChar.homeHex = "1341";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 2;
                newChar.diplomacy = -1;
                break; 
            case "Gilith" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "0302";
                newChar.magicProfile = 1;
                newChar.magicResistance = 4;
                newChar.leadership = 2;
                newChar.diplomacy = 3;
                break;  
            case "Peg-Leg Gonzo" : 
                newChar.Name = name;
                newChar.MagicPL = 2;
                newChar.MagicPotential = 7;
                newChar.magicColor = MagicColor.blue;
                newChar.homeHex = "3150";
                newChar.magicProfile = 2;
                newChar.magicResistance = 3;
                newChar.leadership = 0;
                newChar.diplomacy = -1;
                break;  
            case "Unamit Ahazredit" : 
                newChar.Name = name;
                newChar.MagicPL = 4;
                newChar.MagicPotential = 13;
                newChar.magicColor = MagicColor.blue;
                newChar.homeHex = "3150";
                newChar.magicProfile = 3;
                newChar.magicResistance = 4;
                newChar.leadership = 3;
                newChar.diplomacy = 3;
                break; 
            case "Raman Cronkevitch" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "2151";
                newChar.magicProfile = 4;
                newChar.magicResistance = 4;
                newChar.leadership = 3;
                newChar.diplomacy = 1;
                break;
            case "Svartz Tarnkap" : 
                newChar.Name = name;
                newChar.MagicPL = 2;
                newChar.MagicPotential = 8;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "2151";
                newChar.magicProfile = 4;
                newChar.magicResistance = 3;
                newChar.leadership = 2;
                newChar.diplomacy = 3;
                break;
            case "Mellanthia" : 
                newChar.Name = name;
                newChar.MagicPL = 3;
                newChar.MagicPotential = 8;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "3808";
                newChar.magicProfile = 6;
                newChar.magicResistance = 4;
                newChar.leadership = 2;
                newChar.diplomacy = 3;
                break;
            case "Jeremiah Ben Ruben" : 
                newChar.Name = name;
                newChar.MagicPL = 2;
                newChar.MagicPotential = 8;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "2133";
                newChar.magicProfile = 2;
                newChar.magicResistance = 4;
                newChar.leadership = 4;
                newChar.diplomacy = 6;
                break; 
            case "Theregond the Mage" : 
                newChar.Name = name;
                newChar.MagicPL = 3;
                newChar.MagicPotential = 14;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "2133";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 1;
                newChar.diplomacy = 2;
                break; 
            case "Snorri Gundarchuksson" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "3531";
                newChar.magicProfile = 4;
                newChar.magicResistance = 4;
                newChar.leadership = 3;
                newChar.diplomacy = 4;
                break; 
            case "Ganab the Nasty" : 
                newChar.Name = name;
                newChar.MagicPL = 3;
                newChar.MagicPotential = 12;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "1909";
                newChar.magicProfile = 4;
                newChar.magicResistance = 2;
                newChar.leadership = 2;
                newChar.diplomacy = 2;
                break;
            case "Chairman Naskhund" : 
                newChar.Name = name;
                newChar.MagicPL = 3;
                newChar.MagicPotential = 8;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "1611";
                newChar.magicProfile = 5;
                newChar.magicResistance = 4;
                newChar.leadership = 4;
                newChar.diplomacy = 4;
                break; 
            case "Krawn the Crazy" : 
                newChar.Name = name;
                newChar.MagicPL = 1;
                newChar.MagicPotential = 6;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "1614";
                newChar.magicProfile = 4;
                newChar.magicResistance = 3;
                newChar.leadership = 3;
                newChar.diplomacy = 5;
                break; 
            case "Zarko" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "1605";
                newChar.magicProfile = 2;
                newChar.magicResistance = 2;
                newChar.leadership = 2;
                newChar.diplomacy = -1;
                break;    
            default: name = "Invalid Character Name";
                     break;
        }
       
        return newChar;
    }
    
    public Character(String a, int b, double c, /*int h*/ String h) {
        super();
        this.UnitType = UnitType.Character;
        Name        = a;
        MagicPL     = b;
        //MagicPotential  = c;
        CurrentManna = c; //(double)MagicPotential;
        
        location = h;
        spellBook = new Spell_Book(this);
    }
    
    
    
    public void CastSpell(){
        //Spell_Book sb = new Spell_Book(c);
        //sb.getSpellBook();
        spellBook.getSpellBook();
    }
    
    public boolean checkManna(double x){
        return CurrentManna >= x;
    }
    public void CostManna(double mc){
        CurrentManna -= mc;
    }
    
    /** ===========================
     *      Temp GUI function for get character info
     *  ===========================
     * @param c
     */
    public void getCharacter(final Character c){
        final JFrame char_info = new JFrame("Character Info");
        char_info.setSize(300,300);
        
        char_info.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { 
                System.exit(0);
            }

        });
        
        char_info.setLayout(null);
        char_info.setLocation(10,50);
        
        JLabel char_name = new JLabel("Character name: ");
        char_name.setBounds(10, 20, 150, 20);
        char_info.add(char_name);
        // Textfield character name
        final JTextField char_name_field = new JTextField();
        char_name_field.setBounds(200, 20, 100, 20);
        char_info.add(char_name_field);
        
        JLabel char_PL = new JLabel("Power Level:");
        char_PL.setBounds(10, 60, 150, 20);
        char_info.add(char_PL);
        // Textfield character PL
        final JTextField char_PL_field = new JTextField();
        char_PL_field.setBounds(200,60, 100, 20);
        char_info.add(char_PL_field);
        
        JLabel char_CM = new JLabel("Current Manna:");
        char_CM.setBounds(10,100,150,20);
        char_info.add(char_CM);
        // Textfield character current manna
        final JTextField char_CM_field = new JTextField();
        char_CM_field.setBounds(200,100,100,20);
        char_info.add(char_CM_field);
        
        // Hex info
        JLabel char_hex = new JLabel("Current Hex number:");
        char_hex.setBounds(10,140,150,20);
        char_info.add(char_hex);
        // Textfield character hex
        final JTextField char_hex_field = new JTextField();
        char_hex_field.setBounds(200, 140, 100, 20);
        char_info.add(char_hex_field);
        
        JButton get_c = new JButton("Get");
        get_c.setBounds(150, 200, 50, 20);
        char_info.add(get_c);
        
        get_c.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                c.Name = char_name_field.getText();
                c.MagicPL = Integer.parseInt(char_PL_field.getText());
                c.CurrentManna = Double.parseDouble(char_CM_field.getText());
                c.location = char_hex_field.getText();
                //c.CurrentHex = Integer.parseInt(char_hex_field.getText());
                
                char_info.dispose();
                
                CastSpell();
            }
        });
        
        char_info.setVisible(true);
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getMagicPL() {
        return MagicPL;
    }

    public void setMagicPL(int MagicPL) {
        this.MagicPL = MagicPL;
    }

    public int getMagicPotential() {
        return MagicPotential;
    }

    public void setMagicPotential(int MagicPotential) {
        this.MagicPotential = MagicPotential;
    }

    public double getCurrentManna() {
        return CurrentManna;
    }

    public void setCurrentManna(double CurrentManna) {
        this.CurrentManna = CurrentManna;
    }

    public Spell_Book getSpellBook() {
        return spellBook;
    }

    public void setSpellBook(Spell_Book spellBook) {
        this.spellBook = spellBook;
    }
    
    public void increaseManna(int a){
        this.CurrentManna += a;
    }
    
    public boolean decreaseManna(double a){
        if(CurrentManna > a){
            this.CurrentManna -= a;
            return true;
        } else {
            return false;
        }
    }

    public int getMagicProfile() {
        return magicProfile;
    }

    public void setMagicProfile(int magicProfile) {
        this.magicProfile = magicProfile;
    }

    public int getMagicResistance() {
        return magicResistance;
    }

    public void setMagicResistance(int magicResistance) {
        this.magicResistance = magicResistance;
    }

    public MagicColor getMagicColor() {
        return magicColor;
    }

    public void setMagicColor(MagicColor magicColor) {
        this.magicColor = magicColor;
    }

    public int getLeadership() {
        return leadership;
    }

    public void setLeadership(int leadership) {
        this.leadership = leadership;
    }

    public int getDiplomacy() {
        return diplomacy;
    }

    public void setDiplomacy(int diplomacy) {
        this.diplomacy = diplomacy;
    }

    public String getHomeHex() {
        return this.homeHex;
    }
    
    
}
