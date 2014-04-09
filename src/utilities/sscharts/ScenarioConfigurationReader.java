package sscharts;

import static ObjectCreator.ObjectCreator.CreateObject;
import Units.*;
import java.io.FileNotFoundException;
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


/**
 * This class reads a scenario configuration file from a configuration JSON 
 * file. The class consists of many getters, an ASCII stdout print function, 
 * and a function to populate a UnitPool from the class's unit hash.
 * <p>
 * Each method gives its own author. In some cases, I left Wayne Fuhrman as 
 * the main author even though I made changes to a few lines. The ground work 
 * was still laid by him.
 * <p>
 * TODO: Input error handling and null pointer checking, etc.
 * 
 * @author Wayne Fuhrman
 * @author Tyler Jaszkowiak
 */

public class ScenarioConfigurationReader {
    
     List<String> nationNames;
     List<String> neutralNames;

    //Information storage for each nation:
     
    //The player nation or neutral name is the key for these hashmaps
     Map<String, Integer> controllingPlayers;   //nation -> controllingPlayer
     Map<String, Integer> setupOrders;          //nation -> order
     Map<String, Integer> moveOrders;           //nation -> order
     Map<String, List<String>> provinces;       //nation -> list of province names
     Map<String, List<String>> characters;      //nation -> list of character names
     Map<String, Map<String, Integer>> units;   //nation -> (unitName -> unitCount)
     Map<String, String> replacements;          //nation -> description of replacement
     Map<String, String> reinforcements;        //nation -> description of reinforcements

    //Diplomacy stuff:
     Map<String, String> leaningTowards;
     Map<String, Integer> leaningAmount;
     Map<String, Boolean> acceptsSacrifice;

    //General game information:
     String scenarioName;
     int numberOfPlayers;
     int gameLength; //Number of game turns
     

    /**
     * This method prints to standard out an ASCII representation of the data
     * contained in the {@link ScenarioConfigurationReader} class.
     * 
     * @author Wayne Fuhrman
     */
    public void print() {
        for (String nation : getNationNames()) {
            System.out.println();
            System.out.println("Nation: " + nation);
            System.out.println("Controlled by: Player " + getControllingPlayer(nation));
            System.out.println("Sets up: " + getSetupOrder(nation));
            System.out.println("Moves: " + getMoveOrder(nation));
            System.out.println("Controls Provinces: " + getProvinces(nation));
            System.out.println("Has characters: " + getCharacters(nation));
            System.out.println("Has units: " + getUnits(nation));
            System.out.println("Replacements: " + getReplacement(nation));
            System.out.println("Reinforcements: " + getReinforcement(nation));
        }
        for (String neutral : neutralNames) {
            System.out.println();
            System.out.println("Neutral: " + neutral);
            System.out.println("Controls Provinces: " + getProvinces(neutral));
            System.out.println("Has characters: " + getCharacters(neutral));
            System.out.println("Has units: " + getUnits(neutral));
            System.out.println("Leaning towards: " + getLeaningToward(neutral));
            System.out.println("Leaning amount: " + getLeaningAmount(neutral));
            System.out.println("Human sacrifice: " + acceptsSacrifice(neutral));
        }
    }
    
    /**
     * This method, which is incomplete, is intended to populate the singleton
     * {@link UnitPool} for use in the main Game class. 
     * 
     * @author Tyler Jaszkowiak
     * @see UnitPool
     */
    public void populatePool() {
        UnitPool pool = UnitPool.getInstance();
        for (String nation : getNationNames() ) {
            int player = getControllingPlayer(nation);
            String unitType;
            String objectType;
            int unitQuant;
            ArmyUnit unit;
            // iterate through the map of this player's units
            Map<String, Integer> playerUnits = getUnits(nation);
            Iterator it = playerUnits.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();
                unitType = (String) pairs.getKey();
                objectType = "Units." + unitType;
                unitQuant = (int) pairs.getValue();
                for (int i=0; i<unitQuant; i++) {
                    unit = (ArmyUnit) CreateObject(objectType);
                    unit.setRace(nation);
                    pool.addUnit(player, unit); //TODO add location 
                }
                it.remove();
            }
        }
    }
    
    /**
     * This constructor for {@link ScenarioConfigurationReader} is intended to 
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
    public ScenarioConfigurationReader(String configurationFileName) {
        //Attempts to read and store information from the given file.

        JSONParser parser = new JSONParser();

        controllingPlayers = new HashMap<>();
        nationNames = new ArrayList<>();
        setupOrders = new HashMap<>();
        moveOrders = new HashMap<>();
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

        try {
            //The entire file:
            Object obj = parser.parse(new FileReader(configurationFileName));
            JSONObject jsonObject = (JSONObject) obj;

            //Top-level game information:
            scenarioName = (String) jsonObject.get("scenarioName");
            numberOfPlayers = ((Long) jsonObject.get("numberOfPlayers")).intValue();
            gameLength = ((Long) jsonObject.get("gameLength")).intValue();

            //We'll iterate and grab info for each human controlled nation:
            JSONArray JSONNationArray = (JSONArray) jsonObject.get("nations");
            for (Object baseNationObject : JSONNationArray) {
                JSONObject nationObject = (JSONObject) baseNationObject;

                String nationName = (String) nationObject.get("name");
                nationNames.add(nationName);
                
                int controllingPlayer = ((Long) nationObject.get("player")).intValue();
                controllingPlayers.put(nationName, controllingPlayer);

                int setupOrder = ((Long) nationObject.get("setupOrder")).intValue();
                setupOrders.put(nationName, setupOrder);

                int moveOrder = ((Long) nationObject.get("moveOrder")).intValue();
                moveOrders.put(nationName, moveOrder);

                String replacementDescription = (String) nationObject.get("replacements");
                replacements.put(nationName, replacementDescription);
                
                String reinforcementDescription = (String) nationObject.get("reinforcements");
                reinforcements.put(nationName, reinforcementDescription);

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

            //Iterate and grab information for neutral nations:
            JSONArray neutralJSONArray = (JSONArray) jsonObject.get("neutrals");
            for (Object neutralBaseObject : neutralJSONArray) {
                JSONObject neutralObject = (JSONObject) neutralBaseObject;

                String neutralName = (String) neutralObject.get("name");
                neutralNames.add(neutralName);

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
                JSONObject nationUnitObject = (JSONObject) neutralObject.get("units");
                Map<String, Integer> nmap = new HashMap<>();
                for (Object entry : nationUnitObject.entrySet()) {
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

                if (neutralObject.containsKey("humanSacrifice")) {
                    if (((String) neutralObject.get("humanSacrifice")).equals("true")) {
                        acceptsSacrifice.put(neutralName, true);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ScenarioConfigurationReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ScenarioConfigurationReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
             Logger.getLogger(ScenarioConfigurationReader.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    
    /**
      * A getter for the scenarioName field.
      * 
      * @author Wayne Fuhrman
      * @return the scenario's name as a String
      */
    public String getScenarioName() {
        return scenarioName;
    }

    /**
      * A getter for the number of players in the scenario.
      * 
      * @author Wayne Fuhrman
      * @return the number of players in the scenario
      */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
      * A getter for the game length of the scenario.
      * 
      * @author Wayne Fuhrman
      * @return the game length of the scenario
      */
    public int getGameLength() {
        return gameLength;
    }
    
    /** return the list of nation names.
     * 
     * @author Tyler Jaszkowiak
     * @return a list of nations in the scenario
     */
    public List<String> getNationNames() {
        return nationNames;
    }
    
    /**
      * A getter for the controlling player of a given nation.
      * 
      * @author Tyler Jaszkowiak
      * @param name the name of the nation in question
      * @return the number of the player controlling the nation
      */
    public int getControllingPlayer(String name) {
        return controllingPlayers.get(name);
    }

    /** 
     * Gets the names of the neutrals in the scenario and returns them as a list.
     * 
     * @author Wayne Fuhrman
     * @return a list of neutrals in the scenario
     */
    public List<String> getNeutralNames() {
        return neutralNames;
    }

    /** Gets the setup order of a specific nation in the scenario
     * 
     * @param name name of the nation being queried about
     * @return the setup order of the nation in question
     */
    public Integer getSetupOrder(String name) {
        return setupOrders.get(name);
    }

    /**
     * Gets the move order of a specific nation in the scenario.
     * 
     * @param name name of the nation being queried about
     * @return the setup order of the nation in question
     */
    public Integer getMoveOrder(String name) {
        return moveOrders.get(name);
    }

    /**
     * Get the provinces a nation is allowed to set up in
     * 
     * @param name name of the nation in question
     * @return a list of the names of these provinces TODO: find a Province object?
     */
    public List<String> getProvinces(String name) {
        return provinces.get(name);
    }

    public List<String> getCharacters(String name) {
        return characters.get(name);
    }

    public Map<String, Integer> getUnits(String name) {
        return units.get(name);
    }

    public String  getReplacement(String name) {
        return replacements.get(name);
    }
    
    public String getReinforcement(String name) {
        return reinforcements.get(name);
    }

    public String getLeaningToward(String name) {
        return leaningTowards.get(name);
    }

    public Integer getLeaningAmount(String name) {
        return leaningAmount.get(name);
    }

    public Boolean acceptsSacrifice(String name) {
        return acceptsSacrifice.get(name);
    }

    /**
     * A simple main class for testing the reader on a specified file. It 
     * simply reads and calls the stdout print function.
     * 
     * @author Wayne Fuhrman
     * @param args there should be no command line args
     */
    public static void main(String[] args) {
        ScenarioConfigurationReader reader = new ScenarioConfigurationReader("resources/scenarios/0_Dummy.json");
        reader.populatePool();
        reader.print();
    }
}
