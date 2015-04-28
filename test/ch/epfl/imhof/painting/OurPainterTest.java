package ch.epfl.imhof.painting;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.osm.OSMMap;
import ch.epfl.imhof.osm.OSMToGeoTransformer;
import ch.epfl.imhof.osm.OurOSMMapReaderTest;
import ch.epfl.imhof.projection.CH1903Projection;
import ch.epfl.imhof.projection.Projection;

public class OurPainterTest {
    public static Map readMap(String path) throws IOException, SAXException {
        OSMMap map = OurOSMMapReaderTest.readOSMFile(path);
        Projection proj = new CH1903Projection();
        return new OSMToGeoTransformer(proj).transform(map);
    }
    
    @Test
    public void worksWithExample() throws IOException, SAXException {
        Predicate<Attributed<?>> isLake = Filters.tagged("natural", "water");
        
        Painter lakesPainter = Painter.polygon(Color.BLUE).when(isLake);
        
        Predicate<Attributed<?>> isBuilding = Filters.tagged("building");
        
        Painter buildingsPainter = Painter
            .polygon(Color.BLACK)
            .when(isBuilding);
        
        Painter painter = buildingsPainter.above(lakesPainter);
        
        Map map = readMap("test/data/big/lausanne.osm.gz");
        
        // La toile
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
        
        ImageIO.write(canvas.image(), "png", new File("loz.png"));
    }
}
