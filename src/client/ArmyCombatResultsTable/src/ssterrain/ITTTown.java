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
public class ITTTown implements ImprovedTerrainType{
    Hex hex;
    
    public ITTTown(Hex thisHex){
        this.hex = thisHex;
    }

    @Override
    public double getMovementCost(Unit unit) {
        return 0;
    }

    @Override
    public double getCombatMultiplier(Unit unit) {
        return 2;
    }

    @Override
    public String getCombatEffect(Unit unit) {
        return "Town";
    }
    
    @Override
    public String toString(){
        return "Town";
    }

    @Override
    public double getMovementOverride(Unit unit) {
        return 0;
    }
    
}
