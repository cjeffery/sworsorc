package ssterrain;

import java.util.TreeMap;
import sshexmap.*;

/**
 * An actual edge between two hexes,
 * contains a collection of EdgeElements
 */
public class HexEdge {
    String hex1, hex2;
    
    //elements are stored by type, for fast queries
    public TreeMap<HexEdgeType, EdgeElement> elements;
    
    //public HexEdge() {
    //    elements = new TreeMap<HexEdgeType, EdgeElement>();
    //}

    /** Create an edge off of the given hexID and direction
     * 
     * @param hex The hex the edge is a neighbor of
     * @param dir The direction of the hex relative to the neighbor
     *            (0 = NE, 1 = N, 2 = NW, ... 5 = SE, 
     */
    public HexEdge(String hex, int dir) {
        elements = new TreeMap<HexEdgeType, EdgeElement>();
        hex1 = hex;
        hex2 = Hex.GetNeighborID(hex, dir);
        if(dir > 2) {
            String tmp = hex1;
            hex1 = hex2;
            hex2 = hex1;
        }
    }
    
    /**
     * add an element to the given edge
     * @param e The new edge element to add
     */
    public void put(EdgeElement e) {
        HexEdgeType t = e.getEdgeType();
        elements.put( t, e );
    }
    
    /**
     * If the edge has an element of the given type, return it
     * @param t the type to check for
     * @return the element with the specified type
     */
    public EdgeElement get(HexEdgeType t) {
        return elements.get(t);
    }
    
    /**
     * Untested...
     * Return the String ID for the given edge
     * The ID is just both hex ID's concatenated together with a comma between
     * them.
     * The first Hex ID should be the "lower" one according to the following
     * overly confusing diagram:
     *
     *   2 _ 
     *    /1\
     * 
     * @return The string ID for the given edge.
     */
    public String getEdgeID() {
        return String.format("%s,%s", hex1, hex2);
    }
    
    /**
     * Returns true if the edge contains an element of the specified type
     * @param t The type to search for
     */
    public boolean contains(HexEdgeType t) {
        for(HexEdgeType k : elements.keySet()) {
            if(k == t) return true;
        }
        return false;
    }
}
