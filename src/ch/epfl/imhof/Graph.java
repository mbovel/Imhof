package ch.epfl.imhof;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Graph<N> {
    private Map<N, Set<N>> neighbors;
    
    public Graph(Map<N, Set<N>> neighbors) {
        this.neighbors = new HashMap<N, Set<N>>();
        
        for(Map.Entry<N, Set<N>> entry : neighbors.entrySet()) {
            this.neighbors.put(entry.getKey(), Collections.unmodifiableSet(new HashSet<N>(entry.getValue())));
        }
        
        this.neighbors = Collections.unmodifiableMap(this.neighbors);
    }
    
    public Set<N> nodes() {
        return this.neighbors.keySet();
    }
    
    public Set<N> neighborsOf(N n) {
        if(!this.neighbors.containsKey(n)) {
            throw new IllegalArgumentException("This graph doesn't contain this element.");
        }
        
        return this.neighbors.get(n);
    }
    
    public static class Builder<N> {
        private Map<N, Set<N>> neighbors = new HashMap<N, Set<N>>();
        
        public void addNode(N n) {
            if(!this.neighbors.containsKey(n)) {
                this.neighbors.put(n, new HashSet<N>());
            }
        }
        
        public void addEdge(N n1, N n2) throws IllegalArgumentException{
            if(!this.neighbors.containsKey(n1)) {
                throw new IllegalArgumentException("n1 should first be added with #addNode(N n)");
            }
            
            if(!this.neighbors.containsKey(n2)) {
                throw new IllegalArgumentException("n2 should first be added with #addNode(N n)");
            }
            
            this.neighbors.get(n1).add(n2);
            this.neighbors.get(n2).add(n1);
        }
        
        public Graph<N> build() {
            return new Graph<N>(this.neighbors);
        }
    }
}
