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
public class ITTCapital implements ImprovedTerrainType{
    Hex hex;
    
    public ITTCapital(Hex thisHex){
        this.hex = thisHex;
    }

    @Override
    public double getMovementCost(Unit unit) {
        return 0;
    }

    @Override
    public double getCombatMultiplier(Unit unit) {
        return 1;
    }

    @Override
    public String getCombatEffect(Unit unit) {
        return "This is a Capital";
    }
    
    @Override
    public String toString(){
        return "Capital";
    }

    @Override
    public double getMovementOverride(Unit unit) {
        return 0;
    }
}
