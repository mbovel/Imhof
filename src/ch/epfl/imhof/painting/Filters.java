package ch.epfl.imhof.painting;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import ch.epfl.imhof.Attributed;

/**
 * This class contains some useful filters that can be used to target only some
 * {@link Attributed} objects.
 * 
 * @author Matthieu Bovel (250300)
 */
public class Filters {
    private Filters() {
    }
    
    /**
     * Returns a {@link Predicate} (<code>Attributed<?> -> boolean</code>) whose
     * value is true if its parameter contains the attribute with the given name
     * or false otherwise.
     * 
     * @param name
     *            the attribute to check for in the predicate
     * @return a {@link Predicate} whose value is true if its paramter contains
     *         the attribute with the given name
     */
    static public Predicate<Attributed<?>> tagged(String name) {
        return a -> a.hasAttribute(name);
    }
    
    // Should it support integers as attribute values?
    /**
     * Returns a {@link Predicate} (<code>Attributed<?> -> boolean</code>) whose
     * value is <code>true</code> if its parameter contains the attribute with the given name
     * and this attribute has one of the given values or <code>false</code> otherwise.
     * 
     * @param name
     * @param values
     * @return
     */
    static public Predicate<Attributed<?>> tagged(String name, String... values) {
        Set<String> valuesSet = new HashSet<String>(Arrays.asList(values));
        
        return a -> a.hasAttribute(name)
                && valuesSet.contains(a.attributeValue(name));
    }
    
    /**
     * @param layer
     * @return
     */
    static public Predicate<Attributed<?>> onLayer(int layer) {
        return a -> a.attributeValue("layer", 0) == layer;
    }
}
