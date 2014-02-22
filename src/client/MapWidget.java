import javax.swing.*;
import java.awt.Dimension;
public class MapWidget extends JLabel
                       implements Scrollable {
	/** The width (in pixels) of a game hex. */
	double hexWidth() { return 16*2; }

	/** The height (in pixels) of a game hex. */
	double hexWidth() { return 16*Math.sqrt(3); }

	@Override public void paintComponent(Graphics g) {
		

	@Override Dimension getPreferredScrollableViewportSize() {
		//TODO: what dimension is preferred? I just made something up
		return new Dimension(400, 600);
	}
	/** Todo: this one is a bit tricky because of hex widths and heights
	    and such */
	@Override int getScrollableBlockIncrement(Rectangle visibleRect,
	                                          int orientation, int direction)
	{
		//TODO
		return 1;
	}
	/** @return false */
	@Override boolean getScrollableTracksViewportHeight() {	return false; }
	/** @return false */
	@Override boolean getScrollableTracksViewportWidth()  { return false; }

	public static void scrollDemo() {
		window = new JFrame("Scrolling Map Demo");

    public static void main(String[] args) {
        /* GUI needs to be started on event dispatch thread */
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                scrollDemo();
            }
        });
    }    
}
