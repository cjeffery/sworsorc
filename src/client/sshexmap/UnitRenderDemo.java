package sshexmap;

import MoveCalculator.MovementCalculator;
import Units.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class UnitRenderDemo implements MouseListener, KeyListener {
    private MapView mapView;
    private MainMap map;
    private UnitPool pool;
    private MoveableUnit selected_unit;
    private ArrayList<MapHex> canMoveTo;
    public UnitRenderDemo() {
        JFrame window = new JFrame("Game Map"); 
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        map = MainMap.GetInstance();
        mapView = new MapView(map);
        JScrollPane scrollPane = new JScrollPane(mapView);
        window.add(scrollPane);

        pool = UnitPool.getInstance();
        
        ArmyUnit[] human = {
            new PikeMan(), new HeavyHorse(), new LightBow(), new HeavySword(),
            new RocRider(), new LightHorse(), new Zeppelin()
        };
        ArmyUnit[] elven = {
            new HorseArcher(), new LightBow(), new LightHorse(),
            new LightSword(), new MediumSpear()
        };
        ArmyUnit[] orc = {
            new LightSpear(), new WargRider(), new HeavySword(), new Bow(),
            new LightSword()
        };
        ArmyUnit[] goblin = {
            new LightSpear()
        };
        ArmyUnit[] swamp = {
            new HeavySword(), new DinosaurLegion(), new IntelligentMold(),
        };
        ArmyUnit[] cronk = {
            new HeavyPluglunk(), new Bow(), new LightHorse()
        };
        ArmyUnit[] conjured = {
            new ZombieInfantry(), new CentauroidCavalry(),
            new WyvernAirtroops(), new KoboldicInfantry(), new WraithTroops(),
            new DemonicInfantry()     
        };
        ArmyUnit[] dwarvish = {
            new Bow(), new HeavyAxe(), new LightHorse()
        };
        ArmyUnit[] arachnid = {
            new WebWarriors(), new SpiderLegion()
        };
        
        ArmyUnit[][] armies = {human, elven, orc, goblin, swamp, cronk,
                               conjured, dwarvish, arachnid};
        Race[] races = {Race.Human, Race.Elves, Race.Orc, Race.Goblins,
                        Race.SwampCreature, Race.Cronk, Race.Human,
                        Race.Dwarrows, Race.Spiders};
        int x = 1, y = 1;
        Random r = new Random();
        for(int i = 0; i < armies.length; i++) {
            y = 1;
            ArmyUnit[] army = armies[i];
            Race race = races[i];
            
            for(ArmyUnit u : army) {
                if(u instanceof WyvernAirtroops)
                    u.setRace(Race.Dragon);
                else if(u instanceof KoboldicInfantry)
                    u.setRace(Race.Dwarrows);
                else if(u instanceof CentauroidCavalry)
                    u.setRace(Race.Elves);
                else
                    u.setRace(race);
                u.setDemoralized(r.nextBoolean());
                pool.addUnit(1, u, HexMap.GetIDFromCoords(x, y));
                y++;
            }

            x++;
        }
        
        ArmyUnit unit = new PikeMan();
        unit.setRace(Race.Human);
        pool.addUnit(0, unit, "0101");

        window.pack();
        window.setVisible(true);
    }

    public static void main(String[] args) {
        //ScenarioConfigurationReader scenario;
        //scenario = new ScenarioConfigurationReader("resources/scenarios/2_Dwarrows.json");
       
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UnitRenderDemo md = new UnitRenderDemo();
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
