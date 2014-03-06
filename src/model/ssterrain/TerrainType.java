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
public interface TerrainType {
    public double getMovementCost(Unit unit);
    public double getCombatMultiplier(Unit unit);
    public String getCombatEffect(Unit unit);
    
}
