/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ssterrain;

/**
 *
 * @author John Goettsche
 * CS 383 Software Engineering
 */
import Units.*;
import sshexmap.MapHex;
public abstract class TerrainType {
    public abstract double getMovementCost(MoveableUnit unit);
    public abstract double getCombatMultiplier(MoveableUnit unit);
    public abstract String getCombatEffect(MoveableUnit unit);

    /**
     * convert XML terrain key to terrain type
     * @param code The string key from an XML file
     * @return The terrain type, or null.
     */
    public static TerrainType makeTerrainType(String code) {
        switch(code) {
            /* blasted       */ case "Bl":   return new TTBlasted();
            /* bridge        */ case "Br":   return new TTWater();
            /* broken        */ case "Bro":  return new TTBroken();
            /* city          */ case "Ci":   return new TTClear();
            /* clear         */ case "Cl":   return new TTClear();
            /* cultivated    */ case "Cu":   return new TTCultivated();
            /* dragon tunnel */ case "D":    return new TTMountains();
            /* forest        */ case "Fore": return new TTForest();
            /* glacier       */ case "G":    return new TTGlacier();
            /* karoo         */ case "K":    return new TTKaroo();
            /* water         */ case "Mrl":  return new TTWater();
            /* mountains     */ case "Mo":   return new TTMountains();
            /* clear         */ case "Po":   return new TTClear();
            /* rough         */ case "R":    return new TTRough();
            /* swamp         */ case "Sw":   return new TTSwamp();
            /* clear         */ case "V":    return new TTClear();
            /* woods         */ case "Wo":   return new TTWoods();
            //case "Sh": "Special Hex"; 
        }
        return null;
    }    
    
    
}
