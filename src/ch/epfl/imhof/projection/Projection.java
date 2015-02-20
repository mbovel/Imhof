package ch.epfl.imhof.projection;

import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.PointGeo;

/**
 * A way to transform a point in spherical coordinates ( {@link PointGeo}) to a
 * point in cartesian coordinates ({@link Point}) and the opposite.
 * 
 * @author Matteo Besançon (245826)
 */
public interface Projection {
    /**
     * Transforms a point in spherical coordinates to a point in cartesian
     * coordinates.
     * 
     * @param point
     *            a point in spherical coordinates
     * @return a point in cartesian coordinates
     */
    public Point project(PointGeo point);

    /**
     * Transforms a point in cartesian coordinates to a point in spherical
     * coordinates.
     * 
     * @param point
     *            a point in cartesian coordinates
     * @return a point in spherical coordinates
     */
    public PointGeo inverse(Point point);
}
