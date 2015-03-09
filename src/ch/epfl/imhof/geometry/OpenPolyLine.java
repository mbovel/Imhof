package ch.epfl.imhof.geometry;

import java.util.List;

/**
 * Class representing a connected series of line segments where the first point
 * is not connected to the last one.
 * 
 * @author Matteo Besançon (245826)
 */
public final class OpenPolyLine extends PolyLine {
    /**
     * Constructs an open polyline.
     * 
     * @param points
     *            list of point of the polyline
     */
    public OpenPolyLine(List<Point> points) {
        super(points);
    }

    /**
     * Returns <code>true</code> if the last point is connected to the first
     * one. This is always <code>false</code> for an <code>OpenPolyLine</code>.
     * 
     * @see ch.epfl.imhof.geometry.PolyLine#isClosed()
     * @return false
     */
    public boolean isClosed() {
        return false;
    }
}
