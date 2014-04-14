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
import sshexmap.*;
public abstract class ImprovedTerrainType {
    public ImprovedTerrainType() {};
    public ImprovedTerrainType(MapHex h) {};
    public abstract double getMovementCost(MoveableUnit unit);
    public abstract double getCombatMultiplier(MoveableUnit unit);
    public abstract String getCombatEffect(MoveableUnit unit);
    public abstract double getMovementOverride(MoveableUnit unit);
    
    /**
     * convert XML terrain key to terrain improvement type
     * this should probably be in ImprovedTerrainType but
     * I didn't want to 
     * @param code The string key from an XML file
     * @return The terrain type, or null.
     */
    public static ImprovedTerrainType makeImprovement(String code, MapHex h) {
        switch(code) {
            case "Br": return new ITTBridge(h);
            case "Ci": return new ITTCity(h);
            case "D": return new ITTDragonTunnel(h);
            case "Po": return new ITTPortal(h);
            //case "Sh": "Special Hex"; 
            case "V": return new ITTVortex(h);
        }
        return null;
    }
}
