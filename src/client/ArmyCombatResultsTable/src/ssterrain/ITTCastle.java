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
public class ITTCastle implements ImprovedTerrainType{
    private Hex hex;
    
    public ITTCastle(Hex thisHex){
        this.hex = thisHex;
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
        return "Castle";
    }
    
    @Override
    public String toString(){
        return "Castle";
    }

    @Override
    public double getMovementOverride(Unit unit) {
        return 0;
    }
    
    
}
