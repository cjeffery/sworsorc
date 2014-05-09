package sscharts;

import static ObjectCreator.ObjectCreator.CreateObject;
import static Character.CharacterMaker.createCharacter;
import Units.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import sshexmap.Provinces;
import Character.Characters;


/**
 * This singleton class contains the data pertaining to a particular scenario, 
 * which it reads from a scenario configuration JSON. 
 * The class consists of many getters 
 * and a function to populate a UnitPool from the class's unit hash.
 * 
 * @author Wayne Fuhrman
 * @author Tyler Jaszkowiak
 */

public class Scenario {
    
    private static Scenario instance;

    //General game information:
     static String scenarioName;
     static int numberOfPlayers;
     static int gameLength; //Number of game turns
     static int blueSunStart;
    
     static List<String> armyNames;
     static List<String> neutralNames;
     static Map<String, String> neutralRaces; // neutral -> race

    //Information storage for each army:
    //The player army or neutral name is the key for these hashmaps
     static Map<String, Integer> controllingPlayers;   //army -> controllingPlayer
     static Map<String, Integer> setupOrders;          //army -> order
     static Map<String, Integer> moveOrders;           //army -> order
     
     // nation information
     static Map<String, List<String>> nations;         //army -> names of nations
     static Map<String, String> races;           //nation -> race
     static Map<String, List<String>> provinces;       //nation -> list of province names
     static Map<String, List<String>> characters;      //nation -> list of character names
     static Map<String, Map<String, Integer>> units;   //nation -> (unitName -> unitCount)
     static Map<String, String> replacements;          //nation -> description of replacement
     static Map<String, String> reinforcements;        //nation -> description of reinforcements

    //Diplomacy stuff:
     static Map<String, String> leaningTowards;
     static Map<String, Integer> leaningAmount;
     static Map<String, Boolean> acceptsSacrifice;
    
    /**
     * This method, which is incomplete, is intended to populate the singleton
     * {@link UnitPool} for use in the main Game class. 
     * 
     * @author Tyler Jaszkowiak
     * @see UnitPool
     */
    public static void populatePool() {
        UnitPool pool = UnitPool.getInstance();
        String unitType;
        String objectType;
        String randHexID;
        int unitQuant;
        ArmyUnit unit;
        int player = 0;
        
        for (String army : getArmyNames() ) {
            // one player controls all nations in an army - this is fine here
            player = getControllingPlayer(army);
            // go through each nation in army and get info about units
            for (String nation : getNations(army) ) {
                List<String> nationProvinces = getProvinces(nation);
                String nationRace = getRace(nation);
                // iterate through the map of this nation's units
                Map<String, Integer> playerUnits = getUnits(nation);
                Iterator unitIt = playerUnits.entrySet().iterator();
                while (unitIt.hasNext()) {
                    Map.Entry pairs = (Map.Entry)unitIt.next();
                    unitType = (String) pairs.getKey();
                    objectType = "Units." + unitType;
                    unitQuant = (int) pairs.getValue();
                    for (int i=0; i<unitQuant; i++) {
                        unit = (ArmyUnit) CreateObject(objectType);
                        unit.setNation(nation);
                        unit.setRace(nationRace);
                        // TODO: look for conflicts and stacks of too many
                        // TODO: avoid water, vortices, bottomless plungehole
                        randHexID = Provinces.getRandSafeHex(nationProvinces);
                        System.out.println("Adding to unit pool: " + player + ": " + unit.getUnitType() + ", " + randHexID);
                        pool.addUnit(player, unit, randHexID);
                    }
                    unitIt.remove();
                }
                // iterate through the map of this nation's characters
                Characters thisChar;
                List<String> playerChars = getCharacters(nation);
                Iterator charIt = playerChars.iterator();
                while (charIt.hasNext()) {
                    String charname = (String) charIt.next();
                    thisChar = createCharacter(charname);
                    System.out.println("Adding to unit pool: " + player + ": " + charname + ", " + thisChar.getHomeHex());
                    pool.addUnit(player, thisChar, thisChar.getHomeHex());
                    charIt.remove();
                }
            }
        }
        
        // populate the neutral units
        for (String neutral : neutralNames) {
            player++; // TODO: for now neutrals look like additional players to
                      // the unit pool. see how david wants this implemented
            List<String> neutralProvinces = getProvinces(neutral);
            String neutralRace = getNeutralRace(neutral);
            // iterate through the map of this neutral's units
            Map<String, Integer> playerUnits = getUnits(neutral);
            Iterator unitIt = playerUnits.entrySet().iterator();
            while (unitIt.hasNext()) {
                Map.Entry pairs = (Map.Entry)unitIt.next();
                unitType = (String) pairs.getKey();
                objectType = "Units." + unitType;
                unitQuant = (int) pairs.getValue();
                for (int i=0; i<unitQuant; i++) {
                    unit = (ArmyUnit) CreateObject(objectType);
                    unit.setNation(neutral);
                    unit.setRace(neutralRace);
                    // TODO: look for conflicts and stacks of too many
                    // TODO: avoid water, vortices, bottomless plungehole
                    randHexID = Provinces.getRandSafeHex(neutralProvinces);
                    System.out.println("Adding to unit pool: " + player + ": " + unit.getUnitType() + ", " + randHexID);
                    pool.addUnit(player, unit, randHexID);
                }
                unitIt.remove();
            }
        }
    }
    
    /**
     * This initializer for {@link Scenario} is intended to 
     * read the configuration given by the specified file and use it to 
     * populate all fields of the class to reflect that data.
     * 
     * @author Wayne Fuhrman
     * @param configurationFileName the relative path of the config file
     */
    /* FIXME: THESE EXCEPTIONS ARE APPARENTLY 'UNKNOWN THROWABLES'
     * @exception FileNotFoundException if the scenario config file was not found
     * @exception IOException if the scenario config file could not be read from
     */
    public static void Initialize(String configurationFileName) {
        
        //Attempts to read and store information from the given file.

        JSONParser parser = new JSONParser();

        controllingPlayers = new HashMap<>();
        armyNames = new ArrayList<>();
        setupOrders = new HashMap<>();
        moveOrders = new HashMap<>();
        nations = new HashMap<>();
        races = new HashMap<>();
        provinces = new HashMap<>();    //Array of province names
        characters = new HashMap<>();   //Array of character names
        units = new HashMap<>();        //The return map is unitName:unitCount
        leaningTowards = new HashMap<>();
        leaningAmount = new HashMap<>();
        acceptsSacrifice = new HashMap<>();
        replacements = new HashMap<>();
        reinforcements = new HashMap<>();

        //neutralNames has keys for the maps:
        neutralNames = new ArrayList<>();
        neutralRaces = new HashMap<>();

        try {
            //The entire file:
            Object obj = parser.parse(new FileReader(configurationFileName));
            JSONObject jsonObject = (JSONObject) obj;

            //Top-level game information:
            scenarioName = (String) jsonObject.get("scenarioName");
            numberOfPlayers = ((Long) jsonObject.get("numberOfPlayers")).intValue();
            gameLength = ((Long) jsonObject.get("gameLength")).intValue();
            blueSunStart = ((Long) jsonObject.get("blueSunStart")).intValue();

            //We'll iterate and grab info for each human controlled army:
            // TODO: These are declared each iteration - seems exhausting
            JSONArray JSONArmyArray = (JSONArray) jsonObject.get("armies");
            for (Object baseArmyObject : JSONArmyArray) {
                JSONObject armyObject = (JSONObject) baseArmyObject;

                String armyName = (String) armyObject.get("name");
                armyNames.add(armyName);
                
                int controllingPlayer = ((Long) armyObject.get("player")).intValue();
                controllingPlayers.put(armyName, controllingPlayer);

                int setupOrder = ((Long) armyObject.get("setupOrder")).intValue();
                setupOrders.put(armyName, setupOrder);

                int moveOrder = ((Long) armyObject.get("moveOrder")).intValue();
                moveOrders.put(armyName, moveOrder);
                
                // now get the information for each nation in this army
                JSONArray JSONNationArray = (JSONArray) armyObject.get("nations");
                
                // as we traverse through nations, keep a list of names
                List<String> nationNames = new ArrayList<>();
                for (Object baseNationObject : JSONNationArray) {
                    JSONObject nationObject = (JSONObject) baseNationObject;
                    String nationName = (String) nationObject.get("name");
                    nationNames.add(nationName);
                    
                    String nationRace = (String) nationObject.get("race");
                    races.put(nationName,nationRace);
                    
                    List<String> nationProvinces = new ArrayList<>();
                    for (Object provinceObject : (JSONArray) nationObject.get("provinces")) {
                        String province = (String) provinceObject;
                        nationProvinces.add(province);
                    }
                    provinces.put(nationName, nationProvinces);

                    List<String> nationCharacters = new ArrayList<>();
                    for (Object characterObject : (JSONArray) nationObject.get("characters")) {
                        String character = (String) characterObject;

                        nationCharacters.add(character);
                    }
                    characters.put(nationName, nationCharacters);

                    JSONObject nationUnits = (JSONObject) nationObject.get("units");

                    Map<String, Integer> unitAndCount = new HashMap<>();
                    int unitCount = 0;
                    String unitName = "";
                    for (Object entry : nationUnits.entrySet()) {
                        Map.Entry en = (Map.Entry) entry;
                        unitName = (String) en.getKey();
                        unitCount = ((Long) en.getValue()).intValue();
                        unitAndCount.put(unitName, unitCount);
                    }
                    units.put(nationName, unitAndCount);
                    
                }
                // add the list of this army's nations to the nation hashmap
                nations.put(armyName, nationNames);
                // get the army's reinforcements and replacements
                String replacementDescription = (String) armyObject.get("replacements");
                replacements.put(armyName, replacementDescription);

                String reinforcementDescription = (String) armyObject.get("reinforcements");
                reinforcements.put(armyName, reinforcementDescription);

            }

            //Iterate and grab information for neutral armies:
            // TODO: declare variables outside of loops
            JSONArray neutralJSONArray = (JSONArray) jsonObject.get("neutrals");
            for (Object neutralBaseObject : neutralJSONArray) {
                JSONObject neutralObject = (JSONObject) neutralBaseObject;

                String neutralName = (String) neutralObject.get("name");
                neutralNames.add(neutralName);
                
                String neutralRace = (String) neutralObject.get("race");
                neutralRaces.put(neutralName,neutralRace);

                List<String> neutralProvinces = new ArrayList<>();
                for (Object provinceObject : (JSONArray) neutralObject.get("provinces")) {
                    String province = (String) provinceObject;
                    neutralProvinces.add(province);
                }
                provinces.put(neutralName, neutralProvinces);

                List<String> neutralCharacters = new ArrayList<>();
                for (Object characterObject : (JSONArray) neutralObject.get("characters")) {
                    String character = (String) characterObject;
                    neutralCharacters.add(character);
                }
                characters.put(neutralName, neutralCharacters);

                //Since we don't want to hard-code unit names, we have to iterate
                //over the hash map entries to get unit names:
                JSONObject armyUnitObject = (JSONObject) neutralObject.get("units");
                Map<String, Integer> nmap = new HashMap<>();
                for (Object entry : armyUnitObject.entrySet()) {
                    Map.Entry en = (Map.Entry) entry;
                    String unitName = (String) en.getKey();
                    int unitCount = ((Long) en.getValue()).intValue();
                    nmap.put(unitName, unitCount);
                }
                units.put(neutralName, nmap);

                JSONObject diplomacy = (JSONObject) neutralObject.get("diplomacy");
                String province = (String) diplomacy.get("leaningTowards");

                if (province != null) {
                    leaningTowards.put(neutralName, province);
                    int hexesTowards = ((Long) diplomacy.get("amount")).intValue();
                    leaningAmount.put(neutralName, hexesTowards);
                }
                // read whether the neutral accepts human sacrifices
                if (diplomacy.containsKey("humanSacrifice")) {
                    if (((String) diplomacy.get("humanSacrifice")).equals("true")) {
                        acceptsSacrifice.put(neutralName, true);
                    } else {
                        acceptsSacrifice.put(neutralName, false);
                    }
                } else {
                    acceptsSacrifice.put(neutralName, false);
                }
                
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Scenario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Get the singleton instance of the Scenario
     * 
     * @author Tyler Jaszkowiak
     * @return the scenario
     */
    public static Scenario getInstance() {
        return instance;
    }
    
    
    /**
      * A getter for the scenarioName field.
      * 
      * @author Wayne Fuhrman
      * @return the scenario's name as a String
      */
    public static String getScenarioName() {
        return scenarioName;
    }

    /**
      * A getter for the number of players in the scenario.
      * 
      * @author Wayne Fuhrman
      * @return the number of players in the scenario
      */
    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
      * A getter for the game length of the scenario.
      * 
      * @author Wayne Fuhrman
      * @return the game length of the scenario
      */
    public static int getGameLength() {
        return gameLength;
    }
    
    /**
     * A getter for the blue sun's starting position used by the solar display
     * 
     * @author Tyler Jaszkowiak
     * @return the blue sun's starting position
     */
    public static int getBlueSunStart() {
        return blueSunStart;
    }
    
    
    /** return the list of army names.
     * 
     * @author Tyler Jaszkowiak
     * @return a list of armies in the scenario
     */
    public static List<String> getArmyNames() {
        return armyNames;
    }
    
    /**
      * A getter for the controlling player of a given army.
      * 
      * @author Tyler Jaszkowiak
      * @param name the name of the army in question
      * @return the number of the player controlling the army
      */
    public static int getControllingPlayer(String name) {
        return controllingPlayers.get(name);
    }

    /** 
     * Gets the names of the neutrals in the scenario and returns them as a list.
     * 
     * @author Wayne Fuhrman
     * @return a list of neutrals in the scenario
     */
    public static List<String> getNeutralNames() {
        return neutralNames;
    }

    /** Gets the setup order of a specific army in the scenario
     * 
     * @param name name of the army being queried about
     * @return the setup order of the army in question
     */
    public static Integer getSetupOrder(String name) {
        return setupOrders.get(name);
    }

    /**
     * Gets the move order of a specific army in the scenario.
     * 
     * @param name name of the army being queried about
     * @return the setup order of the army in question
     */
    public static Integer getMoveOrder(String name) {
        return moveOrders.get(name);
    }
    
    /** 
     * Gets the nations in a given army 
     * 
     * @param armyName name of the army being queried about
     * @return a list of nations in the army
     */
     public static List<String> getNations(String armyName) {
         return nations.get(armyName);
     }

    /**
     * Get the provinces a nation is allowed to set up in
     * 
     * @param name name of the army in question
     * @return a list of the names of these provinces TODO: find a Province object?
     */
    public static List<String> getProvinces(String name) {
        return provinces.get(name);
    }

    /**
     * Return a list of names of the characters in a nation.
     * <p>
     * TODO: change this to return Characters instead of strings?
     * 
     * @param name the name of the nation in question
     * @return a list of names of the characters in the nation
     */
    public static List<String> getCharacters(String name) {
        return characters.get(name);
    }

    /**
     * Return the race of a nation.
     * 
     * @param name the name of the nation in question
     * @return the race of nation
     */
    public static String getRace(String name) {
        return races.get(name);
    }

    /**
     * Return the race of a neutral.
     * 
     * @param neutral the name of the nation in question
     * @return the race of nation
     */
    public static String getNeutralRace(String neutral) {
        return neutralRaces.get(neutral);
    }

    /**
     * Return a list of names of the units in a nation.
     * <p>
     * TODO: change this to return ArmyUnits instead of strings?
     *       also, this logic seems very wrong.
     * 
     * @param name the name of the nation in question
     * @return a list of names of the units in the nation
     */
    public static Map<String, Integer> getUnits(String name) {
        return units.get(name);
    }

    public static String  getReplacement(String armyName) {
        return replacements.get(armyName);
    }
    
    public static String getReinforcement(String armyName) {
        return reinforcements.get(armyName);
    }

    public static String getLeaningToward(String neutralName) {
        return leaningTowards.get(neutralName);
    }

    public static Integer getLeaningAmount(String neutralName) {
        return leaningAmount.get(neutralName);
    }

    public static Boolean acceptsSacrifice(String neutralName) {
        return acceptsSacrifice.get(neutralName);
    }

    public void initScenario(String chosenScenario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
