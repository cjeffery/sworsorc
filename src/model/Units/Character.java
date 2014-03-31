/* Character.java
 *
 * @author Tyler
 * extended by Ian
 */


public class Character extends MoveableUnit {

    protected double CurrentMana;
    protected String Name;
    protected int MagicPotential;
    protected int MagicLevel;
    protected int Attack;
    protected bool Demorailzed;

    public Character()
    {
	
    }

    public bool getDemoralized()
    {
	return Demoralized;
    }

    public void setDemoralized(bool D)
    {
	Demoralized = D;
    }

    public int getMagicPotential()
    {
	return MagicPotential;
    }

    public int getMagicLevel()
    {
	return MagicLevel;
    }

    public int getAttack()
    {
	return Attack;
    }

    public void setMagicPotential(int MP)
    {
	this.MagicPotential = MP;
    }

    public void setMagicLevel(int ML)
    {
	this.MagicLevel = ML;
    }

    public void setAttack(int A)
    {
	this.Attack = A;
    }

    public void CastSpell(Character c)
    {
	Implement_Spells ImpSpell = new Implement_Spells(c);
	ImpSpell.getSpellBook();
    }
    
    public void CostManna(double mc)
    {
	CurrentManna -= mc;
    }
    
}
