/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implement_spells;

import character.Character;
import javax.swing.JFrame;

/**
 *
 * @author 张涛
 */
public class Spell_Book {
    Spell[] list;
    
    JFrame mainFrame;
    
    Spell_Book(JFrame mainf, Character c) {
        mainFrame = mainf;
        list = new Spell[32];
        list[0]  = new Spell("Teleportation Protection", 1, 2, mainf,c);
        list[1]  = new Spell("Force Wall", 1, 2, mainf,c);
        list[2]  = new Spell("Conjure Zombie Infantry", 1, 1, mainf, c);
        
        list[3]  = new Spell("Manna Transfer", 2, 2, mainf, c);
        list[4]  = new Spell("River Crossing", 2, 2, mainf, c);
        list[5]  = new Spell("Morale", 2, 3, mainf,c);
        list[6]  = new Spell("Fear", 2, 3, mainf, c);
        list[7]  = new Spell("Conjure Centauroid Cavalry", 2, 1, mainf, c);
        
        list[8]  = new Spell("Monsoon", 3, 5, mainf, c);
        list[9]  = new Spell("Enhance Stature", 3, 1, mainf, c);
        list[10] = new Spell("Forest", 3, 2, mainf, c);
        list[11] = new Spell("Vortex Suppression", 3, 2, mainf, c);
        list[12] = new Spell("Immobilization", 3, 1, mainf, c);
        list[13] = new Spell("Conjure Wyvern Airtroops", 3, 1.5, mainf, c);
        list[14] = new Spell("Dispell Magicks", 3, 3, mainf, c);
        
        list[15] = new Spell("Disintegration", 4, 6, mainf, c);
        list[16] = new Spell("Building", 4, 5, mainf, c);
        list[17] = new Spell("Vortex Creation", 4, 2, mainf, c);
        list[18] = new Spell("Ersatz Winter", 4, 8, mainf, c);
        list[19] = new Spell("Teleportation Control", 4, 4, mainf, c);
        list[20] = new Spell("Conjure Koboldic Infantry", 4, 1, mainf, c);
        
        list[21] = new Spell("Planar Return", 5, 6, mainf, c);
        list[22] = new Spell("Summon Demon", 5, 8, mainf, c);
        list[23] = new Spell("Banish Conjured Troops", 5, 3, mainf, c);
        list[24] = new Spell("Conjure Wraith Troops", 5, 1.5, mainf, c);
        
        list[25] = new Spell("Bind Demon", 6, 4, mainf, c);
        list[26] = new Spell("Banish Demon", 6, 6, mainf, c);
        list[27] = new Spell("Summon Force", 6, 5, mainf, c);
        list[28] = new Spell("Firestorm", 6, 10, mainf, c);
        list[29] = new Spell("Berserkergang", 6, 2, mainf, c);
        list[30] = new Spell("Conjure Demonic Infantry", 6, 2, mainf, c);
        
        list[31] = new Spell("Wizard Wheel", 7, 6, mainf, c);
    }
    
    public Spell[] MySpells(Character character){
    //public Spell[] MySpells(Character x) {
        Spell[] myList;
        int nSpells = 0;
        //if( x.AvgMagicPL == 0) {
        if(character.MagicPL == 0){
            myList = new Spell[1];
            myList[0] = new Spell("void", 0, 0, mainFrame, character);
        } else {
            for(int i = 0; i < 32; i++) {
                //if( list[i].Level <= x.MagicPL+1 && list[i].ManaCost <= x.MannaLevel) {
                if(list[i].Level <= character.MagicPL+1 && list[i].ManaCost <= character.CurrentManna){
                    nSpells++;
                }
            }
            myList = new Spell[nSpells];
            int j = 0;
            for(int i = 0; i < 32; i++) {
                //if( list[i].Level <= x.MagicPL+1 && list[i].ManaCost <= x.MannaLevel) {
                if(list[i].Level <= character.MagicPL+1 && list[i].ManaCost <= character.CurrentManna){
                    myList[j] = list[i];
                    j++;
                }
            }
        }
        return myList;
    }
}
