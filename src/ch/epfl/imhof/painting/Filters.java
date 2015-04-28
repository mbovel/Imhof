package ch.epfl.imhof.painting;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import ch.epfl.imhof.Attributed;

public class Filters {
    private Filters() {
    }
    
    static public Predicate<Attributed<?>> tagged(String name) {
        return a -> a.hasAttribute(name);
    }
    
    // Should it support integers as attribute values?
    static public Predicate<Attributed<?>> tagged(String name, String... values) {
        Set<String> valuesSet = new HashSet<String>(Arrays.asList(values));
        
        return a -> a.hasAttribute(name)
                && valuesSet.contains(a.attributeValue(name));
    }
    
    static public Predicate<Attributed<?>> onLayer(int layer) {
        return a -> a.attributeValue("layer", 0) == layer;
    }
}
