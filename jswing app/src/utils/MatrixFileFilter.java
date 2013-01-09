/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;

/**
 * Class for implementing a matrix data file filter
 * @author nathan
 */

public class MatrixFileFilter extends javax.swing.filechooser.FileFilter{
    @Override
    public boolean accept(File f) {
        String name = f.getName();
        if(!name.endsWith(".gff3")){
            return true;
        }
        else if(!name.endsWith(".fasta")){
            return true;
        }
        else if(f.isDirectory()){
            return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "*.txt";
    }
}