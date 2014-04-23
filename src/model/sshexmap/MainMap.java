package sshexmap;

import org.w3c.dom.Node;

/**
 * 
 *   Email me at klin7456@vandals.uidaho.edu if you have questions or need help
 *   Or Colin at clif7786@vandals.uidaho.edu
 * @author David Klingenberg: 
 */
public class MainMap extends HexMap {
    private static MainMap INSTANCE;
    
    private MainMap(){
        super("resources/MainMap.xml");
    }

    @Override
    MapHex makeHex(Node h) {
        return new MapHex(h);
    }
    
    public MapHex GetHex(String id) {
        return (MapHex)super.GetHex(id);
    }
    
    public static MainMap GetInstance(){
      if (INSTANCE == null) {
          INSTANCE =  new MainMap();
          INSTANCE.BuildMap();
      }
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
