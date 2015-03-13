package ch.epfl.imhof;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
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
        this.attributes = Collections
                .unmodifiableMap(new HashMap<>(attributes));
    }

    /**
     * Returns <code>true</code> if this set of attributes contains no
     * attributes.
     * 
     * @return <code>true</code> if this set of attributes contains no
     *         attributes.
     */
    public Boolean isEmpty() {
        return this.attributes.isEmpty();
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
        return this.attributes.containsKey(key);
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
        return this.attributes.get(key);
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
        return this.attributes.getOrDefault(key, defaultValue);
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
        if (!this.attributes.containsKey(key))
            return defaultValue;

        int i;

        try {
            i = Integer.parseInt(this.attributes.get(key));
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
        Builder builder = new Builder();

        for (String key : keysToKeep) {
            builder.put(key, this.attributes.get(key));
        }

        return builder.build();
    }

    @Override
    public boolean equals(Object b) {
        if (b == null || b.getClass() != this.getClass())
            return false;

        // Should use EqualsBuilder
        return b.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        // Should use HashCodeBuilder
        return this.toString().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("{\n");

        for (Map.Entry<String, String> entry : this.attributes.entrySet()) {
            builder.append("\t\"");
            escapeJsonString(entry.getKey(), builder);
            builder.append("\": \"");
            escapeJsonString(entry.getValue(), builder);
            builder.append("\"\n");
        }

        builder.append("}");

        return builder.toString();
    }

    private static void escapeJsonString(String toEscape, StringBuilder builder) {
        for (int i = 0; i < toEscape.length(); i++) {
            char c = toEscape.charAt(i);
            switch (c) {
            case '\\':
                builder.append("\\\\");
                break;
            case '"':
                builder.append("\\\"");
                break;
            default:
                builder.append(c);
            }
        }
    }

    /**
     * Class that helps in the construction of {@link Attributes}.
     * 
     * @author Matthieu Bovel (250300)
     *
     */
    public static class Builder {
        private Map<String, String> attributes = new HashMap<String, String>();

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
            this.attributes.put(key, value);
            return this;
        }

        /**
         * Constructs a new <code>Attributes</code> instance using data provided
         * with {@link #put}.
         * 
         * @return the new <code>Attributes</code>.
         */
        public Attributes build() {
            return new Attributes(this.attributes);
        }
    }
}
