/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Units;


/**
 *
 * 
 * CS 383 Software Engineering
 * <p>
 * Army Unit extends the MoveableUnit class. This class addresses the combat
 * side of all basic units(ie not monsters and characters). This contains the 
 * the strength, demoralized strength, and other variables that are associated
 * and needed for army units.
 * <p>
 * The functions in this class are mostly getters and setters for the various 
 * member variables in the class.
 * @author John Goettsche
 */
public class ArmyUnit extends MoveableUnit {   
    protected int strength;    
    protected int demoralizedStrength;
    protected int lifeCost;    
    
    protected boolean conjured;
    protected boolean demoralized;
    
    protected ArmyUnitType armyUnitType;

    public boolean isConjured() {
        return conjured;
    }
    
    
    public void setDemoralized(boolean demoralized) {
        this.demoralized = demoralized;
    }
    
    public ArmyUnitType getArmyUnitType(){
        return armyUnitType;
    }
    public ArmyUnit() {
        super();
        this.demoralized = false;
        this.conjured = false;
        this.UnitType = UnitType.ArmyUnit;
    }
    
    public ArmyUnit(String location) {
        this.demoralized = false;
        this.conjured = false;
        this.setLocation(location);
    }  


    public int getStrength() {
        return strength;
    }    

    public int getDemoralizedStrength() {
        return demoralizedStrength;
    }

    public boolean isDemoralized() {
        return demoralized;
    }
    
    public void SetDemoralized(boolean x){
        demoralized = x;
    }
    
    public void setLifeCost(int lifeCost) {
        this.lifeCost = lifeCost;
    }

    public int getLifeCost() {
        return lifeCost;
    }
    
    public void printSelf(){
        System.out.println(armyUnitType + "" + race +" " + strength + " " + movement);
        if(this.conjured){
            System.out.println("Life Cost: " + lifeCost);
        }
    }
    
}
