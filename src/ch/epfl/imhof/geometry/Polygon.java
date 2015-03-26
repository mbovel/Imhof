package ch.epfl.imhof.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class used to represent a polygon with holes.
 * 
 * @author Matthieu Bovel (250300)
 */
public final class Polygon {
    private final ClosedPolyLine shell;
    private final List<ClosedPolyLine> holes;

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
     * Constructs a new polygon with a shell and no holes.
     * 
     * @param shell
     *            shell of the polygon
     */
    public Polygon(ClosedPolyLine shell) {
        this(shell, new ArrayList<ClosedPolyLine>());
    }

    /**
     * Returns the shell of the polygon.
     * 
     * @return the shell of the polygon
     */
    public ClosedPolyLine shell() {
        return this.shell;
    }

    /**
     * Returns holes in the polygon.
     * 
     * @return a List of {@link ClosedPolyLine ClosedPolyLines} representing
     *         holes in the polygon.
     */
    public List<ClosedPolyLine> holes() {
        return this.holes;
    }
    
    public static final class Builder {
        private List<ClosedPolyLine> holes;
        private final ClosedPolyLine shell;
        
        public Builder(ClosedPolyLine shell) {
            this.shell = shell;
        }
        
        public void addHole(ClosedPolyLine hole) {
            this.holes.add(hole);
        }
        
        public ClosedPolyLine shell() {
            return shell;
        }

        public Polygon build() {
            return new Polygon(shell, holes);
        }
        
    }
}
