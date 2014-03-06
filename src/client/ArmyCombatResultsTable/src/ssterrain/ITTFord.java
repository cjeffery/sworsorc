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
public class ITTFord implements ImprovedTerrainType{
    Hex hex;
    
    public ITTFord(Hex thisHex){
        this.hex = thisHex;
    }

    @Override
    public double getMovementCost(Unit unit) {
        return 3;
    }

    @Override
    public double getCombatMultiplier(Unit unit) {
        return 0.5;
    }

    @Override
    public String getCombatEffect(Unit unit) {
        return "";
    }
    
    @Override
    public String toString(){
        return "Ford";
    }

    @Override
    public double getMovementOverride(Unit unit) {
        return 0;
    }
    
}
