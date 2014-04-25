/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package mainswordsorcery;

import sscharts.Scenario;

/**
*
* @author Johnathan Flake
* CS 383 - University of Idaho
* 2/27/2014 - 4/15/2014
*/


public class SolarDisplay {
    /**
* @param args the command line arguments
*/


        static String SunImage, BlueState, RedState;
        
        static int BlueSunStart = Scenario.getBlueSunStart();
        // the red sun is half-way around the orbit, which ranges 1-12, not 0-11
        static int RedSunStart = (BlueSunStart + 5)%12 + 1;
        static int YellowSunStart = 1;
        
        static int SolarDayNum = YellowSunStart;
        static int RedSunVal = RedSunStart;
        static int BlueSunVal = BlueSunStart;
        
    private static SolarDisplay instance;

    // static method to get instance of view
    public static SolarDisplay getInstance() {
        return instance;
    }
    
    public SolarDisplay() {
        instance = this;   
    }
    
    public static String GetRedState(){
        //return RedState+" ("+RedSunVal+")"; // <- useful for debugging
        return RedState;
    }
    public static String GetBlueState(){
        //return BlueState+" ("+BlueSunVal+")"; // <- useful for debugging
        return BlueState;
    }
    public static String GetSunImage(){
        return SunImage;
    }
    
    public static void SunInit() {
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
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 3 && BlueSunVal < 7){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 5: case 6:
                    if (RedSunVal > 4 && RedSunVal < 8){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 4 && BlueSunVal < 8){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 7: case 8: case 9:
                    if (RedSunVal > 5 && RedSunVal < 9){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 5 && BlueSunVal < 9){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 10: case 11:
                    if (RedSunVal > 6 && RedSunVal < 10){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 6 && BlueSunVal < 10){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 12: case 13:
                    if (RedSunVal > 7 && RedSunVal < 11){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 7 && BlueSunVal < 11){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 14: case 15:
                    if (RedSunVal > 8 && RedSunVal < 12){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 8 && BlueSunVal < 12){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 16: case 17:
                    if (RedSunVal > 9 && RedSunVal < 13){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 9 && BlueSunVal < 13){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 18: case 19: case 20:
                    if (RedSunVal == 11 || RedSunVal == 12
                            || RedSunVal == 1){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal == 11 || BlueSunVal == 12
                            || BlueSunVal == 1){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 21: case 22:
                    if (RedSunVal == 12 || RedSunVal == 1
                            || RedSunVal == 2){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal == 12 || BlueSunVal == 1
                            || BlueSunVal == 2){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 23: case 24:
                    if (RedSunVal > 0 && RedSunVal < 4){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 0 && BlueSunVal < 4){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 25: case 26:
                    if (RedSunVal > 1 && RedSunVal < 5){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 1 && BlueSunVal < 5){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
            }
    }
    
    public static void SunCalc() {
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
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 3 && BlueSunVal < 7){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 5: case 6:
                    if (RedSunVal > 4 && RedSunVal < 8){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 4 && BlueSunVal < 8){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 7: case 8: case 9:
                    if (RedSunVal > 5 && RedSunVal < 9){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 5 && BlueSunVal < 9){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 10: case 11:
                    if (RedSunVal > 6 && RedSunVal < 10){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 6 && BlueSunVal < 10){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 12: case 13:
                    if (RedSunVal > 7 && RedSunVal < 11){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 7 && BlueSunVal < 11){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 14: case 15:
                    if (RedSunVal > 8 && RedSunVal < 12){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 8 && BlueSunVal < 12){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 16: case 17:
                    if (RedSunVal > 9 && RedSunVal < 13){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 9 && BlueSunVal < 13){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 18: case 19: case 20:
                    if (RedSunVal == 11 || RedSunVal == 12
                            || RedSunVal == 1){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal == 11 || BlueSunVal == 12
                            || BlueSunVal == 1){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 21: case 22:
                    if (RedSunVal == 12 || RedSunVal == 1
                            || RedSunVal == 2){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal == 12 || BlueSunVal == 1
                            || BlueSunVal == 2){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 23: case 24:
                    if (RedSunVal > 0 && RedSunVal < 4){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 0 && BlueSunVal < 4){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
                    case 25: case 26:
                    if (RedSunVal > 1 && RedSunVal < 5){
                        RedState = ("Ascension");
                        BlueState = ("Descension");
                        SunImage = ("file:resources/images/RedSun.png");
                    }
                    else if (BlueSunVal > 1 && BlueSunVal < 5){
                        RedState = ("Descension");
                        BlueState = ("Ascension");
                        SunImage = ("file:resources/images/BlueSun.png");
                    }
                    else{
                        RedState = ("Equilibrium");
                        BlueState = ("Equilibrium");
                        SunImage = ("file:resources/images/YellowSun.png");
                    }
                break;
            }
    }
    
}