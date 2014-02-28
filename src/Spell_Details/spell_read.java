/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spelldetails;

import java.util.Scanner;

/**
 *
 * @author 张涛
 */
public class spell_read {
    
    String Name = null;
    
    public void getName(){
        System.out.println("Please enter the Spell Name that you wish to read: ");
        Scanner in = new Scanner(System.in);
        Name = in.nextLine();
    }
    
    public String printName(){
        return Name;
    }
   
}
