/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ssterrain;

/**
 *
 * @author John
 */
import Units.*;
public class TTBlasted extends TerrainType{
    public TTBlasted(){
    }

    @Override
    public double getMovementCost(MoveableUnit unit) {
        if(unit.getUnitType().equals(UnitType.ArmyUnit)) return 3;
        else if(unit.getUnitType().equals(UnitType.Character)) return 2;
        else return 99;
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
        return "Blasted";
    }
}
