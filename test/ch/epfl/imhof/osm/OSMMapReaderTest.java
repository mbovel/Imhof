package ch.epfl.imhof.osm;

import static org.junit.Assert.*;
import static org.junit.Assume.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class OSMMapReaderTest {
    private static final double DELTA = 0.000001;
    private static final String LAUSANNE_FILE = "test/data/big/lausanne.osm";
    private static final String NODE_OK_FILE = "test/data/node_ok.osm";
    private static final String NODE_MISSING_ID_FILE = "test/data/node_missing_id.osm";
    private static final String WAY_OK_FILE = "test/data/way_ok.osm";
    private static final String RELATION_OK_FILE = "test/data/relation_ok.osm";

    private static void checkNode(OSMNode node, long id, double latDeg,
            double lonDeg) {
        assertEquals(node.id(), id);
        assertEquals(node.position().latitude(), Math.toRadians(latDeg), DELTA);
        assertEquals(node.position().longitude(), Math.toRadians(lonDeg), DELTA);
    }

    @Test
    public void nodeIsCorrectlyParsed() throws IOException, SAXException {
        OSMMap.Builder mapBuilder = OSMMapReader.readOSMFileToBuilder(
                NODE_OK_FILE, false);
        checkNode(mapBuilder.nodeForId(1), 1, 47.0, 7.0);
    }

    @Test(expected = OSMMissingAttributeException.class)
    public void missingIDThrowsException() throws IOException, SAXException {
        OSMMapReader.readOSMFileToBuilder(NODE_MISSING_ID_FILE, false);
    }

    // Could add missingLonThrowsException

    // Could add missingLatThrowsException

    @Test
    public void wayIsCorrectlyParsed() throws IOException, SAXException {
        OSMMap.Builder mapBuilder = OSMMapReader.readOSMFileToBuilder(
                WAY_OK_FILE, false);

        OSMWay way = mapBuilder.wayForId(3);

        assertEquals(way.id(), 3);
        assertEquals(way.attributeValue("highway"), "unclassified");

        checkNode(way.firstNode(), 1, 47.0, 7.0);
        checkNode(way.lastNode(), 2, 47.0, 6.0);
    }

    @Test
    public void relationIsCorrectlyParsed() throws IOException, SAXException {
        OSMMap.Builder mapBuilder = OSMMapReader.readOSMFileToBuilder(
                RELATION_OK_FILE, false);

        OSMRelation relation = mapBuilder.relationForId(11);

        assertEquals(relation.id(), 11);
        assertEquals(relation.attributeValue("type"), "multipolygon");

        OSMWay aWay = (OSMWay) relation.members().get(0).member();

        assertEquals(aWay.id(), 9);
    }

    @Test
    public void lausanneIsCorrectlyParsed() throws IOException, SAXException {
        // Class Assume
        // http://junit.sourceforge.net/javadoc/org/junit/Assume.html
        // How do I check if a file exists in Java?
        // http://stackoverflow.com/a/1816676
        assumeTrue(new File(LAUSANNE_FILE).isFile());
        OSMMapReader.readOSMFileToBuilder(LAUSANNE_FILE, false);
    }
}
