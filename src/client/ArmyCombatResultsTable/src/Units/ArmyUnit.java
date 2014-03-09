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
public class ArmyUnit extends MoveableUnit{   
    protected int strength;    
    protected int demoralizedStrength;
    protected boolean ranged;
    protected boolean demorlized;
    protected String ID;
    

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }   
    
    public void setDemorlized(boolean demorlized) {
        this.demorlized = demorlized;
    }
    
    public ArmyUnit() {
        this.demorlized = true;
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
}
