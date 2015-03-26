package ch.epfl.imhof.osm;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.Map;
import ch.epfl.imhof.projection.CH1903Projection;
import ch.epfl.imhof.projection.Projection;

public class OSMToGeoTransformerTest {
    @Test
    public void worksWithSimpleRelation() throws IOException, SAXException {
        Map map = transform("test/data/simpleRelation.osm");
        
    }
    
    private static Map transform(String path) throws IOException, SAXException {
        OSMMap map = OSMMapReaderTest.readOSMFile(path);
        Projection proj = new CH1903Projection();
        return new OSMToGeoTransformer(proj).transform(map);
    }
}
