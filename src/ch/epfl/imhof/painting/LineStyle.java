package ch.epfl.imhof.painting;

/**
 * @author Matteo Besançon (245826)
 *
 */
public class LineStyle {
    
    public enum LineJoin {
        BEVEL, MITER, ROUND
    }
    
    public enum LineCap {
        BUTT, ROUND, SQUARE
    }
    
    private float    lineWidth;
    private Color    lineColor;
    private LineCap  lineCap;
    private LineJoin lineJoin;
    private float[]  lineDashingPattern;
    
    public LineStyle(float width, Color color) {
        this(width, color, LineCap.BUTT, LineJoin.MITER, new float[0]);
    }
    
    public LineStyle(float width, Color color, LineCap cap, LineJoin join,
            float[] dashingPattern) throws IllegalArgumentException {
        if (width < 0.0) {
            throw new IllegalArgumentException("width cannot be negative");
        }
        
        for (int i = 0; i != dashingPattern.length; ++i) {
            if (dashingPattern[i] <= 0.0) {
                throw new IllegalArgumentException(
                        "Cannot have a segment null or negative in the dashing pattern");
            }
        }
        
        this.lineWidth = width;
        this.lineColor = color;
        this.lineCap = cap;
        this.lineJoin = join;
        this.lineDashingPattern = dashingPattern;
    }

    public float lineWidth() {
        return lineWidth;
    }

    public Color lineColor() {
        return lineColor;
    }

    public LineCap lineCap() {
        return lineCap;
    }

    public LineJoin lineJoin() {
        return lineJoin;
    }

    public float[] lineDashingPattern() {
        return lineDashingPattern;
    }
    
    public LineStyle withWidth(float newWidth){
        return new LineStyle(newWidth, lineColor, lineCap, lineJoin, lineDashingPattern );
    }
    
    public LineStyle withColor(Color newColor){
        return new LineStyle(lineWidth, newColor, lineCap, lineJoin, lineDashingPattern );
    }
    
    public LineStyle withCap(LineCap newCap){
        return new LineStyle(lineWidth, lineColor, newCap, lineJoin, lineDashingPattern );
    }
    
    public LineStyle withJoin(LineJoin newJoin){
        return new LineStyle(lineWidth, lineColor, lineCap, newJoin, lineDashingPattern );
    }
    
    public LineStyle withDashingPattern(float[] newDashingPattern){
        return new LineStyle(lineWidth, lineColor, lineCap, lineJoin, newDashingPattern );
    }

}
