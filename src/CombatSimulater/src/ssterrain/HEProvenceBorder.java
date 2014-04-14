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
public class HEProvenceBorder implements HexEdge{
    Hex hex;
    int edge;
    
    public HEProvenceBorder(Hex thisHex, int thisEdge){
        this.hex = thisHex;
        this.edge = thisEdge;
    }

    @Override
    public double getMovementCost(MoveableUnit unit) {
        return 0;
    }

    @Override
    public double getCombatMultiplier(MoveableUnit unit) {
        return 1;
    }

    @Override
    public String getCombatEffect(MoveableUnit unit) {
        return "";
    }

    @Override
    public HexEdgeType getEdgeType() {
        return HexEdgeType.ProvenceBoarder;
    } 

    @Override
    public int getEdge() {
        return edge;
    }
    
}
