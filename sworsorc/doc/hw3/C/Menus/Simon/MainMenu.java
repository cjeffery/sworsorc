/* Cameron Simon
 * CS 383
 * Sample main menu
 */

package mainmenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import javax.imageio.*;


public class MainMenu extends JFrame{
    JMenuBar mbar;
    JMenu menu;

    public MainMenu()
    {
        try{
            this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("MenuImage.jpg")))));
        }catch(IOException e){
            System.out.println("Image not found.");
        }
        
        setTitle("Swords and Sorcery Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        mbar=new JMenuBar(){
/*
            public void paintComponent(Graphics g)
            {
                g.drawImage(Toolkit.getDefaultToolkit().getImage(
                     "C:\\Users\\Mr. Cameron Simon\\Documents\\College"
                        + "\\Senior Year\\CS 383\\MenuImage.jpg"),0,0,this);
            }
*/            
        };
    
        
        menu=new JMenu("Menu");
        menu.setForeground(Color.black);
        
        //Create exit button and set up its functionality
        JMenuItem exitButton = new JMenuItem("Exit");
        exitButton.setMnemonic(KeyEvent.VK_Q);
        exitButton.setAccelerator(KeyStroke.getKeyStroke(
             KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        exitButton.setToolTipText("Exit application");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        menu.add(new JMenuItem("New Game"));
        menu.add(new JMenuItem("Load Game"));
        menu.add(new JMenuItem("Save Game"));
        menu.add(new JMenuItem("Options"));
        menu.add(new JMenuItem("About"));
        menu.add(exitButton);

        mbar.add(menu);
        setJMenuBar(mbar);
        setSize(600,600);
    }
    
    public static void main(String[] args) {
        new MainMenu();
    }
}
