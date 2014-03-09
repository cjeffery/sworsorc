/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sshexmap;

/**
 *
 * @author David
 */
public class DiplomacyHex extends Hex {
    private boolean playerHex = false;
    private boolean neturalHex = false;
    
    public DiplomacyHex(String id, String northHexID, String northEastHexID, 
                            String southEastHexID, String southHexID, String southWestHexID, 
                            String northWestHexID, String specialHexCode) {
        super(id, northHexID, northEastHexID, southEastHexID, southHexID, southWestHexID, northWestHexID);
        
        if ("1".equals(specialHexCode))
            SetPlayerHex();
        if ("2".equals(specialHexCode))
            SetNeturalHex();
    }
    
    public DiplomacyHex(String ID, String northHexID, String northEastHexID, 
                            String southEastHexID, String southHexID, String southWestHexID, 
                            String northWestHexID) {
        super(ID, northHexID, northEastHexID, southEastHexID, southHexID, southWestHexID, northWestHexID);
        
    }
        
    private void SetPlayerHex(){
        playerHex = true;
    }
    
    private void SetNeturalHex(){
        neturalHex = true;
    } 
    
    public boolean GetIsPlayerHex(){
        return playerHex; 
    }
        
    public boolean GetIsNeturalHex(){
        return neturalHex;
    }
    
}
