package ch.epfl.imhof;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A class that represents an undirected graph.
 * 
 * @author Matthieu Bovel (250300)
 *
 * @param <N>
 *            the type of the <code>Graph</code>'s nodes
 */
public final class Graph<N> {
    private Map<N, Set<N>> neighbors;
    
    /**
     * Constructs a new undirected <code>Graph</code> given a neighborhood
     * table.
     * 
     * @param neighbors
     *            the table of all the neighbors
     */
    public Graph(Map<N, Set<N>> neighbors) {
        this.neighbors = new HashMap<N, Set<N>>();
        
        for (Map.Entry<N, Set<N>> entry : neighbors.entrySet()) {
            this.neighbors.put(
                entry.getKey(),
                Collections.unmodifiableSet(new HashSet<N>(entry.getValue())));
        }
        
        this.neighbors = Collections.unmodifiableMap(this.neighbors);
    }
    
    /**
     * Returns the <code>Graph</code>'s nodes.
     * 
     * @return the <code>Graph</code>'s nodes
     */
    public Set<N> nodes() {
        return this.neighbors.keySet();
    }
    
    /**
     * Returns the neighbors of a given node.
     * 
     * @param n
     *            the node we want its neighbors returned
     * @return the set of all the neighbors of a given node
     */
    public Set<N> neighborsOf(N n) {
        if (!this.neighbors.containsKey(n)) {
            throw new IllegalArgumentException(
                "This graph doesn't contain this element.");
        }
        
        return this.neighbors.get(n);
    }
    
    /**
     * A class that helps in the construction of a new {@link Graph}.
     * 
     * @author Matthieu Bovel (250300)
     *
     * @param <N>
     *            the type of the future <code>Graph</code>'s nodes
     */
    public static class Builder<N> {
        private final Map<N, Set<N>> neighbors = new HashMap<N, Set<N>>();
        
        /**
         * Adds a node to the future <code>Graph</code>.
         * 
         * @param n
         *            the future <code>Graph</code>'s node
         */
        public void addNode(N n) {
            if (!this.neighbors.containsKey(n)) {
                this.neighbors.put(n, new HashSet<N>());
            }
        }
        
        /**
         * Adds a new edge between two nodes to the future <code>Graph</code>.
         * 
         * @param n1
         *            first node of the edge
         * @param n2
         *            second node of the edge
         * @throws IllegalArgumentException
         *             if n1 or n2 are not already added to the future
         *             <code>Graph</code>
         */
        public void addEdge(N n1, N n2) throws IllegalArgumentException {
            if (!this.neighbors.containsKey(n1)) {
                throw new IllegalArgumentException(
                    "n1 should first be added with #addNode(N n)");
            }
            
            if (!this.neighbors.containsKey(n2)) {
                throw new IllegalArgumentException(
                    "n2 should first be added with #addNode(N n)");
            }
            
            this.neighbors.get(n1).add(n2);
            this.neighbors.get(n2).add(n1);
        }
        
        /**
         * Constructs a new <code>Graph</code> instance using the data provided
         * to this <code>Graph.Builder</code>.
         * 
         * @return the new <code>Graph</code>
         */
        public Graph<N> build() {
            return new Graph<N>(this.neighbors);
        }
    }
}
