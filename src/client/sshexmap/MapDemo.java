package sshexmap;

import MoveCalculator.MovementCalculator;
import Units.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class MapDemo implements MouseListener, KeyListener {
    private MapView mapView;
    private MainMap map;
    private UnitPool pool;
    private MoveableUnit selected_unit;
    private ArrayList<MapHex> canMoveTo;
    public MapDemo() {
        JFrame window = new JFrame("Game Map"); 
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        map = MainMap.GetInstance();
        mapView = new MapView(map);
        JScrollPane scrollPane = new JScrollPane(mapView);
        window.add(scrollPane);

        pool = UnitPool.getInstance();
        
        ArmyUnit unit = new LightSword();
        pool.addUnit(0, unit, "0606");
        //ArrayList<String> units = pool.getUnitsInHex("0606");
        //if(units != null)
        //    for(String s : units)
        //        System.out.println(s);

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
        String hexID = mapView.hexAt(e.getX(), e.getY());
        MapHex hex = map.GetHex(hexID);
        System.out.println("===START===");
        if(selected_unit == null) {
            System.out.println("no unit selected");
            if(hex.getUnits() == null) {
                System.out.println("no units in hex");
                return;
            }
            System.out.println("highlighting moves");
            canMoveTo = new ArrayList<MapHex>();
            ArrayList<ArmyUnit> units = hex.getUnits();
            selected_unit = units.get(units.size()-1); //hack for unitpool, list can contain nulls..
            System.out.println("Selected " + selected_unit + ", size of stack: " + hex.getUnits().size() );
            canMoveTo.clear();
            MovementCalculator.getValidMoves(selected_unit, hex, 5, canMoveTo );
            mapView.highlight(canMoveTo);
        }
        else if( canMoveTo.contains(hex) ) {
            System.out.println("unit selected, can move to destination");
            //hack because unitpool isn't finished
            //pool.clearPool();
            
            mapView.clearHighlights();
            pool.addUnit(0, (ArmyUnit)selected_unit, hex.GetID());
            mapView.repaint();
            selected_unit = null;
        }
        else {
            System.out.println("unit sleected, cannot move to destination");
        }
        //JOptionPane.showMessageDialog(null, "" + res.size() );      
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
