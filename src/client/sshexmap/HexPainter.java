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
    String path = "C:\\Users\\zoe\\Documents\\NetBeansProjects\\sorcery\\";
    
    private void loadImages() throws IOException {
        images = new TreeMap<String, BufferedImage>();
        Class<? extends HexPainter> c = getClass();
        String[] types = {
            "clear", "broken", "cultivated", "forest", "karoo", "mountains",
            "rough", "swamp", "vortex", "water", "woods", "dragon tunnel",
            "bridge", "portal", "city"
        };
        for(String s : types) {
            File f = new File( path + s + "_hex.png" );
            BufferedImage img = ImageIO.read(f);
            images.put(s + "_hex.png", img);
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

        loadImages();
    }

    /* Paint the specified hex onto the specified Graphics */
    public void paintHex(Graphics2D g2, Hex h) {
        if(h == null)
            return;
        if( h instanceof MapHex ) {
            paintTerrain(g2, (MapHex)h);
            paintImprovements(g2, (MapHex)h);
            //...
        }
        if( h instanceof DiplomacyHex ) {
            paintDiplomacyHex(g2, (DiplomacyHex)h);
            //...
        }
    }
    
    public void paintTerrain(Graphics2D g2, MapHex h) {
        TerrainType t = h.GetTerrain();
        if(t == null)
            return;
        String str = t.toString().toLowerCase() + "_hex.png";
        drawImage(g2, str);
    }
    
    public void paintImprovements(Graphics2D g2, MapHex h) {
        ArrayList<ImprovedTerrainType> improvements = h.getImprovements();
        for(ImprovedTerrainType i : improvements) {
            String str = i.toString().toLowerCase() + "_hex.png";
            drawImage(g2, str);
        }
    }
    
    private void drawImage(Graphics2D g2, String imageID) {
        if(!images.containsKey(imageID) || images.get(imageID) == null) {
            System.out.println("Image " + path + imageID + " wasn't loaded");
            return;
        }
        AffineTransform at = AffineTransform.getScaleInstance(.5, .5);
        //g2.drawImage(images.get(str), 0, 0, null);
        g2.drawRenderedImage(images.get(imageID), at);   
    }
    
    public void paintDiplomacyHex(Graphics2D g2, DiplomacyHex h) {
    }
    
}
