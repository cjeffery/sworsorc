/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Units;

/**
 * Moveable Units:
 * This class contains information about the movement allocation, race, location,
 * and the most basic type of a moveable unit(army unit, character, or monster).
 * <p>
 * 
 * The functions in this class work by taking the movement data out of any of the
 * types that extends this class. 
 *
 * @author matt
 */
public class MoveableUnit {
    protected UnitType UnitType;
    protected double movement;
    protected Race race;
    protected String location;
    protected String ID;
    
    public MoveableUnit(){
        
    }
    
    public MoveableUnit(String location){
        this.location = location;
    }
    
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }   
    
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
    
    public double getMovement() {
        return movement;
    }

    public UnitType getUnitType() {
        return UnitType;
    }

    public void setUnitType(UnitType UnitType) {
        this.UnitType = UnitType;
    }
    
    @Override
    public String toString(){
        return getClass().getSimpleName();
    }
}
