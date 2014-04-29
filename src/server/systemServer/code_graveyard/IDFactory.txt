/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package systemServer;

/**
 *
 * @author John Goettsche
 
 creates a singleton IDFactory to insure that every id that is created 
 is unique.
 */
public class IDFactory {
    private static IDFactory instance = null;
    private int id = 0;
    
    private IDFactory(){
        
    }
    
    public static IDFactory getInstance(){
        if(instance == null) instance = new IDFactory();
        return instance;
    }
    
    public int getID(){
        return id++;
    }
}
