package ch.epfl.imhof.projection;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Point;

/**
 * A way to transform a point in spherical coordinates ({@link PointGeo}) into a
 * point in cartesian coordinates ({@link Point}) and the opposite using a
 * simple Equirectangular projection.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Equirectangular_projection">
 *      Equirectangular projection, Wikipedia</a>
 * @author Matteo Besançon (245826)
 */
public final class EquirectangularProjection implements Projection {
    /**
     * Transforms a point in spherical coordinates to a point in cartesian
     * coordinates using a simple Equirectangular projection.
     * 
     * @param point
     *            a point in spherical coordinates
     * @return a point in cartesian coordinates
     */
    @Override
    public Point project(PointGeo point) {
        return new Point(point.longitude(), point.latitude());
    }
    
    /**
     * Transforms a point in cartesian coordinates to a point in spherical
     * coordinates using a simple Equirectangular projection.
     * 
     * @param point
     *            a point in cartesian coordinates
     * @return a point in spherical coordinates
     */
    @Override
    public PointGeo inverse(Point point) {
        return new PointGeo(point.x(), point.y());
    }
}
