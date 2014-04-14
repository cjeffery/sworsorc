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
public interface HexEdge {
    public double getMovementCost(MoveableUnit unit);
    public double getCombatMultiplier(MoveableUnit unit);
    public String getCombatEffect(MoveableUnit unit);
    public HexEdgeType getEdgeType();
    public int getEdge();
}
