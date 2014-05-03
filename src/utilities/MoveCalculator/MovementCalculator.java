/*
 * Here you will find the movement calculator class, god of movement. Without
 * this class you will be unable to move across the great valley and will be 
 * stuck to the currentHex like a tree. Bow before this class and worship the 
 * power it holds. The class commands respect. While the class is THE god of 
 * movement, it is not, in fact, a god class. 
 */
package MoveCalculator;

import Units.MoveableUnit;
import Units.UnitType;
import java.util.*;
import sshexmap.MapHex;
import ssterrain.HexEdge;
import ssterrain.HexEdgeType;
import ssterrain.TerrainType;

/**
 * The movement calculator class handles calculating movement for a given unit, 
 * and potentially, eventually, all units in a stack (if stacks are implemented)
 * The class is a non-instantiable class. 
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
    static HashMap<MapHex, Double> allowanceCache;
    // Retreat allowance cache, works for a similar cause as allowanceCache.
    static HashMap<MapHex, Double> retreatAllowanceCache;
    
    /**
     * This method is nice because it requires less information from the user 
     * of MovementCalculator, and gives more back than getValidMoves(...).
     * Colin created a nice HashMap for this class that optimizes movement by 
     * using a HashMap that I edit here and return to the caller. The HashMap 
     * contains the MapHex objects that are valid moves of 'unit' and the Double
     * that represents the unit's remaining movement allowance. Make sure to use
     * the VALUE (double) of the HashMap to reset a unit's workingMovement 
     * variable when they have selected and moved to a given hex.
     * @param unit
     * @param currHex
     * @return HashMap<MapHex, Double>
     * @author Keith Drew
     */
    public static HashMap<MapHex, Double> movementWrapper(MoveableUnit unit, 
            MapHex currHex )
    {
        ArrayList<MapHex> illegalMoves = new ArrayList<>();
        ArrayList<MapHex> validHexes = new ArrayList<>();
        getValidMoves(unit, currHex, unit.getWorkingMovement(), validHexes );
        
         // find all illegal moves in hashmap - those with negative values.
        allowanceCache.keySet().stream().filter((key) -> 
                ( allowanceCache.get(key) < 0 ))
                .forEach((key) -> { illegalMoves.add(key);
        });
        
        // remove all entries in illegalMoves from the moves hashmap
        illegalMoves.stream().forEach((keyHex) -> {
            allowanceCache.remove( keyHex, allowanceCache.get(keyHex) );
        });
        
        return allowanceCache;
    }
    
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
        // If the current hex is not a valid move, return
        if( !isCurrentHexValid( currentHex, movingUnit ) )
            return;
        
        // Check if current hex is in an enemy zone of control
        if( isZoneOfControl( currentHex, movingUnit ) )
        {
            // If this is not the first of the unit, set movementallowance to 0.
            // this allows enemies to move out of a zone of control at the 
            // beginning of move.
            if( !validHexes.isEmpty() )
                moveAllowance = 0;
        }
                 
        //clear the cache, at the start of a new movement.
        if( validHexes.isEmpty() ) 
        {
            allowanceCache = new HashMap<>();
        }
        // Check for a faster path
        Double allowance;
        allowance = allowanceCache.putIfAbsent(currentHex, moveAllowance);
        if(allowance != null) {
            if(allowance >= moveAllowance )
                return;
            else
                allowanceCache.put(currentHex, moveAllowance);
        }
        
        int edgeSignal;
        double moveCost;
        TerrainType destinationTerrainType;
        ArrayList<MapHex> neighbors;
        
        // This is the case where the move was legal :D
        if( moveAllowance > 0 )
        {   
            // Get the current hexes neighbors
            neighbors = getNeighbors( currentHex );
            
            // Add the current hex to validhexes if it hasn't been added yet
            if( !validHexes.contains(currentHex) )
                validHexes.add(currentHex);

            // Let the recursion begin...
            for( int i = 0; i < neighbors.size(); i++ )
            {
                edgeSignal = getEdgeSignal( currentHex, i ); 
                
                switch (edgeSignal) {
                    case 1 : // Subtract one for moving over bridge edge
                        getValidMoves( movingUnit, neighbors.get(i),
                                moveAllowance - 1., validHexes );
                        break;
                    case 2 : // Subtract 3 for moving over ford edge.
                        getValidMoves( movingUnit, neighbors.get(i), 
                                moveAllowance - 3., validHexes );
                        break;
                    case 3 : // Subtract 1 for moving through gate, like bridge
                        getValidMoves( movingUnit, neighbors.get(i), 
                                moveAllowance - 1., validHexes );
                        break;
                    case 4 : // Subtract .5 for moving along a road
                        getValidMoves( movingUnit, neighbors.get(i),
                                moveAllowance - .5, validHexes );
                        break;
                    case 5 : //Stream hexside - get cost, add one, recurse
                        destinationTerrainType = neighbors.get(i)
                                .getTerrainType();
                        moveCost = destinationTerrainType.
                                getMovementCost(movingUnit);
                        moveCost = moveCost + 1.;
                        getValidMoves( movingUnit, neighbors.get(i),
                                moveAllowance - moveCost, validHexes );
                        break;
                    case 6 : // Subtrace only one from move cost for trail
                        getValidMoves( movingUnit, neighbors.get(i),
                                moveAllowance - 1., validHexes );
                        break;
                    case 7 : // break without recursion for case of wall
                        break;
                    case 8 : // break without recursion for case of enemy unit
                        break;
                    default : // Case where no hex edge applies.
                        destinationTerrainType = neighbors.get(i)
                                .getTerrainType();
                        moveCost = destinationTerrainType
                                .getMovementCost(movingUnit);
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
            
        } else if( moveAllowance < 0 ) // ILLEGAL!!!! DO NOT ADD!!!
        {
        }
    }
    /**
     * This function checks to see if the sourceHex has enemy units in it. It 
     * returns true if there are enemy units in the hex, false otherwise.
     * @param sourceHex
     * @param movingUnit
     * @return boolean
     */
    public static boolean getUnits(MapHex sourceHex, MoveableUnit movingUnit)
    {
        ArrayList<String> unitsInHex = new ArrayList<String>();
        if(sourceHex != null){
            unitsInHex = sourceHex.getUnitIDs();
        } else {
            return false;
        }
        // check if list is empty
        if( unitsInHex == null || unitsInHex.isEmpty() )
        {
            return false;
        }
        
        //oh my gosh this is so terrible
        int idInNewHex = Integer.parseInt(unitsInHex.get(0).split("#")[0]);
        int idOfCurrent = Integer.parseInt(movingUnit.getID().split("#")[0]);
        
        return idInNewHex != idOfCurrent;
    }
    
    /**
     * This method returns -1 if the move between source and dest. is invalid,
     * 0 if there is no road or trail between source and dest., and 
     * 1 if there IS a road/trail linking the hexes. This is a helper for the 
     * getValidMoves() method.
     * @param sourceHex
     * @param sourceEdgeDirection
     * @return edgeCost 
     * @author Keith and Ian
     */
    public static int getEdgeSignal(MapHex sourceHex, int sourceEdgeDirection ) 
    {
        // allocate and initialize return variable and HexEdge being examined
        int edgeSignal;
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
            edgeSignal = 9;
        }
        
        return edgeSignal;
    }

    /**
     * Takes the current hex location and moving unit, and determines if
     * the unit is currently in a zone of control.
     * @param currentHex
     * @param movingUnit
     * @return isZone
     * @author Keith and Ian
     */
    public static boolean isZoneOfControl(MapHex currentHex,
            MoveableUnit movingUnit) 
    {
        boolean isZone = false;
        
        // loop over neighbors and check if each neighbor has an enemy unit
        for( int i = 0; i < 6; i++)
        {
            // if true then neighbor hex has enemy unit
            if ( getUnits(currentHex.getNeighbor(i), movingUnit) )
                isZone = true;
        }
        // Characters are unaffected by zones of control
        if( movingUnit.getUnitType() == UnitType.Character )
            isZone = false;
        return isZone;
    }
    
    /**
     * This method determines if the hex being passed in (the "current" hex)
     * is a legal move from the context of being the hex that the recursion
     * is currently at. Returns false is the hex is an illegal move for the 
     * unit and true otherwise.
     * @param currHex
     * @param unit
     * @return boolean
     * @author Keith Drew
     */
    public static boolean isCurrentHexValid( MapHex currHex, MoveableUnit unit )
    {
        boolean valid = true;
        
        // Check if the current hex is a vortex hex.
        if( currHex.IsVortexHex() )
            valid = false;
        
        // Check if current hex has enemy units in it.
        if( getUnits( currHex, unit ) )
            valid = false;
        
        // Add more conditions for false here
        return valid;
    }
    
    /** 
     * This method returns an ArrayList containing the non-null neighbors of
     * currHex
     * @param currHex
     * @return ArrayList<MapHex> neighbors
     * @author Keith Drew
     */
    public static ArrayList<MapHex> getNeighbors( MapHex currHex )
    {
        ArrayList<MapHex> neighbors = new ArrayList<>();
        //For each hex edge, 0-5, get the neighboring hex, if it's valid
        for( int i = 0; i < 6; i++ )
        {
            // Make sure the neighbor exists
            if( currHex.getNeighbor( i ) != null )
                neighbors.add( currHex.getNeighbor(i));
        }
        return neighbors;
    }
    
    
    public static ArrayList<MapHex> getRetreatMoves( MapHex currentHex, 
            MoveableUnit retreater, double retreatLimit )
    {        
        getAllRetreatHexesInRange( currentHex, retreater, retreatLimit );
        
        filterRetreatMoves( retreater, retreatLimit );
        
        Iterator<Map.Entry<MapHex,Double>> it = 
                retreatAllowanceCache.entrySet().iterator();
        
        double minRetreatCode = Collections.min(retreatAllowanceCache.values());
        
        while ( it.hasNext() ) 
        {
            Map.Entry<MapHex,Double> entry = it.next();
            // If an entry value is greater than minVal, remove it.
            if( entry.getValue() > minRetreatCode )
                it.remove();
        }
        
        ArrayList<MapHex> retreatMoves = 
                new ArrayList<>( retreatAllowanceCache.keySet() );
        
        return retreatMoves;
    }    
    
    
    /**
     * This method filters the retreatAllowanceCache so that it only contains
     * MapHex keys that are at the maximum retreat distance possible for the 
     * retreating unit. The values associated with each remaining key becomes
     * the number that indicates hierarchy preference of valid retreat moves,
     * as per the rules of retreating.
     * @param retreater
     * @param retreatLimit
     * @author Keith and Shaung
     */
    public static void filterRetreatMoves( MoveableUnit retreater, 
            double retreatLimit )
    {
        // Get smallest value in hashmap - indicates max retreat distance
        double minVal = Collections.min( retreatAllowanceCache.values() );
        double retreatCase;
        
        // Create iterator for HashMap
        Iterator<Map.Entry<MapHex,Double>> it = 
                retreatAllowanceCache.entrySet().iterator();
        // Read all entries in hashmap and compare values agains minVal. 
        while ( it.hasNext() ) 
        {
            Map.Entry<MapHex,Double> entry = it.next();
            // If an entry value is greater than minVal, remove it.
            if( entry.getValue() > minVal )
                it.remove();
            else if( entry.getValue() == minVal ) 
            { // Otherwise, replace it's value with the retreatCase value.
                // Get reatreat case.
                retreatCase = getRetreatCase( retreater, entry.getKey() );
                
                // Set entries value to the retreatCase value;
                entry.setValue(retreatCase);
            }
        }
    }
    
    /** This method places all MapHexes within the retreat limit and their 
     *  associated remaining retreatLimit cost value into the 
     *  retreatAllowanceCache. ie, if a unit is forced to retreat 5 hexes, this 
     *  method will place all MapHexes, within 5 MapHexes, that are legal moves 
     *  for the given unit, into retreatAllowanceCache. However, this method 
     *  DOES NOT RETURN LEGAL RETREATS. The HashMap returned must be filtered.
     * @param currentHex
     * @param retreater
     * @param retreatLimit
     * @author Keith Drew
     */ // Currently in need of testing
    public static void getAllRetreatHexesInRange( MapHex currentHex, 
            MoveableUnit retreater, double retreatLimit )
    {
        // If the current hex is not a valid move, return
        if( !isCurrentHexValid( currentHex, retreater ) )
            return;
        
        if( retreatLimit < 0 )
            return;
        
        if( retreatAllowanceCache.isEmpty() )
            retreatAllowanceCache = new HashMap<>();
        
        // Check for a faster path
        Double rAllowance;
        rAllowance = retreatAllowanceCache.putIfAbsent(currentHex, 
                retreatLimit);
        if( rAllowance != null ) 
        {
            if(rAllowance >= retreatLimit )
                return;
            else
                retreatAllowanceCache.put(currentHex, retreatLimit );
        }
        
        // This is the case where the move was legal :D
        if( retreatLimit > 0 )
        {   
            int edgeSignal;
            ArrayList<MapHex> neighbors;
        
            // Get the current hexes neighbors
            neighbors = getNeighbors( currentHex );

            // Let the recursion begin...
            for( int i = 0; i < neighbors.size(); i++ )
            {
                edgeSignal = getEdgeSignal( currentHex, i ); 
                
                switch (edgeSignal) {
                    case 5 : // Stream, add 1 to cost
                        getAllRetreatHexesInRange( currentHex, retreater, 
                                retreatLimit - 2 );
                        break;
                    case 7 : // Wall - cannot pass
                        break;
                    default : 
                        getAllRetreatHexesInRange( currentHex, retreater,
                                retreatLimit - 1 );
                        break;
                }
            }
        
        } 
    }
    
    /**
     * This method returns the integer code for the retreat case of a retreating 
     * unit (retreatingUnit) moving in a hex (neighbor). This allows us to 
     * determine which level of the retreat hierarchy this move will be, as
     * certain retreat conditions are only allowed if others are unavailable.
     * 1 = Neighbor is vacant, uncontrolled hex
     * 2 = Neighbor is friendly-occupied, uncontrolled hex
     * 3 = Neighbor is friendly-occupied, enemy controlled hex
     * 4 = Neighbor is vacant, enemy controlled hex
     * 5 = neighbor holds enemy units
     * @param retreatingUnit
     * @param neighbor
     * @return retreatCaseCode
     * @author Keith and Shaung
     */
    public static int getRetreatCase(MoveableUnit retreatingUnit, 
            MapHex neighbor )
    {
        // Initialize to zero to indicate if no retreat cases are available.
        int retreatCase = 5;
        
        if( getUnits( neighbor, retreatingUnit ) )
            return retreatCase;
            
        // First case checks to make sure the hex is not controlled AND empty
        if( !isZoneOfControl(neighbor, retreatingUnit) 
            && neighbor.getUnits() == null )
            retreatCase = 1;
       
        // Second case checks to see if the hex contains friendly units  
        // and is not in the zone of control of an enemy.
        if( isFriendlyControlledHex( neighbor, retreatingUnit ) 
            && !isZoneOfControl( neighbor, retreatingUnit ))
            retreatCase = 2;
        
        // Third case checks to see if the hex is in a friendly zone of control
        // that has no enemies in it, but could be in an enemy zone of control
        if( isFriendlyControlledHex( neighbor, retreatingUnit )
            && isZoneOfControl( neighbor, retreatingUnit ) )
            retreatCase = 3;
               
        // Fourth case checks to see if the hex is empty and has no enemies in 
        // it, but is in an enemy zone of control.
        if( neighbor.getUnits() == null 
            && isZoneOfControl( neighbor, retreatingUnit) )
            retreatCase = 4;
        
        return retreatCase;
    }
    
    /**
     * Returns true if there are friendly units in the given hex. Otherwise
     * false.
     * @param sourceHex
     * @param movingUnit
     * @return boolean
     * @author Ian, Keith, Shaung
     */
    public static boolean isFriendlyControlledHex(MapHex sourceHex, 
            MoveableUnit movingUnit)
    {
        ArrayList<String> unitsInHex;
        if(sourceHex != null){
            unitsInHex = sourceHex.getUnitIDs();
        } else {
            return false;
        }
        // check if list is empty
        if( unitsInHex == null || unitsInHex.isEmpty() )
        {
            return false;
        }
        
        //oh my gosh this is so terrible
        int idInNewHex = Integer.parseInt(unitsInHex.get(0).split("#")[0]);
        int idOfCurrent = Integer.parseInt(movingUnit.getID().split("#")[0]);
        
        return idOfCurrent == idInNewHex;
    }
}
