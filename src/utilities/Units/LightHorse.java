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
public class LightHorse extends Cavalry{

    public LightHorse() {
        super();
        strength = 2;
        movement = 9;
        demoralizedStrength = 1;
    }
    
    @Override
    public void setRace(Race newRace){
        this.race = newRace;
        switch(race){
            case Elves:
            case Dwarrows:
                strength = 2;
                movement = 9;
                demoralizedStrength = 1;
                break;
            case Cronk:
                strength = 1;
                movement = 9;
                demoralizedStrength = 1;
                break;
            case Human:
                strength = 2;
                movement = 7;
                demoralizedStrength = 1;
                break;
        }
    }
    
}
