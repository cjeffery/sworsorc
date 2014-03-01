/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainSwordSorcery;

/**
 * Email me if anyone has questions or need chages made.
 * 
 * @author David Klingenberg, klin7456@vandals.uidaho.edu
 */
public class Hex {
    
  private String[] hexagon = new String[7];
  
  public Hex(String ID, String northHexID, String northEastHexID, String southEastHexID, 
          String southHexID, String southWestHexID, String northWestHexID){
      SetID(ID);
      SetIDofNorthHexagon(northHexID);
      SetIDofNorthEastHexagon(northEastHexID);
      SetIDofSouthEastHexagon(southEastHexID);
      SetIDofSouthHexagon(southHexID);
      SetIDofSouthWestHexagon(southWestHexID);
      SetIDofNorthWestHexagon(northWestHexID);
  }

  private void SetID(String ID){
      hexagon[0] = ID;
  } 
  
  private void SetIDofNorthHexagon(String ID){
      hexagon[1] = ID;
  }
  
  private void SetIDofNorthEastHexagon(String ID){
      hexagon[2] = ID;
  }
  
  private void SetIDofSouthEastHexagon(String ID){
      hexagon[3] = ID;
  }
  
  private void SetIDofSouthHexagon(String ID){
      hexagon[4] = ID;
  }
   
  private void SetIDofSouthWestHexagon(String ID){
      hexagon[5] = ID;
  }

  private void SetIDofNorthWestHexagon(String ID){
      hexagon[6] = ID;
  }
  
  public String GetID(){
      return hexagon[0];
   }
  
  public String GetIDofNorthHexagon(){
      return hexagon[1];
  }
  
  public String GetIDofNorthEastHexagon(){
      return hexagon[2];
  }
  
  public String GetIDofSouthEastHexagon(){
      return hexagon[3];
  }
  
  public String GetIDofSouthHexagon(){
      return hexagon[4];
  }
   
  public String GetIDofSouthWestHexagon(){
     return hexagon[5];
  }

  public String GetIDofNorthWestHexagon(){
      return hexagon[6];
  }
  
}//class
