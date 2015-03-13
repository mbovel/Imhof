package ch.epfl.imhof.osm;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.epfl.imhof.Attributes;

public abstract class OSMEntityTest {
    static private Attributes attrs;

    public abstract OSMEntity newEntity(long id, Attributes attributes);

    @BeforeClass
    static public void initOSMEntityTest() {
        attrs = new Attributes.Builder().put("name", "Ollon")
                .put("bestCityEver", "true").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void idCannotBeNegative() {
        newEntity(-1, new Attributes(new HashMap<String, String>()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void idCannotBeNull() {
        newEntity(0, new Attributes(new HashMap<String, String>()));
    }

    @Test
    public void idGetterReturnsId() {
        OSMEntity entity = newEntity(80, new Attributes(
                new HashMap<String, String>()));

        assertEquals(entity.id(), 80);
    }

    @Test
    public void idCanBeLong() {
        OSMEntity entity = newEntity(1L << 61, new Attributes(
                new HashMap<String, String>()));

        assertEquals(entity.id(), 1L << 61);
    }

    @Test
    public void attributesGetterReturnsAttributes() {
        OSMEntity entity = newEntity(7, attrs);

        assertEquals(entity.attributes(), attrs);
    }

    @Test
    public void hasAttributeReturnsTrueIfAttributeExists() {
        OSMEntity entity = newEntity(7, attrs);

        assertTrue(entity.hasAttribute("name"));
    }

    @Test
    public void hasAttributeReturnsFalseIfAttributeDoesNotExist() {
        OSMEntity entity = newEntity(7, attrs);

        assertFalse(entity.hasAttribute("hey"));
    }

    @Test
    public void hasAttributeReturnsFalseIfNull() {
        OSMEntity entity = newEntity(7, attrs);

        assertFalse(entity.hasAttribute(null));
    }

    @Test
    public void attributeValueReturnsCorrectValueIfKeyExists() {
        OSMEntity entity = newEntity(7, attrs);

        assertEquals(entity.attributeValue("name"), "Ollon");
        assertEquals(entity.attributeValue("bestCityEver"), "true");
    }

    @Test
    public void attributeValueReturnsNullIfKeyDoesNotExist() {
        OSMEntity entity = newEntity(7, attrs);

        assertEquals(entity.attributeValue("color"), null);
    }
}
