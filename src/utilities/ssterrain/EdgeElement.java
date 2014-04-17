package ssterrain;

/**
 *
 * @author John Goettsche, Colin Clifford
 * CS 383 Software Engineering
 */
import Units.*;
import java.util.ArrayList;
import sshexmap.MainMap;
import sshexmap.MapHex;

/**
 * This class represents a single element of an edge.
 * A HexEdge will contain a collection of these
 */
public abstract class EdgeElement {
    //public abstract double getMovementCost(MoveableUnit unit);
    //public abstract double getCombatMultiplier(MoveableUnit unit);
    //public abstract String getCombatEffect(MoveableUnit unit);
    
    /**
     * @return the (enum) type of the edge
     */
    public abstract HexEdgeType getEdgeType();

    /**
     * Create an EdgeElement from an XML string code
     * @param attr the XML text
     * @return An EdgeElement with the type corresponding to the text
     */
    public static EdgeElement makeEdgeElement( String attr ) {
        switch(attr) {
            case "Pb": return makeEdgeElement( HexEdgeType.ProvinceBorder );
            case "Rd": return makeEdgeElement( HexEdgeType.Road );
            case "Tr": return makeEdgeElement( HexEdgeType.Trail);
            case "Br": return makeEdgeElement( HexEdgeType.Bridge );                
            case "Wa": return makeEdgeElement( HexEdgeType.Wall );
            case "St": return makeEdgeElement( HexEdgeType.Stream );
            case "Ga": return makeEdgeElement( HexEdgeType.Gate );
            
            default:
                System.out.println("unhandled edge attr: " + attr);
        }
        return null;
    }  

    /**
     * Create an EdgeElement from a HexEdgeType
     * @param t the type
     * @return An EdgeElement corresponding to the type
     */
    public static EdgeElement makeEdgeElement( HexEdgeType t ) {
        switch(t) {
            case ProvinceBorder: return new HEProvinceBorder();
            case Road: return new HERoad();
            case Trail: return new HETrail();
            case Bridge: return new HEBridge();    
            case Wall: return new HEWall();
            case Stream: return new HEStream();
            case Gate: return new HEGate();
        }
        System.out.println("unhandled case in makeEdgeElement :(");
        return null;
    }
}
