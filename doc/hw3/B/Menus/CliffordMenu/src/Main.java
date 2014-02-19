import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        /* GUI needs to be started on event dispatch thread */
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Gui().createGui();
            }
        });
    }    
}
