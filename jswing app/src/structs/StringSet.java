/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structs;

import java.util.ArrayList;

/**
 * Utility class for an object that has two ArrayLists of strings
 * @author nathan
 */
public class StringSet {
    
    public ArrayList<String>modelSet;
    public ArrayList<String>viewSet;
    
    /**
     * Creates a new StringSet instance
     */
    
    public StringSet(){
        modelSet = new ArrayList();
        viewSet = new ArrayList();
    }    
}
