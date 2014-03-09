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
public class TTCultivated implements TerrainType{
    public TTCultivated(){
        
    }

    @Override
    public double getMovementCost(MoveableUnit unit) {
        if(unit.getUnitType().equals(UnitType.Character)) return 0.5;
        else return 1;
    }

    @Override
    public double getCombatMultiplier(MoveableUnit unit) {
        return 1;
    }

    @Override
    public String getCombatEffect(MoveableUnit unit) {
        return "";
    }
    
    @Override
    public String toString(){
        return "Cultivated";
    }
}
