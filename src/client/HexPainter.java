import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.imageio.*;

/** A class that does nothing but draw hexes! Right now it's somewhat closely
    tied to MapWidget, but if you ever need to draw hexes outside of the 
    map you could probably use or tweak it */
public class HexPainter {
    private final double hexRadius, width, height;
    private final Path2D.Double hexShape;
    private Map<String, BufferedImage> images;

    private void loadImages() throws IOException {
        Class<? extends HexPainter> c = getClass();
        images.put("Clear", ImageIO.read(c.getResource( "clear_hex.png" )));
        
        /* TODO  "blasted_hex.png", "broken_hex.png", "city_hex.png",
            "cultivated_hex.png", "tunnel_hex.png",
            "forest_hex.png", "glacier_hex.png", "karoo_hex.png",
            "water_hex.png", "mountain_hex.png", "portal_hex.png",
            "rough_hex.png", "road_hex.png", "swamp_hex.png",
            "vortex_hex.png", "woods_hex.png" */
        
            /*"waterbridge_hex.png", "capital_hex.png", "castle_hex.png",
              "ford_hex.png", "town_hex.png" */ 
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

    public void paintDiplomacyHex(Graphics2D g2, DiplomacyHex h) {
        g2.setColor( new Color(245, 245, 220) );
        g2.fill(hexShape);        
    }
    
    public void paintMapHex(Graphics2D g2, MapHex h) {
        switch(h.GetTerrainDescription()) {
            case "Clear":
            default:
                g2.setColor( new Color(245, 245, 220) );
                g2.fill(hexShape); 
        }
    }
    /* Paint the specified hex onto the specified Graphics */
    public void paintHex(Graphics2D g2, Hex h) {
        if(h == null)
            return;
        if( h instanceof DiplomacyHex )
            paintDiplomacyHex(g2, (DiplomacyHex)h);
        else
            paintMapHex(g2, (MapHex)h);
    }

/* TODO: (also these would be better / faster 
          as an enum, but am I motivated enough to do that??? maybe later) 
"Blasted" "Bridge Over Water" "Broken" "Capital" "Castle" "City"
"Cultivated" "Dragon Tunnel Complex" "Ford" "Forest" "Glacier" "Karoo" 
"Moat/River/Lake" "Mountains" "Portal" "Rough" "Road" "Special Hex" "Swamp"
"Town" "Vortex" "Woods"
*/
    
    public void paintEdges(Graphics2D g2, Hex h) {
        /*g2.setColor( Color.BLACK );
        g2.setStroke( new BasicStroke(3) );
        int[] xArr = {(int)width, (int)(width*0.75), (int)(width*0.25), 0,
                    (int)(width*0.25), (int)(width*0.75), (int)width};
        int[] yArr = {(int)(height/2), 0, 0, (int)(height/2), (int)height,
                    (int)height, (int)(height/2)};       
        for(int i = 0; i < 6; i++) {
            if(h.edges[i].type == HexEdge.Type.WALL) {
                g2.drawLine(xArr[i], yArr[i], xArr[i+1], yArr[i+1]);
            }
        } */       
    }
}
