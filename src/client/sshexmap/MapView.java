package sshexmap;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.IOException;
import java.awt.geom.AffineTransform;

/**
 * @author Colin Clifford
 */
public class MapView extends    JPanel
                     implements Scrollable {
    private HexMap map;
    private HexPainter hp;
    public TreeSet<String> highlightSet;
    double radius, width, height;
    
    static MapView instance;
    
    /**
     * Creates a new MapView class given a map to show.
     * @param map The map to show. Either a world map or diplomacy map
     */
    public MapView(HexMap map)  {
        highlightSet = new TreeSet<String>();
        this.map = map;
        radius = 32;
        width  = radius*2;
        height = radius*Math.sqrt(3);
        try {
            hp = new HexPainter(radius);   
        }
        catch(IOException e) {
            System.out.println("Hexpainter threw an IO exception :(");
        }
    }

    public static MapView getMapView()
    {
        if(instance == null)
            instance = new MapView(MainMap.GetInstance());
        return instance;
    }
    
    public static MapView getDipView()
    {
        MapView dip_map = new MapView(DiplomacyMap.GetInstance());
        return dip_map;
    }
    
    /** 
     * @param x The X pixel coordinate
     * @param y The Y pixel coordinate
     * @return The hex ID at the given coordinates or null */
    public String hexAt(int x, int y) {
        int[] hexc = hexCoords(x,y);
        int hexX = hexc[0], hexY = hexc[1];
        System.out.println("" + hexX + " " + hexY);
        return HexMap.GetIDFromCoords(hexX, hexY);
    }
    
    /** 
     * This method returns the direction to the closest hex-edge at the given 
     * pixel coordinates
     * 
     * @param x The X pixel coordinate
     * @param y The Y pixel coordinate
     * @return A number between 0-5 where 0 is northeast, 5 is southeast 
     */
    public int hexEdgeRegionAt(int x, int y) {
        int[] hexc = hexCoords(x,y);
        int hexX = hexc[0], hexY = hexc[1];
      
        //I can indulge in bad variable names on ocassion
        int antilow = map.LowFirstRow() ? 0 : 1; 
        double centerX =  width*( hexX*.75 - .25 );
        double centerY = height*( hexY - 0.5 + ((antilow+hexX)%2)*0.5);
        //y-coordinate has been flipped here for a "sensible" angle
        //since down is positive
        double angle = Math.atan2(centerY - y, x - centerX) + 2*Math.PI;
        angle %= 2*Math.PI;
        
        int region = (int)(angle*3 / Math.PI);
        return region;
    }
    
    /**
     * Calculate what hexes are contained in the specified clipping rect
     * in terms of hexagon coordinates.
     * @param bounds The bounds in pixel coordinates.
     * @return The bounds in hex coordinates.
    */
    private Rectangle hexBounds(Rectangle bounds)
    {
        int x1 = bounds.x, x2 = bounds.x + bounds.width;
        int y1 = bounds.y, y2 = bounds.y + bounds.height;

        int min_col = (int)((x1-width*.25)/(width*.75));
        int max_col = (int)(x2/(width*.75));
        int min_row = (int)((y1-0.5*height)/height);
        int max_row = 1 + (int)(y2/height);
        min_col = Math.max(min_col, 0);
        min_row = Math.max(min_row, 0);
        max_col = Math.min(max_col, map.GetColumns()-1);  
        max_row = Math.min(max_row, map.GetRows()-1);
        //TODO: uncomment this
        //return new Rectangle(        min_col,         min_row,
        //                     max_col-min_col, max_row-min_row);
        return new Rectangle(1, 1, map.GetColumns(), map.GetRows());
    }
        
    /** 
     * return the index of the hex under the given pixel coordinates 
     * For the math, see: http://gamedev.stackexchange.com/a/20762 
     * @param x The X pixel coordinate
     * @param y The Y pixel coordinate
     * @return An (x,y) pair representing the hex coordinates.
     */
    private int[] hexCoords(int x, int y) {
         /* cellWidth and cellHeight are the dimensions of a hex-rectangle
         * i and j are indices into an array of hex-rectangles
         * u and v are the position in the hex-rectangle at i and j
         */
        double cellWidth = .75*width;
        double cellHeight = .5*height;
        int i = (int)(x / cellWidth);
        int j = (int)(y / cellHeight);
        int u = (int)(x % cellWidth);
        int v = (int)(y % cellHeight);

        //System.out.printf("i: %d, j: %d\n", i, j);
        //System.out.printf("u: %d, v: %d\n", u, v);
        /* If we're in the left 1/3 of a hex-rectangle, extra processing is
         * needed, there's a diagonal and we need to know what side we're on
        */
        if(u < cellWidth/3.0) {
            //xp is the x percentage for how far into the green area x is
            //yp is similar for y
            double xp = u / (cellWidth*(1.0/3.0));
            double yp = v / cellHeight;
            //upper is true if the hex diagonal looks like '/'
            //upper is false if the hex diagonal looks like '\'
            boolean upper = (i % 2) == (j % 2);
            //System.out.printf("xp: %f, yp: %f\nupper: %b\n", xp,yp,upper);
            //if we're on the left side of the hex diagonal, we're secretly
            //one hex to the left
            if(    ( upper && (1-yp) > xp)
                || (!upper && yp > xp))
            {
                i--;
            }
        }
        int hexX = i;
        //every other column is offset a little bit
        int hexY = (j - i%2);
        //conditional is needed for detecting negative y coordinate hex
        hexY = (hexY == -1) ? -1 : (hexY / 2);
        int[] res = {hexX+1, hexY+1};
        return res;        
    }
    
    /**
     * highlight the set of given hexes. This adds to any previous highlights
     * to unhighlight use clearHighlights
     * Right now color is hardcoded, and you can't highlight multiple
     * colors at once. This could change in the future.
     * @param hexes A Set of hex IDs 
     */
    public void highlightIDs(ArrayList<String> hexes) {
        highlightSet.addAll(hexes);
        repaint(); //fixme allow partial update
    }
    
    /**
     * highlight the set of given hexes. This adds to any previous highlights
     * to unhighlight use clearHighlights
     * Right now color is hardcoded, and you can't highlight multiple
     * colors at once. This could change in the future.
     * @param hexes A Set of hexes
     */
    public void highlight(ArrayList<MapHex> hexes) {
        for(MapHex hex : hexes)
            highlightSet.add(hex.GetID());
        repaint(); //fixme allow partial update
    }
 
    /**
     * highlight the given hex. Adds to any previous highlights.
     * @param hex A hex ID to highlight
     */
    public void highlight(String hex) {
        highlightSet.add(hex);
        repaint(); //fixme allow partial update
    }
    
    /**
     * Clear all currently highlighted hexes
     */
    public void clearHighlights() {
        highlightSet.clear();
        repaint(); //fixme allow partial update
    }
    
    public void clearHighlight(String hex) {
        highlightSet.remove(hex);
        repaint(); //fixme allow partial update
    }
    
    /**
     * determine if the edge has already been drawn
     * if painting is happening down the columns, left to right
     * @param x hex x coordinate
     * @param y hex y coordinate
     * @param direction edge direction
     * @return true for needs drawing
     */
    boolean edgeNeedsDrawing(int x, int y, int direction) {
        if(direction == 0 || direction == 4 || direction == 5)
            return true;
        else if(direction == 1)
            return (y == 1);
        else if(direction == 2)
            return (y == 1) && ((x % 2) == 0);
        else if(direction == 3)
            return (x == 1);
        assert(false);
        return true;
    }
    
    /**
     * This method draws the hexmap as part of the java swing drawing
     * process.
     * It draws everything it needs to in several passes, first hexes,
     * then hex-edges, etc.
     * @param g the Graphics2D object to draw on.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        /* Get what hexes are in view */
        Rectangle hexRect = hexBounds(g2.getClip().getBounds());
        hexRect.x--;
        hexRect.y--;
        /* effective origin from whatever graphics object we're given */
        AffineTransform identity = g2.getTransform();

        /* For each hex, translate it and then draw it with HexPainter
         * first pass for hexes, second pass for hex edges */

        for(int pass = 0; pass < 3; pass++)
        for(int col = hexRect.x; col < hexRect.getMaxX(); col++) {
            //translate to first hex in row that needs drawing
            g2.setTransform( identity );
            int par = map.LowFirstRow() ? 1 : 0; //map "parity"
            g2.translate(width*col*0.75,
                         height*(hexRect.y + ((col%2)*0.5) - par*0.5));

            //draw all the hexes in the row
            for(int row = hexRect.y; row < hexRect.getMaxY(); row++) {
                if( map.GetHex(col+1,row+1) == null )
                    continue;
                
                //First pass: hexagons
                if(pass == 0)
                    hp.paintHex(g2, map.GetHex(col+1,row+1));

                //highlighting
                if(pass == 1)
                if(highlightSet.contains(HexMap.GetIDFromCoords(col+1,
                                                                row+1))){
                    hp.highlight(g2);
                }
                
                //edges
                if(pass == 2 && map instanceof MainMap ) {
                    for(int i = 0; i < 6; i++) {
                        if(edgeNeedsDrawing(col+1, row+1, i))
                            hp.paintEdge(g2, (MapHex)map.GetHex(col+1,row+1), i);
                    }
                }

                g2.translate(0, height);
            }
        }
    }

    /**
     * @return JScrollPane uses this to determine scrollbar dimensions.
     */        
    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }
    /**
     * @return The actual size of the map in pixels
     */
    public Dimension getPreferredSize() {
        //TODO: 350 is just a quick hack to prevent GUI textbox from obscuring
        //the lower tiles
        return new Dimension((int)(width*((map.GetColumns()-1)*.75+1)),
                             (int)(350+height*map.GetRows()));
    }

    /**
     * Scroll roughly one screens worth of hexes
     */
    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect,
                                           int orientation, int direction)
    {
        double coef = 1.0;
        if(orientation == SwingConstants.HORIZONTAL)
            coef = visibleRect.width / width;
        else
            coef = visibleRect.height / height;

        return (int)(coef*getScrollableUnitIncrement(visibleRect,
                                                     orientation,
                                                     direction));
    }

    /**
     * Return the rough amount to scroll for one unit amount
     * This isn't perfect because it returns an integer, and doesn't keep
     * track of leftover amounts. FIXME: JavaDoc generator doesn't recognize
     * parameter Rectangle.
     * 
     * @param visibleRect the visible area, a Rectangle
     * @param orientation either SwingConstants.HORIZONTAL
     *                    or SwingConstants.VERTICAL
     * @param direction currently unused. See java API docs for what it
     *                  means
     * @return the rough amount of pixelsto scroll for one unit amount */
    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect,
                                          int orientation, int direction)
    {
        if(orientation == SwingConstants.HORIZONTAL)
                return (int)(Math.ceil(0.75*width));
        else
                return (int)(Math.ceil(height));
    }

    /**
     * Currently unused.
     * @return false
     */
    @Override
    public boolean getScrollableTracksViewportHeight() {
    return false;
    }

    /**
     * Currently unused
     * @return false
     */
    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }
}