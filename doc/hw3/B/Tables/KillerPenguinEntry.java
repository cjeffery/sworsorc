/* Jay Drage
   
   KillerPenguinEntry.java
   Killer Penguin Entry Table

   generates number of penguins to enter and on which map edge
*/

import java.util.*;

public class KillerPenguinEntry {
	int numberOfPenguins;
	int numberEnterFromWest;
	int numberEnterFromEast;

	public static void main( String [] args ){
		KillerPenguinEntry k = new KillerPenguinEntry();
		k.getResults();
		System.out.print("number of Penguins:    " + k.numberOfPenguins + "\t\tWest: " + k.numberEnterFromWest);
		System.out.println("\tEast: " + k.numberEnterFromEast);
	}
	//produces table results
	public void getResults(){
		generateNumberOfPenguins();
		findBoardEntrySide();
	}
	//generate the board edge foor penguin entry and assign 
	//numberEnterFromWest and numberEnterFromEast
	public void findBoardEntrySide(){
		int num = DiceRoll();
		System.out.println("\tSideRoll:  " + num );
		switch(num) {
			case 1: numberEnterFromWest = numberOfPenguins;
				numberEnterFromEast = 0;
				break;
			case 2: numberEnterFromWest = numberOfPenguins;
				numberEnterFromEast = 0;
				break;
			case 3: if( numberOfPenguins % 2 == 1 )
					numberEnterFromWest = (numberOfPenguins + 1) / 2;
				else
					numberEnterFromWest = numberOfPenguins / 2;
				numberEnterFromEast = numberOfPenguins / 2;
				break;
			case 4: if( numberOfPenguins % 2 == 1 )
					numberEnterFromEast = (numberOfPenguins + 1) / 2;
				else
					numberEnterFromEast = numberOfPenguins / 2;
				numberEnterFromWest = numberOfPenguins / 2;
				break;
			case 5: numberEnterFromWest = 0; 
				numberEnterFromEast = numberOfPenguins;;
				break;
			case 6: numberEnterFromWest = 0; 
				numberEnterFromEast = numberOfPenguins;;
				break;
			default:
				System.out.println("error: KillerPenguinEntry:findBoardEntrySide() - invalid number");
				break;
		}

	}
	//generate the number of penguins to enter board
	public void generateNumberOfPenguins(){
		int num = DiceRoll() + DiceRoll();
		System.out.print("numPenguins die roll:  " + num );
		switch(num) {
			case 2:	numberOfPenguins = 0;
				break;
			case 3:	numberOfPenguins = 0;
				break;
			case 4:	numberOfPenguins = 1;
				break;
			case 5:	numberOfPenguins = 1;
				break;
			case 6:	numberOfPenguins = 1;
				break;
			case 7:	numberOfPenguins = 1;
				break;
			case 8:	numberOfPenguins = 2;
				break;
			case 9:	numberOfPenguins = 2;
				break;
			case 10:numberOfPenguins = 3;
				break;
			case 11:numberOfPenguins = 4;
				break;
			case 12:numberOfPenguins = 5;
				break;
			default: 
				System.out.println("error: KillerPenguinEntry:generateNumberOfPenguins() - invalid number");
				break;
		}
	}
	//generate random dice roll
	public int DiceRoll(){
		Random rand = new Random();
		int randNum = rand.nextInt(6) +1;
		return randNum;
	}
}

