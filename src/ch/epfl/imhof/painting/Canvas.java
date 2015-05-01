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
     * Draws a polyline on this canvas, given the {@link PolyLine} and a {@link LineStyle}.
     * 
     * @param toDraw the polyline to draw
     * @param style the line style to use to draw the polyline
     */
    void drawPolyLine(PolyLine toDraw, LineStyle style);
    
    /**
     * Draws a polygon on this canvas, given the {@link Polygon} and a {@link Color}.
     * 
     * @param toDraw the polygon to draw
     * @param color the color to use to draw the polygon
     */
    void drawPolygon(Polygon toDraw, Color color);
}
