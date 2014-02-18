/* Jay Drage

   EmissaryResult.java
   Emissary Results Table
   Swords and Sorcery
*/

import java.util.*;

public class EmissaryResult {
	int result;
	boolean eaten;
	// test if working
	public static void main( String [] args ){
		EmissaryResult eR = new EmissaryResult();
		for( int i = -3 ; i < 6 ; i++ ){
			eR.generateResult( i );
			System.out.print("diploScore:  " + i);
			System.out.println("\t   result:  " + eR.result + 
					   "\t   eaten: " + eR.eaten );
		}
	}
	// diploScore comes from Diplomatic Interest Grouping Table
	public void generateResult( int diploScore ){
		int score;
		score = DiceRoll() + DiceRoll() + diploScore;
	System.out.println("\nscore:  " + score);
		if( score <= 2 ){ 
			result = -1;
			eaten = true;
		}
		else if( score == 3 || score == 4 ){ 
			result = -3;
			eaten = false;
		}
		else if( score == 5 ){ 
			result = -2;
			eaten = false;
		} 
		else if( score >= 6 && score <= 8 ){ 
			result = -1;
			eaten = false;
		}
		else if( score == 9 ){ 
			result = 0;
			eaten = false;
		} 
		else if( score >= 10 && score <= 13 ){ 
			result = 1;
			eaten = false;
		}
		else if( score >= 14 && score <= 16 ){ 
			result = 2;
			eaten = false;
		}
		else{ 
			result = 3;
			eaten = false;
		}
	}
	// generate random dice roll
	public int DiceRoll( ){
		Random rand = new Random();
		int randNum = rand.nextInt(6)+1;
		return randNum;
	}
}
