package ch.epfl.imhof.painting;

/**
 * Represents a color in the RGB system.
 * 
 * @author Matteo Besançon (245826)
 * @author Matthieu Bovel (250300)
 */
public class Color {
    public static final Color RED   = rgb(1.0f, 0.0f, 0.0f);
    public static final Color GREEN = rgb(0.0f, 1.0f, 0.0f);
    public static final Color BLUE  = rgb(0.0f, 0.0f, 1.0f);
    public static final Color BLACK = rgb(0.0f, 0.0f, 0.0f);
    public static final Color WHITE = rgb(1.0f, 1.0f, 1.0f);
    
    private final float       r, g, b;
    
    private Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    private static void checkColorValues(float... colors) {
        for (float color : colors) {
            if (color < 0) {
                throw new IllegalArgumentException(
                        "The color value must be at least 0");
            }
            
            if (color > 1) {
                throw new IllegalArgumentException(
                        "The color value must be at most 1");
            }
            
        }
    }
    
    /**
     * Returns the red value of the color.
     *
     * @return the red value of the color
     */
    public float r() {
        return r;
    }
    
    /**
     * Returns the green value of the color.
     * 
     * @return the green value of the color
     */
    public float g() {
        return g;
    }
    
    /**
     * Returns the blue value of the color.
     * 
     * @return the blue value of the color
     */
    public float b() {
        return b;
    }
    
    /**
     * Multiplies two colors. Concretly it multiplies separatly the red, green
     * and blue valueto creat the new color.
     * 
     * @param that
     *            the other color we want to multiply.
     * 
     * @return the new color created by the multiplication
     */
    public Color multiply(Color that) {
        return rgb(r * that.r, g * that.g, b * that.b);
    }
    
    /**
     * Converts a color to a color from the java API ({@link java.awt.Color}).
     * 
     * @return a color from the java API
     */
    public java.awt.Color toJavaColor() {
        return new java.awt.Color(r, g, b);
    }
    
    /**
     * Converts a color from the java API ({@link java.awt.Color}) to a color.
     * 
     * @param javaColor
     *            color from the java API
     *            
     * @return the converted color
     */
    public Color fromJavaColor(java.awt.Color javaColor) {
        return rgb(javaColor.getRed() / 255f, javaColor.getGreen() / 255f,
            javaColor.getBlue() / 255f);
    }
    
    /**
     * Returns a shade of grey given a value in the interval [0.0 , 1.0]. 0.0 is
     * black and 1.0 is white.
     * 
     * @param grey
     *            the value of grey.
     * 
     * @return the wanted shade of grey
     * 
     * @throws IllegalArgumentException
     *             if the value given in argument is not in the interval [0.0 ,
     *             1.0].
     */
    public static Color gray(float grey) throws IllegalArgumentException {
        checkColorValues(grey);
        return new Color(grey, grey, grey);
    }
    
    /**
     * Returns a color given its value of red, green and blue. Each value must
     * be in the interval [0.0 , 1.0].
     * 
     * @param r
     *            the red value of the color
     * @param g
     *            the green value of the color
     * @param b
     *            the blue value of the color
     * 
     * @return the wanted color
     * 
     * @throws IllegalArgumentException
     *             if one of the value given in argument is not in the interval
     *             [0.0 , 1.0].
     */
    public static Color rgb(float r, float g, float b)
            throws IllegalArgumentException {
        checkColorValues(r, g, b);
        return new Color(r, g, b);
    }
    
    /**
     * Returns a color given its value of red, green and blue. These values are
     * encapsulated in an integer, the red value in the bits 23 to 16, the green
     * in the bits 15 to 8 and the blue in the bits 7 to 0.
     * 
     * @param n
     *            the integer that encapsulate the red green and blue values.
     * 
     * @return the wanted color
     */
    public static Color rgb(int n) {
        if (n < 0) {
            throw new IllegalArgumentException(
                    "The color value must be at least 0x000000");
        }
        
        if (n > 0xFFFFFF) {
            throw new IllegalArgumentException(
                    "The color value must be at most 0xFFFFFF");
        }
        
        return rgb(((n & 0xFF0000) >> 16) / 255.0f,
            ((n & 0x00FF00) >> 8) / 255.0f, (n & 0x0000FF) / 255.0f);
    }
}
