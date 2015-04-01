package ch.epfl.imhof.osm;

import ch.epfl.imhof.*;

/**
 * Represents an open street map node.
 * <p>
 * An open street map node has a unique id (<code>long</code>), a position
 * ({@link PointGeo}) and some attributes ({@link Attributes}).
 * 
 * @author Matteo Besançon (245826)
 */
public final class OSMNode extends OSMEntity {

    private final PointGeo position;

    /**
     * Constructs a new <code>OSMNode</code> given its unique id, attributes and
     * position.
     * 
     * @param id
     *            <code>OSMNode</code>'s id
     * @param position
     *            <code>OSMNode</code>'s position ({@link PointGeo})
     * @param attributes
     *            <code>OSMNode</code>'s attributes ({@link Attributes})
     */
    public OSMNode(long id, PointGeo position, Attributes attributes) {
        super(id, attributes);
        this.position = position;
    }

    /**
     * Returns the <code>position</code> of the <code>OSMNode</code>.
     * 
     * @return the <code>position</code> of the <code>OSMNode</code>
     */
    public PointGeo position() {
        return position;
    }

    /**
     * A class that helps in the construction of a {@link OSMNode}.
     * 
     * @author Matteo Besançon (245826)
     *
     */
    public final static class Builder extends OSMEntity.Builder {
        private final PointGeo position;

        /**
         * Constructs an <code>OSMNode.Builder</code> with the id and the
         * position of the future <code>OSMNode</code>.
         * 
         * @param id
         *            the future <code>OSMNode</code>'s id
         * @param position
         *            the future <code>OSMNode</code>'s <code>position</code>
         */
        public Builder(long id, PointGeo position) {
            super(id);
            this.position = position;
        }

        /**
         * Constructs a new <code>OSMNode</code> instance using the datas
         * provided by the <code>OSMNode.Builder</code>.
         * 
         * @return the new <code>OSMNode</code>
         * 
         * @throws IllegalStateException
         *             if the <code>OSMNode</code> is not complete (
         *             <code>incomplete = true</code>)
         */
        public OSMNode build() throws IllegalStateException {
            if (incomplete) {
                throw new IllegalStateException(
                        "the OSMNode is not complete yet.");
            }

            return new OSMNode(this.id, this.position, this.attributes.build());
        }
    }
}
