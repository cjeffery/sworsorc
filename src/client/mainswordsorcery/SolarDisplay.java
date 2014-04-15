/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package mainswordsorcery;

/**
*
* @author Johnathan Flake
* CS 383 - University of Idaho
* 2/27/2014
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SolarDisplay {
    /**
* @param args the command line arguments
*/
    
        JLabel Title, SolarDay, RedSun, BlueSun, SunImageLabel;
        JButton AddDay;
        ImageIcon SunImageIcon, BlueImageIcon, RedImageIcon;
        
        int SolarDayNum = 1;
        int RedSunVal = 1;
        int BlueSunVal = 7;
        
    private static SolarDisplay instance;

    // static method to get instance of view
    public static SolarDisplay getInstance() {
        return instance;
    }
    
    public SolarDisplay() {
        instance = this;   
        
//        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.PAGE_AXIS));
//        setLayout(new GridLayout(6,1)); //6 rows, 1 column
/*        
        Title = new JLabel ("Solar Display", SwingConstants.CENTER);
        Title.setFont(new java.awt.Font("Tahoma", 0, 18));
        
        SolarDay = new JLabel("Day: 1", SwingConstants.CENTER);
        SolarDay.setFont(new java.awt.Font("Tahoma", 0, 14));
        
        RedSun = new JLabel("Red Sun State: Equilibrium", SwingConstants.CENTER);
        BlueSun = new JLabel("Blue Sun State: Equilibrium", SwingConstants.CENTER);
        
//        SunImage = createImageIcon("resources/images/YellowSun.png");
        
        AddDay = new JButton("Add Day");
        AddDay.setActionCommand("AddDay");
        AddDay.addActionListener(this);

        
        try {
            File SunImage = new File("resources/images/YellowSun.png");
            File BlueImage = new File("resources/images/BlueSun.png");
            File RedImage = new File("resources/images/RedSun.png");
            BufferedImage SunImg_buff = ImageIO.read(SunImage);
            BufferedImage BlueImg_buff = ImageIO.read(BlueImage);
            BufferedImage RedImg_buff = ImageIO.read(RedImage);
            SunImageIcon = new ImageIcon(SunImg_buff);
            BlueImageIcon = new ImageIcon(BlueImg_buff);
            RedImageIcon = new ImageIcon(RedImg_buff);
            SunImageLabel = new JLabel(SunImageIcon);
        }
        catch(IOException e){
            SunImageIcon = new ImageIcon();
            System.out.println("Loading image failed");
        }
        
//        add(Title);
//        add(SolarDay);
//        add(RedSun);
//        add(BlueSun);
//        add(AddDay);
//        add(SunImageLabel);
        
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBounds(0, 0, 175, 290);
//        pack();
//        setLocationRelativeTo(null);
//        setVisible(true);
        
*/
/*        
}
    protected ImageIcon createImageIcon(String filename) {
    if (filename != null) {
        return new ImageIcon(filename);
    } else {
        System.err.println("Couldn't find file: " + filename);
        return null;
    }
}
*/    
    //for comparing strings, use if(string1.equals(string2))
    }
    public void SunCalc() {
            if (SolarDayNum < 27) {
            SolarDayNum++;
            }
            else {
                SolarDayNum = 1;
            }
            if (RedSunVal < 12){
                RedSunVal++;
            }
            else {
                RedSunVal = 1;
            }
            if (BlueSunVal < 12){
                BlueSunVal++;
            }
            else {
                BlueSunVal = 1;
            }
            SolarDay.setText("Day: " + SolarDayNum);
            
            switch (SolarDayNum){
                case 1: case 2: case 27:
                    if (RedSunVal > 2 && RedSunVal < 6){
                        RedSun.setText("Red Sun State: Ascension");
                        BlueSun.setText("Blue Sun State: Descension");
                        SunImageLabel.setIcon(RedImageIcon);
                    }
                    else if (BlueSunVal > 2 && BlueSunVal < 6){
                        RedSun.setText("Red Sun State: Descension");
                        BlueSun.setText("Blue Sun State: Ascension");
                        SunImageLabel.setIcon(BlueImageIcon);
                    }
                    else{
                        RedSun.setText("Red Sun State: Equilibrium");
                        BlueSun.setText("Blue Sun State: Equilibrium");
                        SunImageLabel.setIcon(SunImageIcon);
                    }
                break;
                case 3: case 4:
                    if (RedSunVal > 3 && RedSunVal < 7){
                        RedSun.setText("Red Sun State: Ascension");
                        BlueSun.setText("Blue Sun State: Descension");
                        SunImageLabel.setIcon(RedImageIcon);
                    }
                    else if (BlueSunVal > 3 && BlueSunVal < 7){
                        RedSun.setText("Red Sun State: Descension");
                        BlueSun.setText("Blue Sun State: Ascension");
                        SunImageLabel.setIcon(BlueImageIcon);
                    }
                    else{
                        RedSun.setText("Red Sun State: Equilibrium");
                        BlueSun.setText("Blue Sun State: Equilibrium");
                        SunImageLabel.setIcon(SunImageIcon);
                    }
                break;
                    case 5: case 6:
                    if (RedSunVal > 4 && RedSunVal < 8){
                        RedSun.setText("Red Sun State: Ascension");
                        BlueSun.setText("Blue Sun State: Descension");
                        SunImageLabel.setIcon(RedImageIcon);
                    }
                    else if (BlueSunVal > 4 && BlueSunVal < 8){
                        RedSun.setText("Red Sun State: Descension");
                        BlueSun.setText("Blue Sun State: Ascension");
                        SunImageLabel.setIcon(BlueImageIcon);
                    }
                    else{
                        RedSun.setText("Red Sun State: Equilibrium");
                        BlueSun.setText("Blue Sun State: Equilibrium");
                        SunImageLabel.setIcon(SunImageIcon);
                    }
                break;
                    case 7: case 8: case 9:
                    if (RedSunVal > 5 && RedSunVal < 9){
                        RedSun.setText("Red Sun State: Ascension");
                        BlueSun.setText("Blue Sun State: Descension");
                        SunImageLabel.setIcon(RedImageIcon);
                    }
                    else if (BlueSunVal > 5 && BlueSunVal < 9){
                        RedSun.setText("Red Sun State: Descension");
                        BlueSun.setText("Blue Sun State: Ascension");
                        SunImageLabel.setIcon(BlueImageIcon);
                    }
                    else{
                        RedSun.setText("Red Sun State: Equilibrium");
                        BlueSun.setText("Blue Sun State: Equilibrium");
                        SunImageLabel.setIcon(SunImageIcon);
                    }
                break;
                    case 10: case 11:
                    if (RedSunVal > 6 && RedSunVal < 10){
                        RedSun.setText("Red Sun State: Ascension");
                        BlueSun.setText("Blue Sun State: Descension");
                        SunImageLabel.setIcon(RedImageIcon);
                    }
                    else if (BlueSunVal > 6 && BlueSunVal < 10){
                        RedSun.setText("Red Sun State: Descension");
                        BlueSun.setText("Blue Sun State: Ascension");
                        SunImageLabel.setIcon(BlueImageIcon);
                    }
                    else{
                        RedSun.setText("Red Sun State: Equilibrium");
                        BlueSun.setText("Blue Sun State: Equilibrium");
                        SunImageLabel.setIcon(SunImageIcon);
                    }
                break;
                    case 12: case 13:
                    if (RedSunVal > 7 && RedSunVal < 11){
                        RedSun.setText("Red Sun State: Ascension");
                        BlueSun.setText("Blue Sun State: Descension");
                        SunImageLabel.setIcon(RedImageIcon);
                    }
                    else if (BlueSunVal > 7 && BlueSunVal < 11){
                        RedSun.setText("Red Sun State: Descension");
                        BlueSun.setText("Blue Sun State: Ascension");
                        SunImageLabel.setIcon(BlueImageIcon);
                    }
                    else{
                        RedSun.setText("Red Sun State: Equilibrium");
                        BlueSun.setText("Blue Sun State: Equilibrium");
                        SunImageLabel.setIcon(SunImageIcon);
                    }
                break;
                    case 14: case 15:
                    if (RedSunVal > 8 && RedSunVal < 12){
                        RedSun.setText("Red Sun State: Ascension");
                        BlueSun.setText("Blue Sun State: Descension");
                        SunImageLabel.setIcon(RedImageIcon);
                    }
                    else if (BlueSunVal > 8 && BlueSunVal < 12){
                        RedSun.setText("Red Sun State: Descension");
                        BlueSun.setText("Blue Sun State: Ascension");
                        SunImageLabel.setIcon(BlueImageIcon);
                    }
                    else{
                        RedSun.setText("Red Sun State: Equilibrium");
                        BlueSun.setText("Blue Sun State: Equilibrium");
                        SunImageLabel.setIcon(SunImageIcon);
                    }
                break;
                    case 16: case 17:
                    if (RedSunVal > 9 && RedSunVal < 13){
                        RedSun.setText("Red Sun State: Ascension");
                        BlueSun.setText("Blue Sun State: Descension");
                        SunImageLabel.setIcon(RedImageIcon);
                    }
                    else if (BlueSunVal > 9 && BlueSunVal < 13){
                        RedSun.setText("Red Sun State: Descension");
                        BlueSun.setText("Blue Sun State: Ascension");
                        SunImageLabel.setIcon(BlueImageIcon);
                    }
                    else{
                        RedSun.setText("Red Sun State: Equilibrium");
                        BlueSun.setText("Blue Sun State: Equilibrium");
                        SunImageLabel.setIcon(SunImageIcon);
                    }
                break;
                    case 18: case 19: case 20:
                    if (RedSunVal == 11 || RedSunVal == 12
                            || RedSunVal == 1){
                        RedSun.setText("Red Sun State: Ascension");
                        BlueSun.setText("Blue Sun State: Descension");
                        SunImageLabel.setIcon(RedImageIcon);
                    }
                    else if (BlueSunVal == 11 || BlueSunVal == 12
                            || BlueSunVal == 1){
                        RedSun.setText("Red Sun State: Descension");
                        BlueSun.setText("Blue Sun State: Ascension");
                        SunImageLabel.setIcon(BlueImageIcon);
                    }
                    else{
                        RedSun.setText("Red Sun State: Equilibrium");
                        BlueSun.setText("Blue Sun State: Equilibrium");
                        SunImageLabel.setIcon(SunImageIcon);
                    }
                break;
                    case 21: case 22:
                    if (RedSunVal == 12 || RedSunVal == 1
                            || RedSunVal == 2){
                        RedSun.setText("Red Sun State: Ascension");
                        BlueSun.setText("Blue Sun State: Descension");
                        SunImageLabel.setIcon(RedImageIcon);
                    }
                    else if (BlueSunVal == 12 || BlueSunVal == 1
                            || BlueSunVal == 2){
                        RedSun.setText("Red Sun State: Descension");
                        BlueSun.setText("Blue Sun State: Ascension");
                        SunImageLabel.setIcon(BlueImageIcon);
                    }
                    else{
                        RedSun.setText("Red Sun State: Equilibrium");
                        BlueSun.setText("Blue Sun State: Equilibrium");
                        SunImageLabel.setIcon(SunImageIcon);
                    }
                break;
                    case 23: case 24:
                    if (RedSunVal > 0 && RedSunVal < 4){
                        RedSun.setText("Red Sun State: Ascension");
                        BlueSun.setText("Blue Sun State: Descension");
                        SunImageLabel.setIcon(RedImageIcon);
                    }
                    else if (BlueSunVal > 0 && BlueSunVal < 4){
                        RedSun.setText("Red Sun State: Descension");
                        BlueSun.setText("Blue Sun State: Ascension");
                        SunImageLabel.setIcon(BlueImageIcon);
                    }
                    else{
                        RedSun.setText("Red Sun State: Equilibrium");
                        BlueSun.setText("Blue Sun State: Equilibrium");
                        SunImageLabel.setIcon(SunImageIcon);
                    }
                break;
                    case 25: case 26:
                    if (RedSunVal > 1 && RedSunVal < 5){
                        RedSun.setText("Red Sun State: Ascension");
                        BlueSun.setText("Blue Sun State: Descension");
                        SunImageLabel.setIcon(RedImageIcon);
                    }
                    else if (BlueSunVal > 1 && BlueSunVal < 5){
                        RedSun.setText("Red Sun State: Descension");
                        BlueSun.setText("Blue Sun State: Ascension");
                        SunImageLabel.setIcon(BlueImageIcon);
                    }
                    else{
                        RedSun.setText("Red Sun State: Equilibrium");
                        BlueSun.setText("Blue Sun State: Equilibrium");
                        SunImageLabel.setIcon(SunImageIcon);
                    }
                break;
            }
    }
    
    
    public static void main(String[] args) {
       new SolarDisplay();
    }
}