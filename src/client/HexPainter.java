import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;

/** A class that does nothing but draw hexes! Right now it's somewhat closely
    tied to MapWidget, but if you ever need to draw hexes outside of the 
    map you could probably use or tweak it */
public class HexPainter {
    private final double hexRadius, width, height;
    private final Path2D.Double hexShape;
    private BufferedImage mountainImage;
    private BufferedImage forestImage;

    public HexPainter(double hexRadius) {
        this.hexRadius = hexRadius;
        width  = hexRadius*2;
        height = hexRadius*Math.sqrt(3);
        hexShape = new Path2D.Double(Path2D.WIND_NON_ZERO, 7);
        hexShape.moveTo(0,          height*0.5);
        hexShape.lineTo(width*0.25, 0);
        hexShape.lineTo(width*0.75, 0);
        hexShape.lineTo(width,      height*0.5);
        hexShape.lineTo(width*0.75, height);
        hexShape.lineTo(width*0.25, height);
        hexShape.closePath();
        
        try {
            mountainImage = ImageIO.read(new File("mountains.png"));
        }
        //FIXME, need error handling
        catch (IOException ex) {
            System.out.println("Could not load image D:");
        }
    }

    /* Paint the specified hex onto the specified Graphics */
    public void paintHex(Graphics2D g2, Hex h) {
        switch(h.terrain) {
            case CLEAR:
                g2.setColor( new Color(245, 245, 220) );
                g2.fill(hexShape); 
                break;
            case RIVER:
                g2.setColor( Color.BLUE );
                g2.fill(hexShape); 
                break;
            case MOUNTAIN:
                //g2.setColor( Color.RED );
                //g2.fill(hexShape);
                g2.drawImage(mountainImage, 0, 0, null);
                break;
             case FOREST:
                g2.setColor( Color.GREEN );
                g2.fill(hexShape);
                //g2.drawImage(forestImage, 0, 0, null);
        }
    }
}
