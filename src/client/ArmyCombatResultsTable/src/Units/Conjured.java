/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Units;

/**
 *
 * @author Matt
 * More will go here but this is good for now
 */
public class Conjured extends Unit{
    protected int lifeCost;

    public Conjured() {
        super();
    }   

    public void setLifeCost(int lifeCost) {
        this.lifeCost = lifeCost;
    }

    public int getLifeCost() {
        return lifeCost;
    }
    
}
