import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;


public class ScrollDemo implements MouseListener {
    private MapView mapView;
    public ScrollDemo() {
        JFrame window = new JFrame("Scrolling Map Demo"); 
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
     
        mapView = new MapView(new Map());
        
        JScrollPane scrollPane = new JScrollPane(mapView);
        scrollPane.setPreferredSize(new Dimension(800,600));
        window.add(scrollPane);        
        window.pack();
        window.setVisible(true);
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ScrollDemo sd = new ScrollDemo();
                sd.mapView.addMouseListener(sd);
            }
        });
    }    

    @Override
    public void mouseClicked(MouseEvent e) {
        Hex h = mapView.hexAt(e.getX(), e.getY());
        if(h == null)
            JOptionPane.showMessageDialog(null, "You clicked somewhere mysterious");
        else
            JOptionPane.showMessageDialog(null, "You clicked on a " +
                                                h.terrain.toString());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
