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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import solardisplay.SolarDisplay;
import Units.MoveableUnit;
 
public class HUDController {
    @FXML private TabPane Units;
    @FXML private TabPane Targets;
    @FXML private ImageView sun_img;
    @FXML private Text RedSun;
    @FXML private MenuBar menuBar;
    @FXML private TextField message_box;
    @FXML private TextArea chat_box;
    @FXML private ScrollPane map_view;
    @FXML private ScrollPane mini_map;

    /** 
     * initialize() is used to connect GUI view elements with model elements. 
     * example code in HUDController.java
     * 
     * @author Jay Drage        
     */
    public void initialize(){
        //this adds mouse support to map_view, just a placeholder for now
        map_view.setOnMousePressed(new EventHandler<MouseEvent>() {
		public void handle (MouseEvent mouseEvent) {
			System.out.println("X: " + mouseEvent.getX() + " Y: " + mouseEvent.getY());
		}
	});
       //this adds mouse support to mini_map
        mini_map.setOnMousePressed(new EventHandler<MouseEvent>() {
		public void handle (MouseEvent mouseEvent) {
			System.out.println("X: " + mouseEvent.getX() + " Y: " + mouseEvent.getY());
		}
	});
    }
    
    //called when clicking a friendly hex
    @FXML protected void DisplayUnits(ActionEvent event) {
        DisplayStack(Units /*,stack*/);
    }
    
    //called when clicking an enemy hex
    @FXML protected void DisplayTargets(ActionEvent event) {
        DisplayStack(Targets /*,stack*/);
    }
    
    /** 
     * constructs the tab pane for the selected hex
     * @author Joe Higley      
     */
    protected void DisplayStack(TabPane tp /*,stack*/) {
        ClearTitles(tp); //empty the tab pane
        int x = Game.getInstance().getNum(); //this is just a random number of units, will be replaced later
        //create array of tabs
        Tab[] tabs = new Tab[x];
        for(int i=0; i < x; i++) {
            tabs[i] = new Tab();
            tabs[i].setText("Unit " + i);
            FillStats(tabs[i]); //add contents of the tab
        }
        //add tabs to tab pane
        tp.getTabs().addAll(tabs);
    }

    /** 
     * fills the contents of a tab pane
     * with unit stats.
     * @author Joe Higley      
     */
    public void FillStats(Tab tp){
        //stats are kept in a GridPane inside a ScrollPane in case of more stats than space
        ScrollPane scroll = new ScrollPane();
        GridPane grid = new GridPane();
        //set grid formats (may be replaced by css file later)
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(8);
        grid.setHgap(15);
        grid.setPadding(new Insets(5, 5, 5, 5));
        
        //set column1 format
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.LEFT);
        column1.setPercentWidth(50);
        grid.getColumnConstraints().add(column1); 
        //set column2 format
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.RIGHT);
        column2.setPercentWidth(50);
        grid.getColumnConstraints().add(column2);
        
        //adds stats to GridPane (will be replaced with call to unit stats)
        for(int i=0; i < 8; i++){
            Label l = new Label("Field #" + i +": ");
            l.setFont(Font.font(null, FontWeight.BOLD, 12));
            
            TextField tf = new TextField("Stat #" + i);
            tf.setAlignment(Pos.CENTER_RIGHT);
            
            //add stats to new row of GridPane
            grid.add(l, 0, i);
            grid.add(tf, 1, i);
        }
        
        //add GridPane to ScrollPane
        scroll.setFitToWidth(true);
        scroll.setContent(grid);
        //add ScrollPane to Tab
        tp.setContent(scroll);
        
    }
    
    /** 
     * 
     * @author Joe Higley      
     */    
    protected void ClearTitles(TabPane tp) {
        tp.getTabs().clear();
    }
    
    /** 
     * Displays user text in chat_box
     * 
     * @author Joe Higley      
     */    
    @FXML protected void SubmitToChat(ActionEvent event) {
        if(!"".equals(message_box.getText())) {
            chat_box.appendText("<username> " + message_box.getText() + "\n");
            message_box.clear();
        }
    }
    
    /** 
     * Changes current scene to main menu and shows main menu     * 
     * @author Joe Higley      
     */
    @FXML protected void Quit(ActionEvent event) {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.setScene(Game.getInstance().getMainScene());
        stage.show();    
    }
    /** 
     * SolarDisplay code goes here
     * 
     * @author Joe Higley      
     */   
    @FXML protected void ChangeSun(ActionEvent event) {
  //      SolarDisplay.getInstance().function();
  //      sun_img.setImage(new Image("@red_sun6.png")); //place holder
  //      RedSun.setText(SolarDisplay.getInstance().getRedSun());
    }

}
