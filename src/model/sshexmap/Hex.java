package sshexmap;

/**
 *
 * @author David
 */
public class Hex {
    private String ID;
 
    public Hex(String ID) {
        SetID(ID);
    }

    Hex() {}
    void SetID(String ID){
        this.ID = ID;
    } 

    
    public String GetID(){
        return ID;
    }
  
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
     
    public int[] GetCoords() {
        int[] res = new int[2];
        res[0] = GetIntID() / 100;
        res[1] = GetIntID() % 100;
        return res;
    }
 

    public int GetIntID(){
        return Integer.parseInt(ID);
    }  
}