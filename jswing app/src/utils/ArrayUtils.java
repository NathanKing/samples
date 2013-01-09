/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;

/**
 * Utility class containing useful array/ArrayList functions
 * 
 * @author nathan
 */
public class ArrayUtils {
    
    /**
     * Reverses the given static array
     * @param array the array to be reversed
     * @return the reversed array
     */
    
    public static Object[] reverse(Object[] array){
        
        Object[]result = new Object[array.length];
        
        for(int i = 0; i < array.length; i++){
            
            result[i] = array[array.length - 1 - i];
        }
        
        return result;
    }
    
    /**
     * Returns the average of a static array of integers
     * @param data the static integer array
     * @return the average
     */
    
    public static int average(int[]data){
        
        int sum = 0;
        int n = 0;
        for(int i = 0; i < data.length-1; i++) {
        
            n++;
            sum += data[i];
        }

        return sum/n;
    }
    
    /**
     * Returns the maximum value in a static array of integers
     * @param array the static integer array
     * @return the maximum value in the input
     */
    
    public static int max(int[] array){
        
        if(array == null){
            
            throw new IllegalArgumentException("Array cannot be null");
        }
        else if(array.length==0){
            throw new IllegalArgumentException("Array cannot be empty");
        }
        
        int max = array[0];
        
        for(int i = 1; i < array.length; i++){
            
            if(array[i] > max){
                max = array[i];
            }
        }
        return max;
    }
    
    /**
     * Returns the maximum value in a static array of floats
     * @param array the static float array
     * @return the maximum value in the input
     */
    
    public static float max(float[] array){
        
        if(array == null){
            
            throw new IllegalArgumentException("Array cannot be null");
        }
        else if(array.length==0){
            throw new IllegalArgumentException("Array cannot be empty");
        }
        
        float max = array[0];
        
        for(int i = 1; i < array.length; i++){
            
            if(Float.isNaN(array[i])){
                return Float.NaN;
            }
            if(array[i] > max){
                max = array[i];
            }
        }
        return max;
    }
    
    /**
     * Returns the maximum value in a static array of doubles
     * @param array the static double array
     * @return the maximum value in the input
     */
    
    public static double max(double[]array){
        
        if(array == null){
            
            throw new IllegalArgumentException("Array cannot be null");
        }
        else if(array.length==0){
            throw new IllegalArgumentException("Array cannot be empty");
        }
        
        double max = array[0];
        
        for(int i = 1; i < array.length; i++){
            
            if(Double.isNaN(array[i])){
                return Double.NaN;
            }
            if(array[i] > max){
                max = array[i];
            }
        }
        return max;
    }
    
    /**
     * Returns the minimum value in a static array of integers
     * @param array the static integer array
     * @return the minimum value in the input
     */
    
    public static int min(int[] array){
        
        if(array == null){
            
            throw new IllegalArgumentException("Array cannot be null");
        }
        else if(array.length==0){
            throw new IllegalArgumentException("Array cannot be empty");
        }
        
        int min = array[0];
        
        for(int i = 1; i < array.length; i++){
            
           
            if(array[i] < min){
                min = array[i];
            }
        }
        return min;
    }
    
    /**
     * Returns the minimum value in a static array of doubles
     * @param array the static doubles array
     * @return the minimum value in the input
     */
    
    public static double min(double[]array){
        
        if(array == null){
            
            throw new IllegalArgumentException("Array cannot be null");
        }
        else if(array.length==0){
            throw new IllegalArgumentException("Array cannot be empty");
        }
        
        double min = array[0];
        
        for(int i = 1; i < array.length; i++){
            
            if(Double.isNaN(array[i])){
                return Double.NaN;
            }
            if(array[i] < min){
                min = array[i];
            }
        }
        return min;
    }
    
    /**
     * Returns the minimum value in a static array of floats
     * @param array the static float array
     * @return the minimum value in the input
     */
    
    public static float min(float[] array){
        
        if(array == null){
            
            throw new IllegalArgumentException("Array cannot be null");
        }
        else if(array.length==0){
            throw new IllegalArgumentException("Array cannot be empty");
        }
        
        float min = array[0];
        
        for(int i = 1; i < array.length; i++){
            
            if(Float.isNaN(array[i])){
                return Float.NaN;
            }
            if(array[i] < min){
                min = array[i];
            }
        }
        return min;
    }
    
    /**
     * Returns the longest string in an ArrayList of strings
     * @param array the ArrayList of strings
     * @return the longest string in the input
     * @see String
     */
    
    public static String maxString(ArrayList<String> array){
        
        String max = "";
        for(String s:array){
            
            if(s.length() > max.length()){
                max = s;
            }
        }
        return max;
    }
}
