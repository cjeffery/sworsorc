/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package Units;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import mainswordsorcery.Game;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author David Klingenberg
 */
public class Stack {
    String path = "resources/images/units/";
    
    public static boolean overStackWaring(ArrayList<String> unitList){
        return overStackWaring(unitList, true);
    }
    
    public static boolean overStackWaring(ArrayList<String> unitList, Boolean Graf){
        if (unitList.size() > 2){
            if (Graf){
                Dialogs.create()
                .title("Stack Warring")
                .masthead("One too many!")
                .message("You will hafe to eleminate " + 
                        (unitList.size() - 2) +
                        " unit at the end of your move phase if you dont move them.")
                .actions(Dialog.Actions.OK)
                .showConfirm();
            }
            else
                System.out.println("Over Stack limit by: " +
                        Integer.toString((unitList.size() - 2)));
            
            return true;
        }
        return false;
    }

    public void removeOverStack(MoveableUnit unit) {
        final Popup popup = new Popup();
        
        //UnitColor c;
        
        
        //Stage stage = new Stage();
        
        //stage.setScene(Game.getInstance().getHudScene());
        
        BorderPane border = new BorderPane();
        border.setTop(this.addHBox());
        border.setCenter(addFlow(unit));
        border.setBottom(this.addVbox(popup));
        this.traverse(border);
       
        
        popup.setAutoFix(false);
        popup.setHideOnEscape(true);
        popup.getContent().addAll(border);
        popup.setX(350);
        popup.setY(350);
        popup.centerOnScreen();
        
        popup.show(Game.getInstance().getStage()); //Game.getInstance().getHudScene());
        //Scene scene = new Scene(border, 300, 300);

        //stage.


        //stage.setTitle("Tisk! Tisk!");
        //stage.setScene(scene);
        //stage.show();
    }

    private FlowPane addFlow(MoveableUnit unit){
        
        FlowPane flow = new FlowPane();
        
        flow.setPadding(new Insets(1, 0, 1, 0));
        flow.setVgap(2);
        flow.setHgap(2);
        flow.setPrefWrapLength(170); // preferred width allows for two columns
        flow.setStyle("-fx-background-color: #DAE6F3;");
        
        /*ImageView pages[] = new ImageView[8];
        for (int i=0; i<8; i++) {
            pages[i] = new ImageView(
                new Image(LayoutSample.class.getResourceAsStream(
                "graphics/chart_"+(i+1)+".png")));
            flow.getChildren().add(pages[i]);
        }*/
        
        
       
        flow.getChildren().add(addButton(unit));
        //flow.getChildren().add(addButton(unit));
       
        //flow.getChildren().add(addButton(unit));
        //flow.getChildren().add(addButton(unit));
        
        return flow;
    }
    
    private ToggleButton addButton(MoveableUnit unit){
        ToggleButton btn = new ToggleButton();
  
        Polygon polygon = new Polygon();
        
        polygon.getPoints().addAll(new Double[]{
            20.0, 0.0, 
            40.0, 0.0,
            50.0, 20.0,
            40.0, 40.0,
            20.0, 40.0,
            10.0, 20.0

        });
        Group blend = new Group();
        ImageView iv = new ImageView();
        ImageView badge = new ImageView();
        try {
            File file = new File(path + unit.getClass().getSimpleName() + ".png");
            String localUrl = file.toURI().toURL().toString();
            Image image = new Image(localUrl, false);
            iv.setImage(image);
            iv.setY(1);
            
            if(   (unit instanceof ArmyUnit)
               && ((ArmyUnit)unit).isDemoralized())
            {
                File fileBadge = new File(path + "demoralized_badge.png");

                localUrl = fileBadge.toURI().toURL().toString();
                Image image2 = new Image(localUrl, false); // don't load in the background
           
                badge.setImage(image2);
                badge.setScaleX(.8);
                badge.setScaleY(.8);
                badge.setY(20);
                
                blend.getChildren().addAll(badge,iv);
            }
            else
                blend.getChildren().add(iv);
        } 
        catch (MalformedURLException ex) {
        // error
        }

        
        
        
        blend.setBlendMode(BlendMode.COLOR_BURN);
        //blend.setScaleX(.5);
        //blend.setScaleY(.5);
        
        btn.setId(unit.getID());
        btn.setScaleX(.5);
        btn.setScaleY(.5);
        btn.setShape(polygon);

        btn.setStyle("-fx-background-color: " + unit.getNation().color() + " ;");
        btn.setGraphic(blend);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
              
               if (btn.isSelected()){
                   btn.setStyle("-fx-background-color: #2F4F4F ;" );
               }
               else{
                   btn.setStyle("-fx-background-color: " + unit.getNation().color() + " ;");
                   blend.setBlendMode(BlendMode.DARKEN);
               }
            }
        });
        
        return btn;
    }
    
    private HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        
        Text txt = new Text(50,50,"Stack overflow.  You must eleminate unit(s).");
        Text txt2 = new Text("Test Code! Dose Nothing.");
        txt2.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        hbox.getChildren().addAll(txt,txt2);

        return hbox;
    }

     private VBox addVbox(Popup box){
        
     VBox hBox = new VBox();
     Button btn = new Button();
     
     btn.setText("Remove Units");
     btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                box.hide();
            }
        });
     hBox.setPadding(new Insets(15, 12, 15, 12));
     hBox.setStyle("-fx-background-color: #234679;"); 
    
     hBox.getChildren().addAll(btn);
     
     return hBox;
    }
    
    private void traverse (Parent node){
        for (Node subNode : node.getChildrenUnmodifiable()){
            System.out.println(subNode.getClass().getSimpleName() + subNode.getStyleClass() + subNode.getId());
            if(subNode instanceof Parent){
                traverse((Parent)subNode);
            }
        } 
    }
}