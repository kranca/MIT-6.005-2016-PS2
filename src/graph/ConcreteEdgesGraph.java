/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   represents a graph, in which vertices might be connected via weighted edges
    // Representation invariant:
    //   Edges have positive weights, zero weights delete edge from graph
    // Safety from rep exposure:
    //   All fields are private
    //   vertices are mutable sets, therefore vertices() makes defensive copies
    
    // constructor
    public ConcreteEdgesGraph() {
    	checkRep();
    }
    

	// checkRep
    private void checkRep() {
		assert vertices!=null;
		for (Edge<L> edge:edges) {
			assert edge.getWeight()>0;
		}
	}
    
    @Override public boolean add(L vertex) {
    	boolean check = vertices.add(vertex);
    	checkRep();
    	return check;
    }
    
    @Override public int set(L source, L target, int weight) {
        int previousWeight = 0;
        // check if edge is already in edges list
        for (Edge<L> edge:edges) {
        	L compareSource = edge.getSource();
        	L compareTarget = edge.getTarget();
        	// edge is already in edges list
        	if (compareSource==source && compareTarget==target) {
        		previousWeight = edge.getWeight();
        		// if weight is 0, remove edge
        		if (weight==0) {
        			edges.remove(edge);
        			checkRep();
        			return previousWeight;
        		}
        		// if weight is greater than 0, edge has to be removed and added with updated weight value, since weight is final
        		if (weight>0) {
        			edges.remove(edge);
        			edges.add(new Edge<L>(source, target, weight));
        			checkRep();
        			return previousWeight;
        		}
        	}
        	
        }
        //edge is not in edges list, check if vertices has source and target nodes and add them if needed
    	if (!vertices.contains(source)) {
    		vertices.add(source);
    	}
		if (!vertices.contains(target)) {
			vertices.add(target);
		}
		// if weight > 0, add edge
		if (weight>0) {
			edges.add(new Edge<L>(source, target, weight));
		}
        // returns 0 for all cases in which a previous weight wasn't available
        checkRep();
        return previousWeight;
    }
    
    @Override public boolean remove(L vertex) {
        List<Edge<L>> toRemove = new ArrayList<>();
    	boolean vertexRemoved = false;
    	for (Edge<L> edge:edges) {
        	if (edge.getSource().equals(vertex) || edge.getTarget().equals(vertex)) {
        		toRemove.add(edge);
        	}
        }
    	// ... = vertices.remove(
    	vertexRemoved = edges.removeAll(toRemove);
    	vertices.remove(vertex);
		checkRep();
		return vertexRemoved;
    }
    
    @Override public Set<L> vertices() {
        // defensive copy of Set
    	//System.out.println(vertices);
    	return new HashSet<L>(vertices);
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L, Integer> sourcesMap = new TreeMap<L, Integer>();
        //System.out.println(edges);
        for (Edge<L> edge:edges) {
        	if (edge.getTarget().equals(target)) {
        		sourcesMap.put(edge.getSource(), edge.getWeight());
        	}
        }
        //System.out.println(sourcesMap);
        return sourcesMap;
    }
    
    @Override public Map<L, Integer> targets(L source) {
    	Map<L, Integer> targetsMap = new TreeMap<L, Integer>();
        for (Edge<L> edge:edges) {
        	if (edge.getSource().equals(source)) {
        		targetsMap.put(edge.getTarget(), edge.getWeight());
        	}
        }
        return targetsMap;
    }
    
    // toString()
    @Override public String toString() {
    	String stringRep = "";
    	
    	for (Edge<L> edge:edges) {
    		stringRep += edge.toString();
    	}
    	return stringRep;
    	
    }
    
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    
    private final Integer weight;
    private final L source;
    private final L target;
    
    // Abstraction function:
    //   Represents the edge between one source vertex and one target vertex in a graph, which must have a positive weight
    // Representation invariant:
    //   weight must be a positive integer
    //	 must have source and target string values representing vertices
    // Safety from rep exposure:
    //   fields are final strings and integer, no method can modify edges
    
    // constructor
    /** 
     * creates an edge with source, target and weight parameters
     * 
     * @param source is the source of the edge
     * @param target is the target to which the edge is directed
     * @param weight is the weight of the edge
     */
    public Edge(L source, L target, Integer weight) {
    	this.source = source;
    	this.target = target;
    	this.weight = weight;
    	checkRep();
    }
	
    
    // checkRep
    private void checkRep() {
    	assert source!=null;
    	assert target!=null;
    	assert weight>0;
    }
    // methods
	public Integer getWeight() {
		return weight;
	}
	
	public L getSource() {
		return source;
	}
	
	public L getTarget() {
		return target;
	}
    
    // toString()
	@Override public String toString() {
		String stringRep = "";
		String edgeSource = getSource().toString();
		String edgeTarget = getTarget().toString();
		String edgeWeight = getWeight().toString();
		
		stringRep = stringRep + edgeSource + " -> " + edgeTarget + ": " + edgeWeight + "\n";
		checkRep();
		return stringRep;
	}
    
}
