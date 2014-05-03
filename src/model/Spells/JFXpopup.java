/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */

package Spells;

import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JComponent;
import mainswordsorcery.Game;

/**
 *
 * @author David
 */
public class JFXpopup {
   
    public static Stage getJFXPopup(JComponent someComponet){
        Stage popup = new Stage();
        SwingNode swingGui = new SwingNode();

        swingGui.setContent(someComponet);
        
        ScrollPane root = new ScrollPane();
        
        Scene popScene = new Scene(root,450,250);
        VBox vb = new VBox();
        vb.getChildren().add(swingGui);
        
        root.setContent(vb);
        
        popup.centerOnScreen();
        popup.setScene(popScene);
        popup.initOwner(Game.getInstance().getStage());
        //popup.setFullScreen(true);
        popup.show();
        return popup;
    }
    
    
    
    
}
