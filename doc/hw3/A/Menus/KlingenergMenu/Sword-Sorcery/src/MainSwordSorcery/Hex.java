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
public class Hex {
    
  private int[] hexagon = new int[7];
  
  public Hex(int ID, int northHexID, int northEastHexID, int southEastHexID, 
          int southHexID, int southWestHexID, int northWestHexID){
      SetID(ID);
      SetIDofNorthHexagon(northHexID);
      SetIDofNorthEastHexagon(northEastHexID);
      SetIDofSouthEastHexagon(southEastHexID);
      SetIDofSouthHexagon(southHexID);
      SetIDofSouthWestHexagon(southWestHexID);
      SetIDofNorthWestHexagon(northWestHexID);
  }

  private void SetID(int ID){
      hexagon[0] = ID;
  } 
  
  private void SetIDofNorthHexagon(int ID){
      hexagon[1] = ID;
  }
  
  private void SetIDofNorthEastHexagon(int ID){
      hexagon[2] = ID;
  }
  
  private void SetIDofSouthEastHexagon(int ID){
      hexagon[3] = ID;
  }
  
  private void SetIDofSouthHexagon(int ID){
      hexagon[4] = ID;
  }
   
  private void SetIDofSouthWestHexagon(int ID){
      hexagon[5] = ID;
  }

  private void SetIDofNorthWestHexagon(int ID){
      hexagon[6] = ID;
  }
  
  public int GetID(){
      return hexagon[0];
   }
  
  public int GetIDofNorthHexagon(){
      return hexagon[1];
  }
  
  public int GetIDofNorthEastHexagon(){
      return hexagon[2];
  }
  
  public int GetIDofSouthEastHexagon(){
      return hexagon[3];
  }
  
  public int GetIDofSouthHexagon(){
      return hexagon[4];
  }
   
  public int GetIDofSouthWestHexagon(){
     return hexagon[5];
  }

  public int GetIDofNorthWestHexagon(){
      return hexagon[6];
  }
  
}//class