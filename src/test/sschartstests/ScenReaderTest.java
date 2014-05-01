/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package sschartstests;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import sscharts.Scenario;

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
    
    // TODO: Test the unit list
    // TODO: Test those last getters in Scenario.java, whose corresponding
    //       data isn't even used by the game.
    //       Other getters for new data in the structure may help for coverage
    
    
    /*
    // test that the nation provinces were read correctly
    public void testProvinces() {
        boolean test;
        List<String> elvishProvinces;
        List<String> dwarfProvinces;
        elvishProvinces = new ArrayList<>();
        elvishProvinces.add("Intas");
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        Scenario reader = Scenario.getInstance();
        test = reader.getProvinces("Elves").equals(elvishProvinces);
        assertTrue(test);
        dwarfProvinces = new ArrayList<>();
        dwarfProvinces.add("Ithilgil");
        dwarfProvinces.add("Graumthog");
        test = reader.getProvinces("Dwarrows").equals(dwarfProvinces);
        assertTrue(test);
    }
    
    // test that the nation provinces were read correctly
    public void testCharacters() {
        boolean test;
        List<String> elvishChars;
        List<String> dwarfChars;
        elvishChars = new ArrayList<>();
        elvishChars.add("Dalmilandril");
        elvishChars.add("Linfalas");
        Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
        Scenario reader = Scenario.getInstance();
        test = reader.getCharacters("Elves").equals(elvishChars);
        dwarfChars = new ArrayList<>();
        dwarfChars.add("Paladin Glade");
        test = test && reader.getCharacters("Dwarrows").equals(dwarfChars);
        assertTrue(test);
    }
    */
}
