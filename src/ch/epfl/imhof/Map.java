
package ch.epfl.imhof;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

/**
 * 
 * 
 * @author Matthieu Bovel (250300)
 */
public final class Map {
    private final List<Attributed<PolyLine>> polyLines;
    private final List<Attributed<Polygon>> polygons;

    public Map(List<Attributed<PolyLine>> polyLines,
            List<Attributed<Polygon>> polygons) {
        this.polyLines = Collections
                .unmodifiableList(new ArrayList<>(polyLines));
        this.polygons = Collections.unmodifiableList(new ArrayList<>(polygons));
    }

    public List<Attributed<PolyLine>> polyLines() {
        return polyLines;
    }

    public List<Attributed<Polygon>> polygons() {
        return polygons;
    }

    static public class Builder {
        private List<Attributed<PolyLine>> polyLines = new ArrayList<>();
        private List<Attributed<Polygon>> polygons = new ArrayList<>();

        public void addPolyLine(Attributed<PolyLine> polyLine) {
            this.polyLines.add(polyLine);
        }

        public void addPolygon(Attributed<Polygon> polygon) {
            this.polygons.add(polygon);
        }

        public Map build() {
            return new Map(polyLines, polygons);
        }
    }
}
