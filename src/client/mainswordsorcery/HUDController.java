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
    /** 
     * 
     * @author Joe Higley      
     */
    @FXML protected void DisplayStack(ActionEvent event) {
        ClearTitles(event);
        int x = Game.getInstance().getNum();
        Tab[] ups = new Tab[x];
        Tab[] tps = new Tab[x];
        for(int i=0; i < x; i++){
            tps[i] = new Tab();
            ups[i] = new Tab();
            tps[i].setText("Target " + i);
            FillStats(tps[i]);
            ups[i].setText("Unit " + i);
            FillStats(ups[i]);
        }
        Units.getTabs().addAll(ups);
        Targets.getTabs().addAll(tps);
    }
    /** 
     * 
     * @author Joe Higley      
     */
    public void FillStats(Tab tp){
        ScrollPane scroll = new ScrollPane();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(8);
        grid.setHgap(15);
        grid.setPadding(new Insets(5, 5, 5, 5));
        
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.LEFT);
        column1.setPercentWidth(50);
        grid.getColumnConstraints().add(column1); 

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.RIGHT);
        column2.setPercentWidth(50);
        grid.getColumnConstraints().add(column2);
        
        for(int i=0; i < 8; i++){
            Label l = new Label("Field #" + i +": ");
            l.setFont(Font.font(null, FontWeight.BOLD, 12));
            
            TextField tf = new TextField("Stat #" + i);
            tf.setAlignment(Pos.CENTER_RIGHT);
            
            grid.add(l, 0, i);
            grid.add(tf, 1, i);
        }
        
        scroll.setFitToWidth(true);
        scroll.setContent(grid);
        tp.setContent(scroll);
        
    }
    /** 
     * 
     * @author Joe Higley      
     */    
    @FXML protected void ClearTitles(ActionEvent event) {
        Units.getTabs().clear();
        Targets.getTabs().clear();
    }
    /** 
     * Displays user text in chat_box
     * 
     * @author Joe Higley      
     */    
    @FXML protected void SubmitToChat(ActionEvent event) {
        chat_box.appendText("<username> " + message_box.getText() + "\n");
        message_box.clear();
    }
    /** 
     * Changes current scene to main menu and shows main menu
     * 
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
