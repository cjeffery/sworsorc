package Units;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.imageio.*;
import sshexmap.HexPainter;
import ssterrain.*;

/** A class that does nothing but draw hexes! Right now it's somewhat closely
    tied to MapWidget, but if you ever need to draw hexes outside of the 
    map you could probably use or tweak it */
public class UnitPainter {
    private final double hexRadius, width, height;
    private final Path2D.Double hexMask;
    private Map<String, BufferedImage> images;
    String path = "resources/images/units/";
    
    private void loadImages() throws IOException {
        images = new TreeMap<String, BufferedImage>();
        String[] types = {
            "fighter.png"
        };
        for(String s : types) {
            File f = new File( path + s );
            BufferedImage img = ImageIO.read(f);
            images.put(s, img);
        }
    }
    
    /**
     * Constructs a unit painter, loads all relevant images
     * @param hexRadius The radius of a hexagon in pixels
     * @throws IOException 
     */
    public UnitPainter(double hexRadius) throws IOException {
        this.hexRadius = hexRadius;
        width  = hexRadius*2;
        height = hexRadius*Math.sqrt(3);
        //not sure if this number should be 6 or 7
        hexMask = HexPainter.hexShape(hexRadius);
        loadImages();
    }

    /**
     * Renders all the units in a hex
     * FIXME: WHAT ABOUT STACKS?
     * @param g2 The Graphics object to draw on (this will have the origin
                 AT the hex to draw on
     * @param units The units to draw
     */
    public void paintUnits(Graphics2D g2, ArrayList<MoveableUnit> units) {
        HexPainter.drawImage(g2, "fighter.png", images);
    }
}
