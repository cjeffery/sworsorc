/*
 * A hex class to demo my charts with
 *  Keith Drew - Dr. Jeffery - CS 383 - Feb 2014
 */
package terrain;

public class Hex {
    public Hex(int id, int tType, boolean byStream)
    {
        hexID = id;
        terrainType = tType;
        streamSide = byStream;
    }
    private int hexID;
    private int terrainType;
    private boolean streamSide;
    private boolean bridgeSide;   
    
    public int GetHexId()
    {
        return hexID;
    }
    public int GetTerrainType()
    {
        return terrainType;
    }
    public void changeTerrainType(int tType )
    {
        terrainType = tType;
    }
    public boolean IsStreamSide()
    {
        return streamSide;
    }
    public boolean IsBridgeSide()
    {
        return bridgeSide;
    }
}
