import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/** I didn't want MapWidget to get too cluttered with the nitty gritty of
	drawing hexes. So I wrote a class that does NOTHING BUT draw hexes!
	Amazing! */
public class HexPainter {
	private final double hexRadius, width, height;
	private final Path2D.Double hexShape;

	public HexPainter(double hexRadius) {
		this.hexRadius = hexRadius;
		width  = hexRadius*2;
		height = hexRadius*Math.sqrt(3);
		hexShape = new Path2D.Double(Path2D.WIND_NON_ZERO, 7);
		hexShape.moveTo(0,          height*0.5);
		hexShape.lineTo(width*0.25, 0);
		hexShape.lineTo(width*0.75, 0);
		hexShape.lineTo(width,      height*0.5);
		hexShape.lineTo(width*0.75, height);
		hexShape.lineTo(width*0.25, height);
		hexShape.closePath();
	}
		
	public void paintHex(Graphics2D g2, Hex h) {
		switch(h.terrain) {
			case CLEAR:  g2.setColor( new Color(245, 245, 220) ); break;
			case FOREST: g2.setColor( Color.GREEN );              break;
			case RIVER:  g2.setColor( Color.BLUE );               break;
		}
		g2.fill(hexShape);
	}
}
