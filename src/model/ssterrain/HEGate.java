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
public class HEGate extends EdgeElement {
    @Override
    public HexEdgeType getEdgeType() {
        return HexEdgeType.Gate;
    }
    
    /*
    @Override
    public double getMovementCost(MoveableUnit unit) {

    }

    @Override
    public double getCombatMultiplier(MoveableUnit unit) {

    }

    @Override
    public String getCombatEffect(MoveableUnit unit) {

    }
    */
}
