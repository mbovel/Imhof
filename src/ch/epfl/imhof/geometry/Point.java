package ch.epfl.imhof.geometry;

import java.util.function.Function;

/**
 * A point, represented by its cartesian coordinates.
 * 
 * @author Matteo Besançon (245826)
 * @author Matthieu Bovel (250300)
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
    
    /**
     * Let A and B be two cartesian coordinate systems with aligned axes.
     * <p>
     * Given two {@link Point Points} expressed in coordinate system A and the
     * same two {@link Point Points} expressed in coordinate system B, returns a
     * {@link Function} (<code>Point -> Point</code>) representing the change of
     * coordinate from A to B.
     * 
     * @param p1A
     *            first point in cartesian coordinate system A
     * @param p1B
     *            first point in cartesian coordinate system B
     * @param p2A
     *            second point in cartesian coordinate system A
     * @param p2B
     *            second point in cartesian coordinate system B
     * @return a {@link Function} (<code>Point -> Point</code>) representing the
     *         change of coordinate
     * @throws IllegalArgumentException
     *             if p1 and p2 are aligned, either horizontally or vertically.
     */
    public static Function<Point, Point> alignedCoordinateChange(Point p1A,
            Point p1B, Point p2A, Point p2B) throws IllegalArgumentException {
        if (p1A.x() == p2A.x()) {
            throw new IllegalArgumentException(
                "p1 and p2 have the same x-coordinate");
        }
        
        if (p1A.y() == p2A.y()) {
            throw new IllegalArgumentException(
                "p1 and p2 have the same y-coordinate");
        }
        
        double ax = (p1B.x() - p2B.x()) / (p1A.x() - p2A.x());
        double bx = p1B.x() - ax * p1A.x();
        double ay = (p1B.y() - p2B.y()) / (p1A.y() - p2A.y());
        double by = p1B.y() - ay * p1A.y();
        
        return p -> new Point(ax * p.x() + bx, ay * p.y() + by);
    }
}
