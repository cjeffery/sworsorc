/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainswordsorcery;

/**
 *
 * @author Joe Higley
 */
import java.io.IOException;
import java.util.Random;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Units.UnitPool;
import Units.*;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import sscharts.Scenario;

/**
 *
 * @author higle_000
 */
public class Game extends Application {
    //current client player id
    //set to 0 for testing convenience
    //changed by network
    int PlayerID = 0;
    /** true if a scenario is loaded */
    boolean scenarioLoaded;
    /** the singleton unit hash tree UnitPool variable */
    UnitPool unitPool;
    //Stage setup content
    private Parent main;
    private Parent hud;
    private Parent diplo;
    /** JavaFX scene for the main menu */
    private Scene mainMenu;
    /** JavaFX scene for the HUD window */
    private Scene hudWindow;
    /** JavaFX scene for the Diplomacy window */
    private Scene Diplomacy;
    
    /** Stored reference to the HUDController instance used by JavaFX*/
    public HUDController hudController;

    /* window options, for development convenience */
    static int window_flag = 0; //0 = fullscreen, 1 = left, 2 = right, 3 = wind
    public boolean fullscreen = true;
    double screenX;
    double screenY;
    double screenW;
    double screenH;
    
    /** part of an ugly hack to set screen dimensions if command line option
     * is entered
     */ 
    private void setScreenDimensions() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        screenX = screenBounds.getMinX();
        screenY = screenBounds.getMinY();
        screenW = screenBounds.getWidth();
        screenH = screenBounds.getHeight();
        fullscreen = (window_flag == 0);
        if(window_flag == 1) {
            screenW /= 2;          
        }
        else if(window_flag == 2) {
            screenX = (screenX = screenW / 2);
            screenW /= 2;
        }
        stage.setX(screenX);
        stage.setY(screenY);
        stage.setWidth(screenW);
        stage.setHeight(screenH);
                
    }
    private Stage stage;
    public Stage getStage() {
        return stage;
    }
    
    @Override
    public void start(Stage st) throws IOException {
        stage = st;
        setScreenDimensions();
        
        main = createScene("MainMenu.fxml");
        hud = createScene("hud.fxml");
        diplo = createScene("Diplomacy.fxml");

        //We can create main normally:
        main = createScene("MainMenu.fxml");         
        
        //We need to use an fxmlLoader instance to load the HUD, in order to
        //get a reference to the correct controller instance.
        //If we use the static methods, we'll get reference to two
        //different controller instances:
        FXMLLoader fxmlLoader = new FXMLLoader(); 
        URL url = getClass().getResource("hud.fxml");
        fxmlLoader.setLocation(url);
        
        hud = fxmlLoader.load(url.openStream()); //Load the hud, call this only once!

        
        //Use the same loader to get a reference to the actual controller instance:
        hudController = (HUDController) fxmlLoader.getController();
       
        // load the Main Menu font.
        URL fontURL = new URL("file:resources/font/upcjb.ttf");
        Font.loadFont(
            fontURL.toExternalForm(), 
            10
        );
        // get the css file
        String mainCSS = getClass().getResource("mainMenu.css").toExternalForm();
        
        main.getStylesheets().add(mainCSS);
//        hud.getStylesheets().add(mainCSS);
        mainMenu = new Scene(main, screenW, screenH);        
        hudWindow = new Scene(hud, screenW, screenH);

        Diplomacy = new Scene(diplo, 300, 300);
        

        stage.setTitle("Scenario");
        stage.setScene(mainMenu);
        
        if(fullscreen) {
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
        }
        
        stage.show();
    }
    
    /**
     * Cleanup code goes here
     */
    @Override
    public void stop() {
        System.exit(0);     
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for(String s : args) {
            if(s.equals("left")) {
                window_flag = 1;
                break;
            }
            if(s.equals("right")) {
                window_flag = 2;
                break;
            }
            if(s.equals("window")) {
                 window_flag = 3;
                 break;
            }
        }
        launch(args);
    }
   
    private static Game instance;
    public Game() {
           scenarioLoaded = false;
           instance = this;
    }
   /**
    * static method to get instance of view
    * 
    * @author Joe Higley
    */     
    public static Game getInstance() {
        return instance;
    }  
   /**
    * Setup a scene with the correct fxml layout
    * 
    * @author Joe Higley
    */    
    public Parent createScene(String str) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(str));
        return root;
    }
   /**
    * Returns HudScene scene
    * 
    * @author Joe Higley
    */     
    public Scene getHudScene(){
        return hudWindow;
    }
   /**
    * Returns Main Menu scene
    * 
    * @author Joe Higley
    */    
    public Scene getMainScene(){
        return mainMenu;
    }
    /**
    * Returns Diplomacy Map scene
    * 
    * @author Sean Shepherd
    */   
    public Scene getDiploScene(){
        return Diplomacy;
    }
   /**
    * SolarDisplay code goes here
    * 
    * @author Joe Higley
    */
    public int getNum(){
        Random rand = new Random();
        int x = rand.nextInt(9);
        return x;
    }
    /** 
     * used to initialize scenario. called from MainMenuController::LoadScenario()
     * 
     * @author Jay Drage
     * @param scenarioFile the path/filename of the scenario to be loaded.
     */
    public void initScenario(String scenarioFile){
        //set to true to load sample scenario
        //set to false to run actual initScenario
        boolean testScenario = true;
        unitPool = UnitPool.getInstance();
        unitPool.clear();
        if(testScenario){
            // initialize dummy scenario, populate unit pool from it
            Scenario.Initialize("resources/scenarios/7_Dwarro_Orcish_War.json");
            Scenario.populatePool();
            
            //scenario loading complete
            scenarioLoaded = true;
        }
        else{
            //TODO implement real initScenario by providing choice of 
            //     config file (in resources/scenarios)
        }
    }
    /** 
     * used to initialize network. called from MainMenuController::GotoNetworkLobby()
     * 
     * @author Jay Drage        
     */
    public void initNetwork(){
        //TODO connect to network
    }
}
