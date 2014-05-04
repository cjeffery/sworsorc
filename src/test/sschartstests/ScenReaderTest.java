/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package sschartstests;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;
import sscharts.Scenario;
import Units.UnitPool;

/**
 * A test case for the src/utilities/sscharts/Scenario.java
 class. It runs a few simple JUnit tests after loading Scenario 2: Dwarrows in
 * order to determine if the scenario class 
 * behaves as expected.
 * <p>
 * TODO: This only tests the data and the getters under ideal input.
 * Error handling needs to be done in the reader and tests for it should
 * be added here.
 * 
 * @author Tyler
 */
public class ScenReaderTest extends TestCase {
    public ScenReaderTest(String testName) {
        super(testName);
    }
    
    /*
    // test the unitpool populator method 
    // since there is no map to populate, this can't really be tested befeore the 
    // map is loaded. So it's useless as a standalone test.
    public void testPopulator() {
        boolean test;
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        Scenario.populatePool();
        UnitPool pool = UnitPool.getInstance();
        test = pool.getAllPlayerUnits(1).size() == 20;
        assertTrue(test);
    } */
    
    // test that the scenario name was read correctly
    public void testScenarioName() {
        boolean test;
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = Scenario.getScenarioName().equals("The First Dwarro-Orcish War");
        assertTrue(test);
    }
    
    // test that the scenario number of players was read correctly
    public void testNumberofPlayers() {
        boolean test;
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = Scenario.getNumberOfPlayers() == 2;
        assertTrue(test);
    }
    
    // test that the scenario game length was read correctly
    public void testGameLength() {
        boolean test;
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = Scenario.getGameLength() == 12;
        assertTrue(test);
    }
    
    // test the Blue sun starting position
    public void testSolarConfig() {
        boolean test;
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = Scenario.getBlueSunStart() == 10;
        assertTrue(test);
    }
    
    // test that the nation names were read correctly
    public void testArmyNames() {
        boolean test;
        List<String> nations;
        nations = new ArrayList<>();
        nations.add("Dwarrows");
        nations.add("ORC");
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = Scenario.getArmyNames().equals(nations);
        assertTrue(test);
    }
    
    // test that the controlling player is correct
    public void testGetControllingPlayer() {
        boolean test;
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = (Scenario.getControllingPlayer("Dwarrows") == 1) &&
               (Scenario.getControllingPlayer("ORC") == 2);
        assertTrue(test);
    }
    
    // test that the setup order is correct
    public void testGetSetupOrder() {
        boolean test;
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        Scenario reader = Scenario.getInstance();
        test = (reader.getSetupOrder("Dwarrows") == 2) && 
               (reader.getSetupOrder("ORC") == 1);
        assertTrue(test);
    }
    
    // test that the move order is correct
    public void testGetMoveOrder() {
        boolean test;
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        Scenario reader = Scenario.getInstance();
        test = reader.getMoveOrder("Dwarrows") == 2 && 
               (reader.getMoveOrder("ORC") == 1);
        assertTrue(test);
    }
    
    // test the list of nations in the Dwarrows of Scenario 2
    public void testNationNames() {
        boolean test;
        List<String> nations = new ArrayList<>();
        nations.add("Krasnian");
        nations.add("Zirkastian");
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = Scenario.getNations("Dwarrows").equals(nations);
        assertTrue(test);
    }
    
    // test the list of neutrals 
    public void testNeutralNames() {
        boolean test;
        List<String> neutrals = new ArrayList<>();
        neutrals.add("Convivian");
        neutrals.add("Elven");
        neutrals.add("SpiderFolk");
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = Scenario.getNeutralNames().equals(neutrals);
        assertTrue(test);
    }
    
    // test the list of a nation's provinces via the Krasnian Dwarrows
    public void testProvinces() {
        boolean test;
        List<String> provs = new ArrayList<>();
        provs.add("Krasnia");
        provs.add("Outer Krasnia");
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = Scenario.getProvinces("Krasnian").equals(provs);
        assertTrue(test);
    }
    
    // test the list of a nation's characters via ORC
    public void testCharacters() {
        boolean test;
        List<String> chars = new ArrayList<>();
        chars.add("Chairman Naskhund");
        chars.add("Zarko");
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = Scenario.getCharacters("ORC").equals(chars);
        assertTrue(test);
    }
    
    // test getting the units of a given player nation
    public void testNationUnits() {
        boolean test;
        Map<String, Integer> unitList = new HashMap<>();
        unitList.put("Bow", 2);
        unitList.put("HeavyAxe", 8);
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = Scenario.getUnits("Krasnian").equals(unitList);
        assertTrue(test);
    }
    
    // test getting the units of a given neutral nation, just in case it behaves different
    public void testNeutralUnits() {
        boolean test;
        Map<String, Integer> unitList = new HashMap<>();
        unitList.put("Bow", 2);
        unitList.put("HeavyAxe", 8);
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = Scenario.getUnits("Convivian").equals(unitList);
        assertTrue(test);
    }
    
    // test the getRace of a nation via ORC
    public void testNationRace() {
        boolean test;
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = Scenario.getRace("ORC").equals("Orc");
        assertTrue(test);
    }
    
    // test the getRace of a neutral via ORC
    public void testNeutralRace() {
        boolean test;
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = Scenario.getNeutralRace("Convivian").equals("Dwarrows");
        assertTrue(test);
    }

    // test the getReinforcement of a nation via ORC
    public void testReinforcements() {
        boolean test;
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = Scenario.getReinforcement("ORC").equals("0");
        assertTrue(test);
    }

    // test the getReplacement of a nation via ORC
    public void testReplacements() {
        boolean test;
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = Scenario.getReplacement("ORC").equals("1 in 2");
        assertTrue(test);
    }

    // test a netural's leaning toward value
    public void testLeaningToward() {
        boolean test;
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = Scenario.getLeaningToward("SpiderFolk").equals("Orc");
        assertTrue(test);
    }

    // test a netural's leaning amount value
    public void testLeaningAmount() {
        boolean test;
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        test = Scenario.getLeaningAmount("SpiderFolk").equals(1);
        assertTrue(test);
    }

    // test a netural's human sacrifice value
    public void testAcceptsSacrifice() {
        boolean test;
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        System.out.print("Do SpiderFolk accept human sacrifice? " + Scenario.acceptsSacrifice("SpiderFolk"));
        test = Scenario.acceptsSacrifice("SpiderFolk");
        assertTrue(test);
    }
}
