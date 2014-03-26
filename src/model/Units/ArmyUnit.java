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
 */
public class ArmyUnit extends MoveableUnit{   
    protected int strength;    
    protected int demoralizedStrength;
    protected int lifeCost;    
    
    protected boolean conjured;
    protected boolean ranged;
    protected boolean demorlized;
    protected boolean flying;
    protected boolean cavalry;
    
    
    public void setDemorlized(boolean demorlized) {
        this.demorlized = demorlized;
    }
    
    public ArmyUnit() {
        this.demorlized = false;
        this.flying = false;
        this.cavalry = false;
        this.ranged = false;
        this.conjured = false;
    }  

    public int getStrength() {
        return strength;
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
    
    public void setLifeCost(int lifeCost) {
        this.lifeCost = lifeCost;
    }

    public int getLifeCost() {
        return lifeCost;
    }
    
}
