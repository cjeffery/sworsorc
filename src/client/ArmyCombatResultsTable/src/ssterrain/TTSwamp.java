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
public class TTSwamp implements TerrainType{
    public TTSwamp(){
        
    }

    @Override
    public double getMovementCost(Unit unit) {
        if(unit.getRace().equals(Race.Cronk))return 2;
        else if(unit.getRace().equals(Race.SwampCreature))return 1;
        else return 3;
    }

    @Override
    public double getCombatMultiplier(Unit unit) {
        if(unit.getRace().equals(Race.SwampCreature))return 3;
        else return 2;
    }

    @Override
    public String getCombatEffect(Unit unit) {
        return "";
    }
    
    @Override
    public String toString(){
        return "Swamp";
    }
}
