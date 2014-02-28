/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainSwordSorcery;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 *   line 37 may need changing to reflect your local path.
 *   That needs to be fixed at some point in the future.
 * 
 * @author David
 */
public class MainMap {
    private HashMap <String, MapHex> mainMap = new HashMap();
    private String hexNumber, northHexNumber, northEastHexNumber, 
            southEastHexNumber, southHexNumber, southWestHexNumber,
            northWestHexNumber, terrainKey, cityName;
    private boolean cityHex, vortexHex;
    private int portalHex;
    private DocumentBuilderFactory factory; 
    private DocumentBuilder builder;
    private Document doc;
    private File file = new File("resources/MainMap.xml");
    private HashMap<String, ArrayList<String>> edgeDirectionList = new HashMap<>();
    private ArrayList<String> edgeItemList;

    
    private static MainMap INSTANCE;
    
    private MainMap(){
         SetFactory();
        try {
            SetBuilder();
            SetValidateFactory();
            SetDoc();
        
        } catch (ParserConfigurationException|SAXException ex) {
            Logger.getLogger(MainMap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex){
            Logger.getLogger(MainMap.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("You need a resource folder in your projects root directory containing the MainMap.xml.");
        }
        
        BuildmainMap();
        
    }

   
    
    private void BuildmainMap(){
        
        NodeList listOfHex = GetDoc();
       
        //System.out.println("Total number of hexs : " + listOfHex.getLength());
        
        for(int s = 0; s < listOfHex.getLength(); s++){
           Node hex = listOfHex.item(s);
           
            //if(hex.getNodeType() == Node.ELEMENT_NODE )
              //  System.out.println("\nName " + hex.getNodeName()+ s);
        
            NodeList hexList = hex.getChildNodes();
            //System.out.println("Total number of cells : " + hexList.getLength());
            
            for (int i = 0; i < hexList.getLength(); i++){
                Node hexItem = hexList.item(i);
                
                if(hexItem.getNodeType() == Node.ELEMENT_NODE){
                                        
                   if ("hexNumber".equals(hexItem.getNodeName())) 
                       SetHexNumber(hexItem);
                   if ("northHexNumber".equals(hexItem.getNodeName())) 
                       SetNorthHexNumber(hexItem);
                   if ("northEastHexNumber".equals(hexItem.getNodeName())) 
                       SetNorthEastHexNumber(hexItem);
                   if ("southEastHexNumber".equals(hexItem.getNodeName())) 
                       SetSouthEastHexNumber(hexItem);                       
                   if ("southHexNumber".equals(hexItem.getNodeName())) 
                       SetSouthHexNumber(hexItem);
                   if ("southWestHexNumber".equals(hexItem.getNodeName())) 
                       SetSouthWestHexNumber(hexItem);
                   if ("northWestHexNumber".equals(hexItem.getNodeName())) 
                       SetNorthWestHexNumber(hexItem);
                   if ("terrainKey".equals(hexItem.getNodeName())) 
                       SetTerrainKey(hexItem);
                   if ("cityHex".equals(hexItem.getNodeName())) 
                       if ("true".equals(hexItem.getTextContent()))
                       SetCityHex();
                   if ("cityName".equals(hexItem.getNodeName()))
                       SetCityName(hexItem);
                   if ("vortexHex".equals(hexItem.getNodeName())) 
                       if ("true".equals(hexItem.getTextContent()))
                       SetVortexHex();
                   if ("portalHex".equals(hexItem.getNodeName()))
                       SetPortalHex(hexItem);
                   if ("hexEdgeMap".equals(hexItem.getNodeName())){
                       NodeList listOfEdges = hexItem.getChildNodes();
                       
                       for (int j = 0; j < listOfEdges.getLength(); j ++){
                           Node edgeDerection = listOfEdges.item(j);
                           
                           if(edgeDerection.getNodeType() == Node.ELEMENT_NODE){
                               NodeList edgeItems = edgeDerection.getChildNodes();
                               ArrayList<String> edgeList = new ArrayList<>();
                               
                               for (int h = 0; h < edgeItems.getLength(); h++){
                                   Node edgeAttribute = edgeItems.item(h);
                                   
                                   if(edgeAttribute.getNodeType() == Node.ELEMENT_NODE)
                                       edgeList.add(edgeAttribute.getTextContent());
                               }//for (int h = 0; h < edgeItems.getLength(); h++)
                               
                                SetEdgeDirectionList(edgeDerection, edgeList);

                           }
                                          
                       }//for (int j = 0; j < listOfEdges.getLength(); j ++)
                       
                   }//if ("hexEdgeMap".equals(hexItem.getNodeName()))
                
                }//if(hexItem.getNodeType() == Node.ELEMENT_NODE)
            
            }//for(int i = 0; s < listOfHex.getLength(); s++)         
            if (GetPortalHex() >0 ){    
            
            MapHex hexObject = new MapHex(GetHexNumber(), GetNorthHexNumber(), GetNorthEastHexNumber(), 
                    GetSouthEastHexNumber(), GetSouthHexNumber(), GetSouthWestHexNumber(), GetNorthWestHexNumber(), GetTerrainKey(), GetEdgeDirectionList(), GetPortalHex());                      
                SetMainMap(hexObject);
                
            }
            else
                if (GetVortexHex()){
                    MapHex hexObject = new MapHex(GetHexNumber(), GetNorthHexNumber(), GetNorthEastHexNumber(), 
                            GetSouthEastHexNumber(), GetSouthHexNumber(), GetSouthWestHexNumber(), GetNorthWestHexNumber(), GetTerrainKey(), GetEdgeDirectionList(), GetVortexHex());                      
                    SetMainMap(hexObject);
                
                
                }
                else
                    if (GetCityHex()){
                        MapHex hexObject = new MapHex(GetHexNumber(), GetNorthHexNumber(), 
                                GetNorthEastHexNumber(), GetSouthEastHexNumber(), GetSouthHexNumber(), 
                                GetSouthWestHexNumber(), GetNorthWestHexNumber(), GetTerrainKey(), GetEdgeDirectionList(), GetCityHex(), GetCityName());                      
                        SetMainMap(hexObject);
                    }
                    else {
                        MapHex hexObject = new MapHex(GetHexNumber(), GetNorthHexNumber(), GetNorthEastHexNumber(), 
                                GetSouthEastHexNumber(), GetSouthHexNumber(), GetSouthWestHexNumber(), GetNorthWestHexNumber(), GetTerrainKey(), GetEdgeDirectionList());                      
                        SetMainMap(hexObject);
                    }
        }//for(int s = 0; s < listOfHex.getLength(); s++)
        
    }//private void BuildmainMap(){

    private String GetCityName() {
        return cityName;
    }

    private boolean GetCityHex() {
        return cityHex;
    }

    private boolean GetVortexHex() {
        return vortexHex;
    }

    private void SetMainMap(MapHex hexObject) {
        mainMap.put(hexObject.GetID(), hexObject);
    }

    private HashMap<String, ArrayList<String>> GetEdgeDirectionList() {
        return edgeDirectionList;
    }

    private String GetTerrainKey() {
        return terrainKey;
    }

    private String GetNorthWestHexNumber() {
        return northWestHexNumber;
    }

    private String GetSouthWestHexNumber() {
        return southWestHexNumber;
    }

    private String GetSouthHexNumber() {
        return southHexNumber;
    }
    
    private String GetSouthEastHexNumber() {
        return southEastHexNumber;
    }

    private String GetNorthEastHexNumber() {
        return northEastHexNumber;
    }

    private String GetNorthHexNumber() {
        return northHexNumber;
    }

    private int GetPortalHex() {
        return portalHex;
    }

    private void SetEdgeDirectionList(Node edgeDerection, ArrayList<String> edgeList) {
        edgeDirectionList.put(edgeDerection.getNodeName(), edgeList);
    }

    private void SetPortalHex(Node hexItem) throws DOMException, NumberFormatException {
        portalHex = Integer.parseInt(hexItem.getTextContent());
    }

    private void SetVortexHex() {
        vortexHex = true;
    }

    private void SetCityName(Node hexItem) throws DOMException {
        cityName = hexItem.getTextContent();
    }

    private void SetCityHex() {
        cityHex = true;
    }

    private void SetTerrainKey(Node hexItem) throws DOMException {
        terrainKey = hexItem.getTextContent();
    }

    private void SetNorthWestHexNumber(Node hexItem) throws DOMException {
        northWestHexNumber = hexItem.getTextContent();
    }

    private void SetSouthWestHexNumber(Node hexItem) throws DOMException {
        southWestHexNumber = hexItem.getTextContent();
    }

    private void SetSouthHexNumber(Node hexItem) throws DOMException {
        southHexNumber = hexItem.getTextContent();
    }

    private void SetSouthEastHexNumber(Node hexItem) throws DOMException {
        southEastHexNumber = hexItem.getTextContent();
    }

    private void SetNorthEastHexNumber(Node hexItem) throws DOMException {
        northEastHexNumber = hexItem.getTextContent();
    }

    private void SetNorthHexNumber(Node hexItem) throws DOMException {
        northHexNumber = hexItem.getTextContent();
    }

    private void SetHexNumber(Node hexItem) throws DOMException {
        hexNumber =  hexItem.getTextContent();
    }

    private String GetHexNumber() {
        return hexNumber;
    }

    private NodeList GetDoc() {
        return doc.getElementsByTagName("hex");
    }
        
    public MapHex GetMapHex (String id){
        return mainMap.get(id);
    }
    
     private void SetDoc() throws IOException, SAXException {
        doc = builder.parse(file);
    }

    private void SetValidateFactory() {
        factory.setValidating(true);
    }

    private void SetBuilder() throws ParserConfigurationException {
        builder = factory.newDocumentBuilder();
    }

    private void SetFactory() {
        factory = DocumentBuilderFactory.newInstance();
    }
    
    public static MainMap GetMainMap(){
      if (INSTANCE == null)
          INSTANCE =  new  MainMap();
      return INSTANCE;
  }
    
}


