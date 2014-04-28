
package mainswordsorcery;

/**
 *
 * @author Joe Higley
 */
import MoveCalculator.MovementCalculator;
import Units.*;
import java.awt.Color;
import static java.lang.Integer.parseInt;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.beans.value.*;
import javafx.beans.Observable;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import sscharts.Scenario;
import sshexmap.MapHex;
import sshexmap.MapView;
import systemServer.NetworkClient;
 
public class HUDController {
    @FXML private TabPane UnitsPane;
    @FXML private TabPane TargetsPane;
    @FXML private MenuBar menuBar;
    @FXML private TextField message_box;
    @FXML private TextArea chat_box;
    @FXML private ScrollPane map_view;
    @FXML private ScrollPane mini_map;
    @FXML private SwingNode hex_map;
    @FXML private Button undo_button;
    @FXML private ImageView undo_pic;
    
    @FXML private ImageView SunImage;
    @FXML private Button phaseButton;
    @FXML private Text turn;
    @FXML private Text phase;
    @FXML private Text RedState;
    @FXML private Text BlueState;
    
    //currently selected unit and target unit
    MoveableUnit selected_unit;
    MoveableUnit target_unit;
    //currently selected stack of units and target units
    List <MoveableUnit> selected_stack;
    List <MoveableUnit> target_stack;
    //holds valid hexes for selected_unit movement
    private ArrayList<MapHex> canMoveTo;
    private MapHex currentHex;
    

    MapView hmapContent;//MapView swing object set into hmap
    SwingNode hmap = new SwingNode();
    
    String username, ipAddress;
    boolean usernameEntered, ipEntered, connectedToServer;

    public HUDController() {
        selected_stack = new ArrayList <>();
        target_stack = new ArrayList<>();
        this.selected_unit = null;
        this.target_unit = null;
    }
    
    /** 
     * initialize() is used to connect GUI view elements with model elements. 
     * 
     * @author Jay Drage        
     */
    public void initialize(){
        //hdip.setContent(MapView.getDipView());
        phase.setText("Movement");
        //Display map in map_view
        hmapContent = MapView.getMapView();
        hmap.setContent(hmapContent);
        map_view.setContent(hmap);
        
        //adds mouse support to hmap
        setupEventHandlers();
        
        //setup network
        connectedToServer = usernameEntered = ipEntered = false;
        chat_box.setText("Enter your username!");
    }
    
    /**
     * Initialization of the Solar Display
     * Johnathan Flake
     */
    void initializeSunStuff() {
        SolarDisplay.SunCheck();
        Image Sun = new Image(SolarDisplay.GetSunImage());
        SunImage.setImage(Sun);

        RedState.setText(SolarDisplay.GetRedState());
        BlueState.setText(SolarDisplay.GetBlueState());
    }
    
    /**
     * big messy function to set up event handlers
     */
    void setupEventHandlers() {
        hmap.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent mouseEvent) {
                //finds out at which hex a mouse event occured
                String hexID = hmapContent.hexAt((int)mouseEvent.getX(), (int)mouseEvent.getY());
                currentHex = (MapHex)hmapContent.GetHexMap().GetHex(hexID);
                //allows deselecting of unit with left mouse button
                if( mouseEvent.isPrimaryButtonDown() && selected_unit != null){
                    DeselectUnit( hexID, currentHex );
                }           
                //select a unit if none previously selected
                else if( mouseEvent.isPrimaryButtonDown()){
                    SelectUnit( hexID, currentHex );
                }             
                //move unit with right mouse button in movement phase
                else if( mouseEvent.isSecondaryButtonDown() && selected_unit != null && phase.getText().equalsIgnoreCase("Movement")){
                    MoveUnit( hexID, currentHex );
                }
                //allows deselecting of target unit with right mouse button
                else if( mouseEvent.isSecondaryButtonDown() && target_unit != null){
                    DetargetUnit( hexID, currentHex );
                }                    
                 //choose target unit in combat or spell phase
                else if( mouseEvent.isSecondaryButtonDown() //right mouse button
                         && !phase.getText().equalsIgnoreCase("Movement") //check phase
                         && target_unit == null){ //check if target unit already chosen
                    TargetUnit( hexID, currentHex );
                }
                mouseEvent.consume();
            }
	});
        /**adds keyboard support to map_view
         * @author Jay Drage
         */
        map_view.addEventHandler(KeyEvent.KEY_PRESSED, 
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    KeyEventWrapper(keyEvent);
                };
        });
        /**adds keyboard support to UnitsPane
         * @author Jay Drage
         */
        UnitsPane.addEventHandler(KeyEvent.KEY_PRESSED, 
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    KeyEventWrapper(keyEvent);
                };
        });
        /**adds keyboard support to TargetsPane
         * @author Jay Drage
         */
        TargetsPane.addEventHandler(KeyEvent.KEY_PRESSED, 
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    KeyEventWrapper(keyEvent);
                };
        });
        /**adds keyboard support to undo_button
         * @author Jay Drage
         */
        undo_button.addEventHandler(KeyEvent.KEY_PRESSED, 
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    KeyEventWrapper(keyEvent);
                };
        });
        /**adds keyboard support to chat_box
         * @author Jay Drage
         */
        chat_box.addEventHandler(KeyEvent.KEY_PRESSED, 
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    KeyEventWrapper(keyEvent);
                };
        });
        /**adds keyboard support to phaseButton
         * @author Jay Drage
         */
        phaseButton.addEventHandler(KeyEvent.KEY_PRESSED, 
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    KeyEventWrapper(keyEvent);
                };
        });
        /**adds keyboard support to mini_map
         * @author Jay Drage
         */
        mini_map.addEventHandler(KeyEvent.KEY_PRESSED, 
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    KeyEventWrapper(keyEvent);
                };
        });
        /** adds mouse support to mini_map
         * @author Jay Drage
         */
        mini_map.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
		public void handle (MouseEvent mouseEvent) {
			System.out.println("X: " + mouseEvent.getX() + " Y: " + mouseEvent.getY());
		}
	});
        
        //set undo image on hover
        undo_button.addEventHandler(MouseEvent.MOUSE_ENTERED, 
            new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    Image img = new Image("file:resources/images/undo_hover.png");
                    undo_pic.setImage(img);
                }
        });  
        //return to original undo image on mouse off
        undo_button.addEventHandler(MouseEvent.MOUSE_EXITED, 
            new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    Image img = new Image("file:resources/images/undo.png");
                    undo_pic.setImage(img);
                }
        });
        //set undo image on mouse clicked
        undo_button.addEventHandler(MouseEvent.MOUSE_PRESSED, 
            new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    Image img = new Image("file:resources/images/undo_click.png");
                    undo_pic.setImage(img);
                }
        });
        //set undo image on mouse released
        undo_button.addEventHandler(MouseEvent.MOUSE_RELEASED, 
            new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    Image img = new Image("file:resources/images/undo_hover.png");
                    undo_pic.setImage(img);
                }
        });
        /**
         * Allows changing of selected_unit from selected_stack
         * with the UnitsPane
         * @author Jay Drage
         */
        UnitsPane.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Tab>(){
                @Override
                public void changed(ObservableValue<? extends Tab> ov, Tab oldTab, Tab newTab) {
                    if(selected_stack.isEmpty() == false){
                        selected_unit = selected_stack.get(UnitsPane.getTabs().indexOf(newTab));
                    }
                    //highlight valid hexes if in movement phase
                    canMoveTo = new ArrayList<>();
                    canMoveTo.clear();
                    if(phase.getText().equalsIgnoreCase("Movement")){
                        hmapContent.clearHighlights();
                        if(selected_unit != null){
                            MovementCalculator.getValidMoves(selected_unit, currentHex, selected_unit.getMovement(), canMoveTo );
                            hmapContent.highlight(canMoveTo, new Color(0,0,255, 70));
                        }
                    }
                }
            }
        );
        /**
         * Allows changing of target_unit from target_stack
         * with the TargetsPane
         * @author Jay Drage
         */
        TargetsPane.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Tab>(){
                @Override
                public void changed(ObservableValue<? extends Tab> ov, Tab oldTab, Tab newTab) {
                    target_unit = target_stack.get(TargetsPane.getTabs().indexOf(newTab));
                }
            }
        );
    }
    /**
     * Function that is called for keyEvents
     * simply reduces clutter in code
     * @param keyEvent 
     * @author Jay Drage
     */
    public void KeyEventWrapper(KeyEvent keyEvent){
        //attack
        if(keyEvent.getText().equalsIgnoreCase("a") 
           && !target_stack.isEmpty() && !selected_stack.isEmpty()
           && phase.getText().equalsIgnoreCase("Combat")){
            StartCombat();
        }
        //spells
        else if(keyEvent.getText().equalsIgnoreCase("s")
                && !target_stack.isEmpty() && !selected_stack.isEmpty() 
                && phase.getText().equalsIgnoreCase("Spell")){
            StartSpell();
        }
    }
    /** 
     * deselects a unit with left mouse button
     * sets selected_unit to null
     * clears selected_stack
     * 
     * @param hexID
     * @param hex
     * @author Jay Drage
     */
    public void DeselectUnit(String hexID, MapHex hex){
        hmapContent.clearHighlights();
        selected_unit = null;
        selected_stack.clear();
        //update target stack panel
        try {
            DisplayStack(UnitsPane, selected_stack);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ex) {
            Logger.getLogger(HUDController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    /** 
     * selects a unit
     * left mouse button sets unit to selected_unit
     * sets stack to selected_stack
     * 
     * @param hexID
     * @param hex
     * @author Jay Drage
     */
    public void SelectUnit(String hexID, MapHex hex){
        if(hex == null){
            return;
        }
        //check if there is a previously selected unit, return if hex is empty of units
        if(hex.getUnits() == null) {
            return;
        }
        selected_stack = hex.getUnits();
        //update target stack panel
        try {
            DisplayStack(UnitsPane, selected_stack);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ex) {
            Logger.getLogger(HUDController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    /** 
     * Moves selected unit with right mouse button
     * 
     * @param hexID
     * @param hex
     * @author Jay Drage
     */
    public void MoveUnit( String hexID, MapHex hex ){
        if( canMoveTo.contains(hex) ) {
            final UnitPool pool = UnitPool.getInstance();
            hmapContent.clearHighlights();
            //pool.addMove((ArmyUnit)selected_unit, hex.GetID());
            pool.addMove(selected_unit, hex.GetID());
            //pool.addUnit(0, (ArmyUnit)selected_unit, hex.GetID());
            hmapContent.repaint();
            selected_unit = null;
            selected_stack.clear();
            //update target stack panel
            try {
                DisplayStack(UnitsPane, selected_stack);
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ex) {
                Logger.getLogger(HUDController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Detargets a unit
     * sets target_unit to null
     * clears target_stack
     * 
     * @param hexID
     * @param hex
     * @author Jay Drage
     */
    public void DetargetUnit( String hexID, MapHex hex ){
        target_unit = null;
        target_stack.clear();
        //update target stack panel
        try {
            DisplayStack(TargetsPane, target_stack);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ex) {
            Logger.getLogger(HUDController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Targets a unit with right click
     * works only when not in movement phase
     * sets target_unit
     * sets target_stack
     * 
     * @param hexID
     * @param hex
     * @author Jay Drage
     */
    public void TargetUnit( String hexID, MapHex hex ){
        if(hex == null){
            return;
        }
        //check if there is a previously selected target unit, return if hex is empty of units
        if(hex.getUnits() == null) {
            return;
        }
        target_stack = hex.getUnits();
        target_unit = null;
        target_unit = target_stack.get(0);
        //update target stack panel
        try {
            DisplayStack(TargetsPane, target_stack);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ex) {
            Logger.getLogger(HUDController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //called when clicking a friendly hex
    @FXML protected void DisplayUnits(ActionEvent event) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List <MoveableUnit> AU = new ArrayList <>();
        AU.add(selected_unit);
        DisplayStack(UnitsPane, AU);
    }
    //called when clicking an enemy hex
    @FXML protected void DisplayTargets(ActionEvent event) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        List <MoveableUnit> AU = new ArrayList <>();
        AU.add(target_unit);
        DisplayStack(TargetsPane, AU);
    }
    /** 
     * constructs the tab pane for the selected hex
     * @author Joe Higley      
     */
    protected void DisplayStack(TabPane tp, List <MoveableUnit> AU) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
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
    public void FillStats(Tab tp, MoveableUnit AU) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
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
        
        if(AU.getUnitType() == UnitType.Character){
            //labels and function names
            List <String> stats = new ArrayList<>(Arrays.asList("Name: ", "Race", "MagicPL: ", "MagicPotential: ", "Movement: "));
            List <String> funcs = new ArrayList<>(Arrays.asList("getName", "getRace", "getMagicPL", "getMagicPotential", "getMovement"));
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
        }
        else{
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
     * @param event
     * @author Joe Higley, Gabe Pearhill      
     */    
    @FXML protected void SubmitToChat(ActionEvent event) {
        if (!usernameEntered) { // TODO: this should be handled in NetworkClient
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
        stage.setFullScreen(Game.getInstance().fullscreen);
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
                //clear unit move highlights if present
                if( hmapContent.highlightSet != null ){
                    hmapContent.clearHighlights();
                }
                phaseButton.setText("End Spell Phase");
                phase.setText("Spell");
                NetworkClient.sendPhaseChange("Spell");
                break;
        
            case "End Spell Phase":
                phaseButton.setText("End Combat Phase");
                phase.setText("Combat");
                NetworkClient.sendPhaseChange("Combat");
                break;
            
            case "End Combat Phase":
                phaseButton.setText("End Turn");
                phase.setText("End");
                NetworkClient.endTurn();
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
                NetworkClient.sendPhaseChange("Movement");
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
     * @return 
     * @author Gabe Pearhill
     */
    public boolean connectToServer() {
        // 25565 is sworsorc default server port
        NetworkClient.setServerName(ipAddress);
        NetworkClient.setServerPort(25565);
        NetworkClient.setUsername(username);

        return NetworkClient.initializeClient();
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

    /**
     * used to start combat
     * called from keyEvent handlers
     *
     * @author Jay Drage
     */
    public void StartCombat(){
        System.out.println("StartCombat()");
        //Place combat code here
    }
    /**
     * used to start spells
     * called from keyEvent handlers
     *
     * @author Jay Drage
     */
    public void StartSpell(){
        System.out.println("StartSpell()");
        //Place spell code here
    }
}
