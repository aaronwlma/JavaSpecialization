package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

	//Initialize variables
    private TrieNode root;
    private int size;
    
    //Constructor
    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
    
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		// Initialize useful variables
		TrieNode selectedNode = root;
		boolean checkWord = false;
		
		// For-loop that checks each character for a node
		for (int i = 0; i <= word.length() - 1; i++) {
			// If the selected node is the last element, then add a node with the new word
			if (selectedNode.getChild(word.toLowerCase().charAt(i)) == null) {
				TrieNode addedNode = selectedNode.insert(word.toLowerCase().charAt(i));
				// Check if it is the last element of the word, if it is, set it as an end word
				if (i == word.length() - 1) {
					if (addedNode.endsWord() == true) {
						return checkWord;
					}
					addedNode.setEndsWord(true);
					checkWord = true;
				}
				selectedNode = addedNode;
			}
			// If the selected node does not have a character key for the word, make it
			else {
				if (i == word.length() - 1) {
					selectedNode.getChild(word.toLowerCase().charAt(i)).setEndsWord(true);
				}
				selectedNode = selectedNode.getChild(word.toLowerCase().charAt(i));
			}
		}
		return checkWord;

	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
		int count = 0;
		
		if (root != null) {
			// Use a level-order traversal to find nodes with endsWord
			// Initiate useful variables
			Queue<TrieNode> q = new LinkedList<TrieNode>();
			q.add(root);
			
			// While loop that counts up while the queue has stuff in it
			while (!q.isEmpty()) {
				TrieNode curr = q.remove();
				if (curr.endsWord()) {
					count++;
				}
				for (char c : curr.getValidNextCharacters() ) {
					if (curr.getChild(c) != null) {
						q.add(curr.getChild(c));
					}

				}

			}
			
		}
		
		return count;
	}
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		TrieNode node = root;
		boolean hasNext = false;
		
		for (int i = 0; i < s.length(); i++) {
			TrieNode nodeNext = node.getChild(s.toLowerCase().charAt(i));

			if (nodeNext == null) {
				hasNext = false;
			}
			else {
				node = nodeNext;
				hasNext = true;
			}
			
		}
		return hasNext;
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // Initialize Variables
    	 TrieNode startNode = root;
    	 List<String> suggestedWords = new LinkedList<String>();
    	 List<String> emptyWords = Collections.emptyList();
    	 int maxWords = 0;
    	 
    	 // For-loop to traverse to the end of the stem for the prefix
    	 for (int i = 0; i <= prefix.length()-1; i++) {
    		 // If stems don't appear in the trie, return an empty list
    		 if (startNode.getChild(prefix.toLowerCase().charAt(i)) == null) {
    			 return emptyWords;		 
    		 }
    		 // If it's not null, just move to the child pointer
    		 startNode = startNode.getChild(prefix.toLowerCase().charAt(i));
    	 }
    	 
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 if (startNode != null) {
 			// Use a level-order traversal (aka breadth first) to find nodes with endsWord
 			// Initiate useful variables
 			Queue<TrieNode> q = new LinkedList<TrieNode>();
 			q.add(startNode);
 			
 			// While loop that counts up while the queue has stuff in it
 			while (!q.isEmpty()) {
 				TrieNode curr = q.remove();
 				if (curr.endsWord()) {
 					// TODO: ADD THE WORD AT CURR TO THE LIST OF SUGGESTED WORDS
 					if (maxWords < numCompletions) {
 	 					suggestedWords.add(curr.getText());
 	 					maxWords++;
 					}

 				}
 				for (char c : curr.getValidNextCharacters() ) {
 					if (curr.getChild(c) != null) {
 						q.add(curr.getChild(c));
 					}
 				}
 			}
 		}
        return suggestedWords;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}