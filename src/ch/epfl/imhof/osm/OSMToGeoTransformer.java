/**
 * 
 */
package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.w3c.dom.Node;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.Graph;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;
import ch.epfl.imhof.projection.Projection;

/**
 * @author Matthieu Bovel (250300)
 */
public final class OSMToGeoTransformer {
    private static Set<String> surfaceAtts = initHashSet("aeroway", "amenity",
            "building", "harbour", "historic", "landuse", "leisure",
            "man_made", "military", "natural", "office", "place", "power",
            "public_transport", "shop", "sport", "tourism", "water",
            "waterway", "wetland");
    private static Set<String> polyLineAtts = initHashSet("bridge", "highway",
            "layer", "man_made", "railway", "tunnel", "waterway");
    private static Set<String> polygonAtts = initHashSet("building", "landuse",
            "layer", "leisure", "natural", "waterway");

    private final Projection projection;

    public OSMToGeoTransformer(Projection projection) {
        this.projection = projection;
    }

    public Map transform(OSMMap osm) {
        Map.Builder mapBuilder = new Map.Builder();

        osm.ways().forEach(way -> transformWay(way, mapBuilder));
        osm.relations().forEach(rel -> transformRel(rel, mapBuilder));

        return mapBuilder.build();
    }

    private void transformWay(OSMWay way, Map.Builder mapBuilder) {
        boolean isClosed = way.isClosed();

        if (isClosed && isSurface(way)) {
            Attributes atts = way.attributes().keepOnlyKeys(polygonAtts);
            ClosedPolyLine shell = new ClosedPolyLine(
                    transformNodes(way.nodes()));
            Polygon polygon = new Polygon(shell);
            mapBuilder.addPolygon(new Attributed<Polygon>(polygon, atts));
        }
    }

    private void transformRel(OSMRelation rel, Map.Builder mapBuilder) {

    }

    private List<Point> transformNodes(List<OSMNode> in) {
        List<Point> out = new ArrayList<>();
        in.forEach(node -> out.add(projection.project(node.position())));
        return out;
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

    private Graph<OSMNode> makeGraphFromWays(Stream<OSMWay> ways) {
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

        do {
            Set<OSMNode> neighbors = graph.neighborsOf(current);
            prev = current;
            neighbors.remove(prev);
            current = getAny(neighbors);
            toDo.remove(current);

            if (neighbors.size() != 1) {
                return null;
            }
        } while (current != first);

        return polyLineBuilder.buildClosed();
    }

    private OSMNode getAny(Set<OSMNode> s) {
        Optional<OSMNode> node = s.stream().findAny();
        return node.isPresent() ? null : node.get();
    }

    private boolean isSurface(OSMWay way) {
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

    private List<ClosedPolyLine> sortByArea(List<ClosedPolyLine> list) {
        list.sort((a, b) -> Double.valueOf(a.area()).compareTo(b.area()));
        return list;
    }

    private static Set<String> initHashSet(String... strings) {
        HashSet<String> set = new HashSet<String>();
        Arrays.stream(strings).forEach(str -> set.add(str));
        return set;
    }
}
