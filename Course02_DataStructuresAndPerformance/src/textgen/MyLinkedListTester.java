/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		// Test 1:  Attempt to remove from an empty list
		try {
			emptyList.remove(0);
			fail("Removing an element from an empty list is possible.");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
		// Test 2:  Attempt to remove from lists at different locations and check
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		String b = shortList.remove(1);
		assertEquals("Remove: check b is correct ", "B", b);
		assertEquals("Remove: check remaining element is correct ", "A", shortList.get(0));
		assertEquals("Remove: check size is correct ", 1, shortList.size());
		int c = longerList.remove(5);
		assertEquals("Remove: check c is correct ", 5, c);
		assertEquals("Remove: check element 5 is correct ", (Integer)5, longerList.get(5));
		assertEquals("Remove: check size is correct ", 9, longerList.size());

		// Test 3:  Attempt to remove from an index out of bounds
		try {
			longerList.remove(10);
			fail("Removing an index spot that doesn't exist is possible.");
		}
		catch (IndexOutOfBoundsException e) {
		}

	}
	
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
        // DONE: implement this test
		
		// Initiate test values to add
		int elementsToAdd = 100;
		
		// Test 1:  Test adding a null element to the end of an empty list
		try {
			emptyList.add(null);
			fail("Check adding null value to the list");
		}
		catch (NullPointerException e) {	
		}
		
		// Test 2:  Light stress test, check if adding 100 elements get added correctly to our lists
		// Add 100 elements to our test list
		for (int i = 0; i < elementsToAdd; i++)
		{
			emptyList.add(i);
			shortList.add(Integer.toString(i));
			longerList.add(i);
			list1.add(i);
		}
		// Check elements in empty list
		for(int i = 0; i < elementsToAdd - 1; i++) {
			assertEquals("Check " + i + " element in emptyList(int) list", (Integer)i, emptyList.get(i));
		}
		// Check elements in short list
		for (int i = 0; i < elementsToAdd + 1; i++) {
			if (i == 0) {
				assertEquals("Check " + i + " element in shortList(string) list", "A", shortList.get(i)); 
			}
			else if (i == 1) {
				assertEquals("Check " + i + " element in shortList(string) list", "B", shortList.get(i)); 
			}
			else {
				assertEquals("Check " + i + " element in shortList(string) list", String.valueOf(i - 2), shortList.get(i)); 
			}
		}
		// Check elements in longer list
		for (int i = 0; i < elementsToAdd + LONG_LIST_LENGTH; i++) {
			if (i < 10) {
				assertEquals("Check " + i + " element in longerList(int) list", (Integer)i, longerList.get(i));
			}
			else {
				assertEquals("Check " + i + " element in longerList(int) list", (Integer)(i - 10), longerList.get(i));
			}	 
		}
		// Check elements in list1
		for (int i = 0; i < elementsToAdd + 2; i++) {
			if (i == 0) {
				assertEquals("Check " + i + " element in list1(int) list", (Integer)65, list1.get(i));
			}
			else if (i == 1) {
				assertEquals("Check " + i + " element in list1(int) list", (Integer)21, list1.get(i));
			}
			else if (i == 2) {
				assertEquals("Check " + i + " element in list1(int) list", (Integer)42, list1.get(i));
			}
			else {
				assertEquals("Check " + i + " element in list1(int) list", (Integer)(i - 3), list1.get(i));
			}
		}
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// DONE: implement this test
		// Test 1:  Test initial sizes
		assertEquals("Checking emptyList(int) for initial size...", (Integer)0, (Integer)emptyList.size());
		assertEquals("Checking shortList(string) for initial size...", (Integer)2, (Integer)shortList.size());
		assertEquals("Checking longerList(int) for initial size...", (Integer)10, (Integer)longerList.size());
		assertEquals("Checking list1(int) for initial size...", (Integer)3, (Integer)list1.size());
		
		// Test 2:  Test after adding 100 elements
		// Initiate test values to add
		int elementsToAdd = 100;
		// Add 100 elements to our test list
		for (int i = 0; i < elementsToAdd; i++)
		{
			emptyList.add(i);
			shortList.add(Integer.toString(i));
			longerList.add(i);
			list1.add(i);
		}
		// Check if counts the right amount after adding
		assertEquals("Checking emptyList(int) for updated size...", (Integer)100, (Integer)emptyList.size());
		assertEquals("Checking shortList(string) for updated size...", (Integer)102, (Integer)shortList.size());
		assertEquals("Checking longerList(int) for updated size...", (Integer)110, (Integer)longerList.size());
		assertEquals("Checking list1(int) for updated size...", (Integer)103, (Integer)list1.size());
		
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
		// Initialize test variables
		int testSetInt = 999;
		String testSetString = "Pusheen";
		
		// Test 1:  Test adding an element to an invalid index to the end of an empty list
		try {
			emptyList.add(-1, testSetInt);
			fail("Check adding invalid index value to the list");
		}
		catch (IndexOutOfBoundsException e) {	
		}
		
		// Test 2:  Test adding an element to an index that doesn't exist yet to the end of an empty list
		try {
			emptyList.add(1, testSetInt);
			fail("Check adding nonexistent index value to the list");
		}
		catch (IndexOutOfBoundsException e) {	
		}
		
		// Test 3:  Test adding a null element to the end of an empty list
		try {
			emptyList.add(0, null);
			fail("Check adding null value to the list");
		}
		catch (NullPointerException e) {	
		}
		
		// Test 4:  Test adding an element at the beginning of a list
		// Add elements to beginning of the list
		shortList.add(0, testSetString);
		longerList.add(0, testSetInt);
		list1.add(0, testSetInt);
		// Check elements at the beginning of the list
		assertEquals("Check 0 element in shortList(string) list", (String)testSetString, shortList.get(0));
		assertEquals("Check 0 element in longerList(int) list", (Integer)testSetInt, longerList.get(0));
		assertEquals("Check 0 element in list1(int) list", (Integer)testSetInt, list1.get(0));
		// Verify node after is old first element if applicable
		assertEquals("Check following node is old first node in shortList(string) list", "A", shortList.get(1));
		assertEquals("Check following node is old first node in longerList(int) list", (Integer)0, longerList.get(1));
		assertEquals("Check following node is old first node in list1(int) list", (Integer)65, list1.get(1));
		
		// Test 5:  Test adding an element right before the end of a list
		shortList.add(2, testSetString);
		longerList.add(10, testSetInt);
		list1.add(3, testSetInt);
		// Check elements at right before the end of the list
		try {
			emptyList.get(0);
			fail("There is a value at index 1 in emptyList(int) that shouldn't be there.");
		}
		catch (IndexOutOfBoundsException e) {	
		}
		assertEquals("Check element index 2 in shortList(string) list", (String)testSetString, shortList.get(2));
		assertEquals("Check element index 10 in longerList(int) list", (Integer)testSetInt, longerList.get(10));
		assertEquals("Check element index 3 in list1(int) list", (Integer)testSetInt, list1.get(3));
		// Verify node after is old element
		try {
			emptyList.get(1);
			fail("There is a value at index 1 in emptyList(int) that shouldn't be there.");
		}
		catch (IndexOutOfBoundsException e) {	
		}
		assertEquals("Check previous node is old last node in shortList(string) list", "B", shortList.get(3));
		assertEquals("Check previous node is old last node in longerList(int) list", (Integer)9, longerList.get(11));
		assertEquals("Check previous node is old last node in list1(int) list", (Integer)42, list1.get(4));
	
	}
	
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // TODO: implement this test
		// Initialize test variables
		int testSetInt = 999;
		String testSetString = "Pusheen";
		
		// Test 1:  Test setting an element to an invalid index to the end of an empty list
		try {
			emptyList.set(-1, testSetInt);
			fail("Check adding invalid index value to the list");
		}
		catch (IndexOutOfBoundsException e) {	
		}
		
		// Test 2:  Test setting an element to an index that doesn't exist yet to the end of an empty list
		try {
			emptyList.set(0, testSetInt);
			fail("Check adding nonexistent index value to the list");
		}
		catch (IndexOutOfBoundsException e) {	
		}
		
		// Test 3:  Test setting a null element to the end of an empty list
		try {
			emptyList.set(0, null);
			fail("Check adding null value to the list");
		}
		catch (NullPointerException e) {	
		}
		
		// Test 4:  Test setting an element at the beginning of a list
		shortList.set(0, testSetString);
		longerList.set(0, testSetInt);
		list1.set(0,  testSetInt);
		// Check elements at the beginning of the list
		assertEquals("Check 0 element in shortList(string) list", (String)testSetString, shortList.get(0));
		assertEquals("Check 0 element in longerList(int) list", (Integer)testSetInt, longerList.get(0));
		assertEquals("Check 0 element in list1(int) list", (Integer)testSetInt, list1.get(0));
		// Verify node after is unchanged
		assertEquals("Check following node is old first node in shortList(string) list", "B", shortList.get(1));
		assertEquals("Check following node is old first node in longerList(int) list", (Integer)1, longerList.get(1));
		assertEquals("Check following node is old first node in list1(int) list", (Integer)21, list1.get(1));
		
		// Test 5:  Test setting an element in the list
		shortList.set(1, testSetString);
		longerList.set(5, testSetInt);
		list1.set(1,  testSetInt);
		shortList.set(0, "A");
		// Check elements at the set point in the list
		assertEquals("Check 1 element in shortList(string) list", (String)testSetString, shortList.get(1));
		assertEquals("Check 5 element in longerList(int) list", (Integer)testSetInt, longerList.get(5));
		assertEquals("Check 2 element in list1(int) list", (Integer)testSetInt, list1.get(1));
		// Verify node after is unchanged
		assertEquals("Check previous node is old first node in shortList(string) list", "A", shortList.get(0));
		assertEquals("Check following node is old first node in longerList(int) list", (Integer)6, longerList.get(6));
		assertEquals("Check following node is old first node in list1(int) list", (Integer)42, list1.get(2));
		
		// Test 6:  Test setting an element at the end of the list
		shortList.set(1, testSetString);
		longerList.set(9, testSetInt);
		list1.set(2,  testSetInt);
		shortList.set(0, "A");
		list1.set(1, 21);
		// Check elements at the set point in the list
		assertEquals("Check 1 element in shortList(string) list", (String)testSetString, shortList.get(1));
		assertEquals("Check 9 element in longerList(int) list", (Integer)testSetInt, longerList.get(9));
		assertEquals("Check 2 element in list1(int) list", (Integer)testSetInt, list1.get(2));
		// Verify node after is unchanged
		assertEquals("Check previous node is old first node in shortList(string) list", "A", shortList.get(0));
		assertEquals("Check previous node is old first node in longerList(int) list", (Integer)8, longerList.get(8));
		assertEquals("Check previous node is old first node in list1(int) list", (Integer)21, list1.get(1)); 
	}

}
