package sshexmap;

import java.util.ArrayList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ssterrain.TerrainType;

/**
 *
 * @author David
 */
public class DiplomacyHex extends Hex {
    private boolean playerHex = false;
    private boolean neutralHex = false;

    public DiplomacyHex(Node hex) {
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
                case "specialHex":
                    if(contents.equals("1"))
                        SetPlayerHex();
                    if(contents.equals("2"))
                        SetNeutralHex();
                    break;
            }
        }
    }
    
    public DiplomacyHex(String hexNumber, String specialHex) {
        SetID(hexNumber);
        if(specialHex.equals("1"))
            SetPlayerHex();
        if(specialHex.equals("2"))
            SetNeutralHex();       
    }
        
    private void SetPlayerHex(){
        playerHex = true;
    }
    
    private void SetNeutralHex(){
        neutralHex = true;
    } 
    
    public boolean GetIsPlayerHex(){
        return playerHex; 
    }
        
    public boolean GetIsNeutralHex(){
        return neutralHex;
    }
        
}
