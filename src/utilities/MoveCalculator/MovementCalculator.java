/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MoveCalculator;

import Units.MoveableUnit;
import java.util.*;
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
    
    /* movement points cache for current movement calculation 
       keeps track of best path to target hex */
    static HashMap<MapHex, Double> allowance_cache;
    
    /** 
     * This method returns void, but takes an empty ArrayList of MapHex objects
     * to be filled (by reference) during the recursive calls of this method. 
     * The ArrayList will (after final return) hold all valid hex moves for the 
     * moving unit, starting from the currentHex. (\/)..(;,,,;)..(\/)
     * 
     * Also, see MapHex.java comments (approx. line 320) for the hex edge 
     * integer numbering scheme. For quick reference, the scheme is:
     * 
     *                     1     // North is 1
     *                  2     0  // North-west is 2, North-East is 0
     *                  3     5  // South-West is 3, South-East is 5
     *                     4     // South is 4.
     * 
     * @param movingUnit - the unit to be moved 
     * @param currentHex - the current hex location of the moving unit
     * @param moveAllowance - the unit's movement allowance.
     * @param validHexes  - an empty ArrayList holding type MapHex
     * @author Keith and Ian
     */
    public static void getValidMoves(MoveableUnit movingUnit, MapHex currentHex,
            double moveAllowance, ArrayList<MapHex> validHexes) 
    {
        //clear the cache, at the start of a new movement.
        if(validHexes.isEmpty()) {
            allowance_cache = new HashMap<MapHex, Double>();
        }
        /* check to see if there's already a faster path in the cache
           if so no point in recursing.
           if not, add the curret path as the fastest path
        */
        Double allowance;
        allowance = allowance_cache.putIfAbsent(currentHex, moveAllowance);
        if(allowance != null) {
            if(allowance >= moveAllowance)
                return;
            else
                allowance_cache.put(currentHex, moveAllowance);
        }

        int edgeSignal;
        double moveCost;
        TerrainType destinationTerrainType;
        ArrayList<MapHex> neighbors = new ArrayList<MapHex>();
        
        // This is the case where the move was legal :D
        if( moveAllowance > 0 )
        {  
            //For each hex edge, 0-5, get the neighboring hex, if it's valid
            for (int i = 0; i < 6; i++) 
            {
                //Check if the hex is valid, null returned if hex neighbor no exist
                if( currentHex.getNeighbor(i) != null ) 
                {
                    // add each valid neighbor to neighbors if not vortex hex
                    neighbors.add(currentHex.getNeighbor(i));
                }
            }
            
            if( currentHex.IsVortexHex())
                return;
            
            // check to see if current hex has enemy units in it
            // if 0 then hex is empty
            if( getUnits(currentHex, movingUnit) == 0 )
            {
                
            }
            // if 1 then hex has friendly units
            // TODO check if there are more then 2 units
            if( getUnits(currentHex, movingUnit) == 1 )
            {
                
            }
            // if -1 then hex has enemy units
            if( getUnits(currentHex, movingUnit) == -1 )
            {
                
            }
            
            // Add the current hex
            if( !validHexes.contains(currentHex) )
                validHexes.add(currentHex);

            
            // Let the recursion begin...
            for( int i = 0; i < neighbors.size(); i++ )
            {
                edgeSignal = getEdgeSignal( currentHex, i ); 
                
                switch (edgeSignal) {
                    case 1 : // Subtract one for moving over bridge edge
                        getValidMoves( movingUnit, neighbors.get(i),
                                moveAllowance - 1, validHexes );
                        break;
                    case 2 : // Subtract 3 for moving over ford edge.
                        getValidMoves( movingUnit, neighbors.get(i), 
                                moveAllowance - 3, validHexes );
                        break;
                    case 3 : // Subtract 1 for moving through gate, like bridge
                        getValidMoves( movingUnit, neighbors.get(i), 
                                moveAllowance - 1, validHexes );
                        break;
                    case 4 : // Subtract .5 for moving along a road
                        getValidMoves( movingUnit, neighbors.get(i),
                                moveAllowance - .5, validHexes );
                        break;
                    case 5 : // Stream hexside - get terrain cost and add one, recurse
                        destinationTerrainType = neighbors.get(i).getTerrainType();
                        moveCost = destinationTerrainType.getMovementCost(movingUnit);
                        moveCost = moveCost + 1.;
                        getValidMoves( movingUnit, neighbors.get(i),
                                moveAllowance - moveCost, validHexes );
                        break;
                    case 6 : // Subtrace only one from move cost for trail
                        getValidMoves( movingUnit, neighbors.get(i),
                                moveAllowance - 1, validHexes );
                        break;
                    case 7 : // break without recursion for case of wall
                        break;
                    case 8 : // break without recursion for case of enemy unit
                        break;
                    default : // Case where no hex edge applies.
                        destinationTerrainType = neighbors.get(i).getTerrainType();
                        moveCost = destinationTerrainType.getMovementCost(movingUnit);
                        if( moveCost == 99. )
                            break;
                        getValidMoves( movingUnit, neighbors.get(i),
                                moveAllowance - moveCost, validHexes );
                        break;
                }
            }
        
        } else if( moveAllowance == 0 ) // move is exactly legal, add & return
        {
            // do not recurse here - end of the line
            if( !currentHex.IsVortexHex() )
                validHexes.add(currentHex);
            return;
            
        } else if( moveAllowance < 0 ) // ILLEGAL!!!! DO NOT ADD!!!
        {
            // do not add, do not recurse, moving here is illegal
            return;
        }
    }
    /**
     * This function checks to see if the sourceHex has enemy units in it. It 
     * returns a 0 if the sourceHex has no units in it, 1 if it contains units
     * but they are friendly units, and -1 if the sourceHex has enemy units.
     * @param sourceHex
     * @param movingUnit
     * @return 
     */
    public static int getUnits(MapHex sourceHex, MoveableUnit movingUnit)
    {
        ArrayList<MoveableUnit> unitsInHex = new ArrayList<MoveableUnit>();
        unitsInHex = sourceHex.getUnits();
        
        if( unitsInHex == null )
        {
            return 0;
        }
        /*
        The error is here in the .get(0) function. It is trying to get the
        first entry of an empty list. 
        */
        if( unitsInHex.size() > 0) //This if is killing the exception 
        { 
            if( unitsInHex.get(0).getID().equals(movingUnit.getID()) )
            {
                return 1;
            }
            else
            {
                return -1;
            }
        }
        else
        {
            return 0;
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
    public static int getEdgeSignal(MapHex sourceHex, int sourceEdgeDirection ) 
    {
        // allocate and initialize return variable and HexEdge being examined
        int edgeSignal = 0;
        HexEdge edge = sourceHex.getEdge(sourceEdgeDirection);
        
        
        if( edge.contains( HexEdgeType.Bridge ) ) 
        {
            //System.out.println( "Bridge" );
            edgeSignal = 1;
        }
        else if( edge.contains( HexEdgeType.Ford ) )
        {
            //System.out.println( "Ford" );
            edgeSignal = 2;
        }        
        else if( edge.contains( HexEdgeType.Gate ) )
        {
            //System.out.println( "Gate" );
            edgeSignal = 3;
        }
        else if( edge.contains( HexEdgeType.Road ) )
        {
            //System.out.println( "Road" );
            edgeSignal = 4;
        }
        else if( edge.contains( HexEdgeType.Stream ) )
        {
            //System.out.println( "Stream" );
            edgeSignal = 5;
        }
        else if( edge.contains( HexEdgeType.Trail ) )
        {
            //System.out.println( "Trail" );
            edgeSignal = 6;
        }
        else if( edge.contains( HexEdgeType.Wall ) )
        {
            //System.out.println( "Wall" );
            edgeSignal = 7;
        }
        else 
        {
            //System.out.println( "Else" );
            // Here should be a check to see if there are enemy units in the 
            // neighbor hex - for now, just return 0 to indicate terrain should
            // be used for movement cost.
            //edgeSignal = 8;
            edgeSignal = 9;
        }
        
        return edgeSignal;
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
