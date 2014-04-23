/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainSwordSorcery;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
public class DiplomacyMap {
    private HashMap <String, DiplomacyHex> DiplomacyMap = new HashMap();
    private String hexNumber, northHexNumber, northEastHexNumber, 
            southEastHexNumber, southHexNumber, southWestHexNumber,
            northWestHexNumber, specialCode; // specialCode determines if it is a player or neutral hex.
    private DocumentBuilderFactory factory; 
    private DocumentBuilder builder;
    private Document doc;
    private File file = new File("resources/DiplomacyMap.xml");
    
//private DiplomacyHex hexObject;
    
    private static DiplomacyMap INSTANCE;
    
    private DiplomacyMap(){
         factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
            factory.setValidating(true);
            doc = builder.parse(file);
        
        } catch (ParserConfigurationException|SAXException ex) {
            Logger.getLogger(DiplomacyMap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex){
            Logger.getLogger(DiplomacyMap.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("You need a resource folder in your projects root directory containing the DiplomacyMap.xml.");
        }
        
        BuildDiplomacyMap();
        
    }//diplomacy map
    
    public static DiplomacyMap GetDiplomacyMap(){
      if (INSTANCE == null)
          INSTANCE =  new  DiplomacyMap();
      return INSTANCE;
  }
    
    private void BuildDiplomacyMap(){
        
        NodeList listOfHex = doc.getElementsByTagName("hex");
       
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
                       hexNumber =  hexItem.getTextContent();
                   if ("northHexNumber".equals(hexItem.getNodeName())) 
                       northHexNumber = hexItem.getTextContent();
                   if ("northEastHexNumber".equals(hexItem.getNodeName())) 
                       northEastHexNumber = hexItem.getTextContent();
                   if ("southEastHexNumber".equals(hexItem.getNodeName())) 
                       southEastHexNumber = hexItem.getTextContent();                       
                   if ("southHexNumber".equals(hexItem.getNodeName())) 
                       southHexNumber = hexItem.getTextContent();
                   if ("southWestHexNumber".equals(hexItem.getNodeName())) 
                       southWestHexNumber = hexItem.getTextContent();
                   if ("northWestHexNumber".equals(hexItem.getNodeName())) 
                       northWestHexNumber = hexItem.getTextContent();
                   if ("specialHex".equals(hexItem.getNodeName())) 
                       specialCode = hexItem.getTextContent();
                
                }//if(hexItem.getNodeType() == Node.ELEMENT_NODE)
                
            }//for (int i = 0; i < hexList.getLength(); i++)

            DiplomacyHex hexObject = new DiplomacyHex(hexNumber,northHexNumber,northEastHexNumber,southEastHexNumber,southHexNumber,southWestHexNumber,northWestHexNumber,specialCode);                      
                        
            DiplomacyMap.put(hexObject.GetID(), hexObject);
                        
        }//for(int s = 0; s < listOfHex.getLength(); s++)         
        
        
        
    }//BuildDiplomacyMap
    
    public DiplomacyHex GetDiplomacyHex(String id){
        return DiplomacyMap.get(id);
    }
        
    public String GetNorthNeighborID(String id){
        
        return DiplomacyMap.get(id).GetIDofNorthHexagon();
    }
    
    public String GetNorthEastNeighborID(String id){
        
        return DiplomacyMap.get(id).GetIDofNorthEastHexagon();
    }
    
    public String GetSouthEastNeighborID(String id){
        
        return DiplomacyMap.get(id).GetIDofSouthEastHexagon();
    }
    
    public String GetSouthNeighborID(String id){
        
        return DiplomacyMap.get(id).GetIDofSouthHexagon();
    }
    
    public String GetSouthWestNeighborID(String id){
        
        return DiplomacyMap.get(id).GetIDofSouthWestHexagon();
    }
    
    public String GetNorthWestNeighborID(String id){
        
        return DiplomacyMap.get(id).GetIDofNorthWestHexagon();
    }
    
    public boolean GetIsPlayerHex(String id){
        
        return DiplomacyMap.get(id).GetIsPlayerHex();
    }
    
    public boolean GetIsNeutralHex(String id){
        
        return DiplomacyMap.get(id).GetIsNeutralHex();
    }
}


