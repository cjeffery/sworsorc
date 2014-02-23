public class Hex {
    public enum Terrain {
        CLEAR, FOREST, RIVER, MOUNTAIN;
        
        public String toString() {
            switch(this) {
                case CLEAR: return "Clear";
                case FOREST: return "Forest";
                case RIVER: return "River";
                case MOUNTAIN: return "Mountain";
            }
            assert false;
            return "ERROR";
        }
    }

    Terrain terrain;
}
