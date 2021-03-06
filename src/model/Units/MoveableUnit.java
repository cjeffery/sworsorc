/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package Units;

import java.io.Serializable;

/**
 * Movable Units:
 * This class contains information about the movement allocation, race, location,
 * and the most basic type of a movable unit(army unit, character, or monster).
 * <p>
 * 
 * The functions in this class work by taking the movement data out of any of the
 * types that extends this class. 
 *
 * @author matt
 */
public class MoveableUnit implements Serializable {
    private static final long serialVersionUID = 1L;
    protected UnitType UnitType;
    protected double movement;
    protected double workingMovement;
    protected Race race;
    protected Nation nation;
    public String location;
    protected String ID;
    
    public MoveableUnit(){
        race = Race.NOT_SET;
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
     * Matt: Not trying to step on your toes. Talk to me about this if it's 
     * not right. I just needed to be able to access it from Scenario.
     * 
     * @author Tyler Jaszkowiak
     */
    public void setNation(Nation newNation) {
        this.nation = newNation;
    }
    
    public void setNation(String nationStr) {
        switch (nationStr) {
            case "ImperialArmy":
                setNation(Nation.ImperialArmy);
                break;
            case "Elven":
                setNation(Nation.Elven);
                break;
            case "IndependentHuman":
                setNation(Nation.IndependentHuman);
                break;
            case "ORC":
                setNation(Nation.ORC);
                break;
            case "Goblin":
                setNation(Nation.Goblin);
                break;
            case "SwampCreature":
                setNation(Nation.SwampCreature);
                break;
            case "Cronk":
                setNation(Nation.Cronk);
                break;
            case "Conjured":
                setNation(Nation.Conjured);
                break;
            case "Krasnian":
                setNation(Nation.Krasnian);
                break;
            case "Zirkastian":
                setNation(Nation.Zirkastian);
                break;
            case "Mercenary":
                setNation(Nation.Mercenary);
                break;
            case "WhiteORC":
                setNation(Nation.WhiteORC);
                break;
            case "CorfluCultist":
                setNation(Nation.CorfluCultist);
                break;
            case "SpiderFolk":
                setNation(Nation.SpiderFolk);
                break;
            case "Convivian":
                setNation(Nation.Convivian);
                break;
            case "none":
                setNation(Nation.none);
                break;
            default:
                System.err.println("Incorrect Nation sent to setNation(String)");
                System.err.println("Recieved: " + nationStr);
                setNation((Nation)null);
        }
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
        switch (raceStr) {
            case "Cronk":
                setRace(Race.Cronk);
                break;
            case "Dragon":
                setRace(Race.Dragon);
                break;
            case "Dwarrows":
                setRace(Race.Dwarrows);
                break;
            case "Elves":
                setRace(Race.Elves);
                break;
            case "Human":
                setRace(Race.Human);
                break;
            case "KillerPenguin":
                setRace(Race.KillerPenguin);
                break;
            case "Orc":
                setRace(Race.Orc);
                break;
            case "Spiders":
                setRace(Race.Spiders);
                break;
            case "SwampCreature":
                setRace(Race.SwampCreature);
                break;
            case "Goblins":
                setRace(Race.Goblins);
                break;
            default:
                System.err.println("Incorrect Race sent to setRace(String)");
                System.err.println("Recieved: " + raceStr);
                setRace((Race)null);
        }
    }
     
    public Race getRace(){
        return race;
    }

    public Nation getNation(){
        return nation;
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
                System.out.println("Unit has unknown race: " + getRace().toString());  
                return "?";
        }
    }
}
