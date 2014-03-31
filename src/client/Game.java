/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jay Drage
 */

import mainswordsorcery.HUD;
import Units.UnitPool;

public class Game {
        int state = 2; //used for state switch in main()
                           // it will be changed with user actions in
                           // main menu
                           // set to 2 temporarily, will be 1 to start
                           // main menu
        HUD mainMap;       // main map GUI
        UnitPool unitPool; // hash tree for units 
        
        public static void main (String args[]){
            Game g = new Game();
            while (true){
                switch (g.state){
                    case 0:  // exit program
                           System.exit(0);
                    case 1: // display main menu
                        break;
                    case 2: // load scenario
                        g.loadScenario();
                            // this will load UnitPool
                        g.state = 3; // this will happen in main menu
                                       // just a placeholder for now
                        break;
                    case 3: // display map HUD
                        if ( g.mainMap == null )
                            g.displayMainMap();
                        break;
                }
            }
        }
        void loadScenario(){
            unitPool = UnitPool.getInstance();
            unitPool.clearPool();
        }
        void displayMainMap (){
                    /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        mainMap = new HUD();
        mainMap.setVisible(true);
        }
    
}
