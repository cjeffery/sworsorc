/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SandS.spell.table;

import java.util.*;
import java.io.*;

/**
 *
 * @author Joe Higley
 */
public class Spell {
    private final String name;
    private final Boolean resistable;
    private final int powerLvl;
    private final char type;
    private final int manaCost;
    private final int range;
    
    public Spell(){
        name = null;
        resistable = null;
        powerLvl = '\0';
        type = '\0';
        manaCost = '\0';
        range = '\0';                  
    }
    
    public Spell(String n, int res, int p, char t, int m, int r){
        name = n;
        powerLvl = p;
        type = t;
        manaCost = m;
        range = r;
        if(res != 0)
            resistable = true;
        else
            resistable = false;
    }
    
    public static void setupTable(List<Spell> list) throws IOException{
        Scanner inFile = new Scanner(new FileReader("spellTable.txt"));
        int p, m, r, res;
        char t;
        String n;
        
        while(inFile.hasNext()){
            p = inFile.nextInt();
            System.out.println(p);            
            m = inFile.nextInt();
            System.out.println(m);
            r = (int) inFile.nextInt();
            System.out.println(r);
            res = (int) inFile.nextInt();
            System.out.println(res);
            t = inFile.next().charAt(0);
            System.out.println(t);
            n = inFile.nextLine();
            System.out.println(n);
            
            list.add(new Spell(n, res, p, t, m, r));
        }
    }
    
    public int getPowerLvl(Spell spell){
        return spell.powerLvl;
    }
    
    public String getName(Spell spell){
        return spell.name;
    }
    
    public Boolean getResistable(Spell spell){
        return spell.resistable;
    }
    
    public char getType(Spell spell){
        return spell.type;
    }
    
    public int getManaCost(Spell spell){
        return spell.manaCost;
    }
    
    public int getRange(Spell spell){
        return spell.range;
    }
    
    public static void printTable(List<Spell> list) {
        System.out.println("PowerLvl \tSpellName \tResistable \tType \tManaCost \tRange");
        System.out.println("-----------------------------------------------------------------------------------------------");
        for (Spell curr : list){
            System.out.print(curr.powerLvl);
            System.out.print("\t");
            System.out.print(curr.name);
            System.out.print("\t");
            System.out.print(curr.resistable);
            System.out.print("\t");
            System.out.print(curr.type);
            System.out.print("\t");
            System.out.print(curr.manaCost);
            System.out.print("\t");
            System.out.print(curr.range);
            System.out.print("\n");
            

        }
    }
    
    public static void main(String args[]) throws IOException{
        List<Spell> spellList = new ArrayList<>();
        setupTable(spellList);
        printTable(spellList);
    }
    
    
        
}
