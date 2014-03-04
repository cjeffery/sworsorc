package scenarioconfigurationreader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Wayne Fuhrman
 *
 * This class reads a scenario configuration file. There a bunch of getters().
 * Look at the "print()" function as an example of the getters().
 * 
 * You will need to point netbeans at the JSON.simple library (in libs directory)
 */

public class ScenarioConfigurationReader {
    
     List<String> playerNames;
     List<String> neutralNames;

    //Information storage for each player:
     
    //The player or neutral name is the key for these hashmaps
     Map<String, Integer> setupOrders; //name -> order
     Map<String, Integer> moveOrders; //name -> order
     Map<String, List<String>> provinces; //name -> list of province names
     Map<String, List<String>> characters; //name -> list of character names
     Map<String, Map<String, Integer>> units; //name -> (unitName -> unitCount)
     Map<String, String> replacements; //player -> description of replacement 

    //Diplomacy stuff:
     Map<String, String> leaningTowards;
     Map<String, Integer> leaningAmount;
     Map<String, Boolean> acceptsSacrifice;

    //General game information:
     String scenarioName;
     int numberOfPlayers;
     int gameLength; //Number of game turns

    public void print() {
        for (String player : getPlayerNames()) {
            System.out.println();
            System.out.println("Player: " + player);
            System.out.println("Sets up: " + getSetupOrder(player));
            System.out.println("Moves: " + getMoveOrder(player));
            System.out.println("Controls Provinces: " + getProvinces(player));
            System.out.println("Has characters: " + getCharacters(player));
            System.out.println("Has units: " + getUnits(player));
            System.out.println("Replacements: " + getReplacement(player));
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

    public ScenarioConfigurationReader(String configurationFileName) {
        //Attempts to read and store information from the given file.

        JSONParser parser = new JSONParser();

        playerNames = new ArrayList<>();
        setupOrders = new HashMap<>();
        moveOrders = new HashMap<>();
        provinces = new HashMap<>(); //Array of province names
        characters = new HashMap<>(); //Array of character names
        units = new HashMap<>(); //The return map is unitName:unitCount
        leaningTowards = new HashMap<>();
        leaningAmount = new HashMap<>();
        acceptsSacrifice = new HashMap<>();
        replacements = new HashMap<>();

        //neutralNames has keys for the maps:
        neutralNames = new ArrayList<>();

        try {
            //The entire file:
            Object obj = parser.parse(new FileReader("Weird.json"));
            JSONObject jsonObject = (JSONObject) obj;

            //Top-level game information:
            scenarioName = (String) jsonObject.get("scenarioName");
            numberOfPlayers = ((Long) jsonObject.get("numberOfPlayers")).intValue();
            gameLength = ((Long) jsonObject.get("gameLength")).intValue();

            //We'll iterate and grab info for each human controlled player:
            JSONArray JSONPlayerArray = (JSONArray) jsonObject.get("players");
            for (Object basePlayerObject : JSONPlayerArray) {
                JSONObject playerObject = (JSONObject) basePlayerObject;

                String playerName = (String) playerObject.get("name");
                playerNames.add(playerName);

                int setupOrder = ((Long) playerObject.get("setupOrder")).intValue();
                setupOrders.put(playerName, setupOrder);

                int moveOrder = ((Long) playerObject.get("moveOrder")).intValue();
                moveOrders.put(playerName, moveOrder);

                String replacementDescription = (String) playerObject.get("replacements");
                replacements.put(playerName, replacementDescription);

                List<String> playerProvinces = new ArrayList<>();
                for (Object provinceObject : (JSONArray) playerObject.get("provinces")) {
                    String province = (String) provinceObject;
                    playerProvinces.add(province);
                }
                provinces.put(playerName, playerProvinces);

                List<String> playerCharacters = new ArrayList<>();
                for (Object characterObject : (JSONArray) playerObject.get("characters")) {
                    String character = (String) characterObject;

                    playerCharacters.add(character);
                }
                characters.put(playerName, playerCharacters);

                JSONObject playerUnits = (JSONObject) playerObject.get("units");

                Map<String, Integer> unitAndCount = new HashMap<>();
                for (Object entry : playerUnits.entrySet()) {
                    Map.Entry en = (Map.Entry) entry;
                    String unitName = (String) en.getKey();
                    int unitCount = ((Long) en.getValue()).intValue();
                    unitAndCount.put(unitName, unitCount);

                }
                units.put(playerName, unitAndCount);

            }

            //Iterate and grab information for neutral players:
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
                JSONObject playerUnitObject = (JSONObject) neutralObject.get("units");
                Map<String, Integer> nmap = new HashMap<>();
                for (Object entry : playerUnitObject.entrySet()) {
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    public String getScenarioName() {
        return scenarioName;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getGameLength() {
        return gameLength;
    }
    public List<String> getPlayerNames() {
        return playerNames;
    }

    public List<String> getNeutralNames() {
        return neutralNames;
    }

    public Integer getSetupOrder(String name) {
        return setupOrders.get(name);
    }

    public Integer getMoveOrder(String name) {
        return moveOrders.get(name);
    }

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

    public String getLeaningToward(String name) {
        return leaningTowards.get(name);
    }

    public Integer getLeaningAmount(String name) {
        return leaningAmount.get(name);
    }

    public Boolean acceptsSacrifice(String name) {
        return acceptsSacrifice.get(name);
    }

    public static void main(String[] args) {

        ScenarioConfigurationReader reader = new ScenarioConfigurationReader("Weird.json");
        reader.print();
    }
}
