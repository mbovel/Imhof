package ch.epfl.imhof.osm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;
import ch.epfl.imhof.projection.EquirectangularProjection;
import ch.epfl.imhof.projection.Projection;
import ch.epfl.imhof.testUtils.OurTestsUtils;

public class OurOSMToGeoTransformerTest {
    private static final double DELTA = 0.000001;
    
    @Test
    public void worksWithSimpleOpenWay() throws IOException, SAXException {
        Map map = transform("test/data/simpleOpenWay.osm");
        
        assertTrue(map.polygons().isEmpty());
        
        Attributed<PolyLine> result = map.polyLines().get(0);
        
        checkPolyLine(
            result.value(),
            new Point(47.0, 7.0),
            new Point(47.0, 6.0));
        
        assertEquals("unclassified", result.attributeValue("highway"));
    }
    
    /**
     * That's wrong according to official OpenStreetMap Documentation: no way
     * with tag highway should be transformed to
     * 
     * @see <a
     *      href="http://wiki.openstreetmap.org/wiki/Key:highway">Key:highway,
     *      OpenStreetMap Wiki</a>
     * @throws IOException
     * @throws SAXException
     */
    @Test
    public void worksWithSimpleClosedWay() throws IOException, SAXException {
        Map map = transform("test/data/simpleClosedWay.osm");
        
        assertTrue(map.polygons().isEmpty());
        
        Attributed<PolyLine> result = map.polyLines().get(0);
        
        checkPolyLine(
            result.value(),
            new Point(47.0, 7.0),
            new Point(47.0, 6.0));
        
        assertEquals("service", result.attributeValue("highway"));
    }
    
    @Test
    public void worksWithSimpleArea() throws IOException, SAXException {
        Map map = transform("test/data/simpleArea.osm");
        
        assertTrue(map.polyLines().isEmpty());
        
        Attributed<Polygon> result = map.polygons().get(0);
        
        checkPolyLine(result.value().shell(), new Point(47.0, 7.0), new Point(
            47.0,
            6.0));
        
        assertTrue(result.value().holes().isEmpty());
        
        assertEquals("park", result.attributeValue("leisure"));
    }
    
    @Test
    public void worksWithLausanne() throws IOException, SAXException {
        checkListsSizes("test/data/big/lausanne.osm", 80165, 47975);
    }
    
    @Test
    public void worksWithBerne() throws IOException, SAXException {
        checkListsSizes("test/data/big/berne.osm", 87389, 68369);
    }
    
    @Test
    public void worksWithInterlaken() throws IOException, SAXException {
        checkListsSizes("test/data/big/interlaken.osm", 56336, 17903);
    }
    
    @Test
    public void worksWithSimpleRelation() throws IOException, SAXException {
        Map map = transform("test/data/simpleRelation.osm");
        
        assertTrue(map.polyLines().isEmpty());
        
        Attributed<Polygon> result = map.polygons().get(0);
        
        checkPolyLine(result.value().shell(), new Point(47.0, 7.0), new Point(
            47.0,
            6.0), new Point(46.0, 6.0), new Point(46.0, 7.0));
        
        PolyLine hole = result.value().holes().get(0);
        
        checkPolyLine(
            hole,
            new Point(46.8, 6.8),
            new Point(46.8, 6.2),
            new Point(46.2, 6.2),
            new Point(46.2, 6.8));
        
        assertEquals("house", result.attributeValue("building"));
    }
    
    // Following tests are inspired by
    // http://wiki.openstreetmap.org/wiki/Relation:multipolygon#Examples
    
    @Test
    public void worksWithOneOuterAndOneInnerRing() throws IOException,
            SAXException {
        Map map = transform("test/data/oneOuterAndOneInnerRing.osm");
        
        assertTrue(map.polyLines().isEmpty());
        
        Attributed<Polygon> result = map.polygons().get(0);
        
        checkPolyLine(
            result.value().shell(),
            new Point(46.276685, 6.979780),
            new Point(46.273352, 6.979394),
            new Point(46.274772, 6.985241),
            new Point(46.278053, 6.984629));
        
        PolyLine hole = result.value().holes().get(0);
        
        checkPolyLine(hole, new Point(46.276359, 6.982522), new Point(
            46.275953,
            6.982747), new Point(46.276129, 6.983385), new Point(
            46.276531,
            6.983122));
        
        assertEquals("houseboat", result.attributeValue("building"));
    }
    
    @Test
    public void worksWithOneOuterAndTwoInnerRings() throws IOException,
            SAXException {
        Map map = transform("test/data/oneOuterAndTwoInnerRings.osm");
        
        assertTrue(map.polyLines().isEmpty());
        
        Attributed<Polygon> result = map.polygons().get(0);
        
        checkPolyLine(
            result.value().shell(),
            new Point(46.280544, 6.984837),
            new Point(46.278205, 6.985813),
            new Point(46.280511, 6.991918),
            new Point(46.282923, 6.990942));
        
        checkPolyLine(result.value().holes().get(0), new Point(
            46.280424,
            6.985401), new Point(46.280014, 6.985632), new Point(
            46.280320,
            6.986426), new Point(46.280759, 6.986276));
        
        checkPolyLine(result.value().holes().get(1), new Point(
            46.281373,
            6.989505), new Point(46.280963, 6.989864), new Point(
            46.281306,
            6.990733), new Point(46.281942, 6.990486));
        
        assertEquals("houseboat", result.attributeValue("building"));
    }
    
    @Test
    public void worksWithMultipleWaysFormingARing() throws IOException,
            SAXException {
        Map map = transform("test/data/multipleWaysFormingARing.osm");
        
        assertTrue(map.polyLines().isEmpty());
        
        Attributed<Polygon> result = map.polygons().get(0);
        
        checkPolyLine(
            result.value().shell(),
            new Point(46.280544, 6.984837),
            new Point(46.278205, 6.985813),
            new Point(46.280511, 6.991918),
            new Point(46.282923, 6.990942));
        
        checkPolyLine(result.value().holes().get(0), new Point(
            46.280424,
            6.985401), new Point(46.280014, 6.985632), new Point(
            46.280320,
            6.986426), new Point(46.280759, 6.986276));
        
        assertEquals("houseboat", result.attributeValue("building"));
    }
    
    @Test
    public void worksWithTwoDisjunctOuterRings() throws IOException,
            SAXException {
        Map map = transform("test/data/twoDisjunctOuterRings.osm");
        
        assertTrue(map.polyLines().isEmpty());
        
        Attributed<Polygon> result = map.polygons().get(0);
        
        checkPolyLine(map.polygons().get(0).value().shell(), new Point(
            46.280424,
            6.985401), new Point(46.280014, 6.985632), new Point(
            46.280320,
            6.986426), new Point(46.280759, 6.986276));
        
        checkPolyLine(map.polygons().get(1).value().shell(), new Point(
            46.281373,
            6.989505), new Point(46.280963, 6.989864), new Point(
            46.281306,
            6.990733), new Point(46.281942, 6.990486));
        
        assertEquals("houseboat", result.attributeValue("building"));
    }
    
    // TODO: Two disjunct outer rings and multiple ways forming a ring
    
    // TODO: Island within a hole
    
    // TODO: Touching inner rings
    
    // TODO: Unclosed polygons
    
    // TODO: 2 touching rings on single points
    
    // TODO: Multipolygon in "8" shape with point at intersection
    
    private static Map transform(String path) throws IOException, SAXException {
        OSMMap map = OurTestsUtils.readOSMFile(path);
        Projection proj = new EquirectangularProjection();
        return new OSMToGeoTransformer(proj).transform(map);
    }
    
    private static void checkListsSizes(String fileName, int polyLinesN,
            int polygonsN) throws IOException, SAXException {
        Map map = transform(fileName);
        
        assertEquals("number of polylines in " + fileName, polyLinesN, map
            .polyLines()
            .size());
        
        assertEquals("number of polygons in " + fileName, polygonsN, map
            .polygons()
            .size());
    }
    
    private static void checkPolyLine(PolyLine line, Point... exceptedArr) {
        List<Point> excepted = Arrays.stream(exceptedArr).map(p -> {
            return new Point(Math.toRadians(p.y()), Math.toRadians(p.x()));
        }).collect(Collectors.toList());
        
        List<Point> actual = new ArrayList<Point>(line.points());
        
        excepted.sort(new pointsSorter());
        actual.sort(new pointsSorter());
        
        assertEquals(excepted.size(), actual.size());
        
        for (int i = 0; i != excepted.size(); ++i) {
            assertEquals(actual.get(i).x(), actual.get(i).x(), DELTA);
            assertEquals(actual.get(i).y(), actual.get(i).y(), DELTA);
        }
        
    }
    
    private static class pointsSorter implements Comparator<Point> {
        @Override
        public int compare(Point a, Point b) {
            if (a.x() == b.x()) {
                return new Double(a.y()).compareTo(b.y());
            }
            else {
                return new Double(a.x()).compareTo(b.x());
            }
        }
    }
}
