package ch.epfl.imhof.osm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.testUtils.OurTestsUtils;

public class OurOSMMapReaderTest {
    private static final double DELTA = 0.000001;
    
    @Test(expected = OSMMapReader.OSMMissingAttributeException.class)
    public void missingIDThrowsException() throws IOException, SAXException {
        OurTestsUtils.readOSMFile("test/data/nodeMissingId.osm");
    }
    
    // Could add missingLonThrowsException
    
    // Could add missingLatThrowsException
    
    @Test
    public void wayIsCorrectlyParsed() throws IOException, SAXException {
        OSMMap map = OurTestsUtils.readOSMFile("test/data/simpleOpenWay.osm");
        OSMWay way = map.ways().get(0);
        
        assertEquals(3, way.id());
        assertEquals("unclassified", way.attributeValue("highway"));
        
        checkNode(way.nodes().get(0), 1, 47.0, 7.0);
        checkNode(way.nodes().get(1), 2, 47.0, 6.0);
    }
    
    @Test
    public void relationIsCorrectlyParsed() throws IOException, SAXException {
        OSMMap map = OurTestsUtils.readOSMFile("test/data/simpleRelation.osm");
        OSMRelation relation = map.relations().get(0);
        
        assertEquals(11, relation.id());
        assertEquals("multipolygon", relation.attributeValue("type"));
        
        OSMWay way9 = (OSMWay) relation.members().get(0).member();
        OSMWay way10 = (OSMWay) relation.members().get(1).member();
        
        assertTrue(way9.isClosed());
        assertEquals(1012362340L, way9.id());
        checkNode(way9.nodes().get(0), 1, 47, 7);
        checkNode(way9.nodes().get(1), 2, 47, 6);
        checkNode(way9.nodes().get(2), 3, 46, 6);
        checkNode(way9.nodes().get(3), 4, 46, 7);
        
        assertTrue(way10.isClosed());
        assertEquals(1012362341L, way10.id());
        checkNode(way10.nodes().get(0), 5, 46.8, 6.8);
        checkNode(way10.nodes().get(1), 6, 46.8, 6.2);
        checkNode(way10.nodes().get(2), 7, 46.2, 6.2);
        checkNode(way10.nodes().get(3), 8, 46.2, 6.8);
    }
    
    @Test
    public void lausanneIsCorrectlyParsed() throws IOException, SAXException {
        checkListsSizes("test/data/big/lausanne.osm", 139901, 3704);
    }
    
    @Test
    public void lausanneGzIsCorrectlyParsed() throws IOException, SAXException {
        checkListsSizes("test/data/big/lausanne.osm.gz", 139901, 3704);
    }
    
    @Test
    public void berneIsCorrectlyParsed() throws IOException, SAXException {
        checkListsSizes("test/data/big/berne.osm", 161980, 2436);
    }
    
    @Test
    public void berneGzIsCorrectlyParsed() throws IOException, SAXException {
        checkListsSizes("test/data/big/berne.osm.gz", 161980, 2436);
    }
    
    @Test
    public void interlakIsCorrectlyParsed() throws IOException, SAXException {
        checkListsSizes("test/data/big/interlaken.osm", 77946, 973);
    }
    
    @Test
    public void interlakGzIsCorrectlyParsed() throws IOException, SAXException {
        checkListsSizes("test/data/big/interlaken.osm.gz", 77946, 973);
    }
    
    private static void checkListsSizes(final String fileName,
            int exceptedWaysN, int exceptedRelsN) throws IOException,
            SAXException {
        OSMMap map = OurTestsUtils.readOSMFile(fileName);
        
        assertEquals(
            "check number of OSMWays in " + fileName,
            exceptedWaysN,
            map.ways().size());
        
        assertEquals(
            "check number of OSMRelations in " + fileName,
            exceptedRelsN,
            map.relations().size());
    }
    
    private static void checkNode(final OSMNode node, final long id,
            final double latDeg, final double lonDeg) {
        assertEquals("check node's id", id, node.id());
        assertEquals("check nodes's latitude", Math.toRadians(latDeg), node
            .position()
            .latitude(), DELTA);
        assertEquals("check node's longitude", Math.toRadians(lonDeg), node
            .position()
            .longitude(), DELTA);
    }
}
