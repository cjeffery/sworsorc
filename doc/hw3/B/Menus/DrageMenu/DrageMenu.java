/*  Hello4.java
  *
  *  Illustrates Colors, arrays and a method.
  *  Displays lines correctly upon resizing frame (window).
  */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class DrageMenu extends JComponent
{
     static int frameWidth  = 900;
     static int frameHeight = 600;

     public static void main( String[] args )
     {
         JFrame f = new JFrame( "Jay Drage Main Menu" );

         //  Allow application to exit when window is closed 
         f.addWindowListener( new WindowAdapter() {
             public void windowClosing( WindowEvent e )
             {  System.exit(0); }
             }
         );

         f.setSize( frameWidth, frameHeight );
         f.getContentPane().add( new DrageMenu() );
         f.setVisible( true );
     }

     public void paintComponent( Graphics g )
     {
	frameWidth  = getWidth();
	frameHeight = getHeight();
	int thickness = 3;
	int buttonWidth = frameWidth / 2;
	int buttonHeight = frameHeight / 10;
	int startX = (frameWidth / 4) ;
	int startY = frameHeight / 9;
	int offsetY = 2 * buttonHeight;
	//load background image
	BufferedImage img = null;
	try {
    		img = ImageIO.read(new File("menu.jpeg"));
	} catch (IOException e) {
		System.out.println("Could not load menu.jpeg");
	}
	g.drawImage( img, 0, 0, null);
	g.setFont( new Font("Serif", Font.BOLD, buttonHeight / 2 +10));
	g.setColor(Color.RED);
	String title = "Swords and Sorcery";
	g.drawString( title, startX + title.length(), frameHeight / 10);
	g.setFont( new Font("Serif", Font.PLAIN, buttonHeight / 2));
	//create new game button
	g.setColor(Color.BLACK);
	Rectangle newGameBorder = new Rectangle( startX, startY, buttonWidth, buttonHeight );
	g.fillRect((int)newGameBorder.getX(), (int)newGameBorder.getY(), 
			(int)newGameBorder.getWidth(), (int)newGameBorder.getHeight());
	g.setColor(Color.WHITE);
	Rectangle newGame = new Rectangle( startX, startY, buttonWidth, buttonHeight );
	g.fillRect((int)newGame.getX() + thickness , (int)newGame.getY() +thickness, 
			(int)newGame.getWidth() - 2 * thickness, (int)newGame.getHeight() - 2 * thickness);
	g.setColor(Color.BLACK);
	g.drawString( "New Game", startX + buttonWidth / 4 ,startY + (3 * buttonHeight) / 4 );
	//create load game button
	startY += offsetY;
	g.setColor(Color.BLACK);
	Rectangle loadGameBorder = new Rectangle( startX, startY, buttonWidth, buttonHeight );
	g.fillRect((int)loadGameBorder.getX(), (int)loadGameBorder.getY(), 
			(int)loadGameBorder.getWidth(), (int)loadGameBorder.getHeight());
	g.setColor(Color.WHITE);
	Rectangle loadGame = new Rectangle( startX, startY, buttonWidth, buttonHeight );
	g.fillRect((int)loadGame.getX() + thickness , (int)loadGame.getY() +thickness, 
			(int)loadGame.getWidth() - 2 * thickness, (int)loadGame.getHeight() - 2 * thickness);
	g.setColor(Color.BLACK);
	g.drawString( "Load Game", startX + buttonWidth / 4 ,startY + (3 * buttonHeight) / 4 );
	//create join multiplayer button
	startY += offsetY;
	g.setColor(Color.BLACK);
	Rectangle joinMultiBorder = new Rectangle( startX, startY, buttonWidth, buttonHeight );
	g.fillRect((int)joinMultiBorder.getX(), (int)joinMultiBorder.getY(), 
			(int)joinMultiBorder.getWidth(), (int)joinMultiBorder.getHeight());
	g.setColor(Color.WHITE);
	Rectangle joinMulti = new Rectangle( startX, startY, buttonWidth, buttonHeight );
	g.fillRect((int)joinMulti.getX() + thickness , (int)joinMulti.getY() +thickness, 
			(int)joinMulti.getWidth() - 2 * thickness, (int)joinMulti.getHeight() - 2 * thickness);
	g.setColor(Color.BLACK);
	g.drawString( "Multiplayer", startX + buttonWidth / 4 ,startY + (3 * buttonHeight) / 4 );
	//create exit game button
	startY += offsetY;
	g.setColor(Color.BLACK);
	Rectangle exitGameBorder = new Rectangle( startX, startY, buttonWidth, buttonHeight );
	g.fillRect((int)exitGameBorder.getX(), (int)exitGameBorder.getY(), 
			(int)exitGameBorder.getWidth(), (int)exitGameBorder.getHeight());
	g.setColor(Color.WHITE);
	Rectangle exitGame = new Rectangle( startX, startY, buttonWidth, buttonHeight );
	g.fillRect((int)exitGame.getX() + thickness , (int)exitGame.getY() +thickness, 
			(int)exitGame.getWidth() - 2 * thickness, (int)exitGame.getHeight() - 2 * thickness);
	g.setColor(Color.BLACK);
	g.drawString( "Exit Game", startX + buttonWidth / 4 ,startY + (3 * buttonHeight) / 4 );
     }
}


