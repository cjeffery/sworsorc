/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MoveCalculator;

import Units.MoveableUnit;
import java.util.ArrayList;
import sshexmap.MapHex;
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

        // For each hex edge, 0-5, get the neighboring hex, if it's valid
        for (int i = 0; i < 6; i++) 
        {
            // Check if the hex is valid, null returned if hex neighbor no exist
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
                edgeCost = getEdgeCost( currentHex, neighbors.get(i) );
                
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
                        // recurse on neighbor i with modified move cost
                        getValidMoves( movingUnit, neighbors.get(i), 
                                moveAllowance - moveCost, validHexes);
                        break;
                    case 1 : 
                        // Recurse on neighbor i with edge cost mod to movement
                        getValidMoves( movingUnit, neighbors.get(i), 
                                moveAllowance - edgeCost, validHexes );
                        break;
                    default :
                        // just a testing error message.
                        System.out.println("Failure in Switch in MovementCalculator.getValidMoves");
                }
                
            }                        
        } else if( moveAllowance == 0 ) // move is exactly legal, add & return
        {
            
            validHexes.add(currentHex);
            return;
            // do not recurse here - end of the line
            
        } else if( moveAllowance < 0 ) // ILLEGAL!!!! DO NOT ADD!!!
        {
            return;
            // do not add, do not recurse, moving here is illegal
        }
    }
    
    
    
    /**
     * Keith and Ian
     * This method returns void, but takes an empty ArrayList of MapHex objects
     * to be filled (by reference) during the recursive calls of this method. 
     * The ArrayList will (after final return) hold all valid hex moves for the 
     * moving unit, starting from the currentHex. (\/)..(;,,,;)..(\/)
     * @param movingUnit
     * @param currentHex
     * @param moveAllowance
     * @param validHexes 
     *
    public static void getValidMoves(MoveableUnit movingUnit, MapHex startHex,
            double moveAllowance, ArrayList<MapHex> validHexes) 
    {
        double edgeCost = 0;
        double moveCost = 0;
        TerrainType destinationTerrainType;

        ArrayList<MapHex> neighbors = new ArrayList<MapHex>();
        // For each hex edge, 0-5, get the neighboring hex, if it's valid
        for (int i = 0; i < 6; i++) 
        {
            // Check if the hex is valid, null returned if hex neighbor no exist
            if (startHex.getNeighbor(i) != null) 
            {
                // add each valid neighbor to neighbors
                neighbors.add(startHex.getNeighbor(i));
            }
        }

        // The recursion loop
        for (int i = 0; i < neighbors.size(); i++) 
        {
            // get the edgecost as returned from method for each iteration of 
            // loop and effectively each hex neighbor
            edgeCost = getEdgeCost(startHex, neighbors.get(i));
            // If edgeCost is 1, the edge represents a road/trail
            if (edgeCost == 1) 
            {
                // If moveAllowance - 1 allows for the move, recurse accordingly
                if (moveAllowance - edgeCost >= 0) 
                {
                    // Make sure the neighbor is not already in the list
                    if (validHexes.contains(neighbors.get(i))) 
                    {
                        neighbors.remove(i);
                    } else {
                        // add the neighbor hex to valid hexes
                        validHexes.add(neighbors.get(i));
                         //Recursion Step - Subtract the edge cost for 
                          //travelling over a road/trail.
                         
                        getValidMoves(movingUnit, neighbors.get(i),
                                moveAllowance - edgeCost, validHexes);
                    }
                }
            } // If edgecost is 0, then the move is legal, if not affordable. 
             // Determine move cost and recurse, if applicable.
              
            else if (edgeCost == 0) 
            {
                
                 // Get the movement cost of moving to neighbor i from current
                 // location then
                 // Get destination terrain type, move cost for unit into that
                 // terrain type
                 //

                destinationTerrainType = neighbors.get(i).getTerrainType();
                moveCost = destinationTerrainType.getMovementCost(movingUnit);

                // Check if moving into destHex is possible by move type.
                // if moveCost is 99, getMovementCost says it's an invalid move
                if ( moveAllowance - moveCost >= 0 && moveCost != 99 ) 
                {
                    // Make sure the neighbor is not already in the list
                    if ( validHexes.contains( neighbors.get(i) ) ) 
                    {
                        neighbors.remove(i);
                    } else {
                        // add the neighbor hex to valid hexes
                        validHexes.add(neighbors.get(i));
                        // Recursion Step - Subtract the move cost for moving 
                         // into the dest hex.
                         
                        getValidMoves(movingUnit, neighbors.get(i),
                                moveAllowance - moveCost, validHexes);
                    }
                }
            } else if (edgeCost == -1) {
                neighbors.remove(i);
            }
        }

    }
    
    */
    
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
    public static double getEdgeCost(MapHex sourceHex, MapHex destinationHex) {
        // TODO
        double edgeCost = 1;

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
