import java.util.*;
public class Map {
    Hex[][] hexes;
    public Map() {
        hexes = new Hex[39][54];
        Random r = new Random();
        Hex.Terrain[] terrainValues = Hex.Terrain.values();
        int len = terrainValues.length;

        for(int i = 0; i < 39; i++)
        for(int j = 0; j < 54; j++) {
            Hex.Terrain t = terrainValues[r.nextInt(len)];
            HexEdge[] e = new HexEdge[6];
            for(int k = 0; k <6; k++) {
                e[k] = new HexEdge();
                double d = r.nextDouble();
                e[k].type = (d < 0.05) ? HexEdge.Type.WALL : HexEdge.Type.CLEAR;
            }
            hexes[i][j] = new Hex(t, e);
        }
    }

    /** return the number of columns */
    public int columns() {
        return 39;
    }
    /** return the length of the given column */
    public int columnLength(int column) {
        assert(column >= 0 && column < columns());
        if(column <= 11) return 54 - (column%2);
        return 52;
    }
    
    public boolean inBounds(int x, int y) {
        return    x >= 0 && x < columns()
               && y >= 0 && y < columnLength(x);
    }
}
