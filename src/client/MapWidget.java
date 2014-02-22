import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class MapWidget extends JPanel
                       implements Scrollable {
	Map map;
	private HexPainter hp;
	double radius, width, height;

	public MapWidget() {
		map = new Map();
		hp = new HexPainter(16);
		radius = 16;
		width  = radius*2;
		height = radius*Math.sqrt(3);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

		AffineTransform identity = new AffineTransform();
		for(int col = 0; col < map.columns(); col++) {
			g2.setTransform( identity );
			//these numbers came to me in a vision quest, and are sacrosanct
			g2.translate(width*col*0.75, height*(col%2)*0.5 - height);
			for(int row = 0; row < map.columnLength(col); row++) {
				g2.translate(0, height);
				hp.paintHex(g2, map.hexes[col][row]);
			}
		}
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		//TODO: what dimension is preferred? I just made something up
		return new Dimension(400, 600);
	}

	/** Todo: this one is a bit tricky because of hex widths and heights
	    and such */
	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect,
	                                       int orientation, int direction)
	{
		//TODO
		return 1;
	}

	/** Todo: this one is a bit tricky because of hex widths and heights
	    and such */
	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect,
                                          int orientation, int direction)
	{
		//TODO
		return 1;
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
		MapWidget mapWidget = new MapWidget();
		window.getContentPane().add(mapWidget);
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
