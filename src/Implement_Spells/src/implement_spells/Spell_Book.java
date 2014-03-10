/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implement_spells;

/**
 *
 * @author 张涛
 */
public class Spell_Book {
    Spell[] list;
    
    Spell_Book() {
        list = new Spell[32];
        list[0]  = new Spell("Teleportation Protection", 1, 2);
        list[1]  = new Spell("Force Wall", 1, 2);
        list[2]  = new Spell("Conjure Zombie Infanty", 1, 1);
        
        list[3]  = new Spell("Manna Transfer", 2, 2);
        list[4]  = new Spell("River Crossing", 2, 2);
        list[5]  = new Spell("Morale", 2, 3);
        list[6]  = new Spell("Fear", 2, 3);
        list[7]  = new Spell("Conjure Centauroid Cavalry", 2, 1);
        
        list[8]  = new Spell("Monsoon", 3, 5);
        list[9]  = new Spell("Enhance Stature", 3, 1);
        list[10] = new Spell("Forest", 3, 2);
        list[11] = new Spell("Vortex Suppression", 3, 2);
        list[12] = new Spell("Immobilization", 3, 1);
        list[13] = new Spell("Conjure Wyvern Airtroops", 3, 1.5);
        list[14] = new Spell("Dispell Magicks", 3, 3);
        
        list[15] = new Spell("Disintegration", 4, 6);
        list[16] = new Spell("Building", 4, 5);
        list[17] = new Spell("Vortex Creation", 4, 2);
        list[18] = new Spell("Ersatz Winter", 4, 8);
        list[19] = new Spell("Teleportation Control", 4, 4);
        list[20] = new Spell("Conjure Koboldic Infantry", 4, 1);
        
        list[21] = new Spell("Planar Return", 5, 6);
        list[22] = new Spell("Summon Demon", 5, 8);
        list[23] = new Spell("Banish Conjured Troops", 5, 3);
        list[24] = new Spell("Conjure Wraith Troops", 5, 1.5);
        
        list[25] = new Spell("Bind Demon", 6, 4);
        list[26] = new Spell("Banish Demon", 6, 6);
        list[27] = new Spell("Summon Force", 6, 5);
        list[28] = new Spell("Firestorm", 6, 10);
        list[29] = new Spell("Berserkergang", 6, 2);
        list[30] = new Spell("Conjure Demonic Infantry", 6, 2);
        
        list[31] = new Spell("Wizard Whell", 7, 6);
    }
    
    public Spell[] MySpells(int pl){
    //public Spell[] MySpells(Character x) {
        Spell[] myList;
        int nSpells = 0;
        //if( x.AvgMagicPL == 0) {
        if(pl == 0){
            myList = new Spell[1];
            myList[0] = new Spell("void", 0, 0);
        } else {
            for(int i = 0; i < 32; i++) {
                //if( list[i].Level <= x.MagicPL+1 && list[i].ManaCost <= x.MannaLevel) {
                if(list[i].Level <= pl+1){
                    nSpells++;
                }
            }
            myList = new Spell[nSpells];
            int j = 0;
            for(int i = 0; i < 32; i++) {
                //if( list[i].Level <= x.MagicPL+1 && list[i].ManaCost <= x.MannaLevel) {
                if(list[i].Level <= pl+1){
                    myList[j] = list[i];
                    j++;
                }
            }
        }
        return myList;
    }
}
