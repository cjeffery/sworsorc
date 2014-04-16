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
public class WraithTroops extends Conjured{

    public WraithTroops(Character c, int lc) {
        super(c, lc);
        strength = 6;
        movement = 3;
        demoralizedStrength = 3;
    }    
}
