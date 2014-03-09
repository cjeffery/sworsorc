package sshexmap;

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


/* ABC for diplomacy and game map, so they can both be drawn */
public abstract class HexMap {
    HashMap <String, Hex> hexHash = new HashMap();
    private DocumentBuilderFactory factory; 
    private DocumentBuilder builder;
    private Document doc;
    private File file;// = new File("resources/MainMap.xml");
    
    HexMap(String filename) {
        file = new File(filename);
        factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
            factory.setValidating(true);
            doc = builder.parse(file);
        
        } catch (ParserConfigurationException|SAXException ex) {
            Logger.getLogger(DiplomacyMap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex){
            Logger.getLogger(DiplomacyMap.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not find " + filename);
        }        
    }
    
    void BuildMap() {        
        NodeList listOfHex = doc.getElementsByTagName("hex");
        for(int s = 0; s < listOfHex.getLength(); s++){
            Node hex = listOfHex.item(s);
            NodeList hexList = hex.getChildNodes();
            for (int i = 0; i < hexList.getLength(); i++) {
                Node hexItem = hexList.item(i);
                Hex h = makeHex(hexItem);
                hexHash.put(h.GetID(), h);
            }
        }     
    }

    public Hex GetHex(String id){
        return hexHash.get(id);
    }    
    
    public Hex GetHex(int x, int y){
        return GetHex(GetIDFromCoords(x, y));
    }  

    public abstract int GetRows();
    public abstract int GetColumns();
    abstract Hex makeHex(Node h);
    

    //if 0 first row is like the 1st or 3rd rows in the game map (high or even)
    //if 1 first row is like the 2nd or 4th row in the game map (low or odd)
    public abstract boolean LowFirstRow();
    
    public static String GetIDFromCoords(int x, int y) {
        return String.format("%02d%02dfffff", x+1, y+1);
    }
  
}
