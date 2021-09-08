/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for static methods of Graph.
 * 
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 */
public class GraphStaticTest {
    
    // Testing strategy
    //   empty()
    //     no inputs, only output is empty graph
    //     observe with vertices()
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testEmptyVerticesEmpty() {
        assertEquals("expected empty() graph to have no vertices",
                Collections.emptySet(), Graph.empty().vertices());
    }
    
    // TODO test other vertex label types in Problem 3.2
    @Test
    public void testIntegerLabels() {
    	Graph<Integer> graph = Graph.empty();
    	graph.add(1);
    	graph.set(1, 2, 30);
    	String expected = "1 -> 2: 30\n";
    	assertEquals("Expected graph to have two vertices", 2, graph.vertices().size());
    	assertTrue("Expected sources for vertex 2 to be vertex 1 and have a weight of 30", graph.sources(2).get(1).equals(30));
    	assertEquals("Expected graph.toString() to be: \n1 -> 2: 30\n", expected, graph.toString());
    }
    @Test
    public void testIntegerLabelsRemove() {
    	Graph<Integer> graph = Graph.empty();
    	graph.add(1);
    	graph.set(3, 1, 20);
    	graph.remove(1);
    	assertEquals("Expected graph to have two vertices", 1, graph.vertices().size());
    	assertTrue("Expected targets for vertex 3 to be empty", graph.targets(3).isEmpty());
    }
    
    @Test
    public void testCharLabels() {
    	Graph<Character> graph = Graph.empty();
    	char a = 'a';
    	graph.add(a);
    	graph.set(a, 'b', 40);
    	String expected = "a -> b: 40\n";
    	assertEquals("Expected graph to have two vertices", 2, graph.vertices().size());
    	assertTrue("Expected sources for vertex 2 to be vertex 1 and have a weight of 30\n", graph.sources('b').get(a).equals(40));
    	assertEquals("Expected graph.toString() to be: \n1 -> 2: 30\n", expected, graph.toString());
    }
    
    @Test
    public void testCharLabelsRemove() {
    	Graph<Character> graph = Graph.empty();
    	graph.add('a');
    	graph.set('c', 'a', 10);
    	graph.remove('a');
    	assertEquals("Expected graph to have two vertices", 1, graph.vertices().size());
    	assertTrue("Expected targets for vertex 3 to be empty", graph.targets('c').isEmpty());
    }
}
