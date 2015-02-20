package ch.epfl.imhof.projection;

// How java import works
// http://stackoverflow.com/a/12620773
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.PointGeo;

public interface Projection {
    public Point project(PointGeo point);

    public PointGeo inverse(Point point);
}
