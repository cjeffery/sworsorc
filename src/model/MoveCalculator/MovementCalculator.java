/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MoveCalculator;

import Units.MoveableUnit;
import java.util.ArrayList;
import sshexmap.MapHex;
import ssterrain.HexEdge;
import ssterrain.HexEdgeType;
import ssterrain.TerrainType;

/**
 * The movement calculator class handles calculating movement for a given unit, 
 * and potentially, eventually, all units in a stack (if stacks are implemented). 
 * @author Keith/Ian
 */
public class MovementCalculator 
{

    /**
     * Private constructor throws an assertion error as this class should
     * not be instantiated. This only provides utility, no purpose for instance.
     * @author Keith Drew
     */
    private MovementCalculator() 
    {
        // Enforce noninstantiability
        throw new AssertionError();
    }
    
    /** 
     * This method returns void, but takes an empty ArrayList of MapHex objects
     * to be filled (by reference) during the recursive calls of this method. 
     * The ArrayList will (after final return) hold all valid hex moves for the 
     * moving unit, starting from the currentHex. (\/)..(;,,,;)..(\/)
     * @param movingUnit
     * @param startHex
     * @param moveAllowance
     * @param validHexes 
     * @author Keith and Ian
     */
    public static void getValidMoves(MoveableUnit movingUnit, MapHex currentHex,
            double moveAllowance, ArrayList<MapHex> validHexes) 
    {
        double edgeCost = 0;
        double moveCost = 0;
        TerrainType destinationTerrainType;
        ArrayList<MapHex> neighbors = new ArrayList<MapHex>();

        //For each hex edge, 0-5, get the neighboring hex, if it's valid
        for (int i = 0; i < 6; i++) 
        {
            //Check if the hex is valid, null returned if hex neighbor no exist
            if (currentHex.getNeighbor(i) != null) 
            {
                // add each valid neighbor to neighbors
                neighbors.add(currentHex.getNeighbor(i));
            }
        }
        
        // This is the case where the move was legal :D
        if( moveAllowance > 0 )
        {            
            // Add the current hex
            validHexes.add(currentHex);

            // Iterate over neighbors and recurse on them.
            for( int i = 0; i < neighbors.size(); i++ )
            {
                // Get edge cost of moving from current hex to neighbor i
                edgeCost = getEdgeCost( currentHex, neighbors.get(i) , i);
                
                // Switch case determines type of edge - (-1) is invalid, 0 
                // means no bonus from edge, and 1 means the move costs one
                //                     1 = (road/trail)
                switch ( (int)edgeCost ) 
                {
                    case -1 :                         
                        return; // nothing to do here
                    case 0 : 
                        // get the terrain type of the neighbor, determine cost
                        destinationTerrainType = neighbors.get(i).getTerrainType();
                        moveCost = destinationTerrainType.getMovementCost(movingUnit);
                        // If the moveCost is 99, the move is illegal
                        if( moveCost == 99 )
                            break;
                        // recurse on neighbor i with modified move cost
                        if( neighbors.get(i) != null )
                        {
                            getValidMoves( movingUnit, neighbors.get(i), 
                                moveAllowance - moveCost, validHexes );
                        }
                        break;
                    case 1 : case 3 : 
                        // Recurse on neighbor i with edge cost mod to movement
                        if( neighbors.get(i) != null )
                        {
                            getValidMoves( movingUnit, neighbors.get(i), 
                                moveAllowance - edgeCost, validHexes );
                        }
                        break;
                    case -5 : // Signals the special case of a +1 edge cost for streams
                        // Get neighbor terrain type, determine cost
                        destinationTerrainType = neighbors.get(i).getTerrainType();
                        moveCost = destinationTerrainType.getMovementCost(movingUnit);
                        // If the moveCost is 99, move is illegal
                        if( moveCost == 99 )
                            break;
                        moveCost++; // Increment by 1 for stream hexside
                        // recurse on neighbor i with modified move cost
                        if( neighbors.get(i) != null )
                        {
                            getValidMoves( movingUnit, neighbors.get(i),
                                moveAllowance - moveCost, validHexes );
                        }
                        break;
                    case -3 : // Handle the case of road where move cost is .5
                        // Get neighbor terrain type, determine cost
                        destinationTerrainType = neighbors.get(i).getTerrainType();
                        moveCost = destinationTerrainType.getMovementCost(movingUnit);
                        // If the moveCost is 99, move is illegal
                        if( moveCost == 99 )
                            break;
                        moveCost = .5; // Increment by 1 for stream hexside
                        // recurse on neighbor i with modified move cost
                        if( neighbors.get(i) != null )
                        {
                            getValidMoves( movingUnit, neighbors.get(i),
                                moveAllowance - moveCost, validHexes );
                        }
                        break;
                    default :
                        // just a testing error message.
                        System.out.println("Failure in Switch in MovementCalculator.getValidMoves");
                }
                
            }                        
        } else if( moveAllowance == 0 ) // move is exactly legal, add & return
        {
            // do not recurse here - end of the line
            validHexes.add(currentHex);
            return;
            
        } else if( moveAllowance < 0 ) // ILLEGAL!!!! DO NOT ADD!!!
        {
            // do not add, do not recurse, moving here is illegal
            return;
        }
    }
    
    
    /**
     * This method returns -1 if the move between source and dest. is invalid,
     * 0 if there is no road or trail between source and dest., and 
     * 1 if there IS a road/trail linking the hexes. This is a helper for the 
     * getValidMoves() method.
     * @param sourceHex
     * @param destinationHex
     * @return edgeCost 
     * @author Keith and Ian
     */
    public static double getEdgeCost(MapHex sourceHex, MapHex destinationHex,
                                int sourceEdgeDirection ) 
    {
        // allocate and initialize return variable and HexEdge being examined
        double edgeCost = 0;
        HexEdge edge = sourceHex.getEdge(sourceEdgeDirection);
        
        
        if( edge.contains( HexEdgeType.Bridge ) ) 
        {
            //System.out.println( "Bridge" );
            edgeCost = 1;
        }
        else if( edge.contains( HexEdgeType.Ford ) )
        {
            //System.out.println( "Ford" );
            edgeCost = 3;
        }        
        else if( edge.contains( HexEdgeType.Gate ) )
        {
            //System.out.println( "Gate" );
            edgeCost = 1;
        }
        else if( edge.contains( HexEdgeType.Road ) )
        {
            //System.out.println( "Road" );
            edgeCost = -3;
        }
        else if( edge.contains( HexEdgeType.Stream ) )
        {
            //System.out.println( "Stream" );
            edgeCost = -5;
        }
        else if( edge.contains( HexEdgeType.Trail ) )
        {
            //System.out.println( "Trail" );
            edgeCost = 1;
        }
        else if( edge.contains( HexEdgeType.Wall ) )
        {
            //System.out.println( "Wall" );
            edgeCost = -1;
        }
        else 
        {
            //System.out.println( "Else" );
            // Here should be a check to see if there are enemy units in the 
            // neighbor hex - for now, just return 0 to indicate terrain should
            // be used for movement cost.
            edgeCost = 1;
        }
        
        return edgeCost;
    }

    /**
     * This method takes a possible destination hex and a unit, who's valid moves
     * are being calculated, and determines if there are enemy units in that 
     * hex. If there are, the move is illegal and false is returned. Otherwise,
     * true is returned. This method is dependent upon the id numbering scheme
     * of moveable units including a reference to players and the ability to 
     * access any units residing in the destinationHex from that hex. 
     * @param destinationHex
     * @param movingUnit
     * @return enemyOccupied
     * @author Keith and Ian
     */
    public static boolean isEnemyOccupiedHex(MapHex destinationHex,
            MoveableUnit movingUnit) {
        // TODO
        boolean enemyOccupied = false;

        return enemyOccupied;
    }
}
