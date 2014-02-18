
import java.util.*;
import java.io.*;
/**
 *
 * @author Tao Zhang
 */
public class MagicSearch {

    /**
     * @param args the command line arguments
     */
    
    int PowerLevel;
    
    String Name;
    String Resistable;
    String Type;
    String MC;
    String Range;
    
    public static void main(String[] args) {
        MagicSearch ms = new MagicSearch();
        
        ms.getResults();
    }
    
    public void getResults(){
        getPL();
        generateResults();
    }
    
    public void getPL(){
        System.out.println("Please enter the PowerLevel(1~7) that you wish to read: ");
        Scanner in = new Scanner(System.in);
        PowerLevel = in.nextInt();
        if(PowerLevel > 7 || PowerLevel <1){
            getPL();
        }
    }
    
    public void generateResults(){
        int c = PowerLevel;
        System.out.println("Here is the list of spells for PL " + c);
        
        String Heading1 = "Name";
        String Heading2 = "Resistability";
        String Heading3 = "Type";
        String Heading4 = "Mana Cost";
        String Heading5 = "Range";
        
        System.out.printf("%-25s %15s %5s %15s %15s %n", Heading1, Heading2, Heading3, Heading4, Heading5);
        System.out.println("=============================================================================");
        ReadScanFile(c);
    }
    
    public void ReadScanFile(int c){
        String file_name = null;
        switch(c){
            case 1: file_name = "PowerLevelOne.txt";
                    break;
            case 2: file_name = "PowerLevelTwo.txt";
                    break;
            case 3: file_name = "PowerLevelThree.txt";
                    break;
            case 4: file_name = "PowerLevelFour.txt";
                    break;
            case 5: file_name = "PowerLevelFive.txt";
                    break;
            case 6: file_name = "PowerLevelSix.txt";
                    break;    
            case 7: file_name = "PowerLevelSeven.txt";
                    break;
            default:
                    System.out.println("error: MagicSearch:getPL - invalid number");
                    break;                
        }
        
        
        File f = new File(file_name);
        try {
 
            Scanner scanner = new Scanner(f);
 
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                PrintPL(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }
    
    public void PrintPL(String line){
        StringTokenizer strTok = new StringTokenizer(line,",");
        
        if(strTok.hasMoreElements()){
            Name = (String) strTok.nextElement();
        }
        if(strTok.hasMoreElements()){
            Resistable = (String) strTok.nextElement();
        }
        if(strTok.hasMoreElements()){
            Type = (String) strTok.nextElement();
        }
        if(strTok.hasMoreElements()){
            MC = (String) strTok.nextElement();
        }
        if(strTok.hasMoreElements()){
            Range = (String) strTok.nextElement();
        }
        
        if(strTok.hasMoreElements()){
            System.out.println("error: data file error" );
        }
        
        
        System.out.printf("%-25s %15s %5s %15s %15s %n", Name, Resistable, Type, MC, Range);
    }
    
    
}
