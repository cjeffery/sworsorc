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
public class HETrail extends HexEdge{
    MapHex hex;
    int edge;
    
    public HETrail(MapHex thisHex, int thisEdge){
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
        return HexEdgeType.Trail;
    }

    @Override
    public int getEdge() {
        return edge;
    }
}
