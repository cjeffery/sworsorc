/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package systemServer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import junit.framework.TestCase;

/**
 * Unit test for the Network Client
 * 
 * @author Christopher Goes
 */
public class NetworkClientTest extends TestCase {

    public NetworkClientTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        NetworkServer.main(null); // setup a test server
        
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        NetworkServer.stopServer(); // shutdown the test server
        
    }

    public void testCommands() {
        String inputline;
        String tempfile;

        //tempfile = dir + File.separator + "src" + File.separator + "server" + File.separator + helpfile;
        tempfile = "C:\\Users\\Tehlizard\\Documents\\NetBeansProjects\\sworsorc\\src\\test\\test_commands.txt";
        
        try {
            BufferedReader input = new BufferedReader(new FileReader(tempfile));

            try {
                while ((inputline = input.readLine()) != null) {
                    System.out.println(inputline); // Possible errors: newlines, whitespace, etc
                    assertTrue(NetworkClient.testCommand(inputline)); // Test the command
                }
            } catch (IOException ex) {
                System.err.println("Error in " + ex.getClass().getEnclosingMethod().getName()
                        + "!\nException: " + ex.getMessage() + "\nCause: " + ex.getCause());
                ex.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + tempfile + "\nException: " + e);
        }
        
    }
    
    
    /**
     * Send one of each (public) message in the NetworkClient interface.
     * <p>
     * This doesn't check that the message does anything in particular, but
     * it should eventually run a pretty good chunk of MessageUtils, NetworkClient, and
     * maybe even NetworkServer, and catch any outright exceptions/crashes.
     */
    public void testMessageDriver(){
        //NetworkClient.endTurn();
        //NetworkClient.sendChatMessage("Chatter chatter!");
        //other messages here!
    }
    
}
