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
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import solardisplay.SolarDisplay;
 
public class HUDController {
    @FXML private TabPane Units;
    @FXML private TabPane Targets;
    @FXML private ImageView sun_img;
    @FXML private Text RedSun;
    
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
    
    @FXML protected void ClearTitles(ActionEvent event) {
        Units.getTabs().clear();
        Targets.getTabs().clear();
    }
    
    @FXML protected void Quit(ActionEvent event) {
        System.exit(0);        
    }
    
    //SolarDisplay code goes here
    @FXML protected void ChangeSun(ActionEvent event) {
  //      SolarDisplay.getInstance().function();
  //      sun_img.setImage(new Image("@red_sun6.png")); //place holder
  //      RedSun.setText(SolarDisplay.getInstance().getRedSun());
    }

}
