package ch.epfl.imhof.painting;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

/**
 * @author Matthieu Bovel (250300)
 */
public interface Painter {
    void drawMap(Map map, Canvas canvas);
    
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
    
    default Painter above(Painter that) {
        return (map, canvas) -> {
            that.drawMap(map, canvas);
            drawMap(map, canvas);
        };
    }
    
    default Painter layered() {
        return (map, canvas) -> {
            for (int i = -5; i != 6; ++i) {
                Painter layerPainter = when(Filters.onLayer(i));
                layerPainter.drawMap(map, canvas);
            }
        };
    }
    
    static Painter polygon(Color color) {
        return (map, canvas) -> {
            for (Attributed<Polygon> polygon : map.polygons()) {
                canvas.drawPolygon(polygon.value(), color);
            }
        };
    }
    
    static Painter line(LineStyle style) {
        return (map, canvas) -> {
            for (Attributed<PolyLine> polyline : map.polyLines()) {
                canvas.drawPolyLine(polyline.value(), style);
            }
        };
    }
    
    static Painter line(float width, Color color, LineStyle.LineCap cap,
            LineStyle.LineJoin join, float[] dashingPattern) {
        LineStyle style = new LineStyle(width, color, cap, join, dashingPattern);
        return line(style);
    }
    
    static Painter line(float width, Color color) {
        LineStyle style = new LineStyle(width, color);
        return line(style);
    }
    
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
    
    static Painter outline(float width, Color color, LineStyle.LineCap cap,
            LineStyle.LineJoin join, float[] dashingPattern) {
        LineStyle style = new LineStyle(width, color, cap, join, dashingPattern);
        return outline(style);
    }
    
    static Painter outline(float width, Color color) {
        LineStyle style = new LineStyle(width, color);
        return outline(style);
    }
}
