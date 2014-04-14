/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implement_spells.Spell_Details;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author 张涛
 */
public class spell_search {
    
    String inN; // the input name
    
    String Name;
    String PL;
    String Resistable;
    String Type;
    String MC;
    String Range;
    String Limits;
    String Effects;
    
    boolean found = false;
    
    public void search(String NameByIn){
        File f = new File("Spells_Details.txt");
        try (Scanner scanner = new Scanner(f);){
            while (scanner.hasNextLine() && found == false) {
                String line = scanner.nextLine();
                LineSplit(line, NameByIn);
                //scanner.nextLine(); // skip newline
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void LineSplit(String line, String NameByIn){
        StringTokenizer strTok = new StringTokenizer(line,";");
        
        if(strTok.hasMoreElements()){
            PL = (String) strTok.nextElement();
        }
        if(strTok.hasMoreElements()){
            Name = (String) strTok.nextElement();
        }
        if(Name.compareTo(NameByIn) == 0){
            found = true;
            
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
                Limits = (String) strTok.nextElement();
            }
            if(strTok.hasMoreElements()){
                Effects = (String) strTok.nextElement();
            }

            if(strTok.hasMoreElements()){
                System.out.println("error: data file error" );
            }
        }
    }
    
    public String printPL(){
        return PL;
    }
    
    public String printName(){
        return Name;
    }
    
    public String printResistable(){
        return Resistable;
    }
    
    public String printType(){
        return Type;
    }
    
    public String printMC(){
        return MC;
    }
    
    public String printRange(){
        return Range;
    }
    
    public String printLimits(){
        return Limits;
    }
    
    public String printEffects(){
        return Effects;
    }
}
