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
            "bridge", "portal", "city", "mount greymoor"
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
            //paintEdges(g2, (MapHex)h, edgeMask);
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
        if(h.GetHexName() != null) {
            switch(h.GetHexName()) {
                case "Toll Troll":
                    break;
                default:
                    str = h.GetHexName().toLowerCase() + "_hex.png";
            }
        }
        drawImage(g2, str);
    }
    
    public void paintImprovements(Graphics2D g2, MapHex h) {
        ArrayList<ImprovedTerrainType> improvements = h.getImprovements();
        for(ImprovedTerrainType i : improvements) {
            String str = i.toString().toLowerCase() + "_hex.png";
            drawImage(g2, str);
        }
    }
    
    public void paintEdges(Graphics2D g2, Hex hex) {
        if( !(hex instanceof MapHex) )
            return;
        MapHex h = (MapHex)hex;
        for(int i = 0; i < 6; i++) {
            //if(!( (edgeMask & (1 << i)) == 0))
            //    continue;
            double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
            /* TODO remove duplicate coordinate code */
            switch(i) {
                case 0: 
                    x1 = width*.75; x2 = width;
                    y1 = 0;         y2 = height/2;
                    break;
                case 1:
                    x1 = width*.25; x2 = width*.75;
                    y1 = 0;         y2 = 0;
                    break;
                case 2:
                    x1 = 0;         x2 = width*.25;
                    y1 = height/2;  y2 = 0;
                    break;
                case 3:
                    x1 = 0;         x2 = width*.25;
                    y1 = height/2;  y2 = height;
                    break;
                case 4:
                    x1 = width*.25; x2 = width*.75;
                    y1 = height;    y2 = height;
                    break;
                case 5: 
                    x1 = width*.75; x2 = width;
                    y1 = height;    y2 = height/2;
                    break;
            }
            ArrayList<HexEdgeType> edgeTypes = h.getEdgeType(i);
            if(edgeTypes == null)
                return;
            for(HexEdgeType e : edgeTypes) {
                if(e != null) {
                    paintEdge(g2, e, x1, y1, x2, y2);
                }
                //else System.out.println( edgeTypes.size() );
                
            }
        }        
    }
    
    public void paintEdge(Graphics2D g2, HexEdgeType edge, double x1, double y1, double x2, double y2) {
        switch(edge) {
            case ProvinceBorder:
                g2.setColor( Color.RED /*new Color(245, 245, 220)*/ );
                g2.setStroke(new BasicStroke(3));
                g2.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
                //g2.drawLine(0,0, 200, 200);
                break;
            default:
                System.out.println("unhandled paintEdge case " + edge);
        }
    }
    
    private void drawImage(Graphics2D g2, String imageID) {
        /*if(!images.containsKey(imageID) || images.get(imageID) == null) {
            System.out.println("Image " + path + imageID + " wasn't loaded");
            return;
        }*/
        AffineTransform at = AffineTransform.getScaleInstance(.5, .5);
        //g2.drawImage(images.get(str), 0, 0, null);
        g2.drawRenderedImage(images.get(imageID), at);   
    }
    
    public void paintDiplomacyHex(Graphics2D g2, DiplomacyHex h) {
    }
    
}
