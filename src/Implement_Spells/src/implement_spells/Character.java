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
public class Character {
    String  Name;
    // a character's magic power level may differ from his average MagicPL
    int     AvgMagicPL;
    int     MagicPL;
    int     MannaLevel;
    
    Character(String a, int b, int c) {
        Name        = a;
        AvgMagicPL  = b;
        MagicPL     = b;
        MannaLevel  = c;
    }
}
