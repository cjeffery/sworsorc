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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Rebuild of MessageUtils
 * <p>
 * Contains Utilities for sending/receiving and packaging network messages
 * <p>
 * @author Networking Subteam
 */
final public class MessagePhoenix {

    // TODO: tag stripping done in MessagePhoenix
    // TODO: buffer size
    // TODO: would this be better as a method interface, then implement in classes?
    // TODO: make sure server is prepending messagetype/username
    // PUBLIC INTERFACE //
    /**
     * Sending a packed message
     *
     * @param writer
     * @param message
     */
    public static void sendMessage( final ObjectOutputStream writer, final NetworkPacket message ) {
        if ( writer != null && message != null ) {
            try {
                //writer.writeObject( message );
                writer.writeUnshared( message );
                writer.flush();
            } catch ( IOException ex ) {
                ex.printStackTrace();
            }
        } else {
            System.err.println( "Null stream passed to send!!" );
        }
    }

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

    // PUBLIC UTILITIES //

    /**
     *
     * @param message
     *                <p>
     * @return
     */
    public static List<Object> packMessageContents( Object... message ) {
        List<Object> temp = new ArrayList<>( 0 );
        temp.addAll( Arrays.asList( message ) );
        return temp;
    }

    /**
     *
     * @param items
     *              <p>
     * @return
     */
    public static List<String> createStringList( Object... items ) {
        List<String> temp = new ArrayList<>( 0 );
        for ( Object o : items ) {
            temp.add( (String) o );
        }
        return temp;
    }

    /**
     *
     * @param list
     *             <p>
     * @return
     */
    public static List<String> objectToString( List<Object> list ) {
        List<String> temp = new ArrayList<>( 0 );
        for ( Object ob : list ) {
            temp.add( (String) ob );
        }
        return temp;
    }

    /**
     *
     * @param list
     *             <p>
     * @return
     */
    public static List<Object> stringToObject( List<String> list ) {
        List<Object> temp = new ArrayList<>( 0 );
        if ( list != null && list.isEmpty() ) {
            temp.addAll( list );
        }
        return temp;
    }

}
