package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// DONE: Implement this method
		//Create the sentinel nodes in front and back, without anything connected to it yet
		head = new LLNode<E>();
		tail = new LLNode<E>();
		//Initialize the linked list to have 0 elements in it
		size = 0;
		//Draw the connections between the sentinel nodes
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	//Note that add(E) doesn't specify location, so this adds to the end of the linked list
	public boolean add(E element) {
		// DONE: Implement this method
		//Check the element to see if there's actually an element, if there isn't throw a NPE
		if (element == null) {
			throw new NullPointerException("Not a valid specified element.");
		}
		boolean result = false;
		//Check to see if there are already elements in the linked list, if there is add to the end of the list
		if (size != 0) {
			//First initialize the new node and the new sentinel tail
			LLNode<E> newNode = new LLNode<E>(element);
			LLNode<E> prevNode = tail.prev;
			//Connect relationships
			newNode.next = prevNode.next;
			newNode.prev = newNode.next.prev;
			newNode.next.prev = newNode;
			newNode.prev.next = newNode;
		}
		//If there aren't already elements in the linked list, create based on empty linked list
		else {
			//Initialize the new node and properly configure the relationships
			LLNode<E> newNode = new LLNode<E>(element);
			newNode.next = head.next;
			newNode.prev = newNode.next.prev;
			newNode.next.prev = newNode;
			newNode.prev.next = newNode;
			
		}
		//After adding the element, update the size of the linked list
		size++;
		//Set the result equal to true to express that we added the element to a blank linked list
		result = true;
		//Return the results of running this method
		return result;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) {
		// DONE: Implement this method.
		//Check the integer to check if there is an actual valid input, if not, throw an IOBE
		if (index < 0) {
			throw new IndexOutOfBoundsException("You did not input a valid index.");
		}
		//Check if the requested index actually exists in the linked list, if not, throw an IOBE
		else if (size <= index) {
			throw new IndexOutOfBoundsException("You are requesting an index that does not exist.");
		}
		//If checks pass, actually execute the contents of the get method
		else {
			//Initialize the variables we use to scan through the linked list
			LLNode<E> selectedNode = new LLNode<E>();
			LLNode<E> prevNode = new LLNode<E>();
			prevNode = head.next;
			//If the requested index is 0, then we just assign the selected node to whatever the head node pointed to
			if (index == 0) {
				selectedNode = prevNode;
			}
			//If the requested index bigger than 0, run through a loop that assigns the selects the node at our designated index
			else {
				for (int i = 0; i < index; i++ ) {
					selectedNode = prevNode.next;
					prevNode = selectedNode;
				}
			}
			//Return the data in our selected index
			return selectedNode.data;
		}
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element) {
		// Check the element to see if there's actually an element, if there isn't throw a NPE
		if (element == null) {
			throw new NullPointerException("Not a valid specified element.");
		}
		
		// Check to see if it's actually a valid index, if there isn't throw an IOOB
		if (index < 0) {
			throw new IndexOutOfBoundsException("Invalid index value, can't add to specified index.");
		}
		
		//Check if the requested index actually exists in the linked list, if not, throw an IOBE
		if (size < index) {
			throw new IndexOutOfBoundsException("You are requesting an index that does not exist.");
		}
		
		LLNode<E> newNode = new LLNode<E>();
		LLNode<E> selectedNode = new LLNode<E>();
		LLNode<E> prevNode = new LLNode<E>();
		
		// Conditional statement for if the index is the front of the list
		if (index == 0 && element != null && index >= 0) {
			newNode.next = head.next;
			newNode.prev = newNode.next.prev;
			newNode.next.prev = newNode;
			newNode.prev.next = newNode;
			newNode.data = element;
			size++;
		}
		
		// Conditional statement for if the index is not at the front of the list
		if (index < size && element != null && index > 0) {
			//Initialize the variables we use to scan through the linked list
			prevNode = head.next;
			
			for (int i = 0; i < index - 1; i++ ) {
				selectedNode = prevNode.next;
				prevNode = selectedNode;
			}
			
			newNode.next = prevNode.next;
			newNode.prev = newNode.next.prev;
			newNode.next.prev = newNode;
			newNode.prev.next = newNode;
			newNode.data = element;
			size++;
		}
	}

	/** Return the size of the list */
	public int size() {
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// First make sure the requested index exists in the list
		if (index == size && index == 0 || index >= size) {
			throw new IndexOutOfBoundsException("Cannot remove from empty list.");
		}
		
		// Initiatize the useful nodes for this method
		LLNode<E> selectedNode = new LLNode<E>();
		LLNode<E> prevNode = new LLNode<E>();
		LLNode<E> nextNode = new LLNode<E>();
		
		// Establish the starting point of the list
		prevNode = head.next;
		
		// Two processes depending on the index, re-establishes relationships to disassociate node to remove
		if (index == 0) {
			selectedNode = prevNode;
			nextNode = selectedNode.next;
			head = selectedNode;
		}
		else if (index > 0 ) {
			for (int i = 0; i < index; i++ ) {
				selectedNode = prevNode.next;
				nextNode = selectedNode.next;
				prevNode = selectedNode;
			}
		}
		prevNode.next = nextNode;
		nextNode.prev = prevNode;
		
		// Update the size of the list as well as return the data point that we deleted
		size--;
		return selectedNode.data;

	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		// Check the element to see if there's actually an element, if there isn't throw a NPE
		if (element == null) {
			throw new NullPointerException("Not a valid specified element.");
		}
		
		// Make sure the requested index isn't requesting from an empty list
		if (index == size && index == 0 || index >= size) {
			throw new IndexOutOfBoundsException("Cannot set element in an empty list.");
		}

		// Check to see if it's actually a valid index, if there isn't throw an IOOB
		if (index < 0) {
			throw new IndexOutOfBoundsException("Invalid index value, can't add to specified index.");
		}
		
		//Check if the requested index actually exists in the linked list, if not, throw an IOBE
		if (size == index) {
			throw new IndexOutOfBoundsException("You are requesting the end of the list, please use add(E element) method and do not specify index.");
		}
		if (size < index) {
			throw new IndexOutOfBoundsException("You are requesting an index that does not exist.");
		}
		
		// Initiatize the useful nodes for this method
		LLNode<E> newNode = new LLNode<E>();
		LLNode<E> selectedNode = new LLNode<E>();
		LLNode<E> prevNode = new LLNode<E>();
		LLNode<E> nextNode = new LLNode<E>();
		
		// Establish the starting point of the list
		prevNode = head.next;
		
		// Conditional statement for if the index is the front of the list
		if (index == 0 && element != null && index >= 0) {
			selectedNode = head.next;
			prevNode = selectedNode;

		}
		
		// Conditional statement for if the index is at the back of the list
		for (int i = 0; i < index; i++ ) {
			selectedNode = prevNode.next;
			nextNode = selectedNode.next;
			prevNode = selectedNode;
		}
		
		selectedNode.data = element;		
		// Update the element of the list as well as return the data point that we inputted
		return selectedNode.data;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// DONE: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	//Constructor to create a node with data, but no references yet
	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	//Constructor to create a node with no references
	public LLNode() {
		this.prev = null;
		this.next = null;
	}
	
	//Constructor to allow to create a node with link references
	public LLNode(E e, LLNode<E> prevNode, LLNode<E> nextNode) {
		this(e);
		this.next = prevNode.next;
		prevNode.next = this;
		this.prev = nextNode.prev;
		nextNode.prev = this;
	}

}
