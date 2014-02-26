import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
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
        //not sure if this number should be 6 or 7
        hexShape = new Path2D.Double(Path2D.WIND_NON_ZERO, 7);
        hexShape.moveTo(0,          height*0.5);
        hexShape.lineTo(width*0.25, 0);
        hexShape.lineTo(width*0.75, 0);
        hexShape.lineTo(width,      height*0.5);
        hexShape.lineTo(width*0.75, height);
        hexShape.lineTo(width*0.25, height);
        hexShape.closePath();
        
        try {
            mountainImage = ImageIO.read(new File("mountains2.png"));
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
                Shape s = g2.getClip();
                g2.clip(hexShape);
                float scale = (float) (width/mountainImage.getWidth());
                AffineTransform at = new AffineTransform();
                at.scale(scale, scale);
                g2.drawImage(mountainImage, at, null);
                g2.setClip(s);
                break;
             case FOREST:
                g2.setColor( Color.GREEN );
                g2.fill(hexShape);
                g2.drawImage(forestImage, 0, 0, null);
        }
    }
    
    public void paintEdges(Graphics2D g2, Hex h) {
        g2.setColor( Color.BLACK );
        g2.setStroke( new BasicStroke(3) );
        int[] xArr = {(int)width, (int)(width*0.75), (int)(width*0.25), 0,
                    (int)(width*0.25), (int)(width*0.75), (int)width};
        int[] yArr = {(int)(height/2), 0, 0, (int)(height/2), (int)height,
                    (int)height, (int)(height/2)};       
        for(int i = 0; i < 6; i++) {
            if(h.edges[i].type == HexEdge.Type.WALL) {
                g2.drawLine(xArr[i], yArr[i], xArr[i+1], yArr[i+1]);
            }
        }        
    }
}
