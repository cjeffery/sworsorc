/* Unit.java
*
*  Stub class for Unit used by MouseMoveUnit
*/

import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

public class Unit{
	BufferedImage icon;
	int currentX;
	int currentY;

	public Unit( int x, int y, String pic ){
		currentX = x;
		currentY = y;
		try{
			icon = ImageIO.read( new File(pic));
		} catch (IOException e){
			System.out.println("Unit: Could not load image");
		}
	}	
}
