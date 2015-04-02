package ch.epfl.imhof;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

/**
 * Represents a projected map made of attributed geometric entities.
 * 
 * @author Matthieu Bovel (250300)
 */
public final class Map {
    private final List<Attributed<PolyLine>> polyLines;
    private final List<Attributed<Polygon>>  polygons;
    
    /**
     * Constructs a new <code>Map</code> given a list of {@link PolyLine
     * PolyLines} and a list {@link Polygon Polygons}.
     * 
     * @param polyLines
     *            <code>Map</code>'s {@link List} of {@link PolyLine}
     * @param polygons
     *            <code>Map</code>'s {@link List} of {@link Polygon}
     */
    public Map(List<Attributed<PolyLine>> polyLines,
            List<Attributed<Polygon>> polygons) {
        this.polyLines = Collections
            .unmodifiableList(new ArrayList<>(polyLines));
        this.polygons = Collections.unmodifiableList(new ArrayList<>(polygons));
    }
    
    /**
     * Returns all the {@link PolyLine PolyLines} of this <code>Map</code>.
     * 
     * @return all the {@link PolyLine PolyLines} of this <code>Map</code>
     */
    public List<Attributed<PolyLine>> polyLines() {
        return polyLines;
    }
    
    /**
     * Returns all the {@link Polygon Polygons} of this <code>Map</code>.
     * 
     * @return all the {@link Polygon Polygons} of this <code>Map</code>
     */
    public List<Attributed<Polygon>> polygons() {
        return polygons;
    }
    
    /**
     * A class that helps in the construction of a {@link Map}.
     * 
     * @author Matthieu Bovel (250300)
     *
     */
    static public class Builder {
        private final List<Attributed<PolyLine>> polyLines = new ArrayList<>();
        private final List<Attributed<Polygon>>  polygons  = new ArrayList<>();
        
        /**
         * Adds a new {@link PolyLine} to the future <code>Map</code>.
         * 
         * @param polyLine
         *            the {@link PolyLine} to add to the future <code>Map</code>
         */
        public void addPolyLine(Attributed<PolyLine> polyLine) {
            polyLines.add(polyLine);
        }
        
        /**
         * Adds a new {@link Polygon} to the future <code>Map</code>.
         * 
         * @param polygon
         *            the {@link Polygon} to add to the future <code>Map</code>
         */
        public void addPolygon(Attributed<Polygon> polygon) {
            polygons.add(polygon);
        }
        
        /**
         * Constructs a new <code>Map</code> instance using the data provided to
         * the <code>Map.Builder</code> via {@link #addPolyLine} and
         * {@link #addPolygon}.
         * 
         * @return the new <code>Map</code>
         */
        public Map build() {
            return new Map(polyLines, polygons);
        }
    }
}
