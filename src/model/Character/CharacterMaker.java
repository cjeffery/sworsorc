/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package Character;

import Units.Race;

/**
 *
 * @author Cam
 */
public class CharacterMaker{
     public static Character createCharacter(String name){
        Character newChar = new Character();
        switch (name){
            case "Emperor Coron III the Unconquerable" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "0552";
                newChar.magicProfile = 6;
                newChar.magicResistance = 4;
                newChar.leadership = 5;
                newChar.diplomacy = 3;
                newChar.setRace(Race.Human);
                break;
            case "The Paladin Glade" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "0452";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 3;
                newChar.diplomacy = 4;
                newChar.setRace(Race.Human);
                break;  
            case "Stephen the Paladin" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "0950";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 2;
                newChar.diplomacy = 2;
                newChar.setRace(Race.Human);
                break;   
            case "Lord Dil" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "0451";
                newChar.magicProfile = 4;
                newChar.magicResistance = 4;
                newChar.leadership = 2;
                newChar.diplomacy = 3;
                newChar.setRace(Race.Human);
                break;
            case "Tim the Enchanter" : 
                newChar.Name = name;
                newChar.MagicPL = 4;
                newChar.MagicPotential = 16;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "0245";
                newChar.magicProfile = 6;
                newChar.magicResistance = 4;
                newChar.leadership = 0;
                newChar.diplomacy = 1;
                newChar.setRace(Race.Human);
                break; 
            case "Eodred the Sorceress" : 
                newChar.Name = name;
                newChar.MagicPL = 4;
                newChar.MagicPotential = 12;
                newChar.magicColor = MagicColor.blue;
                newChar.homeHex = "0540";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 0;
                newChar.diplomacy = 3;
                newChar.setRace(Race.Human);
                break;
            case "Larraka" : 
                newChar.Name = name;
                newChar.MagicPL = 5;
                newChar.MagicPotential = 17;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "3713";
                newChar.magicProfile = 5;
                newChar.magicResistance = 4;
                newChar.leadership = 1;
                newChar.diplomacy = 0;
                newChar.setRace(Race.Human);
                break;
            case "Talerren the Not-So-Brave" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "0803";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 4;
                newChar.diplomacy = 3;
                newChar.setRace(Race.Human);
                break;  
            case "Weldron" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "2433";
                newChar.magicProfile = 1;
                newChar.magicResistance = 3;
                newChar.leadership = 3;
                newChar.diplomacy = 2;
                newChar.setRace(Race.Human);
                break;  
            case "Almuric" : 
                newChar.Name = name;
                newChar.MagicPL = 1;
                newChar.MagicPotential = 6;
                newChar.magicColor = MagicColor.blue;
                newChar.homeHex = "2341";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 3;
                newChar.diplomacy = 1;
                newChar.setRace(Race.Human);
                break; 
            case "Dierdra" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "3638";
                newChar.magicProfile = 3;
                newChar.magicResistance = 2;
                newChar.leadership = 3;
                newChar.diplomacy = 2;
                newChar.setRace(Race.Human);
                break; 
            case "Wendolyn the Wary" : 
                newChar.Name = name;
                newChar.MagicPL = 3;
                newChar.MagicPotential = 15;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "1528";
                newChar.magicProfile = 2;
                newChar.magicResistance = 3;
                newChar.leadership = 0;
                newChar.diplomacy = -1;
                newChar.setRace(Race.Human);
                break;
            case "Curvenol" : 
                newChar.Name = name;
                newChar.MagicPL = 5;
                newChar.MagicPotential = 16;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "0352";
                newChar.magicProfile = 5;
                newChar.magicResistance = 2;
                newChar.leadership = 1;
                newChar.diplomacy = 0;
                newChar.setRace(Race.Human);
                break; 
            case "Zareth" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "1246";
                newChar.magicProfile = 5;
                newChar.magicResistance = 5;
                newChar.leadership = 1;
                newChar.diplomacy = 2;
                newChar.setRace(Race.Human);
                break; 
            case "Loki Hellsson" : 
                newChar.Name = name;
                newChar.MagicPL = 6;
                newChar.MagicPotential = 20;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "0606";
                newChar.magicProfile = 6;
                newChar.magicResistance = 5;
                newChar.leadership = 5;
                newChar.diplomacy = 4;
                newChar.setRace(Race.Human);
                break; 
            case "Alric" : 
                newChar.Name = name;
                newChar.MagicPL = 3;
                newChar.MagicPotential = 11;
                newChar.magicColor = MagicColor.blue;
                newChar.homeHex = "0509";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 0;
                newChar.diplomacy = 1;
                newChar.setRace(Race.Human);
                break; 
            case "X the Unknown (Roc Deathsinger)" : 
                newChar.Name = name;
                newChar.MagicPL = 6;
                newChar.MagicPotential = 20;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "0904";
                newChar.magicProfile = 4;
                newChar.magicResistance = 5;
                newChar.leadership = 1;
                newChar.diplomacy = 2;
                newChar.setRace(Race.Human);
                break;
            case "Gygax Dragonlord" : 
                newChar.Name = name;
                newChar.MagicPL = 2;
                newChar.MagicPotential = 6;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "0302";
                newChar.magicProfile = 6;
                newChar.magicResistance = 5;
                newChar.leadership = 3;
                newChar.diplomacy = 3;
                newChar.setRace(Race.Dragon);
                break;
            case "Gislan the Rock" : 
                newChar.Name = name;
                newChar.MagicPL = 4;
                newChar.MagicPotential = 8;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "0818";
                newChar.magicProfile = 3;
                newChar.magicResistance = 4;
                newChar.leadership = 2;
                newChar.diplomacy = 4;
                newChar.setRace(Race.Dwarrows);
                break;  
            case "Gerudirr Dragonslayer" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "2808";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 3;
                newChar.diplomacy = 3;
                newChar.setRace(Race.Dwarrows);
                break; 
            case "Zurik Bladebreaker" : 
                newChar.Name = name;
                newChar.MagicPL = 4;
                newChar.MagicPotential = 12;
                newChar.magicColor = MagicColor.blue;
                newChar.homeHex = "2517";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 2;
                newChar.diplomacy = 2;
                newChar.setRace(Race.Dwarrows);
                break;  
            case "Narklath" : 
                newChar.Name = name;
                newChar.MagicPL = 2;
                newChar.MagicPotential = 13;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "3645";
                newChar.magicProfile = 1;
                newChar.magicResistance = 4;
                newChar.leadership = 2;
                newChar.diplomacy = 1;
                newChar.setRace(Race.Human); //Double check
                break;    
            case "Gwaigilion Elengal" : 
                newChar.Name = name;
                newChar.MagicPL = 3;
                newChar.MagicPotential = 10;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "0630";
                newChar.magicProfile = 4;
                newChar.magicResistance = 4;
                newChar.leadership = 3;
                newChar.diplomacy = 6;
                newChar.setRace(Race.Elves);
                break; 
            case "Maytwist" : 
                newChar.Name = name;
                newChar.MagicPL = 3;
                newChar.MagicPotential = 10;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "3333";
                newChar.magicProfile = 2;
                newChar.magicResistance = 3;
                newChar.leadership = 0;
                newChar.diplomacy = 3;
                newChar.setRace(Race.Elves);
                break;   
            case "Linfalas" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "3426";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 4;
                newChar.diplomacy = 4;
                newChar.setRace(Race.Elves);
                break; 
            case "Dalmilandril" : 
                newChar.Name = name;
                newChar.MagicPL = 4;
                newChar.MagicPotential = 12;
                newChar.magicColor = MagicColor.blue;
                newChar.homeHex = "0319";
                newChar.magicProfile = 4;
                newChar.magicResistance = 4;
                newChar.leadership = 1;
                newChar.diplomacy = 2;
                newChar.setRace(Race.Elves);
                break; 
            case "Sliggoth" : 
                newChar.Name = name;
                newChar.MagicPL = 2;
                newChar.MagicPotential = 8;
                newChar.magicColor = MagicColor.blue;
                newChar.homeHex = "1341";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 2;
                newChar.diplomacy = -1;
                newChar.setRace(Race.SwampCreature);
                break; 
            case "Gilith" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "0302";
                newChar.magicProfile = 1;
                newChar.magicResistance = 4;
                newChar.leadership = 2;
                newChar.diplomacy = 3;
                newChar.setRace(Race.Elves);
                break;  
            case "Peg-Leg Gonzo" : 
                newChar.Name = name;
                newChar.MagicPL = 2;
                newChar.MagicPotential = 7;
                newChar.magicColor = MagicColor.blue;
                newChar.homeHex = "3150";
                newChar.magicProfile = 2;
                newChar.magicResistance = 3;
                newChar.leadership = 0;
                newChar.diplomacy = -1;
                newChar.setRace(Race.Human);
                break;  
            case "Unamit Ahazredit" : 
                newChar.Name = name;
                newChar.MagicPL = 4;
                newChar.MagicPotential = 13;
                newChar.magicColor = MagicColor.blue;
                newChar.homeHex = "3150";
                newChar.magicProfile = 3;
                newChar.magicResistance = 4;
                newChar.leadership = 3;
                newChar.diplomacy = 3;
                newChar.setRace(Race.Human);
                break; 
            case "Raman Cronkevitch" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "2151";
                newChar.magicProfile = 4;
                newChar.magicResistance = 4;
                newChar.leadership = 3;
                newChar.diplomacy = 1;
                newChar.setRace(Race.Cronk);
                break;
            case "Svartz Tarnkap" : 
                newChar.Name = name;
                newChar.MagicPL = 2;
                newChar.MagicPotential = 8;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "2151";
                newChar.magicProfile = 4;
                newChar.magicResistance = 3;
                newChar.leadership = 2;
                newChar.diplomacy = 3;
                newChar.setRace(Race.Human);
                break;
            case "Mellanthia" : 
                newChar.Name = name;
                newChar.MagicPL = 3;
                newChar.MagicPotential = 8;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "3808";
                newChar.magicProfile = 6;
                newChar.magicResistance = 4;
                newChar.leadership = 2;
                newChar.diplomacy = 3;
                newChar.setRace(Race.Spiders);
                break;
            case "Jeremiah Ben Ruben" : 
                newChar.Name = name;
                newChar.MagicPL = 2;
                newChar.MagicPotential = 8;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "2133";
                newChar.magicProfile = 2;
                newChar.magicResistance = 4;
                newChar.leadership = 4;
                newChar.diplomacy = 6;
                newChar.setRace(Race.Human);
                break; 
            case "Theregond the Mage" : 
                newChar.Name = name;
                newChar.MagicPL = 3;
                newChar.MagicPotential = 14;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "2133";
                newChar.magicProfile = 3;
                newChar.magicResistance = 3;
                newChar.leadership = 1;
                newChar.diplomacy = 2;
                newChar.setRace(Race.Human);
                break; 
            case "Snorri Gundarchuksson" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "3531";
                newChar.magicProfile = 4;
                newChar.magicResistance = 4;
                newChar.leadership = 3;
                newChar.diplomacy = 4;
                newChar.setRace(Race.Human);
                break; 
            case "Ganab the Nasty" : 
                newChar.Name = name;
                newChar.MagicPL = 3;
                newChar.MagicPotential = 12;
                newChar.magicColor = MagicColor.red;
                newChar.homeHex = "1909";
                newChar.magicProfile = 4;
                newChar.magicResistance = 2;
                newChar.leadership = 2;
                newChar.diplomacy = 2;
                newChar.setRace(Race.Goblins);
                break;
            case "Chairman Naskhund" : 
                newChar.Name = name;
                newChar.MagicPL = 3;
                newChar.MagicPotential = 8;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "1611";
                newChar.magicProfile = 5;
                newChar.magicResistance = 4;
                newChar.leadership = 4;
                newChar.diplomacy = 4;
                newChar.setRace(Race.Orc);
                break; 
            case "Krawn the Crazy" : 
                newChar.Name = name;
                newChar.MagicPL = 1;
                newChar.MagicPotential = 6;
                newChar.magicColor = MagicColor.yellow;
                newChar.homeHex = "1614";
                newChar.magicProfile = 4;
                newChar.magicResistance = 3;
                newChar.leadership = 3;
                newChar.diplomacy = 5;
                newChar.setRace(Race.Orc);
                break; 
            case "Zarko" : 
                newChar.Name = name;
                newChar.MagicPL = 0;
                newChar.MagicPotential = 0;
                newChar.magicColor = MagicColor.none;
                newChar.homeHex = "1605";
                newChar.magicProfile = 2;
                newChar.magicResistance = 2;
                newChar.leadership = 2;
                newChar.diplomacy = -1;
                newChar.setRace(Race.Orc);
                break;    
            default: name = "Invalid Character Name";
                     break;
        }
       
        return newChar;
    }
}
