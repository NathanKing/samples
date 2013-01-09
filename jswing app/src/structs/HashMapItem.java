/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structs;

import utils.DataUtils;
import java.util.ArrayList;

/**
 * Primary data structure for application
 * 
 * Data can be a MatrixRow, Chromosome, or Feature
 * 
 * @author nathan
 */
public class HashMapItem{
    
    protected int maxBins;
    protected int minBins;
    protected int min;
    protected int max;
    protected ArrayList<Double> avgs = new ArrayList();
    protected double avg;

    private Object data;
    private String name;
    
    /**
     * 
     * @param data the data
     * @param name the name of the data set
     */
    
    public HashMapItem(Object data, String name){
        this.data = data;
        this.name = name;
    }
    
    /**
     * Returns the data belonging to this object
     * @return the data
     */
    
    public Object getData() {
        return data;
    }
    
    /**
     * Sets the data
     * @param data New data
     */
    
    public void setData(Object data) {
        this.data = data;
    }
    
    /**
     * Returns the name of this object
     * @return the name
     */
    
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of this object
     * @param name the new name
     */
    
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Calculates important information regarding the data owned by this object
     */
    
    public void calculateValues(){
        maxBins = DataUtils.maxFeatureBins(this);
        avg = DataUtils.average(this);
        avgs = DataUtils.avgs(this);
        min = DataUtils.min(this);
        max = DataUtils.max(this);
    }
    
    /**
     * Returns the overall average
     * @return overall average
     */

    public double getAvg() {
        return avg;
    }
    
    /**
     * Returns the averages of each feature
     * @return averages
     */

    public ArrayList<Double> getAvgs() {
        return avgs;
    }
    
    /**
     * Returns the maximum value contained in any feature / chromosome
     * @return the maximum value
     */

    public int getMax() {
        return max;
    }
    
    /**
     * Returns the maximum number of bins
     * @return the maximum number of bins in a given HashMapItem
     */

    public int getMaxBins() {
        return maxBins;
    }

    /**
     * Returns the minimum value contained in any feature / chromosome
     * @return the minimum value
     */
    public int getMin() {
        return min;
    }
}
