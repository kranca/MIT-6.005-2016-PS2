/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   A graph-based poetry generator
    // Representation invariant:
    //   - Vertices in the graph are words. Words are defined as non-empty case-insensitive 
    //	   strings of non-space non-newline characters.
    //	 - Edge weights are in-order adjacency counts
    // Safety from rep exposure:
    //   Fields are final and private
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
    	ArrayList<String> wordList = new ArrayList<>();
    	Scanner scanner = new Scanner(new BufferedReader(new FileReader(corpus)));
        while (scanner.hasNext()) {
        	wordList.add(scanner.next().replaceAll("[^a-zA-Z0-9]", "").trim().toLowerCase());
        }
        scanner.close();
        setToGraph(wordList);
        checkRep();
    }
    
    // checkRep
    private void checkRep() {
    	assert graph!=null;
    }
    
    //Additional methods:
    // getGraph
    /**
     * Safely access graph
     * @return graph
     */
    public Graph<String> getGraph(){
    	return graph;
    }
    
    // setToGrapgh
    /**
     * Sets every word of text to graph
     * @param text - text splited in words in a List
     */
    public void setToGraph(ArrayList<String> text) {
    	int n = 0;
    	// set pairs of word until list is empty
		while (n < (text.size()-1)) {
			int previousWeight = graph.set(text.get(n), text.get(n+1), 1);
			// if a pair of words was already set in graph, get previous weight and set previous weight +1 for
			// the same pair of words
			if (previousWeight > 0) {
				graph.set(text.get(n), text.get(n+1), previousWeight+1);
			}
			n+=1;
		}
    }
    
    // bridgeAvailable
    /**
     * Checks if there is a word bridge available between word1 and word2
     * @param word1 - String to be checked for bridge word
     * @param word2 - String to be checked for bridge word
     * @return true if a bridge word is available, false otherwise
     */
    public boolean bridgeAvailable(String word1, String word2) {
    	// if there is matching word in targets(word1) and sources(word2), return true
    	for (String word:getGraph().targets(word1).keySet()) {
    		if (getGraph().sources(word2).keySet().contains(word)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    // getBridgeWord
    /**
     * Gets bridge word to fit between word1 and word2
     * @param word1 - String to be checked for bridge word
     * @param word2 - String to be checked for bridge word
     * @return
     */
    public String getBridgeWord(String word1, String word2) {
    	String bridgeWord = "";
    	// same logic as bridgeAvailable
    	ArrayList<String> possibleWords = new ArrayList<>();
    	for (String word:getGraph().targets(word1).keySet()) {
    		if (getGraph().sources(word2).keySet().contains(word)) {
    			possibleWords.add(word);
    		}
    	}
    	
    	// if more than one bridge word is available, the one with the highest weight value has to be selected
    	if (possibleWords.size()>1) {
    		HashMap<String, Integer> possibleWordsMap = new HashMap<>();
    		// put possible words in map with weight value
    		for (String bridge:possibleWords) {
    			possibleWordsMap.put(bridge, getGraph().targets(word1).get(bridge));
    		}
    		// define max weight value
    		int maxValue = 0;
    		for (int value:possibleWordsMap.values()) {
    			if (value > maxValue) {
    				maxValue = value;
    			}
    		}
    		// return word with highest weight value
    		for (String bridgeFinal:possibleWordsMap.keySet()) {
    			if (possibleWordsMap.get(bridgeFinal).equals(maxValue)) {
    				return bridgeFinal;
    			}
    		}
    	}
    	// case possibleWords size is 0
    	else {
    		return possibleWords.get(0);
    	}
    	return bridgeWord;
    }
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
    	List<String> wordList = Arrays.asList(input.trim().split("[^a-zA-Z0-9]"));
    	ArrayList<String> poemList = new ArrayList<>();
    	int n = 0;
    	// set pairs of word until list is empty
		while (n < (wordList.size()-1)) {
			poemList.add(wordList.get(n));
			if (bridgeAvailable(wordList.get(n).toLowerCase(),wordList.get(n+1).toLowerCase())) {
				poemList.add(getBridgeWord(wordList.get(n).toLowerCase(),wordList.get(n+1).toLowerCase()));
			}
			n+=1;
		}
		poemList.add(wordList.get(wordList.size()-1));
		String poem = "";
		// return from list to string form
		for (String word:poemList) {
			poem+= " " + word;
		}
		return poem.trim();
    }
    
    // toString()
    public String toString() {
    	return graph.toString();
    }
}
