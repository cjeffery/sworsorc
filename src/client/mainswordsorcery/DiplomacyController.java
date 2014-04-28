/*
 * All source code is the work of Clinton Jeffery's Spring 2014 Software Engineering 
 * class at the University of Idaho consisting of the following members:
 * Brown, Clifford, Drage, Drew, Flake, Fuhrman, Goes, Goetsche, Higley,
 * Jaszkowiak, Klingenberg, Pearhill, Sheppard, Simon, Wang, Westrope, Zhang
 */
package mainswordsorcery;
/**
 *
 * @author Sean
 */
import Units.*;
import java.io.IOException;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.stage.Stage;
import sshexmap.MapView;

public class DiplomacyController {
    @FXML private ScrollPane dip_view;
    SwingNode hdip = new SwingNode();
    public void initialize(){
        hdip.setContent(MapView.getDipView());
        dip_view.setContent(hdip);
    }
    @FXML protected void Exit(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        stage.setScene(Game.getInstance().getHudScene());
        stage.setFullScreen(Game.getInstance().fullscreen);
        stage.show();
    }
}
