import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import static javax.swing.WindowConstants.*;

public class MapWidget extends JComponent
                       implements Scrollable {
    Map map;
    private HexPainter hp;
    double radius, width, height;

    public MapWidget() {
        map = new Map();
        radius = 32;
        width  = radius*2;
        height = radius*Math.sqrt(3);
        hp = new HexPainter(radius);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        AffineTransform identity = g2.getTransform();
        for(int col = 0; col < map.columns(); col++) {
            g2.setTransform( identity );
            g2.translate(width*col*0.75, height*(col%2)*0.5);
            for(int row = 0; row < map.columnLength(col); row++) {
                hp.paintHex(g2, map.hexes[col][row]);
                g2.translate(0, height);
            }
        }
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }
        
    @Override
    public Dimension getPreferredSize() {
        return new Dimension((int)(width*((map.columns()-1)*0.75+1)),
                             (int)(height*map.columnLength(0)));
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect,
                                           int orientation, int direction)
    {
        //TODO
        return 32;
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect,
                                          int orientation, int direction)
    {
        //TODO
        return 32;
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

    public static void scrollDemo() {
        JFrame window = new JFrame("Scrolling Map Demo");
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        MapWidget mapWidget = new MapWidget();
        JScrollPane scrollPane = new JScrollPane(mapWidget);
        
        scrollPane.setPreferredSize(new Dimension(400,600));
        window.getContentPane().add(scrollPane);
        window.pack();
        window.setVisible(true);
    }
    public static void main(String[] args) {
        /* GUI needs to be started on event dispatch thread */
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                scrollDemo();
            }
        });
    }    
}
