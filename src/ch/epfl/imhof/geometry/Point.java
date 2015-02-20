package ch.epfl.imhof.geometry;

/**
 * A point, represented by its cartesian coordinates.
 * 
 * @author Matteo Besançon (245826)
 */
public final class Point {
    private final double x;
    private final double y;

    /**
     * Constructs a point, given its x-coordinate and y-coordinate
     * 
     * @param x
     *            point's abscissa
     * @param y
     *            point's ordinate
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return point's abscissa
     */
    public double x() {
        return x;
    }

    /**
     * @return point's ordinate
     */
    public double y() {
        return y;
    }
}
