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
public class ITTTown extends ImprovedTerrainType{
    MapHex hex;
    
    public ITTTown(MapHex thisHex){
        this.hex = thisHex;
    }

    @Override
    public double getMovementCost(MoveableUnit unit) {
        return 0;
    }

    @Override
    public double getCombatMultiplier(MoveableUnit unit) {
        return 2;
    }

    @Override
    public String getCombatEffect(MoveableUnit unit) {
        return "Town";
    }
    
    @Override
    public String toString(){
        return "Town";
    }

    @Override
    public double getMovementOverride(MoveableUnit unit) {
        return 0;
    }
    
}
