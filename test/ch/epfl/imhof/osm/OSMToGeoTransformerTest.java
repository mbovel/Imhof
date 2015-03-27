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

import ch.epfl.imhof.Graph;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;
import ch.epfl.imhof.projection.EquirectangularProjection;
import ch.epfl.imhof.projection.Projection;

public class OSMToGeoTransformerTest {
    private static final double DELTA = 0.000001;
    
    @Test
    public void worksWithSimpleRelation() throws IOException, SAXException {
        Map map = transform("test/data/simpleRelation.osm");
        
        assertTrue(map.polyLines().isEmpty());
        
        Polygon result = map.polygons().get(0).value();
        
        checkPolyLine(
            result.shell(),
            new Point(47.0, 7.0),
            new Point(47.0, 6.0),
            new Point(46.0, 6.0),
            new Point(46.0, 7.0));
        
        PolyLine hole = result.holes().get(0);
        
        checkPolyLine(
            hole,
            new Point(46.8, 6.8),
            new Point(46.8, 6.2),
            new Point(46.2, 6.2),
            new Point(46.2, 6.8));
    }
    
    private static Map transform(String path) throws IOException, SAXException {
        OSMMap map = OSMMapReaderTest.readOSMFile(path);
        Projection proj = new EquirectangularProjection();
        return new OSMToGeoTransformer(proj).transform(map);
    }
    
    private void checkPolyLine(PolyLine line, Point... exceptedArr) {
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
    
    private static void printGraph(Graph<OSMNode> g) {
        System.out.println(g + ":");
        
        for (OSMNode node : g.nodes()) {
            System.out.println("  " + node.id());
            
            for (OSMNode neighbor : g.neighborsOf(node)) {
                System.out.println("    -> " + neighbor.id());
            }
        }
    }
}
