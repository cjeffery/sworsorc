import java.awt.event.*;
import javax.swing.*;

public class Gui implements ActionListener {
    public void createGui() {
        window = new JFrame("Swords & Sorcery");
        JMenuBar menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);
        /* Game Menu */
        JMenu         gameMenu =  menuBar.add( new JMenu(    "Game"     )   );
        JMenuItem  newGameItem = gameMenu.add( new JMenuItem("New Game" )   );
        JMenuItem loadGameItem = gameMenu.add( new JMenuItem("Load Game")   );
        JMenuItem saveGameItem = gameMenu.add( new JMenuItem("Save Game")   );
        gameMenu.addSeparator();
        JMenuItem   optionItem = gameMenu.add( new JMenuItem("Preferences") );
        gameMenu.addSeparator();
        JMenuItem     exitItem = gameMenu.add( new JMenuItem("Exit")        );
        /* Help Menu */
        JMenu         helpMenu =  menuBar.add( new JMenu(    "Help" )       );
        JMenuItem    aboutItem = helpMenu.add( new JMenuItem("About")       );
        
        /* Keyboard shortcuts */
        saveGameItem.setMnemonic(KeyEvent.VK_S); //summon the mighty SLNPAXGH
        loadGameItem.setMnemonic(KeyEvent.VK_L);
         newGameItem.setMnemonic(KeyEvent.VK_N);
          optionItem.setMnemonic(KeyEvent.VK_P);
           aboutItem.setMnemonic(KeyEvent.VK_A);
            exitItem.setMnemonic(KeyEvent.VK_X); 
            gameMenu.setMnemonic(KeyEvent.VK_G);
            helpMenu.setMnemonic(KeyEvent.VK_H); //SLNPAXGH is now summoned. D:

        /* set-up event listeners */
         newGameItem.addActionListener(this);
        loadGameItem.addActionListener(this);
        saveGameItem.addActionListener(this);
          optionItem.addActionListener(this);
            exitItem.addActionListener(this);
           aboutItem.addActionListener(this);

        /* display an image in the content pane of the window */
        ImageIcon image = new ImageIcon("./swords-and-sorcery-5.jpg");
        JLabel imageLabel = new JLabel(image);
        window.getContentPane().add(imageLabel);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }
    @Override public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)e.getSource();
        String text = source.getText();
        /* switch would be unwieldy with more cases, but OK for example */
        switch(text) {
            case "New Game":
                JOptionPane.showMessageDialog(window, "New Game Stub");
                break;
            case "Load Game":
                JOptionPane.showMessageDialog(window, "Load Game Stub");
                break;
            case "Save Game":
                JOptionPane.showMessageDialog(window, "Save Game Stub");
                break;
            case "Preferences":
                JOptionPane.showMessageDialog(window, "Preferences Stub");
                break;
            case "About":
                JOptionPane.showMessageDialog(window, "About Stub");
                break;
            case "Exit":
                window.dispatchEvent(
                        new WindowEvent(window, WindowEvent.WINDOW_CLOSING)
                );
                break;
            default:
                assert false; 
                break;
        }
    }
    private JFrame window;
}
