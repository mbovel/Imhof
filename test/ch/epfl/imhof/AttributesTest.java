package ch.epfl.imhof;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

public class AttributesTest {
    private HashMap<String, String> map = new HashMap<String, String>();
    
    @Test
    public void mapIsClonedBeforeBeingStored() {
        map.put("firstname", "Marilyn");
        map.put("lastname", "Manson");
        
        Attributes attrs = new Attributes(map);
        
        map.put("firstname", "Marilyn");
        map.remove("lastname");
        
        assertEquals("Marilyn", attrs.get("firstname"));
        assertEquals("Manson", attrs.get("lastname"));
    }
    
    @Test
    public void isEmptyReturnsTrueIfEmpty() {
        Attributes attrs = new Attributes(map);
        
        assertTrue(attrs.isEmpty());
    }
    
    @Test
    public void isEmptyReturnsFalseIfNotEmpty() {
        map.put("firstname", "Cara");
        map.put("lastname", "Delevingne");
        
        Attributes attrs = new Attributes(map);
        
        assertFalse(attrs.isEmpty());
    }
    
    @Test
    public void containsReturnsFalseIfAttributeDoesNotExist() {
        Attributes attrs = new Attributes(map);
        
        assertFalse(attrs.contains("lastname"));
        
        map = new HashMap<String, String>();

        map.put("firstname", "Marilyn");
        
        assertFalse(attrs.contains("lastname"));
    }
    
    @Test
    public void containsReturnsTrueIfAttributeExists() {
        map.put("firstname", "Marilyn");
        map.put("lastname", "Manson");
        
        Attributes attrs = new Attributes(map);
        
        assertTrue(attrs.contains("firstname"));
    }
    
    @Test
    public void getReturnsCorrectValue() {
        map.put("firstname", "Cara");
        map.put("lastname", "Delevingne");
        
        Attributes attrs = new Attributes(map);
        
        assertEquals("Cara", attrs.get("firstname"));
        assertEquals("Delevingne", attrs.get("lastname"));
    }
    
    @Test
    public void getReturnsNullIfAttributeDoesnotExist() {
        Attributes attrs = new Attributes(map);
        
        assertEquals(attrs.get("firstname"), null);
    }
    
    @Test
    public void getReturnsCorrectDefaultStringValue() {
        Attributes attrs = new Attributes(map);
        
        assertEquals(attrs.get("firstname", "Marilyn"), "Marilyn");
    }
    
    @Test
    public void getWorksWithUnknownKeyAndDefaultValueString() {
        Attributes attrs = new Attributes(map);
        
        assertEquals(attrs.get("firstname", "Marilyn"), "Marilyn");
    }
    
    @Test
    public void getWorksWithKnownKeyAndDefaultValueString() {
        Attributes attrs = new Attributes(map);
        
        assertEquals(attrs.get("firstname", "Marilyn"), "Marilyn");
    }
}
