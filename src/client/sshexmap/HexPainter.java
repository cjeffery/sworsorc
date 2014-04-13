package sshexmap;

import Units.ArmyUnit;
import Units.MoveableUnit;
import Units.UnitPainter;
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
    private final Path2D.Double hexMask;
    private Map<String, BufferedImage> images;
    String path = "resources/images/hex/";
    
    private UnitPainter up;
    
    private void loadImages() throws IOException {
        images = new TreeMap<String, BufferedImage>();
        String[] types = {
            "clear", "broken", "cultivated", "forest", "karoo", "mountains",
            "rough", "swamp", "vortex", "water", "woods", "dragon tunnel",
            "glacier",
            "bridge", "portal", "city", "castle", "mount greymoor",
            "glade keep"
            //"gateway of evil", "balkathos"
        };
        for(String s : types) {
            File f = new File( path + s + "_hex.png" );
            BufferedImage img = ImageIO.read(f);
            images.put(s + "_hex.png", img);
        }
        
        //hax for special hexes. make them look like castles until they're ready
        String[] special = {
            "gateway of evil", "balkathos", "citadel of blood", 
            "gateway to the empire", "castle of brandon", 
            "battleaxe stronghold", "the ruins", "castle krawn",
            "the bottomless plungehole", "citadel of ice",
            "city of urf durfal"
        };
        for(String s : special) {
            File f = new File( path + "castle" + "_hex.png" );
            BufferedImage img = ImageIO.read(f);
            images.put(s + "_hex.png", img);
        }             
    }
    
    /**
     * Constructs a hex painter, loads all relevant images
     * @param hexRadius The radius of a hexagon in pixels
     * @throws IOException 
     */
    public HexPainter(double hexRadius) throws IOException {
        this.hexRadius = hexRadius;
        width  = hexRadius*2;
        height = hexRadius*Math.sqrt(3);
        //not sure if this number should be 6 or 7
        hexMask = hexShape(hexRadius);

        loadImages();
        
        up = new UnitPainter(hexRadius);
    }
    
    public static Path2D.Double hexShape(double hexRadius) {
        double w  = hexRadius*2;
        double h = hexRadius*Math.sqrt(3);
        Path2D.Double hex_shape = new Path2D.Double(Path2D.WIND_NON_ZERO, 7);
        hex_shape.moveTo(0,      h*0.5);
        hex_shape.lineTo(w*0.25, 0    );
        hex_shape.lineTo(w*0.75, 0    );
        hex_shape.lineTo(w,      h*0.5);
        hex_shape.lineTo(w*0.75, h    );
        hex_shape.lineTo(w*0.25, h    );
        hex_shape.closePath();     
        return hex_shape;
    }

    /**
     * Renders a single hex, not including edges or highlighing
     * @param g2 The Graphics object to draw on
     * @param h The hex to draw
     */
    public void paintHex(Graphics2D g2, Hex h) {
        if(h == null)
            return;
        if( h instanceof MapHex ) {
            paintTerrain(g2, (MapHex)h);
            paintImprovements(g2, (MapHex)h);
            paintRoad(g2, (MapHex)h);
            paintUnits(g2, (MapHex)h);
            //paintEdges(g2, (MapHex)h, edgeMask);
            //...
        }
        if( h instanceof DiplomacyHex ) {
            paintDiplomacyHex(g2, (DiplomacyHex)h);
            //...
        }
    }
    
    public void paintUnits(Graphics2D g2, MapHex h) {
        ArrayList<MoveableUnit> units = h.getUnits();
        if(units != null && units.size() != 0) {
            up.paintUnits(g2, units);
        }

    }
        
    /**
     * Renders the terrain of a hex
     * @param g2 The Graphics object to draw on
     * @param h  The hex to draw
     */
    public void paintTerrain(Graphics2D g2, MapHex h) {
        TerrainType t = h.getTerrainType();
        if(t == null)
            return;
        String str = t.toString().toLowerCase() + "_hex.png";
        if(h.GetHexName() != null) {
            switch(h.GetHexName()) {
                //certain named hexes maybe(?) can just be drawn as default
                case "Toll Troll":
                    break;
                //otherwise load a specific image for them
                default:
                    str = h.GetHexName().toLowerCase() + "_hex.png";
            }
        }
        drawImage(g2, str, images);
    }
    
    public void paintRoad(Graphics2D g2, MapHex h) {
        Color c = new Color(150, 75, 0);
        for(int t = 0; t < 2; t++) {
            Stroke s;
            HexEdgeType type;
            float dash[] = { 3.0f };
            if(t == 0){
                type = HexEdgeType.Trail;
                //c = new Color(0, 0, 0);
                s = new BasicStroke(3, BasicStroke.CAP_BUTT,
                                       BasicStroke.JOIN_MITER,
                                       1.0f, dash, 0.0f);
            }
            else {
                type = HexEdgeType.Road;
                //c = new Color(0, 0, 0);
                s = new BasicStroke(3);      
            }

            ArrayList<Integer> roads = new ArrayList<Integer>();
            for(int i = 0; i < 6; i++) {
                HexEdge e = h.getEdge(i);
                if( e.contains( type ) )//|| e.contains( HexEdgeType.Trail ) )
                    roads.add(i);            
            }

            //if(roads.size() == 2) {
            double cx = width/2.0, cy = height/2.0;
            for(Integer dir : roads) {
                double x = 0, y = 0;
                switch(dir) {
                    case 0: x = width*0.875; y = height*0.25; break;
                    case 1: x = width*0.50;  y = 0;           break;
                    case 2: x = width*0.125; y = height*0.25; break;
                    case 3: x = width*0.125; y = height*0.75; break;
                    case 4: x = width*0.50;  y = height;      break;
                    case 5: x = width*0.875; y = height*0.75; break;
                }
                g2.setColor( c );
                g2.setStroke( s );
                g2.drawLine((int)x, (int)y, (int)cx, (int)cy);
            }

        }
    }
    
    /**
     * Renders the improvments of a hex. TODO: not totally sure what this
     * entails yet to be honest.
     * @param g2 The Graphics object to draw on
     * @param h The hex to draw
     */
    public void paintImprovements(Graphics2D g2, MapHex h) {
        ArrayList<ImprovedTerrainType> improvements = h.getImprovements();
        for(ImprovedTerrainType i : improvements) {
            String str = i.toString().toLowerCase() + "_hex.png";
            drawImage(g2, str, images);
        }
    }
    
    /**
     * Render the edges of a hex.
     * @param g2 The Graphics object to draw on
     * @param hex The hex to draw (Maybe make this a MapHex?)
     */
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
            HexEdge edge = h.getEdge(i);
            for(EdgeElement e : edge.elements.values()) {
                if(e != null) {
                    paintEdge(g2, e.getEdgeType(), x1, y1, x2, y2);
                }
                //else System.out.println( edgeTypes.size() );
                
            }
        }        
    }
    
    /**
     * Renders a simple edge type between the given coordinates
     * @param g2 The Graphics object to draw on
     * @param edge The edge to draw
     * @param x1
     * @param y1
     * @param x2
     * @param y2 
     */
    public void paintEdge(Graphics2D g2, HexEdgeType edge,
                          double x1, double y1, double x2, double y2) {
        switch(edge) {
            case ProvinceBorder:
                g2.setColor( Color.RED );
                g2.setStroke(new BasicStroke(3));
                g2.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
                break;
            case Stream:
                g2.setColor( Color.BLUE );
                g2.setStroke(new BasicStroke(3));
                g2.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
                break;
            case Wall:
                g2.setColor( Color.BLACK );
                g2.setStroke(new BasicStroke(3));
                g2.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
                break;
            case Gate:
                 g2.setColor( Color.LIGHT_GRAY );
                g2.setStroke(new BasicStroke(3));
                g2.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
                break;               
            default:
                //System.out.println("unhandled paintEdge case " + edge);
        }
    }
    
    /**
     * Renders an image onto the given Graphics object at the origin
     * @param g2 The Graphics object to draw on
     * @param imageID  The key for the image collection
     */
    public static Boolean drawImage(Graphics2D g2, String imageID,
                                    Map<String, BufferedImage> imageMap) {
        if(!imageMap.containsKey(imageID) || imageMap.get(imageID) == null) {
            System.out.println("Image " + imageID + " wasn't loaded");
            return false;
        }
        //TODO: coefficients should be based on radius (and zoom?)
        AffineTransform at = AffineTransform.getScaleInstance(.5, .5);
        //g2.drawImage(images.get(str), 0, 0, null);
        g2.drawRenderedImage(imageMap.get(imageID), at);
        return true;
    }
    
    public void paintDiplomacyHex(Graphics2D g2, DiplomacyHex h) {
        if(h.GetIsPlayerHex()) {
            String s = "";
            switch(h.GetID()) {
                case "0601": s = "A"; break;
                case "1104": s = "B"; break;
                case "1109": s = "C"; break;
                case "0611": s = "D"; break;
                case "0109": s = "E"; break;
                case "0104": s = "F"; break;
            }
            g2.setColor( new Color(255, 127, 127) );
            g2.fill(hexMask);
            g2.setColor( Color.BLACK );
            g2.drawString(s, (int)(width / 2), (int)(height / 2));
        }
        else if(h.GetIsNeturalHex()) {
            g2.setColor( Color.RED );
            g2.fill(hexMask);
            g2.setColor( Color.BLACK );
            g2.drawString("Neutral", (int)(width / 4), (int)(height / 2));
        }
        else
            drawImage(g2, "clear_hex.png", images);
        
    }
    
    /**
     * Draws a hexagon shaped highlight at the origin
     * @param g2 The graphics object to draw on
     */
    public void highlight(Graphics2D g2) {
        g2.setColor( new Color(0,0,255, 70) );
        g2.fill(hexMask);
    }
}
