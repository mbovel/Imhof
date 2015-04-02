package ch.epfl.imhof;

/**
 * This class provide a simple way to associate a set of attributes to any
 * object.
 * 
 * @author Matthieu Bovel (250300)
 * @param <T>
 *            the type of object to associate attributes
 */
public final class Attributed<T> {
    private final T          value;
    private final Attributes attributes;
    
    /**
     * Constructs a new <code>Attributed</code> given a <code>value</code> (any
     * object) and its attributes (represented by an instance of
     * {@link Attributes}).
     * 
     * @param value
     *            the object to which attributes must be associated
     * @param attributes
     *            the associated attributes
     */
    public Attributed(T value, Attributes attributes) {
        this.value = value;
        this.attributes = attributes;
    }
    
    /**
     * Returns the attributed object.
     * 
     * @return the attributed object
     */
    public T value() {
        return this.value;
    }
    
    /**
     * Returns the attributes.
     * 
     * @return the attributes
     */
    public Attributes attributes() {
        return this.attributes;
    }
    
    /**
     * Returns <code>true</code> if this object has an attribute with the given
     * key.
     * 
     * @see {@link Attributes#contains(String)}
     * @param attributeName
     *            the key of the attribute to look for
     * @return <code>true</code> if there is an attribute with key
     *         <code>attributeName</code>, <code>false</code> if not.
     */
    public boolean hasAttribute(String attributeName) {
        return attributes.contains(attributeName);
    }
    
    /**
     * Returns the value associated to the given key, specified as a string, or
     * <code>null</code> if an attribute with the given key does not exist.
     * 
     * @see {@link Attributes#get(String)}
     * @param key
     *            the attribute key as a {@link String}
     * @return the String value of the specified attribute key, or
     *         <code>null</code> if an attribute with the given key does not
     *         exist.
     */
    public String attributeValue(String attributeName) {
        return this.attributes.get(attributeName);
    }
    
    /**
     * Returns the string value associated to the given key, or
     * <code>defaultValue</code> if an attribute with the given key does not
     * exist.
     * 
     * @see {@link Attributes#get(String, String)}
     * @param key
     *            the attribute key as a string
     * @param defaultValue
     *            the default value associated to the key
     * @return the string value associated to the given key, or
     *         <code>defaultValue</code> if such an attribute does not exist.
     */
    public String attributeValue(String attributeName, String defaultValue) {
        return this.attributes.get(attributeName, defaultValue);
    }
    
    /**
     * Returns the integer value associated to the given key, or
     * <code>defaultValue</code> if an attribute with the given key does not
     * exist.
     * 
     * @see {@link Attributes#get(String, int)}
     * @param key
     *            the attribute key as a string
     * @param defaultValue
     *            the default value associated to the key
     * @return the integer value associated to the given key, or
     *         <code>defaultValue</code> if an attribute with the given key does
     *         not exist.
     */
    public int attributeValue(String attributeName, int defaultValue) {
        return this.attributes.get(attributeName, defaultValue);
    }
}
