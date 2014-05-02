/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package Units;

/**
 * This contains the nation of the movable unit. Not to be confused with the 
 * Race of the Unit.
 * @author Matthew
 * @author David Klingenberg
 */
    public enum Nation{
        Conjured        ("#FB353F"),
        Convivian       ("#CD4241"),
        CorfluCultist   ("#A67C89"),
        Cronk           ("#F92827"),
        Elven           ("#346045"),
        Goblin          ("#EE7D2F"),
        ImperialArmy    ("#3165B4"),
        IndependentHuman("#6A95C9"),
        Krasnian        ("#F63D42"),
        Mercenary       ("#C4C3BC"),
        none            ("#FFFFFF"),
        ORC             ("#FDDB41"),
        SpiderFolk      ("#FFFFFF"),
        SwampCreature   ("#A4B152"),
        WhiteORC        ("#F2F3E8"),
        Zirkastian      ("#C4C2BB");
        
        private final String uColor;
        private Nation(String uColor){
            this.uColor = uColor;    
        }
        public String color(){
            
            return uColor;
        }
    }
