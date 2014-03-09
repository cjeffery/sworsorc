package sshexmap;

import org.w3c.dom.Node;

/**
 * 
 *   Email me at klin7456@vandals.uidaho.edu if you hove questions or need help
 * 
 * @author David Klingenberg: 
 */
public class MainMap extends HexMap {
    private static MainMap INSTANCE;
    
    private MainMap(){
        super("MainMap.xml");
        BuildMap();
    }

    @Override
    MapHex makeHex(Node h) {
        return new MapHex(h);
    }
    
    public static MainMap GetInstance(){
      if (INSTANCE == null)
          INSTANCE =  new MainMap();
      return INSTANCE;
    }

    @Override
    public int GetRows() {
        return 54;
    }

    @Override
    public int GetColumns() {
        return 39;
    }

    @Override
    public boolean LowFirstRow() {
        return false;
    }
}
