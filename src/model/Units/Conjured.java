/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Units;

import Character.Character;

/**
 *
 * @author Matt
 * More will go here but this is good for now
 */
public class Conjured extends ArmyUnit{
    
    public Conjured(Character c, double lc) {
        super();
        this.conjuror = c;
        this.lifeCost = lc;
        conjured = true;
    }   
  
}
