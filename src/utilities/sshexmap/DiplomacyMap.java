package sshexmap;

import org.w3c.dom.Node;

/**
 * Email me if anyone has any questions or needs changes made.
 *
 * 
 * @author David Klingenberg,  klin7456@vandals.uidaho.edu 
 */
public class DiplomacyMap extends HexMap {
    private static DiplomacyMap INSTANCE;
    
    private DiplomacyMap(){
        super("resources/DiplomacyMap.xml");
        BuildMap();
    }

    @Override
    DiplomacyHex makeHex(Node h) {
        return new DiplomacyHex(h);
    }
    
    public static DiplomacyMap GetInstance(){
      if (INSTANCE == null)
          INSTANCE =  new DiplomacyMap();
      return INSTANCE;
    }

    @Override
    public int GetRows() {
        return 11;
    }

    @Override
    public int GetColumns() {
        return 11;
    }

    @Override
    public boolean LowFirstRow() {
        return true;
    }
}