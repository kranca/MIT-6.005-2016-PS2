/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import org.junit.Test;
import java.io.File;
import java.io.IOException;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //   number of edges(): 0, 1 , n
	//   methods: setToGraph(), brigdeAvailable(), getBridgeWord(), toString(), poem()
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // tests
    
    // Test creator
    @Test
    public void testGraphPoetCreator() throws IOException {
    	File corpus = new File("test/poet/test.txt");
    	GraphPoet testGraphPoet = new GraphPoet(corpus);
    	String testWord1 = "some";
    	String testWord2 = "ice";
    	String testWord3 = "desire";
    	//System.out.print(testGraphPoet.getGraph());
    	//System.out.print(testGraphPoet.getGraph().targets(testWord1));
    	assertTrue("Expected word 'some' to be contained in graph", testGraphPoet.getGraph().vertices().contains(testWord1));
    	assertTrue("Expected word 'ice' to be contained in graph", testGraphPoet.getGraph().vertices().contains(testWord2));
    	assertTrue("Expected word 'desire' to be contained in graph", testGraphPoet.getGraph().vertices().contains(testWord3));
    	assertTrue("Expected weighted edge with value 2 for vertices some and say", testGraphPoet.getGraph().targets(testWord1).get("say").equals(2));
    }
    
    // Test setToGraph
    @Test
    public void testGraphPoetSetToGraph() throws IOException {
    	File corpus = new File("test/poet/test.txt");
    	GraphPoet testGraphPoet = new GraphPoet(corpus);
    	
    	assertTrue("Expected word 'some' to be contained in graph", testGraphPoet.getGraph().vertices().contains("i"));
    	assertTrue("Expected word 'ice' to be contained in graph", testGraphPoet.getGraph().vertices().contains("those"));
    	assertTrue("Expected word 'desire' to be contained in graph", testGraphPoet.getGraph().vertices().contains("fire"));
    }
    
    // Test bridge Available
    @Test
    public void testGraphPoetBridgeAvailable() throws IOException {
    	File corpus = new File("test/poet/test.txt");
    	GraphPoet testGraphPoet = new GraphPoet(corpus);
    	String testWord1 = "end";
    	String testWord2 = "fire";
    	assertTrue("Expected bridge word to be found between 'end' and 'fire'", testGraphPoet.bridgeAvailable(testWord1, testWord2));
    	assertFalse("Expected no bridge word to be found between 'fire' and 'end'", testGraphPoet.bridgeAvailable(testWord2, testWord1));
    }
    
    // Test getBridgeWord
    @Test
    public void testGraphPoetGetBridgeWord() throws IOException {
    	File corpus = new File("test/poet/test.txt");
    	GraphPoet testGraphPoet = new GraphPoet(corpus);
    	String testWord1 = "end";
    	String testWord2 = "fire";
    	String expectedBridgeWord = "in";
    	assertEquals("Expected 'in' as bridge word", expectedBridgeWord, testGraphPoet.getBridgeWord(testWord1, testWord2));
    }
    
    // Test toString
    // no need for test, as returns toString() from graph implementation
    
    // Test poem
    @Test
    public void testGraphPoetPoem1() throws IOException {
    	File corpus = new File("test/poet/test.txt");
    	String text = "End fire";
    	GraphPoet testGraphPoet = new GraphPoet(corpus);
    	String expected = "End in fire";
    	
    	System.out.println(testGraphPoet.poem(text));
    	assertEquals("Expected poem: End in fire", expected, testGraphPoet.poem(text));
    }
    
    @Test
    public void testGraphPoetPoem2() throws IOException {
    	File corpus = new File("test/poet/test2.txt");
    	String text = "Seek to explore new and exciting synergies!";
    	GraphPoet testGraphPoet = new GraphPoet(corpus);
    	String expected = "Seek to explore strange new life and exciting synergies";
    	
    	System.out.println(testGraphPoet.poem(text));
    	assertEquals("Expected poem: Seek to explore strange new life and exciting synergies", expected, testGraphPoet.poem(text));
    }
}
