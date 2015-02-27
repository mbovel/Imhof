package ch.epfl.imhof.geometry;

import java.util.List;

/**
 * @author Matteo Besançon (245826)
 *
 */
public final class ClosedPolyLine extends PolyLine {

    /**
     * construct an open polyline by calling the constructor from the super
     * class {@link PolyLine}
     * 
     * @param points
     *            list of points of the closedpolyline
     */
    public ClosedPolyLine(List<Point> points) {
        super(points);
    }

    /**
     * @see ch.epfl.imhof.geometry.PolyLine#isClosed()
     * @return return false because the Polyline isn't closed
     */
    public boolean isClosed() {
        return true;
    }

    /**
     * @return the area of a closed polyline by using an external point ( in
     *         this case (0,0)) and the sined area formed by the triangles
     */
    public double area() {
        double area = 0;

        for (int i = 0; i != points.size(); ++i) {
            Point p1 = points.get(i);
            Point p2 = points.get(indiceGen(i + 1));

            area += (p1.x() * p2.y() - p2.x() * p1.y());
        }

        return area;
    }

    /**
     * @param p
     *            the point we want to know if it is contained by the polyline
     * @return true if the polyline contains the point p
     */
    public boolean containsPoint(Point p) {
        int indice = 0;
        for (int i = 0; i != points.size(); ++i) {
            Point p1 = points.get(i);
            Point p2 = points.get(indiceGen(i + 1));

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
     * method used to know if a point is right or left from a segment (P1,P2)
     * 
     * @param p
     *            the point we want to know its emplacement
     * @param p1
     *            the first point of the segment
     * @param p2
     *            the second point of the segment
     * @return true if the point is left from (p1,p2)
     */
    private boolean isLeft(Point p, Point p1, Point p2) {

        return ((p1.x() - p.x()) * (p2.y() - p.y()) > (p2.x() - p.x())
                * (p1.y() - p.y()));
    }

    /**
     * @param indice
     *            is the indice of the vertex in a polyline polyline with n+1
     *            vertices
     * @return the indice of the vertex ( if indice is equal to n+1 it return 0)
     */
    private int indiceGen(int indice) {

        return (java.lang.Math.floorMod(indice, points.size()));
    }
}
