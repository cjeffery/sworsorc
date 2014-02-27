/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ssterrain;

import java.util.ArrayList;

/**
 *
 * @author John Goettsche
 * CS 383 Software Engineering
 */
public class HEWall implements HexEdge{
    Hex hex;
    int edge;
    
    public HEWall(Hex thisHex, int thisEdge){
        this.hex = thisHex;
        this.edge = thisEdge;
    }

    @Override
    public double getMovementCost(Unit unit) {
        ArrayList<HexEdgeType> thisEdge = hex.getEdgeType(edge);
        Boolean aBridge = false;
        for(int i = 0; i < thisEdge.size(); i++)
            if(thisEdge.get(i).equals(HexEdgeType.Bridge))aBridge = true;
        if(aBridge)return 0;
        else return 1;
    }

    @Override
    public double getCombatMultiplier(Unit unit) {
        return 1;
    }

    @Override
    public String getCombatEffect(Unit unit) {
        return "";
    }

    @Override
    public HexEdgeType getEdgeType() {
        return HexEdgeType.Wall;
    }

    @Override
    public int getEdge() {
        return edge;
    }

}
