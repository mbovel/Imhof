package ch.epfl.imhof.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class used to represent a polygon with or without holes.
 * 
 * @author Matthieu Bovel (250300)
 */
public final class Polygon {
    private final List<ClosedPolyLine> holes;
    private final ClosedPolyLine       shell;
    
    /**
     * Constructs a new polygon with a shell and no holes.
     * 
     * @param shell
     *            shell of the polygon
     */
    public Polygon(ClosedPolyLine shell) {
        this(shell, new ArrayList<ClosedPolyLine>());
    }
    
    /**
     * Constructs a new Polygon with a shell and a List of holes.
     * 
     * @param shell
     *            shell of the polygon
     * @param holes
     *            a List of {@link ClosedPolyLine ClosedPolyLines} representing
     *            holes in the polygon
     */
    public Polygon(ClosedPolyLine shell, List<ClosedPolyLine> holes) {
        this.shell = shell;
        this.holes = Collections
            .unmodifiableList(new ArrayList<ClosedPolyLine>(holes));
    }
    
    /**
     * Returns holes in the polygon.
     * 
     * @return a List of {@link ClosedPolyLine ClosedPolyLines} representing
     *         holes in the polygon.
     */
    public List<ClosedPolyLine> holes() {
        return holes;
    }
    
    /**
     * Returns the shell of the polygon.
     * 
     * @return the shell of the polygon
     */
    public ClosedPolyLine shell() {
        return shell;
    }
    
    /**
     * Helper class that aids in the construction of {@link Polygon Polygons}.
     * 
     * @author Matthieu Bovel (250300)
     */
    public static final class Builder {
        private final List<ClosedPolyLine> holes = new ArrayList<ClosedPolyLine>();
        private final ClosedPolyLine       shell;
        
        /**
         * Constructs a new {@link Polygon} builder.
         * 
         * @param shell
         *            shell of the future polygon
         */
        public Builder(ClosedPolyLine shell) {
            this.shell = shell;
        }
        
        /**
         * Adds a new hole to the future polygon.
         * 
         * @param hole
         *            a {@link ClosedPolyLine} representing the hole to add
         */
        public void addHole(ClosedPolyLine hole) {
            holes.add(hole);
        }
        
        /**
         * Constructs the {@link Polygon}.
         * 
         * @return the new {@link Polygon}
         */
        public Polygon build() {
            return new Polygon(shell, holes);
        }
        
        /**
         * Returns the shell of the future polygon.
         * 
         * @return the shell of the future polygon
         */
        public ClosedPolyLine shell() {
            return shell;
        }
    }
}
