/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Units;

/**
 *
 * @author matt
 */

public class FlyingConjured extends ArmyUnit{
     protected int lifeCost;

    public FlyingConjured() {
    }

    public int getLifeCost() {
        return lifeCost;
    }

    public void setLifeCost(int lifeCost) {
        this.lifeCost = lifeCost;
    }    
}
