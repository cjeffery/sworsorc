/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainswordsorcery;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    public void initialize(){
        
    }
    //activated by "Start Game" button
    @FXML protected void GotoGame(ActionEvent event) throws IOException {
        //LoadScenario() is only here so that a scenario loads if "Start Game" is clicked
        //in the future "Start Game" should be greyed out or unclickable before a scenario is loaded
        //comment:Jay Drage
        LoadScenario(event);
        //check if scenario is loaded and if true then display
        //main hud map
        if(Game.getInstance().scenarioLoaded){
            Node node = (Node) event.getSource();
            Stage stage=(Stage) node.getScene().getWindow();

            stage.setScene(Game.getInstance().getHudScene());
            stage.setFullScreen(true);
            stage.show();
        }
    }
    //activated by "Load Scenario" button
    @FXML protected void LoadScenario(ActionEvent event) {
           //TODO change GUI to show possible scenario files and pass
           //selected file to initScenario() below.
           Game.getInstance().initScenario( "filename" );
    }
    //activated by "Network" button
    @FXML protected void GotoNetworkLobby(ActionEvent event){
            Game.getInstance().initNetwork();
    }
    //activated by "Quit" button
    @FXML protected void Quit(ActionEvent event) {
        Game.getInstance().stop();
    }
}
