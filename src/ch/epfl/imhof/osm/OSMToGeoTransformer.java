/**
 * 
 */
package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.Graph;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.OpenPolyLine;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;
import ch.epfl.imhof.projection.Projection;

/**
 * @author Matthieu Bovel (250300)
 */
public final class OSMToGeoTransformer {
    private static Set<String> surfaceAtts  = initHashSet("aeroway", "amenity",
                                                "building", "harbour",
                                                "historic", "landuse",
                                                "leisure", "man_made",
                                                "military", "natural",
                                                "office", "place", "power",
                                                "public_transport", "shop",
                                                "sport", "tourism", "water",
                                                "waterway", "wetland");
    private static Set<String> polyLineAtts = initHashSet("bridge", "highway",
                                                "layer", "man_made", "railway",
                                                "tunnel", "waterway");
    private static Set<String> polygonAtts  = initHashSet("building",
                                                "landuse", "layer", "leisure",
                                                "natural", "waterway");
    
    private final Projection   projection;
    
    public OSMToGeoTransformer(Projection projection) {
        this.projection = projection;
    }
    
    private static Set<String> initHashSet(String... strings) {
        HashSet<String> set = new HashSet<String>();
        Arrays.stream(strings).forEach(str -> set.add(str));
        return set;
    }
    
    public Map transform(OSMMap osm) {
        Map.Builder mapBuilder = new Map.Builder();
        
        osm.ways().forEach(way -> transformWay(way, mapBuilder));
        osm.relations().forEach(rel -> transformRel(rel, mapBuilder));
        
        return mapBuilder.build();
    }
    
    public void transformWay(OSMWay way, Map.Builder mapBuilder) {
        boolean isClosed = way.isClosed();
        Attributes atts;
        List<Point> points;
        
        // If the way is closed and has needed attributes (checked by
        // #isSurface), then it is converted to a simple polygon without holes.
        if (isClosed && isSurface(way)) {
            atts = way.attributes().keepOnlyKeys(polygonAtts);
            
            if (atts.isEmpty()) {
                return;
            }
            
            points = transformNodes(way.nodes());
            
            ClosedPolyLine shell = new ClosedPolyLine(points);
            Polygon polygon = new Polygon(shell);
            
            mapBuilder.addPolygon(new Attributed<Polygon>(polygon, atts));
        }
        // If not, its converted to a PolyLine.
        else {
            atts = way.attributes().keepOnlyKeys(polyLineAtts);
            
            if (atts.isEmpty()) {
                return;
            }
            
            points = transformNodes(way.nodes());
            
            PolyLine polyline;
            
            if (isClosed) {
                polyline = new ClosedPolyLine(points);
            }
            else {
                polyline = new OpenPolyLine(points);
            }
            
            mapBuilder.addPolyLine(new Attributed<PolyLine>(polyline, atts));
        }
    }
    
    private static boolean isSurface(OSMWay way) {
        if (way.hasAttribute("area")) {
            switch (way.attributeValue("area")) {
                case "true":
                case "yes":
                case "1":
                    return true;
            }
        }
        
        return !way.attributes().keepOnlyKeys(surfaceAtts).isEmpty();
    }
    
    public void transformRel(OSMRelation rel, Map.Builder mapBuilder) {
        // Conversion of multipolygons
        // http://cs108.epfl.ch/p06_osm-to-geo.html#sec-1-2
        // Relation:multipolygon
        // http://wiki.openstreetmap.org/wiki/Relation:multipolygon
        
        // Ways in the relation are selected.
        Stream<OSMWay> ways = rel.members()
                .stream()
                .filter(member -> member.type() == OSMRelation.Member.Type.WAY)
                .map(member -> (OSMWay) member.member());
        
        // 1. Ways are separated in two sets depending on their role.
        Stream<OSMWay> innerWays = filterByRole(ways, "inner");
        Stream<OSMWay> outerWays = filterByRole(ways, "outer");
        
        // 2. Ways in each of these sets are grouped depending on their first
        // and last nodes to make PolyLines that we'll call "rings". Outer rings
        // are sorted by area.
        List<ClosedPolyLine> innerRings = makeRings(innerWays);
        List<ClosedPolyLine> outerRings = sortByArea(makeRings(outerWays));
        
        // Return if there is no outer ring
        if (outerRings.isEmpty()) {
            return;
        }
        
        // Prepare a polygon builder for each outer ring.
        List<Polygon.Builder> polygonsBuilders = outerRings.stream()
                .map(outerRing -> new Polygon.Builder(outerRing))
                .collect(Collectors.toList());
        
        // 3. Each inner ring is inserted in the smallest possible outer ring in
        // which it is contained.
        innerRings.forEach(innerRing -> {
            polygonsBuilders.forEach(pb -> {
                if (pb.shell().containsPoint(innerRing.firstPoint())) {
                    pb.addHole(innerRing);
                }
            });
        });
        
        // Keep only needed attributes from the relation. They will be used for
        // each created polygon. Ways' attributes are not conserved.
        Attributes atts = rel.attributes().keepOnlyKeys(polygonAtts);
        
        // For each polygon builder, we build a polygon the attributes of the
        // relation and add it to the map.
        polygonsBuilders.forEach(pb -> {
            mapBuilder.addPolygon(new Attributed<Polygon>(pb.build(), atts));
        });
    }
    
    private static Stream<OSMWay> filterByRole(Stream<OSMWay> ways, String role) {
        return ways.filter(way -> way.attributeValue("role") == role);
    }
    
    private static List<ClosedPolyLine> sortByArea(List<ClosedPolyLine> list) {
        list.sort((a, b) -> Double.valueOf(a.area()).compareTo(b.area()));
        return list;
    }
    
    private List<ClosedPolyLine> makeRings(Stream<OSMWay> ways) {
        Graph<OSMNode> graph = makeGraphFromWays(ways);
        Set<OSMNode> toDo = graph.nodes();
        List<ClosedPolyLine> polyLines = new ArrayList<ClosedPolyLine>();
        
        while (!toDo.isEmpty()) {
            ClosedPolyLine polyLine = makeRing(graph, toDo);
            
            if (polyLine != null) {
                polyLines.add(polyLine);
            }
        }
        
        return polyLines;
    }
    
    private static Graph<OSMNode> makeGraphFromWays(Stream<OSMWay> ways) {
        Graph.Builder<OSMNode> graphBuilder = new Graph.Builder<OSMNode>();
        
        ways.forEach(way -> {
            OSMNode last = way.nodes().get(way.nodes().size() - 1);
            
            way.nodes().stream().reduce(last, (current, prev) -> {
                graphBuilder.addNode(current);
                graphBuilder.addEdge(prev, current);
                return current;
            });
        });
        
        return graphBuilder.build();
    }
    
    private ClosedPolyLine makeRing(Graph<OSMNode> graph, Set<OSMNode> toDo) {
        PolyLine.Builder polyLineBuilder = new PolyLine.Builder();
        OSMNode current = getAny(toDo);
        OSMNode first = current;
        OSMNode prev = null;
        
        while (prev != first) {
            toDo.remove(current);
            polyLineBuilder.addPoint(transformNode(current));
            
            Set<OSMNode> neighbors = graph.neighborsOf(current);
            
            if (neighbors.size() != 2) {
                return null;
            }
            
            if (prev != null) {
                neighbors.remove(prev);
            }
            
            prev = current;
            current = getAny(neighbors);
        }
        
        return polyLineBuilder.buildClosed();
    }
    
    private static OSMNode getAny(Set<OSMNode> s) {
        // return s.stream().findAny().get();
        // https://piazza.com/class/i39wbwd15v83mt?cid=117
        return s.iterator().next();
    }
    
    public List<Point> transformNodes(List<OSMNode> nodes) {
        return nodes.stream()
                .map(node -> transformNode(node))
                .collect(Collectors.toList());
    }
    
    public Point transformNode(OSMNode node) {
        return projection.project(node.position());
    }
}
