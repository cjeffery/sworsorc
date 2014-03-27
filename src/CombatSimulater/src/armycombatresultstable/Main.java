/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armycombatresultstable;


import Units.Race;
import static armycombatresultstable.ArmyCombatResultsTable.*;
import Units.*;
import ssterrain.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author matt
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Input: What I need from Hex information:

        ////////////////////////////////////

        ArrayList<ArmyUnit> attackers = new ArrayList<>();
        ArrayList<ArmyUnit> defencers = new ArrayList<>();

        HashMap units = new HashMap();
        Hex hex1 = new Hex();
        TerrainType tt1 = new TTWoods();
        hex1.setTerrainType(tt1);

        Create_unit1(Units1_name, Units1_racial, units, attackers);
        Create_unit2(Units2_name, Units2_racial, units, attackers);
        Create_unit3(Units3_name, Units3_racial, units, defencers);
        Create_unit4(Units4_name, Units4_racial, units, defencers);


        
        int[] results;

        //results = PrepareAttackResults(attackers, defencers, hex1, jTextField6);

        //System.out.println("Attackers: " + results[0]);
        //System.out.println("Defenders: " + results[1]);

    }

    public static void Create_unit1(String Units1_name, String Units1_racial, HashMap units, ArrayList<ArmyUnit> attackers) {

        /* For unit 1 */
        switch (Units1_name) {

            case "Bow": {
                Bow unit1 = new Bow();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HB01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DB01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWB01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EB01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPH01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OH01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SB01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCB01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
            } // case Bow

            case "CentauroidCavalry": {
                CentauroidCavalry unit1 = new CentauroidCavalry();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HCC01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DCC01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWCC01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("ECC01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPCC01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OCC1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SCC01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCCC01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
            } // case CentauroidCavalry

            case "DemonicInfantry": {
                DemonicInfantry unit1 = new DemonicInfantry();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HDI01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DDI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWDI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EDI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPDI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("ODI1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SDI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCDI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "DinosaurLegion": {
                DinosaurLegion unit1 = new DinosaurLegion();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HDL01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DDL01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWDL01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EDL01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPDL01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("ODL1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SDL01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCDL01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HeavyAxe": {
                HeavyAxe unit1 = new HeavyAxe();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HHA01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DHA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWHA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EHA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPHA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OHA1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SHA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCHA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HeavyHorse": {
                HeavyHorse unit1 = new HeavyHorse();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HHH01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DHH01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWHH01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EHH01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPHH01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OHH1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SHH01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCHH01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HeavyPluglunk": {
                HeavyPluglunk unit1 = new HeavyPluglunk();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HHP01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DHP01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWHP01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EHP01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPHP01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OHP1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SHP01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCHP01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HeavySword": {
                HeavySword unit1 = new HeavySword();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HHS01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DHS01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWHS01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EHS01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPHS01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OHS1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SHS01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCHS01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HorseArcher": {
                HorseArcher unit1 = new HorseArcher();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HHA01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DHA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWHA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EHA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPHA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OHA1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SHA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCHA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "IntelligentMold": {
                IntelligentMold unit1 = new IntelligentMold();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HIM01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DIM01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWIM01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EIM01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPCC01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OIM1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SIM01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCIM01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "KoboldicInfantry": {
                KoboldicInfantry unit1 = new KoboldicInfantry();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HKI01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DKI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWKI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EKI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPKI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OKI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SKI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCKI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "LightBow": {
                LightBow unit1 = new LightBow();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HLB01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DLB01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWLB01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("ELB01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPLB01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OLB1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SLB01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCLB01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End 

            case "LightHorse": {
                LightHorse unit1 = new LightHorse();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HLH01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DLH01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWLH01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("ELH01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPLH01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OLH1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SLH01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCLH01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "LightSpear": {
                LightSpear unit1 = new LightSpear();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HLSR01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DLSR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWLSR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("ELSR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPLSR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OLSR1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SLSR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCLSR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "LightSword": {
                LightSword unit1 = new LightSword();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HLSD01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DLSD01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWLSD01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("ELSD01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPLSD01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OLSD1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SLSD01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCLSD01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "MediumSpear": {
                MediumSpear unit1 = new MediumSpear();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HMS01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DMS01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWMS01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EMS01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPMS01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OMS1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SMS01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCMS01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "PikeMan": {
                PikeMan unit1 = new PikeMan();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HPM01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DPM01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWPM01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EPM01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPPM01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OPM1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SPM01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCPM01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "RocRider": {
                RocRider unit1 = new RocRider();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HRR01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DRR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWRR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("ERR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPRR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("ORR1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SRR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCRR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "SpiderLegion": {
                SpiderLegion unit1 = new SpiderLegion();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HSL01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DSL01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWSL01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("ESL01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPSL01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OSL1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SSL01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCSL01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End 

            case "WargRider": {
                WargRider unit1 = new WargRider();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HWR01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DWR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWWR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EWR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPWR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OWR1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SWR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCWR01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "WebWarriors": {
                WebWarriors unit1 = new WebWarriors();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HWW01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DWW01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWWW01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EWW01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPWW01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OWW1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SWW01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCWW01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "WraithTroops": {
                WraithTroops unit1 = new WraithTroops();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HWT01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DWT01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWWT01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EWT01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPWT01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OWT1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SWT01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCWT01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "WyvernAirtroops": {
                WyvernAirtroops unit1 = new WyvernAirtroops();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HWA01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DWA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWWA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EWA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPWA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OWA1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SWA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCWA01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "Zeppelin": {
                Zeppelin unit1 = new Zeppelin();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HZ01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DZ01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWZ01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EZ01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPZ01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OZ1", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SZ01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCZ01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End 

            case "ZombieInfantry": {
                ZombieInfantry unit1 = new ZombieInfantry();
                switch (Units1_racial) {
                    case "Human": {
                        unit1.setRace(Race.Human);
                        units.put("HZI01", unit1);
                        attackers.add(unit1);
                    }

                    case "Dragon": {
                        unit1.setRace(Race.Dragon);
                        units.put("DZI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Dwarrows": {
                        unit1.setRace(Race.Dwarrows);
                        units.put("DWZI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Elves": {
                        unit1.setRace(Race.Elves);
                        units.put("EZI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "KillerPenguin": {
                        unit1.setRace(Race.KillerPenguin);
                        units.put("KPZI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Orc": {
                        unit1.setRace(Race.Orc);
                        units.put("OZI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "Spiders": {
                        unit1.setRace(Race.Spiders);
                        units.put("SZI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    case "SwampCreature": {
                        unit1.setRace(Race.SwampCreature);
                        units.put("SCZI01", unit1);
                        attackers.add(unit1);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            default: {
                break;
            } // End

        }

    }
    public static void Create_unit2(String units2_name, String units2_racial, HashMap units, ArrayList<ArmyUnit> attackers) {

        /* For unit 1 */
        switch (units2_name) {

            case "Bow": {
                Bow unit2 = new Bow();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HB01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DB01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWB01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EB01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPH01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OH01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SB01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCB01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
            } // case Bow

            case "CentauroidCavalry": {
                CentauroidCavalry unit2 = new CentauroidCavalry();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HCC01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DCC01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWCC01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("ECC01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPCC01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OCC1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SCC01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCCC01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
            } // case CentauroidCavalry

            case "DemonicInfantry": {
                DemonicInfantry unit2 = new DemonicInfantry();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HDI01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DDI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWDI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EDI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPDI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("ODI1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SDI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCDI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "DinosaurLegion": {
                DinosaurLegion unit2 = new DinosaurLegion();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HDL01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DDL01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWDL01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EDL01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPDL01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("ODL1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SDL01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCDL01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HeavyAxe": {
                HeavyAxe unit2 = new HeavyAxe();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HHA01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DHA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWHA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EHA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPHA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OHA1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SHA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCHA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HeavyHorse": {
                HeavyHorse unit2 = new HeavyHorse();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HHH01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DHH01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWHH01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EHH01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPHH01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OHH1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SHH01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCHH01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HeavyPluglunk": {
                HeavyPluglunk unit2 = new HeavyPluglunk();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HHP01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DHP01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWHP01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EHP01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPHP01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OHP1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SHP01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCHP01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HeavySword": {
                HeavySword unit2 = new HeavySword();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HHS01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DHS01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWHS01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EHS01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPHS01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OHS1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SHS01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCHS01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HorseArcher": {
                HorseArcher unit2 = new HorseArcher();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HHA01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DHA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWHA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EHA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPHA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OHA1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SHA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCHA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "IntelligentMold": {
                IntelligentMold unit2 = new IntelligentMold();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HIM01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DIM01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWIM01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EIM01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPCC01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OIM1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SIM01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCIM01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "KoboldicInfantry": {
                KoboldicInfantry unit2 = new KoboldicInfantry();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HKI01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DKI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWKI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EKI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPKI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OKI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SKI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCKI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "LightBow": {
                LightBow unit2 = new LightBow();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HLB01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DLB01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWLB01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("ELB01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPLB01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OLB1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SLB01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCLB01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End 

            case "LightHorse": {
                LightHorse unit2 = new LightHorse();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HLH01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DLH01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWLH01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("ELH01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPLH01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OLH1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SLH01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCLH01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "LightSpear": {
                LightSpear unit2 = new LightSpear();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HLSR01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DLSR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWLSR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("ELSR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPLSR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OLSR1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SLSR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCLSR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "LightSword": {
                LightSword unit2 = new LightSword();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HLSD01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DLSD01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWLSD01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("ELSD01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPLSD01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OLSD1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SLSD01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCLSD01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "MediumSpear": {
                MediumSpear unit2 = new MediumSpear();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HMS01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DMS01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWMS01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EMS01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPMS01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OMS1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SMS01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCMS01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "PikeMan": {
                PikeMan unit2 = new PikeMan();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HPM01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DPM01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWPM01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EPM01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPPM01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OPM1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SPM01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCPM01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "RocRider": {
                RocRider unit2 = new RocRider();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HRR01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DRR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWRR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("ERR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPRR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("ORR1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SRR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCRR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "SpiderLegion": {
                SpiderLegion unit2 = new SpiderLegion();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HSL01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DSL01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWSL01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("ESL01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPSL01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OSL1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SSL01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCSL01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End 

            case "WargRider": {
                WargRider unit2 = new WargRider();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HWR01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DWR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWWR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EWR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPWR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OWR1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SWR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCWR01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "WebWarriors": {
                WebWarriors unit2 = new WebWarriors();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HWW01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DWW01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWWW01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EWW01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPWW01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OWW1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SWW01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCWW01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "WraithTroops": {
                WraithTroops unit2 = new WraithTroops();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HWT01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DWT01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWWT01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EWT01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPWT01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OWT1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SWT01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCWT01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "WyvernAirtroops": {
                WyvernAirtroops unit2 = new WyvernAirtroops();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HWA01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DWA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWWA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EWA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPWA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OWA1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SWA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCWA01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "Zeppelin": {
                Zeppelin unit2 = new Zeppelin();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HZ01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DZ01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWZ01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EZ01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPZ01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OZ1", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SZ01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCZ01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End 

            case "ZombieInfantry": {
                ZombieInfantry unit2 = new ZombieInfantry();
                switch (units2_racial) {
                    case "Human": {
                        unit2.setRace(Race.Human);
                        units.put("HZI01", unit2);
                        attackers.add(unit2);
                    }

                    case "Dragon": {
                        unit2.setRace(Race.Dragon);
                        units.put("DZI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Dwarrows": {
                        unit2.setRace(Race.Dwarrows);
                        units.put("DWZI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Elves": {
                        unit2.setRace(Race.Elves);
                        units.put("EZI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "KillerPenguin": {
                        unit2.setRace(Race.KillerPenguin);
                        units.put("KPZI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Orc": {
                        unit2.setRace(Race.Orc);
                        units.put("OZI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "Spiders": {
                        unit2.setRace(Race.Spiders);
                        units.put("SZI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    case "SwampCreature": {
                        unit2.setRace(Race.SwampCreature);
                        units.put("SCZI01", unit2);
                        attackers.add(unit2);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            default: {
                break;
            } // End

        }

    }  
    public static void Create_unit3(String units3_name, String units3_racial, HashMap units, ArrayList<ArmyUnit> defencers) {

        /* For unit 1 */
        switch (units3_name) {

            case "Bow": {
                Bow units3 = new Bow();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HB01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DB01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWB01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EB01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPH01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OH01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SB01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCB01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
            } // case Bow

            case "CentauroidCavalry": {
                CentauroidCavalry units3 = new CentauroidCavalry();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HCC01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DCC01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWCC01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("ECC01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPCC01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OCC1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SCC01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCCC01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
            } // case CentauroidCavalry

            case "DemonicInfantry": {
                DemonicInfantry units3 = new DemonicInfantry();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HDI01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DDI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWDI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EDI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPDI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("ODI1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SDI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCDI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "DinosaurLegion": {
                DinosaurLegion units3 = new DinosaurLegion();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HDL01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DDL01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWDL01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EDL01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPDL01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("ODL1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SDL01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCDL01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HeavyAxe": {
                HeavyAxe units3 = new HeavyAxe();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HHA01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DHA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWHA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EHA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPHA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OHA1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SHA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCHA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HeavyHorse": {
                HeavyHorse units3 = new HeavyHorse();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HHH01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DHH01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWHH01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EHH01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPHH01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OHH1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SHH01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCHH01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HeavyPluglunk": {
                HeavyPluglunk units3 = new HeavyPluglunk();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HHP01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DHP01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWHP01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EHP01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPHP01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OHP1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SHP01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCHP01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HeavySword": {
                HeavySword units3 = new HeavySword();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HHS01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DHS01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWHS01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EHS01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPHS01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OHS1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SHS01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCHS01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HorseArcher": {
                HorseArcher units3 = new HorseArcher();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HHA01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DHA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWHA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EHA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPHA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OHA1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SHA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCHA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "IntelligentMold": {
                IntelligentMold units3 = new IntelligentMold();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HIM01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DIM01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWIM01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EIM01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPCC01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OIM1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SIM01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCIM01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "KoboldicInfantry": {
                KoboldicInfantry units3 = new KoboldicInfantry();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HKI01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DKI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWKI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EKI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPKI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OKI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SKI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCKI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "LightBow": {
                LightBow units3 = new LightBow();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HLB01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DLB01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWLB01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("ELB01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPLB01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OLB1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SLB01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCLB01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End 

            case "LightHorse": {
                LightHorse units3 = new LightHorse();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HLH01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DLH01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWLH01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("ELH01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPLH01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OLH1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SLH01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCLH01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "LightSpear": {
                LightSpear units3 = new LightSpear();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HLSR01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DLSR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWLSR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("ELSR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPLSR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OLSR1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SLSR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCLSR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "LightSword": {
                LightSword units3 = new LightSword();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HLSD01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DLSD01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWLSD01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("ELSD01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPLSD01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OLSD1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SLSD01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCLSD01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "MediumSpear": {
                MediumSpear units3 = new MediumSpear();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HMS01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DMS01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWMS01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EMS01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPMS01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OMS1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SMS01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCMS01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "PikeMan": {
                PikeMan units3 = new PikeMan();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HPM01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DPM01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWPM01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EPM01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPPM01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OPM1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SPM01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCPM01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "RocRider": {
                RocRider units3 = new RocRider();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HRR01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DRR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWRR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("ERR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPRR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("ORR1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SRR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCRR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "SpiderLegion": {
                SpiderLegion units3 = new SpiderLegion();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HSL01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DSL01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWSL01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("ESL01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPSL01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OSL1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SSL01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCSL01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End 

            case "WargRider": {
                WargRider units3 = new WargRider();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HWR01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DWR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWWR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EWR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPWR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OWR1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SWR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCWR01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "WebWarriors": {
                WebWarriors units3 = new WebWarriors();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HWW01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DWW01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWWW01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EWW01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPWW01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OWW1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SWW01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCWW01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "WraithTroops": {
                WraithTroops units3 = new WraithTroops();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HWT01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DWT01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWWT01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EWT01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPWT01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OWT1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SWT01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCWT01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "WyvernAirtroops": {
                WyvernAirtroops units3 = new WyvernAirtroops();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HWA01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DWA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWWA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EWA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPWA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OWA1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SWA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCWA01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "Zeppelin": {
                Zeppelin units3 = new Zeppelin();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HZ01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DZ01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWZ01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EZ01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPZ01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OZ1", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SZ01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCZ01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End 

            case "ZombieInfantry": {
                ZombieInfantry units3 = new ZombieInfantry();
                switch (units3_racial) {
                    case "Human": {
                        units3.setRace(Race.Human);
                        units.put("HZI01", units3);
                        defencers.add(units3);
                    }

                    case "Dragon": {
                        units3.setRace(Race.Dragon);
                        units.put("DZI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Dwarrows": {
                        units3.setRace(Race.Dwarrows);
                        units.put("DWZI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Elves": {
                        units3.setRace(Race.Elves);
                        units.put("EZI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "KillerPenguin": {
                        units3.setRace(Race.KillerPenguin);
                        units.put("KPZI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Orc": {
                        units3.setRace(Race.Orc);
                        units.put("OZI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "Spiders": {
                        units3.setRace(Race.Spiders);
                        units.put("SZI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    case "SwampCreature": {
                        units3.setRace(Race.SwampCreature);
                        units.put("SCZI01", units3);
                        defencers.add(units3);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            default: {
                break;
            } // End

        }

    }
    public static void Create_unit4(String units4_name, String units4_racial, HashMap units, ArrayList<ArmyUnit> defencers) {

        /* For unit 1 */
        switch (units4_name) {

            case "Bow": {
                Bow unit4 = new Bow();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HB01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DB01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWB01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EB01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPH01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OH01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SB01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCB01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
            } // case Bow

            case "CentauroidCavalry": {
                CentauroidCavalry unit4 = new CentauroidCavalry();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HCC01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DCC01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWCC01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("ECC01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPCC01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OCC1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SCC01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCCC01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
            } // case CentauroidCavalry

            case "DemonicInfantry": {
                DemonicInfantry unit4 = new DemonicInfantry();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HDI01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DDI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWDI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EDI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPDI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("ODI1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SDI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCDI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "DinosaurLegion": {
                DinosaurLegion unit4 = new DinosaurLegion();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HDL01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DDL01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWDL01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EDL01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPDL01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("ODL1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SDL01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCDL01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HeavyAxe": {
                HeavyAxe unit4 = new HeavyAxe();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HHA01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DHA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWHA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EHA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPHA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OHA1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SHA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCHA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HeavyHorse": {
                HeavyHorse unit4 = new HeavyHorse();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HHH01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DHH01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWHH01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EHH01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPHH01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OHH1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SHH01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCHH01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HeavyPluglunk": {
                HeavyPluglunk unit4 = new HeavyPluglunk();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HHP01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DHP01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWHP01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EHP01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPHP01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OHP1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SHP01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCHP01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HeavySword": {
                HeavySword unit4 = new HeavySword();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HHS01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DHS01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWHS01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EHS01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPHS01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OHS1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SHS01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCHS01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "HorseArcher": {
                HorseArcher unit4 = new HorseArcher();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HHA01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DHA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWHA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EHA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPHA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OHA1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SHA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCHA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "IntelligentMold": {
                IntelligentMold unit4 = new IntelligentMold();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HIM01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DIM01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWIM01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EIM01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPCC01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OIM1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SIM01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCIM01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "KoboldicInfantry": {
                KoboldicInfantry unit4 = new KoboldicInfantry();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HKI01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DKI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWKI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EKI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPKI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OKI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SKI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCKI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "LightBow": {
                LightBow unit4 = new LightBow();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HLB01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DLB01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWLB01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("ELB01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPLB01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OLB1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SLB01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCLB01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End 

            case "LightHorse": {
                LightHorse unit4 = new LightHorse();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HLH01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DLH01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWLH01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("ELH01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPLH01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OLH1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SLH01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCLH01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "LightSpear": {
                LightSpear unit4 = new LightSpear();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HLSR01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DLSR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWLSR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("ELSR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPLSR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OLSR1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SLSR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCLSR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "LightSword": {
                LightSword unit4 = new LightSword();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HLSD01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DLSD01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWLSD01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("ELSD01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPLSD01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OLSD1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SLSD01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCLSD01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "MediumSpear": {
                MediumSpear unit4 = new MediumSpear();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HMS01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DMS01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWMS01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EMS01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPMS01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OMS1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SMS01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCMS01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "PikeMan": {
                PikeMan unit4 = new PikeMan();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HPM01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DPM01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWPM01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EPM01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPPM01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OPM1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SPM01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCPM01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "RocRider": {
                RocRider unit4 = new RocRider();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HRR01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DRR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWRR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("ERR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPRR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("ORR1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SRR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCRR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "SpiderLegion": {
                SpiderLegion unit4 = new SpiderLegion();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HSL01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DSL01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWSL01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("ESL01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPSL01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OSL1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SSL01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCSL01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End 

            case "WargRider": {
                WargRider unit4 = new WargRider();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HWR01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DWR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWWR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EWR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPWR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OWR1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SWR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCWR01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "WebWarriors": {
                WebWarriors unit4 = new WebWarriors();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HWW01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DWW01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWWW01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EWW01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPWW01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OWW1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SWW01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCWW01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "WraithTroops": {
                WraithTroops unit4 = new WraithTroops();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HWT01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DWT01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWWT01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EWT01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPWT01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OWT1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SWT01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCWT01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "WyvernAirtroops": {
                WyvernAirtroops unit4 = new WyvernAirtroops();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HWA01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DWA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWWA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EWA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPWA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OWA1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SWA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCWA01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            case "Zeppelin": {
                Zeppelin unit4 = new Zeppelin();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HZ01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DZ01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWZ01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EZ01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPZ01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OZ1", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SZ01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCZ01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End 

            case "ZombieInfantry": {
                ZombieInfantry unit4 = new ZombieInfantry();
                switch (units4_racial) {
                    case "Human": {
                        unit4.setRace(Race.Human);
                        units.put("HZI01", unit4);
                        defencers.add(unit4);
                    }

                    case "Dragon": {
                        unit4.setRace(Race.Dragon);
                        units.put("DZI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Dwarrows": {
                        unit4.setRace(Race.Dwarrows);
                        units.put("DWZI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Elves": {
                        unit4.setRace(Race.Elves);
                        units.put("EZI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "KillerPenguin": {
                        unit4.setRace(Race.KillerPenguin);
                        units.put("KPZI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Orc": {
                        unit4.setRace(Race.Orc);
                        units.put("OZI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "Spiders": {
                        unit4.setRace(Race.Spiders);
                        units.put("SZI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    case "SwampCreature": {
                        unit4.setRace(Race.SwampCreature);
                        units.put("SCZI01", unit4);
                        defencers.add(unit4);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            } // End

            default: {
                break;
            } // End

        }

    }

 
}
