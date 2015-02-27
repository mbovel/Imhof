package ch.epfl.imhof.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Basic abstract class to represent a connected series of line segments. It can
 * either be closed ({@link ClosedPolyLine}, meaning that the last point is
 * connected with the first one) or open ({@link OpenPolyLine}).
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Polygonal_chain">Polygonal chain,
 *      Wikipedia</a>
 * @author Matteo Besançon (245826)
 * @author Matthieu Bovel (250300)
 *
 */
public abstract class PolyLine {
    protected final List<Point> points;
    
    public final static class Builder {
        private List<Point> points;
        
        public Builder() {
            this.points = new ArrayList<Point>();
        }
        
        public void addPoint(Point p) {
            this.points.add(p);
        }
        
        public OpenPolyLine buildOpen() {
            return new OpenPolyLine(this.points);
        }
        
        public ClosedPolyLine buildClosed() {
            return new ClosedPolyLine(this.points);
        }
    }

    /**
     * Constructs a {@link PolyLine} given a list of points.
     * 
     * @param points
     *            list of all points in the polyLine
     * 
     * @throws IllegalArgumentException
     *             if points is empty
     */

    public PolyLine(List<Point> points) throws IllegalArgumentException {
        if (points.isEmpty()) {
            throw new IllegalArgumentException("Polyline size must not be null");
        }
        
        this.points = Collections.unmodifiableList(new ArrayList<Point>(points));
    }

    /**
     * @return true if the PolyLine is closed
     */
    public abstract boolean isClosed();

    /**
     * @return the List of points
     */
    public List<Point> points() {
        return points;
    }

    /**
     * @return the first point of the list
     */
    public Point firstPoint() {
        return points.get(0);
    }

}
