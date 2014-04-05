/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ObjectCreator;

import Units.ArmyUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class was built to turn a string into a class of the strings name.
 * It works as such: Sting i = "packagename.classname"
 * It will work with any type of object. When declaring the object the function
 * must be cast to the wanted type: 
 * ArmyUnit unit1 = (ArmyUnit)CreateObject(String)
 * 
 * This function returns null if the object was not found in the project.
 * 
 * Note: The class needs to be complied for the Creator to work. You may get an 
 * error about this if the class is not compiled.
 * 
 * @author Matt
 */

public class ObjectCreator {
    
    public static Object CreateObject(String type){
        try {
            try {
                return Class.forName(type).newInstance();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ObjectCreator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ObjectCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static void main(String [] args){ //This is here as an example to the object creator
        ArmyUnit unit1 = (ArmyUnit)CreateObject("Units.Bow");
        
        //ArmyUnit unit1 = new Bow();
        
        System.out.println(unit1.toString());
        System.out.println(unit1.getUnitType().toString());
        System.out.println(unit1.getStrength());
    System.out.println(unit1.getArmyUnitType());
        System.out.println(unit1.getMovement());
    }
    
}
