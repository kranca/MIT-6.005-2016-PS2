/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    // 
	// only use of emptyInstance()
	// Partitions
	// 
	// add(L vertex):
	// 		graph already contains vertex, expect boolean false
	//		graph does not contain vertex, expect boolean true
	//
	// set(L source, L target, int weight):
	//		directed edge already existed + non zero weight, expect updated weight
	//		directed edge did not exist before + non zero weight, create new edge
	//		directed edge already existed + zero weight, expect removed edge
	//		directed edge did not exist before + zero weight, expect no mutation
	//		vertices haven't been added to graph, expect 1, 2 vertices to be added
	//
	// remove(L vertex):
	//		graph contains vertex, expect no edges from or to removed edge, expect boolean true
	//		graph does not contain vertex, expect false and no mutation
	//
	// vertices():
	//		graph contains 0, 1, >1 vertices
	//
	// sources(L target):
	//		graph contains 0, 1, >1 source vertices
	//
	// targets(L source):
	//		graph contains 0, 1, >1 target vertices
	
	private static final String vertex1 = "v1";
	private static final String vertex2 = "v2";
	private static final String vertex3 = "v3";
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    @Test
    public void testAddVertexAlreadyContainsVertex() {
    	Graph<String> graph = emptyInstance();
    	graph.add(vertex1);
    	assertFalse("Expected no modification to the graph since vertex already existed", graph.add(vertex1));
    }
    
    @Test
    public void testAddVertexDoesntContainVertex() {
    	Graph<String> graph = emptyInstance();
    	assertTrue("Expected vertex to be added to graph", graph.add(vertex1));
    }
    
    @Test
    public void testSetDirectedEdgeAlreadyExistsAndNonZeroWeight() {
    	Graph<String> graph = emptyInstance();
    	graph.add(vertex1);
    	graph.add(vertex2);
    	graph.set(vertex2, vertex1, 100);
    	graph.set(vertex2, vertex1, 50);
    	assertTrue("Expected weighted edge to be updated from 100 to 50", graph.sources(vertex2).containsValue(50));
    }
    
    @Test
    public void testSetDirectedEdgeDoesntExistAndNonZeroWeight() {
    	Graph<String> graph = emptyInstance();
    	graph.add(vertex1);
    	graph.add(vertex2);
    	graph.set(vertex2, vertex1, 100);
    	assertTrue("Expected new weighted edge with value 100", graph.sources(vertex2).containsValue(100));
    }
    
    @Test
    public void testSetDirectedEdgeAlreadyExistsAndZeroWeight() {
    	Graph<String> graph = emptyInstance();
    	graph.add(vertex1);
    	graph.add(vertex2);
    	graph.set(vertex2, vertex1, 100);
    	graph.set(vertex2, vertex1, 0);
    	assertFalse("Expected weighted edge to be deleted from sources map", graph.sources(vertex2).containsValue(100));
    	assertFalse("Expected key vertex1 to be removed from sources map", graph.sources(vertex2).containsKey(vertex1));
    }
    
    @Test
    public void testSetDirectedEdgeDoesntExistAndZeroWeight() {
    	Graph<String> graph = emptyInstance();
    	graph.add(vertex2);
    	graph.set(vertex2, vertex1, 0);
    	assertTrue("Expected vertex1 to be added by method set(vertex2, vertex1, 0)", graph.vertices().contains(vertex1));
    	assertTrue("Expected sources map not to contain keys", graph.sources(vertex2).keySet().isEmpty());
    }
    
    @Test
    public void testSetDirectedEdgeOneVertexHasntBeenAdded() {
    	Graph<String> graph = emptyInstance();
    	graph.add(vertex1);
    	graph.set(vertex2, vertex1, 40);
    	assertTrue("Expected vertex2 to be added by method set(vertex2, vertex1, 40)", graph.sources(vertex2).containsValue(40));
    }
    
    @Test
    public void testSetDirectedEdgeBothVerticesHaventBeenAdded() {
    	Graph<String> graph = emptyInstance();
    	graph.set(vertex2, vertex1, 20);
    	assertTrue("Expected vertices to be added by method set(vertex2, vertex1, 20)", graph.sources(vertex2).containsValue(20));
    }
    
    @Test
    public void testRemoveContainsVertex() {
    	Graph<String> graph = emptyInstance();
    	graph.add(vertex1);
    	graph.add(vertex2);
    	graph.set(vertex2, vertex1, 10);
    	graph.remove(vertex2);
    	assertFalse("Expected weighted edge to be deleted from sources map", graph.sources(vertex2).containsValue(10));
    	assertTrue("Expected vertex2 to be removed from vertices", graph.vertices().isEmpty());
    }
    
    @Test
    public void testRemoveDoesntContainVertex() {
    	Graph<String> graph = emptyInstance();
    	graph.remove(vertex2);
    	assertTrue("Expected vertices to be empty", graph.vertices().isEmpty());
    }
    
    @Test
    public void testVerticesContainsNoVertices() {
    	Graph<String> graph = emptyInstance();
    	assertTrue("Expected vertices to be empty", graph.vertices().isEmpty());
    }
    
    @Test
    public void testVerticesContainsOneVertex() {
    	Graph<String> graph = emptyInstance();
    	graph.add(vertex2);
    	assertTrue("Expected vertices to contain vertex2", graph.vertices().contains(vertex2));
    	assertFalse("Expected vertices not to contain other vertices than vertex2", graph.vertices().contains(vertex1));
    }
    
    @Test
    public void testVerticesContainsTwoVertices() {
    	Graph<String> graph = emptyInstance();
    	graph.add(vertex2);
    	graph.add(vertex3);
    	assertTrue("Expected vertices to contain vertex2", graph.vertices().contains(vertex2));
    	assertTrue("Expected vertices to contain vertex3", graph.vertices().contains(vertex3));
    	assertFalse("Expected vertices not to contain other vertices than vertex2", graph.vertices().contains(vertex1));
    }
    
    @Test
    public void testSourcesContainsNoSourceVertices() {
    	Graph<String> graph = emptyInstance();
    	assertTrue("Expected sources map not to contain keys", graph.sources(vertex2).keySet().isEmpty());
    }
    
    @Test
    public void testSourcesContainsOneSourceVertex() {
    	Graph<String> graph = emptyInstance();
    	graph.add(vertex1);
    	graph.add(vertex2);
    	graph.set(vertex1, vertex2, 200);
    	assertTrue("Expected sources map to contain key vertex1", graph.sources(vertex2).keySet().contains(vertex1));
    	assertTrue("Expected sources map to contains value 200", graph.sources(vertex2).containsValue(200));
    }
    
    @Test
    public void testSourcesContainsTwoSourceVertices() {
    	Graph<String> graph = emptyInstance();
    	graph.add(vertex1);
    	graph.add(vertex2);
    	graph.add(vertex3);
    	graph.set(vertex3, vertex1, 300);
    	graph.set(vertex2, vertex1, 150);
    	assertTrue("Expected sources map to contain key vertex2", graph.sources(vertex1).keySet().contains(vertex2));
    	assertTrue("Expected sources map to contain key vertex3", graph.sources(vertex1).keySet().contains(vertex3));
    	assertTrue("Expected sources map to contains value 300", graph.sources(vertex1).containsValue(300));
    	assertTrue("Expected sources map to contains value 150", graph.sources(vertex1).containsValue(150));
    }
    
    @Test
    public void testTargetsContainsNoTargetVertices() {
    	Graph<String> graph = emptyInstance();
    	graph.add(vertex1);
    	assertTrue("Expected targets map not to contain keys", graph.targets(vertex1).keySet().isEmpty());
    }
    
    @Test
    public void testTargetsContainsOneTargetVertex() {
    	Graph<String> graph = emptyInstance();
    	graph.add(vertex1);
    	graph.add(vertex3);
    	graph.set(vertex3, vertex1, 250);
    	assertTrue("Expected targets map to contain key vertex1", graph.targets(vertex3).keySet().contains(vertex1));
    	assertTrue("Expected targets map to contains value 250", graph.targets(vertex3).containsValue(250));
    }
    
    @Test
    public void testTargetsContainsTwoTargetVertices() {
    	Graph<String> graph = emptyInstance();
    	graph.add(vertex1);
    	graph.add(vertex2);
    	graph.add(vertex3);
    	graph.set(vertex3, vertex1, 500);
    	graph.set(vertex3, vertex2, 350);
    	assertTrue("Expected sources map to contain key vertex2", graph.targets(vertex3).keySet().contains(vertex1));
    	assertTrue("Expected sources map to contain key vertex3", graph.targets(vertex3).keySet().contains(vertex2));
    	assertTrue("Expected sources map to contains value 500", graph.targets(vertex3).containsValue(500));
    	assertTrue("Expected sources map to contains value 350", graph.targets(vertex3).containsValue(350));
    }
    
}
