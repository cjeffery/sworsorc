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
     /**
      * Set the race of a MoveableUnit from a string. If the string 
      * is not part of the {@link Race} enumerated list, it is set to 
      * null.
      * 
      * @author Tyler Jaszkowiak
      * @param raceStr the string identifying the unit's new race
      */
     public void setRace(String raceStr){
        Race newRace;
        switch (raceStr) {
            case "Cronk":
                newRace = Race.Cronk;
                break;
            case "Dragon":
                newRace = Race.Dragon;
                break;
            case "Dwarrows":
                newRace = Race.Dwarrows;
                break;
            case "Elves":
                newRace = Race.Elves;
                break;
            case "Human":
                newRace = Race.Human;
                break;
            case "KillerPenguin":
                newRace = Race.KillerPenguin;
                break;
            case "Orc":
                newRace = Race.Orc;
                break;
            case "Spiders":
                newRace = Race.Spiders;
                break;
            case "SwampCreature":
                newRace = Race.SwampCreature;
                break;
            default:
                newRace = null;
        }
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
    
    public void setUnitType(String unitTypeStr){
        UnitType newUnitType;
        switch (unitTypeStr) {
            case "ArmyUnit":
                newUnitType = UnitType.ArmyUnit;
                break;
            case "Character":
                newUnitType = UnitType.Character;
                break;
            case "Monster":
                newUnitType = UnitType.Monster;
                break;
            default:
                newUnitType = null;
        }
        this.UnitType = newUnitType;
    }
    
    @Override
    public String toString(){
        return getClass().getSimpleName();
    }
}
