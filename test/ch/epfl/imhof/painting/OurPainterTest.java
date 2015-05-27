package ch.epfl.imhof.painting;

import java.io.IOException;
import java.util.function.Predicate;

import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.testUtils.OurTestsUtils;

public class OurPainterTest {
    @Test
    public void worksWithExample() throws IOException, SAXException {
        Predicate<Attributed<?>> isLake = Filters.tagged("natural", "water");
        
        Painter lakesPainter = Painter.polygon(Color.BLUE).when(isLake);
        
        Predicate<Attributed<?>> isBuilding = Filters.tagged("building");
        
        Painter buildingsPainter = Painter
            .polygon(Color.BLACK)
            .when(isBuilding);
        
        Painter painter = buildingsPainter.above(lakesPainter);
        
        Map map = OurTestsUtils
            .readOSMFileToMap("test/data/big/lausanne.osm.gz");
        
        Point bl = new Point(532510, 150590);
        Point tr = new Point(539570, 155260);
        
        Java2DCanvas canvas = new Java2DCanvas(
            bl,
            tr,
            800,
            530,
            72,
            Color.WHITE);
        
        // Dessin de la carte et stockage dans un fichier
        painter.drawMap(map, canvas);
        
        OurTestsUtils.assertImagesSame(
            "test/data/big/lausanne_bb.png",
            canvas.image());
    }
}
