/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package sshexmap;

import java.util.*;
import ssterrain.ITTVortex;
import ssterrain.TTWater;
import ssterrain.TerrainType;

/**
 * simple wrapper class for provinces
 * give it a province name and it will give you a set of hexes
 * see example code at bottom
 * TODO: add unit tests. Make less horrifying(?)
 */
public class Provinces {
    //key is province ID
    //value is set of hex IDs in given province
    private static TreeMap<String, TreeSet<String>> provinceMap;
    
    /**
     * oh no singletons are taking over D:
     * no point in this constructor since everything is static
     */
    private Provinces() {}
    
    /**
     * add hex to province, used by XML loading code
     * @param hexID the ID of the hex to add
     * @param province_name the name of the province the hex belongs to
     */
    static void addHex(String hexID, String province_name) {
        if(provinceMap == null)
            provinceMap = new TreeMap<String, TreeSet<String>>();
        
        TreeSet<String> hexes = provinceMap.get(province_name);
        if(hexes == null) {
            hexes = new TreeSet<String>();
            provinceMap.put(province_name, hexes);
        }
        hexes.add(hexID);
    }
    
    /**
     * Get the set of hex IDs in a given province
     * @param province_name
     * @return The set of hexes in a given province
     */
    public static ArrayList<String> getHexList(String province_name) {
        if(provinceMap == null) {
            System.out.println("Did you call provinces before map was loaded?");
            return null;
        }
        //convert set to arraylist, because arraylist
        //is probably easier for people to work with
        ArrayList res = new ArrayList<String>();
        TreeSet<String> hexes = provinceMap.get(province_name);
        if(hexes == null)
            return null;
        res.addAll( provinceMap.get(province_name) );
        return res;
    }   
    
    /**
     * Get a random hexID string in a given Province set
     * 
     * @author Tyler Jaszkowiak
     * @param province_names a list of provinces to select from
     * @return A random hex ID string in the province set
     */
    public static String getRandHex(List<String> province_names) {
        if(provinceMap == null) {
            System.out.println("Did you call provinces before map was loaded?");
            return null;
        }
        // choose a random province from the list
        Random rand = new Random();
        int index = rand.nextInt(province_names.size());
        String province_name = province_names.get(index);
        
        //generate arraylist of hexes in the province
        ArrayList<String> res = new ArrayList<String>();
        TreeSet<String> hexes = provinceMap.get(province_name);
        if(hexes == null) {
            System.out.println("No hexes in province " + province_name);
            return null;
        }
        res.addAll( provinceMap.get(province_name) );
        
        // now get a random hex from that province
        index = rand.nextInt(res.size());
        String hexid = res.get(index);
        return hexid;
    }   
    
    /**
     * Get a random hexID string in a given Province set. This hex cannot be 
     * a water type
     * TODO: add check for vortices, bottomless plungehole
     * 
     * @author Tyler Jaszkowiak
     * @param province_names a list of provinces to select from
     * @return A random hex ID string in the province set
     */
    public static String getRandSafeHex(List<String> province_names) {
        if(provinceMap == null) {
            System.out.println("Did you call provinces before map was loaded?");
            return null;
        }
        // choose a random province from the list
        Random rand = new Random();
        int index = rand.nextInt(province_names.size());
        String province_name = province_names.get(index);
        
        //generate arraylist of hexes in the province
        ArrayList<String> res = new ArrayList<String>();
        TreeSet<String> hexes = provinceMap.get(province_name);
        if(hexes == null) {
            System.out.println("No hexes in province " + province_name);
            return null;
        }
        res.addAll( provinceMap.get(province_name) );
        
        // now get a random hex from that province
        index = rand.nextInt(res.size());
        String hexid = res.get(index);
        
        // recalculate if water chosen
        while (MainMap.GetInstance().GetHex(hexid).getTerrainType() instanceof TTWater) {
            index = rand.nextInt(res.size());
            hexid = res.get(index);
        }
        
        return hexid;
    }   
    
    /**
     * For debug purposes, return all the province names
     * loaded from the XML
     * @return The set of province names
     */
    public static Set<String> getNames() {
        if(provinceMap == null) {
            System.out.println("Did you call provinces before map was loaded?");
            return null;
        }
        return provinceMap.keySet();
    }
    
    public static void main(String args[]) {
        //load map so provinces will work
        MainMap m = MainMap.GetInstance();
        
        //print out all the provinces in the map
        //(use this to detect typos!)
        for(String s : Provinces.getNames()) {
            System.out.println(s);
        }
        
        //print out all the hexes belonging to The Swamps
        for(String s : Provinces.getHexList("The Swamps")) {
            System.out.println(s);
        }
    }
}
