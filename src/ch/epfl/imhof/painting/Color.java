package ch.epfl.imhof.painting;


public class Color {
    
    private final float r, g, b;
    
    private Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    private static void colorValueCheck(float... color) {
        for (int i = 0; i != color.length; ++i) {
            if (color[i] < 0 || color[i] > 1) {
                throw new IllegalArgumentException(
                        "The color value must be between 0 an 1");
            }
        }
    }
    
    public static Color gray(float grey) throws IllegalArgumentException {
        colorValueCheck(grey);
        return new Color(grey, grey, grey);
    }
    
    public static Color rgb(float r, float g, float b)
            throws IllegalArgumentException {
        colorValueCheck(r, g, b);
        return new Color(r, g, b);
    }
    
    // Still need to do the method that uses the argument int.
    
    public float r() {
        return r;
    }
    
    public float g() {
        return g;
    }
    
    public float b() {
        return b;
    }
    
    public Color multiplyTwoColor(Color a, Color b){
        return rgb(a.r*b.r, a.g*b.g, a.b*b.b);
    }
    
    public java.awt.Color convertToJavaColor(){
        return new java.awt.Color(r,g,b);
    }
}
