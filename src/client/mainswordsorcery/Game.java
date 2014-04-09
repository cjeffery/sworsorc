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

/**
 *
 * @author higle_000
 */

public class Game extends Application {
    /** the singleton unit hash tree UnitPool variable */
    UnitPool unitPool;
    
    //Stage setup content
    private Parent main;
    private Parent hud;
    /** JavaFX scene for the main menu */
    private Scene mainMenu;
    /** JavaFX scene for the HUD window */
    private Scene hudWindow;
    
    @Override
    public void start(Stage stage) throws IOException {
        main = createScene("MainMenu.fxml");
        hud = createScene("hud.fxml");
        
        String mainCSS = getClass().getResource("mainMenu.css").toExternalForm();
        
        mainMenu = new Scene(main);
        mainMenu.getStylesheets().add(mainCSS);
        
        hudWindow = new Scene(hud);
        
        stage.setTitle("Scenario");
        stage.setScene(mainMenu);
        stage.setFullScreen(true);
        stage.show();
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
        launch(args);
    }
   
    private static Game instance;
    public Game() {
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
            //TODO load simple test scenario
            
        }
        else{
            //TODO implement real initScenario from scenario file
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
