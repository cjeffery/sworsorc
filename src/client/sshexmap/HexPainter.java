package sshexmap;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.imageio.*;
import ssterrain.*;

/** A class that does nothing but draw hexes! Right now it's somewhat closely
    tied to MapWidget, but if you ever need to draw hexes outside of the 
    map you could probably use or tweak it */
public class HexPainter {
    private final double hexRadius, width, height;
    private final Path2D.Double hexShape;
    private Map<String, BufferedImage> images;
    String path = "resources/images/";
    
    private void loadImages() throws IOException {
        Class<? extends HexPainter> c = getClass();
        String[] types = {
            "clear", "broken", "cultivated", "forest", "karoo", "mountain",
            "rough", "swamp", "vortex", "water", "woods"            
        };
        for(String s : types) {
            images.put(s, ImageIO.read(c.getResource( path + s + "_hex.png" )));
        }
    }
    public HexPainter(double hexRadius) throws IOException {
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

        Map<String, String> imageFiles = new HashMap<String, String>();
        imageFiles.put("Clear", "clear_hex.png");

        URL resource = getClass().getResource("mountains2.png");
        //mountainImage = ImageIO.read(resource);
    }

    /* Paint the specified hex onto the specified Graphics */
    public void paintHex(Graphics2D g2, Hex h) {
        if(h == null)
            return;
        if( h instanceof MapHex ) {
            paintTerrain(g2, (MapHex)h);
            //...
        }
        if( h instanceof DiplomacyHex ) {
            paintDiplomacyHex(g2, (DiplomacyHex)h);
            //...
        }
    }
    
    public void paintTerrain(Graphics2D g2, MapHex h) {
        ssterrain.TerrainType GetTerrain = h.GetTerrain();
        String str = t.toString().toLowerCase() + "_hex.png";
        if(!images.containsKey(str) || images.get(str) == null) {
            System.out.println("Image " + path + str + " wasn't loaded");
            return;
        }
        g2.drawImage(images.get(str), 0, 0, null);
    }
    
    public void paintDiplomacyHex(Graphics2D g2, DiplomacyHex h) {
    }
    
}
