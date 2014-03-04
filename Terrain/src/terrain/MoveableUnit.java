/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package terrain;

public class MoveableUnit {
    public MoveableUnit( int initMovement, String unitRace )
    {
        baseMove = initMovement;
        race = unitRace;
    }
private int baseMove;
private String race;

    public String GetRace()
    {
        return race;
    }
    public int GetBaseMovement()
    {
        return baseMove;
    }
}
