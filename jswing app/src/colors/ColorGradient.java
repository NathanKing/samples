
package colors;
import java.awt.Color;

public class ColorGradient {
    protected Color startColor;
    protected Color endColor;
    protected float c1p[];
    protected float c2p[];
    protected float start;
    protected float end;
    
    protected float hue = 0.0f;
    protected float saturation = 0.0f;
    protected float brightness = 0.0f;
    
    public final static Color[] GRADIENT_RED_TO_BLUE = createGradient(Color.RED, Color.BLUE, 500);
    public final static Color[] GRADIENT_BLACK_TO_WHITE = createGradient(Color.BLACK, Color.WHITE, 500);
    public final static Color[] GRADIENT_RED_ORANGE_YELLOW_GREEN = createMultiGradient(new Color[]{Color.red, Color.orange, Color.yellow, Color.green}, 500);
    public final static Color[] GRADIENT_DEFAULT = createMultiGradient(new Color[]{new Color(181, 32, 255), Color.blue, Color.green, Color.yellow, Color.orange, Color.red}, 500);
    
    /**
     * Creates an array of Color objects for use as a gradient, using a linear 
     * interpolation between the two specified colors.
     * @param one Color used for the bottom of the gradient
     * @param two Color used for the top of the gradient
     * @param numSteps The number of steps in the gradient. 250 is a good number.
     */
    public static Color[] createGradient(final Color one, final Color two, final int numSteps)
    {
        int r1 = one.getRed();
        int g1 = one.getGreen();
        int b1 = one.getBlue();
        int a1 = one.getAlpha();

        int r2 = two.getRed();
        int g2 = two.getGreen();
        int b2 = two.getBlue();
        int a2 = two.getAlpha();

        int newR;
        int newG;
        int newB;
        int newA;

        Color[] gradient = new Color[numSteps];
        double iNorm;
        for (int i = 0; i < numSteps; i++)
        {
            iNorm = i / (double)numSteps; //a normalized [0:1] variable
            newR = (int) (r1 + iNorm * (r2 - r1));
            newG = (int) (g1 + iNorm * (g2 - g1));
            newB = (int) (b1 + iNorm * (b2 - b1));
            newA = (int) (a1 + iNorm * (a2 - a1));
            
            gradient[i] = new Color(newR, newG, newB, newA);
        }

        return gradient;
    }

    /**
     * Creates an array of Color objects for use as a gradient, using an array of Color objects. It uses a linear interpolation between each pair of points. The parameter numSteps defines the total number of colors in the returned array, not the number of colors per segment.
     * @param colors An array of Color objects used for the gradient. The Color at index 0 will be the lowest color.
     * @param numSteps The number of steps in the gradient. 250 is a good number.
     */
    public static Color[] createMultiGradient(Color[] colors, int numSteps)
    {
        //we assume a linear gradient, with equal spacing between colors
        //The final gradient will be made up of n 'sections', where n = colors.length - 1
        int numSections = colors.length - 1;
        int gradientIndex = 0; //points to the next open spot in the final gradient
        Color[] gradient = new Color[numSteps];
        Color[] temp;

        if (numSections <= 0)
        {
            throw new IllegalArgumentException("You must pass in at least 2 colors in the array!");
        }

        for (int section = 0; section < numSections; section++)
        {
            //we divide the gradient into (n - 1) sections, and do a regular gradient for each
            temp = createGradient(colors[section], colors[section+1], numSteps / numSections);
            for (int i = 0; i < temp.length; i++)
            {
                //copy the sub-gradient into the overall gradient
                gradient[gradientIndex++] = temp[i];
            }
        }

        if (gradientIndex < numSteps)
        {
            //The rounding didn't work out in our favor, and there is at least
            // one unfilled slot in the gradient[] array.
            //We can just copy the final color there
            for (/* nothing to initialize */; gradientIndex < numSteps; gradientIndex++)
            {
                gradient[gradientIndex] = colors[colors.length - 1];
            }
        }

        return gradient;
    }

    /**
     * Creates a new instance of ColorGradient 
     */
    public ColorGradient() {
        startColor = Color.green;  // default color
        endColor = Color.red;      // default color
        c1p = new float[3];
        c2p = new float[3];
        prepareColors();
    }

    /**
     * Creates an new instance of ColorGradient with the designated start and end colors.
     * @param startColor The starting color of the gradient.
     * @param endColor The ending color of the gradient
     */
    public ColorGradient(Color startColor, Color endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
        c1p = new float[3];
        c2p = new float[3];
        prepareColors();
    }
   
    public void setColorRange(Color startColor, Color endColor){
        this.startColor = startColor;
        this.endColor = endColor;
        prepareColors();
    }
    
    //****************************************************************************
    // methods for color map
    protected final void prepareColors() {
     
        getRGBColorComponents(startColor, c1p);
        getRGBColorComponents(endColor, c2p);
    }

    protected void getRGBColorComponents(Color c, float[] f) {
        f[0] = (float)(c.getRed() / (float)255);
        f[1] = (float)(c.getGreen() / (float)255);
        f[2] = (float)(c.getBlue() / (float)255);
    }

    /** Retrieve the start color */
    public Color getColor1() { 
        return startColor; 
    }

    /** Retrieve the end color */
    public Color getColor2() { 
        return endColor; 
    }

    /** Set the start color */
    public void setColor1(Color c) { 
        startColor = c; 
        prepareColors(); 
    }

    /** Set the end color */
    public void setColor2(Color c) { 
        endColor = c; 
        prepareColors(); 
    }

    /** Retrieve the start of the range */
    public float getValueRangeStart() { return start; }

    /** Retrieve the end of the range */
    public float getValueRangeEnd() { return end; }

 
    /** Set the start and end values */
    public void setValueRange(float s, float e) {
     //   if (s < 0.0F || s >= 1.0F ||
     //       e <= 0.0F || e > 1.0F || s >= e) {
     //       throw new IllegalArgumentException("Bad start/end params");
     //   }
        start = s;
        end = e;
    }

 
 
    /**
     * Get a Color corresponding to the value on
     * the range 0.0 to 1.0.  If the value given
     * is outside the range that this segment is
     * responsible for, then null is returned.
     */
    public Color getColor(float v) {
        double pr;
        if (v < start || v > end) return null;

        pr = (v - start)/(end - start);
        float f0 = (float)(c1p[0] + (c2p[0] - c1p[0]) * pr);
        float f1 = (float)(c1p[1] + (c2p[1] - c1p[1]) * pr);
        float f2 = (float)(c1p[2] + (c2p[2] - c1p[2]) * pr);
        return new Color(f0,f1,f2);
   }

    public void reset(){
        this.hue = 0.0f;
        this.saturation = 0.0f;
        this.brightness = 0.0f;
    }

    // not good for this module
    public Color getColor(Color start, Color end, float p) {
        float[] startHSB = Color.RGBtoHSB(start.getRed(), start.getGreen(), start.getBlue(), null);
        float[] endHSB = Color.RGBtoHSB(end.getRed(), end.getGreen(), end.getBlue(), null);
        
        //System.out.println(startHSB[0] + " " + startHSB[1] + " " + startHSB[2]);
        
        //System.out.println(endHSB[0] + " " + endHSB[1] + " " + endHSB[2]);
        float h, s, b;
        
        //black to white
        if(startHSB[2] == 0.0f && endHSB[0] == 0.0f && endHSB[1] == 0.0f){
            

            return Color.getHSBColor(hue, saturation, p);
        }
        
        else if(startHSB[2] == 0.0f){
            
            float hueMax;
            float hueMin;
            if (startHSB[0] > endHSB[0]) {
                hueMax = startHSB[0];
                hueMin = endHSB[0];
            } else {
                hueMin = startHSB[0];
                hueMax = endHSB[0];
            }

            h = ((hueMax - hueMin) * p) + hueMin;
            //s = (startHSB[1] + endHSB[1]) / 2;

            //System.out.println(endHSB[0] + " " + endHSB[1] + " " + endHSB[2]);

            return Color.getHSBColor(h, p, p);

        }
        
        else{
            
            b = (startHSB[2] + endHSB[2]) / 2;
            s = (startHSB[1] + endHSB[1]) / 2;

            float hueMax;
            float hueMin;
            if (startHSB[0] > endHSB[0]) {
                hueMax = startHSB[0];
                hueMin = endHSB[0];
            } else {
                hueMin = startHSB[0];
                hueMax = endHSB[0];
            }

            h = ((hueMax - hueMin) * p) + hueMin;
            //System.out.println("hue " + hue + " sat " + saturation + " brightness " + brightness);
            return Color.getHSBColor(h, s, b);
        }
    }
}