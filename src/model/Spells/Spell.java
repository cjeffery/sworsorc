/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package Spells;

import Character.Characters;
import Spells.PL_1.*;
import Spells.PL_2.*;
import Spells.PL_3.*;
import Spells.PL_4.*;
import Spells.PL_5.*;
import Spells.PL_6.*;
import Spells.PL_7.*;
import Spells.Spell_Book;
import Units.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Tyler and Tao Zhang
 */
public class Spell implements Serializable {

    // This was causing network client to panic, it needs to be serializable!
    // Its not even being used, what is its purpose?
    // public Stage popup;
            
    private static final long serialVersionUID = 1L;
    Characters character;
    
    String  Name;
    int     Level;
    double  ManaCost;
    
    public static ArmyUnit conjured = null;
    
    @FXML public Button sButton;
    public  JButton spellbutton;
    private JFrame spellFrame;
 
    //public JFrame BookFrame;
    
    public Spell(String n, int lv, double mc, Characters c) {
        Name  = n;
        Level = lv;
        ManaCost  = mc;   
        //BookFrame = mainf;
        sButton = new javafx.scene.control.Button(n);
        spellbutton = new JButton(n);
        character = c;
        
        spellbutton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //Spell_Book.hideWindow();
                showSpellDetails();
                showButtonDemo();
            }
        });
    }
    
    public void showSpellDetails(){
        spellFrame = new JFrame(Name);
        spellFrame.setSize(300,500);
        
        spellFrame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {   //System.exit(0); 
                //BookFrame.setVisible(true);
                //Spell_Book.popupBook.close();
            }
        });
        
        spellFrame.getContentPane().add(new Spell_Details(Name));
        //popup = JFXpopup.getJFXPopup(spellbutton)
        spellFrame.setVisible( true );
    }
    
    public void showButtonDemo(){
        JButton cast_button = new JButton("Cast");
        JButton cancel_button = new JButton("Cancel");
        
        cast_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //JFrame spell_cast_window = new JFrame(Name);
                spellFrame.dispose();
                //CastSpell cs = new CastSpell(character);
                call_spell();
            }
        });
        
        cancel_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                spellFrame.dispose();
                //BookFrame.setVisible(true);
                //popup.;
            }
        });
        
        JPanel selection = new JPanel();// panel for cast and cancel buttons
        selection.setLayout(new FlowLayout());
        selection.add(cast_button);
        selection.add(cancel_button);
        
        spellFrame.setLayout(new BorderLayout());
        spellFrame.add(selection, BorderLayout.SOUTH);
        spellFrame.setVisible(true);
    }
    
    
    /** ==================================
     *      Spell details
     *  ==================================
     */
    
    /** ==================================
     *      Call Spells
     *  ==================================
     */
    public void call_spell(){
        
        switch(Name){
            //=======================
            // PL_1
            case "Teleportation Protection":
                TP_Protection tp_protection = new TP_Protection();
                break;
            case "Force Wall":
                Force_Wall force_wall = new Force_Wall();
                break;
            case "Conjure Zombie Infantry":
                C_Z_I c_z_i = new C_Z_I(character);
                c_z_i.performSpellEffects();
                break;
            // ======================
            // PL_2
            case "Manna Transfer":
                Manna_Transfer m_t = new Manna_Transfer();
                break;
            case "River Crossing":
                River_Crossing r_c = new River_Crossing();
                break;
            case "Morale":
                Morale morale = new Morale(character);
                break;
            case "Fear":
                Fear fear = new Fear(character);
                break;
            case "Conjure Centauroid Cavalry":
                C_C_C c_c_c = new C_C_C(character);
                c_c_c.performSpellEffects();
                break;
            // ===============================
            // PL_3
            case "Monsoon":
                Monsoon monsoon = new Monsoon();
                break;
            case "Enhance Stature":
                Enhance_Stature e_s = new Enhance_Stature();
                break;
            case "Forest":
                Forest forest = new Forest();
                break;
            case "Vortex Suppression":
                Vortex_Suppression v_s = new Vortex_Suppression();
                break;
            case "Immobilization":
                Immobilization immobilization = new Immobilization();
                break;
            case "Conjure Wyvern Airtroops":
                C_W_A c_w_a = new C_W_A(character);
                c_w_a.performSpellEffects();
                break;
            case "Dispell Magicks":
                Dispell_Magicks d_m = new Dispell_Magicks();
                break;
            // ===============================
            // PL_4
            case "Disintegration":
                Distintegration distintegration = new Distintegration();
                break;
            case "Building":
                Building building = new Building();
                break;
            case "Vortex Creation":
                Vortex_Creation v_c = new Vortex_Creation();
                break;
            case "Ersatz Winter":
                Ersatz_Winter e_w = new Ersatz_Winter();
                break;
            case "Teleportation Control":
                Teleportation_Control t_c = new Teleportation_Control(character);
                break;
            case "Conjure Koboldic Infantry":
                C_K_I c_k_i = new C_K_I(character);
                c_k_i.performSpellEffects();
                break;
            // ===============================
            // PL_5
            case "Planar Return":
                Planar_Return p_r = new Planar_Return();
                break;
            case "Summon Demon":
                Summon_Demon s_d = new Summon_Demon();
                break;
            case "Banish Conjured Troops":
                B_C_T b_c_t = new B_C_T();
                break;
            case "Conjure Wraith Troops":
                C_W_T c_w_t = new C_W_T(character);
                c_w_t.performSpellEffects();
                break;
            // ===============================
            // PL_6
            case "Bind Demon":
                Bind_Demon bind_d = new Bind_Demon();
                break;
            case "Banish Demon":
                Banish_Demon banish_d = new Banish_Demon();
                break;
            case "Summon Force":
                Summon_Force s_f = new Summon_Force();
                break;
            case "Firestorm":
                Firestorm firestorm = new Firestorm();
                break;
            case "Berserkergang":
                Berserkergang berserkergang = new Berserkergang();
                break;
            case "Conjure Demonice Infantry":
                C_D_I c_d_i = new C_D_I(character);
                c_d_i.performSpellEffects();
                break;
            // ===============================
            // PL_7
            case "Wizard Wheel":
                Wizard_Wheel w_w = new Wizard_Wheel();
                break;
            default:
                System.err.println("Error: Unknown spell name: CastSpell: call_spell");
        }
        
        if(conjured != null){
            //TODO:
            System.out.println("placing unit into pool");
            conjured.ResetWorkingMovement();
            conjured.setRace(Race.Conjured);
            conjured.setNation(character.getNation());
            UnitPool unitpool = UnitPool.getInstance();
            unitpool.addUnit(1, conjured, character.getLocation());

            //insert the conjured unit into the Unit Pool
            conjured = null;
        }
        character.hideSpellWindow();
    }
    
}
