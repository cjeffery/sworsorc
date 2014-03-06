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
public class TTBlasted implements TerrainType{
    public TTBlasted(){
    }

    @Override
    public double getMovementCost(Unit unit) {
        if(unit.getUnitType().equals(UnitType.Fighter)) return 3;
        else if(unit.getUnitType().equals(UnitType.MagicUser)) return 2;
        else return 99;
    }

    @Override
    public double getCombatMultiplier(Unit unit) {
        return 1;
    }

    @Override
    public String getCombatEffect(Unit unit) {
        return "";
    }
    
    @Override
    public String toString(){
        return "Blasted";
    }
}
