package ch.epfl.imhof.dem;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Vector3d;

/**
 * Represents a digital elevation model.
 * 
 * @author Matthieu Bovel (250300)
 */
public interface DigitalElevationModel extends AutoCloseable {
    /**
     * Returns a normal vector of a given point of the elevation model.
     * 
     * @param point
     *            of the normal vector wanted
     * 
     * @return a normal vector of a given point of the elevation model
     * 
     * @throws IllegalArgumentException
     *             if the point is not contained in the digital elevation model
     */
    Vector3d normalAt(PointGeo point) throws IllegalArgumentException;
}
