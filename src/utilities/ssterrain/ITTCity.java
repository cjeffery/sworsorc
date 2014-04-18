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
import sshexmap.MapHex;
public class ITTCity extends ImprovedTerrainType{
    MapHex hex;
    
    public ITTCity(MapHex thisHex){
        hex = thisHex;
    }

    @Override
    public double getMovementCost(MoveableUnit unit) {
        return 0;
    }

    @Override
    public double getCombatMultiplier(MoveableUnit unit) {
        return 3;
    }

    @Override
    public String getCombatEffect(MoveableUnit unit) {
        return "City";
    }
    
    @Override
    public String toString(){
        return "City";
    }

    @Override
    public double getMovementOverride(MoveableUnit unit) {
        if(unit.getUnitType().equals(UnitType.Character)) {
            return  0.5;
        }
        else return 0;
    }
}
