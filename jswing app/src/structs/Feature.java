/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structs;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Class to distinguish between whether an HashMapItem is a feature
 * @author nathan
 */
public class Feature extends HashMapItem{
    
    public Feature(ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> countData, String name) {
        super(countData, name);
    }
    
    @Override
    public ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> getData(){
        return (ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>) super.getData();
    }
}
