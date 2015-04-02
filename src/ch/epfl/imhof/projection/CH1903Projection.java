package ch.epfl.imhof.projection;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Point;

/**
 * A way to transform a point in spherical coordinates ({@link PointGeo}) into a
 * point in cartesian coordinates ({@link Point}) and the opposite using a a
 * “CH1903 projection”, including WGS 84 conversion.
 * 
 * @see <a href="http://cs108.epfl.ch/p01_points.html#unnumbered-3">Projection
 *      Suisse, CS-108 course, EPFL (in french)</a>
 * @author Matthieu Bovel (250300)
 * @author Matteo Besançon (245826)
 */
public final class CH1903Projection implements Projection {
    /**
     * Transforms a point in cartesian coordinates to a point in spherical
     * coordinates using a “CH1903 projection”.
     * 
     * @param point
     *            a point in cartesian coordinates
     * @return a point in spherical coordinates
     */
    @Override
    public PointGeo inverse(Point point) {
        double lambda0, phi0, x1, y1, lambda, phi;
        
        // @formatter:off
             x1 = (point.x() - 600000.0) / 1000000.0;

             y1 = (point.y() - 200000.0) / 1000000.0;

        lambda0 =   2.6779094
                   + 4.728982 * x1
                   + 0.791484 * x1 * y1
                   + 0.1306 * x1 * Math.pow(y1, 2.0)
                   - 0.0436 * Math.pow(x1, 3.0);

           phi0 =   16.9023892
                   + 3.238272 * y1
                   - 0.270978 * Math.pow(x1, 2.0)
                   - 0.002528 * Math.pow(y1, 2.0)
                   - 0.0447 * Math.pow(x1, 2.0) * y1
                   - 0.0140 * Math.pow(y1, 3.0);

          lambda = lambda0 * (100.0 / 36.0);

             phi = phi0 * (100.0 / 36.0);
        // @formatter:on
        
        return new PointGeo(Math.toRadians(lambda), Math.toRadians(phi));
    }
    
    /**
     * Transforms a point in spherical coordinates to a point in cartesian
     * coordinates using a “CH1903 projection”.
     * 
     * @param point
     *            a point in spherical coordinates
     * @return a point in cartesian coordinates
     */
    @Override
    public Point project(PointGeo point) {
        double lambda1, phi1, x, y;
        double lambda = Math.toDegrees(point.longitude());
        double phi = Math.toDegrees(point.latitude());
        
        // Implementation of the algorithm described here:
        // http://cs108.epfl.ch/p01_points.html#unnumbered-3
        // The longitude lambda and the latitude phi are in degrees
        // @formatter:off
        lambda1 = 1 / 10000.0 * (lambda * 3600.0 - 26782.5);

           phi1 = 1 / 10000.0 * (phi * 3600.0 - 169028.66);

               x =   600072.37
                   + 211455.93 * lambda1
                   - 10938.51 * lambda1 * phi1
                   - 0.36 * lambda1 * Math.pow(phi1, 2.0)
                   - 44.54 * Math.pow(lambda1, 3.0);

               y =   200147.07
                   + 308807.95 * phi1
                   + 3745.25 * Math.pow(lambda1, 2.0)
                   + 76.63 * Math.pow(phi1, 2.0)
                   - 194.56 * Math.pow(lambda1, 2.0) * phi1
                   + 119.79 * Math.pow(phi1, 3.0);
        // @formatter:on
        
        return new Point(x, y);
    }
}
