package ch.epfl.imhof;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

/**
 * Represents a Map (nothing to do with the java interface Map) with geometric
 * attributed identities.
 * 
 * @author Matthieu Bovel (250300)
 */
public final class Map {
    private final List<Attributed<PolyLine>> polyLines;
    private final List<Attributed<Polygon>>  polygons;
    
    /**
     * Constructs a new <code>Map</code> given its Polylines and Polygons.
     * 
     * @param polyLines
     *            <code>Map</code>'s {@link List} of {@link PolyLine}.
     * @param polygons
     *            <code>Map</code>'s {@link List} of {@link Polygon}.
     */
    public Map(List<Attributed<PolyLine>> polyLines,
            List<Attributed<Polygon>> polygons) {
        this.polyLines = Collections.unmodifiableList(new ArrayList<>(polyLines));
        this.polygons = Collections.unmodifiableList(new ArrayList<>(polygons));
    }
    
    /**
     * Returns all the <code>PolyLines</code> of the <code>Map</code>.
     * 
     * @return all the <code>PolyLines</code> of the <code>Map</code>.
     */
    public List<Attributed<PolyLine>> polyLines() {
        return polyLines;
    }
    
    /**
     * Returns all the <code>Polygons</code> of the <code>Map</code>.
     * 
     * @return all the <code>Polygons</code> of the <code>Map</code>.
     */
    public List<Attributed<Polygon>> polygons() {
        return polygons;
    }
    
    /**
     * A class that helps in the consctruction of a {@link Map}.
     * 
     * @author Matthieu Bovel (250300)
     *
     */
    static public class Builder {
        private List<Attributed<PolyLine>> polyLines = new ArrayList<>();
        private List<Attributed<Polygon>>  polygons  = new ArrayList<>();
        
        /**
         * Adds a new <code>PolyLine</code> to the futur <code>Map</code>.
         * 
         * @param polyLine
         *            the futur <code>Map</code>'s <code>PolyLine</code>.
         */
        public void addPolyLine(Attributed<PolyLine> polyLine) {
            this.polyLines.add(polyLine);
        }
        
        /**
         * Adds a new <code>Polygon</code> to the futur <code>Map</code>.
         * 
         * @param polygon
         *            the futur <code>Map</code>'s <code>Polygon</code>.
         */
        public void addPolygon(Attributed<Polygon> polygon) {
            this.polygons.add(polygon);
        }
        
        /**
         * Constructs a new <code>Map</code> instance using the datas provided
         * by the <code>Map.Builder</code>.
         * 
         * @return the new <code>Map</code>.
         */
        public Map build() {
            return new Map(polyLines, polygons);
        }
    }
}
