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
    private final long       id;
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
     * Returns the attributes of this <code>OSMEntity</code>.
     * 
     * @return the attributes of this <code>OSMEntity</code>
     */
    public Attributes attributes() {
        return attributes;
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
        return attributes.get(attributeName);
    }
    
    /**
     * Abstract class that helps in the construction of {@link OSMEntity}.
     * 
     * @author Matteo Besançon (245826)
     *
     */
    public abstract static class Builder {
        protected final long         id;
        protected Attributes.Builder attributes = new Attributes.Builder();
        private boolean              incomplete = false;
        
        abstract public OSMEntity build();
        
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
         * Flags this builder as incomplete.
         */
        public void setIncomplete() {
            incomplete = true;
        }
        
        /**
         * Returns <code>true</code> if this builder has not enough data to
         * build the entity, <code>false</code> if it's ready to build.
         * 
         * @return <code>true</code> if this builder has not enough data to
         *         build the entity, <code>false</code> if it's ready to build.
         */
        public boolean isIncomplete() {
            return incomplete;
        }
    }
}
