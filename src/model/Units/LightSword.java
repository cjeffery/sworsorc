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
public class LightSword extends LandUnit{

    public LightSword() {
        super();
        strength = 3;
        movement = 5; // temp change for testing = reset to 5 'minder
        demoralizedStrength = 1;
        this.ResetWorkingMovement();
    }
    
    @Override
    public void setRace(Race r){
        this.race = r;
        switch(race){
            case Elves:
                movement = 5;
            case Orc:
                movement = 4;
        }
    }
    
    
}
