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
public class DemonicInfantry extends Conjured{

    public DemonicInfantry(Characters c, int lc) {
        super(c, lc);
        strength = 8;
        movement = 3;
        demoralizedStrength = 4;
        this.ResetWorkingMovement();
    }
        
}
