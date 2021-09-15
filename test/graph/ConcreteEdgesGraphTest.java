/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;
import java.util.ArrayList;
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
   
// For some reason, ConcreteEdgesGraph implementation doesn't work properly when working with a list
// and for this reason, I created 2 additional tests to try to find the problem behind this implementation.
// When setting strings directly to the graph or when setting from a list to the graph, the result is as expected
// but when setting from a list to the graph from the GraphPoet client setting from a list of strings doesn't work
// creating two equal edges. This is not the case with the concreteVerticesGraph implementation.
    
    @Test
    public void testPoetCase1() {
    	Graph<String> graph = emptyInstance();
    	String word1 = "some";
    	String word2 = "say";
    	String word3 = "the";
    	String word4 = "world";
    	String word5 = "will";
    	String word6 = "end";
    	String word7 = "in";
    	String word8 = "fire";
    	String word9 = "some";
    	String word10 = "say";
    	String word11 = "in";
    	String word12 = "ice";
    	int test1 = graph.set(word1, word2, 1);
    	graph.set(word2, word3, 1);
    	graph.set(word3, word4, 1);
    	graph.set(word4, word5, 1);
    	graph.set(word5, word6, 1);
    	graph.set(word6, word7, 1);
    	graph.set(word7, word8, 1);
    	graph.set(word8, word9, 1);
    	int test2 = graph.set(word9, word10, 2);
    	graph.set(word10, word11, 1);
    	graph.set(word11, word12, 1);
    	
    	int expected1 = 0;
    	int expected2 = 1;
    	//System.out.print(graph);
    	assertEquals("Expected", expected1, test1);
    	assertEquals("Expected", expected2, test2); 
    }
    
    @Test
    public void testPoetCase2() {
    	Graph<String> graph = emptyInstance();
    	ArrayList<String> list = new ArrayList<>();
    	String word1 = "some";
    	String word2 = "say";
    	String word3 = "the";
    	String word4 = "world";
    	String word5 = "will";
    	String word6 = "end";
    	String word7 = "in";
    	String word8 = "fire";
    	String word9 = "some";
    	String word10 = "say";
    	String word11 = "in";
    	String word12 = "ice";
    	list.add(word1);
    	list.add(word2);
    	list.add(word3);
    	list.add(word4);
    	list.add(word5);
    	list.add(word6);
    	list.add(word7);
    	list.add(word8);
    	list.add(word9);
    	list.add(word10);
    	list.add(word11);
    	list.add(word12);
    	System.out.println(list);
    	
    	int n = 0;
    	// set pairs of word until list is empty
		while (n < (list.size()-1)) {
			int previousWeight = graph.set(list.get(n), list.get(n+1), 1);
			
			// if a pair of words was already set in graph, get previous weight and set previous weight +1 for
			// the same pair of words
			if (previousWeight > 0) {
				graph.set(list.get(n), list.get(n+1), previousWeight+1);
			}
			n+=1;
		}
    	
    	int expected1 = 2;
    	int expected2 = 2;
    	int real1 = graph.targets(word1).get(word2);
    	int real2 = graph.targets(word9).get(word10);
    	System.out.print(graph);
    	assertEquals("Expected", expected1, real1);
    	assertEquals("Expected", expected2, real2); 
    }
}
