/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Units;
import Character.Characters;

/**
 *
 * @author Matt
 */
public class KoboldicInfantry extends Conjured{

    public KoboldicInfantry(Characters c, int lc) {
        super(c, lc);
        strength = 4;
        movement = 4;
        demoralizedStrength = 2;
        this.ResetWorkingMovement();
    }
    
    
}
