/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package systemServer;

/**
 *
 * @author John
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IDFactory factory = IDFactory.getInstance();
        System.out.println(factory.getID());
        IDFactory factory2 = IDFactory.getInstance();
        System.out.println(factory2.getID());
    }
    
}
