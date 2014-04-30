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
public final class Characters extends MoveableUnit{
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
    
    public Characters(){
        super();
        this.UnitType = UnitType.Character;
        this.movement = 9.;
        this.ResetWorkingMovement();
        spellBook = new Spell_Book(this);
    }
    
    public Characters(String a, int b, double c, /*int h*/ String h) {
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
    public void getCharacter(final Characters c){
        final JFrame char_info = new JFrame("Character Info");
        char_info.setSize(300,300);
        
        char_info.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { 
                //System.exit(0);
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
        if(CurrentManna > this.MagicPotential)
            CurrentManna = MagicPotential;
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
