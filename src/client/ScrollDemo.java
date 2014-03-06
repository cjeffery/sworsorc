import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class ScrollDemo implements MouseListener, KeyListener {
    private MapView mapView;
    public ScrollDemo() {
        JFrame window = new JFrame("Scrolling Map Demo"); 
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        try {
            mapView = new MapView(DiplomacyMap.GetInstance());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "IOException: " +
                                          ex.getMessage()+" "+ex.getCause());
        }
        
        window.add(mapView);
        window.pack();
        window.setVisible(true);
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ScrollDemo sd = new ScrollDemo();
                sd.mapView.surface.addMouseListener(sd);
                sd.mapView.surface.addKeyListener(sd);
            }
        });
    }    

    @Override
    public void mouseClicked(MouseEvent e) {
        mapView.requestFocusInWindow();
        Hex h = mapView.hexAt(e.getX(), e.getY());
        //mapView.hexEdgeRegionAt(e.getX(), e.getY());
        if(h == null)
            JOptionPane.showMessageDialog(null, "You clicked somewhere mysterious");
        else
            JOptionPane.showMessageDialog(null, "You clicked on a " +
                                                h.GetID());
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e){}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    
    @Override public void keyPressed(KeyEvent e) {
        /*int key = e.getKeyCode();
        if(key == KeyEvent.VK_EQUALS) {
            mapView.setZoom( mapView.getZoom() + 0.1 );
        }
        else if(key == KeyEvent.VK_MINUS) {
            mapView.setZoom( mapView.getZoom() - 0.1);
        }*/
    }

    @Override public void keyReleased(KeyEvent e) {}
}
