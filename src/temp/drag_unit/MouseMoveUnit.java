/* MouseMove.java
*
*  Left click to selects unit
*  Unit is redrawn when mouse is dragged to mouse (x.y)
*  Release left click stops redraw
*/

import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/*  
*
*/
public class MouseMoveUnit extends JComponent implements MouseListener, MouseMotionListener
{
   // background image
   BufferedImage background;
   // true if mouse cursor is in background area
   boolean inBackground = true;
	// set demonstration unit icon
	Unit unit = new Unit( 40, 50, "infantry.jpg");
   // This is unit that has been selected by left mouse click
   // set by mousePressed(), unset by mouseReleased();
   Unit selectedUnit = null;
	static int FRAME_WIDTH = 504;
	static int FRAME_HEIGHT = 620;

	public static void main( String [] args ){
		JFrame f = new JFrame ("MouseMoveTest");		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize( FRAME_WIDTH, FRAME_HEIGHT );
		f.getContentPane().add( new MouseMoveUnit() );
		f.setVisible( true );
	}
	// adds MouseListener and MouseMotionListern
	public MouseMoveUnit(){
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	public void paintComponent( Graphics g )
	{
		try {
			background = ImageIO.read( new File("background.jpg"));
		} catch (IOException e){
			System.out.println("Could not load background.jpg");
		}
		g.drawImage( background, 0, 0, null);
		g.drawImage( unit.icon, unit.currentX, unit.currentY, null);
	}
	// changes (x,y) of selectedUnit and calls repaint()
	public void mouseDragged( MouseEvent event ){
		int x = event.getX();
		int y = event.getY();
      if( selectedUnit != null && inBackground ){
         //move selectedUnit to current mouse (x,y)
         selectedUnit.currentX = x;
         selectedUnit.currentY = y;

         // check to see if mouse cursor is out of window
         if( x < 0) 
            selectedUnit.currentX = 0;
         else if( x > background.getWidth() - selectedUnit.icon.getWidth())
            selectedUnit.currentX = background.getWidth() - selectedUnit.icon.getWidth();
         if( y < 0) 
            selectedUnit.currentY = 0;
         else if( y > background.getHeight() - selectedUnit.icon.getHeight())
            selectedUnit.currentY = background.getHeight() - selectedUnit.icon.getHeight();
      }
      //update changes
      repaint();
	}
	// listens for left moust button and checks if unit was selected	
	public void mousePressed( MouseEvent event ){
		if(event.getButton() == 1){//left mouse button
			// (x,y) of button press event
			int x = event.getX();
			int y = event.getY();
			//check if button was pressed in unit icon
			if( x >= unit.currentX 
            && x<= unit.currentX +unit.icon.getWidth()
            && y >= unit.currentY
            && y <= unit.currentY + unit.icon.getHeight()){
               selectedUnit = unit;
            }
		}
	}
	// deselects selectedUnit after left mouse button released	
	public void mouseReleased( MouseEvent event ){
		if(event.getButton() == 1){//left mouse button
         selectedUnit = null;// deselect unit
      }
	}
	// needed to overide in MouseListener	
	public void mouseMoved( MouseEvent event ){
	}
	// needed to overide in MouseListener	
	public void mouseClicked( MouseEvent event ){
	}
	// check if mouse has entered window
	public void mouseEntered( MouseEvent event ){
      inBackground = true;
	}
	// check if mouse has exited window
	public void mouseExited( MouseEvent event ){
      inBackground = false;
	}
}
