package ch.epfl.imhof.painting;

/**
 * The drawing style of a {@link PolyLine}.
 * <p>
 * Holds informations about
 * <ul>
 * <li>line width,</li>
 * <li>line color,</li>
 * <li>line cap,</li>
 * <li>line join,
 * <li>line dashing pattern.</li>
 * </ul>
 * 
 * @author Matteo Besançon (245826)
 */
public final class LineStyle {
    /**
     * The different ways to join two segments of a line.
     *
     */
    public enum LineJoin {
        BEVEL, MITER, ROUND
    }
    
    /**
     * The different ways to end a line.
     *
     */
    public enum LineCap {
        BUTT, ROUND, SQUARE
    }
    
    private final float    width;
    private final Color    color;
    private final LineCap  cap;
    private final LineJoin join;
    private final float[]  dashingPattern;
    
    /**
     * Creates a new instance of <code>LineStyle</code> given all its
     * parameters.
     * 
     * @param width
     *            the width of the line
     * @param color
     *            the color of the line
     * @param cap
     *            the way to end the line
     * @param join
     *            the way to join two segments of the line
     * @param dashingPattern
     *            the sequence of opacity of the line
     * 
     * @throws IllegalArgumentException
     *             if the width of the line is negative or if the sequence of
     *             opacity of the line contains a negative or null value
     */
    public LineStyle(float width, Color color, LineCap cap, LineJoin join,
            float[] dashingPattern) throws IllegalArgumentException {
        if (width < 0.0f) {
            throw new IllegalArgumentException("Width cannot be negative");
        }
        
        if(dashingPattern!= null){
            for (float f : dashingPattern) {
                if (f <= 0.0f) {
                    throw new IllegalArgumentException(
                        "Cannot have a null or negative segment in the dashing pattern");
                }
            }
        }
        
        this.width = width;
        this.color = color;
        this.cap = cap;
        this.join = join;
        this.dashingPattern = dashingPattern;
    }
    
    /**
     * Creates a new instance of <code>LineStyle</code> given its width and
     * color. By default the <code>LineCap</code> is <code>BUTT</code>, the
     * <code>LineJoin</code> is <code>MITER</code> and the
     * <code>dashingPattern</code> is empty.
     * 
     * @param width
     *            the width of the line
     * @param color
     *            the color of the line
     */
    public LineStyle(float width, Color color) {
        this(width, color, LineCap.BUTT, LineJoin.MITER, new float[0]);
    }
    
    /**
     * Returns the width of the line.
     * 
     * @return the width of the line
     */
    public float width() {
        return width;
    }
    
    /**
     * Returns the color of the line.
     * 
     * @return the color of the line
     */
    public Color color() {
        return color;
    }
    
    /**
     * Returns the line cap.
     * 
     * @return the line cap
     */
    public LineCap cap() {
        return cap;
    }
    
    /**
     * Returns the line join.
     * 
     * @return the line join
     */
    public LineJoin join() {
        return join;
    }
    
    /**
     * Returns the dashing pattern.
     * 
     * @return the dashing pattern
     */
    public float[] dashingPattern() {
        return dashingPattern;
    }
    
    /**
     * Returns a new <code>LineStyle</code> with the given width.
     * 
     * @param newWidth
     *            the line width wanted for the new <code>LineStyle</code>
     * 
     * @return the new <code>LineStyle</code>
     */
    public LineStyle withWidth(float newWidth) {
        return new LineStyle(newWidth, color, cap, join, dashingPattern);
    }
    
    /**
     * Returns a new <code>LineStyle</code> with the given color.
     * 
     * @param newColor
     *            the color wanted for the new <code>LineStyle</code>
     * 
     * @return the new <code>LineStyle</code>
     */
    public LineStyle withColor(Color newColor) {
        return new LineStyle(width, newColor, cap, join, dashingPattern);
    }
    
    /**
     * Returns a new <code>LineStyle</code> with the given line cap.
     * 
     * @param newCap
     *            the line cap wanted for the new <code>LineStyle</code>
     * 
     * @return the new <code>LineStyle</code>
     */
    public LineStyle withCap(LineCap newCap) {
        return new LineStyle(width, color, newCap, join, dashingPattern);
    }
    
    /**
     * Returns a new <code>LineStyle</code> with the given line join.
     * 
     * @param newJoin
     *            the line join wanted for the new <code>LineStyle</code>
     * 
     * @return the new <code>LineStyle</code>
     */
    public LineStyle withJoin(LineJoin newJoin) {
        return new LineStyle(width, color, cap, newJoin, dashingPattern);
    }
    
    /**
     * Returns a new <code>LineStyle</code> with the given dashing pattern.
     * 
     * @param newDashingPattern
     *            the dashing pattern wanted for the new <code>LineStyle</code>
     * 
     * @return the new <code>LineStyle</code>
     */
    public LineStyle withDashingPattern(float[] newDashingPattern) {
        return new LineStyle(width, color, cap, join, newDashingPattern);
    }
}
