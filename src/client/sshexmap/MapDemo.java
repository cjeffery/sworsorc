package sshexmap;

import MoveCalculator.MovementCalculator;
import Units.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class MapDemo extends JComponent implements MouseListener, KeyListener {
    private MapView mapView;
    private DiplomacyMap map;
    private UnitPool pool;
    private MoveableUnit selected_unit;
    private ArrayList<MapHex> canMoveTo;
    public MapDemo() {
        JFrame window = new JFrame("Game Map"); 
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        map = DiplomacyMap.GetInstance(); //MainMap.GetInstance();
        mapView = new MapView(map);
        JScrollPane scrollPane = new JScrollPane(mapView);
        window.add(scrollPane);

        pool = UnitPool.getInstance();
        
        ArmyUnit unit = new LightSword();
        unit.setRace(Race.Elves);
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
