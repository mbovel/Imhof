package ch.epfl.imhof;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GraphTest {
    @Test
    public void neighborsOfReturnsEmptySetIfNoNeighbors() {
        Map<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();
        map.put(0, new HashSet<Integer>());
        
        Graph<Integer> graph = new Graph<Integer>(map);
        
        assertTrue(graph.neighborsOf(0).isEmpty());
    }
    
    @Test
    public void neighborsOfReturnsCorrectSingleNeighbor() {
        Map<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();
        Set<Integer> neighbors0 = new HashSet<Integer>();
        Set<Integer> neighbors1 = new HashSet<Integer>();
        Set<Integer> neighbors2 = new HashSet<Integer>();
        
        neighbors0.add(1);
        neighbors1.add(0);
        
        map.put(0, neighbors0);
        map.put(1, neighbors1);
        map.put(2, neighbors2);
        
        Graph<Integer> graph = new Graph<Integer>(map);
        
        assertEquals(1, graph.neighborsOf(1).size());
        assertTrue(graph.neighborsOf(0).contains(1));
        
        assertEquals(1, graph.neighborsOf(1).size());
        assertTrue(graph.neighborsOf(1).contains(0));
        
        assertTrue(graph.neighborsOf(2).isEmpty());
        
        //printGraph(graph);
    }
    
    @Test
    public void neighborsOfReturnsCorrectManyNeighbor() {
        Map<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();
        Set<Integer> neighbors0 = new HashSet<Integer>();
        Set<Integer> neighbors1 = new HashSet<Integer>();
        Set<Integer> neighbors2 = new HashSet<Integer>();
        
        neighbors0.add(1);
        neighbors0.add(2);
        neighbors1.add(0);
        neighbors2.add(0);
        
        map.put(0, neighbors0);
        map.put(1, neighbors1);
        map.put(2, neighbors2);
        
        Graph<Integer> graph = new Graph<Integer>(map);
        
        assertEquals(2, graph.neighborsOf(0).size());
        assertTrue(graph.neighborsOf(0).contains(1));
        assertTrue(graph.neighborsOf(0).contains(2));
        
        assertEquals(1, graph.neighborsOf(1).size());
        assertTrue(graph.neighborsOf(1).contains(0));
        
        assertEquals(1, graph.neighborsOf(2).size());
        assertTrue(graph.neighborsOf(2).contains(0));
        
        //printGraph(graph);
    }
    
    @Test
    public void builderCorrectlyBuildsGraph() {
        Graph.Builder<Integer> builder = new Graph.Builder<Integer>();
        
        builder.addNode(0);
        builder.addNode(1);
        builder.addNode(2);
        builder.addNode(3);
        builder.addNode(4);
        builder.addNode(5);
        
        builder.addEdge(0, 1);
        builder.addEdge(3, 4);
        builder.addEdge(4, 5);
        builder.addEdge(3, 5);
        
        Graph<Integer> graph = builder.build();
        
        assertEquals(1, graph.neighborsOf(0).size());
        assertTrue(graph.neighborsOf(0).contains(1));
        
        assertEquals(1, graph.neighborsOf(1).size());
        assertTrue(graph.neighborsOf(1).contains(0));
        
        assertTrue(graph.neighborsOf(2).isEmpty());
        
        assertEquals(2, graph.neighborsOf(3).size());
        assertTrue(graph.neighborsOf(3).contains(4));
        assertTrue(graph.neighborsOf(3).contains(5));
        
        assertEquals(2, graph.neighborsOf(4).size());
        assertTrue(graph.neighborsOf(4).contains(3));
        assertTrue(graph.neighborsOf(4).contains(5));
        
        assertEquals(2, graph.neighborsOf(5).size());
        assertTrue(graph.neighborsOf(5).contains(3));
        assertTrue(graph.neighborsOf(5).contains(4));
        
        //printGraph(graph);
    }
    
    // TODO builder add doesn't replace
    
    private static <N> void printGraph(Graph<N> g) {
        System.out.println(g + ":");
        
        for(N node : g.nodes()) {
            System.out.println("  " + node);
            
            for(N neighbor : g.neighborsOf(node)) {
                System.out.println("    -> " + neighbor);
            }
        }
    }
}
