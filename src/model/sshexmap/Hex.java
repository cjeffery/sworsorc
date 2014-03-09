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
                y++;
                x -= (x%2 == 1) ? 0 : 1;
                break;
            case 1: //North
                x--;
                break;
            case 2:
                y--;
                x -= (x%2 == 1) ? 0 : 1;
                break;
            case 3:
                y--;
                x += (x%2 == 1) ? 1 : 0;
                break;             
            case 4:
                x++;
                break;
            case 5:
                y++;
                x += (x%2 == 1) ? 1 : 0;
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