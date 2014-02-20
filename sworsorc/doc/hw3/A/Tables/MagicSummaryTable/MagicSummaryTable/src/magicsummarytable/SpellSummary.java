package magicsummarytable;

/**
 *
 * @author Gabe Pearhill
 */
public class SpellSummary {
    private String name;
    private char type;
    private String mannaCost;
    private int range;
    private boolean resistable;
    
    public SpellSummary(String nameIn, boolean resistableIn, char typeIn, 
                String mannaCostIn, int rangeIn){
        name = nameIn;
        type = typeIn;
        mannaCost = mannaCostIn;
        range = rangeIn;
        resistable = resistableIn;
    }
    
    public SpellSummary(){
        
    }
    
    public void setName(String nameIn){
        name = nameIn;
    }
    public void setType(char typeIn){
        type = typeIn;
    }
    public void setMannaCost(String cost){
        mannaCost = cost;
    }
    public void setRange(int rangeIn){
        range = rangeIn;
    }
    public String getName(){
        return name;
    }
    public char getType(){
        return type;
    }
    public String getMannaCost(){
        return mannaCost;
    }
    public int getRange(){
        return range;
    }
}
