/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainSwordSorcery;

import java.io.File;
import java.io.IOException;
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
 * @author David
 */
public class DiplomacyMap {
    private HashMap <String, DiplomacyHex> DiplomacyMap = new HashMap();
    private String id, nid, neid, seid, sid, swid, nwid, scode;
    private DocumentBuilderFactory factory; 
    private DocumentBuilder builder;
    private Document doc;
    private File file = new File("C:\\Users\\David\\Documents\\GitHub\\sworsorc\\doc\\hw3\\A\\Menus\\KlingenergMenu\\Sword-Sorcery\\src\\MainSwordSorcery\\DiplomacyMap.xml");
    //private DiplomacyHex hexObject;
    
    private static DiplomacyMap INSTANCE;
    
    private DiplomacyMap(){
         factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
            factory.setValidating(true);
            doc = builder.parse(file);
        
        } catch (ParserConfigurationException|SAXException|IOException ex) {
            Logger.getLogger(DiplomacyMap.class.getName()).log(Level.SEVERE, null, ex);
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
                    
                    
                   if ("id".equals(hexItem.getNodeName())) 
                       id =  hexItem.getTextContent();
                   if ("nid".equals(hexItem.getNodeName())) 
                       nid = hexItem.getTextContent();
                   if ("neid".equals(hexItem.getNodeName())) 
                       neid = hexItem.getTextContent();
                   if ("seid".equals(hexItem.getNodeName())) 
                       seid = hexItem.getTextContent();
                   if ("sid".equals(hexItem.getNodeName())) 
                       sid = hexItem.getTextContent();
                   if ("swid".equals(hexItem.getNodeName())) 
                       swid = hexItem.getTextContent();
                   if ("nwid".equals(hexItem.getNodeName())) 
                       nwid = hexItem.getTextContent();
                   if ("scode".equals(hexItem.getNodeName())) 
                       scode = hexItem.getTextContent();
                   
                   //System.out.println("child name: "  + hexItem.getNodeName() + "\tValue : " + hexItem.getTextContent());
                  
                
                }//if(hexItem.getNodeType() == Node.ELEMENT_NODE)
                
            }//for (int i = 0; i < hexList.getLength(); i++)

            //System.out.println("child name data: " + id + " " + nid + " " + sid + " " + scode);
            
            DiplomacyHex hexObject;
            
            
               hexObject = new DiplomacyHex(id,nid,neid,seid,sid,swid,nwid,scode);                      
            
            //System.out.println("Hex object id : " + hexObject.GetID());
            
            DiplomacyMap.put(hexObject.GetID(), hexObject);
            
            
            
        }//for(int s = 0; s < listOfHex.getLength(); s++)         
        
        //DiplomacyHex tempHexObject;
        //tempHexObject = DiplomacyMap.get("101");
        //System.out.println("test id :" + tempHexObject.GetID());
        
    }//BuildDiplomacyMap
        
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
    
    public boolean GetIsNeturalHex(String id){
        
        return DiplomacyMap.get(id).GetIsNeturalHex();
    }
}


