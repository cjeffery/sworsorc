/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package systemServer;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * Tests for NetworkClient. These tests occur in isolation (no network connection),
 * so many of these tests are quite trivial. 
 */
public class NetworkClientTest extends TestCase {
    
    public NetworkClientTest() {
    }
    
    private static Thread serverThread;
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    
        
    }
    
    @Before
    public void setUp() {
        //Hmm. So full test actually connections requires lauching the GUI classes.
//        serverThread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                NetworkServer.main(null);
//            }
//        });
//        serverThread.start();
//        
//        NetworkClient.initializeClient("Username", "127.0.0.1");
//        //NetworkClient.
//    
//        System.out.println("Set up called");
        
        
    }
    
    @After
    public void tearDown() {
        
           // serverThread.destroy();
        //System.out.println("Tore down");
    }

 
  


    /**
     * Test of poke method, of class NetworkClient.
     */
    @Test
    public void testPoke() {
        System.out.println("poke");
        Flag flag = null;
        Tag tag = null;
        NetworkClient.poke(flag, tag);
        
    }

    /**
     * Test of send method, of class NetworkClient.
     */
    @Test
    public void testSend() {
        System.out.println("send");
        Flag flag = null;
        Tag tag = null;
        Object[] message = null;
        NetworkClient.send(flag, tag, message);

    }

    /**
     * Test of userInput method, of class NetworkClient.
     */
    @Test
    public void testUserInput() {
        System.out.println("userInput");
        String command = "";
        NetworkClient.userInput(command);

    }

    /**
     * Test of sendChatMessage method, of class NetworkClient.
     */
    @Test
    public void testSendChatMessage() {
        System.out.println("sendChatMessage");
        String message = "";
        NetworkClient.sendChatMessage(message);

    }

    /**
     * Test of sendPrivateMessage method, of class NetworkClient.
     */
    @Test
    public void testSendPrivateMessage() {
        System.out.println("sendPrivateMessage");
        String user = "";
        String message = "";
        NetworkClient.sendPrivateMessage(user, message);

    }

    /**
     * Test of sendPhaseChange method, of class NetworkClient.
     */
    @Test
    public void testSendPhaseChange() {
        System.out.println("sendPhaseChange");
        String phase = "";
        NetworkClient.sendPhaseChange( phase, "" );
   
    }

    /**
     * Test of endTurn method, of class NetworkClient.
     */
    @Test
    public void testEndTurn() {
        System.out.println("endTurn");
        NetworkClient.endTurn();
    }

    /**
     * Test of createLobby method, of class NetworkClient.
     */
    @Test
    public void testCreateLobby() {
        System.out.println("createLobby");
        String lobby = "";
        NetworkClient.createLobby(lobby);
    }

    /**
     * Test of joinLobby method, of class NetworkClient.
     */
    @Test
    public void testJoinLobby() {
        System.out.println("joinLobby");
        String lobby = "";
        NetworkClient.joinLobby(lobby);
    }

    /**
     * Test of startGame method, of class NetworkClient.
     */
    @Test
    public void testStartGame() {
        System.out.println("startGame");
        NetworkClient.startGame();
    }

    /**
     * Test of isPhasing method, of class NetworkClient.
     */
    @Test
    public void testIsPhasing() {
        System.out.println("isPhasing");
        boolean expResult = false;
        boolean result = NetworkClient.isPhasing();
        assertEquals(expResult, result);
    }


    /**
     * Test of setServerName method, of class NetworkClient.
     */
    @Test
    public void testSetServerName() {
        System.out.println("setServerName");
        String sName = "127.0.0.1";
        NetworkClient.setServerName(sName);
        assertEquals(sName, NetworkClient.getServerName());
    }

    /**
     * Test of setServerPort method, of class NetworkClient.
     */
    @Test
    public void testSetServerPort() {
        System.out.println("setServerPort");
        int sPort = 7777;
        NetworkClient.setServerPort(sPort);
        assertEquals(sPort, NetworkClient.getServerPort());
    }

    /**
     * Test of setUsername method, of class NetworkClient.
     */
    @Test
    public void testSetUsername() {
        System.out.println("setUsername");
        String uName = "Vlad";
        NetworkClient.setUsername(uName);
        assertEquals(uName, NetworkClient.getUsername());
    }

 

    /**
     * Test of clientIsInitialized method, of class NetworkClient.
     */
    @Test
    public void testClientIsInitialized() {
        System.out.println("clientIsInitialized");
        boolean expResult = false;
        boolean result = NetworkClient.clientIsInitialized();
        assertEquals(expResult, result);
    }

 
    
}
