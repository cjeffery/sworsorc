package Units;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.imageio.*;
import sshexmap.HexPainter;
import ssterrain.*;

/** A class that does nothing but draw units! */
public class UnitPainter {
    private final double hexRadius, width, height;
    private final Path2D.Double hexMask;
    private Map<String, BufferedImage> images;
    String path = "resources/images/units/";
    
    private void loadImages() throws IOException {
        images = new TreeMap<String, BufferedImage>();
        String[] types = {
            "LightBow", "PikeMan", "HeavyHorse", "SpiderLegion", "WebWarriors",
            "LightHorse", "HeavyAxe", "Bow", "DemonicInfantry", "WraithTroops",
            "KoboldicInfantry", "WyvernAirtroops", "CentauroidCavalry",
            "ZombieInfantry", "HeavyPluglunk", "IntelligentMold",
            "DinosaurLegion", "HeavySword", "LightSword", "WargRider",
            "LightSpear", "MediumSpear", "HorseArcher", "Zeppelin", "RocRider"
        };
        for(String s : types) {            
            BufferedImage img = null;
            try {
                File f = new File( path + s + ".png" );
                img = ImageIO.read(f);
            } catch(IOException e) {
                File f = new File( path + "generic.png" );
                img = ImageIO.read(f);
            }
            images.put(s, img);
        }
    }
    
    /**
     * Constructs a unit painter, loads all relevant images
     * @param hexRadius The radius of a hexagon in pixels
     * @throws IOException 
     */
    public UnitPainter(double hexRadius) throws IOException {
        this.hexRadius = hexRadius;
        width  = hexRadius*2;
        height = hexRadius*Math.sqrt(3);
        //not sure if this number should be 6 or 7
        hexMask = HexPainter.hexShape(hexRadius);
        loadImages();
    }

    /**
     * Renders all the units in a hex
     * FIXME: WHAT ABOUT STACKS?
     * @param g2 The Graphics object to draw on (this will have the origin
                 AT the hex to draw on
     * @param units The units to draw
     */
    public void paintUnits(Graphics2D g2, ArrayList<MoveableUnit> units) {
        //HexPainter.drawImage(g2, "fighter.png", images);
        //only draw one unit for now
        if(units == null || units.size() == 0)
            return;
        MoveableUnit unit = units.get(0);
        paintUnit(g2, unit);
    }
    
    private void paintUnit(Graphics2D g2, MoveableUnit unit) {
        UnitType t = unit.getUnitType();
        if(t == null) {
            System.out.println("Cannot draw unit " + unit.toString()
                               + " as it has no unit type");
            return;
        }
        switch( unit.getUnitType() ) {
            case ArmyUnit:
                paintArmyUnit(g2, (ArmyUnit)unit);
                return;
            case Character:
                System.out.println("Drawing characters isn't supported yet");
                return;
            case Monster:
                System.out.println("Drawing monsters isn't supported yet");
                return;
        }
    }

    private Color getBGColor(MoveableUnit unit ) {
        //for now set bg color based on race
        //this isn't totally correct, but it's
        //hard to see what needs to be done from here
        Color bgColor;
        
        /* special cases */
        //conjured units are a variety of races
        if(unit instanceof Conjured || unit instanceof FlyingConjured) {
            /* conjured - red */
            bgColor = new Color(0xff4346);            
            return bgColor;
        }
        //roc riders are human color
        if(unit instanceof RocRider) {
            /* imperial - blue */ 
            bgColor = new Color(0x467ed1);                    
            return bgColor;
        }        

        switch( unit.getRace() ) {
            case Human:
                /* imperial - blue */ 
                bgColor = new Color(0x467ed1);
                break;
            case Elves:
                /* elves - dark green */
                bgColor = new Color(0x3f7750);
                break;
            case Orc:
                /* o.r.c - yellow */
                bgColor = new Color(0xfdd22e);
                break;
            case SwampCreature:
                /* swamp - light green */
                bgColor = new Color(0xaeb35f);
                break;
            case Cronk:
                /* cronk - orange */
                bgColor = new Color(0xFFA500);
                break;
            case Dragon:
            case Dwarrows:
            case KillerPenguin:
            case Spiders:
            default:
                /* hell if I know - white */
                bgColor = new Color(0xffffff);
                break;            
        }
        return bgColor;
    }
    
    private String getArmyUnitStatusLine(ArmyUnit unit) {
        boolean defense_halved;
        defense_halved = (unit instanceof HeavyHorse);
        int strength = unit.isDemoralized() ? unit.getDemoralizedStrength()
                                            : unit.getStrength();        
        return "" + (defense_halved ? "(" : "")
                  + strength
                  + (defense_halved ? ")" : "")
                  +      unit.getRaceCode()
                  + (int)unit.getMovement();
    }
    
    private void paintArmyUnit(Graphics2D g2, ArmyUnit unit) {
        Color bgColor = getBGColor(unit);
        String status = getArmyUnitStatusLine(unit);
        
        //used for positioning text
        //no idea how fast this is, it could be cached if needed
        FontMetrics fm = g2.getFontMetrics();
        int status_w = fm.stringWidth( status );
        //int status_h = fm.getHeight();
        
        g2.setColor( bgColor ); 
        g2.fill(hexMask);
        
        HexPainter.drawImage(g2, unit.toString(), images);
        
        g2.setColor( Color.BLACK );
        g2.drawString(status,
                      (int)(width/2.0 - status_w/2.0),
                      (int)(height - 8 ));        
    }
}


