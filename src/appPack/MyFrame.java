/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appPack;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Demet Koçhan
 */
public class MyFrame extends JFrame {
    
    public MyFrame()  throws HeadlessException {
        getContentPane().setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon("D:\\Belgeler\\NetBeansProjects\\project\\src\\icon"));
        getContentPane().add(background);
        background.setLayout(new FlowLayout());
        pack();

        
      
        
    }
    
}
