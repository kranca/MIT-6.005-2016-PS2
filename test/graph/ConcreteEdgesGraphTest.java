/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
	
	private static final String vertex1 = "v1";
	private static final String vertex2 = "v2";
	private static final String vertex3 = "v3";
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    // 	 number of edges: 0, 1 , n
    
    // TODO tests for ConcreteEdgesGraph.toString()
    
    @Test
    public void testGraphToStringZeroEdges() {
    	Graph<String> graph = emptyInstance();
    	String expected = "";
    	assertEquals("Expected empty string", expected, graph.toString());
    }
    
    @Test
    public void testGraphToStringOneEdge() {
    	Graph<String> graph = emptyInstance();
    	String expected = "";
    	Integer weight1 = 10;
    	expected = expected + vertex1 + " -> " + vertex2 + ": " + weight1.toString() + "\n";
    	graph.set(vertex1, vertex2, 10);
    	assertEquals("Expected string: \n v1 -> v2: 10\n", expected, graph.toString());
    }
    
    @Test
    public void testGraphToStringTwoEdges() {
    	Graph<String> graph = emptyInstance();
    	String expected = "";
    	Integer weight1 = 20;
    	Integer weight2 = 5;
    	expected = expected + vertex1 + " -> " + vertex3 + ": " + weight1.toString() + "\n"
    			+ vertex2 + " -> " + vertex3 + ": " + weight2.toString() + "\n";
    	graph.set(vertex1, vertex3, 20);
    	graph.set(vertex2, vertex3, 5);
    	assertEquals("Expected string: \nv1 -> v3: 20\nv2 -> v3: 5", expected, graph.toString());
    }
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   test creator
    //   test methods: getSource, getTarget, getWeight, toString()
    
    // TODO tests for operations of Edge
    
    @Test
    public void testEdgeCreator() {
    	Edge<String> test = new Edge<String>(vertex1, vertex2, 50);
    	String expected = "";
    	expected = expected + vertex1 + " -> " + vertex2 + ": " + "50" + "\n";
    	assertTrue("Expected Edge source to equal vertex1", test.getSource().equals(vertex1));
    	assertTrue("Expected Edge target to equal vertex2", test.getTarget().equals(vertex2));
    	assertTrue("Expected Edge weight to equal 50", test.getWeight().equals(50));
    	assertEquals("Expected string: \nv1 -> v2: 50\n", expected, test.toString());
    }
    
}
