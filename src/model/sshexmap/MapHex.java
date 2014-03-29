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
 *   contain a Providence border, a bridge exit, a shoreline, and a gate.
 *   
 *   @author David Klingenberg 2/25/2014
 * 
 ******************************************************************************/

import ssterrain.*;
import Units.*;
public class MapHex extends Hex{
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
    private ArrayList<HexEdge> edgeList;

    public MapHex() {    }
    
    /** 
     * This constructor constructs a MapHex from an XML node
     * @param hex The XML node
     */
    public MapHex(Node hex) {

        this.edgeList = new ArrayList<HexEdge>(6);
        
        improvements = new ArrayList<ImprovedTerrainType>();
        NodeList hexList = hex.getChildNodes();
        for(int i = 0; i < hexList.getLength(); i++) {
            Node hexItem = hexList.item(i);
            if(hexItem.getNodeType() != Node.ELEMENT_NODE)
                continue;
            String contents = hexItem.getTextContent();
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
                            edgeList.add(j,  new HexEdge());
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
                case "providenceName": //FIXME typo in XML
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
                                    addEdge(e, dir);
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
        return MainMap.GetInstance().GetHex( GetNeighborID(dir) );
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
     */
    public void SetHexEdgeAdditions(int edgedDirection, String hexEdgeCode){
        //hexEdgeMap.get(edgedDirection).add(hexEdgeCode);      
    }
    
    /**
     * Removes any modifications to hex edges that are caused by spells that 
     * have expired. 
     * 
     * This method needs implementation.
     */    
    public void RemoveHexEdgeAdditions (int edgedDirection,String hexEdgeCode ) { 
        //to do
    }
    
    
    /**      
     * Modifies the train to reflect the effects of spells or random events.
     * 
     * This method needs implementation.
     */
    public void ModifyTerrainCode (String keyterrain) {
        //to do.
    }     

    /**
     * Add an EdgeElement between this hex and the hex in direction dir
     * @param e The EdgeElement
     * @param dir direction from current hex
     */
    public void addEdge(EdgeElement e, int dir){
        edgeList.get(dir).put(e);
    }
    
    public void setTerrainType(TerrainType newTerrainType){
        this.terrainType = newTerrainType;
    }
    
    public void addImprovement(ImprovedTerrainType newImprovement){
        improvements.add(newImprovement);
    }
    
    public void removeEdge(HexEdge deadEdge){
        //edges.remove(deadEdge);
    }
    
    public TerrainType getTerrainType(){
        return terrainType;
    }
    
    public void removeImprovement(ImprovedTerrainType deadImprovement){
        improvements.remove(deadImprovement);
    }
    
    public ArrayList<ImprovedTerrainType> getImprovements(){
        return improvements;
    }
    
    
    public HexEdge getEdge(int dir){
        return edgeList.get(dir);
    }

    private void SetProvinceName(String nameProvidence) {
        provinceName = nameProvidence;
    }

    private void SetIsVortex(boolean hexVortex) {
        vortexHex = hexVortex;
    }
    
    public String GetProvinceName(){
        return this.provinceName;
    }
    
    public int GetPortalNumber() {
        return portalHex;        
    }
    
    public boolean IsPortalHex(){
        return portalHex > 0;
    }
    
    public boolean IsCastleHex(){
        return castleHex;
    }
    
    public boolean IsCapitalHex(){
        return capitalHex;
    }
    
    public boolean IsTownHex(){
        return townHex;
    }
    
    public boolean IsCityTownCastle(){
        return capitalHex  || castleHex || townHex;
    }
    
    public boolean IsVortexHex(){
        return vortexHex;
    }
    
    public boolean IsCityHex (){
        return cityHex;
    }
  
    public String GetHexName(){ 
        return hexName;
    }

    public TerrainType GetTerrain() {
        return terrainType;
    }
    
}
