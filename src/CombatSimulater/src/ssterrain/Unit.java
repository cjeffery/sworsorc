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
public class Unit {  
    protected Race race;
    protected UnitType unitType;
    protected int strength;
    protected int movement;
    protected int demoralizedStrength;
    protected boolean ranged;
    protected boolean demorlized;
      
    public void setDemorlized(boolean demorlized) {
        this.demorlized = demorlized;
    }
    
    public Unit() {
        this.demorlized = true;
    }
    
    public void setRace(Race newRace){
        this.race = newRace;
    }
    
    public void setUnitType(UnitType newType){
        this.unitType = newType;
    }
     
    public Race getRace(){
        return race;
    }
    
    public UnitType getUnitType(){
        return unitType;
    }

    public int getStrength() {
        return strength;
    }

    public int getMovement() {
        return movement;
    }

    public int getDemoralizedStrength() {
        return demoralizedStrength;
    }

    public boolean isRanged() {
        return ranged;
    }

    public boolean isDemorlized() {
        return demorlized;
    }
    
    public void SetDemorlized(boolean x){
        demorlized = x;
    }
}
