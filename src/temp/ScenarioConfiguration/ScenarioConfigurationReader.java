
package scenarioconfigurationreader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Wayne Fuhrman
 * Currently, this file reads and prints data from the example config file.
 * There will be a nicer interface at some point.
 */
public class ScenarioConfigurationReader {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JSONParser parser = new JSONParser();
        
        try {
            //The entire file:
            Object obj = parser.parse(new FileReader("Weird.json"));
            JSONObject jsonObject = (JSONObject) obj;
            
            //Top-level game information:
            String scenarioName = (String) jsonObject.get("scenarioName");
            long numberOfPlayers = (Long) jsonObject.get("numberOfPlayers");
            long gameLength = (Long) jsonObject.get("gameLength");
            
            //We'll iterate and grab info for each human controlled player:
            JSONArray players = (JSONArray) jsonObject.get("players");
            for (Object playerObject : players){
                JSONObject player = (JSONObject) playerObject;
                String playerName = (String) player.get("name");
                long setupOrder = (Long) player.get("setupOrder");
                long moveOrder = (Long) player.get("moveOrder");
                System.out.println("Player:" + playerName);
                System.out.println("Setup order: " +  setupOrder);
                System.out.println("Move order: " + moveOrder);
                
                for (Object provinceObject: (JSONArray) player.get("provinces")){
                    String province = (String) provinceObject;
                    System.out.println("Province:" + province);
                }
                
                for (Object characterObject: (JSONArray) player.get("characters")){
                    String character = (String) characterObject;
                    System.out.println("Characters: " + character);
                }
                
                //Units are: "unitName":unitCount
                JSONObject units = (JSONObject) player.get("units");
                for (Object entry : units.entrySet()){
                    Map.Entry en = (Map.Entry) entry;
                    String unitName = (String) en.getKey();
                    Long unitCount = (Long) en.getValue();
                    System.out.println(unitName + ": " + unitCount);
                    
                }
            }
            
            //Iterate and grab information for neutral players:
            JSONArray neutrals = (JSONArray) jsonObject.get("neutrals");
            for (Object neutralObject : neutrals){
                JSONObject neutral = (JSONObject) neutralObject;
                String neutralName = (String) neutral.get("name");
                System.out.println("Neutral: " + neutralName);
                
                for (Object provinceObject: (JSONArray) neutral.get("provinces")){
                    String province = (String) provinceObject;
                    System.out.println("Province:" + province);
                }
                
                for (Object characterObject: (JSONArray) neutral.get("characters")){
                    String character = (String) characterObject;
                    System.out.println("Characters: " + character);
                }
                
                JSONObject units = (JSONObject) neutral.get("units");
                for (Object entry : units.entrySet()){
                    Map.Entry en = (Map.Entry) entry;
                    String unitName = (String) en.getKey();
                    Long unitCount = (Long) en.getValue();
                    System.out.println(unitName + ": " + unitCount);
                }
                
                JSONObject diplomacy = (JSONObject) neutral.get("diplomacy");
                String province = (String) diplomacy.get("leaningTowards");
                Long hexesTowards = (Long) diplomacy.get("amount");
                if (neutral.containsKey("humanSacrifice")){
                    boolean humanSacrifice = true;
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
}

