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
    private final ArrayList<ArmyUnit> units;
    private String location;
    
    Stack(String x){
        units = new ArrayList();
        location = x;
    }
    
    public String getLocation(){
        return location;
    }
    
    public void setLocation(String x){
        location = x;
    }
    
    public void addUnit(ArmyUnit unit){
        units.add(unit);
    }
    
    public void removeUnit(int x){
        units.remove(x);
    }
    
    public ArmyUnit getUnit(int x){
        return units.get(x);
    }
    
    public void checkStack(){
        if(units.size() > 2){
            System.out.println("A unit needs to be removed!");
            System.out.println("Chose a unit to remove:");
        }
    }
    
    public void printUnits(){
        System.out.println("Units in this stack:");
        
        for(ArmyUnit unit: units){
            unit.printSelf();
        }
    }
}
