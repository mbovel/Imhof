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
    private final T value;
    private final Attributes attributes;

    /**
     * Constructs a new <code>Attributed</code> instance given a
     * <code>value</code> (any object) and its associated attributes
     * (represented by an instance of {@link Attributes}).
     * 
     * @param value
     *            the object to associate attributes
     * @param attributes
     *            the associated attributes
     */
    public Attributed(T value, Attributes attributes) {
        this.value = value;
        this.attributes = attributes;
    }

    /**
     * @return
     */
    public T value() {
        return this.value;
    }

    public Attributes attributes() {
        return this.attributes;
    }

    public boolean hasAttribute(String attributeName) {
        return this.attributes.contains(attributeName);
    }

    public String attributeValue(String attributeName) {
        return this.attributes.get(attributeName);
    }

    public String attributeValue(String attributeName, String defaultValue) {
        return this.attributes.get(attributeName, defaultValue);
    }

    public int attributeValue(String attributeName, int defaultValue) {
        return this.attributes.get(attributeName, defaultValue);
    }
}
