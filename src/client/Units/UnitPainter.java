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
            "LightSpear", "MediumSpear", "HorseArcher", "Zeppelin", "RocRider",
            "stack_badge", "demoralized_badge"
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
        boolean stacked = units.size() > 1;
        paintUnit(g2, unit, stacked );
    }
    
    private void paintUnit(Graphics2D g2, MoveableUnit unit, boolean stacked) {
        UnitType t = unit.getUnitType();
        if(t == null) {
            System.out.println("Cannot draw unit " + unit.toString()
                               + " as it has no unit type");
            return;
        }
        switch( unit.getUnitType() ) {
            case ArmyUnit:
                paintArmyUnit(g2, (ArmyUnit)unit, stacked);
                return;
            case Character:
                System.out.println("Drawing characters isn't supported yet");
                return;
            case Monster:
                System.out.println("Drawing monsters isn't supported yet");
                return;
        }
    }

    /* only display null nation error output one time */
    boolean error_displayed = false;
    /**
     * gets background color corresponding to unit's nation.
     * Used to render the unit's background.
     * @return A color object representing the RGB color of the unit's nation
     */
    private Color getBGColor(MoveableUnit unit ) {
        Color c;
        int a = 127; //the alpha value
        if(unit.getNation() == null) {
            if(error_displayed == false) {
                System.out.println("UnitPainter.java - unit's nation is null");
                error_displayed = true;
            }
            return new Color(0xff, 0xff, 0xff, a);
        }
        switch(unit.getNation()) {
            case Conjured:         c = new Color(0xff, 0x43, 0x46, a); //red
            //case Convivian:
            //case CorfluCultist:
            case Cronk:            c = new Color(0xff, 0xa5, 0x00, a); //orange
                
            //dark green
            case Elven:            c = new Color(0x3f, 0x77, 0x50, a);
                
            //case Goblin:
            case ImperialArmy:     c = new Color(0x46, 0x7e, 0xd1, a); //blue
            //case IndependentHuman:
            //case Krasnian:
            //case Mercenary:
            case ORC:              c = new Color(0xfd, 0xd2, 0x2e, a); //yellow
            //case SpiderFolk:
                
            //light green
            case SwampCreature:    c = new Color(0xae, 0xb3, 0x5f, a);
                
            //case WhiteORC:
            //case Zirkastian:
            default:
                ; //System.out.println("UnitPainter.java - unhandled nation");
            case none:             c = new Color(0xff, 0xff, 0xff, a); //white 

        }
        return c;
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
    
    private void paintArmyUnit(Graphics2D g2, ArmyUnit unit, boolean stacked) {
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
        
        if(stacked)
            HexPainter.drawImage(g2, "stack_badge", images );
        if(unit.isDemoralized())
            HexPainter.drawImage(g2, "demoralized_badge", images );            
        
        g2.setColor( Color.BLACK );
        g2.drawString(status,
                      (int)(width/2.0 - status_w/2.0),
                      (int)(height - 8 ));        
    }
}


