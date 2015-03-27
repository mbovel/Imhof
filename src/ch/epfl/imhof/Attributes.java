package ch.epfl.imhof;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The <code>Attributes</code> class maps strings keys to string or integer
 * values. It can be associated to any object with {@link Attributed}.
 * 
 * @author Matthieu Bovel (250300)
 *
 */
public final class Attributes {
    private final Map<String, String> attributes;
    
    /**
     * Constructs a new <code>Attributes</code> object from a
     * <code>Map<String, String></code>.
     * 
     * @param attributes
     */
    public Attributes(Map<String, String> attributes) {
        // This an optimization trick to avoid creating large amounts of
        // HashMaps for nothing. Not keeping these useless empty HashMaps in
        // memory make the method OSMMapReader.ReadOSMFile 1.15 times faster.
        if (attributes.isEmpty()) {
            this.attributes = null;
        }
        else {
            this.attributes = Collections.unmodifiableMap(new HashMap<>(
                attributes));
        }
    }
    
    /**
     * Returns <code>true</code> if this set of attributes contains no
     * attributes.
     * 
     * @return <code>true</code> if this set of attributes contains no
     *         attributes.
     */
    public Boolean isEmpty() {
        if (attributes == null) {
            return true;
        }
        
        return attributes.isEmpty();
    }
    
    /**
     * Returns <code>true</code> if this set of attributes contains the
     * specified attribute key.
     * 
     * @param key
     *            the attribute key
     * @return <code>true</code> if this set of attributes contains the
     *         specified attribute key
     */
    public Boolean contains(String key) {
        if (attributes == null) {
            return false;
        }
        
        return attributes.containsKey(key);
    }
    
    /**
     * Returns the value of the specified attribute key, specified as a string,
     * or null if the attribute was not found.
     * 
     * @param key
     *            the attribute key as a {@link String}
     * @return the String value of the specified attribute key, or
     *         <code>null</code> if not found
     */
    public String get(String key) {
        if (attributes == null) {
            return null;
        }
        
        return attributes.get(key);
    }
    
    /**
     * Returns the string value to which the specified key is mapped, or
     * <code>defaultValue</code> if this map contains no mapping for the given
     * key.
     * 
     * @param key
     *            the attribute key as a string
     * @param defaultValue
     *            the default mapping associated to the key
     * @return the string value to which the specified key is mapped, or
     *         <code>defaultValue</code> if this map contains no string mapped
     *         to the given key
     */
    public String get(String key, String defaultValue) {
        if (attributes == null) {
            return defaultValue;
        }
        
        return attributes.getOrDefault(key, defaultValue);
    }
    
    /**
     * Returns the integer value to which the specified key is mapped, or
     * <code>defaultValue</code> if this map contains no mapping for the given
     * key.
     * 
     * @param key
     *            the attribute key as a string
     * @param defaultValue
     *            the default mapping associated to the key
     * @return the integer value to which the specified key is mapped, or
     *         <code>defaultValue</code> if this map contains no valid integer
     *         mapped to the given key.
     */
    public int get(String key, int defaultValue) {
        if (attributes == null || !attributes.containsKey(key)) {
            return defaultValue;
        }
        
        int i;
        
        try {
            i = Integer.parseInt(attributes.get(key));
        }
        catch (NumberFormatException e) {
            return defaultValue;
        }
        
        return i;
    }
    
    /**
     * Constructs a new <code>Attributes</code> object from this one containing
     * only the attributes whose key are in <code>keys</code>.
     * 
     * @param keysToKeep
     *            a set of string keys used to filter the set of attributes
     * @return a new <code>Attributes</code> object containing the attributes
     *         with the given keys.
     */
    public Attributes keepOnlyKeys(Set<String> keysToKeep) {
        if (attributes == null) {
            return new Attributes(new HashMap<>());
        }
        
        Builder builder = new Builder();
        
        for (String key : keysToKeep) {
            if (attributes.containsKey(key)) {
                builder.put(key, attributes.get(key));
            }
        }
        
        return builder.build();
    }
    
    /**
     * Class that helps in the construction of {@link Attributes}.
     * 
     * @author Matthieu Bovel (250300)
     *
     */
    public static class Builder {
        private final Map<String, String> attributes = new HashMap<String, String>();
        
        /**
         * Adds an attribute (<code>key</code>/<code>value</code> strings) to
         * the future {@link Attributes} object.
         * 
         * @param key
         *            attribute's key
         * @param value
         *            attribute's value
         */
        public Builder put(String key, String value) {
            attributes.put(key, value);
            return this;
        }
        
        /**
         * Constructs a new <code>Attributes</code> instance using data provided
         * with {@link #put}.
         * 
         * @return the new <code>Attributes</code>.
         */
        public Attributes build() {
            return new Attributes(attributes);
        }
    }
}
