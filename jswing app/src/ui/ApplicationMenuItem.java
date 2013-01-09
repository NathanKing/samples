/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;

/**
 * Custom JMenuItem class with a pointer to a JInternalFrame object
 * 
 * @author nathan
 */
public class ApplicationMenuItem extends JMenuItem{
    
    public JInternalFrame ptr;
    
    /**
     * Creates a new ApplicationMenuItem instance
     */
    
    public ApplicationMenuItem(){
        super();
    }
    
}
