package ch.epfl.imhof.osm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class OSMMapReaderTest {
    private static final double DELTA = 0.000001;
    
    @Test
    public void nodeIsCorrectlyParsed() throws IOException, SAXException {
        final OSMMap.Builder mapBuilder = readOSMFileToBuilder("test/data/node_ok.osm");
        checkNode(mapBuilder.nodeForId(1), 1, 47.0, 7.0);
    }
    
    @Test(expected = OSMMissingAttributeException.class)
    public void missingIDThrowsException() throws IOException, SAXException {
        readOSMFile("test/data/node_missing_id.osm");
    }
    
    // Could add missingLonThrowsException
    
    // Could add missingLatThrowsException
    
    @Test
    public void wayIsCorrectlyParsed() throws IOException, SAXException {
        final OSMMap.Builder mapBuilder = readOSMFileToBuilder("test/data/way_ok.osm");
        
        final OSMWay way = mapBuilder.wayForId(3);
        
        assertEquals(way.id(), 3);
        assertEquals(way.attributeValue("highway"), "unclassified");
        
        checkNode(way.firstNode(), 1, 47.0, 7.0);
        checkNode(way.lastNode(), 2, 47.0, 6.0);
    }
    
    @Test
    public void relationIsCorrectlyParsed() throws IOException, SAXException {
        final OSMMap.Builder mapBuilder = readOSMFileToBuilder("test/data/relation_ok.osm");
        
        final OSMRelation relation = mapBuilder.relationForId(11);
        
        assertEquals(relation.id(), 11);
        assertEquals(relation.attributeValue("type"), "multipolygon");
        
        final OSMWay aWay = (OSMWay) relation.members().get(0).member();
        
        assertEquals(aWay.id(), 9);
    }
    
    @Test
    public void lausanneIsCorrectlyParsed() throws IOException, SAXException {
        readOSMFile("test/data/big/lausanne.osm");
    }
    
    @Test
    public void lausanneGzIsCorrectlyParsed() throws IOException, SAXException {
        readOSMFile("test/data/big/lausanne.osm.gz");
    }
    
    @Test
    public void berneIsCorrectlyParsed() throws IOException, SAXException {
        readOSMFile("test/data/big/berne.osm");
    }
    
    @Test
    public void berneGzIsCorrectlyParsed() throws IOException, SAXException {
        readOSMFile("test/data/big/berne.osm.gz");
    }
    
    @Test
    public void interlakIsCorrectlyParsed() throws IOException, SAXException {
        readOSMFile("test/data/big/interlaken.osm");
    }
    
    @Test
    public void interlakGzIsCorrectlyParsed() throws IOException, SAXException {
        readOSMFile("test/data/big/interlaken.osm.gz");
    }
    
    private static void readOSMFile(final String fileName) throws IOException,
            SAXException {
        assumeFileExists(fileName);
        final boolean unGZip = fileExtension(fileName).equals("gz");
        OSMMapReader.readOSMFile(fileName, unGZip);
    }
    
    private static OSMMap.Builder readOSMFileToBuilder(final String fileName)
            throws IOException, SAXException {
        assumeFileExists(fileName);
        final OSMMap.Builder mapBuilder = new OSMMap.Builder();
        final boolean unGZip = fileExtension(fileName).equals("gz");
        OSMMapReader.readOSMFileToBuilder(fileName, unGZip, mapBuilder);
        return mapBuilder;
    }
    
    private static void assumeFileExists(final String fileName) {
        // Conditionally ignoring tests in JUnit 4
        // http://stackoverflow.com/a/1689309
        // Class Assume
        // http://junit.sourceforge.net/javadoc/org/junit/Assume.html
        // How do I check if a file exists in Java?
        // http://stackoverflow.com/a/1816676
        assumeTrue(fileName + " exists", new File(fileName).isFile());
    }
    
    private static String fileExtension(final String fileName) {
        // How do I get the file extension of a file in Java?
        // http://stackoverflow.com/a/16202288
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
    
    private static void checkNode(final OSMNode node, final long id,
            final double latDeg, final double lonDeg) {
        assertEquals(node.id(), id);
        assertEquals(node.position().latitude(), Math.toRadians(latDeg), DELTA);
        assertEquals(node.position().longitude(), Math.toRadians(lonDeg), DELTA);
    }
}
