/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package systemServer;

import java.io.IOException;
import junit.framework.TestCase;

/**
 * Unit test for the Network Client
 * <p>
 * Currently very rudimentary, will be making it more through and robust for the next week
 * @author Christopher Goes
 */
public class NetworkClientTest extends TestCase {
    
    public NetworkClientTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of connect method, of class NetworkClient.
     */
    public void testConnect() {
        System.out.println("connect() test");
        NetworkClient instance = null;
        try {
            instance = new NetworkClient("127.0.0.1", 25565, "default_user");
        } catch (IOException e) {
            
        }
        boolean expResult = false;
        instance.connect();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of startClient method, of class NetworkClient.
     */
    public void testStartClient() throws Exception {
        System.out.println("start");
        NetworkClient instance = null;
        instance.startClient();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of runClient method, of class NetworkClient.
     */
    public void testRunClient() {
        System.out.println("runClient");
        NetworkClient instance = null;
        instance.runClient();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class NetworkClient.
     */
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        NetworkClient.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
