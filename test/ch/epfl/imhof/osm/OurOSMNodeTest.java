package ch.epfl.imhof.osm;

import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;

public class OurOSMNodeTest extends OurOSMEntityTest {
    @Override
    public OSMEntity newEntity(long id, Attributes attributes) {
        return new OSMNode(id, new PointGeo(0, 0), attributes);
    }
}
