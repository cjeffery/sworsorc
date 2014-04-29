/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Units;



/**
 *
 * @author Matt
 */
public class LightBow extends RangedLandUnit{

    public LightBow() {
        super();
        strength = 3;
        movement = 5;
        demoralizedStrength = 1;
        this.ResetWorkingMovement();
    }
       
}
