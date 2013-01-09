/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structs;

import java.util.ArrayList;

/**
 * Class to distinguish between whether an HashMapItem is a MatrixRow
 * @author nathan
 */
public class MatrixRow extends HashMapItem{

    public MatrixRow(ArrayList<Double> data, String title){
        super(data, title);
    }
    
    @Override
    public ArrayList<Double> getData(){
        return (ArrayList<Double>) super.getData();
    }
}