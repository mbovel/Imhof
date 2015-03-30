package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;

import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;

public class OurOSMWayTest extends OurOSMEntityTest {
    private static List<OSMNode> nodes;

    @BeforeClass
    static public void initOSMWayTest() {
        Attributes attrs = new Attributes(new HashMap<String, String>());
        OSMNode node1 = new OSMNode(12983, new PointGeo(
                Math.toRadians(46.516568), Math.toRadians(6.626057)), attrs);
        OSMNode node2 = new OSMNode(28934, new PointGeo(
                Math.toRadians(46.524178), Math.toRadians(6.624007)), attrs);
        nodes = new ArrayList<OSMNode>();
        nodes.add(node1);
        nodes.add(node2);
        nodes = Collections.unmodifiableList(nodes);
    }

    @Override
    public OSMEntity newEntity(long id, Attributes attributes) {
        return new OSMWay(id, nodes, attributes);
    }
}
