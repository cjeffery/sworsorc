/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainSwordSorcery;

/**
 *  
 * @author David Klingenberg
 */


public class StellarConfiguration {
  int yellowSunPosition;
  char redSunPosition;
  char blueSunPosition;
  
  boolean redSunAscendent;
  boolean redSunDescendent;
 
  
  public StellarConfiguration(char positionOfRedSun){
      
      SetYellowSunPosition(1);
      
      
      if(Character.isUpperCase(positionOfRedSun))
        Character.toLowerCase(positionOfRedSun);
      
      SetRedSunPosition(positionOfRedSun);
      
      
      // Determins the position of the blue sun in reference to the red sun position.
      switch (GetRedSunPosition()) {
          case 'a' : 
               SetBlueSunPosition('g');
              break;
              
          case 'b' : 
              SetBlueSunPosition('h');
              break;
              
          case 'c' : 
              SetBlueSunPosition('i');
              break;
              
          case 'd' :
              SetBlueSunPosition('j');
              break;
              
          case 'e' :
              SetBlueSunPosition('k');
              break;
              
          case 'f' :
              SetBlueSunPosition('l');
              break;
              
          case 'g' : 
              SetBlueSunPosition('a');
              break;

          case 'h' : 
              SetBlueSunPosition('b');
              break;
              
          case 'i' : 
              SetBlueSunPosition('c');
              break;
              
          case 'j' :
              SetBlueSunPosition('d');
              break;
              
          case 'k' :
              SetBlueSunPosition('e');
              break;
              
          case 'l' :
              SetBlueSunPosition('f');
              break;      
      }//switch
      
      SetSunPhase();
      
  }//StellarCongfiguration
  
  public void AdvanceSuns(){
      
      int intSunPosition;
      char charSunPosition;
      
      if (GetYelloSunPosition() < 27){
        intSunPosition = GetYelloSunPosition();
        intSunPosition++;
        SetYellowSunPosition(intSunPosition);
      }//if
      else
        SetYellowSunPosition(1);
      
      if (GetRedSunPosition() < 'l'){  //That first char is not a one, it is an L. 
        charSunPosition = GetRedSunPosition();
        charSunPosition++;
        SetRedSunPosition(charSunPosition);
      }//if
      else 
          SetRedSunPosition('a');
      
      if (GetBlueSunPosition() < 'l'){  //That first char is not a one, it is an L.
          charSunPosition = GetBlueSunPosition();
          charSunPosition++;
          SetBlueSunPosition(charSunPosition); 
      }//if
      else
          SetBlueSunPosition('a');
      
      SetSunPhase();
  }//AdvanceSuns()
  
  private void CheckMinorSunsPosition(char minorSunAscendentPositionOne, char minorSunAscendentPositionTwo, char minorSunAscendentPositionThree){
    if (GetRedSunPosition() == minorSunAscendentPositionOne || GetRedSunPosition() == minorSunAscendentPositionTwo || GetRedSunPosition() == minorSunAscendentPositionThree){
                SetRedSunAscendent(true);
                SetRedSunDescendent(false);
                }//if
                else              
                  if (GetBlueSunPosition() == minorSunAscendentPositionOne || GetBlueSunPosition() == minorSunAscendentPositionTwo || GetBlueSunPosition() == minorSunAscendentPositionThree){
                    SetRedSunAscendent(false);
                    SetRedSunDescendent(true);
                  }//if
                  else{
                    SetRedSunAscendent(false);
                    SetRedSunDescendent(false);
                  }//else
  }//CheckMinorSunsPosition(char p1, char p2, char p3)
  
  private void SetSunPhase(){
      switch (GetYelloSunPosition()) {
              case 1 :  
              case 2 :
                CheckMinorSunsPosition('c','d','e');
                break;                  
                  
              case 3 :
              case 4 :
                CheckMinorSunsPosition('d','e','f');
                break;                  
                  
              case 5 :                  
              case 6 :
                CheckMinorSunsPosition('e','f','g');
                break;
                  
              case 7 :
              case 8 :
              case 9 :
                CheckMinorSunsPosition('f','g','h');
                break;                  
             
              case 10 :
              case 11 :
                CheckMinorSunsPosition('g','h','i');
                break;        
      
              case 12 :
              case 13 :
                  CheckMinorSunsPosition('h','i','j');
                  break;
                  
              case 14 :
              case 15 :
                  CheckMinorSunsPosition('i','j','k');
                  break;
                  
              case 16:
              case 17 :
                  CheckMinorSunsPosition('j','k','l');
                  break;
                  
              case 18:
              case 19:
              case 20:
                  CheckMinorSunsPosition('k','l','a');
                  break;
                  
              case 21 :
              case 22 :
                  CheckMinorSunsPosition('l','a','b');
                  break;
                  
              case 23 :
              case 24 :
                  CheckMinorSunsPosition('a','b','c');
                  break;
                  
              case 25 :
              case 26 :
                  CheckMinorSunsPosition('b','c','d');
                  break;
                  
              case 27 :
                  CheckMinorSunsPosition('c','d','e');
                  break;
      }//switch       
  }//SetSunPhase()
          
  public boolean GetRedSunIsInAscension(){
      return redSunAscendent;
  }//GetRedSunIsInAscension()
  
  public boolean GetRedSunIsInDeclension(){
      return redSunDescendent;
  }//GetRedSunIsInDeclension(){
  
  public boolean GetBlueSunIsInAscension(){
     return redSunDescendent;
  }//GetBlueSunIsInAscension()
  
  public boolean GetBlueSunIsInDeclension(){
      return redSunAscendent;
  }//GetBlueSunIsInDeclension()
  
  public int GetYelloSunPosition(){
      return yellowSunPosition;
  }//GetYelloSunPosition()
  
  public char GetRedSunPosition(){
      return redSunPosition;
  }//GetRedSunPosition()
  
  public char GetBlueSunPosition(){
      return blueSunPosition;
  }//GetBlueDunPosition()
  
  private void SetYellowSunPosition(int sunPosition){
      yellowSunPosition = sunPosition;
  }//SetYellowSunPosition(int sP)
  
  private void SetRedSunPosition(char sunPosition){
      redSunPosition = sunPosition;
  }//SetRedSunPosition(char sP)
  
  private void SetBlueSunPosition(char sunPosition){
      blueSunPosition = sunPosition;
  }//SetBlueSunPosition(char sP)
  
  private void SetRedSunAscendent(boolean sunAscension){
      redSunAscendent = sunAscension;
  }//SetRedSunAscendent(boolean sA)
  
  private void SetRedSunDescendent(boolean sunDescendent){
      redSunDescendent = sunDescendent;
  }//SetRedSunDescendent(boolean sA)
}//class
