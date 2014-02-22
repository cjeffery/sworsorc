/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainSwordSorcery;

/**
 *
 * @author David
 */
public class DiplomacyMapHex extends Hex {
    private boolean playerHex = false;
    private boolean neturalHex = false;
    
    public DiplomacyMapHex(int id, int northHexID, int northEastHexID, 
                            int southEastHexID, int southHexID, int southWestHexID, 
                            int northWestHexID, boolean playerHex, int specialHexCode) {
        super(id, northHexID, northEastHexID, southEastHexID, southHexID, southWestHexID, northWestHexID);
        
        if (specialHexCode == 1)
            SetPlayerHex();
        if (specialHexCode == 2)
            SetNeturalHex();
    }
    
    public DiplomacyMapHex(int ID, int northHexID, int northEastHexID, 
                            int southEastHexID, int southHexID, int southWestHexID, 
                            int northWestHexID) {
        super(ID, northHexID, northEastHexID, southEastHexID, southHexID, southWestHexID, northWestHexID);
        if (playerHex)
            SetPlayerHex();        
    }
        
    private void SetPlayerHex(){
        playerHex = true;
    }
    
    private void SetNeturalHex(){
        neturalHex = true;
    } 
    
    public void RemoveANetural(){
        //needs implementing. 
    }
    
    public boolean GetIsPlayerHex(){
        return playerHex; 
    }
        
    public boolean GetIsNeturalHex(){
        return neturalHex;
    }
    
}
