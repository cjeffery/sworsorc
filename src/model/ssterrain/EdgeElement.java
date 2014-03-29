package ssterrain;

/**
 *
 * @author John Goettsche
 * CS 383 Software Engineering
 */
import Units.*;
import java.util.ArrayList;
import sshexmap.MainMap;
import sshexmap.MapHex;
public abstract class EdgeElement {
    //public abstract double getMovementCost(MoveableUnit unit);
    //public abstract double getCombatMultiplier(MoveableUnit unit);
    //public abstract String getCombatEffect(MoveableUnit unit);
    public abstract HexEdgeType getEdgeType();

    /**
     * Create an EdgeElement from an XML string code
     * @param attr the XML text
     * @return An EdgeElement with the type corresponding to the text
     */
    public static EdgeElement makeEdgeElement( String attr ) {
        switch(attr) {
            case "Pb":
                return makeEdgeElement( HexEdgeType.ProvinceBorder );
            default:
                //System.out.println(attr);
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
            case ProvinceBorder:
                return new HEProvinceBorder();
        }
        return null;
    }
}
