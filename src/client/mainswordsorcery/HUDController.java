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
import MoveCalculator.MovementCalculator;
import Units.*;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Arrays;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import sshexmap.MainMap;
import sshexmap.MapHex;

import sshexmap.MapView;

import systemServer.ClientData;
import systemServer.ClientDataForm;
import systemServer.NetworkClient;
 
public class HUDController {
    @FXML private TabPane Units;
    @FXML private TabPane Targets;
    @FXML private MenuBar menuBar;
    @FXML private TextField message_box;
    @FXML private TextArea chat_box;
    @FXML private ScrollPane map_view;
    @FXML private ScrollPane mini_map;
    @FXML private SwingNode hex_map;
    
    @FXML private ImageView SunImage;
    @FXML private Button phaseButton;
    @FXML private Text turn;
    @FXML private Text phase;
    @FXML private Text RedState;
    @FXML private Text BlueState;
    
    ArmyUnit bow = new Bow();
    ArmyUnit lightsword = new LightSword();
    ArmyUnit pike = new PikeMan();

    SwingNode hdip = new SwingNode();      

    
    SwingNode hmap = new SwingNode();
    
    String username, ipAddress;
    boolean usernameEntered, ipEntered, connectedToServer;
    
    //stuff for movement demo hax
    MoveableUnit selected_unit;
    ArrayList<MapHex> canMoveTo;
    

    /** 
     * initialize() is used to connect GUI view elements with model elements. 
     * example code in HUDController.java
     * 
     * @author Jay Drage        
     */
    public void initialize(){
        
        hdip.setContent(MapView.getDipView());
        
        //Display map in map_view
        hmap.setContent(MapView.getMapView());
        map_view.setContent(hmap);
        UnitPool pool = UnitPool.getInstance();
        pike.setRace(Race.Human);
        pool.addUnit(0, pike, "0607");

        /**
         * hacked in movement demo, needs to merge into game flow
         */
        map_view.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override
        public void handle (MouseEvent mouseEvent) {
            MapView mapview = MapView.getMapView();
            MainMap mainmap = MainMap.GetInstance();
            
            String hexID = mapview.hexAt((int)mouseEvent.getX(),
                                         (int)mouseEvent.getY());
            MapHex hex = mainmap.GetHex(hexID);
            if(hex == null) {
                System.out.println("No hex at " + hexID + "???");
            }

            if(selected_unit == null) {
                ArrayList<MoveableUnit> units = hex.getUnits();
                if(units == null || units.size() == 0) {
                    return;
                }
                selected_unit = units.get(0);

                if(selected_unit == null) {
                    System.out.println("Something went horribly wrong");
                }

                canMoveTo = new ArrayList<MapHex>();

                MovementCalculator.getValidMoves(selected_unit, hex, 5, canMoveTo );
                mapview.highlight(canMoveTo);
            }
            else {
                if(canMoveTo.contains(hex)) {
                    mapview.clearHighlights();
                    UnitPool pool = UnitPool.getInstance();
                    pool.addMove(selected_unit, hexID);
                    selected_unit = null;
                }
            }
        }
        });
                
        //this adds mouse support to map_view, just a placeholder for now
        hmap.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
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
        
        connectedToServer = usernameEntered = ipEntered = false;
        chat_box.setText("Enter your username!");
    }
   
    
    //called when clicking a friendly hex
    @FXML protected void DisplayUnits(ActionEvent event) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List <ArmyUnit> AU = new ArrayList <>();
        AU.add(bow);
        AU.add(lightsword);
        AU.add(pike);
        DisplayStack(Units, AU);
    }
    
    //called when clicking an enemy hex
    @FXML protected void DisplayTargets(ActionEvent event) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        List <ArmyUnit> AU = new ArrayList <>();
        AU.add(lightsword);
        AU.add(pike);
        DisplayStack(Targets, AU);
    }
    
    /** 
     * constructs the tab pane for the selected hex
     * @author Joe Higley      
     */
    protected void DisplayStack(TabPane tp, List <ArmyUnit> AU) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ClearTitles(tp); //empty the tab pane
        int x = AU.size();
//        int x = Game.getInstance().getNum(); //this is just a random number of units, will be replaced later
        //create array of tabs
        Tab[] tabs = new Tab[x];
        for(int i=0; i < x; i++) {
            tabs[i] = new Tab();
            tabs[i].setText(AU.get(i).toString());
            FillStats(tabs[i], AU.get(i)); //add contents of the tab
        }
        //add tabs to tab pane
        tp.getTabs().addAll(tabs);
    }

    /** 
     * fills the contents of a tab pane
     * with unit stats.
     * @author Joe Higley      
     */
    public void FillStats(Tab tp, ArmyUnit AU) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
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
        column1.setPercentWidth(65);
        grid.getColumnConstraints().add(column1); 
        //set column2 format
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.RIGHT);
        column2.setPercentWidth(35);
        grid.getColumnConstraints().add(column2);
        
        //labels and function names
        List <String> stats = new ArrayList<>(Arrays.asList("Race: ", "Demoralized: ", "Strength: ", "Movement: "));
        List <String> funcs = new ArrayList<>(Arrays.asList("getRace", "isDemoralized", "getStrength", "getMovement"));
        //adds stats to GridPane (will be replaced with call to unit stats)
        for(int i=0; i < stats.size(); i++){
            Label l = new Label(stats.get(i));
            l.setFont(Font.font(null, FontWeight.BOLD, 12));
            //"get" method to be called
            Method method = AU.getClass().getMethod(funcs.get(i));
            Object b = method.invoke(AU); //get desired data
            TextField tf = new TextField(String.valueOf(b));
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
     * @author Joe Higley, Gabe Pearhill      
     */    
    @FXML protected void SubmitToChat(ActionEvent event) {
        if (!usernameEntered) {
            if (!"".equals(message_box.getText())) {
                username = message_box.getText();
                usernameEntered = true;
                chat_box.setText("Enter the server's IP address.");
                message_box.clear();
            }
        } else if (!ipEntered) {
            if (!"".equals(message_box.getText())) {
                ipAddress = message_box.getText();
                ipEntered = true;
                message_box.clear();
                chat_box.clear();
                connectedToServer = connectToServer();
            }
        } else if (!"".equals(message_box.getText())) {
            NetworkClient.sendChatMessage(message_box.getText());
            message_box.clear();
        }
    }
    
    /**
     * Undo the last move made
     * Not functional need to talk to whoever coded Unit.UnitPool.undoMove(String)
     * @param event 
     */
    @FXML protected void undoMove(ActionEvent event) {
        UnitPool.getInstance().undoMove(null);
    }
    
    /** 
     * Changes current scene to main menu and shows main menu     * 
     * @author Joe Higley      
     */
    @FXML protected void Quit(ActionEvent event) {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.setScene(Game.getInstance().getMainScene());
        stage.setFullScreen(true);
        stage.show();
    }
    
    /** 
     * SolarDisplay code goes here
     * 
     * @author Joe Higley      
     */   
    @FXML protected void ChangePhase(ActionEvent event) {
        
        /* This if-else is a test that Game's hudController reference actually
         * refers to the right instance. It's an ackward place,
         * but putting the code here guarantees that we
         * are checking against the proper instance. This code should have no
         * effect besides printing to System.out: 
         */
        if (Game.getInstance().hudController == this){
            System.out.println("Game's hudController reference is correct!");
        }
        else {
            System.out.println("Game's hudController reference is NOT correct!");
        }
        
        switch(phaseButton.getText()){
            case "End Movement Phase":
                phaseButton.setText("End Spell Phase");
                phase.setText("Spell");
                break;
        
            case "End Spell Phase":
                phaseButton.setText("End Combat Phase");
                phase.setText("Combat");
                break;
            
            case "End Combat Phase":
                phaseButton.setText("End Turn");
                phase.setText("End");
                break;
                
            case "End Turn":
                phaseButton.setText("End Movement Phase");
                phase.setText("Movement");
                int x = parseInt(turn.getText());
                x++;
                turn.setText(Integer.toString(x));
                
                SolarDisplay.SunCalc();
                Image Sun = new Image(SolarDisplay.GetSunImage());
                SunImage.setImage(Sun);
        
                RedState.setText(SolarDisplay.GetRedState());
                BlueState.setText(SolarDisplay.GetBlueState());
                break;
        }
        
    }
    

    @FXML protected void DisplayDiplomacy(ActionEvent event) {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.setScene(Game.getInstance().getDiploScene());
        stage.show();
    }

    /**
     * Connect to server
     *
     * @author Gabe Pearhill
     */
    public boolean connectToServer() {
        // 25565 is sworsorc default server port
        NetworkClient.setServerName(ipAddress);
        NetworkClient.setServerPort(25565);
        NetworkClient.setUsername(username);

        if (NetworkClient.connect()) {
            if (NetworkClient.startClient()) {
                //NetworkClient.runClient(true);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    /**
     * A simple function to post a message to the chat box from outside of the 
     * class. This function is used by the networking software in order to post
     * an incoming chat message.
     * 
     * @author Gabe Pearhill
     */
    public void postMessage(String message){
        chat_box.appendText(message + "\n");
    }

 
}
