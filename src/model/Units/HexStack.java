/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package Units;

import Character.Characters;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainswordsorcery.Game;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author David Klingenberg
 */
public class HexStack {
    String path = "resources/images/units/";
    SortedMap<String, Integer> uCount = new TreeMap<String, Integer>();
    ArrayList<MoveableUnit> removeList = new ArrayList<MoveableUnit>();
    Scene popScene;
    public static boolean overStackWaring(ArrayList<String> unitList){
        return overStackWaring(unitList, true);
    }
    
    public static boolean overStackWaring(ArrayList<String> unitList, Boolean Graf){
        int count = unitList.size();
        
        for (String e : unitList)
            if ( UnitPool.getInstance().getUnit(e) instanceof Characters)
                count--;
        
        if (count > 2){
            if (Graf){
                Dialogs.create()
                .title("Stack Warring")
                .masthead("One too many!")
                .message("You will hafe to eleminate " + 
                        (count - 2) +
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

    
    /*public void removeOverStack(SortedMap<String, ArrayList<MoveableUnit>> stackList){
        for(Entry e : stackList.entrySet()){
            removeOverStackPopup((ArrayList<MoveableUnit>)e.getValue());
        }
    }
    */
    
    public void removeOverStack(SortedMap<String, ArrayList<MoveableUnit>> 
            stackList) {
        
        final Stage popup = new Stage();
        final ScrollBar sc = new ScrollBar();
        int count = 0;
              
        Group cent = new Group();
        
        ScrollPane sp = new ScrollPane();
        
        
        FlowPane fp = new FlowPane();
        VBox vb = new VBox();
        sp.setContent(vb);
        fp.setLayoutX(5);
        fp.setPadding(new Insets(5,0,5,0));
        fp.setVgap(4);
        fp.setHgap(4);
                       
        for(Entry e : stackList.entrySet()){
            Object[] temp = addFlow((ArrayList<MoveableUnit>)e.getValue(),count);
            count = count + (int)temp[0];
            vb.getChildren().add((FlowPane)temp[1]);
        
        }
            
        vb.getChildren().add(fp);
      
        cent.getChildren().addAll(vb);
        
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initStyle(StageStyle.UNDECORATED);
      
        BorderPane border = new BorderPane();
                
        border.setTop(this.addHBox());
        border.setCenter(sp);
        border.setBottom(this.addVbox(popup, count));
        this.traverse(border);
        
        StackPane root = new StackPane();
        root.getChildren().add(border);
        
        popScene = new Scene(root,450,250);
               
        popup.centerOnScreen();
        popup.setScene(popScene);
        popup.initOwner(Game.getInstance().getStage());
        //popup.setFullScreen(true);
        popup.show();
    }

    private Object[]  addFlow(ArrayList<MoveableUnit> units , int count){
        Object[] ob = new Object[2];
        FlowPane flow = new FlowPane();
        flow.setPadding(new Insets(1, 0, 1, 0));
        flow.setVgap(2);
        flow.setHgap(2);
        //flow.setPrefWrapLength(250); // preferred width allows for two columns
        flow.setStyle("-fx-background-color: #DAE6F3;"
                + "-fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");
        count = count - 2; 
        for (MoveableUnit s : units){
            
            flow.getChildren().add(addButton(s,units.size()));
            count ++;
        }
        ob[0] = count;
        ob[1]= flow;
        flow.setId(units.get(0).getLocation());
        uCount.put(flow.getId(), 0);
        
        return ob;
    }
    
    private ToggleButton addButton(MoveableUnit unit, int count){
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
        
        btn.setId(unit.getID());
        btn.setScaleX(.5);
        btn.setScaleY(.5);
        btn.setShape(polygon);

        Nation nation = unit.getNation();
        if(nation != null) {
            btn.setStyle(  "-fx-background-color: "
                         + unit.getNation().color() + " ;");
        }
        else {
            btn.setStyle(  "-fx-background-color: #FFFFFF;");
        }
        btn.setGraphic(blend);
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
           public void handle(ActionEvent event) {
               Button tBtn = (Button) popScene.lookup("#Remove");
               if (btn.isSelected() && (count - uCount.get(unit.getLocation())) > 2){
                   btn.setStyle("-fx-background-color: #2F4F4F ;" );
                   uCount.put(unit.getLocation(), uCount.get(unit.getLocation()) + 1);
                   removeList.add(unit);
                   tBtn.setText("new");
               }
               else{
                   btn.setStyle("-fx-background-color: " + unit.getNation().color() + " ;");
                   blend.setBlendMode(BlendMode.DARKEN);
                   if(!btn.isSelected())
                       uCount.put(unit.getLocation(), uCount.get(unit.getLocation()) - 1);
                       removeList.remove(unit);
                       
                       tBtn.setText("new");
                   btn.setSelected(false);
                   btn.disarm();        
               }
               
               
            }
        });
        
        return btn;
    }
    
    private VBox addHBox() {
        VBox hbox = new VBox();
        hbox.setPadding(new Insets(5, 12, 5, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        
        Text txt = new Text(50,50,"Stack overflow.  You must eleminate unit(s).");
        Text txt2 = new Text("Test Code! Dose Nothing.");
        txt2.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        hbox.getChildren().addAll(txt,txt2);

        return hbox;
    }

    private VBox addVbox(Stage box, int count){
        int tCount = count;
        for(Entry e : this.uCount.entrySet())
            tCount= tCount - (Integer) e.getValue();
        VBox hBox = new VBox();
        Button btn = new Button();
        btn.setId("Remove");
        btn.setDefaultButton(true);
        btn.setText("Remove" + tCount + "Units");
        btn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    box.close();
                }
            });
        hBox.setPadding(new Insets(15, 12, 15, 100));
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