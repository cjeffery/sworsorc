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
public class SpellIndex {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Character fry, leela;
        SpellBook book = new SpellBook();
        
        Spell[] frybook;
        Spell[] leelabook;
        
        fry = new Character("Philip J. Fry", 0, 3);
        leela = new Character("Turanga Leela", 3, 2);
        
        frybook = book.MySpells(fry);
        leelabook = book.MySpells(leela);
        
        System.out.println("Fry's Spellbook:");
        for (Spell frybook1 : frybook) {
            frybook1.Print();
        }
        System.out.println("\nLeela's Spellbook:");
        for (Spell leelabook1 : leelabook) {
            leelabook1.Print();
        }
        
        leela.MagicPL++;
        leelabook = book.MySpells(leela);
        System.out.println("\nLeela's Spellbook after increment in MPL:");
        for (Spell leelabook1 : leelabook) {
            leelabook1.Print();
        }
        
    }
    
}
