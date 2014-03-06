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
public class TTWoods implements TerrainType{
    public TTWoods(){
        
    }

    @Override
    public double getMovementCost(Unit unit) {
        if(unit.getRace().equals(Race.Elves))return 1;
        else return 2;
    }

    @Override
    public double getCombatMultiplier(Unit unit) {
        return 2;
    }

    @Override
    public String getCombatEffect(Unit unit) {
        return "";
    }
    
    @Override
    public String toString(){
        return "Woods";
    }
}
