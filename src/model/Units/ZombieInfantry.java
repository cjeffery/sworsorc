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
public class ZombieInfantry extends Conjured{

    public ZombieInfantry(Character c, int LifeCost) {
        super(c, LifeCost);
        strength = 2;
        movement = 3;
        demoralizedStrength = 1;
    }
    
    
}
