/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ssterrain;

import java.util.ArrayList;

/**
 *
 * @author John
 */
import Units.*;
public class ITTRoad implements ImprovedTerrainType{
    Hex hex;
    
    public ITTRoad(Hex thisHex){
        this.hex = thisHex;
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
    public String toString(){
        return "Road";
    }

    @Override
    public double getMovementOverride(MoveableUnit unit) {
        ArrayList<HexEdgeType> list = new ArrayList<HexEdgeType>();
        list.add(HexEdgeType.Bridge);
        list.add(HexEdgeType.Road);
        list.add(HexEdgeType.Ford);
        list.add(HexEdgeType.Trail);
        if(hex.checkIfCrossed(list)) return 0.5;
        else return 0;
    }
    
}
