package ch.epfl.imhof.geometry;

import java.util.List;

/**
 * Class representing a connected series of line segments where the first point
 * is connected to the last one.
 * 
 * @author Matteo Besançon (245826)
 */
public final class ClosedPolyLine extends PolyLine {
    /**
     * Constructs an open PolyLine by calling the constructor from the super
     * class {@link PolyLine}.
     * 
     * @param points
     *            list of points of the ClosedPolyLine
     */
    public ClosedPolyLine(List<Point> points) {
        super(points);
    }

    /**
     * Returns true if the last point is connected to the first one. This is
     * always false for a {@link ClosedPolyLine}.
     *
     * @see ch.epfl.imhof.geometry.PolyLine#isClosed()
     * @return true
     */
    public boolean isClosed() {
        return true;
    }

    /**
     * Returns the signed area.
     * <p>
     * It is positive if if and only if the vertices of the triangle are
     * organized counterclockwise.
     * 
     * @return the signed area
     */
    public double signedArea() {
        double area = 0;

        for (int i = 0; i != points.size(); ++i) {
            Point p1 = points.get(i);
            Point p2 = points.get(nextIndex(i));

            area += p1.x() * p2.y() - p2.x() * p1.y();
        }

        return area / 2;
    }

    /**
     * Returns the area.
     * <p>
     * This value is always positive.
     * 
     * @return the area
     */
    public double area() {
        return Math.abs(signedArea());
    }

    /**
     * Returns true if p is in this polyline.
     * 
     * @param p
     *            the point we want to know if it is contained by the polyline.
     * @return true if the polyline contains the point p
     */
    public boolean containsPoint(Point p) {
        int indice = 0;
        for (int i = 0; i != points.size(); ++i) {
            Point p1 = points.get(i);
            Point p2 = points.get(nextIndex(i));

            if (p1.y() <= p.y()) {
                if (p2.y() > p.y() && isLeft(p, p1, p2)) {
                    ++indice;
                }
            }

            else if (p2.y() <= p.y() && isLeft(p, p2, p1)) {
                --indice;
            }
        }
        return (indice != 0);
    }

    /**
     * Returns true if p is on the left of the segment formed by the points p1
     * and p2, false otherwise.
     * 
     * @param p
     *            the point whose we want to know the location
     * @param p1
     *            the first point of the segment
     * @param p2
     *            the second point of the segment
     * @return true if the point is left to (p1,p2)
     */
    private boolean isLeft(Point p, Point p1, Point p2) {
        return ((p1.x() - p.x()) * (p2.y() - p.y()) > (p2.x() - p.x())
                * (p1.y() - p.y()));
    }

    /**
     * Returns the index of the next point, given the valid index of a point,
     * 
     * @param index
     *            the valid index of a point
     * @return the index of the next point
     */
    private int nextIndex(int index) {
        return java.lang.Math.floorMod(index + 1, points.size());
    }
}
