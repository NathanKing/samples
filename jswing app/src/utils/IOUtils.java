/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import javax.swing.JFileChooser;

/**
 * Utility class containing useful IO functions
 * @author nathan
 */
public class IOUtils {
    
    /**
     * Returns the number of lines in a file
     * @param filename  the name of the file to read
     * @return the number of lines in the file
     * @see Long
     * @throws Exception 
     */
    
    public static long countLines(String filename) throws Exception{
        
        LineNumberReader lnr = new LineNumberReader(new FileReader(filename));
        lnr.skip(Long.MAX_VALUE);
        return lnr.getLineNumber();
    }
    
    /**
     * Presents a dialog box that allows the user to select
     * a matrix data file.
     * @return the file selected
     * @see File
     */
    
    public static File matrixFileChooser(){
        int returnVal;
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new MatrixFileFilter());
        returnVal = fileChooser.showOpenDialog(null);
        
        File fastaFile = null;

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            try {
                return fileChooser.getSelectedFile();
            } catch (Exception ex) {
                System.out.println("error accessing file" +fastaFile.getAbsolutePath());
            }
        } else {
            System.out.println("File Access cancelled by user");
        }     
        return null;
    }
    
    /**
     * Presents a dialog box that allows the user to select
     * a file containing sequence data (fasta suffix).
     * @return the file selected
     * @see File
     */
    
    public static File fastaFileChooser(){
        int returnVal;
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FastaFileFilter());
        returnVal = fileChooser.showOpenDialog(null);
        
        File fastaFile = null;

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            try {
                return fileChooser.getSelectedFile();
            } catch (Exception ex) {
                System.out.println("error accessing file" +fastaFile.getAbsolutePath());
            }
        } else {
            System.out.println("File Access cancelled by user");
        }     
        return null;
    }
    
    /**
     * Presents a dialog box that allows the user to select
     * a GFF3FileFilter file.
     * @return the file selected
     * @see File
     */
    
    public static File gff3FileChooser(){
        
        int returnVal;
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new GFF3FileFilter());
        returnVal = fileChooser.showOpenDialog(null);
        
        File gff3File = null;

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            try {
                return fileChooser.getSelectedFile();
                
               
            } catch (Exception ex) {
                System.out.println("error accessing file" + gff3File.getAbsolutePath());
            }
        } else {
            System.out.println("File Access cancelled by user");
        }  
        
        return null;
    }
}
