package textgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// Step 0:  Parse data and add to a list of words
		String[] parsedText = sourceText.split(" +");
		
		// Step 1:  Set "starter" to be the first word in the text
		starter = parsedText[0];
		
		// Step 2:  Set "prevWord" to be starter
		String prevWord = starter;
		
		// Step 2.5:  Clear wordList
		wordList.clear();
		
		// Step 3:  For each word "w" in the source text starting at the second word
		for (String w : parsedText) {
			// If examined word is the same referenced object as the starter
			if (w == starter){
				// add a node to the list with "prevWord" as the node's word
				ListNode newNode = new ListNode(prevWord);
				wordList.add(newNode);
			}
			
			// If the examined word is not the same as the starter word
			else {
				// Step 4:  Check to see if node with "prevWord" exists
				if (getListNode(prevWord).getWord()==null) {
					// add a node to the list with "prevWord" as the node's word
					ListNode newNode = new ListNode(prevWord);
					wordList.add(newNode);
					// add "w" as a nextWord to the "prevWord" node
					newNode.addNextWord(w);
				}
				// If it exists, add it to the list of next words
				else {
					getListNode(prevWord).addNextWord(w);
				}
			}
			
		    // set "prevWord" = "w"
			prevWord = w;
			
			// Step 5:  Add starter to be a next word for the last word in the source text.*/
			if (prevWord.equals(parsedText[parsedText.length - 1])) {
				ListNode newNode = new ListNode(prevWord);
				wordList.add(newNode);
				newNode.addNextWord(starter);
			}
		}		
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
		// set "currWord" to be the starter word
		String currWord = starter;
		
		// set "output" to be ""
		StringBuilder output = new StringBuilder("");
		
		//add "currWord" to output
		output.append(currWord);
		
		//while you need more words
		int counter = 1;
		if (starter == "" || numWords == 0) {
			String noText = "";
			return noText;
		}
		else {
			while (counter < numWords) {
				//find the "node" corresponding to "currWord" in the list
				ListNode correspondingNode = getListNode(currWord);
				
				//select a random word "w" from the "wordList" for "node"
				String w = correspondingNode.getRandomNextWord(rnGenerator);
				
				//add "w" to the "output"
				output.append(" " + w);
				
				//set "currWord" to be "w" 
				currWord = w;

				//increment number of words added to the list
				counter++;
			}
			String generatedText = output.toString();
			return generatedText;
		}
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// Step 0:  Parse data and add to a list of words
		String[] parsedText = sourceText.split(" +");
		
		// Step 1:  Set "starter" to be the first word in the text
		starter = parsedText[0];
		// Step 2:  Set "prevWord" to be starter
		String prevWord = starter;
		
		// Step 2.5:  Clear wordList
		wordList.clear();
		
		// Step 3:  For each word "w" in the source text starting at the second word
		for (String w : parsedText) {
			// If examined word is the same referenced object as the starter
			if (w == starter){
				// add a node to the list with "prevWord" as the node's word
				ListNode newNode = new ListNode(prevWord);
				wordList.add(newNode);
			}
			
			// If the examined word is not the same as the starter word
			else {
				// Step 4:  Check to see if node with "prevWord" exists
				if (getListNode(prevWord).getWord()==null) {
					// add a node to the list with "prevWord" as the node's word
					ListNode newNode = new ListNode(prevWord);
					wordList.add(newNode);
					// add "w" as a nextWord to the "prevWord" node
					newNode.addNextWord(w);
				}
				// If it exists, add it to the list of next words
				else {
					getListNode(prevWord).addNextWord(w);
				}
			}
			
		    // set "prevWord" = "w"
			prevWord = w;
			
			// Step 5:  Add starter to be a next word for the last word in the source text.*/
			if (prevWord.equals(parsedText[parsedText.length - 1])) {
				ListNode newNode = new ListNode(prevWord);
				wordList.add(newNode);
				newNode.addNextWord(starter);
			}
		}		
	}
	
	// Add any private helper methods you need here.
	private ListNode getListNode(String word)
	{
		ListNode resultNode = new ListNode(null);
		for (ListNode compareNode : wordList) {
			if (compareNode.getWord().equals(word)) {
				resultNode = compareNode;
			}
		}
		return resultNode;
	}
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		// select a random word from the "wordList" for "node"
		String randomNextWord = nextWords.get(generator.nextInt(nextWords.size()));
	    return randomNextWord;
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


