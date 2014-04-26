package Util;

import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.*;
import mainswordsorcery.Game;

/**
 * This class comes from stackoverflow! 
 * And thus has a different license from the rest of the project!
 * Fun!
 * http://stackoverflow.com/a/12718117/1938232
 */
public class FXOptionPane {

    public enum Response { NO, YES, CANCEL };

    private static Response buttonSelected = Response.CANCEL;

    private static ImageView icon = new ImageView();

    static class Dialog extends Stage {
        public Dialog(String title, Stage owner, Scene scene,
                      String iconFile)
        {
            setTitle( title );
            initStyle( StageStyle.UTILITY );
            initModality( Modality.APPLICATION_MODAL );
            initOwner( owner );
            setResizable( false );
            setScene( scene );
            //TODO: make images work
            //icon.setImage( new Image(iconFile));
        }
        public void showDialog() {
            sizeToScene();
            centerOnScreen();
            showAndWait();
        }
    }

    static class Message extends Text {
        public Message( String msg ) {
            super( msg );
            setWrappingWidth( 250 );
        }
    }

    public static Response showConfirmDialog(Stage owner, String message,
                                             String title )
    {
        if(owner == null) {
            owner = Game.getInstance().getStage();
        }
        VBox vb = new VBox();
        Scene scene = new Scene( vb );
        String path = "resources/images/ui/dialog_confirm.png";
        final Dialog dial = new Dialog( title, owner, scene, path);
        vb.setPadding( new Insets(10) );
        vb.setSpacing( 10 );
        Button yesButton = new Button( "Yes" );
        yesButton.setOnAction( new EventHandler<ActionEvent>() {
            @Override public void handle( ActionEvent e ) {
                dial.close();
                buttonSelected = Response.YES;
            }
        } );
        Button noButton = new Button( "No" );
        noButton.setOnAction( new EventHandler<ActionEvent>() {
            @Override public void handle( ActionEvent e ) {
                dial.close();
                buttonSelected = Response.NO;
            }
        } );
        BorderPane bp = new BorderPane();
        HBox buttons = new HBox();
        buttons.setAlignment( Pos.CENTER );
        buttons.setSpacing( 10 );
        buttons.getChildren().addAll( yesButton, noButton );
        bp.setCenter( buttons );
        HBox msg = new HBox();
        msg.setSpacing( 5 );
        msg.getChildren().addAll( icon, new Message( message ) );
        vb.getChildren().addAll( msg, bp );
        dial.showDialog();
        return buttonSelected;
    }

    public static void showMessageDialog(Stage owner, String message,
                                         String title )
    {
        if(owner == null) {
            owner = Game.getInstance().getStage();
        }
        showMessageDialog( owner, new Message( message ), title );
    }
    public static void showMessageDialog(Stage owner, Node message,
                                         String title)
    {
        if(owner == null) {
            owner = Game.getInstance().getStage();
        }
        VBox vb = new VBox();
        Scene scene = new Scene( vb );
        final Dialog dial = new Dialog( title, owner, scene, "res/Info.png" );
        vb.setPadding( new Insets(10) );
        vb.setSpacing( 10 );
        Button okButton = new Button( "OK" );
        okButton.setAlignment( Pos.CENTER );
        okButton.setOnAction( new EventHandler<ActionEvent>() {
            @Override public void handle( ActionEvent e ) {
                dial.close();
            }
        } );
        BorderPane bp = new BorderPane();
        bp.setCenter( okButton );
        HBox msg = new HBox();
        msg.setSpacing( 5 );
        msg.getChildren().addAll( icon, message );
        vb.getChildren().addAll( msg, bp );
        dial.showDialog();
    }
}