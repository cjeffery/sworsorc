/* CombatTable.java

   Ian Westrope
   CS 383
   2/17/14
*/

import java.*;

public class CombatTable
{
    public static int[] GetCombatResults(int DieRoll, double Atk, double Def)
    {
	double Ratio = Atk / Def;
	int[] ret = new int[2];

	if( Ratio == (1.0/5.0))
        {
	    switch(DieRoll)
	    {
	    case 1: ret[0] = 2;
		ret[1] = 0;
		break;
	    case 2: ret[0] = 3;
		ret[1] = 0;
		break;
	    case 3: ret[0] = 4;
		ret[1] = 0;
		break;
	    case 4: ret[0] = -1;
		ret[1] = 0;
		break;
	    case 5: ret[0] = -1;
		ret[1] = 0;
		break;
	    case 6: ret[0] = -1;
		ret[1] = 0;
		break;
	    default: System.out.println("bad dice roll");
		break;
	    }
	}

	if( Ratio == (1.0/4.0))
        {
	    switch(DieRoll)
	    {
	    case 1: ret[0] = 1;
		ret[1] = 0;
		break;
	    case 2: ret[0] = 2;
		ret[1] = 0;
		break;
	    case 3: ret[0] = 3;
		ret[1] = 0;
		break;
	    case 4: ret[0] = 4;
		ret[1] = 0;
		break;
	    case 5: ret[0] = -1;
		ret[1] = 0;
		break;
	    case 6: ret[0] = -1;
		ret[1] = 0;
		break;
	    default: System.out.println("bad dice roll");
		break;
	    }
	}

	if( Ratio == (1.0/3.0))
        {
	    switch(DieRoll)
	    {
	    case 1: ret[0] = 1;
		ret[1] = 1;
		break;
	    case 2: ret[0] = 1;
		ret[1] = 0;
		break;
	    case 3: ret[0] = 2;
		ret[1] = 0;
		break;
	    case 4: ret[0] = 3;
		ret[1] = 0;
		break;
	    case 5: ret[0] = 3;
		ret[1] = 0;
		break;
	    case 6: ret[0] = -1;
		ret[1] = 0;
		break;
	    default: System.out.println("bad dice roll");
		break;
	    }
	}

	if( Ratio == (1.0/2.0))
        {
	    switch(DieRoll)
	    {
	    case 1: ret[0] = 1;
		ret[1] = 1;
		break;
	    case 2: ret[0] = 1;
		ret[1] = 1;
		break;
	    case 3: ret[0] = 1;
		ret[1] = 0;
		break;
	    case 4: ret[0] = 2;
		ret[1] = 0;
		break;
	    case 5: ret[0] = 2;
		ret[1] = 0;
		break;
	    case 6: ret[0] = 3;
		ret[1] = 0;
		break;
	    default: System.out.println("bad dice roll");
		break;
	    }
	}

	if( Ratio == (2.0/3.0))
        {
	    switch(DieRoll)
	    {
	    case 1: ret[0] = 0;
		ret[1] = 1;
		break;
	    case 2: ret[0] = 1;
		ret[1] = 1;
		break;
	    case 3: ret[0] = 1;
		ret[1] = 1;
		break;
	    case 4: ret[0] = 1;
		ret[1] = 0;
		break;
	    case 5: ret[0] = 2;
		ret[1] = 0;
		break;
	    case 6: ret[0] = 2;
		ret[1] = 0;
		break;
	    default: System.out.println("bad dice roll");
		break;
	    }
	}

	if( Ratio == (1.0/1.0))
        {
	    switch(DieRoll)
	    {
	    case 1: ret[0] = 2;
		ret[1] = 1;
		break;
	    case 2: ret[0] = 0;
		ret[1] = 1;
		break;
	    case 3: ret[0] = 1;
		ret[1] = 1;
		break;
	    case 4: ret[0] = 1;
		ret[1] = 1;
		break;
	    case 5: ret[0] = 1;
		ret[1] = 0;
		break;
	    case 6: ret[0] = 2;
		ret[1] = 0;
		break;
	    default: System.out.println("bad dice roll");
		break;
	    }
	}

	if( Ratio == (3.0/2.0))
        {
	    switch(DieRoll)
	    {
	    case 1: ret[0] = 0;
		ret[1] = 2;
		break;
	    case 2: ret[0] = 2;
		ret[1] = 1;
		break;
	    case 3: ret[0] = 0;
		ret[1] = 1;
		break;
	    case 4: ret[0] = 1;
		ret[1] = 1;
		break;
	    case 5: ret[0] = 1;
		ret[1] = 1;
		break;
	    case 6: ret[0] = 1;
		ret[1] = 0;
		break;
	    default: System.out.println("bad dice roll");
		break;
	    }
	}
		
	if( Ratio == (2.0/1.0))
        {
	    switch(DieRoll)
	    {
	    case 1: ret[0] = 0;
		ret[1] = 3;
		break;
	    case 2: ret[0] = 0;
		ret[1] = 2;
		break;
	    case 3: ret[0] = 2;
		ret[1] = 1;
		break;
	    case 4: ret[0] = 0;
		ret[1] = 1;
		break;
	    case 5: ret[0] = 1;
		ret[1] = 1;
		break;
	    case 6: ret[0] = 1;
		ret[1] = 1;
		break;
	    default: System.out.println("bad dice roll");
		break;
	    }
	}

	if( Ratio == (3.0/1.0))
        {
	    switch(DieRoll)
	    {
	    case 1: ret[0] = 0;
		ret[1] = 4;
		break;
	    case 2: ret[0] = 0;
		ret[1] = 3;
		break;
	    case 3: ret[0] = 0;
		ret[1] = 3;
		break;
	    case 4: ret[0] = 1;
		ret[1] = 2;
		break;
	    case 5: ret[0] = 0;
		ret[1] = 1;
		break;
	    case 6: ret[0] = 1;
		ret[1] = 1;
		break;
	    default: System.out.println("bad dice roll");
		break;
	    }
	}

	if( Ratio == (4.0/1.0))
        {
	    switch(DieRoll)
	    {
	    case 1: ret[0] = 0;
		ret[1] = -1;
		break;
	    case 2: ret[0] = 0;
		ret[1] = 4;
		break;
	    case 3: ret[0] = 0;
		ret[1] = 3;
		break;
	    case 4: ret[0] = 0;
		ret[1] = 2;
		break;
	    case 5: ret[0] = 1;
		ret[1] = 2;
		break;
	    case 6: ret[0] = 0;
		ret[1] = 1;
		break;
	    default: System.out.println("bad dice roll");
		break;
	    }
	}

	if( Ratio == (5.0/1.0))
        {
	    switch(DieRoll)
	    {
	    case 1: ret[0] = 0;
		ret[1] = -1;
		break;
	    case 2: ret[0] = 0;
		ret[1] = -1;
		break;
	    case 3: ret[0] = 0;
		ret[1] = 4;
		break;
	    case 4: ret[0] = 0;
		ret[1] = 3;
		break;
	    case 5: ret[0] = 0;
		ret[1] = 2;
		break;
	    case 6: ret[0] = 1;
		ret[1] = 2;
		break;
	    default: System.out.println("bad dice roll");
		break;
	    }
	}

	if( Ratio == (6.0/1.0))
        {
	    switch(DieRoll)
	    {
	    case 1: ret[0] = 0;
		ret[1] = -1;
		break;
	    case 2: ret[0] = 0;
		ret[1] = -1;
		break;
	    case 3: ret[0] = 0;
		ret[1] = -1;
		break;
	    case 4: ret[0] = 0;
		ret[1] = 4;
		break;
	    case 5: ret[0] = 0;
		ret[1] = 3;
		break;
	    case 6: ret[0] = 0;
		ret[1] = 2;
		break;
	    default: System.out.println("bad dice roll");
		break;
	    }
	}

	return ret;
    }

	public static void main (String  Args[])
	{
	    int[] a = new int[2];
	    a = GetCombatResults(4, 2.0, 3.0);
	    System.out.println("Die roll of 4, atk of 2, def of 3");
	    System.out.println("Atack in table " + a[0]);
	    System.out.println("Def in table " + a[1]);
	}
}
