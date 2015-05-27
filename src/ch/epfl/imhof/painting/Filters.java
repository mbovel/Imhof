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
public final class Filters {
    private Filters() {
    }
    
    /**
     * Returns a {@link Predicate} (<code>Attributed<?> -> boolean</code>) whose
     * return value is <code>true</code> if its parameter contains the attribute
     * with the given name or <code>false</code> otherwise.
     * 
     * @param name
     *            the attribute's key to look for
     * @return a {@link Predicate} whose return value is <code>true</code> if
     *         its parameter contains the attribute with the given name, or
     *         <code>false</code> otherwise
     */
    static public Predicate<Attributed<?>> tagged(String name) {
        return a -> a.hasAttribute(name);
    }
    
    // Should it support integers as attribute values?
    /**
     * Returns a {@link Predicate} whose return value is <code>true</code> if
     * its parameter contains an attribute with the given name and its value is
     * one of the given values, or <code>false</code> otherwise.
     * 
     * @param name
     *            the attribute's key to look for
     * @param values
     *            the possible attribute's keys
     * @return a {@link Predicate} whose return value is <code>true</code> if
     *         its parameter contains an attribute with the given name and its
     *         value is one of the given values, <code>false</code> otherwise
     */
    static public Predicate<Attributed<?>> tagged(String name, String... values) {
        Set<String> valuesSet = new HashSet<String>(Arrays.asList(values));
        
        return a -> a.hasAttribute(name)
                && valuesSet.contains(a.attributeValue(name));
    }
    
    /**
     * Returns a {@link Predicate} whose return value is <code>true</code> if
     * its parameter is on a given layer, <code>false</code> otherwise.
     * 
     * @param layer
     *            the layer the tested {@link Predicate}'s parameter should be
     *            on
     * @return a {@link Predicate} whose return value is <code>true</code> if
     *         its parameter is on a given layer, <code>false</code> otherwise
     */
    static public Predicate<Attributed<?>> onLayer(int layer) {
        return a -> a.attributeValue("layer", 0) == layer;
    }
}
