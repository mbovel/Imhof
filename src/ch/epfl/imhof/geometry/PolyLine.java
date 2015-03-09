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

    /**
     * Constructs a {@link PolyLine} given a list of points.
     * 
     * @param points
     *            list of all points in the PolyLine
     * 
     * @throws IllegalArgumentException
     *             if points List is empty
     */

    public PolyLine(List<Point> points) throws IllegalArgumentException {
        if (points.isEmpty()) {
            throw new IllegalArgumentException("Points list cannot be empty");
        }

        this.points = Collections
                .unmodifiableList(new ArrayList<Point>(points));
    }

    /**
     * Returns <code>true</code> if the last point is connected to the first
     * one. It is <code>true</code> in the case of a ({@link ClosedPolyLine} or
     * <code>false</code> in the case of a ( {@link OpenPolyLine}).
     * 
     * @return false
     */
    public abstract boolean isClosed();

    /**
     * Returns the list of points of this polyline.
     * 
     * @return the list of points
     */
    public List<Point> points() {
        return points;
    }

    /**
     * Return the first point of this polyline.
     * 
     * @return the first point of this polyline.
     */
    public Point firstPoint() {
        return points.get(0);
    }

    /**
     * Helper class that aids in the construction of {@link PolyLine PolyLines}.
     * 
     * @author Matthieu Bovel (250300)
     */
    public final static class Builder {
        private List<Point> points;

        /**
         * Construct a new empty an empty {@link PolyLine} builder.
         */
        public Builder() {
            this.points = new ArrayList<Point>();
        }

        /**
         * Adds a new point to the future the future {@link PolyLine}.
         * 
         * @param p
         *            the point to add
         * @return a {@link ClosedPolyLine}
         */
        public Builder addPoint(Point p) {
            this.points.add(p);
            return this;
        }

        /**
         * Constructs a new {@link OpenPolyLine} from data added with
         * {@link #addPoint}.
         * 
         * @return a {@link OpenPolyLine}
         */
        public OpenPolyLine buildOpen() {
            return new OpenPolyLine(this.points);
        }

        /**
         * Constructs a new {@link ClosedPolyLine} from data added with
         * {@link #addPoint}.
         * 
         * @return a {@link ClosedPolyLine}
         */
        public ClosedPolyLine buildClosed() {
            return new ClosedPolyLine(this.points);
        }
    }

}
