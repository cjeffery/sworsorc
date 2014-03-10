/* Character.java
 *
 * @author Tyler
 * extended by Ian
 */


public class Character extends Monster {

    String  Name;
    char Race;
    char MagicColor;
    int HomeHex;
    int MagicProfile;
    int MagicResistance;
    int Leadership;
    int Diplomatic;
    int Fighting;
    int Endurance;
    int AvgMagicPL;
    int MagicPL;
    int MannaLevel;

    // something with talisman of orbs possibly an arrayList

    public Character()
    {
	
    }

    public void SetMannaLevel( int mana )
    {
	// add checking later
	MannaLevel = mana;
    }

    public int GetMannaLevel()
    {
	return MannaLevel;
    }

    public char GetRace()
    {
	return Race;
    }

    public char GetMagicColor()
    {
	return MagicColor;
    }

    public int GetHomeHex()
    {
	return HomeHex;
    }

    public int GetMagicProfile()
    {
	return MagicProfile;
    }

    public int GetMagicResistance()
    {
	return MagicResistance;
    }

    public int GetLeadership()
    {
	return Leadership;
    }

    public int GetDiplomatic()
    {
	return Diplomatic;
    }

    public int GetFighting()
    {
	return Fighting;
    }

    public int GetEndurance()
    {
	return Endurance;
    }

    public int GetAvgMagicPL()
    {
	return GetAvgMagicPL;
    }
    
    public int GetMagicPL()
    {
	return MagicPL;
    }

    public void GetSpells( /* don't know what needs to be passed probably mana and power level */ )
    {

    }

    public void CastSpell( /* don't know exactly what needs to be passed */ )
    {

    }
    
}
