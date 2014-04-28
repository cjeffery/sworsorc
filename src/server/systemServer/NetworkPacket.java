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
    private static final long serialVersionUID = 1L;
    private final Flag flag;

    private final Tag tag;
    private final List<Object> data;
    private final String message;

    /**
     *
     * @param FLAG
     * @param TAG
     * @param DATA
     * @param MESSAGE
     */
    public NetworkPacket( Flag FLAG, Tag TAG, List<Object> DATA, String MESSAGE ) {
        this.flag = FLAG;
        this.tag = TAG;
        this.data = DATA;
        this.message = MESSAGE;
    }

    public NetworkPacket( Flag FLAG, Tag TAG, List<Object> DATA ) {
        this( FLAG, TAG, DATA, null );
    }

    /**
     *
     * @param FLAG
     * @param TAG
     * @param MESSAGE
     */
    public NetworkPacket( Flag FLAG, Tag TAG, String MESSAGE ) {
        this( FLAG, TAG, null, MESSAGE );
    }

    public NetworkPacket( Flag FLAG, Tag TAG ) {
        this( FLAG, TAG, null, null );
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
    public String getMessage() {
        return message;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return data.isEmpty() && tag == null && flag == null && message.isEmpty();
    }

}
