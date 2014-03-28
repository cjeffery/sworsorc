/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Units;

import ssterrain.*;

/**
 *
 * @author John Goettsche
 * CS 383 Software Engineering
 */

// NOTE: I didn't want to make this change because I wasn't sure where else the 
// interface was used - but it should be "demoralized" rather than "demorlized"
public class Unit {    
    protected Race race;
    protected UnitType unitType;
    protected int strength;
    protected int movement;
    protected int demoralizedStrength;
    protected boolean ranged;
    protected boolean demorlized;
    protected String ID;
    protected int location;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
    
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
    
    // I think it would be an easier interface for the rest of the developers
    // if we had functions Demoralize() and Rally() so they don't have to 
    // know about this boolean at all. Just a thought - Tyler Jaszkowiak
    //
    // public void Demoralize() {
    //     demorlized = true;
    // }
    //
    // public void Rally() {
    //     demorlized = false;
    // }   
    public void SetDemorlized(boolean x){
        demorlized = x;
    }
}
