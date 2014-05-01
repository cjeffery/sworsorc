/*
 * All source code is the work of ClIntegeron Jeffery's Spring 2014 Software Engineering
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */
package systemServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Rebuild of MessageUtils
 * <p>
 * Contains Utilities for sending/receiving and packaging network messages
 * <p>
 * @author Networking Subteam
 */
final public class MessagePhoenix {

    // Leave disabled unless you are testing Network internals
    final private static boolean debug = true; // Change this to enable/disable debugging

    /**
     * Test if debugging is enabled
     *
     * @return
     */
    public static boolean debugStatus() {
        return debug;
    }

    // TODO: tag stripping done in MessagePhoenix
    // TODO: buffer size
    // TODO: need checks for game state on certain actions, such as lobbies
    /**
     * Sending a packed message to the specified output stream
     * <p>
     * Writes a single object, then flushes the stream
     *
     * @param writer
     * @param message
     *
     * @author Christopher Goes
     */
    public static void sendMessage( final ObjectOutputStream writer, final NetworkPacket message ) {
        if ( writer != null && message != null ) {
            try {
                writer.writeUnshared( message );
                writer.flush();
            } catch ( IOException ex ) {
                ex.printStackTrace();
            }
        } else {
            System.err.println( "Null stream passed to send!!" );
        }
    }

    /**
     * Receive a message from the specified stream
     *
     * @param reader
     *
     * @return
     *         A {@link NetworkPacket message} is returned
     *
     * @author Christopher Goes
     */
    public static NetworkPacket recieveMessage( final ObjectInputStream reader ) {
        NetworkPacket temp = new NetworkPacket();

        if ( reader != null ) {
            try {
                temp = (NetworkPacket) reader.readUnshared();
            } catch ( IOException | ClassNotFoundException |
                      NullPointerException ex ) {
                ex.printStackTrace();
            }
            return temp;
        } else {
            System.err.println( "Null reader passed to recieveMessage!" );
            return temp;
        }
    }

    /**
     * Pack a variable array of individual messages into a List suitable for sending
     *
     * @param message
     * @return
     *
     * @author Christopher Goes
     */
    public static List<Object> packMessageContents( Object... message ) {
        return message != null ? Arrays.asList( message ) : Collections.emptyList();
    }
}
