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
        List<String> nations = null;
        nations.add("Elves");
        nations.add("Dwarrows");
        ScenarioConfigurationReader reader = new ScenarioConfigurationReader("resources/scenarios/0_Dummy.json");
        test = reader.getNationNames() == nations;
        assertTrue(test);
    }
    
}
