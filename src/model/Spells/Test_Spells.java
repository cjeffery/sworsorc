/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package Spells;

import Character.Character;

/**
 *
 * @author TaoZhang
 */
public final class Test_Spells {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Implement_Spells is = new Implement_Spells();
        //is.getCharacter();
        //is.getSpellBook(pl, mp);
        //is.prepareGUI();
        
        //Character ch = new Character("Tao", 3, 3);
        //ch.CastSpell(ch);
        //System.out.println("Character CurrentManna: "+ ch.CurrentManna);
    
        //Character c = new Character();
        //c.getCharacter(c);
        //c.CastSpell(c);
        
       Character char1 = new Character("Matt", 7, 20, "11051");
        
        
        
        //char1.setMagicPL(0);
        
        char1.CastSpell();
    }
}
