/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Units;


/**
 *
 * @author John Goettsche
 * CS 383 Software Engineering
 * tjasz: changed 'demorlized' to 'demoralized' before the problem spread
 */
public class ArmyUnit extends MoveableUnit {   
    protected int strength;    
    protected int demoralizedStrength;
    protected int lifeCost;    
    
    protected boolean conjured;
    protected boolean demoralized;
    
    protected ArmyUnitType armyUnitType;
    
    
    public void setDemoralized(boolean demoralized) {
        this.demoralized = demoralized;
    }
    
    public ArmyUnitType getArmyUnitType(){
        return armyUnitType;
    }
    public ArmyUnit() {
        this.demoralized = false;
        this.conjured = false;
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
