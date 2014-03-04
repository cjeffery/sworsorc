/*
 * Bob Ross - Dr. Jeffery - CS 383 - Feb 2014
 */
package terrain;

public class Character {
    public Character( int baseMove, boolean caster, String charRace )
    {
        baseMovement = baseMove;
        magicUserFlag = caster;
        race = charRace;
    }
    private int baseMovement;
    private boolean magicUserFlag;
    private String race;

    public int getBaseMovement()
    {
        return baseMovement;
    }
    public boolean GetCasterFlag()
    {
        return magicUserFlag;
    }
    public String GetRace()
    {
        return race;
    }
}
