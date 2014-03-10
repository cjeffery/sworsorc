/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implement_spells.spells;

import implement_spells.spells.PL_1.*;
import implement_spells.spells.PL_2.*;
import implement_spells.spells.PL_3.*;
import implement_spells.spells.PL_4.*;
import implement_spells.spells.PL_5.*;
import implement_spells.spells.PL_6.*;
import implement_spells.spells.PL_7.*;

import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author 张涛
 */
public class CastSpell extends JComponent{
    
    String Name;

    public CastSpell(String n){
        Name = n;
    }
    
    @Override
    public void paintComponent(Graphics g){
        
    }
    
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
                C_Z_I c_z_i = new C_Z_I();
                break;
            // ======================
            // PL_2
            case "Mana Transfer":
                Mana_Transfer m_t = new Mana_Transfer();
                break;
            case "River Crossing":
                River_Crossing r_c = new River_Crossing();
                break;
            case "Morale":
                Morale morale = new Morale();
                break;
            case "Fear":
                Fear fear = new Fear();
                break;
            case "Conjure Centauroid Cavalry":
                C_C_C c_c_c = new C_C_C();
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
                C_W_A c_w_a = new C_W_A();
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
            case "Teleporation Control":
                Teleporation_Control t_c = new Teleporation_Control();
                break;
            case "Conjure Koboldic Infantry":
                C_K_I c_k_i = new C_K_I();
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
                C_W_T c_w_t = new C_W_T();
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
                C_D_I c_d_i = new C_D_I();
                break;
            // ===============================
            // PL_7
            case "Wizard Wheel":
                Wizard_Wheel w_w = new Wizard_Wheel();
                break;
            default:
                System.err.println("Error: Unknown spell name: CaseSpell: call_spell");
        }
    }
    
}
