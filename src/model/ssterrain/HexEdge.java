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
public abstract class HexEdge {
    public abstract double getMovementCost(MoveableUnit unit);
    public abstract double getCombatMultiplier(MoveableUnit unit);
    public abstract String getCombatEffect(MoveableUnit unit);
    public abstract HexEdgeType getEdgeType();
    public abstract int getEdge();
    
    
    public static HexEdgeType getType(String attr) {
        switch(attr) {
            case "Pb": return HexEdgeType.ProvinceBorder;
            case "Rd": return HexEdgeType.Road;
            case "Tr": return HexEdgeType.Trail;
            case "Br": return HexEdgeType.Bridge;
            case "Wa": return HexEdgeType.Wall;
            case "St": return HexEdgeType.Stream;
            //case "Ga": return HexEdgeType.Gate;
            //case "Ro": //????????????
            default: //System.out.println(attr);
        }
        return null;
    }
}
