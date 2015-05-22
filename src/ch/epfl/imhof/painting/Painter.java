package ch.epfl.imhof.painting;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

/**
 * Functional interface representing a way to draw a {@link Map} on a
 * {@link Canvas}.
 * 
 * @author Matthieu Bovel (250300)
 */
public interface Painter {
    /**
     * Draws the given {@link Map} on the given {@link Canvas}.
     * 
     * @param map
     *            the {@link Map} to draw
     * @param canvas
     *            the {@link Canvas} to draw on
     */
    void drawMap(Map map, Canvas canvas);
    
    /**
     * Returns a new <code>Painter</code> that draws only the element of this
     * painter's {@link Map} that pass the given {@link Predicate}.
     * 
     * @param filter
     *            a {@link Predicate} to use to test elements in the {@link Map}
     * @return the new <code>Painter</code>
     */
    default Painter when(Predicate<Attributed<?>> filter) {
        return (map, canvas) -> {
            List<Attributed<Polygon>> filteredPolygons = map
                .polygons()
                .stream()
                .filter(filter)
                .collect(Collectors.toList());
            
            List<Attributed<PolyLine>> filteredPolyLines = map
                .polyLines()
                .stream()
                .filter(filter)
                .collect(Collectors.toList());
            
            Map filteredMap = new Map(filteredPolyLines, filteredPolygons);
            
            drawMap(filteredMap, canvas);
        };
    }
    
    /**
     * Combines this <code>Painter</code> with another such that this one is
     * applied after (above) the other.
     * 
     * @param that
     *            the <code>Painter</code> to draw under this one
     * @return the combined <code>Painter</code>
     */
    default Painter above(Painter that) {
        return (map, canvas) -> {
            that.drawMap(map, canvas);
            drawMap(map, canvas);
        };
    }
    
    /**
     * Returns a new <code>Painter</code> where all elements of this painter's
     * are drawn in “layer order”. That means that the elements who have smaller
     * layer number are drawn under the elements that have bigger layer number.
     * 
     * @return the new <code>Painter</code>
     */
    default Painter layered() {
        return (map, canvas) -> {
            for (int i = -5; i != 6; ++i) {
                Painter layerPainter = when(Filters.onLayer(i));
                layerPainter.drawMap(map, canvas);
            }
        };
    }
    
    /**
     * Returns an empty <code>Painter</code>: a painter that draws nothing.
     * 
     * @return an empty <code>Painter</code>.
     */
    static Painter empty() {
        return (map, canvas) -> {
        };
    }
    
    /**
     * Returns a new <code>Painter</code> that draws all {@link Polygon
     * Polygons} of the its {@link Map} with the given background color.
     * 
     * @param color
     *            the background {@link Color} to use
     * @return the new <code>Painter</code>
     */
    static Painter polygon(Color color) {
        return (map, canvas) -> {
            for (Attributed<Polygon> polygon : map.polygons()) {
                canvas.drawPolygon(polygon.value(), color);
            }
        };
    }
    
    /**
     * Returns a new <code>Painter</code> that draws all {@link PolyLine
     * PolyLines} of its {@link Map} with the given line style.
     * 
     * @param style
     *            the {@link LineStyle} to use
     * @return the new <code>Painter</code>
     */
    static Painter line(LineStyle style) {
        return (map, canvas) -> {
            for (Attributed<PolyLine> polyline : map.polyLines()) {
                canvas.drawPolyLine(polyline.value(), style);
            }
        };
    }
    
    /**
     * Returns a new <code>Painter</code> that draws all {@link PolyLine
     * PolyLines} of its {@link Map} with the given width, {@link Color},
     * {@link LineStyle.LineCap}, {@link LineStyle.LineJoin} and dashing
     * pattern.
     * 
     * @param width
     *            width in pixels to use to draw {@link PolyLine PolyLines}
     * @param color
     *            {@link Color} to use to draw {@link PolyLine PolyLines}
     * @param cap
     *            {@link LineStyle.LineCap} to use to draw {@link PolyLine
     *            PolyLines}
     * @param join
     *            {@link LineStyle.LineJoin} to use to draw {@link PolyLine
     *            PolyLines}
     * @param dashingPattern
     *            dashing pattern (<code>float[]</code>) to use to draw
     *            {@link PolyLine PolyLines}
     * @return the new <code>Painter</code>
     */
    static Painter line(float width, Color color, LineStyle.LineCap cap,
            LineStyle.LineJoin join, float[] dashingPattern) {
        LineStyle style = new LineStyle(width, color, cap, join, dashingPattern);
        return line(style);
    }
    
    /**
     * Returns a new <code>Painter</code> that draws all {@link PolyLine
     * PolyLines} of its {@link Map} with the given width and {@link Color}.
     * 
     * @param width
     *            width in pixels to use to draw {@link PolyLine PolyLines}
     * @param color
     *            {@link Color} to use to draw {@link PolyLine PolyLines}
     * @return the new <code>Painter</code>
     */
    static Painter line(float width, Color color) {
        LineStyle style = new LineStyle(width, color);
        return line(style);
    }
    
    /**
     * Returns a new <code>Painter</code> that draws all {@link Polygon
     * Polygons} borders of its {@link Map} with the given line style.
     * 
     * @param style
     *            the {@link LineStyle} to use to draw the {@link Polygon
     *            Polygons} borders
     * @return the new <code>Painter</code>
     */
    static Painter outline(LineStyle style) {
        return (map, canvas) -> {
            for (Attributed<Polygon> polygon : map.polygons()) {
                canvas.drawPolyLine(polygon.value().shell(), style);
                
                for (PolyLine hole : polygon.value().holes()) {
                    canvas.drawPolyLine(hole, style);
                }
                
            }
        };
    }
    
    /**
     * Returns a new <code>Painter</code> that draws all {@link Polygon
     * Polygons} borders of its {@link Map} with the given width, {@link Color},
     * {@link LineStyle.LineCap}, {@link LineStyle.LineJoin} and dashing
     * pattern.
     * 
     * @param width
     *            width in pixels to use to draw {@link Polygon Polygons}
     * @param color
     *            {@link Color} to use to draw {@link Polygon Polygons}
     * @param cap
     *            {@link LineStyle.LineCap} to use to draw {@link Polygon
     *            Polygons}
     * @param join
     *            {@link LineStyle.LineJoin} to use to draw {@link PPolygon
     *            Polygons}
     * @param dashingPattern
     *            dashing pattern (<code>float[]</code>) to use to draw
     *            {@link Polygon Polygons}
     * @return the new <code>Painter</code>
     */
    static Painter outline(float width, Color color, LineStyle.LineCap cap,
            LineStyle.LineJoin join, float[] dashingPattern) {
        LineStyle style = new LineStyle(width, color, cap, join, dashingPattern);
        return outline(style);
    }
    
    /**
     * Returns a new <code>Painter</code> that draws all {@link Polygon
     * Polygons} borders of its {@link Map} with the given width and {@link Color}.
     * 
     * @param width
     *            width in pixels to use to draw {@link Polygon Polygons}
     * @param color
     *            {@link Color} to use to draw {@link Polygon Polygons}
     */
    static Painter outline(float width, Color color) {
        LineStyle style = new LineStyle(width, color);
        return outline(style);
    }
}
