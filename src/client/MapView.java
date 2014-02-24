import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class MapView extends JPanel {
    /** The surface that MapView displays behind the JScrollPane.
     * only a subclass for the purposes of implementing scrollable
     */
    public class MapSurface extends JComponent
                            implements Scrollable {
        /** Paints the map surface by calling out to HexPainter for each hex
         * there are currently two passes, hexEdges get pained in the second
         * pass
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;

            /* Get what hexes are in view */
            Rectangle hexRect = hexBounds(g2.getClip().getBounds());

            /* effective origin from whatever graphics object we're given */
            AffineTransform identity = g2.getTransform();

            /* For each hex, translate it and then draw it with HexPainter
             * first pass for hexes, second pass for hex edges */
            for(int edge = 0; edge < 2; edge++)
            for(int col = hexRect.x; col <= hexRect.x+hexRect.width; col++) {
                //translate to first hex in row that needs drawing
                g2.setTransform( identity );
                g2.translate(width*col*0.75, height*(hexRect.y + (col%2)*0.5));

                //determine the furthest hex that needs drawing in the row
                int max_row = (int)(hexRect.y+hexRect.height);
                max_row = Math.min(max_row, map.columnLength(col)-1);

                //draw all the hexes in the row
                for(int row = hexRect.y; row <= max_row; row++) {
                    if(edge == 0)
                        hp.paintHex(g2, map.hexes[col][row]);
                    else
                        hp.paintEdges(g2, map.hexes[col][row]);
                    g2.translate(0, height);
                }
            }
        }

        /** Return the size for the scrollbars
         */
        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }
        /** return the size of the map surface */
        public Dimension getPreferredSize() {
            return new Dimension((int)(width*((map.columns()-1)*.75+1)),
                                 (int)(height*map.columnLength(0)));
        }

        /** Scroll roughly one screens worth of hexes */
        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect,
                                               int orientation, int direction)
        {
            double coef = 1.0;
            switch(orientation) {
                case SwingConstants.HORIZONTAL:
                    coef = visibleRect.width / width;
                    break;
                case SwingConstants.VERTICAL:
                    coef = visibleRect.height / height;
                    break;
            }
            return (int)(coef*getScrollableUnitIncrement(visibleRect,
                                                         orientation,
                                                         direction));
        }

        /** Scroll roughly one hex */
        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect,
                                              int orientation, int direction)
        {
            switch(orientation) {
                case SwingConstants.HORIZONTAL:
                    return (int)(Math.ceil(0.75*width));
                case SwingConstants.VERTICAL:
                    return (int)(Math.ceil(height));
            }
            assert false; 
            return 0;

        }

        /** @return false */
        @Override
        public boolean getScrollableTracksViewportHeight() {
        return false;
        }

        /** @return false */
        @Override
        public boolean getScrollableTracksViewportWidth() {
            return false;
        }
    }
    
    private Map map;
    private HexPainter hp;
    public MapSurface surface;
    double radius, width, height;    
   
    public MapView(Map map) {
        super(new BorderLayout());
        this.map = map;
        this.map = map;
        this.radius = 32;
        width  = radius*2;
        height = radius*Math.sqrt(3);
        hp = new HexPainter(radius);   
        surface = new MapSurface();
        
        JScrollPane scrollPane = new JScrollPane(surface);
        scrollPane.setPreferredSize(new Dimension(800,600));
        //scrollPane.setWheelScrollingEnabled(false);
        add(scrollPane);        
    }

    /** @return the hex at the given coordinates */
    public Hex hexAt(int x, int y) {
        int[] hexc = hexCoords(x,y);
        int hexX = hexc[0], hexY = hexc[1];
        
        //System.out.printf("X: %d, Y: %d\n\n", hexX, hexY);
        if(map.inBounds(hexX, hexY))
            return map.hexes[hexX][hexY];
        return null;
    }
    
    /** @return the hexEdge closest to the given coordinates */
    public HexEdge hexEdgeAt(int x, int y) {
        int[] hexc = hexCoords(x,y);
        int hexX = hexc[0], hexY = hexc[1];
        
        if(!map.inBounds(hexX, hexY))
            return null;
                
        double centerX = width*(0.5 + hexX*0.75);
        double centerY = height*(0.5+hexY + (hexX%2)*0.5);
        
        //y-coordinate has to be flipped here if a "sensible" angle is desired
        //since down is positive
        double angle = Math.atan2(centerY - y, x - centerX) + 2*Math.PI;
        angle %= 2*Math.PI;
        
        int region = (int)(angle*3 / Math.PI);
        //System.out.printf("centerX: %f, centerY: %f\n\n", centerX, centerY);
        System.out.printf("Angle: %f, region: %d\n",angle*57.2957795, region);
        return map.hexes[hexX][hexY].edges[region];
    }
    
    /** Calculate what hexes are contained in the specified clipping rect
        keep in mind that column lengths vary, this method assumes the first
        column's length.
    */
    private Rectangle hexBounds(Rectangle bounds)
    {
        int x1 = bounds.x, x2 = bounds.x + bounds.width;
        int y1 = bounds.y, y2 = bounds.y + bounds.height;

        int min_col = (int)((x1-width*.25)/(width*.75));
        int max_col = (int)(x2/(width*.75));
        int min_row = (int)((y1-0.5*height)/height);
        int max_row = (int)(y2/height);
        min_col = Math.max(min_col, 0);
        min_row = Math.max(min_row, 0);
        max_col = Math.min(max_col, map.columns()-1);  
        max_row = Math.min(max_row, map.columnLength(0)-1);
        return new Rectangle(        min_col,         min_row,
                             max_col-min_col, max_row-min_row);
    }
        
    /** return the index of the hex under the given pixel coordinates 
     * For the math, see: http://gamedev.stackexchange.com/a/20762 
     * 
     * This is about the trickiest hex math we're liable to see
     * my comments probably won't make much sense without the above article
     *
     * If you have to touch this method then I guess may god have mercy on 
     * your sou-- erm, I mean, feel free to email clif7786
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
        int[] res = {hexX, hexY};
        return res;        
    }
}
