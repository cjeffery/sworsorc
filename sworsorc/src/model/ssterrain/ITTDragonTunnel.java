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
import Units.Race;
import Units.*;
import sshexmap.MapHex;
public class ITTDragonTunnel extends ImprovedTerrainType{
    MapHex hex;
    
    public ITTDragonTunnel()
    {
        // nur nur nur
    }
    
    public ITTDragonTunnel(MapHex thisHex){
        this.hex = thisHex;
    }

    @Override
    public double getMovementCost(MoveableUnit unit) {
        if(unit.getRace().equals(Race.Dragon))return 1;
        else return 2;
    }

    @Override
    public double getCombatMultiplier(MoveableUnit unit) {
        if(unit.getRace().equals(Race.Dragon))return 4;
        else return 1;
    }

    @Override
    public String getCombatEffect(MoveableUnit unit) {
        return "Dragon Tunnel Complex";
    }
    
    @Override
    public String toString(){
        return "Dragon Tunnel";
    }

    @Override
    public double getMovementOverride(MoveableUnit unit) {
        return 0;
    }
    
}
