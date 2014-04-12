/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package sshexmap;

import java.util.*;

/**
 * simple wrapper class for provinces
 * give it a province name and it will give you a set of hexes
 * see example code at bottom
 * TODO: add unit tests
 */
class Provinces {
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
     * @return The set of provinces in a given hex
     */
    public static ArrayList<String> getHexList(String province_name) {
        if(provinceMap == null) {
            System.out.println("Did you call provinces before map was loaded?");
            return null;
        }
        ArrayList res = new ArrayList<String>();
        res.addAll( provinceMap.get(province_name) );
        return res;
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
