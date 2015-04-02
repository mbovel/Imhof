package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.imhof.Attributes;

/**
 * Represents an open street map way.
 * <p>
 * An open street map way is made out of a list of {@link OSMNode OSMNodes}, has
 * a unique id and some attributes.
 * 
 * @author Matteo Besançon (245826)
 *
 */
public final class OSMWay extends OSMEntity {
    private final List<OSMNode> nodes;
    
    /**
     * Constructs a new <code>OSMWay</code> given its id, attributes and nodes.
     * 
     * @param id
     *            <code>OSMWay</code>'s id
     * @param nodes
     *            <code>OSMWay</code>'s {@link List} of {@link OSMNode}
     * @param attributes
     *            <code>OSMWay</code>'s attributes ({@link Attributes})
     * 
     * @throws IllegalArgumentException
     *             if the <code>nodes</code> contains strictly less than two
     *             nodes
     */
    public OSMWay(long id, List<OSMNode> nodes, Attributes attributes)
            throws IllegalArgumentException {
        super(id, attributes);
        
        if (nodes.size() < 2) {
            throw new IllegalArgumentException(
                "nodes must contain at least two ellements");
        }
        
        this.nodes = Collections
            .unmodifiableList(new ArrayList<OSMNode>(nodes));
    }
    
    /**
     * Returns the number of nodes in the <code>OSMWay</code> /!\ it will counts
     * two times the first node if the <code>OSMWay</code> is closed.
     * 
     * @return the number of {@link OSMNode OSMNodes} in this
     *         <code>OSMWay</code> the <code>OSMWay</code>
     */
    public int nodesCount() {
        return nodes.size();
    }
    
    /**
     * Returns a {@link List} of all the <code>OSMNode</code>.
     * 
     * @return the field <code>nodes</code> of the <code>OSMWay</code>
     */
    public List<OSMNode> nodes() {
        return nodes;
    }
    
    /**
     * Returns <code>true</code> if this <code>OSMWay</code> is closed. More
     * formally it tests if the first node is the same as the last one.
     * 
     * @return <code>true</code> if the <code>OSMWay</code> is closed
     */
    public boolean isClosed() {
        return nodes.get(0).equals(nodes.get(nodes.size() - 1));
    }
    
    /**
     * Returns a list of all the {@link OSMNode OSMNodes} of this
     * <code>OSMWay</code> without repeating two times the same node if the
     * first node is the same as the last one.
     * 
     * @return a <code>List</code> of all the <code>OSMNode</code> constituing
     *         the <code>OSMWay</code> without repeating two times the same node
     *         if the first of the <code>List</code> is the same than the last
     *         one
     */
    public List<OSMNode> nonRepeatingNodes() {
        if (isClosed()) {
            return nodes.subList(0, nodes.size() - 1);
        }
        else {
            return nodes;
        }
    }
    
    /**
     * Returns the first node.
     * 
     * @return the first node
     */
    public OSMNode firstNode() {
        return nodes.get(0);
    }
    
    /**
     * Returns the last node.
     * 
     * @return the last node
     */
    public OSMNode lastNode() {
        return nodes.get(nodes.size() - 1);
    }
    
    /**
     * A Class that helps in the construction of a <code>OSMWay</code>.
     * 
     * @author Matteo Besançon (245826)
     *
     */
    public final static class Builder extends OSMEntity.Builder {
        private final List<OSMNode> nodes = new ArrayList<OSMNode>();
        
        /**
         * Constructs an <code>OSMWay.Builder</code> with the <code>id</code> of
         * the future <code>OSMWay</code>.
         * 
         * @param id
         *            the future <code>OSMWay</code>'s identification
         */
        public Builder(long id) {
            super(id);
        }
        
        /**
         * Adds a new node the future <code>OSMWay</code>.
         * 
         * @param newNode
         *            the future <code>OSMWay</code>'s node
         */
        public void addNode(OSMNode newNode) {
            nodes.add(newNode);
        }
        
        /**
         * Constructs a new <code>OSMWay</code> instance using the data provided
         * by the <code>OSMWay.Builder</code>.
         * 
         * @return the new <code>OSMWay</code>
         * 
         * @throws IllegalStateException
         *             if the <code>OSMWay</code> is not complete (
         *             <code>incomplete = true</code>)
         */
        @Override
        public OSMWay build() throws IllegalStateException {
            if (incomplete || nodes.size() < 2) {
                throw new IllegalStateException(
                    "The OSMWay is not complete yet.");
            }
            return new OSMWay(id, nodes, attributes.build());
        }
        
        @Override
        public boolean isIncomplete() {
            return incomplete || nodes.size() < 2;
        }
    }
}
