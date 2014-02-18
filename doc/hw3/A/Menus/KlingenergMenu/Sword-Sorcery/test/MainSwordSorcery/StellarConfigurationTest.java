/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainSwordSorcery;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David
 */
public class StellarConfigurationTest {
    StellarConfiguration sun;
 
    public StellarConfigurationTest() {
        sun = new StellarConfiguration('b');
    }

    /**
     * Test of AdvanceSuns method, of class StellarConfiguration.
     */
    @Test
    public void testAdvanceSuns() {
        do {
        System.out.println("\nAdvanceSuns");
        System.out.println("Red in ass:  " + sun.GetRedSunIsInAscension());
        System.out.println("Red in des:  " + sun.GetRedSunIsInDeclension());
        System.out.println("Blue in ass: " + sun.GetBlueSunIsInAscension());
        System.out.println("Blue in dss: " + sun.GetBlueSunIsInDeclension());
        System.out.println("Yellow Pos:  " + sun.GetYelloSunPosition());
        System.out.println("Red Pos:     " + sun.GetRedSunPosition());
        System.out.println("Blue Pos:    " + sun.GetBlueSunPosition());
        
        
        sun.AdvanceSuns();
        } while (sun.GetYelloSunPosition() != 1);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of GetRedSunIsInAscension method, of class StellarCongfiguration.
     */
    
}
