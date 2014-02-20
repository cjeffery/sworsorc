package magicsummarytable;

import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Gabe Pearhill
 */
public class MagicSummaryTable {

    private Map<String, SpellSummary> table;
    
    public SpellSummary lookup(String name){
        return table.get(name);
    }
    
    public MagicSummaryTable(){
        /*
            For now I have chosen to ignore spells of the "Quest" type
            since our focus is on the army game.
        */
        
        table = new HashMap<String, SpellSummary>();
        /*
            Initialize Map
        */
        // Level 1
        SpellSummary conjureZombieInfantry = new SpellSummary("Conjure Zombie Infantry", false, 'A' ,"1 per 2 GT", 15);
        table.put("Conjure Zombie Infantry", conjureZombieInfantry);
        SpellSummary forceWall= new SpellSummary("Force Wall", false, 'A' , "2 per hexside" , 7);
        table.put("Force Wall", forceWall);
        SpellSummary magicBlow= new SpellSummary("Magic Blow", true , 'P' , "1" , 4);
        table.put("Magic Blow", magicBlow);
        SpellSummary strength = new SpellSummary("Strength", false, 'P' , "3 minimum" , 2);
        table.put("Strength", strength);
        SpellSummary weakness = new SpellSummary("Weakness", true, 'P' , "3 minimum" , 6);
        table.put("Weakness", weakness);
        
        // Level 2
        SpellSummary conjureCentauroidCavalry = new SpellSummary("Conjure Centauroid Cavalry", false, 'A' , "1 per GT" , 0);
        table.put("Conjure Centauroid Cavalry", conjureCentauroidCavalry);
        SpellSummary fear = new SpellSummary("Fear", false, 'A' , "3 per unit" , 7);
        table.put("Fear", fear);
        SpellSummary magicLance = new SpellSummary("Magic Lance", true, 'P' , "2" , 5);
        table.put("Magic Lance", magicLance);
        SpellSummary mannaTransfer = new SpellSummary("Manna Transfer", false, 'A' , "2 for 1" , 0);
        table.put( "Manna Transfer" , mannaTransfer );
        SpellSummary morale = new SpellSummary("Moral", false, 'C' , "3 per unit" , 7);
        table.put( "Morale" , morale );
        SpellSummary riverCrossing = new SpellSummary("River Crossing", false , 'A' , "2 per hex" , 10);
        table.put( "River Crossing", riverCrossing );
        
        // Level 3
        SpellSummary bladeEnchantment = new SpellSummary("Blade Enchantment", true, 'P' , "2 per GT" , 1);
        table.put("Blade Enhancement",  bladeEnchantment);
        SpellSummary boltOfFire = new SpellSummary("Bolt of Fire", true, 'P' , "3" , 5);
        table.put("Bolt of Fire" , boltOfFire);
        SpellSummary conjureWyvernAirtroops = new SpellSummary("Conjure Wyvern Airtroops", false, 'A' , "1.5 per GT" , 5);
        table.put("Conjure Wyvern Airtroops" , conjureWyvernAirtroops);
        SpellSummary dispellMagicks = new SpellSummary("Dispell Magics", false, 'C' , "3" , -1);
        table.put("Dispel Magics" , dispellMagicks);
        SpellSummary enhanceStature = new SpellSummary("Enhace Stature", false, 'A' , "1 per GT/Dip Pt 2\n2 per Dip Pt Emissary" , 0);
        table.put( "Enhance Stature", enhanceStature);
        SpellSummary forest = new SpellSummary("Forest", false, 'A' , "2 per hex" , 5);
        table.put( "Forest" , forest);
        SpellSummary immobilization = new SpellSummary("Immobilization", false, 'A' , "1 per unit" , 7);
        table.put( "Immobilization", immobilization);
        // Unlimited Range
        SpellSummary monsoon = new SpellSummary("Monsoon", false, 'A' , "5" , 1000000);
        table.put( "Monsoon", monsoon );
        SpellSummary vortexSuppression = new SpellSummary("Vortex Suppression", false, 'A' , "1 per unit" , 10);
        table.put( "Vortex Suppression" , vortexSuppression );
        SpellSummary dissipateSpellScreen = new SpellSummary("Dissipate Spell Screen", true, 'P' , "1 minimum" , 0);
        table.put( "Dissipate Spell Screen", dissipateSpellScreen );
        
        // Level 4
        SpellSummary blastOfFire = new SpellSummary("Blast of Fire", true, 'P' , "4" , 5);
        table.put("Blast of Fire" , blastOfFire);
        SpellSummary building = new SpellSummary("Building", false, 'A' , "1 per GT" , 5);
        table.put( "Building", building );
        SpellSummary conjureKoboldicInfantry = new SpellSummary("Conjure Koboldic Infantry", false, 'A' , "1 per GT" , 0);
        table.put( "Conjure Koboldic Infantry", conjureKoboldicInfantry );
        SpellSummary disintegration = new SpellSummary("Disintegration", false, 'A' , "6" , 5);
        table.put( "Disintegration", disintegration );
        // Unlimited Range
        SpellSummary ersatzWinter = new SpellSummary("Ersatz Winter", false , 'A' , "8" , 1000000);
        table.put( "Ersatz Winter", ersatzWinter);
        SpellSummary teleportationControl = new SpellSummary("Teleportation Control", false, 'A' , "4" , 0);
        table.put("Teleportation Control" , teleportationControl);
        SpellSummary vortexCreation = new SpellSummary("Vortex Creation", false, 'A' , "2 per Vortex" , 5);
        table.put( "Vortex Creation", vortexCreation);
        
        // Level 5
        SpellSummary banishConjuredTroops = new SpellSummary("Banish Conjured Troops", false, 'A' , "3" , 7);
        table.put("Banish Conjured Troops" , banishConjuredTroops);
        SpellSummary boltOfLightning = new SpellSummary("Bolt of Lightning", true, 'P' , "5" , 6);
        table.put("Bolt of Lightning" , boltOfLightning);
        SpellSummary conjureWraithTroops = new SpellSummary("Conjure Wraith Troops", false, 'A' , "1.5 per GT" , 0);
        table.put( "Conjure Wraith Troops", conjureWraithTroops);
        SpellSummary convulsion = new SpellSummary("Convulsion", true, 'P' , "6" , 4);
        table.put( "Convulsion", convulsion );
        SpellSummary  planarReturn= new SpellSummary("Planar Return", false, 'A' , "6" , 2);
        table.put( "Planar Return", planarReturn);
        SpellSummary spellScreen = new SpellSummary("Spell Screen", false, 'P' , "6" , 2);
        table.put( "Spell Screen", spellScreen);
        SpellSummary summonDemon = new SpellSummary("Summon Demon", false, 'A' , "4" , 0);
        table.put( "Summon Demon", summonDemon);
        
        // Level 6
        SpellSummary banishDemon = new SpellSummary("Banish Demon", false, 'C' , "6" , 4);
        table.put( "Banish Demon", banishDemon);
        SpellSummary beserkergang = new SpellSummary("Berserkergang", false, 'A' , "2 per unit" , 7);
        table.put( "Berserkergang", beserkergang);
        SpellSummary bindDemon = new SpellSummary("Bind Demon", false, 'A' , "4 per GT" , 15);
        table.put( "Bind Demon", bindDemon);
        SpellSummary conjureDemonicInfantry = new SpellSummary("Conjure Demonic Infantry", false, 'A' , "2 per GT" , 0);
        table.put( "Conjure Demonic Infantry", conjureDemonicInfantry );
        SpellSummary fireball = new SpellSummary("Fireball", true, 'P' , "6" , 5);
        table.put( "Fireball", fireball );
        SpellSummary firestorm = new SpellSummary("Firestorm", false, 'A' , "10" , 5);
        table.put( "Firestorm", firestorm );
        // -2 range - only hex 0606
        SpellSummary summonForce = new SpellSummary("Summon Force", false, 'A' , "10" , -2);
        table.put( "Summon Force", summonForce);
        
        // Level 7
        // -3 range - half mana pts
        SpellSummary wizardWheel = new SpellSummary("Wizard Wheel", false, 'A' , "All (6 minimum)" , -3);
        table.put( "Wizard Wheel", wizardWheel);
    }
    
    public static void main(String[] args) {
        
    }
    
}
