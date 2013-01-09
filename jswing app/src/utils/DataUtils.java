/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import structs.Chromosome;
import structs.Feature;
import structs.HashMapItem;
import structs.MatrixRow;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class containing useful data structure functions
 * @author nathan
 */
public class DataUtils {
    
    /**
     * Creates and returns a deep copy of the input
     * @param data an ArrayList of HashMaps
     * @return a deep copy of the input
     * @see ArrayList
     * @see HashMapItem
     */
    
    public static ArrayList<HashMapItem> clone(ArrayList<HashMapItem> data){
        ArrayList<HashMapItem>clone = new ArrayList();
        
        ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> featureSetData;
        ConcurrentHashMap<Integer, Integer> values;
        HashMapItem dataItem;
        Iterator <Map.Entry<String, ConcurrentHashMap<Integer, Integer>>> iter;
        Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry;
        
        
        for(HashMapItem di:data){

            featureSetData = new ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>();
            if(di instanceof Chromosome){
                iter = ((Chromosome)di).getData().entrySet().iterator();
            }
            else{
                iter = ((Feature)di).getData().entrySet().iterator();
            }

            while(iter.hasNext()){

                entry = iter.next(); //get next feature set
                values = entry.getValue();              //get feature values
                featureSetData.put(entry.getKey(), new ConcurrentHashMap(values));

            }
            dataItem = new Chromosome(featureSetData, di.getName());
            clone.add(dataItem);
        }
        
        return clone;
    }
    
    /**
     * Applies a sliding window algorithm to the given input
     * 
     * Run time will increase considerably as windowSize increases.
     * 
     * @param data an ArrayList of HashMapItems
     * @param windowSize the smoothing factor
     */
    
    public static void slidingWindow(ArrayList<HashMapItem> data, int windowSize){
        
        
        ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> hash;
        ConcurrentHashMap<Integer, Integer> values;
        ConcurrentHashMap<Integer, Integer> newValues;
        
        Iterator <Map.Entry<String, ConcurrentHashMap<Integer, Integer>>> iter;
        Iterator <Map.Entry<Integer, Integer>>it;
        
        Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry;
        Map.Entry<Integer, Integer> val;

        float avg;
        float sum;
        int max;
        Chromosome chr;
        
        for(HashMapItem item:data){
            
            chr = (Chromosome)item;
            max = DataUtils.maxFeatureBins(chr);
            hash = (ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>)chr.getData();
            iter = hash.entrySet().iterator();
           
            while (iter.hasNext()) {
                
                entry = iter.next(); //get next feature set
                values = entry.getValue();             //get feature values
                it = values.entrySet().iterator();
                newValues = new ConcurrentHashMap();
                
                while(it.hasNext()){
                    val = it.next();
                    
                    for(int i = 0; i < max; i++){
                        sum = 0;
                        for(int j = i; j < i+windowSize; j++){
                            if(j < max){
                                if(values.containsKey(i)){
                                    sum += values.get(new Integer(i));
                                }
                            }
                        }
                        avg = sum / windowSize;
                        newValues.put(i, (int)avg);
                    }
                }
                entry.setValue(newValues);
            }
        }
    }
    
    /**
     * Sorts input data by comparing strings of the form: String, number (eg: Str1)
     * 
     * @param data the data to be sorted
     */
    
    public static void sortByString(ArrayList<HashMapItem> data){
        
        int i, j;
        int cmp;
        String numbers[];
        
        HashMapItem b;
        
        for(i = 0; i < data.size(); i++){
            
            numbers = data.get(i).getName().split("\\D+");
            j = i;
            
            try{
            
                cmp = Integer.valueOf(numbers[1]);
                b = data.get(i);
            
                while( j > 0 && (Integer.valueOf(data.get(j-1).getName().split("\\D+")[1]) > cmp)){
                    data.set(j, data.get(j-1));
                    j--;
                }
                data.set(j, b);
            }
            catch(Exception e){}
        }
    }
    
    /**
     * Returns the minimum value in an ArrayList of HashMapItems
     * 
     * @param data
     * @return the minimum value
     */
    
    public static double min(ArrayList<HashMapItem> data) {
        double min = 1.7976931348623157E+308D;
        ArrayList<Double> tmp;
        int temp;
        
        if(data!=null){
        
            for(int i = 0; i < data.size(); i++) {

                if(data.get(0) instanceof MatrixRow){
                    tmp = (ArrayList<Double>)data.get(i).getData();
                    for(int j = 0; j < tmp.size(); j++) {
                        min = tmp.get(j).doubleValue() >= min ? min : tmp.get(j).doubleValue();
                    }
                    break;
                }
                else{
                    temp = min(data.get(i));
                    if(temp < min){
                        min = temp;
                    }
                }
            }
        }
        return min;
    }
    
     /**
     * Returns the maximum value in an ArrayList of HashMapItems
     * 
     * @param data
     * @return the maximum value
     */
    
    public static double max(ArrayList<HashMapItem> data) {
        double max = 0D;
        ArrayList<Double> tmp;
        double temp;
        if(data!=null){
        
            for(int i = 0; i < data.size(); i++) {

                if(data.get(0) instanceof MatrixRow){
                    tmp = (ArrayList<Double>)data.get(i).getData();

                    for(int j = 0; j < tmp.size(); j++) {
                        max = tmp.get(j).doubleValue() <= max ? max : tmp.get(j).doubleValue();
                    }
                }
                else{

                    temp = max(data.get(i));
                    if(temp > max){
                        max = temp;
                    }
                }
            }
        }
        return max;
    }

    /**
     * Returns the minimum value in a HashMapItem
     * @param data a HashMapItem variable
     * @return the minimum value
     * @see HashMapItem
     */
    
    public static int min(HashMapItem data){
        
        Iterator <Map.Entry<String, ConcurrentHashMap<Integer, Integer>>> iter = ((ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>)data.getData()).entrySet().iterator();
        Iterator <Map.Entry<Integer, Integer>> it;
        int min = 0;
          
        while(iter.hasNext()){

            Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry = iter.next(); //get next feature set
            ConcurrentHashMap<Integer, Integer> values = entry.getValue();              //get feature values
            Set<Map.Entry<Integer, Integer>> entrySet = values.entrySet();
            it = entrySet.iterator();
            
            while(it.hasNext()){
                Map.Entry<Integer, Integer> val = it.next();
                int value = val.getValue();

                if(min > value){
                    min = value;
                }
            }
        }
        return min;
    }
    /**
     * Returns the maximum value in a HashMapItem
     * @param data a HashMapItem variable
     * @return the maximum value
     * @see HashMapItem
     */
    public static int max(HashMapItem data){
        
        Iterator <Map.Entry<String, ConcurrentHashMap<Integer, Integer>>> iter = ((ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>)data.getData()).entrySet().iterator();
        Iterator <Map.Entry<Integer, Integer>> it;
        int max = 0;
          
        while(iter.hasNext()){

            Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry = iter.next(); //get next feature set
            ConcurrentHashMap<Integer, Integer> values = entry.getValue();              //get feature values
            Set<Entry<Integer, Integer>> entrySet = values.entrySet();
            it = entrySet.iterator();
            
            while(it.hasNext()){
                Map.Entry<Integer, Integer> val = it.next();
                int value = val.getValue();

                if(max < value){
                    max = value;
                }
            }
        }
        return max;
    }
    
    /**
     * Returns the average calculated from a HashMap of (Key, Value) pairs
     * @param values a HashMap of K,V pairs
     * @return the sum of values divided by n
     */
    
    public static double avg(ConcurrentHashMap<Integer, Integer> values){
        double sum = 0.0;
        int n = 0;
        Iterator <Map.Entry<Integer, Integer>> it = values.entrySet().iterator();
        Map.Entry<Integer, Integer> entry;
        
        while(it.hasNext()){
            entry = it.next();
            n++;
            sum += entry.getValue();
        }
        return sum/n;
    }
    
    /**
     * Returns the averages calculated from an HashMapItem containing HashMaps of (Key, Value) pairs
     * @param dataItem a HashMapItem variable
     * @return an ArrayList containing the calculated averages
     */
    
    public static ArrayList<Double> avgs(HashMapItem dataItem){
        ArrayList<Double> avgs = new ArrayList();
        ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> hash = (ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>)dataItem.getData();
        Iterator <Map.Entry<String, ConcurrentHashMap<Integer, Integer>>> iter = hash.entrySet().iterator();
        Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry;
        while(iter.hasNext()){
            entry = iter.next();
            avgs.add(avg(entry.getValue()));
        }
        return avgs;
    }
    
    /**
     * Returns the average of all features in a HashMapItem
     * @param dataItem
     * @return a double containing the calculated average
     */
    
    public static double average(HashMapItem dataItem){
        
        double sum = 0.0;
        int n = 0;
        
        ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> hash = (ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>)dataItem.getData();
        Iterator <Map.Entry<String, ConcurrentHashMap<Integer, Integer>>> iter = hash.entrySet().iterator();
        while(iter.hasNext()){
            
            Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry = iter.next();
            ConcurrentHashMap<Integer, Integer> values = entry.getValue();
            Iterator <Map.Entry<Integer, Integer>> it = values.entrySet().iterator();
            
            Map.Entry<Integer, Integer> val;

            while(it.hasNext()){
                val = it.next();
                n++;
                sum += val.getValue();
            }
        }
        
        return sum/n;
    }

    public static double average(ArrayList<HashMapItem> data) {
        double sum = 0;
        int n = 0;
        ArrayList<Double> tmp;
        
        for(int i = 0; i < data.size(); i++) {
            
            if(data.get(0) instanceof MatrixRow){
                
                tmp = (ArrayList<Double>)data.get(i).getData();
                
                for(int j = 0; j < tmp.size(); j++) {
                    n++;
                    sum += tmp.get(j).doubleValue();
                }
            }
        }
        return sum/n;
    }
    
    /**
     * Removes the outliers from a set of data
     * 
     * Criteria for outlier: 3 * the average of the data
     * @param data set of input data
     */
    
    public static void removeOutliers(ArrayList<HashMapItem> data) {
        double cutoff = average(data) * 3;
        ArrayList<Double> tmp;
        HashMapItem tmp2;
        
        if(data.get(0) instanceof MatrixRow){
            for(int i = 0; i < data.size(); i++) {
                tmp = (ArrayList<Double>)data.get(i).getData();
                tmp2 = data.get(i);
                for(int j = 0; j < tmp.size(); j++) {
                    if (tmp.get(j).doubleValue() > cutoff){
                        tmp.set(j, cutoff);
                    }
                }
                data.set(i, tmp2);
            }
        }
   }
    
    /**
     * Returns the highest number of bins in the input
     * @param data
     * @return the highest number of bins in any set of data in the input
     */
    public static int maxBins(ArrayList<HashMapItem> data){
        int maxBins = 0;
        int bins;
        ArrayList<Double>matrix;
        HashMapItem dataItem;
        
        if(data != null && !data.isEmpty()){
            if(data.get(0) instanceof MatrixRow){
                for (int i = 0; i < data.size(); i++) {
                    matrix = (ArrayList<Double>)data.get(i).getData();
                    bins = matrix.size();
                    if (bins > maxBins) {
                        maxBins = bins;
                    }
                      
                }
                return maxBins;
            }
            else{
                for (int i = 0; i < data.size(); i++) {
                    dataItem = data.get(i);
                    bins = maxFeatureBins(dataItem);
                    
                    if(bins > maxBins){
                        maxBins = bins;
                    }
                }
                return maxBins;
            }
           
        }
        else{
            return 0;
        }
    }
    
    /**
     * Returns the lowest number of bins in the input
     * @param data
     * @return the lowest number of bins in the shortest feature or chromosome contained in the input
     */
    public static int minBins(ArrayList<HashMapItem> data){
        int minBins = 0;
        int bins;
        ArrayList<Double>matrix;
        HashMapItem dataItem;
        
        if(data != null && !data.isEmpty()){
            if(data.get(0) instanceof MatrixRow){
                for (int i = 0; i < data.size(); i++) {
                    matrix = (ArrayList<Double>)data.get(i).getData();
                    bins = matrix.size();
                    if (bins < minBins) {
                        minBins = bins;
                    }
                }
                return minBins;
            }
            else{
                for (int i = 0; i < data.size(); i++) {
                    dataItem = data.get(i);
                    bins = minFeatureBins(dataItem);
                    if(bins < minBins){
                        minBins = bins;
                    }
                }
                return minBins;
            }
        }
        else{
            return 0;
        }
    }
    
    /**
     * Returns the number of bins contained in the longest data set contained in the input
     * @param data a set of features or chromosomes
     * @return the number of bins in the longest feature or chromosome contained in the input
     */
    
    public static int maxFeatureBins(HashMapItem data){
        
        ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> hash = (ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>)data.getData();
        Iterator <Map.Entry<String, ConcurrentHashMap<Integer, Integer>>> iter = hash.entrySet().iterator();
        int maxBins = 0;
          
        while(iter.hasNext()){
            
            Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry = iter.next();
            ConcurrentHashMap<Integer, Integer> values = entry.getValue();    
            int bins = values.size();
            
            if(bins > maxBins){
                maxBins = bins;
            }
        }
        return maxBins;
    }
    
    /**
     * Returns the number of bins contained in the shortest feature or chromosome contained in the input
     * @param data a set of features or chromosomes
     * @return the number of bins in the shortest feature or chromosome contained in the input
     */
    
    public static int minFeatureBins(HashMapItem data){
        
        ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> hash = (ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>)data.getData();
        Iterator <Map.Entry<String, ConcurrentHashMap<Integer, Integer>>> iter = hash.entrySet().iterator();
        int minBins = 0;
          
        while(iter.hasNext()){
            
            Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry = iter.next();
            ConcurrentHashMap<Integer, Integer> values = entry.getValue();    
            int bins = values.size();
            if(bins < minBins){
                minBins = bins;
            }
        }
        return minBins;
    }
    
    /**
     * Finds, calculates, and returns the total length of all features/chromosomes in the input
     * @param data an ArrayList of chromosomes / feature sets
     * @return the total length of the input data
     */
    
    public static int findTotalLength(ArrayList<HashMapItem> data){
        int len = 0;
        
        if(data!=null){
        
            ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>fSet;
            Iterator <Map.Entry<String, ConcurrentHashMap<Integer, Integer>>> iter;
            Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry;
            Map.Entry<Integer, Integer> val;

            for(int i = 0; i < data.size(); i ++){

                HashMapItem dataItem = data.get(i);


                fSet = (ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>)dataItem.getData();
                iter = fSet.entrySet().iterator();

                while(iter.hasNext()){
                    entry = iter.next(); //get next feature set

                    ConcurrentHashMap<Integer, Integer> vals = entry.getValue();             //get feature values
                    Iterator <Map.Entry<Integer, Integer>> it = vals.entrySet().iterator();
                    while(it.hasNext()){
                        val = it.next();
                        len += val.getValue();
                    }
                }
            }
        }
        return len;
    }
    
    /**
     * Finds and calculates the average of a given feature across each chromosome
     * contained in the input.
     * @param data an ArrayList of chromosomes / feature sets
     * @param feature the name of the feature whose average we wish to calculate
     * @return the average of the feature across every chromosome in the input
     */
    
    public static double featureAvg(ArrayList<HashMapItem> data, String feature){
        double sum = 0;
        int n = 0;
        
        if(data!=null){
        
            ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>fSet;
            Iterator <Map.Entry<String, ConcurrentHashMap<Integer, Integer>>> iter;
            Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry;
            Map.Entry<Integer, Integer> val;

            for(int i = 0; i < data.size(); i ++){

                HashMapItem dataItem = data.get(i);
                fSet = (ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>)dataItem.getData();
                iter = fSet.entrySet().iterator();

                while(iter.hasNext()){
                    entry = iter.next(); //get next feature set
                    if(feature.equals(entry.getKey())){
                        
                        ConcurrentHashMap<Integer, Integer> vals = entry.getValue();             //get feature values
                        Iterator <Map.Entry<Integer, Integer>> it = vals.entrySet().iterator();
                        while(it.hasNext()){
                            val = it.next();
                            sum += val.getValue();
                            n++;
                        }
                    }
                }
            }
        }
        return sum/n;
    }
    
    public static int sumMaxes(ArrayList<HashMapItem> data){
        
        int sum = 0;
        int n = 0;
        if(data!=null){
        
            ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>fSet;
            Iterator <Map.Entry<String, ConcurrentHashMap<Integer, Integer>>> iter;
            Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry;
            Map.Entry<Integer, Integer> val;
        
            for(int i = 0; i < data.size(); i ++){

                HashMapItem dataItem = data.get(i);
                sum += max(dataItem);
                n++;
            }
        }
        
        return sum/n;
    }
}
