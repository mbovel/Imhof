package ch.epfl.imhof.osm;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class OSMMapReaderTest {
    private static final double DELTA = 0.000001;

    private static void checkNode(OSMNode node, long id, double latDeg,
            double lonDeg) {
        assertEquals(node.id(), id);
        assertEquals(node.position().latitude(), Math.toRadians(latDeg), DELTA);
        assertEquals(node.position().longitude(), Math.toRadians(lonDeg), DELTA);
    }

    @Test
    public void nodeIsCorrectlyParsed() throws IOException, SAXException {
        OSMMap.Builder mapBuilder = OSMMapReader.readOSMFileToBuilder(
                "test/ch/epfl/imhof/osm/data/node_ok.xml", false);
        checkNode(mapBuilder.nodeForId(1), 1, 47.0, 7.0);
    }

    @Test(expected = OSMMissingAttributeException.class)
    public void missingIDThrowsException() throws IOException, SAXException {
        OSMMapReader.readOSMFileToBuilder(
                "test/ch/epfl/imhof/osm/data/node_missing_id.xml", false);
    }

    // Could add missingLonThrowsException

    // Could add missingLatThrowsException

    @Test
    public void wayIsCorrectlyParsed() throws IOException, SAXException {
        OSMMap.Builder mapBuilder = OSMMapReader.readOSMFileToBuilder(
                "test/ch/epfl/imhof/osm/data/way_ok.xml", false);

        OSMWay way = mapBuilder.wayForId(3);

        assertEquals(way.id(), 3);
        assertEquals(way.attributeValue("highway"), "unclassified");
        
        checkNode(way.firstNode(), 1, 47.0, 7.0);
        checkNode(way.lastNode(), 2, 47.0, 6.0);
    }
    
    @Test
    public void relationIsCorrectlyParsed() throws IOException, SAXException {
        OSMMap.Builder mapBuilder = OSMMapReader.readOSMFileToBuilder(
                "test/ch/epfl/imhof/osm/data/relation_ok.xml", false);

        OSMRelation relation = mapBuilder.relationForId(11);

        assertEquals(relation.id(), 11);
        assertEquals(relation.attributeValue("type"), "multipolygon");
        
        OSMWay aWay = (OSMWay)relation.members().get(0).member();
        
        assertEquals(aWay.id(), 9);
    }
}
