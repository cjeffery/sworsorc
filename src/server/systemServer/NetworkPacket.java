/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package systemServer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Christopher Goes
 */
public class NetworkPacket implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Flag flag;
    private final Tag tag;
    private final List<Object> data;
    private final String sender;

    /**
     *
     * @param FLAG
     * @param TAG
     * @param DATA
     * @param sender
     */
    public NetworkPacket( Flag FLAG, Tag TAG, String sender, List<Object> DATA ) {
        this.flag = FLAG;
        this.tag = TAG;
        if ( DATA != null ) {
            this.data = DATA;
        } else {
            this.data = new ArrayList<>( 0 );
        }
        this.sender = sender; // there should not be a null sender
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
        //return Collections.unmodifiableList( data );
        return data;
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
