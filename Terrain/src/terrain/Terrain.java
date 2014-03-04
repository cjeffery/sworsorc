/*
 * Keith Drew
 * Terrain Effects Chart Task
 * CS 383 - Dr. Jeffery - Feb 2014
 */
package terrain;

public class Terrain {

    public static void main(String[] args) {
        int hexID = 1040;
        int terrainTypeID = 7;
        int baseMove = 3;
        boolean streamside = false;
        boolean casterFlag = true;
        
        Hex testHex = new Hex( hexID, terrainTypeID, streamside );
        Character testChar = new Character( baseMove, casterFlag, "DRAGON" );
        MoveableUnit testUnit = new MoveableUnit( baseMove, "ELF" );
        double retDef = 0.0;
        double retCostChar = 0.0;
        double retCostUnit = 0.0;
        
        // Use this function's return value as a multiplier on the unit's 
        //  defense. If a zero is returned, the unit should not be defending 
        //  from that hex.
        retDef = terrainDefenseBonus( testUnit, testHex );

        // Use this function's return value as the literal movement cost of 
        //  stepping into the hex. This function is overloaded to handle
        //  character class units and moveableunit class units.
        retCostChar = terrainMoveCost( testChar, testHex );
        retCostUnit = terrainMoveCost( testUnit, testHex );
        
        System.out.println("Test Details");
        System.out.println("Hex Constructer Args: " + hexID + " " + 
                            terrainTypeID + " " + streamside );
        
        System.out.println("Defense Bonus: " + retDef);
        System.out.println("Character Move Cost: " + retCostChar );
        System.out.println("Unit Move Cost: " + retCostUnit );
        
    }

    
    
  public static double terrainDefenseBonus( MoveableUnit defendingUnit, Hex unitHex )
  {
      int hexType = unitHex.GetTerrainType();
      String raceDragon = "DRAGON";
      String raceElf = "ELF";
      String raceSpider = "SPIDER";
      String raceCronk = "CRONK";
      String raceSwampCreature = "SWAMPCREATURE";
      String raceDwarrow = "DWARROW";
      
      switch (hexType) {
          case 0: return 0;
          case 1: return .5;
          case 2: return 1;
          case 3: return 1;
          case 4: return 1;
          case 5: return 3;
          case 6: return 3;
          case 7: return 1;
          case 8: return 1;
          case 9: 
              if( raceDragon.equals(defendingUnit.GetRace()) )
                  return 4;
              else     
                  return 1;
          case 10: return .5;
          case 11: 
              if( raceElf.equals(defendingUnit.GetRace()) )
                  return 3;
              else if( raceSpider.equals(defendingUnit.GetRace()))
                  return 3;
              else return 2;
          case 12: return 2;
          case 13: 
              if( raceCronk.equals(defendingUnit.GetRace()) )
                  return 3;
              else 
                  return 2;
          case 14: 
              if( raceSwampCreature.equals(defendingUnit.GetRace()) )
                  return 2;
              else 
                  return 1;
          case 15: 
              if( raceDwarrow.equals(defendingUnit.GetRace()) )
                  return 4;
              else
                  return 3;
          case 16: return 1;
          case 17: return 1;
          case 18: return 1;
          case 19: return 2;
          case 20: return 1;
          case 21: 
              if( unitHex.IsStreamSide() )
                return 2;
              else 
                return 1;
          case 22: 
              if( raceSwampCreature.equals( defendingUnit.GetRace()) )
                  return 3;
              else 
                  return 2;
          case 23: return 2;
          case 24: return 1;
          case 25: return 0;
          case 26: return 1;
          case 27: return 2;
          default: return 1;
        }
  }

  public static double terrainMoveCost( Character movingChar, Hex destHex )
  {
      int hexType = destHex.GetTerrainType();
      String raceDragon = "DRAGON";
      String raceElf = "ELF";
      String raceSpider = "SPIDER";
      String raceKPenguin = "KILLERPENGUIN";
      String raceCronk = "CRONK";
      String raceSwampCreature = "SWAMPCREATURE";
      String raceDwarrow = "DWARROW";
      String raceOrc = "ORC";
      
      switch (hexType) {
          case 0: 
              if( movingChar.GetCasterFlag() )
                  return 2;
              else 
                  return 3;
          case 1: 
              if( destHex.IsBridgeSide() )
                  return 1;
              else 
                  return -1; // Impassable unless hex is bridgeside
          case 2: return 1;
          case 3: return 2;
          case 4: return 1;
          case 5: return 1;
          case 6: return .5;
          case 7: return 1;
          case 8: return .5;
          case 9: 
              if( raceDragon.equals( movingChar.GetRace()) )
                  return 1;
              else 
                  return 2;
          case 10: return 3;
          case 11: 
              if( raceElf.equals( movingChar.GetRace()) )
                  return 2;
              else if( raceSpider.equals( movingChar.GetRace()) )
                  return 1;
              else 
                  return 3;
          case 12: 
              if( raceKPenguin.equals( movingChar.GetRace()) )
                  return 1;
              else 
                  return 5;
          case 13: 
              if( raceCronk.equals(movingChar.GetRace()) )
                  return 1;
              else 
                  return 2;
          case 14: 
              if( raceSwampCreature.equals(movingChar.GetRace()) )
                  return 2;
              else 
                  return -1;                  
          case 15: 
              if( raceDwarrow.equals(movingChar.GetRace()) )
                  return 2;
              else if( raceOrc.equals(movingChar.GetRace()) )
                  return 2;
              else 
                  return 3;
          case 16: return 1;
          case 17: return 1;
          case 18: return .5;
          case 19: 
              if( raceDwarrow.equals(movingChar.GetRace()) )
                  return 2;
              else if( raceOrc.equals(movingChar.GetRace()) )
                  return 2;
              else 
                  return 3;
          case 20: return 1;
          case 21: return 1;
          case 22: 
              if( raceSwampCreature.equals( movingChar.GetRace()) )
                  return 1;
              else if( raceCronk.equals( movingChar.GetRace()) )
                  return 2;
              else
                  return 3;
          case 23: return 1;
          case 24: return 1;
          case 25: return -1;
          case 26: return -1;
          case 27: 
              if( raceElf.equals(movingChar.GetRace()) )
                  return 1;
              else 
                  return 2;
          default: return 1;
        }
  }
  
  public static double terrainMoveCost( MoveableUnit movingUnit, Hex destHex )
  {
      int hexType = destHex.GetTerrainType();
      String raceDragon = "DRAGON";
      String raceElf = "ELF";
      String raceSpider = "SPIDER";
      String raceKPenguin = "KILLERPENGUIN";
      String raceCronk = "CRONK";
      String raceSwampCreature = "SWAMPCREATURE";
      String raceDwarrow = "DWARROW";
      String raceOrc = "ORC";
      
      switch (hexType) {
          case 0: return -1; // Blasted Hex
          case 1:             // Bridge Hex
              if( destHex.IsBridgeSide() )
                  return 1;
              else 
                  return -1;// Impassable unless hex is bridgeside
          case 2: return 1; // Bridge/Gate Hex
          case 3: return 2; // Broken Hex
          case 4: return 1; // Capital Hex
          case 5: return 1; // Castle Hex
          case 6: return 1; // City Hex
          case 7: return 1; // Clear Hex
          case 8: return 1; // Cultivated Hex
          case 9:           // Dragon Tunnel Complex
              if( raceDragon.equals( movingUnit.GetRace()) )
                  return 1;
              else 
                  return 2;
          case 10: return 3; // Ford Hex
          case 11:           // Forest Hex
              if( raceElf.equals( movingUnit.GetRace()) )
                  return 2;
              else if( raceSpider.equals( movingUnit.GetRace()) )
                  return 1;
              else 
                  return 3;
          case 12:           // Glacier Hex
              if( raceKPenguin.equals( movingUnit.GetRace()) )
                  return 1;
              else 
                  return -1;
          case 13:           // Karoo Hex
              if( raceCronk.equals(movingUnit.GetRace()) )
                  return 1;
              else 
                  return 2;
          case 14:              // Moat/River/Lake hex
              if( raceSwampCreature.equals(movingUnit.GetRace()) )
                  return 2;
              else if( raceKPenguin.equals(movingUnit.GetRace()) )
                  return 1;
              else 
                  return -1;                  
          case 15:                  // Mountain Hex
              if( raceDwarrow.equals(movingUnit.GetRace()) )
                  return 2;
              else if( raceOrc.equals(movingUnit.GetRace()) )
                  return 2;
              else 
                  return 3;
          case 16: return 1; // Portal Hex
          case 17: return 1; // Province Boarder
          case 18: return .5; // On a road
          case 19:            // Rough Hex
              if( raceDwarrow.equals(movingUnit.GetRace()) )
                  return 2;
              else if( raceOrc.equals(movingUnit.GetRace()) )
                  return 2;
              else 
                  return 3;
          case 20: return 1; // Special Hex - wat?
          case 21: return 1; // StreamHexside
          case 22:                // Swamp hex
              if( raceSwampCreature.equals( movingUnit.GetRace()) )
                  return 1;
              else if( raceCronk.equals( movingUnit.GetRace()) )
                  return 2;
              else
                  return 3;
          case 23: return 1; // Town
          case 24: return 1; // Trail
          case 25: return -1; // Impassable vortex hex
          case 26: return -1; // Impassable wall hexside
          case 27:            // Wooded hex
              if( raceElf.equals(movingUnit.GetRace()) )
                  return 1;
              else 
                  return 2;
          default: return 1;
        }

  }

}

