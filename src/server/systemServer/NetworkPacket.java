/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package systemServer;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Christopher Goes
 */
public class NetworkPacket implements Serializable {

    // Packet ID (Used by JVM)
    private static final long serialVersionUID = 1L;

    // Packet data
    private final Flag flag;
    private final Tag tag;
    private final List<Object> data;
    private final String sender;

    /**
     *
     * @param FLAG
     * @param TAG
     * @param DATA
     * @param SENDER
     */
    public NetworkPacket( Flag FLAG, Tag TAG, String SENDER, List<Object> DATA ) {

        // Null checks
        // Apparantly using ternary statements here is an error, according to netbeans
        // Not sure why that is, seems to be a design practice thing than an actual error
        if ( FLAG != null ) {
            this.flag = FLAG;
        } else {
            this.flag = Flag.NULL_FLAG;
        }

        if ( TAG != null ) {
            this.tag = TAG;
        } else {
            this.tag = Tag.NULL_TAG;
        }

        if ( DATA != null ) {
            this.data = DATA;
        } else {
            this.data = Collections.emptyList();
        }

        if ( SENDER != null ) {
            this.sender = SENDER;
        } else {
            this.sender = "";
        }
    }

    /**
     *
     * @param FLAG
     * @param TAG
     * @param sender
     */
    public NetworkPacket( Flag FLAG, Tag TAG, String sender ) {
        this( FLAG, TAG, sender, null );
    }

    /*
     * Default constructor, empty > null
     */
    public NetworkPacket() {
        this( null, null, null, null );
    }

    /**
     * @return the data
     */
    public List<Object> getData() {
        return Collections.unmodifiableList( data );
    }

    /**
     * @return the TAG
     */
    public Tag getTAG() {
        return tag;
    }

    /**
     *
     * @return
     */
    public Flag getFlag() {
        return flag;
    }

    /**
     *
     * @return
     */
    public String getSender() {
        return sender;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return data.isEmpty() && tag == null && flag == null && sender.isEmpty();
    }

}
