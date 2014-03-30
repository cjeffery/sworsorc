package sshexmap;

/**
 *
 * @author David
 */
public class Hex {
    /**
     * Hex JUST has ID, more interesting things in subclasses
     */
    private String ID;

    /**
     * Construct "barebones" hex with the given ID
     */
    public Hex(String ID) {
        SetID(ID);
    }

    /**
     * default constructor does nothing
     */
    Hex() {}
    
    /**
     * Sets the string ID
     */
    void SetID(String ID){
        this.ID = ID;
    } 

    /**
     * @return the string ID, ie "1234"
     */
    public String GetID(){
        return ID;
    }
  
    /**
     * see docs for static version
     */
    public String GetNeighborID(int direction) {
        int[] coords = GetCoords();
        int x = coords[0], y = coords[1];
        switch(direction) {
            case 0: //NE
                y -= (x%2 == 1) ? 1 : 0;
                x++;
                break;
            case 1: //N
                y--;
                break;
            case 2: //NW
                y -= (x%2 == 1) ? 1 : 0;
                x--;
                break;
            case 3: //SW
                y += (x%2 == 1) ? 0 : 1;
                x--;
                break;             
            case 4: //S
                y++;
                break;
            case 5: //SE
                y += (x%2 == 1) ? 0 : 1;
                x++;
                break;                
        }
        return HexMap.GetIDFromCoords(x,y);
    }
     
    /**
     * Returns a neighbor ID given a direction
     * no OOB or null checking is performed
     * @param HexID The Hex to find the neighbor of
     * @param direction The direction, 0 = NE, 1 = N, 2 = NW, ... 5 = SE
     * @return The ID of the neighbor, whether or not the neighbor actually
     *         exists
     */
    public static String GetNeighborID(String HexID, int direction) {
        Hex h = new Hex(HexID);
        return h.GetNeighborID(direction);
    }
     
    /**
     * @return A pair of coordinates for the given hex, IE returns {1,23} for
     *         hex "0123"
     */
    public int[] GetCoords() {
        int[] res = new int[2];
        res[0] = GetIntID() / 100;
        res[1] = GetIntID() % 100;
        return res;
    }
 

    /**
     * @return the integer ID of the hex, IE returns 123 for hex "0123" 
     */
    public int GetIntID(){
        return Integer.parseInt(ID);
    }  
}
