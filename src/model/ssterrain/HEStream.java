/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ssterrain;

import java.util.ArrayList;
import Units.*;
import sshexmap.MapHex;
/**
 *
 * @author John Goettsche
 * CS 383 Software Engineering
 */
public class HEStream extends EdgeElement {
    @Override
    public HexEdgeType getEdgeType() {
        return HexEdgeType.Stream;
    }
    
    /*
    @Override
    public double getMovementCost(MoveableUnit unit) {
        ArrayList<HexEdgeType> thisEdge = hex.getEdgeType(edge);
        Boolean aBridge = false;
        for(int i = 0; i < thisEdge.size(); i++)
            if(thisEdge.get(i).equals(HexEdgeType.Bridge))aBridge = true;
        if(aBridge)return 0;
        else return 1;
    }

    @Override
    public double getCombatMultiplier(MoveableUnit unit) {
        return 2;
    }

    @Override
    public String getCombatEffect(MoveableUnit unit) {
        return "";
    }
    */
}
