package ch.epfl.imhof.geometry;

import static org.junit.Assert.*;

import java.util.function.Function;

import org.junit.Test;

public class OurPointTest {
    private static final double DELTA = 0.000001;
    
    @Test
    public void alignedCoordinateChangeWorksWithExemple() {
        Function<Point, Point> blueToRed = Point.alignedCoordinateChange(
            new Point(1, -1),
            new Point(5, 4),
            new Point(-1.5, 1),
            new Point(0, 0));
        
        Point result = blueToRed.apply(new Point(0, 0));
        
        assertEquals(3.0, result.x(), DELTA);
        assertEquals(2.0, result.y(), DELTA);
    }
}
