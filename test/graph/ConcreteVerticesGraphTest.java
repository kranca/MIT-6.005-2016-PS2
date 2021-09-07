/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.HashSet;
import java.util.Set;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
	private static final String vertex1 = "v1";
	private static final String vertex2 = "v2";
	private static final String vertex3 = "v3";
	
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   number of directed edges: 0, 1, n
    
    //  tests for ConcreteVerticesGraph.toString()
    @Test
    public void testGraphToStringZeroDirectedEdges() {
    	Graph<String> graph = emptyInstance();
    	String expected = "";
    	assertEquals("Expected empty string", expected, graph.toString());
    }
    
    @Test
    public void testGraphToStringOneDirectedEdge() {
    	Graph<String> graph = emptyInstance();
    	String expected = vertex1 + " -> " + vertex2 + ": 15" + "\n";
    	graph.set(vertex1, vertex2, 15);
    	assertEquals("Expected string: \n v1 -> v2: 15\n", expected, graph.toString());
    }
    
    @Test
    public void testGraphToStringTwoDirectedEdges() {
    	Graph<String> graph = emptyInstance();
    	String expected = vertex1 + " -> " + vertex2 + ": 15" + "\n" + vertex2 + " -> " + vertex3 + ": 20" + "\n";
    	graph.set(vertex1, vertex2, 15);
    	graph.set(vertex2, vertex3, 20);
    	assertEquals("Expected string: \n v1 -> v2: 15\nv2 -> v3: 20\n", expected, graph.toString());
    }
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   test creator, test methods getName(), getDirectedEdges(), getWeight(), toString()
    
    // tests for operations of Vertex
    @Test
    public void testCreatorAndGetName() {
    	String name = "v1";
    	Vertex test = new Vertex(name);
    	assertTrue("Expected Vertex name to be 'v1'", test.getName().equals(name));
    	assertTrue("Expected empty set of directed edges from vertice v1", test.getDirectedEdges().isEmpty());
    }
    
    @Test
    public void testGetWeight() {
    	String name1 = "v1";
    	String name2 = "v2";
    	Vertex test1 = new Vertex(name1);
    	Vertex test2 = new Vertex(name2);
    	test1.setEdgeTo(test2, 10);
    	assertTrue("Expected weight with value 10", test1.getWeight(test2).equals(10));
    }
    
    @Test
    public void testToString() {
    	String name1 = "v1";
    	String name2 = "v2";
    	String name3 = "v3";
    	Vertex test1 = new Vertex(name1);
    	Vertex test2 = new Vertex(name2);
    	Vertex test3 = new Vertex(name3);
    	test1.setEdgeTo(test2, 10);
    	test1.setEdgeTo(test3, 20);
    	String expected = name1 + " -> " + name2 + ": 10\n"
    			+ name1 + " -> " + name3 + ": 20\n";
    	
    	assertEquals("Expected string: \nv1 -> v2: 10\nv1 -> v3: 20\n", test1.toString(), expected);
    }
    
    @Test
    public void testGetDirectedEdges() {
    	String name1 = "v1";
    	String name2 = "v2";
    	String name3 = "v3";
    	Vertex test1 = new Vertex(name1);
    	Vertex test2 = new Vertex(name2);
    	Vertex test3 = new Vertex(name3);
    	test1.setEdgeTo(test2, 10);
    	test1.setEdgeTo(test3, 20);
    	Set<Vertex> testSet = new HashSet<>();
    	testSet.add(test2);
    	testSet.add(test3);
    	assertTrue("Expected Vertex 'v2' and 'v3' to be contained in Set directedEdges", test1.getDirectedEdges().containsAll(testSet));
    	
    }
}
