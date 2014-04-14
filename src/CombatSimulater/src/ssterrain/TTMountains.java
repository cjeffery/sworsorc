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
import Units.Race;
import Units.*;
public class TTMountains implements TerrainType{
    public TTMountains(){
        
    }

    @Override
    public double getMovementCost(MoveableUnit unit) {
        if(unit.getRace().equals(Race.Dwarrows) || unit.getRace().equals(Race.Orc))return 2;
        else return 3;
    }

    @Override
    public double getCombatMultiplier(MoveableUnit unit) {
        if(unit.getRace().equals(Race.Dwarrows))return 4;
        else return 3;
    }

    @Override
    public String getCombatEffect(MoveableUnit unit) {
        return "";
    }
    
    @Override
    public String toString(){
        return "Mountains";
    }
}
