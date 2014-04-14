/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Units;

import java.util.ArrayList;

/**
 *
 * @author matt
 */
public class Stack {
    private final ArrayList<String> units;
    private int location;
    
    Stack(int x){
        units = new ArrayList();
        location = x;
    }
    
    public int getLocation(){
        return location;
    }
    
    public void setLocation(int x){
        location = x;
    }
    
    public void addUnit(String unit){
        units.add(unit);
    }
    
    public void removeUnit(int x){
        units.remove(x);
    }
    
    public String getUnit(int x){
        return units.get(x);
    }
    
    public void checkStack(){
        if(units.size() > 2){
            System.out.println("A unit needs to be removed!");
            System.out.println("Chose a unit to remove:");
            for(String unit: units){
                
            }
        }
    }
}
