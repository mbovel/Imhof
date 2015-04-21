package ch.epfl.imhof.painting;

//import static org.junit.Assert.*;

//import java.util.function.Function;

import java.io.IOException;

import org.junit.Test;

import ch.epfl.imhof.geometry.Point;

public class OurJava2DCanvasTest {
    
    @Test
    public void imageTest() throws IOException {
        Java2DCanvas a = new Java2DCanvas(new Point(1, -1), new Point(8, 4),
                134, 500, 2, Color.GREEN);
        a.paint("test2");
    }
    
}
