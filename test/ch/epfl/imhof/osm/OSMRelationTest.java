package ch.epfl.imhof.osm;

import java.util.ArrayList;

import ch.epfl.imhof.Attributes;

public class OSMRelationTest extends OSMEntityTest {
    @Override
    public OSMEntity newEntity(long id, Attributes attributes) {
        return new OSMRelation(id, new ArrayList<OSMRelation.Member>(),
                attributes);
    }
}
