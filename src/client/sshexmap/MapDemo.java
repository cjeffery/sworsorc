package sshexmap;

import Units.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.*;
import mainswordsorcery.HUD;
import sscharts.ScenarioConfigurationReader;

public class MapDemo implements MouseListener, KeyListener {
    private MapView mapView;
    public MapDemo() {
        JFrame window = new JFrame("Game Map"); 
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       
        mapView = new MapView(MainMap.GetInstance());
        JScrollPane scrollPane = new JScrollPane(mapView);
        window.add(scrollPane);

        UnitPool pool = UnitPool.getInstance();
        
        ArmyUnit unit = new LightSword();
        unit.setLocation("0101");
        pool.addUnit(0, unit, "0101");
        ArrayList<String> units = pool.getUnitsInHex("0101");
        if(units != null)
            for(String s : units)
                System.out.println(s);

        window.pack();
        window.setVisible(true);
    }

    public static void main(String[] args) {
        //ScenarioConfigurationReader scenario;
        //scenario = new ScenarioConfigurationReader("resources/scenarios/2_Dwarrows.json");
       
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MapDemo md = new MapDemo();
                md.mapView.addMouseListener(md);
                md.mapView.addKeyListener(md);
            }
        });
    }    

    @Override
    public void mouseClicked(MouseEvent e) {
        //mapView.requestFocusInWindow();
        String s = mapView.hexAt(e.getX(), e.getY());
        //mapView.hexEdgeRegionAt(e.getX(), e.getY());
        if(s == null)
            JOptionPane.showMessageDialog(null, "You clicked somewhere mysterious");
        else {
            mapView.highlight(s);
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e){}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    
    @Override public void keyPressed(KeyEvent e) {
        /*int key = e.getKeyCode();*/

    }

    @Override public void keyReleased(KeyEvent e) {}
}
