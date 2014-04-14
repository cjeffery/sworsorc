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
    
    public NetworkClient instance = null;

    public NetworkClientTest(String testName) {
        super(testName);
        instance = new NetworkClient("127.0.0.1", 25565, "default_user");
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        NetworkServer.main(null);
        
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        NetworkServer.stopServer();
        
    }

    /**
     * Test of connect method, of class NetworkClient.
     */
    public void testConnect() {
        System.out.println("connect() test");           
        assertTrue(instance.connect());
    }

    /**
     * Test of startClient method, of class NetworkClient.
     */
    public void testStartClient() {
        System.out.println("start");
        instance.startClient();
    }

    /**
     * Test of runClient method, of class NetworkClient.
     */
    public void testRunClient() {
        System.out.println("runClient");
        instance.runClient();
    }

    /**
     * Test of main method, of class NetworkClient.
     */
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        NetworkClient.main(args);
    }
    
}
