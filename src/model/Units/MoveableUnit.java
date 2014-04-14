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
    protected double workingMovement;
    protected Race race;
    protected String location;
    protected String ID;
    
    public MoveableUnit(){
        
    }
    
    public void ResetWorkingMovement(){
        workingMovement = movement;
    }

    public double getWorkingMovement() {
        return workingMovement;
    }

    public void setWorkingMovement(double workingMovement) {
        this.workingMovement = workingMovement;
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

    /**
     * @return UnitType enum: Character, ArmyUnit, Monster, etc
     */
    public UnitType getUnitType() {
        return UnitType;
    }

    /**
     * Sets the UnitTYpe enum: Character, ArmyUnit, Monster, etc
     */
    public void setUnitType(UnitType UnitType) {
        this.UnitType = UnitType;
    }
    
    @Override
    public String toString(){
        return getClass().getSimpleName();
    }
    
    public String getRaceCode() {
        if( getRace() == null ) {
            System.out.println("Unit has no race? D:");
            return "~";
        }
        
        if(this instanceof FlyingUnit)
            return "w";
        switch( getRace() ) {
            case Cronk:         return "c";
            case Dragon:        return "w";
            case Dwarrows:      return "d";
            case Elves:         return "e";
            case Human:         return "m";
            case KillerPenguin: return "o"; //rules shows penguins as orcs
            case Orc:           return "o";
            case Spiders:       return "a";
            case SwampCreature: return "s";
            case Goblins: return "g";
            default:
                System.out.println("Unit has unknown race: " + getRace());  
                return "?";
        }
    }
}
