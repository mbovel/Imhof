package ch.epfl.imhof.osm;

import ch.epfl.imhof.Attributes;

/**
 * Abstract class to represent an open street map entity.
 * <p>
 * An open street map entity has a unique id (<code>long</code>) and some
 * attributes ({@link Attributes}).
 * 
 * @author Matteo Besançon (245826)
 */
public abstract class OSMEntity {
    private final long id;
    private final Attributes attributes;

    /**
     * Construct a new <code>OSMEntity</code> from an id and an
     * {@link Attributes} object.
     * 
     * @param id
     *            unique open street map id of the <code>OSMEntity</code>
     * @param attributes
     *            attributes of the <code>OSMEntity</code>
     * @throws IllegalArgumentException
     *             if id is negative or non null
     */
    public OSMEntity(long id, Attributes attributes)
            throws IllegalArgumentException {
        this.attributes = attributes;

        if (id < 1) {
            throw new IllegalArgumentException(
                    "id must be a positive non null integer");
        }
        
        this.id = id;
    }

    /**
     * Returns the identification of the <code>OSMEntity</code>.
     * 
     * @return the id of the <code>OSMEntity</code>
     */
    public long id() {
        return id;
    }

    /**
     * Returns the <code>attributes</code> of this <code>OSMEntity</code>.
     * 
     * @return the <code>attributes</code> of this <code>OSMEntity</code>
     */
    public Attributes attributes() {
        return attributes;
    }

    /**
     * Returns <code>true</code> if this OSM entity has an attribute with the
     * given key.
     * 
     * @param key
     *            the key of the attribute searched in <code>attributes</code>
     * @return <code>true</code> if this OSM entity has an attribute with the
     *         given key
     */
    public boolean hasAttribute(String key) {
        return attributes.contains(key);
    }

    /**
     * Returns the value which correspond to a specified key or
     * <code>null</code> if <code>attributes</code> doesn't contain this key.
     * 
     * @param key
     *            the <code>key</code> of the value to be returned
     * @return the value which correspond to a specified <code>key</code> or
     *         <code>null</code> if <code>attributes</code> doesn't contain this
     *         key
     */
    public String attributeValue(String key) {
        return attributes.get(key);
    }

    /**
     * Abstract class that helps in the construction of {@link OSMEntity}.
     * 
     * @author Matteo Besançon (245826)
     *
     */
    public abstract static class Builder {
        protected final long id;
        protected Attributes.Builder attributes = new Attributes.Builder();
        protected boolean incomplete;

        /**
         * Constructs an <code>OSMEntity.Builder</code> with the <code>id</code>
         * of the future <code>OSMEntity</code>.
         * 
         * @param id
         *            the future <code>OSMEntity</code>'s identification
         */
        public Builder(long id) {
            this.id = id;
        }

        /**
         * Adds an attribute to the future OSM entity.
         * 
         * @param key
         *            attribute's key
         * @param value
         *            attribute's value
         */
        public void setAttribute(String key, String value) {
            attributes.put(key, value);
        }

        /**
         * Sets the value of <code>incomplete</code> to <code>true</code>.
         */
        public void setIncomplete() {
            incomplete = true;
        }

        /**
         * Returns the value of <code>incomplete</code>.
         * 
         * @return <code>true</code> if the future <code>OSMEntity</code> is not
         *         complete
         */
        public boolean isIncomplete() {
            return incomplete;
        }
    }
}
