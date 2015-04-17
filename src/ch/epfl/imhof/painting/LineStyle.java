package ch.epfl.imhof.painting;

/**
 * @author Matteo Besançon (245826)
 */
public class LineStyle {
    public enum LineJoin {
        BEVEL, MITER, ROUND
    }
    
    public enum LineCap {
        BUTT, ROUND, SQUARE
    }
    
    private final float    width;
    private final Color    color;
    private final LineCap  cap;
    private final LineJoin join;
    private final float[]  dashingPattern;
    
    public LineStyle(float width, Color color, LineCap cap, LineJoin join,
            float[] dashingPattern) throws IllegalArgumentException {
        if (width < 0.0) {
            throw new IllegalArgumentException("width cannot be negative");
        }
        
        for (float f : dashingPattern) {
            if (f <= 0.0) {
                throw new IllegalArgumentException(
                    "Cannot have a null or negative segment in the dashing pattern");
            }
        }
        
        this.width = width;
        this.color = color;
        this.cap = cap;
        this.join = join;
        this.dashingPattern = dashingPattern;
    }
    
    public LineStyle(float width, Color color) {
        this(width, color, LineCap.BUTT, LineJoin.MITER, new float[0]);
    }
    
    public float width() {
        return width;
    }
    
    public Color color() {
        return color;
    }
    
    public LineCap cap() {
        return cap;
    }
    
    public LineJoin join() {
        return join;
    }
    
    public float[] dashingPattern() {
        return dashingPattern;
    }
    
    public LineStyle withWidth(float newWidth) {
        return new LineStyle(newWidth, color, cap, join, dashingPattern);
    }
    
    public LineStyle withColor(Color newColor) {
        return new LineStyle(width, newColor, cap, join, dashingPattern);
    }
    
    public LineStyle withCap(LineCap newCap) {
        return new LineStyle(width, color, newCap, join, dashingPattern);
    }
    
    public LineStyle withJoin(LineJoin newJoin) {
        return new LineStyle(width, color, cap, newJoin, dashingPattern);
    }
    
    public LineStyle withDashingPattern(float[] newDashingPattern) {
        return new LineStyle(width, color, cap, join, newDashingPattern);
    }
}
