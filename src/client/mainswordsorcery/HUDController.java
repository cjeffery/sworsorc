
package mainswordsorcery;

/**
 *
 * @author Joe Higley
 */
import Character.*;
import MoveCalculator.MovementCalculator;
import Units.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.*;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.controlsfx.control.Notifications;
import sscharts.RandomEventTable;
import sscharts.Scenario;
import sshexmap.MapHex;
import sshexmap.MapView;
import systemServer.NetworkClient;

import static java.lang.Integer.parseInt;
import static mainswordsorcery.LaunchCombat.LaunchBotton;


public class HUDController {
    @FXML private TabPane UnitsPane;
    @FXML private TabPane TargetsPane;
    @FXML private MenuBar menuBar;
    @FXML private TextField message_box;
    @FXML public TextArea chat_box;
    @FXML private ScrollPane map_view;
    @FXML private ScrollPane mini_map;
    @FXML private SwingNode hex_map;
    @FXML private Button undo_button;
    @FXML private ImageView undo_pic;
    
    @FXML private ImageView SunImage;
    @FXML public Button phaseButton;
    @FXML private Text turn;
    @FXML private Text phase;
    @FXML private Text RedState;
    @FXML private Text BlueState;
    @FXML private Text currentPlayerText;
    
    //currently selected unit and target unit
    MoveableUnit selected_unit;
    public MoveableUnit target_unit;
    //currently selected stack of units and target units
    List <MoveableUnit> selected_stack;
    List <MoveableUnit> target_stack;
    //holds valid hexes for selected_unit movement
    private ArrayList<MapHex> canMoveTo;
    private HashMap<MapHex, Double> moves;
    private MapHex currentHex;
    //End of Game popup
    JFrame frame;
    

    public MapView hmapContent;//MapView swing object set into hmap
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
        phase.setText("Spell");
        currentPlayerText.setText(Integer.toString(
                                        Game.getInstance().currentTurnPlayer));
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
                String hexID = hmapContent.hexAt((int)mouseEvent.getX(), 
                                                 (int)mouseEvent.getY());
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
                else if( mouseEvent.isSecondaryButtonDown() 
                         && selected_unit != null 
                         && phase.getText().equalsIgnoreCase("Movement")){
                    MoveUnit( hexID, currentHex );
                }
                //allows deselecting of target unit with right mouse button
                else if( mouseEvent.isSecondaryButtonDown() 
                         && target_unit != null){
                    DetargetUnit( hexID, currentHex );
                }                    
                 //choose target unit in combat or spell phase
                else if( mouseEvent.isSecondaryButtonDown() //right mouse button
                         //check phase
                         && !phase.getText().equalsIgnoreCase("Movement") 
                         //check if target unit already chosen
                         && target_unit == null){ 
                    TargetUnit( hexID, currentHex );
                }
                //retreat if combat lost
                /*
                elseif(){
                }
                */
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
			System.out.println("X: " + mouseEvent.getX() + 
                                           " Y: " + mouseEvent.getY());
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
                    //canMoveTo = new ArrayList<>();
                    //canMoveTo.clear();
                    moves = new HashMap<>();
                    moves.clear();
                    if(phase.getText().equalsIgnoreCase("Movement")){
                        hmapContent.clearHighlights();
                        if(selected_unit != null){
                            moves = MovementCalculator.movementWrapper(selected_unit, currentHex);
                            canMoveTo = new ArrayList<MapHex>(moves.keySet());
                            //MovementCalculator.getValidMoves(selected_unit, currentHex, selected_unit.getMovement(), canMoveTo );
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
           && phase.getText().equalsIgnoreCase("Combat")
           && (selected_stack.get(0).getID().charAt(0)-48 // check if unit is 
                == Game.getInstance().PlayerID)      // current player's unit
           && (target_stack.get(0).getID().charAt(0)-48 // check if unit is not
                != Game.getInstance().PlayerID)        // current player's unit
           && Game.getInstance().currentTurnPlayer //check if it is player's
                == Game.getInstance().PlayerID)    //turn    
        {
            StartCombat(hmapContent);
        }
        //spells
        else if(keyEvent.getText().equalsIgnoreCase("s")
                && !target_stack.isEmpty() && !selected_stack.isEmpty() 
                && phase.getText().equalsIgnoreCase("Spell"))
        {
            StartSpell();
        }
        //This is only used for testing. no in game value.
        else if(keyEvent.getText().equalsIgnoreCase("x")){
            System.out.println(target_unit);
        }
        //keyboard shortcut to quickly allow client to become
        //current player
        else if(keyEvent.getText().equalsIgnoreCase("b")){
            MakeCurrentPlayer();
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
            DisplayStack(UnitsPane, selected_stack, true);
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
            DisplayStack(UnitsPane, selected_stack, true);
        } catch (InvocationTargetException | NoSuchMethodException 
                | IllegalAccessException ex) {
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
        if(selected_stack.get(0).getID().charAt(0)-48 // check if unit is 
                == Game.getInstance().PlayerID        // player's unit
           && Game.getInstance().currentTurnPlayer //check if it is player's
                == Game.getInstance().PlayerID)    //turn
        {
            if( moves.containsKey(hex) ) {
                final UnitPool pool = UnitPool.getInstance();
                hmapContent.clearHighlights();
                //pool.addMove((ArmyUnit)selected_unit, hex.GetID());
                selected_unit.setWorkingMovement( moves.get(hex) );
                pool.addMove(selected_unit, hex.GetID());
                //pool.addUnit(0, (ArmyUnit)selected_unit, hex.GetID());
                
                selected_unit = null;
                selected_stack.clear();
                //update target stack panel
                try {
                    DisplayStack(UnitsPane, selected_stack, true);
                } catch (InvocationTargetException | NoSuchMethodException 
                        | IllegalAccessException ex) {
                    Logger.getLogger(HUDController.class.getName()).log(Level.SEVERE, null, ex);
                }
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
            DisplayStack(TargetsPane, target_stack, false);
        } catch (InvocationTargetException | NoSuchMethodException 
                | IllegalAccessException ex) {
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
            DisplayStack(TargetsPane, target_stack, false);
        } catch (InvocationTargetException | NoSuchMethodException 
                | IllegalAccessException ex) {
            Logger.getLogger(HUDController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //called when clicking a friendly hex
    @FXML protected void DisplayUnits(ActionEvent event) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List <MoveableUnit> AU = new ArrayList <>();
        AU.add(selected_unit);
        DisplayStack(UnitsPane, AU, true);
    }
    //called when clicking an enemy hex
    @FXML protected void DisplayTargets(ActionEvent event) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        List <MoveableUnit> AU = new ArrayList <>();
        AU.add(target_unit);
        DisplayStack(TargetsPane, AU, false);
    }
    /** 
     * constructs the tab pane for the selected hex
     * @author Joe Higley      
     */
    protected void DisplayStack(TabPane tp, List <MoveableUnit> AU, Boolean isMyUnit) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ClearTitles(tp); //empty the tab pane
        int x = AU.size();
//        int x = Game.getInstance().getNum(); //this is just a random number of units, will be replaced later
        //create array of tabs
        Tab[] tabs = new Tab[x];
        for(int i=0; i < x; i++) {
            tabs[i] = new Tab();
            tabs[i].setText(AU.get(i).toString());
            FillStats(tabs[i], AU.get(i), isMyUnit); //add contents of the tab
        }
        //add tabs to tab pane
        tp.getTabs().addAll(tabs);
    }

    /** 
     * fills the contents of a tab pane
     * with unit stats.
     * @author Joe Higley    
     */
    public void FillStats(Tab tp, MoveableUnit AU, Boolean isMyUnit) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        //total number of details
        int details = 0;
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
            List <String> stats = new ArrayList<>(Arrays.asList("Name: ", "Race", "MagicPL: ", "Manna: ", "Movement: "));
            List <String> funcs = new ArrayList<>(Arrays.asList("getName", "getRace", "getMagicPL", "getCurrentManna", "getMovement"));
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
                details = i;
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
                details = i;
            }
        }
        
        if(isMyUnit){
            Button b = new Button("Cast Spell");
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    StartSpell();
                }
            });
        
            grid.add(b, 0, details+1, 2, 1);
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
     * @param event
     */    
    @FXML protected void SubmitToChat(ActionEvent event) {
        if ( !"".equals( message_box.getText() ) ) {
            NetworkClient.userInput( message_box.getText() );
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
     * Same as Quit(ActionEvent event) but does not require
     * a JavaFX ActionEvent.
     * @param event
     * @author Jay Drage
     */
    private void QuitNoEvent() {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.setScene(Game.getInstance().getMainScene());
        stage.setFullScreen(Game.getInstance().fullscreen);
        stage.show();
    }

    /**
     *
     * @param p
     *
     * @author Christopher Goes
     */
    @FXML public void setPhaseButtonText( String p ) {
        phaseButton.setText( p );
    }

    /**
     *
     * @param p
     *
     * @author Christopher Goes
     */
    @FXML public void setPhaseText( String p ) {
        phase.setText( p );
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
        // only allow current player to make phase changes
        if(Game.getInstance().currentTurnPlayer == Game.getInstance().PlayerID)
        {
            switch(phaseButton.getText()){
                case "End Spell Phase":
                    phaseButton.setText("End Movement Phase");
                    phase.setText("Movement");
                    NetworkClient.sendPhaseChange( "Movement", "End Movement Phase" );
                    break;

                case "End Movement Phase":
                    UnitPool.getInstance().endMovementPhase();
                    HexStack stack = new HexStack();
                    if(UnitPool.getInstance().getOverStack() != null 
                       && UnitPool.getInstance().getOverStack().size() > 0)
                       stack.removeOverStack(UnitPool.getInstance().getOverStack());
                    //clear unit move highlights if present
                    if( hmapContent.highlightSet != null ){
                        hmapContent.clearHighlights();
                    }
                    phaseButton.setText("End Combat Phase");
                    phase.setText("Combat");
                    NetworkClient.sendPhaseChange( "Combat", "End Combat Phase" );
                    break;

                case "End Combat Phase":
                    phaseButton.setText("End Turn");
                    phase.setText("End");
                    NetworkClient.endTurn();
                    break;

                case "End Turn":
                    phaseButton.setText("End Spell Phase");
                    phase.setText( "Spell" );
                    NetworkClient.sendPhaseChange( "Spell", "End spell Phase" );
                    // end game turn, all players finished
                    if(Game.getInstance().numPlayersGoneThisTurn 
                            == Scenario.getInstance().getNumberOfPlayers())
                    {
                        //set back to first player
                        Game.getInstance().numPlayersGoneThisTurn = 1;
                        Game.getInstance().currentTurnPlayer = 1;
                        Game.getInstance().currentGameTurn += 1;
                        currentPlayerText.setText("1");
                        //check if game is over
                        //if current turn is greater than scenario game length
                        if(Game.getInstance().currentGameTurn 
                                > Scenario.getInstance().getGameLength() ){
                            chat_box.appendText("Game is over\n\n");
                            EndOfGameGUI();
                        }
                        else{ //continue to next game turn
                            int x = parseInt(turn.getText());
                            x++;
                            turn.setText(Integer.toString(x));

                            RandomEventTable.getInstance().DisplayEvent();
                            SolarDisplay.SunCalc();
                            Image Sun = new Image(SolarDisplay.GetSunImage());
                            SunImage.setImage(Sun);
                            RedState.setText(SolarDisplay.GetRedState());
                            BlueState.setText(SolarDisplay.GetBlueState());
                        }
                        
                    }
                    // same game turn, next player turn
                    else
                    {
                        //move to next player
                        Game.getInstance().numPlayersGoneThisTurn += 1;
                        Game.getInstance().currentTurnPlayer += 1;
                        //update display
                        currentPlayerText.setText(
                                Integer.toString(
                                        Game.getInstance().currentTurnPlayer));
                    }

                    break;
            }
        }
        else
        {
            chat_box.appendText("You are not current player. " +
                                "To change players go to Scenario menu\n");
        }
        
    }
    
    @FXML protected void DisplayDiplomacy(ActionEvent event) {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.setScene(Game.getInstance().getDiploScene());
        stage.show();
    }
    
    /**
     * A simple function to post a message to the chat box from outside of the 
     * class. This function is used by the networking software in order to post
     * an incoming chat message.
     * 
     * @author Gabe Pearhill
     * @param message 
     */
    public void postMessage(String message){
        chat_box.appendText(message + "\n");
    }

    /**
     * used to start combat
     * called from keyEvent handlers
     *
     * @param hmapContent
     * @author Jay Drage & Shaung
     */
    public void StartCombat(MapView hmapContent){  
        
        System.out.println("StartCombat()");
        if (!CheckNext()) {
            Notifications.create()
                        .title("Not a Valid Command")
                        .text("Error Target: Out of range.")
                        .showError();
        }
            
        else 
        LaunchBotton(selected_stack, target_stack, hmapContent);

    }
    
    /**
     * @author Shaung
     * 
     * 
     */
    
    public boolean CheckNext() {
    boolean next = false;
        MapHex slected_Terrain = new MapHex();
        MapHex target_Terrain = new MapHex();
        
        MapView temp = MapView.getMapView();
        target_Terrain = (MapHex)temp.GetHexMap().GetHex(target_stack.get(0).getLocation());
        slected_Terrain = (MapHex)temp.GetHexMap().GetHex(selected_stack.get(0).getLocation());
        
        for (int i = 0; i < 6; i++) {
            if (target_Terrain.getNeighbor(i).GetID() == slected_Terrain.GetID()) {
                next = true;
            }
        }
        
    return next;
    }
    
    
    
    /**
     * used to start spells
     * called from keyEvent handlers
     *
     * @author Jay Drage and Tao
     */
    public void StartSpell(){
        System.out.println("StartSpell()");
        if(selected_unit != null){
            if(selected_unit.getUnitType() == UnitType.Character){
               Characters tempCaster = (Characters) selected_unit;
               tempCaster.CastSpell();
               tempCaster = null;
            }
        }
    }
    /**
     * changes Game.PlayerID to current player
     * used for testing with only one client
     * to disable remove MenuItem from hud.fxml
     * 
     * @author Jay Drage
     */
    public void MakeCurrentPlayer(){
        Game.getInstance().PlayerID = Game.getInstance().currentTurnPlayer;
    }
    /**
     * This displays the end of game popup.
     * It allows game to quit or continue playing
     * @author Jay Drage
     */
    public void EndOfGameGUI(){
        int frameWidth = 450, frameHeigth = 600;
        frame = new JFrame("Game Over");
        frame.setSize(frameWidth, frameHeigth);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e )
            {  
            }
        });
        StringBuilder sb = new StringBuilder(128);
        sb.append("<html>");
        sb.append("<p style=\"font-size:20px;text-align: center;\">");
        sb.append("You Lose");
        sb.append("<br>");
        sb.append("The world was sucked into a ");
        sb.append("rift portal and trampled ");
        sb.append("by ethereal cows");
        sb.append("</p>");
        sb.append("</html>");
        JLabel notice = new JLabel(sb.toString(), 0);
        JButton Quit_button = new JButton("Quit Game");
        Quit_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                frame.dispose();
                //TODO change to allow going to main menu
                System.exit(0);
            }
        });
        
        JButton KeepPlaying_button = new JButton("Keep Playing");
        KeepPlaying_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                frame.dispose();
            }
        });
        
        JPanel selection = new JPanel();
        selection.setLayout(new FlowLayout());
        selection.add(Quit_button);
        selection.add(KeepPlaying_button);
        
        ImageIcon EndGameImage = new ImageIcon("resources/images/EtherealCow.jpg");
        java.awt.Image img = EndGameImage.getImage();
        java.awt.Image ScaledImage = img.getScaledInstance(frameWidth, frameHeigth, 1);
        EndGameImage.setImage(ScaledImage);
        JPanel image_panel = new JPanel();
        //image_panel.setLayout(new BorderLayout());
        JLabel image_lable = new JLabel("",EndGameImage, JLabel.CENTER);
        
        image_panel.add(image_lable);
        frame.add(image_panel, BorderLayout.CENTER);
        frame.add(selection, BorderLayout.SOUTH);
        frame.add(notice, BorderLayout.NORTH);
        frame.setVisible(true);
    }
}
