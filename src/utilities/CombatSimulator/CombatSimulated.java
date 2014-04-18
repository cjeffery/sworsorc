/*

1. Got two objects (attacking hex and defence hex) -> this may give me what units one that 
2. Read and Caculate all units by attacking and defencing side.
3. Need a Combat table, use attack and defence value and return me the outcome.


*/





package CombatSimulator;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import Units.Race;
import static sscharts.ArmyCombatResultsTable.*;
import Units.*;
//import static sscharts.Main.*;
//import static sscharts.Main.Create_unit2;

//import static armycombatresultstable.Main.Create_unit3;
//import static armycombatresultstable.Main.Create_unit4;
import ssterrain.*;
import java.util.ArrayList;
import java.util.HashMap;
import sshexmap.MapHex;
/**
 *
 * @author chishaung
 */
public class CombatSimulated extends javax.swing.JFrame {
    private String atk;
    String itemText = "Blasted";
    int[] results;
    String Units1_racial = "Human";
    String Units2_racial = "Human";
    String Units3_racial = "Human";
    String Units4_racial = "Human";
    String Units1_name = "Bow";
    String Units2_name = "Bow";
    String Units3_name = "Bow";
    String Units4_name = "Bow";    

    /**
     * Creates new form CharacterCombobox
     */
    public CombatSimulated() {
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        Result = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        comboOne = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        ComboTwo = new javax.swing.JComboBox();
        ComboThree = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        ComboFour = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        ComboFive = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        ComboEight = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        ComboSeven = new javax.swing.JComboBox();
        ComboSix = new javax.swing.JComboBox();
        ComboNine = new javax.swing.JComboBox();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Show Combat Details");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Exit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Clear");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText("Attacker Hex ID");

        jLabel2.setText("Defencer Hex ID");

        jLabel10.setText("Attacker's Unit1 Racial");

        jLabel13.setText("Combat Simulator");

        comboOne.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Blasted", "Broken", "Clear", "Cultivated", "Forest", "Glacier", "Karoo", "Mountains", "Rough", "Swamp", "Water", "Woods", " " }));
        comboOne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboOneActionPerformed(evt);
            }
        });

        jLabel5.setText("Defender's Terrain Type");

        jButton4.setText("Show Combat Result");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        ComboTwo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Human", "Dragon", "Dwarrows", "Elves", "KillerPenguin", "Orc", "Spiders", "SwampCreature" }));
        ComboTwo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboTwoActionPerformed(evt);
            }
        });

        ComboThree.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Human", "Dragon", "Dwarrows", "Elves", "KillerPenguin", "Orc", "Spiders", "SwampCreature" }));
        ComboThree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboThreeActionPerformed(evt);
            }
        });

        jLabel11.setText("Attacker's Unit2 Racial");

        ComboFour.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Human", "Dragon", "Dwarrows", "Elves", "KillerPenguin", "Orc", "Spiders", "SwampCreature" }));
        ComboFour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboFourActionPerformed(evt);
            }
        });

        jLabel12.setText("Defender's Unit1 Racial");

        ComboFive.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Human", "Dragon", "Dwarrows", "Elves", "KillerPenguin", "Orc", "Spiders", "SwampCreature" }));
        ComboFive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboFiveActionPerformed(evt);
            }
        });

        jLabel14.setText("Defender's Unit2 Racial");

        jLabel15.setText("Attacker's Unit1 Type");

        ComboEight.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bow", "Calvery", "CentauroidCavalry", "DemonicInfantry", "DinosaurLegion", "HeavyAxe", "HeavyHorse", "HeavyPluglunk", "HeavySword", "HorseArcher", "IntelligentMold", "KoboldicInfantry", "LightBow", "LightHorse", "LightSpear", "LightSword", "MediumSpear", "PikeMan", "RocRider", "SpiderLegion", "WargRider", "WebWarriors", "WraithTroops", "WyvernAirtroops", "Zeppelin", "ZombieInfantry", " ", " ", " " }));
        ComboEight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboEightActionPerformed(evt);
            }
        });

        jLabel16.setText("Attacker's Unit2 Type");

        jLabel17.setText("Defender's Unit1 Type");

        jLabel18.setText("Defender's Unit2 Type");

        ComboSeven.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bow", "Calvery", "CentauroidCavalry", "DemonicInfantry", "DinosaurLegion", "HeavyAxe", "HeavyHorse", "HeavyPluglunk", "HeavySword", "HorseArcher", "IntelligentMold", "KoboldicInfantry", "LightBow", "LightHorse", "LightSpear", "LightSword", "MediumSpear", "PikeMan", "RocRider", "SpiderLegion", "WargRider", "WebWarriors", "WraithTroops", "WyvernAirtroops", "Zeppelin", "ZombieInfantry", " ", " ", " " }));
        ComboSeven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboSevenActionPerformed(evt);
            }
        });

        ComboSix.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bow", "Calvery", "CentauroidCavalry", "DemonicInfantry", "DinosaurLegion", "HeavyAxe", "HeavyHorse", "HeavyPluglunk", "HeavySword", "HorseArcher", "IntelligentMold", "KoboldicInfantry", "LightBow", "LightHorse", "LightSpear", "LightSword", "MediumSpear", "PikeMan", "RocRider", "SpiderLegion", "WargRider", "WebWarriors", "WraithTroops", "WyvernAirtroops", "Zeppelin", "ZombieInfantry", " ", " ", " " }));
        ComboSix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboSixActionPerformed(evt);
            }
        });

        ComboNine.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bow", "Calvery", "CentauroidCavalry", "DemonicInfantry", "DinosaurLegion", "HeavyAxe", "HeavyHorse", "HeavyPluglunk", "HeavySword", "HorseArcher", "IntelligentMold", "KoboldicInfantry", "LightBow", "LightHorse", "LightSpear", "LightSword", "MediumSpear", "PikeMan", "RocRider", "SpiderLegion", "WargRider", "WebWarriors", "WraithTroops", "WyvernAirtroops", "Zeppelin", "ZombieInfantry", " ", " ", " " }));
        ComboNine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboNineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2))
                            .addComponent(jButton4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(comboOne, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ComboTwo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ComboThree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ComboFour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel12))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ComboFive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel14)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ComboSeven, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ComboEight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ComboNine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel18)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ComboSix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15)))
                        .addGap(2, 2, 2)))
                .addComponent(Result, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(0, 0, 0)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(ComboTwo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(ComboThree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(ComboFour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ComboEight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel14)
                                .addComponent(ComboFive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ComboNine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel18)))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Result, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(51, 51, 51)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(ComboSix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel15))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(ComboSeven, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel16))))
                            .addGap(66, 66, 66)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboOne, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(4, 4, 4)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton3))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        
        ArrayList<ArmyUnit> attackers = new ArrayList<>();
        ArrayList<ArmyUnit> defencers = new ArrayList<>();

        HashMap units = new HashMap();
        MapHex hex1 = new MapHex();
        TerrainType tt1 = null;


        switch (itemText) {
            case "Blasted":
                tt1 = new TTBlasted();
                break;
            case "Broken":
                tt1 = new TTBroken();
                break;
            case "Clear":
                tt1 = new TTClear();
                break;
            case "Cultivated":
                tt1 = new TTCultivated();
                break;                
            case "Forest":
                tt1 = new TTForest();
                break;
            case "Glacier":
                tt1 = new TTGlacier();
                break;                
            case "Karoo":
                tt1 = new TTKaroo();
                break;
            case "Mountains":
                tt1 = new TTMountains();
                break;                
            case "Rough":
                tt1 = new TTRough();
                break;
            case "Swamp":
                tt1 = new TTSwamp();
                break;                
            case "Water":
                tt1 = new TTWater();
                break;
            case "Woods":
                tt1 = new TTWoods();
                break; 
            default:
                break;
                          
        }
        hex1.setTerrainType(tt1);        
        
        // Sorry, I commented out the method definitions to get things compiling!
        //Create_unit1(Units1_name, Units1_racial, units, attackers);
        //Create_unit2(Units2_name, Units2_racial, units, attackers);
        //Create_unit3(Units3_name, Units3_racial, units, defencers);
        //Create_unit4(Units4_name, Units4_racial, units, defencers);


        results = PrepareAttackResults(attackers, defencers, hex1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        javax.swing.JOptionPane.showMessageDialog(null, "Successful Exit.");
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        jTextField2.setText("");
        jTextField3.setText("");


    }//GEN-LAST:event_jButton3ActionPerformed

    private void comboOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboOneActionPerformed
        // TODO add your handling code here:
        itemText = (String)comboOne.getSelectedItem();
        
        

    }//GEN-LAST:event_comboOneActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        javax.swing.JOptionPane.showMessageDialog(null, "Attackers: " + results[0] + "\nDefenders: " + results[1]);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void ComboTwoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboTwoActionPerformed
        // TODO add your handling code here:
        Units1_racial = (String)ComboTwo.getSelectedItem();
    }//GEN-LAST:event_ComboTwoActionPerformed

    private void ComboThreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboThreeActionPerformed
        // TODO add your handling code here:
        Units2_racial = (String)ComboThree.getSelectedItem();
    }//GEN-LAST:event_ComboThreeActionPerformed

    private void ComboFourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboFourActionPerformed
        // TODO add your handling code here:
        Units3_racial = (String)ComboFour.getSelectedItem();
    }//GEN-LAST:event_ComboFourActionPerformed

    private void ComboFiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboFiveActionPerformed
        // TODO add your handling code here:
        Units4_racial = (String)ComboFive.getSelectedItem();
    }//GEN-LAST:event_ComboFiveActionPerformed

    private void ComboEightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboEightActionPerformed
        // TODO add your handling code here:
        Units3_name = (String)ComboEight.getSelectedItem();
    }//GEN-LAST:event_ComboEightActionPerformed

    private void ComboSevenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboSevenActionPerformed
        // TODO add your handling code here:
        Units2_name = (String)ComboSeven.getSelectedItem();
    }//GEN-LAST:event_ComboSevenActionPerformed

    private void ComboSixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboSixActionPerformed
        // TODO add your handling code here:
        Units1_name = (String)ComboSix.getSelectedItem();
    }//GEN-LAST:event_ComboSixActionPerformed

    private void ComboNineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboNineActionPerformed
        // TODO add your handling code here:
        Units4_name = (String)ComboNine.getSelectedItem();
    }//GEN-LAST:event_ComboNineActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(CombatSimulated.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CombatSimulated.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CombatSimulated.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CombatSimulated.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CombatSimulated().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox ComboEight;
    private javax.swing.JComboBox ComboFive;
    private javax.swing.JComboBox ComboFour;
    private javax.swing.JComboBox ComboNine;
    private javax.swing.JComboBox ComboSeven;
    private javax.swing.JComboBox ComboSix;
    private javax.swing.JComboBox ComboThree;
    private javax.swing.JComboBox ComboTwo;
    private javax.swing.JLabel Result;
    private javax.swing.JComboBox comboOne;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

    private class jLabel1 {

        public jLabel1() {
        }
    }
}
