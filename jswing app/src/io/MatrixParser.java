/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import structs.HashMapItem;
import structs.MatrixRow;
import java.io.*;
import java.util.ArrayList;
import javax.swing.ProgressMonitorInputStream;


/**
 * Instance class for parsing matrix data (string, float, float, etc)
 * @author nathan
 */
public class MatrixParser {
    
    private ArrayList<HashMapItem> data;
    
    /**
     * Returns an ArrayList containing the data parsed from a file
     * containing 2D matrix data.
     * @return the most recently parsed data
     */
    
    public ArrayList<HashMapItem> getData() {
        return data;
    }
    
    /**
     * Creates a new MatrixParser instance object
     */
    
    public MatrixParser(){}
    
    /**
     * Parses the file indicated by the fileName parameter
     * and returns a structure containing the data.
     * @param fileName the name of the file to be parsed
     * @return the parsed data
     */
    
    public ArrayList<HashMapItem> parse(String fileName) throws Exception {
        data = new ArrayList();
        ProgressMonitorInputStream pm = new ProgressMonitorInputStream(null, "reading", new FileInputStream(fileName));
        InputStream is = new BufferedInputStream(pm);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine() )!= null) {

            String[] cols = line.split("\\t");
            ArrayList<Double> row = new ArrayList();
            for (int i = 1; i < cols.length; i++) {
                row.add(new Double(cols[i]));
            }
            HashMapItem dataitem = new MatrixRow(row, cols[0]);
            data.add(dataitem);
        }
        return data;
    }
}
