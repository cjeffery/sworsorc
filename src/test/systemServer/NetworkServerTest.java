/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package systemServer;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Test of NetworkServer interface. These tests are done in isolation (i.e. no
 * network activity), so this is far from a complete test. As such, many of
 * these tests have trivial expected results (like getUserNames() returns a list
 * of size zero).
 */
public class NetworkServerTest {
    
    public NetworkServerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of sendToAllClients method, of class NetworkServer.
     */
    @Test
    public void testSendToAllClients_String_String() {
        System.out.println("sendToAllClients");
        String sender = "test";
        String message = "message";
        NetworkServer.sendToAllClients(sender, message);
    }

    /**
     * Test of sendToAllClients method, of class NetworkServer.
     */
    @Test
    public void testSendToAllClients_4args() {
        System.out.println("sendToAllClients");
        Flag flag = null;
        Tag tag = null;
        String sender = "";
        List<Object> message = null;
        NetworkServer.sendToAllClients(flag, tag, sender, message);
    }

    /**
     * Test of sendToClient method, of class NetworkServer.
     */
    @Test
    public void testSendToClient() {
        System.out.println("sendToClient");
        String handle = "";
        Flag flag = null;
        Tag tag = null;
        String sender = "";
        List<Object> message = null;
        boolean expResult = false;
        boolean result = NetworkServer.sendToClient(handle, flag, tag, sender, message);
        assertEquals(expResult, result);
    }

    /**
     * Test of sendToEntireLobby method, of class NetworkServer.
     */
    @Test
    public void testSendToEntireLobby() {
        System.out.println("sendToEntireLobby");
        Lobby dalobby = null;
        Flag flag = null;
        Tag tag = null;
        String sender = "";
        List<Object> message = null;
        NetworkServer.sendToEntireLobby(dalobby, flag, tag, sender, message);
 
    }

    /**
     * Test of clientDisconnected method, of class NetworkServer.
     */
    @Test
    public void testClientDisconnected() {
        System.out.println("clientDisconnected");
        ClientObject client = null;
        NetworkServer.clientDisconnected(client);

    }

    /**
     * Test of getAllUserNames method, of class NetworkServer.
     */
    @Test
    public void testGetAllUserNames() {
        System.out.println("getAllUserNames");
        List<String> result = NetworkServer.getAllUserNames();
        assertEquals(result.size(), 0);
  
    }

    /**
     * Test of getTotalClients method, of class NetworkServer.
     */
    @Test
    public void testGetTotalClients() {
        System.out.println("getTotalClients");
        int expResult = 0;
        int result = NetworkServer.getTotalClients();
        assertEquals(expResult, result);
    }

    /**
     * Test of createNewLobby method, of class NetworkServer.
     */
    @Test
    public void testCreateNewLobby() {
        System.out.println("createNewLobby");
        String lobbyname = "";
        boolean expResult = false;
        boolean result = NetworkServer.createNewLobby(lobbyname);
        assertEquals(expResult, result);
    }

    /**
     * Test of joinLobby method, of class NetworkServer.
     */
    @Test
    public void testJoinLobby() {
        System.out.println("joinLobby");
        String lobbyName = "";
        ClientObject client = null;
        boolean expResult = false;
        boolean result = NetworkServer.joinLobby(lobbyName, client);
        assertEquals(expResult, result);
    }

    /**
     * Test of leaveLobby method, of class NetworkServer.
     */
    @Test
    public void testLeaveLobby() {
        System.out.println("leaveLobby");
        String client = "";
        NetworkServer.leaveLobby(client);
    }

    /**
     * Test of getLobbyUsers method, of class NetworkServer.
     */
    @Test
    public void testGetLobbyUsers() {
        System.out.println("getLobbyUsers");
        String lobbyName = "";
        List<String> result = NetworkServer.getLobbyUsers(lobbyName);
        assertEquals(result.size(), 0);
    }

    /**
     * Test of getLobbyNames method, of class NetworkServer.
     */
    @Test
    public void testGetLobbyNames() {
        System.out.println("getLobbyNames");
        List<String> result = NetworkServer.getLobbyNames();
        assertEquals(result.size(), 0);
    }

    /**
     * Test of lobbyExists method, of class NetworkServer.
     */
    @Test
    public void testLobbyExists() {
        System.out.println("lobbyExists");
        String lobbyname = "";
        boolean expResult = false;
        boolean result = NetworkServer.lobbyExists(lobbyname);
        assertEquals(expResult, result);
    }

    /**
     * Test of getTotalLobbies method, of class NetworkServer.
     */
    @Test
    public void testGetTotalLobbies() {
        System.out.println("getTotalLobbies");
        int expResult = 0;
        int result = NetworkServer.getTotalLobbies();
        assertEquals(expResult, result);
    }

    /**
     * Test of generateUniqueID method, of class NetworkServer.
     */
    @Test
    public void testGenerateUniqueID() {
        System.out.println("generateUniqueID");
        int result = NetworkServer.generateUniqueID();
        int resultTwo = NetworkServer.generateUniqueID();
        assertFalse(result == resultTwo);
    }

    /**
     * Test of pollClients method, of class NetworkServer.
     */
    @Test
    public void testPollClients() {
        System.out.println("pollClients");
        String question = "";
        boolean expResult = false;
        boolean result = NetworkServer.pollClients(question);
        assertEquals(expResult, result);
    }

    /**
     * Test of stopServer method, of class NetworkServer.
     */
    @Test
    public void testStartAndStopServer() {
        System.out.println("stopServer");
        NetworkServer.startServer();
        NetworkServer.stopServer();
    }

    /**
     * Test of main method, of class NetworkServer.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        NetworkServer.main(args);
    }

    /**
     * Test of startServer method, of class NetworkServer.
     */
    @Test
    public void testStartServer() {
        System.out.println("startServer");
        NetworkServer.startServer();
    }
    
}
