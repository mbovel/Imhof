package ch.epfl.imhof.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Matthieu Bovel (250300)
 *
 */
public final class Polygon {
    private final ClosedPolyLine shell;
    private final List<ClosedPolyLine> holes;

    Polygon(ClosedPolyLine shell, List<ClosedPolyLine> holes) {
        this.shell = shell;
        this.holes = Collections.unmodifiableList(new ArrayList<ClosedPolyLine>(holes));
    }

    Polygon(ClosedPolyLine shell) {
        this(shell, new ArrayList<ClosedPolyLine>());
    }
    
    public ClosedPolyLine shell() {
        return this.shell;
    }
    
    public List<ClosedPolyLine> holes() {
        return this.holes;
    }
}
