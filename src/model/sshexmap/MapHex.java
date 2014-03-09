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
import ssterrain.Unit;

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
                    break;
                case "cityHex":
                    cityHex = contents.equals("true");
                    break;
                case "hexName":
                    hexName = contents;
                case "vortexHex":
                    vortexHex = contents.equals("true");
                case "portalHex":
                    portalHex = Integer.parseInt(contents);
                case "providenceName": //FIXME typo in XML
                    provinceName = contents;
                case "castleHex":
                    castleHex = contents.equals("true");
                case "townHex":
                    townHex = contents.equals("true");
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
}
