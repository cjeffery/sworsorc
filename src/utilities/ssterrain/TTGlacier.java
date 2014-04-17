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
public class TTGlacier extends TerrainType{
    public TTGlacier(){
        
    }
    
    @Override
    public double getMovementCost(MoveableUnit unit) {
        if(unit.getRace().equals(Race.KillerPenguin))return 1;
        else if(unit.getUnitType().equals(UnitType.Character)) return 5;
        else return 99;
    }

    @Override
    public double getCombatMultiplier(MoveableUnit unit) {
        return 2;
    }

    @Override
    public String getCombatEffect(MoveableUnit unit) {
        return "";
    }    
    
    @Override
    public String toString(){
        return "Glacier";
    }
}
