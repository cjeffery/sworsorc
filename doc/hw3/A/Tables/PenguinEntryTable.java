import java.util.ArrayList;
import java.util.List;
import java.util.Random;
    
class PenguinEntryTable {

    // Public interface:
    // These function answer the question:
    // "How many penguins need to be spawned at each glacier?"

    public static int getNumberForWestGlacier(int rollOne, int rollTwo, int rollThree){
	int totalPenguins = getNumberOfPenguins(rollOne, rollTwo);
	GlacialOrder order = getGlacialOrder(rollThree);
	return getCountByLocation(Glacier.West, totalPenguins, order);
    }

    public static int getNumberForEastGlacier(int rollOne, int rollTwo, int rollThree){
	int totalPenguins = getNumberOfPenguins(rollOne, rollTwo);
	GlacialOrder order = getGlacialOrder(rollThree);
	return getCountByLocation(Glacier.East, totalPenguins, order);
    }

    // TODO: Do we want an auto-roll method? We'd have to package the east and west return
    // values together in some ugly way...

    // Private stuff:

    private enum Glacier {
	East,
	West;
    }

    private enum GlacialOrder {
	AllEast,
	AllWest,
	WestThenEast,
	EastThenWest;
    }

    private static int getNumberOfPenguins(int diceRollA, int diceRollB){
	// The total number of penguins to introduce.
	int diceSum = diceRollA + diceRollB;

	switch(diceSum){
	case 2: return 0;
	case 3: return 0;
	case 4: return 1;
	case 5: return 1;
	case 6: return 1;
	case 7: return 1;
	case 8: return 2;
	case 9: return 2;
	case 10: return 3;
	case 11: return 4;
	case 12: return 5;
	}
	return -1;
    }
    
    private static GlacialOrder getGlacialOrder(int diceRoll){
	// This will determine how we divide penguins between
	// the glaciers
	switch(diceRoll){
	case 1:
	case 2:
	    return GlacialOrder.AllWest;
	case 3:
	    return GlacialOrder.WestThenEast;
	case 4:
	    return GlacialOrder.EastThenWest;
	case 5:
	case 6:
	    return GlacialOrder.AllEast;
	}
	return null;
    }

    
    private static int getCountByLocation( Glacier location,
					   int totalPenguins,
					   GlacialOrder glacialOrder){
	// How many penguins spawn at the glacier specified by 'location'?
	// Per the rules, this depends on the GlacialOrder in a somewhat
	// messy way. 

	if (totalPenguins == 0) return 0;

	// Easy Cases: Everything goes on the same glacier:
	if (glacialOrder == GlacialOrder.AllWest){
	    if (location == Glacier.West){
		return totalPenguins;
	    }
	    else {
		return 0;
	    }
	}
	if (glacialOrder == GlacialOrder.AllEast){
	    if (location == Glacier.East){
		return totalPenguins;
	    }
	    else {
		return 0;
	    }
	}

	// In these cases, we put half of the penguins on
	// each glacier. If the total is odd, we put the extra
	// penguin on the glacier listed in the order first.
	if (glacialOrder == GlacialOrder.WestThenEast){
	    if (location == Glacier.West){
		return totalPenguins/2 + totalPenguins%2; // "round up"
	    }
	    else {
		return totalPenguins/2;
	    }
	}
	if (glacialOrder == GlacialOrder.EastThenWest){
	    if (location == Glacier.East){
		return totalPenguins/2 + totalPenguins%2; // "round up"
	    }
	    else {
		return totalPenguins/2;
	    }
	}
	return -1;
    }

    private static void test(){
	// Run all valid inputs. Yes, really.
	for (int rollOne = 1; rollOne < 7; rollOne++){
	    for (int rollTwo = 1; rollTwo < 7; rollTwo++){
		for (int rollThree = 1; rollThree < 7; rollThree++){
		    System.out.println("Roll 1, 2, 3: " + rollOne + " " + 
				       rollTwo + " " + 
				       rollThree);
		    System.out.println("East: " + getNumberForEastGlacier(rollOne, rollTwo, rollThree));
		    System.out.println("West: " + getNumberForWestGlacier(rollOne, rollTwo, rollThree));
		}
	    }
	}
    }

    public static void main(String[] args) {
	PenguinEntryTable.test();
    }
}


