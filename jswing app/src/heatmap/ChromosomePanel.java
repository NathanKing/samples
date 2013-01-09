/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heatmap;

import structs.StringSet;
import structs.HashMapItem;
import structs.Feature;
import structs.Chromosome;
import utils.DataUtils;
import utils.ArrayUtils;
import com.bioinforsoft.imageio.graphics2d.VectorGraphics;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.util.Map.Entry;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Canvas for GFF3 data
 * @author nathan
 */
public class ChromosomePanel extends MainViewPanel{
    
   
    private ArrayList<HashMapItem> chrSet = new ArrayList();
    private ArrayList<HashMapItem> featureSet = new ArrayList();
    
    private StringSet chrDrawSet = new StringSet();
    private StringSet featureDrawSet = new StringSet();
    private ArrayList<String> curveSetNames = new ArrayList();
    
    private StringSet drawSet1 = chrDrawSet;
    private StringSet drawSet2 = featureDrawSet;
    
    private boolean drawXAxisGlobal;
    private boolean drawXAxisLocal;
    private boolean chromosomeView;
    
    private double curveHeight;
    private Dimension curveSize;
    
    private Color[] colors = {new Color(0, 0, 153), new Color(0, 153, 153),
                                new Color(255, 153, 0),
                                new Color(153, 0, 0), new Color(0, 153, 0),
                                new Color(0, 102, 0), new Color(204, 102, 0),
                                new Color(102, 102, 153), new Color(153, 0, 204),
                                new Color(153, 102, 0), new Color(0, 102, 51),
                                new Color(102, 102, 51), new Color(0, 102, 204),
                                new Color(102, 102, 0)
                                };
    
    private ArrayList<Color> curveColors = new ArrayList();
    
    
    public ChromosomePanel(){
       super();
       initComponents();
       chr_gap = 30;
       xaxis_gap = 3;
       drawXAxisGlobal = false;
       drawXAxisLocal = true;
       chromosomeView = true;
    }
    
    @Override
    public void uploadData(ArrayList<HashMapItem> data, String title, double binSize, int option){
        
        super.uploadData(data, title, binSize, option);
        
        DataUtils.sortByString(this.data);
        for(HashMapItem i:data){
            yTitleLabels.add(i.getName());
            chrDrawSet.modelSet.add(i.getName());
        }
        chrDrawSet.viewSet = chrDrawSet.modelSet;
        this.legendTitle = "[% per " + binSize + " Mb]";
        
        ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>tmp = ((Chromosome)data.get(0)).getData();
        Iterator <Map.Entry<String, ConcurrentHashMap<Integer, Integer>>> iter = tmp.entrySet().iterator();
        Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry;
        
        while(iter.hasNext()){
            entry = iter.next();
            yValueLabels.add(entry.getKey());
            featureDrawSet.modelSet.add(entry.getKey());
        }
        featureDrawSet.viewSet = featureDrawSet.modelSet;
        for(HashMapItem di:data){
            
            if(di instanceof Chromosome){
                ((Chromosome)di).calculateValues();
            }
        }
        int i = 0;
        int n = 5;
        
        while(i < featureDrawSet.modelSet.size()){
            
            if(colors.length > featureDrawSet.modelSet.size()){
                curveColors.add(colors[i]);
                i++;
            }
            else{
                curveColors.add(new Color(102, 102, n));
                n+=5;
                i++;
            }
        }
      
      
        
        chrSet = DataUtils.clone(data);
        this.option = option;
        chromosomeView = true;
    }
    
    //Create and Return a structure ordered by feature first
    
    private ArrayList<HashMapItem> createFeatureSets(){
        
        ArrayList<HashMapItem>fSet = new ArrayList();
        ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> featureSetData;
        HashMapItem dataItem;
        
        for(String feature:yValueLabels){
            
            featureSetData = new ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>();
            
            for(HashMapItem di:chrSet){
            
                Chromosome chr = (Chromosome)di;
            
                Iterator <Map.Entry<String, ConcurrentHashMap<Integer, Integer>>> iter = chr.getData().entrySet().iterator();
                
                while(iter.hasNext()){

                    Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry = iter.next(); //get next feature set
                    ConcurrentHashMap<Integer, Integer> values = entry.getValue();              //get feature values
                    Set<Map.Entry<Integer, Integer>> entrySet = values.entrySet();
                    Iterator <Map.Entry<Integer, Integer>> it = entrySet.iterator();

                    if(feature.equals(entry.getKey())){
                        featureSetData.put(chr.getName(), new ConcurrentHashMap(values));
                    }
                }
                dataItem = new Feature(featureSetData, feature);
                fSet.add(dataItem);
            }
        }
        return fSet;
    }
    
    @Override
    protected void calculateDimensions(VectorGraphics vg)
    {
        super.calculateDimensions(vg);
        
        int x, y;
        curveSize = new Dimension(0, 0);
        FontRenderContext frc = vg.getFontRenderContext();
        
        if(chromosomeView){
            drawSet1 = chrDrawSet;
            drawSet2 = featureDrawSet;
        }
        else{
            drawSet2 = chrDrawSet;
            drawSet1 = featureDrawSet;
        }
        
        for(HashMapItem di:data){
            di.calculateValues();
        }
        
        maxBins = DataUtils.maxBins(data);
        globalLowValue = DataUtils.min(data);
        globalHighValue = DataUtils.max(data);
        dataLength = DataUtils.findTotalLength(data);
        int xAxisLabelHeight = (int)dataTitlesFont.getStringBounds(xAxisLabel, frc).getHeight();
        int tickValueHeight = (int) dataTitlesFont.getStringBounds("0", frc).getHeight();  
        
        int width = 0, height, i = 50, j = 50;
                
        this.maxYLabelString = ArrayUtils.maxString(drawSet1.viewSet);
        this.maxYValueString = ArrayUtils.maxString(drawSet2.viewSet);
        this.xAxisLabelStringSize = new Dimension((int)dataTitlesFont.getStringBounds(xAxisLabel, frc).getWidth(), (int)dataTitlesFont.getStringBounds(xAxisLabel, frc).getHeight());
        this.legendLabelSize = new Dimension((int)dataTitlesFont.getStringBounds(legendTitle, frc).getWidth(), (int)dataTitlesFont.getStringBounds(legendTitle, frc).getHeight());
        
        //Calculate heatmap view dimensions
        int chrgaps, vgaps, bars;
        
        chrgaps = drawSet1.modelSet.size() * chr_gap;
        
        if(chromosomeView){
            vgaps = drawSet1.modelSet.size() * ((drawSet2.modelSet.size() - curveSetNames.size()) * v_gap);
            bars = drawSet1.modelSet.size() * ((drawSet2.modelSet.size() - curveSetNames.size()) * cellSize.height);
        }
        else{
            vgaps = drawSet1.modelSet.size() * (drawSet2.modelSet.size() * v_gap);
            bars = drawSet1.modelSet.size() * (drawSet2.modelSet.size() * cellSize.height);
        }
        
        height = chrgaps + vgaps + bars;
        
        if(height < 0){
            width = 0;
            height = 0;
        }
        
        canvasSize.setSize(maxBins * cellSize.width, height);
        
        
        if(cellSize.height >= 30){
            curveHeight = 1.5;
        }
        else if(cellSize.height >= 20 && cellSize.height < 30){
            curveHeight = 1.5;
        }
        else{
            curveHeight = 1.5;
        }
        
        //Calculate Y-Axis dimensions

        if(drawYAxis){
            maxYValueStringLength = (int)dataTitlesFont.getStringBounds(maxYValueString, frc).getWidth();
            if(drawYAxisTitle){
                maxYLabelStringLength = (int)dataTitlesFont.getStringBounds(maxYLabelString, frc).getWidth();
            }
            yAxisSize.setSize(featureLabelToCanvasGap + chrToFeatureLabelGap + maxYValueStringLength + maxYLabelStringLength, dataLabelsSize.height + yAxisLabelSize.height);
        }
        
        height = 0;
        
        //Calculate X-Axis dimensions
        
        if(drawXAxis){
            if(drawXAxisValues){
                height += tickValueHeight * 1.5;
            }
            if(drawXAxisTitle){
                height += xAxisLabelHeight;
            }
            height += xaxis_gap;
        }
        xAxisSize.setSize(width, height);
        
        width = 0; height = 0;
        //length
        if(option==2){
            height += (globalHighValue / maxBins) + chr_gap;
        }
        else{
            
            if(curveHeight < 1){
                height += globalHighValue + chr_gap;
            }
            else{
                
                height += globalHighValue * curveHeight + chr_gap;
            }
        }
        
        if(chromosomeView){
            if(!curveSetNames.isEmpty()){
                curveSize.setSize(cellSize.height + maxYValueStringLength, height);
            }
        }
        else{
            curveSize.setSize(0, 0);
        }
        
        
        width = 0;
        height = 0;
        //Calculate title dimensions
        
        if(drawTitle){
            width = (int)titleFont.getStringBounds(title, frc).getWidth();
            height = (int)titleFont.getStringBounds(title, frc).getHeight();
            titleSize.setSize(width + margin, height);
        }
        
        width = 0;
        height = 0;
        
        //Calculate legend dimensions
        if(drawLegend){
            
            if(cellSize.height < 20){
                height = 20;
            }
            else if(cellSize.height > 30){
                height = 30;
            }
            else{
                height = cellSize.height;
            }
            width = (int)dataTitlesFont.getStringBounds(legendTitle, frc).getWidth()*2;
            height += (int)dataTitlesFont.getStringBounds(legendTitle, frc).getHeight();
            if(drawHorizontalLegend){
                width += cellSize.height;
                legendSize.setSize(width, height);
            }
            else{
                legendSize.setSize(width + margin*2, 0);
            }
        }
        
        if(chromosomeView){
        
            if(curveSetNames.size() == drawSet2.modelSet.size()){
                canvasSize.height = 0;
                xAxisSize.height = 0;
            }
        }
        width = 0; height = 0;
        //Calculate the total overall dimensions
        width = canvasSize.width + legendSize.width + yAxisSize.width + curveSize.width + margin ;
        
        if(titleSize.width >= canvasSize.width){
            width += titleSize.width - canvasSize.width;
        }
        
        height += titleSize.height + canvasSize.height + legendSize.height + margin + margin;
        
        height += curveSize.height * drawSet1.modelSet.size();

        if(drawXAxis && drawXAxisLocal){
            height += xAxisSize.height * drawSet1.modelSet.size();
        }
        
        else if (drawXAxis && drawXAxisGlobal){
            height += xAxisSize.height;
        }

        heatMapSize.setSize(width, height);
        heatMapTopLeft.setLocation(i, j);
        i = heatMapTopLeft.x + heatMapSize.width;
        j = heatMapTopLeft.y + heatMapSize.height;
        heatMapBottomRight.setLocation(i, j);
        i = heatMapTopLeft.x + heatMapSize.width / 2;
        j = heatMapTopLeft.y + heatMapSize.height / 2;
        heatMapCenter.setLocation(i, j);
        
        
        docTitleTopLeft.setLocation(yAxisSize.width + margin, titleSize.height);
        canvasTopLeft.setLocation(docTitleTopLeft.x, titleSize.height*2 + docTitleTopLeft.y);
        yAxisTopLeft.setLocation(canvasTopLeft.x, canvasTopLeft.y);
       
        if(drawXAxisGlobal){
            x = canvasTopLeft.x;
            y = canvasTopLeft.y + canvasSize.height;
            
            if(!curveSetNames.isEmpty()){
                y += drawSet1.modelSet.size() * curveSize.height;
            }
        }
        else{
            x = canvasTopLeft.x;
            y = xaxis_gap + canvasTopLeft.y + ((drawSet2.modelSet.size() * v_gap) + (drawSet2.modelSet.size() * cellSize.height));
        }
        
        xAxisTopLeft.setLocation(x, y);
        
        x = 0;
        y = 0;
        
        if(drawLegend){
            x = canvasTopLeft.x + canvasSize.width;
            y = canvasTopLeft.y;
            //horizontal legend
            if(drawHorizontalLegend){
                y += canvasSize.height;
                if(!curveSetNames.isEmpty()){
                    y += curveSize.height * drawSet1.modelSet.size();
                }
                //global
                if(drawXAxis && drawXAxisGlobal){
                    y += xAxisSize.height + v_gap;
                }
                //local
                else if(drawXAxis && drawXAxisLocal){
                    y += xAxisSize.height * drawSet1.modelSet.size();
                }
            }
            else{
                x += canvasToLegendGap  + curveSize.width;
            }
        }
        
        legendTopLeft.setLocation(x, y);
        System.out.println(y);
        //System.out.println("total size = " + heatMapSize.width + " " + heatMapSize.height);
    }

    /**
     * The overridden painting method, now optimized to simply draw the data
     * plot to the screen, letting the drawImage method do the resizing. This
     * saves an extreme amount of time.
     */
    @Override
    public void paintComponent(Graphics g){
        VectorGraphics vg = VectorGraphics.create(g);
        int width = this.getWidth();
        int height = this.getHeight();
        this.setOpaque(true);
        // clear the panel
        vg.setColor(bg);
        vg.fillRect(0, 0, width, height);
        
        if(drawSet1.modelSet != null && drawSet2.modelSet != null){

            // draw the heatmap
            if (bufferedImage == null)
            {
                calculateDimensions(vg);
                bufferedImage = new BufferedImage(heatMapSize.width, heatMapSize.height, 2);
                VectorGraphics vg2d = (VectorGraphics.create(bufferedImage.createGraphics()));
                
                drawImage(vg2d);
                
                setPreferredSize(new Dimension(heatMapSize.width, heatMapSize.height));     
                setSize(new Dimension(heatMapSize.width, heatMapSize.height)); 
                invalidate(); 
            }
            vg.drawImage(bufferedImage, heatMapTopLeft.x, heatMapTopLeft.y, heatMapSize.width, heatMapSize.height, null);
        }
    }
    
    private void drawImage(VectorGraphics vg2d){
        
        if(drawTitle){
            drawTitle(vg2d);
        }
      
        drawHeatMapData(vg2d);

        if(drawXAxis && drawXAxisGlobal && (curveSetNames.size() != drawSet2.modelSet.size())){
            drawXAxisGlobal(vg2d);
        }

        if(drawLegend){
            drawLegend(vg2d);
        }
    }
    
    @Override
    protected void drawHeatMapData(VectorGraphics vg2d){
        
        int x = canvasTopLeft.x;
        int y = canvasTopLeft.y;
        int n;
        int localBins;
        int max;
        int min;
        int xcurve;
        int ycurve;
        int curveY = canvasTopLeft.y;
        FontRenderContext frc = vg2d.getFontRenderContext();
        vg2d.setColor(Color.black);
        vg2d.setFont(dataTitlesFont);
        
        ConcurrentHashMap<Integer, Integer> values;
        Set<Entry<Integer, Integer>> entrySet;
        Iterator <Map.Entry<Integer, Integer>> it;
        HashMapItem dataItem = null;
        
        for(int i = 0; i < drawSet1.modelSet.size(); i++){
            for(HashMapItem di:data){
                if(drawSet1.modelSet.get(i).equals(di.getName())){
                    dataItem = di;
                    break;
                }
            }
            
            min = dataItem.getMin();
            max = dataItem.getMax();
            lowValue = min;
            highValue = max;
            adjustLowValueAndHighValue();
            x = canvasTopLeft.x;
            localBins = dataItem.getMaxBins();
            
            curveY = y;
            
            if(drawYAxisTitle){
                int xpos = maxYLabelStringLength;
                int ypos = y + (int)vg2d.getFont().getStringBounds(dataItem.getName() + "", frc).getHeight();
                vg2d.drawString(dataItem.getName(), xpos, ypos);
            }
            
            if(chromosomeView){
            
                ArrayList<Double> averages = dataItem.getAvgs();
                Collections.sort(averages);
                Collections.reverse(averages);
                n = 0;
                Set<Entry<String, ConcurrentHashMap<Integer, Integer>>> entries = ((Chromosome)dataItem).getData().entrySet();
                Iterator <Map.Entry<String, ConcurrentHashMap<Integer, Integer>>> iter = entries.iterator();
                
                for(Double average:averages){
                    while(iter.hasNext()){
                        Entry<String, ConcurrentHashMap<Integer, Integer>> entry = iter.next();

                        if(curveSetNames.contains(entry.getKey())){
                            values = entry.getValue();

                            if(average == DataUtils.avg(values)){
                                xcurve = x + (values.size() * cellSize.width) + canvasToLegendGap;
                                ycurve = y + (cellSize.height * n);
                                vg2d.setColor(curveColors.get(n));
                                n++;
                                drawCurve(vg2d, values, curveY);
                                break;
                            }
                        }
                        
                    }
                    iter = entries.iterator();
                }
            }
            
            if(chromosomeView && !curveSetNames.isEmpty()){
                y += curveSize.height;
            }
            
            n = 0;
            
            for(int j = 0; j < drawSet2.modelSet.size(); j++){
                String attribute = drawSet2.modelSet.get(j);
                if(dataItem instanceof Chromosome){
                    values = ((Chromosome)dataItem).getData().get(attribute);
                }
                else{
                    values = ((Feature)dataItem).getData().get(attribute);
                }
                entrySet = values.entrySet();
                it = entrySet.iterator();
                
                if(chromosomeView){
                    
                    if(!curveSetNames.contains(attribute)){
                
                        while(it.hasNext()){
                            Map.Entry<Integer, Integer> val = it.next();
                            vg2d.setColor(getCellColor(val.getValue(), lowValue, highValue));
                            x = x + cellSize.width;
                            vg2d.fillRect(x, y, cellSize.width, cellSize.height);
                        }


                        x = canvasTopLeft.x;

                        if(drawYAxis){
                            drawYAxis(vg2d, drawSet2.viewSet.get(j), y);
                        }
                        n++;
                        y += cellSize.height;
                        y += v_gap;
                    }
                }
                else{
                
                        while(it.hasNext()){
                            Map.Entry<Integer, Integer> val = it.next();
                            vg2d.setColor(getCellColor(val.getValue(), lowValue, highValue));
                            x = x + cellSize.width;
                            vg2d.fillRect(x, y, cellSize.width, cellSize.height);
                        }


                        x = canvasTopLeft.x;

                        if(drawYAxis){
                            drawYAxis(vg2d, drawSet2.viewSet.get(j), y);
                        }
                        n++;
                        y += cellSize.height;
                        y += v_gap;
                }
            }
            

            if(drawXAxis){
                
                if(drawXAxisLocal){
                    
                    if(curveSetNames.size() != drawSet2.modelSet.size()){
                        drawXAxisLocal(vg2d, localBins, y);
                        y += xAxisSize.height;
                    }
                }
            }
            if(curveSetNames.size() == drawSet2.modelSet.size()){
                y -= chr_gap;
            }
            y += chr_gap;
        }
    }
    protected void drawCurve(VectorGraphics vg2d, ConcurrentHashMap<Integer, Integer> values, int y){
        
        Font f = new Font("Arial", 1, 12);
        vg2d.setFont(f);
        FontRenderContext frc = vg2d.getFontRenderContext();
        int x = canvasTopLeft.x;
        Polygon p = new Polygon();
        Iterator <Map.Entry<Integer, Integer>> it = values.entrySet().iterator();
        Map.Entry<Integer, Integer> val;
        
        String tickValue;
        int tickValueWidth;
        int tickValueHeight;
        int xpos, ypos;

        int highVal = (int) globalHighValue;
        
        if(option==2){
            
            while(highVal > 100){
                
                highVal = highVal / 10;
            }
        }
        
        int max = (int) (highVal * 1.5);
        
        y = y + max;
        
        
        while(it.hasNext()){
            val = it.next();
            int value = val.getValue();

            if(option==2){
                while(value > 100){
                    value /= 10;
                }
            }
            
            value = (int) Math.floor(value);
            
            if(value >= max){
                value = max;
            }
            //vg2d.setXORMode(getCellColor(avgs.get(1), min, max));
            p.addPoint(x, y);//bottom left pt
            p.addPoint(x, y - value);
            x = x + cellSize.width;
            p.addPoint(x, y - value);
            p.addPoint(x, y);

        }
        vg2d.fillPolygon(p);
        
        vg2d.setColor(Color.BLACK);
        
        x = canvasTopLeft.x;
        
        //draw ticks
        vg2d.drawLine(x-2, y, x+2, y);
        vg2d.drawLine(x-2, y - max/2, x+2, y - max/2);
        vg2d.drawLine(x-2, y - max, x+2, y - max);
        
        //draw V line
        vg2d.drawLine(x, y - max, x, y);
        
        tickValue = this.convertToString(0, (int)globalHighValue);
        tickValueWidth = (int)f.getStringBounds(globalHighValue + "", frc).getWidth();
        tickValueHeight = (int)f.getStringBounds(globalHighValue + "", frc).getHeight();
        xpos = (int) (x - tickValueWidth *1.5);
        ypos = y + tickValueHeight /2;
        vg2d.drawString(tickValue, xpos, ypos, x, ypos);
        
        tickValue = this.convertToString((int)globalHighValue/2, (int)globalHighValue);
        tickValueWidth = (int)f.getStringBounds(globalHighValue + "", frc).getWidth();
        tickValueHeight = (int)f.getStringBounds(globalHighValue + "", frc).getHeight();
        xpos = (int) (x - tickValueWidth *1.5);
        ypos = (y - (max/2)) + tickValueHeight/2;
        vg2d.drawString(tickValue, xpos, ypos, x, ypos);
        
        tickValue = this.convertToString((int)globalHighValue, (int)globalHighValue);
        tickValueWidth = (int)f.getStringBounds(globalHighValue + "", frc).getWidth();
        tickValueHeight = (int)f.getStringBounds(globalHighValue + "", frc).getHeight();
        xpos = (int) (x - tickValueWidth *1.5);
        ypos = (y - max) + tickValueHeight/2;
        vg2d.drawString(tickValue, xpos, ypos, x, ypos);
        
        vg2d.setFont(dataTitlesFont);
    }
    
    
    protected void drawXAxisLocal(VectorGraphics vg2d, int bins, int y){
        FontRenderContext frc = vg2d.getFontRenderContext();
        
        vg2d.setColor(Color.black);
        vg2d.setFont(dataTitlesFont);

        int max;
        int xAxisLabelHeight = (int)dataTitlesFont.getStringBounds(xAxisLabel, frc).getHeight();
        int x = canvasTopLeft.x;
        int x1;
        int x2;
        int tickValueWidth, tickValueHeight;
        int titleLoc;
        int tickValueY = y;
        int noTicks;
        double totalSize; 
        String tickValue;
        
        
        totalSize = bins * binSize;
        tickInterval = calculateTickInterval(totalSize);

        noTicks = (int)totalSize / tickInterval + 1;
        x2 = x + (bins * cellSize.width);
        
        for (int k = 0; k < noTicks; k++) {

            tickValueY = y;
            x1 = (int)(k * tickInterval/binSize*cellSize.width + x);

            vg2d.drawLine(x1, y, x2, y);

            //draw vertical ticks
            if(drawXAxisTicks){
                vg2d.drawLine(x1, tickValueY-tickLength, x1, tickValueY+tickLength);
            }

            if(drawXAxisValues){
                tickValue = k * tickInterval + "";
                tickValueWidth = (int)dataTitlesFont.getStringBounds(tickValue, frc).getWidth();
                tickValueHeight = (int)dataTitlesFont.getStringBounds(tickValue, frc).getHeight();
                tickValueY += tickValueHeight*1.5;
                vg2d.drawString(tickValue, x1 - (tickValueWidth/2), y + (tickValueHeight*1.5));
            }
        }

        //draw X axis label
        if(drawXAxisTitle){

            titleLoc = x2/2 + xAxisLabelStringSize.width;
            if(titleLoc >= x2/2){
                titleLoc -= xAxisLabelStringSize.width/2;
            }
            vg2d.drawString(xAxisLabel, titleLoc, tickValueY + xAxisLabelHeight);
        }
    }
    protected void drawXAxisGlobal(VectorGraphics vg2d){
        
        FontRenderContext frc = vg2d.getFontRenderContext();
        
        vg2d.setColor(Color.black);
        vg2d.setFont(dataTitlesFont);

        int xAxisLabelHeight = (int)dataTitlesFont.getStringBounds(xAxisLabel, frc).getHeight();
        int x = canvasTopLeft.x;
        int y = xAxisTopLeft.y;
        int x1;
        int x2;
        int tickValueWidth, tickValueHeight;
        int titleLoc;
        int tickValueY = y;
        int noTicks;
        double totalSize; 
        String tickValue;
        
            x = xAxisTopLeft.x;
            y = xAxisTopLeft.y;
            x2 = x + (maxBins * cellSize.width);
            totalSize = maxBins * binSize;
            tickInterval = calculateTickInterval(totalSize);
            noTicks = (int)totalSize / tickInterval + 1;
            vg2d.drawLine(x, y, x2, y);

            // draw ticks + values
            for (int k = 0; k < noTicks; k++) {
                x1 = (int)(k * tickInterval/binSize*cellSize.width + x);
                tickValueY = y;
                
                //draw vertical ticks
                if(drawXAxisTicks){
                    vg2d.drawLine(x1, y-tickLength, x1, y+tickLength);
                }
                
                if(drawXAxisValues){
                    tickValue = k * tickInterval + "";
                    tickValueWidth = (int)dataTitlesFont.getStringBounds(tickValue, frc).getWidth();
                    tickValueHeight = (int)dataTitlesFont.getStringBounds(tickValue, frc).getHeight();
                    tickValueY += tickValueHeight*1.5;
                    vg2d.drawString(tickValue, x1 - (tickValueWidth/2), tickValueY);
                }
            }
            //draw X axis label
            if(drawXAxisTitle){
                titleLoc = x2/2 + xAxisLabelStringSize.width;
                if(titleLoc >= x2/2){
                    titleLoc -= xAxisLabelStringSize.width/2;
                }
                vg2d.drawString(xAxisLabel, titleLoc, tickValueY + xAxisLabelHeight);
            }
    }
    //@Override
    protected void drawYAxis(VectorGraphics vg2d, String label, int y){
        FontRenderContext frc = vg2d.getFontRenderContext();

        vg2d.setFont(dataTitlesFont);
        vg2d.setColor(dataTitlesColor);
        
        int x;
        int featureNameHeight;
        int middle;

        featureNameHeight = (int)dataTitlesFont.getStringBounds(label, frc).getHeight()/2;

        middle = y + cellSize.height / 2;
        x = canvasTopLeft.x;

        if(drawYAxis){
            if(drawYAxisValues){
                vg2d.setFont(dataTitlesFont);
                vg2d.setColor(dataTitlesColor);
                vg2d.drawString(label, x - maxYValueStringLength - 15, middle +featureNameHeight);
            }
            if(drawYAxisTicks){
                vg2d.setColor(Color.BLACK);
                vg2d.drawLine(x, middle, x + tickLength, middle);
            }
        }
    }
    @Override
    protected void drawLegend(VectorGraphics vg2d){
        
        if(!chromosomeView){
            if (drawHorizontalLegend) {
                drawHLegend(vg2d);
            } 
            else {
                drawVLegend(vg2d);
            }
        }
        
        else{
            //full
            if(curveSetNames.size() == drawSet2.modelSet.size()){
                drawCurveLegend(vg2d);
            }
            //not full
            else if(!curveSetNames.isEmpty()){
                if (drawHorizontalLegend) {
                    drawHLegend(vg2d);
                } 
                else {
                    drawVLegend(vg2d);
                }
                drawCurveLegend(vg2d);
            }
            //empty
            else{
                if (drawHorizontalLegend) {
                    drawHLegend(vg2d);
                } 
                else {
                    drawVLegend(vg2d);
                }
            }
        }
    }
    
    private void drawCurveLegend(VectorGraphics vg2d){
        FontRenderContext frc = vg2d.getFontRenderContext();
        int xcurve = canvasSize.width + canvasTopLeft.x + 5;
        int ycurve = canvasTopLeft.y;
        int n = 0;
        int strHeight = 0;
        for(String str:curveSetNames){
            vg2d.setColor(Color.BLACK);
            vg2d.drawString(str, xcurve, ycurve, xcurve + 5, ycurve);
            vg2d.setColor(curveColors.get(n));
            strHeight = (int)dataTitlesFont.getStringBounds(str, frc).getHeight()/2;
            ycurve += strHeight;
            vg2d.fillRect(xcurve, ycurve, 30, cellSize.height/3);
            n++;
            ycurve +=(cellSize.height/3) + strHeight*2;
        }
    }
    
    private void drawVLegend(VectorGraphics vg2d){
        
        FontRenderContext frc = vg2d.getFontRenderContext();
        String tickValue;
        double step = 0.005;
        int max = (int)(1/step);   
        int x = legendTopLeft.x;
        int y = canvasTopLeft.y;
        int xLegendStart = x;
        int yLegendStart = y;
        int legendHeight;
        int tickValueWidth;
        
        if(option == 1){
            lowValue = 0;
            highValue = maxBins;
            this.legendTitle = "[Total bins]";
        }
        //length
        else if(option == 2){
            lowValue = 0;
            highValue = dataLength;
            legendTitle = "[Total Length]";
            
        }
        //percentage
        else{
            
            lowValue = globalLowValue;
            highValue = globalHighValue;
            legendTitle = "[% per " + binSize + " Mb]";
        }
        
        adjustLowValueAndHighValue();

        if(cellSize.height < 20){
            legendHeight = 20;
        }
        else if(cellSize.height > 30){
            legendHeight = 30;
        }
        else{
            legendHeight = cellSize.height;
        }
        
        for (int k = 0; k <= max; k++) {

            vg2d.setColor(colorGradient.getColor(lowValueColor, highValueColor, (float)(step*k)));
            x = xLegendStart;
            y = yLegendStart + k ;
            vg2d.fillRect(x, y, cellSize.height/2, cellSize.width);
            
            //draw Vertical legend values
            if (k == 0 || k == max || k == max/2) { 
                tickValue = convertToString(k, max);
                int tickValueHeight = (int)dataTitlesFont.getStringBounds(tickValue, frc).getHeight();
                vg2d.setColor(dataTitlesColor);
                vg2d.setFont(dataTitlesFont);
                if(k == 0){
                    vg2d.drawString(tickValue, x+legendHeight/2 + 15, y + tickValueHeight);
                }
                else{
                    vg2d.drawString(tickValue, x+legendHeight/2 + 15, y + tickValueHeight/2);
                }
            }
        }
        int itemWidth = (int)dataTitlesFont.getStringBounds(legendTitle, frc).getHeight();

        //draw legend title
        if(drawLegendTitle){
            vg2d.drawString(legendTitle, xLegendStart, y + itemWidth + 30);
        }
    }

    private void drawHLegend(VectorGraphics vg2d){
        
        FontRenderContext frc = vg2d.getFontRenderContext();
        String tickValue;
        double step = 0.005;
        int max = (int)(1/step);   
        int x = legendTopLeft.x;
        int y = legendTopLeft.y;
        int xLegendStart = x;
        int yLegendStart = y;
        int legendHeight;
        int tickValueWidth;
        
        if(option == 1){
            lowValue = 0;
            highValue = maxBins;
            this.legendTitle = "[Total bins]";
        }
        //length
        else if(option == 2){
            lowValue = 0;
            highValue = dataLength;
            legendTitle = "[Total Length]";
            
        }
        //percentage
        else{
            
            lowValue = globalLowValue;
            highValue = globalHighValue;
            legendTitle = "[% per " + binSize + " Mb]";
        }
        
        adjustLowValueAndHighValue();

        if(cellSize.height < 20){
            legendHeight = 20;
        }
        else if(cellSize.height > 30){
            legendHeight = 30;
        }
        else{
            legendHeight = cellSize.height;
        }
        
        xLegendStart -= max;
        for (int k = 0; k <= max; k++) {
            vg2d.setColor(colorGradient.getColor(lowValueColor, highValueColor, (float)(step*k)));
            x = xLegendStart + k;

            vg2d.fillRect(x, y, cellSize.width, legendHeight/2);

            tickValue = convertToString(k, max);

            tickValueWidth = (int)dataTitlesFont.getStringBounds(tickValue, frc).getWidth()/2;

            //draw legend ticks

            if (k == 0 || k == max/2  || k == max) { 
                vg2d.setColor(dataTitlesColor);
                vg2d.drawString(tickValue, x-tickValueWidth, y+legendHeight/2 + 15);
            }
        }
        if(drawLegendTitle){
            vg2d.drawString(legendTitle, x + 15, y+legendHeight/2);
        }
    }
    
    private int calculateTickInterval(double totalSize){
        if(totalSize <= 5){
            return 2;
        }
        else if(totalSize > 5 && totalSize <= 10){
            return 5;
        }
        else if(totalSize > 10 && totalSize <= 100){
            return 10;
        }
        else if(totalSize > 100 && totalSize <= 500){
            return 25;
        }
        else if(totalSize > 500){
            return 50;
        }
        else{
            return 100;
        }
    }
   
 
    
    @Override
    protected Image getChartImage(boolean flag)
    {
        byte byte0 = ((byte)(flag ? 6 : 5));
        
        int width = heatMapSize.width;
        int height = heatMapSize.height;
        
        BufferedImage bufferedimage = new BufferedImage(width, height, byte0);
        VectorGraphics vg2d = (VectorGraphics)bufferedimage.createGraphics();
        vg2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        vg2d.setColor(bg);
        vg2d.fillRect(0, 0, width, height);
        
        drawTitle(vg2d);
        drawHeatMapData(vg2d);
        if(drawXAxis && drawXAxisGlobal){
            drawXAxisGlobal(vg2d);
        }
        if(drawYAxis){
            drawYAxis(vg2d);
        }
        if(drawLegend){
            drawLegend(vg2d);
        }
        return bufferedimage;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    
    void updateData(ArrayList<HashMapItem> dt, StringSet s1, StringSet s2, ArrayList<String> curveSetNames) {
        
        chrDrawSet.modelSet = s1.modelSet;
        chrDrawSet.viewSet = s1.viewSet;
        featureDrawSet.modelSet = s2.modelSet;
        featureDrawSet.viewSet = s2.viewSet;
        
        this.curveSetNames = curveSetNames;
  
        if(dt!=null){
            this.data = dt;
            chrSet = DataUtils.clone(data);
            filterData();
        }
        else{
            if(chromosomeView){
                data = DataUtils.clone(chrSet);
            }
            else{
                data = DataUtils.clone(featureSet);
            }
            filterData();
        }

        bufferedImage = null;
        repaint();
    }
    
    public void filterData(){
        ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>fSet;
        Iterator <Map.Entry<String, ConcurrentHashMap<Integer, Integer>>> iter;
        Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry;
        Map.Entry<Integer, Integer> val;

        for(int i = 0; i < data.size(); i ++){

            HashMapItem dataItem = data.get(i);


            if(drawSet1.modelSet.contains(dataItem.getName())){

                fSet = (ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>)dataItem.getData();

                Set<String> keyset = fSet.keySet();

                for(String key:fSet.keySet()){

                    if(drawSet2.modelSet.contains(key)){

                    }
                    else{
                        fSet.remove(key);
                    }
                }
            }
            else{
                data.remove(dataItem);
            }
        }
        
       
    }
    
    public void setDrawXAxisGlobal(boolean drawXAxisGlobal) {
        this.drawXAxisGlobal = drawXAxisGlobal;
        bufferedImage = null;
        repaint();
    }
    
    public void setDrawXAxisLocal(boolean drawXAxisLocal) {
        this.drawXAxisLocal = drawXAxisLocal;
        bufferedImage = null;
        repaint();
    }
    public void setOption(int i) {
        this.option = i;
        if(option == 1){
            legendTitle = "[Total bins]";
        }
        //length
        else if(option == 2){
            legendTitle = "[Total Length]";
            
        }
        //percentage
        else{
            legendTitle = "[% per " + binSize + " Mb]";
        }
    }
    
    public void setChr_gap(int chr_gap) {
        this.chr_gap = chr_gap;
        bufferedImage = null;
        repaint();
    }

    public void setChromosomeView(boolean chromosomeView) {
       
        if(this.chromosomeView != chromosomeView){
            this.chromosomeView = chromosomeView;
            
            if(chromosomeView){
                data = DataUtils.clone(chrSet);
                drawSet1 = chrDrawSet;
                drawSet2 = featureDrawSet;
            }
            else{
                featureSet = createFeatureSets();
                data = featureSet;
                drawSet2 = chrDrawSet;
                drawSet1 = featureDrawSet;
            }
            bufferedImage = null;
            repaint();
        }
    }

    @Override
    protected void drawYAxis(VectorGraphics vg2d) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void drawXAxis(VectorGraphics vg2d) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
  
}