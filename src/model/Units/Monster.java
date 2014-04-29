package Units;

/* Monster.java

   Created by Ian

*/

public class Monster extends MoveableUnit
{
    int MagicPL;
    int MagicPotential;
    char MagicColor;
    int MagicResistance;
    int Fighting;
    int Endurance;
    int CombatStrength;
    char Owner;

    public Monster()
    {
        this.ResetWorkingMovement();
    }

    public int GetMagicPL()
    {
	return MagicPL;
    }

    public int GetMagicPoential()
    {
	return MagicPotential;
    }

    public char GetMagicColor()
    {
	return MagicColor;
    }

    public int GetMagicResistance()
    {
	return MagicResistance;
    }

    public int GetFighting()
    {
	return Fighting;
    }

    public int GetEndurance()
    {
	return Endurance;
    }

    public int GetCombatStrength()
    {
	return CombatStrength;
    }

    public char GetOwner()
    {
	return Owner;
    }

}
