/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spellindex;

/**
 *
 * @author Tyler
 */
public class Spell {
    String  Name;
    int     Level;
    double  Cost;
    
    Spell(String a, int b, double c) {
        Name  = a;
        Level = b;
        Cost  = c;        
    }
    
    public void Print() {
        System.out.println(Name + ", Level: " + Level + ", Cost: " + Cost);
    }
    
}
