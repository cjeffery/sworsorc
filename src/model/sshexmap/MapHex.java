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
    private HexEdge[] edgeList = new HexEdge[6];
    private HashMap<String, ArrayList<String>> hexEdgeMap = new HashMap<>();
    //ArrayList<Integer> hexEdgeAdditions = new ArrayList<>();

    public MapHex(Node hex) {
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
                    break;
                case "terrainKey":
                    terrainType = TerrainType.makeTerrainType(contents);
                    //FIXME could be more than one improvement
                    //yeah I know it's bad passing 'this' from a constructor, 
                    //but it should be OK in this instance
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
                        Node edge = listOfEdges.item(j); 
                        if(edge.getNodeType() != Node.ELEMENT_NODE)
                            break;
                         
                        NodeList edgeItems = edge.getChildNodes();
                        ArrayList<String> attrList = new ArrayList<>();
                        //iterate over attributes of given edge
                        int[] xmlOrder = {1, 0, 5, 4, 3, 2};
                        for (int h = 0; h < edgeItems.getLength(); h++){
                            Node attr = edgeItems.item(h);

                            if(attr.getNodeType() == Node.ELEMENT_NODE)
                                attrList.add(attr.getTextContent());
                        }//for (int h = 0; h < edgeItems.getLength(); h++)
                        //FIXME edgeList[xmlOrder[j]] = new HexEdge(attrList);                       
                    }//for (int j = 0; j < listOfEdges.getLength(); j ++)
                    break;
                case "default":
                    System.out.println("uh oh :(");
            }
        }
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


    public void addEdge(HexEdge newEdge){
        //edges.add(newEdge);
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
    
    /* TODO: what does this method suppoed to do */
    public boolean checkIfCrossed(ArrayList<HexEdgeType> list){
        /*for(int l = 0; l < list.size(); l++){
            for(int e = 0; e < edges.size(); e++){
                if(edges.get(e).getEdgeType().equals(list.get(l)))return true;
            }
        }*/
        return false;
    }
    
    public ArrayList<HexEdgeType> getEdgeType(int edge){
        /*ArrayList<HexEdgeType> thisEdge = new ArrayList<HexEdgeType>();
        for(int e = 0; e < edges.size(); e++){
            if(edges.get(e).getEdge() == edge) thisEdge.add(edges.get(e).getEdgeType());
        }
        return thisEdge;*/
        return null;
    }
    
    public double getMovementCost(MoveableUnit unit){
        double move = terrainType.getMovementCost(unit);
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
    }
    
    public double getCombatMultiplier(ArmyUnit unit){
        double mult = 1;
        mult *= terrainType.getCombatMultiplier(unit);
        if(improvements.size() > 0)
            for(int i = 0; i < improvements.size(); i++)
                mult *= improvements.get(i).getCombatMultiplier(unit);
        return mult;
    }
}
