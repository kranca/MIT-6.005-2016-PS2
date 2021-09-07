/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   Represents the edge between one source vertex and one target vertex in a graph, which must have a positive weight
    // Representation invariant:
    //   weight must be a positive integer
    //	 must have source and target string values representing vertices
    // Safety from rep exposure:
    //   field is final, since vertices is a mutable list defensive copies will be done
    
    // constructor
    public ConcreteVerticesGraph() {
    	checkRep();
    }
    
    // checkRep
    private void checkRep() {
    	assert vertices!=null;
    }
    
    // Additional methods:
    
    //findVertexWithName()
    /**
     * Finds vertex with string name in vertices
     * @param name - String name of the vertex to be searched for
     * @return Vertex with name "name"
     */
    private Vertex findVertexWithName(String name) {
    	for (Vertex vertex:vertices) {
    		if(vertex.getName().equals(name)) {
    			checkRep();
    			return vertex;
    		}
    	}
    	throw new AssertionError("Vertex with name '" + name + "' not found");
    }
    
    //vertexFound()
    /**
     * returns True if a Vertex with name "name" is found in vertices, False otherwise
     * @param name - String name of the Vertex to be found
     * @return True if Vertex is found, False otherwise
     */
    private Boolean vertexFound(String name) {
    	List<String> vertexList = new ArrayList<>();
    	for (Vertex vertex:vertices) {
    		vertexList.add(vertex.getName());
    	}
    	checkRep();
    	return vertexList.contains(name);
    }
    
    @Override public boolean add(String vertex) {
        Boolean check=false;
        Vertex new_vertex = new Vertex(vertex);
        // in this implementation add methods modifies a list instead of a set (set cannot have duplicate objects),
        // therefore list has to be inspected
        
        // if vertices list is empty directly add new vertex
        if (vertices.isEmpty()) {
        	check = vertices.add(new_vertex);
        	checkRep();
        	return check;
        }
        // if list is not empty, check if a vertex with the same name already exists before adding a new vertex
        if (!this.vertexFound(vertex)) {
        	check = vertices.add(new_vertex);
        	checkRep();
        	return check;
        }
        return check;
    }
    
    @Override public int set(String source, String target, int weight) {
    	int previousWeight = 0;
        if (weight > 0) {
        	// check if vertices already exist in list
        	// case both source and target vertices exist already
        	if(this.vertexFound(source) && this.vertexFound(target)) {
        		try {
        			previousWeight = this.findVertexWithName(source).getWeight(this.findVertexWithName(target));
        			checkRep();
        		}
        		catch(NullPointerException e) {
        			previousWeight = 0;
        		}
        		this.findVertexWithName(source).setEdgeTo(this.findVertexWithName(target), weight);
        		checkRep();
        		return previousWeight;
        	}
        	// case source is not in vertices
        	else if (!this.vertexFound(source) && this.vertexFound(target)) {
        		this.add(source);
        		this.findVertexWithName(source).setEdgeTo(this.findVertexWithName(target), weight);
        		checkRep();
        		return previousWeight;
        	}
        	// case target is not in vertices
        	else if (this.vertexFound(source) && !this.vertexFound(target)) {
        		this.add(target);
        		this.findVertexWithName(source).setEdgeTo(this.findVertexWithName(target), weight);
        		checkRep();
        		return previousWeight;
        	}
        	// case both source and target are not in vertices
        	else {
        		this.add(source);
        		this.add(target);
        		this.findVertexWithName(source).setEdgeTo(this.findVertexWithName(target), weight);
        		checkRep();
        		return previousWeight;
        	}
        }
        // case weight is zero and edge has to be deleted
        else {
        	// case both source and target vertices exist already
        	if(this.vertexFound(source) && this.vertexFound(target)) {
        		try {
        			previousWeight = this.findVertexWithName(source).remove(this.findVertexWithName(target));
        			checkRep();
        		}
        		catch(NullPointerException e) {
        			previousWeight = 0;
        		}
            	return previousWeight;
        	}
        	// case source is not in vertices
        	else if (!this.vertexFound(source) && this.vertexFound(target)) {
        		this.add(source);
        		checkRep();
        		return previousWeight;
        	}
        	// case target is not in vertices
        	else if (this.vertexFound(source) && !this.vertexFound(target)) {
        		this.add(target);
        		checkRep();
        		return previousWeight;
        	}
        	// case both source and target are not in vertices
        	else {
        		this.add(source);
        		this.add(target);
        		checkRep();
        		return previousWeight;
        	}
        	
        	
        }
    	
    }
    
    @Override public boolean remove(String vertex) {
        boolean check = false;
        // if vertex to be removed is in vertices list
        try {
        	if (vertices.contains(this.findVertexWithName(vertex))) {
            	// check for possible directed edges in each source vertex and remove directed edge
            	for (Vertex ver:vertices) {
            		if (ver.getDirectedEdges().contains(this.findVertexWithName(vertex))) {
            			ver.remove(this.findVertexWithName(vertex));
            		}
            	}
            	// finally remove vertex from vertices list
            	check = vertices.remove(this.findVertexWithName(vertex));
            }
            checkRep();
        }
        catch (AssertionError e) {
        	
        }
        
        return check;
    }
    
    @Override public Set<String> vertices() {
        Set<String> copy_vertices = new HashSet<>();
        // copy every vertex from vertices<Vertex> to copy_vertices set
        for (Vertex vertex:vertices) {
        	copy_vertices.add(vertex.getName());
        }
        checkRep();
        return copy_vertices;
    }
    
    @Override public Map<String, Integer> sources(String target) {
        TreeMap<String, Integer> sources = new TreeMap<>();
    	// look for vertex with name target
    	if (this.vertexFound(target)) {
        	// look within vertices for edges containing target as target vertex
    		for (Vertex vertex:vertices) {
        		if (vertex.getDirectedEdgesMap().containsKey(target)) {
        			sources.put(vertex.getName(), vertex.getDirectedEdgesMap().get(target));
        		}
        	}
    		return sources;
        }
    	// else return empty map
    	else {
    		return sources;
    	}
    }
    
    @Override public Map<String, Integer> targets(String source) {
        // look for vertex with name source, return converted map with string keys
    	if (this.vertexFound(source)) {
    		return this.findVertexWithName(source).getDirectedEdgesMap();
    	}
    	//else return empty map
    	else {
    		return new TreeMap<String, Integer>();
    	}
    }
    
    // toString()
    @Override public String toString() {
    	String stringRep = "";
    	for (Vertex vertex:vertices) {
    		stringRep += vertex.toString();
    	}
    	return stringRep;
    }
    
}

/**
 * specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * Vertex type represents a vertex in a graph and its connections to other vertices in the same graph.
 * If there is a connection between two vertices, there must be a positive weighted edge.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex implements Comparable<Vertex> {
    
	private final String name;
	private final Map<Vertex, Integer> directedEdges = new TreeMap<>();
    
    // Abstraction function:
    //   Represents a vertex, which might or might not be connected to more vertices.
	//	 If the vertex is connected to another Vertex the connection is documented in HashMap directedEdges.
    // Representation invariant:
    //   Values in directedEdges must be a positive integers
	//	 
    // Safety from rep exposure:
    //   fields are private and final, Map is defensive copied
    
    // constructor
	/**
	 * Creates new vertex
	 * @param name - name of the vertex
	 */
	public Vertex(String name) {
		this.name = name;
		checkRep();
	}
    
    // checkRep
	private void checkRep() {
		assert name != null;
		for (Integer weight:directedEdges.values()) {
			assert weight>0;
		}
	}
    
    // TODO methods
	/**
	 * Safely access Vertex name
	 * @return String - vertex name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Safely access directed edges from source vertex
	 * @return new HashSet with copied Key values from directedEdges HashMap
	 */
	public Set<Vertex> getDirectedEdges() {
		return new HashSet<Vertex>(directedEdges.keySet());
	}
	
	/**
	 * Creates a copy of directedEdges with String keys instead of vertices
	 * @return copy of directedEdges with String keys
	 */
	public Map<String, Integer> getDirectedEdgesMap() {
		TreeMap<String, Integer> copyDirectedEdges = new TreeMap<>();
		for (Vertex vertex:this.directedEdges.keySet()) {
			copyDirectedEdges.put(vertex.getName(), this.directedEdges.get(vertex));
		}
		checkRep();
		return copyDirectedEdges;
	}
	
	/**
	 * Safely access edge weight from source vertex to target vertex
	 * @param targetVertex String - name of target vertex
	 * @return integer - weight of edge between source and target vertices
	 */
	public Integer getWeight(Vertex target){
		return new Integer (directedEdges.get(target));
	}
	
	/**
	 * Creates connection to target vertex and assigns a positive weight
	 * @param target - target vertex
	 * @param weight - weight value to target vertex
	 */
	public void setEdgeTo(Vertex target, Integer weight) {
		directedEdges.put(target, weight);
		checkRep();
	}
	
	/**
	 * removes a vertex from source directedEdges
	 * @param target - target vertex
	 * @return previous weight
	 */
	public int remove(Vertex target) {
		int previousWeight = 0;
		try {
			previousWeight = directedEdges.remove(target);
			checkRep();
		}
		catch (NullPointerException e) {
			
		}
		return previousWeight;
	}
    
	// toString
	@Override public String toString() {
		String stringRep = "";
		String source = this.getName();
		for (Vertex target:getDirectedEdges()) {
			stringRep +=  source + " -> " + target.getName() + ": " + getWeight(target) + "\n";
		}
		checkRep();
		return stringRep;
	}
    
	// compareTo needed to sort vertices by name in map
	// multipilied by -1 to reverse order in TreeMap
	@Override public int compareTo(Vertex thatVertex) {
		int compare = (this.getName().compareTo(thatVertex.getName()))*-1;
		checkRep();
		return compare;
	}
}
