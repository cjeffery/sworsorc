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


        String SunImage, BlueState, RedState;
        
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
           
    //for comparing strings, use if(string1.equals(string2))
    }
    
    public String GetRedState(){
        return RedState;
    }
    public String GetBlueState(){
        return BlueState;
    }
    public String GetSunImage(){
        return SunImage;
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
//            SolarDay.setText("Day: " + SolarDayNum);
            
            switch (SolarDayNum){
                case 1: case 2: case 27:
                    if (RedSunVal > 2 && RedSunVal < 6){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 2 && BlueSunVal < 6){
                        RedState = ("Descension");
                        BlueState =("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                case 3: case 4:
                    if (RedSunVal > 3 && RedSunVal < 7){
                        RedSun.setText("Ascension");
                        BlueSun.setText("Descension");
                        SunImageLabel.setIcon(RedImageIcon);
                    }
                    else if (BlueSunVal > 3 && BlueSunVal < 7){
                        RedSun.setText("Descension");
                        BlueSun.setText("Ascension");
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