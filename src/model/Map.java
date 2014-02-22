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
            hexes[i][j] = new Hex();
            hexes[i][j].terrain = terrainValues[r.nextInt(len)];
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
}
