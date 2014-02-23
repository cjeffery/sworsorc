import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.*;

public class MapView extends JComponent
                       implements Scrollable {
    Map map;
    private HexPainter hp;
    double radius, width, height;

    public MapView(Map map) {
        this.map = map;
        radius = 32;
        width  = radius*2;
        height = radius*Math.sqrt(3);
        hp = new HexPainter(radius);
    }
    
    /** return what Hex is under the given pixel coordinates.
     * See also: http://gamedev.stackexchange.com/a/20762 
     * This is about the trickiest hex math we're liable to see
     * my comments probably won't make much sense without the above article
     *
     * If you have to touch this method then I guess may god have mercy on your
     * sou-- erm, I mean, feel free to email clif7786
     */
    Hex hexAt(int x, int y) {
        /* cellWidth and cellHeight specify the dimensions of a hex-rectangle
         * i and j are indices into an array of hex-rectangles
         * u and v are the position in the hex-rectangle at i and j
         */
        double cellWidth = .75*width;
        double cellHeight = .5*height;
        int i = (int)(x / cellWidth);
        int j = (int)(y / cellHeight);
        int u = (int)(x % cellWidth);
        int v = (int)(y % cellHeight);
        
        int hexX = 0;
        int hexY = 0;

        //System.out.printf("i: %d, j: %d\n", i, j);
        //System.out.printf("u: %d, v: %d\n", u, v);
        /* If we're in the left 1/3 of a hex-rectangle, extra processing is
         * needed, as there's a diagonal and we need to know what side we're on
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
        hexX = i;
        //every other column is offset a little bit
        hexY = (j - i%2);
        //conditional is needed for detecting negative y coordinate hex clicks
        hexY = (hexY == -1) ? -1 : (hexY / 2);

        //System.out.printf("X: %d, Y: %d\n\n", hexX, hexY);
        if(   hexX >= 0 && hexX < map.columns()
           && hexY >= 0 && hexY < map.columnLength(hexX))
            return map.hexes[hexX][hexY];
        return null;
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
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        /* Get what hexes are in view */
        Rectangle hexRect = hexBounds(g2.getClip().getBounds());
       
        /* effective origin from whatever graphics object we're given */
        AffineTransform identity = g2.getTransform();
        /* For each hex, translate it and then draw it with HexPainter */
        for(int col = hexRect.x; col <= hexRect.x+hexRect.width; col++) {
            //translate to first hex in row that needs drawing
            g2.setTransform( identity );
            g2.translate(width*col*0.75, height*(hexRect.y + (col%2)*0.5));
            
            //determine the furthest hex that needs drawing in the row
            int max_row = (int)(hexRect.y+hexRect.height);
            max_row = Math.min(max_row, map.columnLength(col)-1);
            
            //draw all the hexes in the row
            for(int row = hexRect.y; row <= max_row; row++) {
                hp.paintHex(g2, map.hexes[col][row]);
                g2.translate(0, height);
            }
        }
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }
        
    /** Returns the actual size of the game map, in pixels */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension((int)(width*((map.columns()-1)*0.75+1)),
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
                                                     orientation, direction));
    }

    /** Scroll roughly one hex */
    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect,
                                          int orientation, int direction)
    {
        switch(orientation) {
            case SwingConstants.HORIZONTAL: return (int)(Math.ceil(0.75*width));
            case SwingConstants.VERTICAL: return (int)(Math.ceil(height));
        }
        assert false; 
        return (int)width;
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
