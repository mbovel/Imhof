package ch.epfl.imhof.dem;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Vector3d;

/**
 * @author Matthieu Bovel (250300)
 */
public interface DigitalElevationModel extends AutoCloseable {
    Vector3d normalAt(PointGeo point) throws IllegalArgumentException;
}
