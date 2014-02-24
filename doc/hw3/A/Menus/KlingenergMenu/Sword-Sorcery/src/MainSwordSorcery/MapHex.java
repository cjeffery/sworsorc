/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainSwordSorcery;

import java.awt.List;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author David
 */
public class MapHex extends Hex{
    private String terrainKey;
    private boolean cityHex;
    private String cityName;
    private boolean vortexHex;
    private int portalHex;
   
    
    public MapHex(String id, String northHexID, String northEastHexID, 
                  String southEastHexID, String southHexID, String southWestHexID, 
                  String northWestHexID, String keyTerrain, boolean hexIsCity, 
                  String nameCity, boolean hexVortex, int portalNumber, String[] edgesHex ) {
                  
        
        super(id, northHexID, northEastHexID, southEastHexID, southHexID, southWestHexID, northWestHexID);
   
        
        terrainKey = keyTerrain;
        cityHex = hexIsCity;
        cityName = nameCity;
        vortexHex = hexVortex;
        portalHex = portalNumber;
   
       
    }
    
    public boolean GetIsPortalHex(){
        if (portalHex > 0)            
          return true;
        else
            return false;
    }
    
    
    
    public boolean GetIsVortexHex(){
        return vortexHex;
    }
    
    public String GetCityName (){
        return cityName;
    }
    
    public boolean GetIsCityHex (){
        return cityHex;
    }
   
    
    
    public  String GetTerrainDescription (String terrainCode){
        
        
        if ("Bl".equals(terrainCode))
            return"Blasted";        
        if ("Br".equals(terrainCode))
            return"Broken";
        if ("Cap".equals(terrainCode))
            return"Capital";
        if ("Cas".equals(terrainCode))
            return"Castel";
        if ("Ci".equals(terrainCode))
            return"City";
        if ("Cl".equals(terrainCode))
            return"Clear";
        if ("Cu".equals(terrainCode))
            return"Cultivated";
        if ("D".equals(terrainCode))
            return"Dragon Tunnel Complex";
        if ("Ford".equals(terrainCode))
            return"Ford";
        if ("Fore".equals(terrainCode))
            return"Forest";
        if ("G".equals(terrainCode))
            return"Glacier";
        if ("K".equals(terrainCode))
            return"Karoo";
        if ("Mrl".equals(terrainCode))
            return"Moat/River/Lake";
        if ("Mo".equals(terrainCode))
            return"Mountains";
        if ("Po".equals(terrainCode))
            return"Portal";
        if ("Roa".equals(terrainCode))
            return"Road";
        if ("Sh".equals(terrainCode))
            return"Special Hex";
        if ("Sw".equals(terrainCode))
            return"Swamp";
        if ("To".equals(terrainCode))
            return"Town";
        if ("V".equals(terrainCode))
            return"Vortex";
        if ("Wo".equals(terrainCode))
            return"Woods";
        
        
        /*    Hexside
              
                if ("Br".equals(terrainCode))  Hexside
                    return"Bridge";
                if ("Ga".equals(terrainCode))  Hexside
                    return"Gate";
                if ("Pb".equals(terrainCode))
                    return"Province Border";
                if ("St".equals(terrainCode))
                    return"Stream";
                if ("Tr".equals(terrainCode))
                    return"Trail";
                if ("Wa".equals(terrainCode))
                    return"Wall";
        */
        
        return "";
    }
    
    public String GetTerrainKey(){
        return terrainKey;
        
    }
    
}
