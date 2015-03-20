package ch.epfl.imhof.osm;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class OSMMapReaderTest {
    @Test
    public void nodeIsCorrectlyParsed() throws IOException, SAXException {
        OSMMap.Builder mapBuilder = OSMMapReader.readOSMFileToBuilder("test/ch/epfl/imhof/osm/data/node.xml", false);
        
        OSMNode node = mapBuilder.nodeForId(256186062);
    }
}
