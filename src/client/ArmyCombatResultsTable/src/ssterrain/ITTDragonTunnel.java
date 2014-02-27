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
public class ITTDragonTunnel implements ImprovedTerrainType{
    Hex hex;
    
    public ITTDragonTunnel(Hex thisHex){
        this.hex = thisHex;
    }

    @Override
    public double getMovementCost(Unit unit) {
        if(unit.getRace().equals(Race.Dragon))return 1;
        else return 2;
    }

    @Override
    public double getCombatMultiplier(Unit unit) {
        if(unit.getRace().equals(Race.Dragon))return 4;
        else return 1;
    }

    @Override
    public String getCombatEffect(Unit unit) {
        return "Dragon Tunnel Complex";
    }
    
    @Override
    public String toString(){
        return "Dragon Tunnel";
    }

    @Override
    public double getMovementOverride(Unit unit) {
        return 0;
    }
    
}
