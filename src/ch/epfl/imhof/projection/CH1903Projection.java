package ch.epfl.imhof.projection;

import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.PointGeo;
import java.lang.Math;

/**
 * A way to transform a point in spherical coordinates ( {@link PointGeo}) to a
 * point in cartesian coordinates ({@link Point}) and the opposite using a a
 * “CH1903 projection”, including WGS 84 conversion.
 * 
 * @see <a href="http://cs108.epfl.ch/p01_points.html#unnumbered-3" lang="fr">
 *      Projection Suisse, CS-108 course, EPFL</a> (in french)
 * @author Matthieu Bovel (250300)
 * @author Matteo Besançon (245826)
 */
public final class CH1903Projection implements Projection {
    /**
     * Transforms a point in spherical coordinates to a point in cartesian
     * coordinates using a “CH1903 projection”.
     * 
     * @param point
     *            a point in spherical coordinates
     * @return a point in cartesian coordinates
     */
    public Point project(PointGeo point) {
        double lambda_1, phi_1, x, y;
        double lambda = Math.toDegrees(point.longitude());
        double phi = Math.toDegrees(point.latitude());

        // Implementation of the algorithm described here:
        // http://cs108.epfl.ch/p01_points.html#unnumbered-3
        // The longitude lambda and the latitude phi are in degrees
        // @formatter:off
        lambda_1 = (1 / 10000.0) * (lambda * 3600.0 - 26782.5);

           phi_1 = (1 / 10000.0) * (phi * 3600.0 - 169028.66);

               x =   600072.37
                   + 211455.93 * lambda_1
                   - 10938.51 * lambda_1 * phi_1
                   - 0.36 * lambda_1 * Math.pow(phi_1, 2.0)
                   - 44.54 * Math.pow(lambda_1, 3.0);

               y =   200147.07
                   + 308807.95 * phi_1
                   + 3745.25 * Math.pow(lambda_1, 2.0)
                   + 76.63 * Math.pow(phi_1, 2.0)
                   - 194.56 * Math.pow(lambda_1, 2.0) * phi_1
                   + 119.79 * Math.pow(phi_1, 3.0);
        // @formatter:on

        return new Point(x, y);
    }

    /**
     * Transforms a point in cartesian coordinates to a point in spherical
     * coordinates using a “CH1903 projection”.
     * 
     * @param point
     *            a point in cartesian coordinates
     * @return a point in spherical coordinates
     */
    public PointGeo inverse(Point point) {
        double lambda_0, phi_0, x_1, y_1, lambda, phi;

        // @formatter:off
             x_1 = (point.x() - 600000.0) / 1000000.0;

             y_1 = (point.y() - 200000.0) / 1000000.0;

        lambda_0 =   2.6779094
                   + 4.728982 * x_1
                   + 0.791484 * x_1 * y_1
                   + 0.1306 * x_1 * Math.pow(y_1, 2.0)
                   - 0.0436 * Math.pow(x_1, 3.0);

           phi_0 =   16.9023892
                   + 3.238272 * y_1
                   - 0.270978 * Math.pow(x_1, 2.0)
                   - 0.002528 * Math.pow(y_1, 2.0)
                   - 0.0447 * Math.pow(x_1, 2.0) * y_1
                   - 0.0140 * Math.pow(y_1, 3.0);

          lambda = lambda_0 * (100.0 / 36.0);

             phi = phi_0 * (100.0 / 36.0);
        // @formatter:on

        return new PointGeo(Math.toRadians(lambda), Math.toRadians(phi));
    }
}
