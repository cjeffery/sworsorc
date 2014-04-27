/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Spells;

import Character.Character;
import mainswordsorcery.Game;
import Units.*;

import Spells.PL_1.*;
import Spells.PL_2.*;
import Spells.PL_3.*;
import Spells.PL_4.*;
import Spells.PL_5.*;
import Spells.PL_6.*;
import Spells.PL_7.*;


/**
 *
 * @author Tao Zhang
 */
public class CastSpell{
    public static ArmyUnit conjured = null;
    public double TotalMannaCost;
    
    Character character;
    
    public CastSpell(Character c){
        character = c;
    }
    
    public void call_spell(String name){
        
        switch(name){
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
                Fear fear = new Fear();
                break;
            case "Conjure Centauroid Cavalry":
                C_C_C c_c_c = new C_C_C(character);
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
                C_K_I c_k_i = new C_K_I(character);
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
            conjured.ResetWorkingMovement();
            //insert the conjured unit into the Unit Pool
            conjured = null;
        }
    }
    
}
