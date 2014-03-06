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
public class TTWater implements TerrainType{
    public TTWater(){
        
    }

    @Override
    public double getMovementCost(Unit unit) {
        if(unit.getRace().equals(Race.SwampCreature))return 2;
        else if(unit.getRace().equals(Race.KillerPenguin))return 1;
        else return 99;
    }

    @Override
    public double getCombatMultiplier(Unit unit) {
        if(unit.getRace().equals(Race.SwampCreature))return 2;
        else return 1;
    }

    @Override
    public String getCombatEffect(Unit unit) {
        return "";
    }
    
    @Override
    public String toString(){
        return "Water";
    }
}
