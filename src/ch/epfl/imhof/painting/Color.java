package ch.epfl.imhof.painting;

/**
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
    
    public float r() {
        return r;
    }
    
    public float g() {
        return g;
    }
    
    public float b() {
        return b;
    }
    
    public Color multiply(Color that) {
        return rgb(r * that.r, g * that.g, b * that.b);
    }
    
    public java.awt.Color toJavaColor() {
        return new java.awt.Color(r, g, b);
    }
    
    public static Color gray(float grey) throws IllegalArgumentException {
        checkColorValues(grey);
        return new Color(grey, grey, grey);
    }
    
    public static Color rgb(float r, float g, float b)
            throws IllegalArgumentException {
        checkColorValues(r, g, b);
        return new Color(r, g, b);
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
    
    public static Color rgb(int n) {
        if (n < 0) {
            throw new IllegalArgumentException(
                "The color value must be at least 0x000000");
        }
        
        if (n > 0xFFFFFF) {
            throw new IllegalArgumentException(
                "The color value must be at most 0xFFFFFF");
        }
        
        return rgb(
            ((n & 0xFF0000) >> 16) / 255.0f,
            ((n & 0x00FF00) >> 8) / 255.0f,
            (n & 0x0000FF) / 255.0f);
    }
}
