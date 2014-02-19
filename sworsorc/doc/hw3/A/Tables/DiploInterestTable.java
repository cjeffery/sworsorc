// DiploInterestTable.java
// Christopher Goes 2-17-14

//package cs383;

/**
 *
 * @author Tehlizard
 */
public class DiploInterestTable {
    
    public int getDiploResults( String neutral_race, String emissary_race )
    {
        switch(neutral_race.charAt(0))
        {
            case 'h':
            case 'H':
                switch(emissary_race.charAt(0))
                {
                    case 'h':
                    case 'H':
                        return 1;
                    case 'e':
                    case 'E':
                        return 1;
                    case 'g':
                    case 'G':
                    case 'd':
                    case 'D':
                        return 0;
                    case 'a':
                    case 'A':
                        return -1;
                    case 'c':
                    case 'C':
                    case 's':
                    case 'S':
                        return -2;
                    case 'o':
                    case 'O':
                        return -2;
                    default:
                        System.err.println("Error: inner switch reached default");
                }
            break;
                
            case 'e':
            case 'E':
                switch(emissary_race.charAt(0))
                {
                    case 'h':
                    case 'H':
                        return 1;
                    case 'e':
                    case 'E':
                        return 3;
                    case 'g':
                    case 'G':
                    case 'd':
                    case 'D':
                        return -2;
                    case 'a':
                    case 'A':
                        return -1;
                    case 'c':
                    case 'C':
                    case 's':
                    case 'S':
                        return -2;
                    case 'o':
                    case 'O':
                        return -2;
                    default:
                        System.err.println("Error: inner switch reached default");
                }
            break;
                
            case 'd':
            case 'D':
            case 'g':
            case 'G':
                switch(emissary_race.charAt(0))
                {
                    case 'h':
                    case 'H':
                        return 0;
                    case 'e':
                    case 'E':
                        return -2;
                    case 'g':
                    case 'G':
                    case 'd':
                    case 'D':
                        return 2;
                    case 'a':
                    case 'A':
                        return -1;
                    case 'c':
                    case 'C':
                    case 's':
                    case 'S':
                        return -1;
                    case 'o':
                    case 'O':
                        return -3;
                    default:
                        System.err.println("Error: inner switch reached default");
                }
            break;
                
            case 'a':
            case 'A':
                switch(emissary_race.charAt(0))
                {
                    case 'h':
                    case 'H':
                        return -2;
                    case 'e':
                    case 'E':
                        return -2;
                    case 'g':
                    case 'G':
                    case 'd':
                    case 'D':
                        return 0;
                    case 'a':
                    case 'A':
                        return -2;
                    case 'c':
                    case 'C':
                    case 's':
                    case 'S':
                        return 2;
                    case 'o':
                    case 'O':
                        return 1;
                    default:
                        System.err.println("Error: inner switch reached default");
                }
            break;
                
            case 'c':
            case 'C':
            case 's':
            case 'S':
                switch(emissary_race.charAt(0))
                {
                    case 'h':
                    case 'H':
                        return 2;
                    case 'e':
                    case 'E':
                        return 0;
                    case 'g':
                    case 'G':
                    case 'd':
                    case 'D':
                        return -1;
                    case 'a':
                    case 'A':
                        return 1;
                    case 'c':
                    case 'C':
                    case 's':
                    case 'S':
                        return 1;
                    case 'o':
                    case 'O':
                        return 2;
                    default:
                        System.err.println("Error: inner switch reached default");
                }
            break;
                
            case 'o':
            case 'O':
                switch(emissary_race.charAt(0))
                {
                    case 'h':
                    case 'H':
                        return -1;
                    case 'e':
                    case 'E':
                        return -1;
                    case 'g':
                    case 'G':
                    case 'd':
                    case 'D':
                        return -2;
                    case 'a':
                    case 'A':
                        return -1;
                    case 'c':
                    case 'C':
                    case 's':
                    case 'S':
                        return 2;
                    case 'o':
                    case 'O':
                        return 3;
                    default:
                        System.err.println("Error: inner switch reached default");
                }
            break;
                
            default:
                System.err.println("Error: outer switch reached default");
        }
        return -999;
    }
}
