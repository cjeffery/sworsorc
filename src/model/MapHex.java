/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainMapBuilder;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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


public class MapHex extends Hex{
    private String hexName;
    private String terrainKey;
    private String providenceName;
    private boolean cityHex = false;
    private boolean vortexHex = false;
    private boolean castleHex = false;
    private boolean capitalHex = false;
    private boolean townHex = false;
    private int portalHex = 0;
    private HashMap<String, ArrayList<String>> hexEdgeMap = new HashMap<>();
    private ArrayList<String> northList;
    private ArrayList<String> northWestList;
    private ArrayList<String> northEastList;
    private ArrayList<String> southList;
    private ArrayList<String> southEastList;
    private ArrayList<String> southWestList;
    //ArrayList<Integer> hexEdgeAdditions = new ArrayList<>();
    
     /** 
     * This constructor creates a standard hex.
     * all of the IDs are strings.
     * 
     * @param id Unique identifier of the current hex.
     * @param northHexID  Unique identifier of the North neighbor.
     * @param northEastHexID Unique identifier the North East neighbor.
     * @param southEastHexID Unique identifier of the southeast neighbor.
     * @param southHexID Unique identifier of the South neighbor.
     * @param southWestHexID unique identifier of the Southwest neighbor.
     * @param northWestHexID unique identifier of the Northwest neighbor.
     * @param keyTerrain  String.
     * @param edgesOfHex an HashMap of ArrayList<Strings> that contains 
     * the attributes of all 6 hex edges.
     */
    
    public MapHex(String id, String northHexID, String northEastHexID, 
                  String southEastHexID, String southHexID, String southWestHexID, 
                  String northWestHexID, String keyTerrain, String nameProvidence,
                  HashMap<String, ArrayList<String>> edgesOfHex){
        
        super(id, northHexID, northEastHexID, southEastHexID, 
                          southHexID, southWestHexID, northWestHexID);
        
        setHexEdgeList(edgesOfHex);        
        SetKeyterrain(keyTerrain);
        SetProvidenceName(nameProvidence);
    }

    
     /** 
     * This constructor creates a named (non-city/castle) hex. 
     * all of the IDs are strings.
     * 
     * @param id Unique identifier of the current hex.
     * @param northHexID  Unique identifier of the North neighbor.
     * @param northEastHexID Unique identifier the North East neighbor.
     * @param southEastHexID Unique identifier of the southeast neighbor.
     * @param southHexID Unique identifier of the South neighbor.
     * @param southWestHexID unique identifier of the Southwest neighbor.
     * @param northWestHexID unique identifier of the Northwest neighbor.
     * @param keyTerrain  String.
     * @param edgesOfHex an HashMap of ArrayList<Strings> that contains 
     * the attributes of all 6 hex edges.
     * @param nameHex String.
     */
    public MapHex(String id, String northHexID, String northEastHexID, 
                  String southEastHexID, String southHexID, String southWestHexID, 
                  String northWestHexID, String keyTerrain, String nameProvidence, 
                  HashMap<String, ArrayList<String>> edgesOfHex,String nameHex){
        
        super(id, northHexID, northEastHexID, southEastHexID, 
                          southHexID, southWestHexID, northWestHexID);
        
        setHexEdgeList(edgesOfHex);
        SetKeyterrain(keyTerrain);
        SetHexName(nameHex);
        SetProvidenceName(nameProvidence);
    }

    
    
     /** 
     * This constructor creates a city hex.
     * all of the IDs are strings.
     * 
     * @param id Unique identifier of the current hex.
     * @param northHexID  Unique identifier of the North neighbor.
     * @param northEastHexID Unique identifier the North East neighbor.
     * @param southEastHexID Unique identifier of the southeast neighbor.
     * @param southHexID Unique identifier of the South neighbor.
     * @param southWestHexID unique identifier of the Southwest neighbor.
     * @param northWestHexID unique identifier of the Northwest neighbor.
     * @param keyTerrain  String.
     * @param edgesOfHex an HashMap of ArrayList<Strings> that contains 
     * the attributes of all 6 hex edges.
     * @param hexIsCity boolean.
     * @param nameCity String. 
     */
    public MapHex(String id, String northHexID, String northEastHexID, 
                  String southEastHexID, String southHexID, String southWestHexID, 
                  String northWestHexID, String keyTerrain, String nameProvidence, 
                  HashMap<String, ArrayList<String>> edgesOfHex, boolean hexIsCity, 
                  boolean hexIsCapital, boolean hexIsCastle, boolean hexIsTown, String nameHex){
        
        super(id, northHexID, northEastHexID, southEastHexID, southHexID,
                southWestHexID, northWestHexID);
        
        setHexEdgeList(edgesOfHex);
        SetKeyterrain(keyTerrain);
        SetCityHex(hexIsCity);
        this.SetCastleHex(hexIsCastle);
        SetCapitalHex(hexIsCapital);
        SetTownHex(hexIsTown);
        SetHexName(nameHex);
        SetProvidenceName(nameProvidence);
    }

    private void SetCapitalHex(boolean hexIsCapital) {
        this.capitalHex = hexIsCapital;
    }

    private void SetTownHex(boolean hexIsTown) {
        this.townHex = hexIsTown;
    }
    
    private void SetCastleHex(boolean hexIsCastleHex) {
        castleHex = hexIsCastleHex;
    }
    
    /** This constructor creates a vortex hex.
     * 
     * @param id Unique identifier of the current hex.
     * @param northHexID  Unique identifier of the North neighbor.
     * @param northEastHexID Unique identifier the North East neighbor.
     * @param southEastHexID Unique identifier of the southeast neighbor.
     * @param southHexID Unique identifier of the South neighbor.
     * @param southWestHexID unique identifier of the Southwest neighbor.
     * @param northWestHexID unique identifier of the Northwest neighbor.
     * @param keyTerrain String;
     * @param edgesOfHex an HashMap of ArrayList<Strings> that contains 
     * the attributes of all 6 hex edges.
     * @param hexVortex 
     */
    public MapHex(String id, String northHexID, String northEastHexID, 
                  String southEastHexID, String southHexID, String southWestHexID, 
                  String northWestHexID, String keyTerrain, String nameProvidence, 
                  HashMap<String, ArrayList<String>> edgesOfHex, boolean hexVortex){
        
        super(id, northHexID, northEastHexID, southEastHexID, southHexID,
                southWestHexID, northWestHexID);
        
        setHexEdgeList(edgesOfHex);
        SetIsVortex(hexVortex);
        this.SetKeyterrain(keyTerrain);
        SetProvidenceName(nameProvidence);
    }

    /** This constructor creates a numbered portal hex.
     * 
     * @param id Unique identifier of the current hex.
     * @param northHexID  Unique identifier of the North neighbor.
     * @param northEastHexID Unique identifier the North East neighbor.
     * @param southEastHexID Unique identifier of the southeast neighbor.
     * @param southHexID Unique identifier of the South neighbor.
     * @param southWestHexID unique identifier of the Southwest neighbor.
     * @param northWestHexID unique identifier of the Northwest neighbor.
     * @param keyTerrain String.
     * @param edgesOfHex an HashMap of ArrayList<Strings> that contains 
     * the attributes of all 6 hex edges.  
     * @param portalNumber Integer.
     */
    public MapHex(String id, String northHexID, String northEastHexID, 
                  String southEastHexID, String southHexID, String southWestHexID, 
                  String northWestHexID, String keyTerrain, String nameProvidence, 
                  HashMap<String,ArrayList<String>> edgesOfHex, int portalNumber){
        
        super(id, northHexID, northEastHexID, southEastHexID, southHexID,
                southWestHexID, northWestHexID);
        
        this.SetKeyterrain(keyTerrain);
        setHexEdgeList(edgesOfHex);      
        SetPortalNumber(portalNumber);
        SetProvidenceName(nameProvidence);
    }

    private void SetProvidenceName(String nameProvidence) {
        providenceName = nameProvidence;
    }

    private void SetIsVortex(boolean hexVortex) {
        vortexHex = hexVortex;
    }
  
    private void SetPortalNumber(int portalNumber) {
        portalHex = portalNumber;
    }

    private void SetCityHex(boolean hexIsCity) {
        cityHex = hexIsCity;
    }

    private void SetHexName(String nameHex) {
        hexName = nameHex;
    }
    
    private void SetKeyterrain(String keyTerrain) {
        terrainKey = keyTerrain;
    }
    
    
    private void setHexEdgeList(HashMap<String, ArrayList<String>> edgesOfHex) {
        hexEdgeMap = new HashMap<>();
        
        if (edgesOfHex.containsKey("northWestEdge")){
            this.northWestList = new ArrayList(edgesOfHex.get("northWestEdge"));
            hexEdgeMap.put("northWestEdge", northWestList);
        }
        
        if (edgesOfHex.containsKey("northEdge")){
            this.northList = new ArrayList(edgesOfHex.get("northEdge"));
            hexEdgeMap.put("northEdge", northList);
        }
        
        if (edgesOfHex.containsKey("northEastEdge")){
            this.northEastList = new ArrayList(edgesOfHex.get("northEastEdge"));
            hexEdgeMap.put("northEastEdge", northEastList);
        }
        
        if (edgesOfHex.containsKey("southWestEdge")){
            this.southWestList = new ArrayList(edgesOfHex.get("southWestEdge"));
            hexEdgeMap.put("southWestEdge", southWestList);
        }
        
        if (edgesOfHex.containsKey("southEdge")){
            this.southList = new ArrayList(edgesOfHex.get("southEdge"));
            hexEdgeMap.put("southEdge", southList);
        }
        
        if (edgesOfHex.containsKey("southEastEdge")){
            this.southEastList = new ArrayList(edgesOfHex.get("southEastEdge"));
            hexEdgeMap.put("southEastEdge", southEastList);
        }
        
        
        //hexEdgeMap = new HashMap<>();
        //hexEdgeMap = edgesOfHex;
    }
    public String GetProvidenceName(){
        return this.providenceName;
    }
    
    public int GetPortalNumber(){
        return this.portalHex;
    }
    
    public boolean IsPortalHex(){
        if (portalHex > 0)            
          return true;
        else
            return false;
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
    
    public boolean IsCityTownCastel(){
        if (this.capitalHex  || this.castleHex || this.townHex)
            return true;
        return false;
    }
    
    public boolean IsVortexHex(){
        return vortexHex;
    }
    
    public boolean IsCityHex (){
        return cityHex;
    }
  
     /** 
     * Converts the terrain key into a description
     * 
     * @return The name of hex terrain.
     */
    public  String GetTerrainDescription(){
        
        if ("Bl".equals(terrainKey))
            return "Blasted";        
        if ("Br".equals(terrainKey)) 
            return "Bridge Over Water";
        if ("Bro".equals(terrainKey))
            return "Broken";
        if ("Ci".equals(terrainKey))
            return "City";
        if ("Cl".equals(terrainKey))
            return "Clear";
        if ("Cu".equals(terrainKey))
            return "Cultivated";
        if ("D".equals(terrainKey))
            return "Dragon Tunnel Complex";
        if ("Fore".equals(terrainKey))
            return "Forest";
        if ("G".equals(terrainKey))
            return "Glacier";
        if ("K".equals(terrainKey))
            return "Karoo";
        if ("Mrl".equals(terrainKey))
            return "Moat/River/Lake";
        if ("Mo".equals(terrainKey))
            return "Mountains";
        if ("Po".equals(terrainKey))
            return "Portal";
        if ("R".equals(terrainKey))
            return "Rough";
        if ("Sh".equals(terrainKey))
            return "Special Hex";
        if ("Sw".equals(terrainKey))
            return "Swamp";
        if ("V".equals(terrainKey))
            return "Vortex";
        if ("Wo".equals(terrainKey))
            return "Woods";
        
        return null;
    }

    public String GetHexName(){ 
        return hexName;
    }
   
    public ArrayList<String> GetNorthHexSideDescription () {
        ArrayList<String> descriptionsOfHexSide = new ArrayList<>();

         for (String s : hexEdgeMap.get(0)){
             descriptionsOfHexSide.add(GetHexSidDescription(s));
         }
         
         return descriptionsOfHexSide;                 
    }

    public ArrayList<String> GetNorthEastHexSideDescription () {
        ArrayList<String> descriptionsOfHexSide = new ArrayList<>();

         for (String s : hexEdgeMap.get(1)){
             descriptionsOfHexSide.add(GetHexSidDescription(s));
         }
         
         return descriptionsOfHexSide;                 
    }
    
    public ArrayList<String> GetSouthEastHexSideDescription () {
        ArrayList<String> descriptionsOfHexSide = new ArrayList<>();

         for (String s : hexEdgeMap.get(2)){
             descriptionsOfHexSide.add(GetHexSidDescription(s));
         }
         
         return descriptionsOfHexSide;                 
    }
    
    public ArrayList<String> GetSouthHexSideDescription () {
        ArrayList<String> descriptionsOfHexSide = new ArrayList<>();

         for (String s : hexEdgeMap.get(3)){
             descriptionsOfHexSide.add(GetHexSidDescription(s));
         }
         
         return descriptionsOfHexSide;                 
    }
    
    public ArrayList<String> GetSouthWestHexSideDescription () {
        ArrayList<String> descriptionsOfHexSide = new ArrayList<>();

         for (String s : hexEdgeMap.get(4)){
             descriptionsOfHexSide.add(GetHexSidDescription(s));
         }
         
         return descriptionsOfHexSide;                 
    }
    
    public ArrayList<String> GetNorthWestSideDescription () {
        ArrayList<String> descriptionsOfHexSide = new ArrayList<>();

         for (String s : hexEdgeMap.get(5)){
             descriptionsOfHexSide.add(GetHexSidDescription(s));
         }
         
         return descriptionsOfHexSide;                 
    }
    
     /**  
     * Converts the text side key into a description.
     * 
     * @return Hex edge description.
     */ 
    public String GetHexSidDescription(String hexSideCode) {
        if ("Ro".equals(hexSideCode))
            return "Road";
        if ("Ford".equals(hexSideCode))
            return "Ford";
        if ("Br".equals(hexSideCode)) 
            return "Bridge Entrance/Exit";
        if ("Ga".equals(hexSideCode))
            return "Gate";
        if ("Pb".equals(hexSideCode))
            return "Province Border";
        if ("St".equals(hexSideCode))
            return "Stream";
        if ("Tr".equals(hexSideCode))
            return "Trail";
        if ("Wa".equals(hexSideCode))
            return "Wall";
        
        return null;
    }


    
    public String GetTerrainKey(){
        return terrainKey;
        
    }
    
    public ArrayList<String> getNorthHexEdgeCodes () { 
        return hexEdgeMap.get("northEdge");
    }
    
    public ArrayList<String> getNorthEastHexEdgeCodes () { 
        return hexEdgeMap.get("northEastEdge");
    }
    
    public ArrayList<String> getSouthEastHexEdgeCodes () { 
        return hexEdgeMap.get("southEastEdge");
    }
    
    public ArrayList<String> getSouthHexEdgeCodes () { 
        return hexEdgeMap.get("southEdge");
    }
    
    public ArrayList<String> getSoutWesthexEdgeCodes () { 
        return hexEdgeMap.get("southWestEdge");
    }
    
    public ArrayList<String> getNorthWestHexEdgeCodes () { 
        return hexEdgeMap.get("northWestEdge");
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
    public void SetHexEdgeAdditions(int edgedDirection,String hexEdgeCode){
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
