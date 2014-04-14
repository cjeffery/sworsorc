/*
 * Cameron Simon
 * Unit Detail Display
 * 2/28/14
 */

package unitdetailsdisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import java.awt.Color;


public class UnitDetailsDisplay extends JFrame implements ActionListener{
    JMenuBar mbar;
    JMenu menu;
    JFrame frame;

    public UnitDetailsDisplay()
    {
        frame = new JFrame("Swords and Sorcery");
        //Background image (doesn't display with buttons on top)
        try{
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("MenuImage.jpg")))));
        }catch(IOException e){
            System.out.println("Image not found.");
        }
        setVisible(true);
        
        //Create grid of buttons
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(4,4,4,4));

        //Name buttons and set their size
        for(int i=1 ; i<=16 ; i++){
            JButton btn = new JButton(String.valueOf("Unit " + i));
            btn.setPreferredSize(new Dimension(40, 40));
            panel.add(btn);
            
            //Design unit details window
            btn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                    JOptionPane.showMessageDialog(frame.getComponent(0), 
                            "Unit Details: \n"
                                    + "    Province\n"
                                    + "    Terrain Type\n"
                                    + "    Special Features");
                    
                }
            });
            //Set buttons to clear
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            //btn.setBorderPainted(false);
        }     
  
        //Create menu with options
        mbar=new JMenuBar();
        menu=new JMenu("Menu");
        menu.setForeground(Color.black);
        
        //Create exit button and set up its functionality
        JMenuItem exitButton = new JMenuItem("Exit");
        exitButton.setMnemonic(KeyEvent.VK_Q);
        exitButton.setAccelerator(KeyStroke.getKeyStroke(
             KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        exitButton.setToolTipText("Exit application");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        //Add items to menu
        menu.add(new JMenuItem("New Game"));
        menu.add(new JMenuItem("Load Game"));
        menu.add(new JMenuItem("Save Game"));
        menu.add(new JMenuItem("Options"));
        menu.add(new JMenuItem("About"));
        menu.add(exitButton);

        mbar.add(menu);
        frame.setJMenuBar(mbar);
        frame.setContentPane(panel);
        frame.pack();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(600,600);        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
       new UnitDetailsDisplay();
    }

    
    public void paint(Graphics g)  
    {  
        super.paint(g);
  /*
        //draw square outline  
        g.drawRect(300,300,100,100);  
  
        //set color to RED  
        //So after this, if you draw anything, all of it's result will be RED  
        g.setColor(Color.RED);  
  
        //fill square with RED  
        g.fillRect(300,300,100,100); 
*/          
    }

    
//*******MAIN******************************************    
    public static void main(String[] args) {
        new UnitDetailsDisplay();
    }

}