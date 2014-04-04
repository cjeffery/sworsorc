package sshexmap;

//package MainMapBuilder;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ssterrain.HexEdge;
import ssterrain.HexEdgeType;
import ssterrain.ImprovedTerrainType;
import ssterrain.TerrainType;
import Units.*;

/*******************************************************************************
 * 
 *   This class defines a map hex. All the attributes of a map hex is contained
 *   here, such as terrain key, portal number, vortex hex,  etc.
 * 
 *   Hex edges can have multiple attributes. For example a single hex edge could
 *   contain a Province border, a bridge exit, a shoreline, and a gate.
 *   
 *   @author David Klingenberg 2/25/2014
 * 
 ******************************************************************************/

import ssterrain.*;
import Units.*;
public class MapHex extends Hex{
    /**
     * Various attributes!
     */
    private String hexName;
    private TerrainType terrainType;
    private ArrayList<ImprovedTerrainType> improvements;
    private String provinceName;
    private boolean cityHex = false;
    private boolean vortexHex = false;
    private boolean castleHex = false;
    private boolean capitalHex = false;
    private boolean townHex = false;
    private int portalHex = 0;
    
    /* Hey look units! TODO: untested */
    
    /**
     * An array of size 6 of HexEdge objects
     */
    private ArrayList<HexEdge> edgeList;

    /**
     * Don't actually use this constructor >_> except for mockups
     * bad or confusing things could happen
     */
    public MapHex() {
        edgeList = new ArrayList<HexEdge>(6);
        improvements = new ArrayList<ImprovedTerrainType>();
    }
    
    /** 
     * This constructor constructs a MapHex from an XML node
     * @param hex The XML node
     */
    public MapHex(Node hex) {
        edgeList = new ArrayList<HexEdge>(6);
        improvements = new ArrayList<ImprovedTerrainType>();
        NodeList hexList = hex.getChildNodes();
        for(int i = 0; i < hexList.getLength(); i++) {
            Node hexItem = hexList.item(i);
            if(hexItem.getNodeType() != Node.ELEMENT_NODE)
                continue;
            String contents = hexItem.getTextContent();
            
            /**
             * Giant switch statements checks for possible XML sub-nodes
             */
            switch(hexItem.getNodeName()) {
                case "hexNumber":
                    SetID(contents);
                    //edges can't be set up until ID is known
                    //so set them up here
                    for(int j = 0; j < 6; j++) {
                        //if the Edge already exists use it,
                        MapHex neighbor = this.getNeighbor(j);
                        if(neighbor != null) {
                            edgeList.add(j, neighbor.getEdge( (j+3) % 6 ));
                        }
                        else
                            edgeList.add(j,  new HexEdge(GetID(), j));
                    }
                    
                    break;
                case "terrainKey":
                    terrainType = TerrainType.makeTerrainType(contents);
                    //FIXME could be more than one improvement
                    ImprovedTerrainType improvement = ImprovedTerrainType.makeImprovement(contents, this);
                    if(improvement != null)
                        improvements.add(improvement);
                    break;
                case "cityHex":
                    cityHex = contents.equals("true");
                    break;
                case "hexName":
                    hexName = contents;
                    break;
                case "vortexHex":
                    vortexHex = contents.equals("true");
                    break;
                case "portalHex":
                    portalHex = Integer.parseInt(contents);
                    break;
                case "provinceName": //FIXME typo in XML
                    provinceName = contents;
                    break;
                case "castleHex":
                    castleHex = contents.equals("true");
                    break;
                case "townHex":
                    townHex = contents.equals("true");
                    break;
                case "hexEdgeMap":
                    //iterate over edges
                    NodeList listOfEdges = hexItem.getChildNodes();
                    for (int j = 0; j < listOfEdges.getLength(); j ++){
                        //determine the direction
                        Node edgeDir = listOfEdges.item(j); 
                        int dir = 0;
                        switch(edgeDir.getNodeName()) {
                            case "northEastEdge": dir = 0; break;
                            case "northEdge":     dir = 1; break;
                            case "northWestEdge": dir = 2; break;
                            case "southWestEdge": dir = 3; break;
                            case "southEdge":     dir = 4; break;
                            case "southEastEdge": dir = 5; break;                                                         
                        }
                        if(edgeDir.getNodeType() != Node.ELEMENT_NODE)
                            continue;
                        NodeList edgeItems = edgeDir.getChildNodes();
                        
                        //iterate through the edge elements
                        for (int k = 0; k < edgeItems.getLength(); k++){
                            Node attr = edgeItems.item(k);
                            if(attr.getNodeType() == Node.ELEMENT_NODE) {
                                String s = attr.getTextContent();
                                EdgeElement e = EdgeElement.makeEdgeElement(s);
                                if(e != null) {
                                    addEdgeElement(e, dir);
                                }
                            }
                        }
                        
                    }
                    break;
                case "default":
                    System.out.println("uh oh :(");
            }
        }
    }
    
    /**
     * Uses the MainMap singleton to get the neighbor in the specified direction
     * @param dir the direction
     * @return the neighbor or null
     */
    public final MapHex getNeighbor(int dir) {
        // Get the neighboring hex, via edge number. 
        MapHex tempHex = MainMap.GetInstance().GetHex( GetNeighborID(dir) );
        // Check that it's a valid hex, although still returns null if not.
        if( tempHex != null )
            return tempHex;
        else
            return null;
    }
    
     /**
     * This method will allow the addition of new conditions on hex edges. These 
     * additions can be caused by spells affecting hexes.  For example the 
     * wall spell.
     * 
     * Note: This method is not fully implemented and needs testing.  
     * 
     * @param edgedDirection an integer that reflects a hex face.
     * @param hexEdgeCode 
     * Commented out for now because of hex edge changes
     */
   // public void SetHexEdgeAdditions(int edgedDirection, String hexEdgeCode){
        //hexEdgeMap.get(edgedDirection).add(hexEdgeCode);      
    //}
    
    /**
     * Removes any modifications to hex edges that are caused by spells that 
     * have expired. 
     * 
     * This method needs implementation.
     * Commented out for now because of Hex Edge changes
     */    
    //public void RemoveHexEdgeAdditions (int edgedDirection,String hexEdgeCode ) { 
        //to do
    //}
    
    
    /**      
     * Modifies the train to reflect the effects of spells or random events.
     * 
     * This method needs implementation.
     * Commented out for now because of Hex Edge changes
     */
    //public void ModifyTerrainCode (String keyterrain) {
        //to do.
    //}     

    /**
     * Helper function to
     * Add an EdgeElement between this hex and the hex in direction dir
     * @param e The EdgeElement
     * @param dir direction from current hex
     */
    public void addEdgeElement(EdgeElement e, int dir){
        edgeList.get(dir).put(e);
    }
    
    /**
     * Set the base terrain type (not improvements)
     */
    public void setTerrainType(TerrainType newTerrainType){
        this.terrainType = newTerrainType;
    }
    
    /**
     * Add an "improved" terrain type (not base terrain type)
     */
    public void addImprovement(ImprovedTerrainType newImprovement){
        improvements.add(newImprovement);
    }
    
    /**
     * Commented out for now, because of Hex Edge changes
     */
    //public void removeEdge(HexEdge deadEdge){
        //edges.remove(deadEdge);
    //}

    /**
     * 
     * @return terrainType 
     */
    public TerrainType getTerrainType(){
        return terrainType;
    }
    
    /**
     * Remove the specified improvement
     * @param deadImprovement a reference to the improvement in the list to
     *                        remove
     */
    public void removeImprovement(ImprovedTerrainType deadImprovement){
        improvements.remove(deadImprovement);
    }
    
    /**
     * Get all the "terrain improvements" (not base terrain type)
     */
    public ArrayList<ImprovedTerrainType> getImprovements(){
        return improvements;
    }
    
    /**
     * Get the HexEdge at the particular direction
     * @param dir direction. 0 = NE, 1 = N, 2 = NW, ... 5 = SE
     * @return The edge
     */
    public HexEdge getEdge(int dir){
        return edgeList.get(dir);
    }

    /**
     * Sets the hexes province name
     */
    private void SetProvinceName(String nameProvince) {
        provinceName = nameProvince;
    }

    /**
     * Sets whether or not the hex is a vortex hex
     */
    private void SetIsVortex(boolean hexVortex) {
        vortexHex = hexVortex;
    }
    
    /**
     * @return The province name of the hex
     */
    public String GetProvinceName(){
        return this.provinceName;
    }
    
    /**
     * @return The portal number (1-6), or -1 if it's not a portal
     */
    public int GetPortalNumber() {
        return portalHex;        
    }
    
    /**
     * @return whether or not the hex is a portal
     */
    public boolean IsPortalHex(){
        return portalHex > 0;
    }
    
    /**
     * @return whether or not the hex is a castle
     */
    public boolean IsCastleHex(){
        return castleHex;
    }
    
    /**
     * @return whether or not the hex is a capital
     */
    public boolean IsCapitalHex(){
        return capitalHex;
    }
    
    /**
     * @return whether or not the hex is a town
     */
    public boolean IsTownHex(){
        return townHex;
    }
    
    /**
     * @return whether or not the hex is a city, castle, or town
     */
    public boolean IsCityTownCastle(){
        return capitalHex  || castleHex || townHex;
    }
    
    /**
     * @return whether or not the hex is a vortex
     */
    public boolean IsVortexHex(){
        return vortexHex;
    }
    
    /**
     * @return whether or not the hex is a city 
     */
    public boolean IsCityHex (){
        return cityHex;
    }
  
    /**
     * @return If the hex has a special name (like Mt. Greymoor) this will
     *         return it.
     */
    public String GetHexName(){ 
        return hexName;
    }

    /**
     * This will be deleted, use getMoveCost 
     * This function is currently incomplete, just copied pretty much verbatim
     * Needs to take hex edge into account
     * 
     * @param unit The unit that is moving
     * @param dir The direction the unit is moving, 0 = NE, 1 = N, etc.
     *                 1
     *                2 0
     *                3 5
     *                 4
     * @return the cost to move the unit on to this hex
     * 
     * Note - This may not be needed due to the new methods below.
     * 
     */
    /*public double getMovementCost(MoveableUnit unit, int dir){
        double move = terrainType.getMovementCost(unit); // 
        double override = 100;
        if(improvements.size() > 0)
            for(int i = 0; i < improvements.size(); i++){
                move += improvements.get(i).getMovementCost(unit);
                if(improvements.get(i).getMovementOverride(unit) > 0.0)
                    if(improvements.get(i).getMovementOverride(unit) < override)
                        override = improvements.get(i).getMovementOverride(unit);
            }
        if(override > 0 && override < 100) move = override;
        return move;
    }*/
    
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
     */
    public void getValidMoves( MoveableUnit movingUnit, MapHex startHex, 
            double moveAllowance, ArrayList<MapHex> validHexes )
    {
        double edgeCost = 0;
        double moveCost = 0;
        
        ArrayList<MapHex> neighbors = new ArrayList<MapHex>();
        // For each hex edge, 0-5, get the neighboring hex, if it's valid
        for(int i = 0; i < 6; i++ )
        {
            // Check if the hex is valid, null returned if hex neighbor no exist
            if( startHex.getNeighbor( i ) != null )
            {
                // add each valid neighbor to neighbors
                neighbors.add( startHex.getNeighbor( i ) );
            }
        }
        
        // The recursion loop
        for( int i = 0; i < neighbors.size(); i++ )
        {
            // get the edgecost as returned from method for each iteration of 
            // loop and effectively each hex neighbor
            edgeCost = getEdgeCost( startHex, neighbors.get(i) );
            // If edgeCost is 1, the edge represents a road/trail
            if( edgeCost == 1 )
            {
                // If moveAllowance - 1 allows for the move, recurse accordingly
                if( moveAllowance - edgeCost >= 0 )
                {
                    // Make sure the neighbor is not already in the list
                    if( validHexes.contains( neighbors.get(i) ) )
                        neighbors.remove(i);
                    else 
                    {
                        // add the neighbor hex to valid hexes
                        validHexes.add( neighbors.get(i) );
                        /* Recursion Step - Subtract the edge cost for 
                         * travelling over a road/trail.
                         */
                        getValidMoves( movingUnit, neighbors.get(i), 
                                moveAllowance - edgeCost, validHexes );
                    }
                }
            } 
            /* If edgecost is 0, then the move is legal, if not affordable. 
             * Determine move cost and recurse, if applicable.
             */
            else if( edgeCost == 0 )
            {
                /*
                 * Get the movement cost of moving to neighbor i from current
                 * location.
                 */
                moveCost = getMoveCost( movingUnit, startHex, neighbors.get(i));
                if( moveAllowance - moveCost >= 0 )
                {
                    // Make sure the neighbor is not already in the list
                    if( validHexes.contains( neighbors.get(i) ) )
                        neighbors.remove(i);
                    else 
                    {
                        // add the neighbor hex to valid hexes
                        validHexes.add( neighbors.get(i) );
                        /* Recursion Step - Subtract the move cost for moving 
                         * into the dest hex.
                         */
                        getValidMoves( movingUnit, neighbors.get(i), 
                                moveAllowance - edgeCost, validHexes );
                    }
                }
            }
            else if( edgeCost == -1 )
            { 
                neighbors.remove(i);
            }
        }
        
    }
    
    /**
     * Keith and Ian
     * This method should return the cost of moving from sourceHex to 
     * destinationHex for the given unit, movingUnit. This is a helper function
     * for the getValidMoves() method above.
     * @param movingUnit
     * @param sourceHex
     * @param destinationHex
     * @return moveCost
     */
    public static double getMoveCost( MoveableUnit movingUnit, MapHex sourceHex,
                                        MapHex destinationHex )
    {
        // TODO
        double moveCost = 1;
        
        return moveCost;
    }
    
    /**
     * Keith and Ian
     * This method returns -1 if the move between source and dest. is invalid,
     * 0 if there is no road or trail between source and dest., and 
     * 1 if there IS a road/trail linking the hexes. This is a helper for the 
     * getValidMoves() method.
     * @param sourceHex
     * @param destinationHex
     * @return edgeCost 
     */
    public static double getEdgeCost( MapHex sourceHex, MapHex destinationHex )
    {
        // TODO
        double edgeCost = 1;
        
        return edgeCost;
    }
    
    /**
     * Keith and Ian
     * This method takes a possible destination hex and a unit, who's valid moves
     * are being calculated, and determines if there are enemy units in that 
     * hex. If there are, the move is illegal and false is returned. Otherwise,
     * true is returned. This method is dependent upon the id numbering scheme
     * of moveable units including a reference to players and the ability to 
     * access any units residing in the destinationHex from that hex. 
     * @param destinationHex
     * @param movingUnit
     * @return enemyOccupied
     */
    public static boolean isEnemyOccupiedHex( MapHex destinationHex, 
                                            MoveableUnit movingUnit )
    {
        // TODO
        boolean enemyOccupied = false;

        return enemyOccupied;
    }
    
    /**
     * TODO: This will probably need some changes?
     * wrt having both an attacker, defender, what hex they're both on
     * and the hex edge between them
     * @param unit
     * @return 
     */
    public double getCombatMultiplier(ArmyUnit unit){
        double mult = 1;
        mult *= terrainType.getCombatMultiplier(unit);
        if(improvements.size() > 0)
            for(int i = 0; i < improvements.size(); i++)
                mult *= improvements.get(i).getCombatMultiplier(unit);
        return mult;
    }
    
    /**
     * helper function around UnitPool. HEY KINDA LIKE PART OF A FASCADE :D
     * See documentation for getUnitsInHex in UnitPool.java
     * (or write it if it doesn't exist)
     * @return arraylist of Unit IDs in the current hex
     */
    public ArrayList<String> getUnitIDs(){
        return UnitPool.getInstance().getUnitsInHex( GetID() );
    }
    
    /**
     * helper function around UnitPool. HEY KINDA LIKE PART OF A FASCADE :D
     * See documentation for getUnit in UnitPool.java
     * (or write it if it doesn't exist)
     * @return arraylist of Unis in the current hex
     */
    public ArrayList<ArmyUnit> getUnits() {
        ArrayList<ArmyUnit> units = new ArrayList<ArmyUnit>();
        ArrayList<String> ids = getUnitIDs();
        if(ids == null)
            return null;
        for(String id : ids)
            units.add( UnitPool.getInstance().getUnit(id) );
        return units;
    }
}
