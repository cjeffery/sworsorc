/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package sschartstests;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import junit.framework.TestCase;
import sscharts.ScenarioConfigurationReader;

/**
 * A test case for the src/utilities/sscharts/ScenarioConfigurationReader.java
 * class. It runs a few simple JUnit tests to determine if the scenario reader 
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
        ScenarioConfigurationReader reader = new ScenarioConfigurationReader("resources/scenarios/0_Dummy.json");
        test = reader.getScenarioName().equals("The Dummy Scenario");
        assertTrue(test);
    }
    
    // test that the scenario number of players was read correctly
    public void testNumberofPlayers() {
        boolean test;
        ScenarioConfigurationReader reader = new ScenarioConfigurationReader("resources/scenarios/0_Dummy.json");
        test = reader.getNumberOfPlayers() == 2;
        assertTrue(test);
    }
    
    // test that the scenario game length was read correctly
    public void testGameLength() {
        boolean test;
        ScenarioConfigurationReader reader = new ScenarioConfigurationReader("resources/scenarios/0_Dummy.json");
        test = reader.getGameLength() == 3;
        assertTrue(test);
    }
    
    // test that the nation names were read correctly
    public void testNationNames() {
        boolean test;
        List<String> nations;
        nations = new ArrayList<>();
        nations.add("Elves");
        nations.add("Dwarrows");
        ScenarioConfigurationReader reader = new ScenarioConfigurationReader("resources/scenarios/0_Dummy.json");
        test = reader.getNationNames().equals(nations);
        assertTrue(test);
    }
    
    // test that the controlling player is correct
    public void testGetControllingPlayer() {
        boolean test;
        ScenarioConfigurationReader reader = new ScenarioConfigurationReader("resources/scenarios/0_Dummy.json");
        test = (reader.getControllingPlayer("Elves") == 1) &&
                (reader.getControllingPlayer("Dwarrows") == 2);
        assertTrue(test);
    }
    
    // TODO: since neutrals are not yet in Dummy or handled by the pool populator, 
    // they will be tested later.
    
    // test that the setup order is correct
    public void testGetSetupOrder() {
        boolean test;
        ScenarioConfigurationReader reader = new ScenarioConfigurationReader("resources/scenarios/0_Dummy.json");
        test = (reader.getSetupOrder("Dwarrows") == 2) && 
               (reader.getSetupOrder("Elves") == 1);
        assertTrue(test);
    }
    
    // test that the move order is correct
    public void testGetMoveOrder() {
        boolean test;
        ScenarioConfigurationReader reader = new ScenarioConfigurationReader("resources/scenarios/0_Dummy.json");
        test = reader.getMoveOrder("Dwarrows") == 2 && 
               (reader.getMoveOrder("Elves") == 1);
        assertTrue(test);
    }
    
    // test that the nation provinces were read correctly
    public void testProvinces() {
        boolean test;
        List<String> elvishProvinces;
        List<String> dwarfProvinces;
        elvishProvinces = new ArrayList<>();
        elvishProvinces.add("Vynar");
        elvishProvinces.add("Nattily Woods");
        ScenarioConfigurationReader reader = new ScenarioConfigurationReader("resources/scenarios/0_Dummy.json");
        test = reader.getProvinces("Elves").equals(elvishProvinces);
        dwarfProvinces = new ArrayList<>();
        dwarfProvinces.add("The Empire");
        test = test && reader.getProvinces("Dwarrows").equals(dwarfProvinces);
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
        ScenarioConfigurationReader reader = new ScenarioConfigurationReader("resources/scenarios/0_Dummy.json");
        test = reader.getCharacters("Elves").equals(elvishChars);
        dwarfChars = new ArrayList<>();
        dwarfChars.add("Paladin Glade");
        test = test && reader.getCharacters("Dwarrows").equals(dwarfChars);
        assertTrue(test);
    }
    
}
