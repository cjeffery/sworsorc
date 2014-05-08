/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainswordsorcery;

import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sscharts.RandomEventTable;
import sscharts.Scenario;

/**
 *
 * @author Joe Higley
 */
public class MainMenuController {
    /** 
     * initialize() is used to connect GUI view elements with model elements. 
     * example code in HUDController.java
     * @author Jay Drage        
     */
    Stage  stage;
    private String chosenScenario;
     
    public void initialize(){
        
    }
    //activated by "Start Game" button
    @FXML protected void GotoGame(ActionEvent event) throws IOException {
        //LoadScenario() is only here so that a scenario loads if "Start Game" is clicked
        //in the future "Start Game" should be greyed out or unclickable before a scenario is loaded
        //comment:Jay Drage
        LoadScenario(event);
        setScenario();
        //check if scenario is loaded and if true then display
        //main hud map
        if(Game.getInstance().scenarioLoaded){
            HUDController c = Game.getInstance().hudController;
            c.initializeSunStuff();
            Node node = (Node) event.getSource();
            stage=(Stage) node.getScene().getWindow();

            stage.setScene(Game.getInstance().getHudScene());
            stage.setFullScreen(Game.getInstance().fullscreen);
            stage.show();
            RandomEventTable.getInstance().DisplayEvent();
        }
    }
    //activated by "Load Scenario" button
    @FXML protected void LoadScenario(ActionEvent event) {
            //TODO change GUI to show possible scenario files and pass
            //selected file to initScenario() below.
            String currentDir = System.getProperty("user.dir") + File.separator;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.setInitialDirectory(new File(currentDir + "/resources/scenarios")); 
            File file = fileChooser.showOpenDialog(stage);
            if(file != null)
            {
                chosenScenario = new String();
                chosenScenario = file.getPath();
                Game.getInstance().initScenario(chosenScenario);
            }
            else {
                //Game.getInstance().initScenario("resources/scenarios/");
            }
    }
    //activated by "Network" button
    @FXML protected void GotoNetworkLobby(ActionEvent event){
            Game.getInstance().initNetwork();
    }
    //activated by "Quit" button
    @FXML protected void Quit(ActionEvent event) {
        Game.getInstance().stop();
    }
    public void setScenario()
    {
        Game.getInstance().chosenScenario = chosenScenario;
    }
}
