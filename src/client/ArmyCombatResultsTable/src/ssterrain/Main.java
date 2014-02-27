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
public class Main {

    /**
     * @param args the command line arguments
     */
    
    //John Wrote this im using it as an example
    
    public static void main(String[] args) {
        Hex hex1 = new Hex();
        TerrainType tt1 = new TTWoods();
        hex1.setTerrainType(tt1);
        ImprovedTerrainType itt1 = new ITTCastle(hex1);
        hex1.addImprovement(itt1);
        Unit unit1 = new Unit();
        unit1.setRace(Race.Human);
        System.out.println(unit1.getRace().toString() + " moved into a hex that contains: ");
        if(hex1.getImprovements().size() > 0)
            for (int i = 0; i < hex1.getImprovements().size(); i++)
                System.out.println("\t" + hex1.getImprovements().get(i).toString());
        else System.out.println("Nothing");
        System.out.println("in the " + hex1.getTerrainType().toString());
        System.out.println("with a movement cost of: " + hex1.getMovementCost(unit1));
        System.out.println("and a combat bonus of: *" + hex1.getCombatMultiplier(unit1));
        
        System.out.println("\n\n");
        Hex hex2 = new Hex();
        TerrainType tt2 = new TTMountains();
        hex2.setTerrainType(tt2);
        ImprovedTerrainType itt2 = new ITTTrail(hex2);
        hex2.addImprovement(itt2);
        ImprovedTerrainType itt3 = new ITTCity(hex2);
        hex2.addImprovement(itt3);
        
        Unit unit2 = new Unit();
        unit2.setRace(Race.Orc);
        unit2.setUnitType(UnitType.Character);
        System.out.println(unit2.getRace().toString() + " moved into a hex that contains: ");
        if(hex2.getImprovements().size() > 0)
            for (int i = 0; i < hex2.getImprovements().size(); i++)
                System.out.println("\t" + hex2.getImprovements().get(i).toString());
        else System.out.println("Nothing");
        System.out.println("in the " + hex2.getTerrainType().toString());
        System.out.println("with a movement cost of: " + hex2.getMovementCost(unit2));
        System.out.println("and a combat bonus of: *" + hex2.getCombatMultiplier(unit2));
    }
}
