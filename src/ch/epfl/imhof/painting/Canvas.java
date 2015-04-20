package ch.epfl.imhof.painting;

import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

/**
 * Represents an object on which it is possible to draw {@link PolyLine
 * PolyLines} styled with a {@link LineStyle} and {@link Polygon Polygons} of a
 * given {@link Color}.
 * 
 * @author Matteo Besançon (245826)
 * @author Matthieu Bovel (250300)
 */
public interface Canvas {
    /**
     * @param polyline
     * @param style
     */
    void drawPolyLine(PolyLine toDraw, LineStyle style);
    
    /**
     * @param toDraw
     * @param color
     */
    void drawPolygon(Polygon toDraw, Color color);
}
