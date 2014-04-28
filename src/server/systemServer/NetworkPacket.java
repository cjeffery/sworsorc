/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package systemServer;

import java.io.Serializable;
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
    public NetworkPacket( Flag FLAG, Tag TAG, List<Object> DATA, String sender ) {
        this.flag = FLAG;
        this.tag = TAG;
        this.data = DATA;
        this.sender = sender;
    }

    public NetworkPacket( Flag FLAG, Tag TAG, List<Object> DATA ) {
        this( FLAG, TAG, DATA, null );
    }

    /**
     *
     * @param FLAG
     * @param TAG
     * @param sender
     */
    public NetworkPacket( Flag FLAG, Tag TAG, String sender ) {
        this( FLAG, TAG, null, sender );
    }

    public NetworkPacket( Flag FLAG, Tag TAG ) {
        this( FLAG, TAG, null, null );
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
