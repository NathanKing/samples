/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import structs.Chromosome;
import structs.HashMapItem;
import utils.DataUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.ProgressMonitorInputStream;

/**
 * Instance class for parsing GFF3 file data.
 * @author nathan
 */
public class GFF3Parser {

    private ArrayList<HashMapItem> data;
    public String fastaFile = null;
    public ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> seqData = null;

    /**
     * Creates a new GFF3Parser instance object
     */
    public GFF3Parser() {}
    
    /**
     * Returns an ArrayList filled with HashMap data structures.
     * @param fileName the name of the GFF3 data file
     * @param fastaFile the name of the sequence data file (optional)
     * @param raw_binSize used to calculate the number of bins per data set.
     * @param option used to decide which method of calculation to be used (Count, Length, Percentage (default))
     * @param useSequenceData boolean value indicating whether to use supplied sequence data in parsing.
     * @param winSize window size value for applying the sliding window algorithm
     * @return an ArrayList filled with HashMap data structures.
     * @throws Exception 
     * @see HashMapItem
     */

    public ArrayList<HashMapItem> parse(String fileName, String fastaFile, double raw_binSize, int option, boolean useSequenceData, int winSize) throws Exception {

        // option: 1: count then number of features in a bin
        //         2: count the length of feature in a bin
        //         3: count the length of feature in a bin and divided by bin size 

        int binSize = (int) (raw_binSize * 1000000);  // bp 

        if (option == 3) {

            if (useSequenceData) {

                if (seqData == null) {
                    System.out.println("read fasta");
                    seqData = readActualBinSize(fastaFile, raw_binSize);
                }
            }
        }

        ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> countData = new ConcurrentHashMap();
        ProgressMonitorInputStream pm = new ProgressMonitorInputStream(null, "reading" + fileName, new FileInputStream(fileName));
        InputStream is = new BufferedInputStream(pm);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        data = new ArrayList();
        Chromosome newChr = null;

        String temp = "";

        int count = 0;
        int start, end, bin, len;

        String[] cols;
        String featureName;
        String chr;

        while ((line = reader.readLine()) != null) {

            if (line.trim().equals("")) {
                continue;
            }

            cols = line.split("\\t");
            featureName = cols[2];
            chr = cols[0];

            if (chr.startsWith("scaffold")) {
                continue;
            }
            if (!chr.matches("^.+?\\d$")) {
                continue;
            }

            boolean found = false;

            start = Math.min(Integer.parseInt(cols[3]), Integer.parseInt(cols[4]));
            end = Math.max(Integer.parseInt(cols[3]), Integer.parseInt(cols[4]));

            bin = (int) (start / binSize);

            if (bin > binSize) {
            }

            if (end > (bin + 1) * binSize) {
                end = (int) ((bin + 1) * binSize);
            } else {
            }

            len = end - start + 1;
            Integer binObj = new Integer(bin);

            ConcurrentHashMap<Integer, Integer> featureData;

            for (HashMapItem chromo : data) {
                if (chromo.getName().equals(chr)) {
                    found = true;
                    newChr = (Chromosome) chromo;
                    countData = (ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>) chromo.getData();
                    break;
                }
            }

            if (found == false) {
                countData = new ConcurrentHashMap();
                newChr = new Chromosome(countData, chr);
                data.add(newChr);
            }

            if (countData.containsKey(featureName)) {
                featureData = countData.get(featureName);
                if (featureData.containsKey(binObj)) {
                    if (option == 1) {
                        featureData.put(binObj, new Integer(featureData.get(binObj).intValue() + 1));

                    } else if (option == 2 || option == 3) {
                        featureData.put(binObj, new Integer(featureData.get(binObj).intValue() + len));
                    }
                } else {
                    if (option == 1) {
                        featureData.put(binObj, new Integer(1));

                    } else if (option == 2 || option == 3) {
                        featureData.put(binObj, new Integer(len));
                    }
                }
                countData.put(featureName, featureData);
            } else {

                featureData = new ConcurrentHashMap();
                if (option == 1) {
                    featureData.put(binObj, new Integer(1));

                } else if (option == 2 || option == 3) {
                    featureData.put(binObj, new Integer(len));
                }
                countData.put(featureName, featureData);
            }
            newChr.setData(countData);

        }//end while

        if (option == 3) {

            for (HashMapItem chromo : data) {
                percentage((ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>) chromo.getData(), binSize, chromo.getName(), useSequenceData);
            }
        }

        DataUtils.slidingWindow(data, winSize);

        reader.close();
        return data;
    }
  
    /**
     * Algorithm for adjusting parsed data based on percentage
     * @param countData the set of data to be adjusted
     * @param binSize used to calculate the adjusted value
     * @param chr the name of the chromosome being adjusted; key for matching data in seqData
     * @param useSequenceData boolean value indicating whether to use supplied sequence data in parsing.
     * @throws Exception 
     */
    private void percentage(ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> countData, int binSize, String chr, boolean useSequenceData) throws Exception {
        Iterator<Map.Entry<String, ConcurrentHashMap<Integer, Integer>>> iter = countData.entrySet().iterator();
        ConcurrentHashMap<Integer, Integer> chromosomeBinSizes;
        int binsize;

        while (iter.hasNext()) {
            Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry = iter.next(); //get next feature set
            ConcurrentHashMap<Integer, Integer> values = entry.getValue();
            float value;
            ConcurrentHashMap<Integer, Integer> vals = entry.getValue();             //get feature values
            Iterator<Map.Entry<Integer, Integer>> it = vals.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry<Integer, Integer> val = it.next();

                if (useSequenceData) {

                    chromosomeBinSizes = seqData.get(chr);
                    if (!chromosomeBinSizes.containsKey(val.getKey())) {
                        binsize = binSize;
                    } else {
                        binsize = chromosomeBinSizes.get(val.getKey());
                    }
                } else {
                    binsize = binSize;
                }
                value = (((float) val.getValue()) / binsize) * 100;
                val.setValue((int) value);
            }
        }
    }

    /**
     * Input: (1) chromosome sequence file with fasta format (2) bin size in Mbp
     * unit, for example, 0.5M Output: a ConcurrentHashMap with a key of bin
     * number and a value of actual sequence bin size of a bin for each
     * chromosome sequence
     * @param fastaFile the name of the file containing sequence data
     * @param binSize used to calculate the number of bins
     * @return a hashmap containing the set of binsizes
     * @throws Exception 
     */
    private ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> readActualBinSize(String fastaFile, double binSize) throws Exception {
        ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> binSizes = new ConcurrentHashMap();
        int binSizeBp = (int) (binSize * 1000000);

        ProgressMonitorInputStream pm = new ProgressMonitorInputStream(null, "Reading - " + fastaFile, new FileInputStream(fastaFile));
        InputStream is = new BufferedInputStream(pm);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line; // no header

        String preId = null;
        StringBuffer seq = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.equals("")) {
                continue;
            }

            if (line.startsWith(">")) {
                String id = line.substring(1);

                if (preId != null && !preId.equals(id)) {
                    binSizes.put(preId, getBinSizes(seq.toString(), binSizeBp));
                    seq = new StringBuffer();
                }
                preId = id;
            } else {
                seq.append(line);
            }
        }

        if (!seq.equals("")) {
            binSizes.put(preId, getBinSizes(seq.toString(), binSizeBp));
        }

        reader.close();

        return binSizes;
    }
    
    /**
     * Returns a HashMap containing the bin sizes 
     * @param seq input string containing a sequence of characters
     * @param binSizeBp the bin size in base pairs
     * @return a HashMap containing the bin sizes
     */

    private ConcurrentHashMap<Integer, Integer> getBinSizes(String seq, int binSizeBp) {
        ConcurrentHashMap<Integer, Integer> seqBinGapSizes = new ConcurrentHashMap();
        int len = seq.length();

        int bins = len / binSizeBp;

        if (len % binSizeBp > 0) {
            bins++;
        }

        ConcurrentHashMap<Integer, Integer> seqBinSizes = new ConcurrentHashMap();

        for (int i = 0; i < bins; i++) {
            seqBinGapSizes.put(new Integer(i), new Integer(0));
        }
        for (int i = 0; i < len; i++) {
            if (seq.charAt(i) == 'N' || seq.charAt(i) == 'n') {
                int bin = i / binSizeBp;
                if (seqBinGapSizes.containsKey(bin)) {
                    seqBinGapSizes.put(new Integer(bin), new Integer(seqBinGapSizes.get(bin).intValue() + 1));
                } else {
                    seqBinGapSizes.put(new Integer(bin), new Integer(1));
                }
            }
        }

        for (int i = 0; i < bins; i++) {

            seqBinSizes.put(new Integer(i), new Integer(binSizeBp - seqBinGapSizes.get(i).intValue()));

            if (seqBinSizes.get(i).intValue() > (binSizeBp - seqBinGapSizes.get(i).intValue())) {
                seqBinSizes.put(i, binSizeBp);
            }
        }
        return seqBinSizes;
    }
}
