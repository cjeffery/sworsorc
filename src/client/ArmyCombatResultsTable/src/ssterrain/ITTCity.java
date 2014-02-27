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
public class ITTCity implements ImprovedTerrainType{
    Hex hex;
    
    public ITTCity(Hex thisHex){
        hex = thisHex;
    }

    @Override
    public double getMovementCost(Unit unit) {
        return 0;
    }

    @Override
    public double getCombatMultiplier(Unit unit) {
        return 3;
    }

    @Override
    public String getCombatEffect(Unit unit) {
        return "City";
    }
    
    @Override
    public String toString(){
        return "City";
    }

    @Override
    public double getMovementOverride(Unit unit) {
        if(unit.getUnitType().equals(UnitType.Character)) {
            return  0.5;
        }
        else return 0;
    }
}
