/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package Units;

import java.io.Serializable;

/**
 *
 * @author matt
 */
public class MoveableUnit implements Serializable {

    private static final long serialVersionUID = 1L;
    protected UnitType UnitType;
    protected int movement;
    protected Race race;
    protected String location;
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
     public void setRace(Race newRace){
        this.race = newRace;
    }
     
    public Race getRace(){
        return race;
    }
    
    public int getMovement() {
        return movement;
    }

    public UnitType getUnitType() {
        return UnitType;
    }

    public void setUnitType(UnitType UnitType) {
        this.UnitType = UnitType;
    }
    
    
}
