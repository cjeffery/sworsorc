package ssterrain;

import java.util.TreeMap;

public class HexEdge {
    public TreeMap<HexEdgeType, EdgeElement> elements;
    
    public HexEdge() {
        elements = new TreeMap<HexEdgeType, EdgeElement>();
    }
    
    public void put(EdgeElement e) {
        HexEdgeType t = e.getEdgeType();
        elements.put( t, e );
    }
    
    public EdgeElement get(HexEdgeType t) {
        return elements.get(t);
    }
}
