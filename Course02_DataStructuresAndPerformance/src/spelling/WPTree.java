/**
 * 
 */
package spelling;

//import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * WPTree implements WordPath by dynamically creating a tree of words during a Breadth First
 * Search of Nearby words to create a path between two words. 
 * 
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class WPTree implements WordPath {

	// this is the root node of the WPTree
	private WPTreeNode root;
	// used to search for nearby Words
	private NearbyWords nw; 
	
	// This constructor is used by the Text Editor Application
	// You'll need to create your own NearbyWords object here.
	public WPTree () {
		this.root = null;
		Dictionary d = new DictionaryHashSet();
		DictionaryLoader.loadDictionary(d, "data/dict.txt");
		this.nw = new NearbyWords(d);
	}
	
	//This constructor will be used by the grader code
	public WPTree (NearbyWords nw) {
		this.root = null;
		this.nw = nw;
	}
	
	// see method description in WordPath interface
	public List<String> findPath(String word1, String word2) 
	{
//	    Input:  word1 which is the start word
//		Input:  word2 which is the target word
//		Output: list of a path from word1 to word2 (or null)
//
//		Create a queue of WPTreeNodes to hold words to explore
		List<WPTreeNode> queue = new LinkedList<WPTreeNode>();
//		Create a visited set to avoid looking at the same word repeatedly
		HashSet<String> visited = new HashSet<String>();
//		Set the root to be a WPTreeNode containing word1
		root = new WPTreeNode(word1, null);
		
//		Add the initial word to visited
		visited.add(root.getWord());
//		Add root to the queue
		queue.add(root);
		WPTreeNode curr = queue.get(0);

//		while the queue has elements and we have not yet found word2
		while(!queue.isEmpty() && !curr.getWord().equals(word2)) {
//			remove the node from the start of the queue and assign to curr
			curr = queue.get(0);
			//System.out.println(word1 + ": " + "Initial Queue: " + printQueue(queue));
			queue.remove(0);
			//System.out.println(word1 + ": " + "Remove First Element: " + printQueue(queue));
//			get a list of real word neighbors (one mutation from curr's word)
			List<String> eval = new LinkedList<String>();
			eval = nw.distanceOne(curr.getWord(), true);
			//System.out.println(word1 + ": " + "Evaluate: " + eval);
//			for each n in the list of neighbors
			for (String n : eval) {
//			    if n is not visited
				//System.out.print(word1 + ": " + "String in Question: " + n);
				//System.out.println("   " + "Visited Words: " + visited);
				if (!visited.contains(n)) {
//				    add n as a child of curr 
					WPTreeNode checked = curr.addChild(n);
//				    add n to the visited set
					visited.add(curr.getWord());
					//System.out.println(word1 + ": " + "Add New to Visited: " + visited);
//				    add the node for n to the back of the queue
					queue.add(checked);
					//System.out.println(word1 + ": " + "Resulting Queue: " + printQueue(queue));
					//System.out.println(word1 + " " + n);
//				    if n is word2
					if (n.equals(word2)) {
						List<String> finalList = curr.buildPathToRoot();
						finalList.add(word2);
//				        return the path from child to root
						//System.out.println(n);
						return finalList;
						
					}
				}
			}
		}

//		return null as no path exists
		return null;
	    //return new LinkedList<String>();
	}
	
	// Method to print a list of WPTreeNodes (useful for debugging)
	private String printQueue(List<WPTreeNode> list) {
		String ret = "[ ";
		
		for (WPTreeNode w : list) {
			ret+= w.getWord()+", ";
		}
		ret+= "]";
		return ret;
	}
	
}

/* Tree Node in a WordPath Tree. This is a standard tree with each
 * node having any number of possible children.  Each node should only
 * contain a word in the dictionary and the relationship between nodes is
 * that a child is one character mutation (deletion, insertion, or
 * substitution) away from its parent
*/
class WPTreeNode {
    
    private String word;
    private List<WPTreeNode> children;
    private WPTreeNode parent;
    
    /** Construct a node with the word w and the parent p
     *  (pass a null parent to construct the root)  
	 * @param w The new node's word
	 * @param p The new node's parent
	 */
    public WPTreeNode(String w, WPTreeNode p) {
        this.word = w;
        this.parent = p;
        this.children = new LinkedList<WPTreeNode>();
    }
    
    /** Add a child of a node containing the String s
     *  precondition: The word is not already a child of this node
     * @param s The child node's word
	 * @return The new WPTreeNode
	 */
    public WPTreeNode addChild(String s){
        WPTreeNode child = new WPTreeNode(s, this);
        this.children.add(child);
        return child;
    }
    
    /** Get the list of children of the calling object
     *  (pass a null parent to construct the root)  
	 * @return List of WPTreeNode children
	 */
    public List<WPTreeNode> getChildren() {
        return this.children;
    }
   
    /** Allows you to build a path from the root node to 
     *  the calling object
     * @return The list of strings starting at the root and 
     *         ending at the calling object
	 */
    public List<String> buildPathToRoot() {
        WPTreeNode curr = this;
        List<String> path = new LinkedList<String>();
        while(curr != null) {
            path.add(0,curr.getWord());
            curr = curr.parent; 
        }
        return path;
    }
    
    /** Get the word for the calling object
     *
	 * @return Getter for calling object's word
	 */
    public String getWord() {
        return this.word;
    }
    
    /** toString method
    *
	 * @return The string representation of a WPTreeNode
	 */
    public String toString() {
        String ret = "Word: "+word+", parent = ";
        if(this.parent == null) {
           ret+="null.\n";
        }
        else {
           ret += this.parent.getWord()+"\n";
        }
        ret+="[ ";
        for(WPTreeNode curr: children) {
            ret+=curr.getWord() + ", ";
        }
        ret+=(" ]\n");
        return ret;
    }

}

