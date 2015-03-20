package ch.epfl.imhof;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Graph<N> {
    private Map<N, Set<N>> neighbors;
    
    public Graph(Map<N, Set<N>> neighbors) {
        this.neighbors = Collections.unmodifiableMap(new HashMap<N, Set<N>>(neighbors));
    }
    
    public Set<N> nodes() {
        return this.neighbors.keySet();
    }
    
    public Set<N> neighborsOf(N n) {
        return this.neighbors.get(n);
    }
    
    public class Builder {
        private Map<N, Set<N>> neighbors;
        
        void addNode(N n) {
            this.neighbors.put(n, new HashSet<N>());
        }
        
        void addEdge(N n1, N n2) throws IllegalArgumentException{
            if(!this.neighbors.containsKey(n1)) {
                throw new IllegalArgumentException("n1 should first be added with #addNode(N n)");
            }
            
            if(!this.neighbors.containsKey(n2)) {
                throw new IllegalArgumentException("n2 should first be added with #addNode(N n)");
            }
            
            this.neighbors.get(n1).add(n2);
            this.neighbors.get(n2).add(n1);
        }
        
        Graph<N> build() {
            return new Graph<N>(this.neighbors);
        }
    }
}
