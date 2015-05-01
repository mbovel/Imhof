package ch.epfl.imhof.painting;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.osm.OSMMap;
import ch.epfl.imhof.osm.OSMToGeoTransformer;
import ch.epfl.imhof.projection.CH1903Projection;
import ch.epfl.imhof.projection.Projection;
import ch.epfl.imhof.testUtils.OurTestsUtils;

public class OurRoadPainterGeneratorTest {
    public static Map readMap(String path) throws IOException, SAXException {
        OSMMap map = OurTestsUtils.readOSMFile(path);
        Projection proj = new CH1903Projection();
        return new OSMToGeoTransformer(proj).transform(map);
    }
    
    @Test
    public void worksWithExample() throws IOException, SAXException {
        
        
        Map map = readMap("test/data/big/lausanne.osm.gz");
        
        // La toile
        Point bl = new Point(532510, 150590);
        Point tr = new Point(539570, 155260);
        
        Java2DCanvas canvas = new Java2DCanvas(bl, tr, 1600, 1060, 150,
                Color.WHITE);
        
        // Dessin de la carte et stockage dans un fichier
        SwissPainter.painter().drawMap(map, canvas);
        
        ImageIO.write(canvas.image(), "png", new File("loz150dpi.png"));
    }
}
