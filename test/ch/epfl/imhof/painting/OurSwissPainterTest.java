package ch.epfl.imhof.painting;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.testUtils.OurTestsUtils;

public class OurSwissPainterTest {
    @Test
    public void worksWithLausanne150dpi() throws IOException, SAXException {
        Map map = OurTestsUtils
            .readOSMFileToMap("test/data/big/lausanne.osm.gz");
        
        // La toile
        Point bl = new Point(532510, 150590);
        Point tr = new Point(539570, 155260);
        
        Java2DCanvas canvas = new Java2DCanvas(
            bl,
            tr,
            1600,
            1060,
            150,
            Color.WHITE);
        
        // Dessin de la carte et stockage dans un fichier
        SwissPainter.painter().drawMap(map, canvas);
        
        OurTestsUtils.assertImagesSame(
            "test/data/lausanne_150dpi.png",
            canvas.image());
    }
    
    @Test
    public void worksWithInterlaken72dpi() throws IOException, SAXException {
        Map map = OurTestsUtils
            .readOSMFileToMap("test/data/big/interlaken.osm.gz");
        
        // La toile
        Point bl = new Point(628590, 168210);
        Point tr = new Point(635660, 172870);
        
        Java2DCanvas canvas = new Java2DCanvas(
            bl,
            tr,
            800,
            530,
            72,
            Color.WHITE);
        
        // Dessin de la carte et stockage dans un fichier
        SwissPainter.painter().drawMap(map, canvas);
        
        OurTestsUtils.assertImagesSame(
            "test/data/interlaken_72dpi.png",
            canvas.image());
    }
}
