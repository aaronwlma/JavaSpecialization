/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;


/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//DONE: Add your member variables here in WEEK 3
	
	// Member Variables
	private HashMap<GeographicPoint, MapNode> theMap;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// DONE: Implement in this constructor in WEEK 3
		theMap = new HashMap<GeographicPoint, MapNode>();

	}
	
	// Helpful Methods
	// Not a necessary method, but it helps keep track of the points along the way
	public void printIntersections() {
		Set<Entry<GeographicPoint, MapNode>> mapSet = theMap.entrySet();
		for (Entry entry : mapSet) {
			System.out.println("Key-> " + entry.getKey() + "         Values-> " + entry.getValue());
		}
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//DONE: Implement this method in WEEK 3
		// The number of vertices in the map is equal to the number of entries in the HashMap, so we just need the size
		int numVertices = theMap.size();
		return numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//DONE: Implement this method in WEEK 3
		// The vertices in the MapGraph is just the keys in the HashMap, so we just need to retrieve the key set
		Set<GeographicPoint> verticesSet = theMap.keySet();
		return verticesSet;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		// Initialize count
		int count = 0;
		
		// Iterate through each key and count the number of edges in the key
		for (GeographicPoint key : theMap.keySet()) {
			count += theMap.get(key).getEdges().size();
		}
		return count;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// DONE: Implement this method in WEEK 3
		// Initialize the new vertex first
		MapNode newNode = new MapNode(location);
		
		// Make sure the vertex doesn't exist already
		if (!theMap.containsKey(location)) {
			// Also make sure there's actually a location associated with it
			if (location == null) {
				System.out.println("Null vertex can not be added.");
				return false;
			}
			// If the checks are OK, then add the node into the HashMap
			else {
				//System.out.println("Vertex at [" + location + "] has been added.");
				theMap.put(location, newNode);
				return true;
			}
		}
		// If the vertex already exists, return false and give the user a heads up
		else {
			System.out.println("Specified vertex already exists.");
			return false;
		}
		
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//DONE: Implement this method in WEEK 3
		// Check to see if both input GeographicPoints actually exist
		if (theMap.containsKey(from) && theMap.containsKey(to)) {
			// If they do, create two new directed MapEdges
			MapEdge newEdgeFromTo = new MapEdge(from, to, roadName, roadType, length);
			// Add the statement below if we want to implement bi-directional roads
			//MapEdge newEdgeToFrom = new MapEdge(to, from, roadName, roadType, length);
			//Add the property to the MapNode objects
			theMap.get(from).edges.add(newEdgeFromTo);
			// Add the statement below if we want to implement bi-directional roads
			//theMap.get(to).edges.add(newEdgeToFrom);		
		}
		// If the two input GeographicPoints do not exist, throw exception
		else {
			throw new IllegalArgumentException("One or both of your two points do not exist.");
		}
		
	}
	
	public class DistanceComparator implements Comparator<MapNode> {
		@Override
		public int compare(MapNode a, MapNode b) {
			if (a.getPriority() > b.getPriority()) {
				return 1;
			}
			else if (a.getPriority() < b.getPriority()) {
				return -1;
			}
			return 0;
		}
	}
	
	public class aStarComparator implements Comparator<MapNode> {
		@Override
		public int compare(MapNode a, MapNode b) {
			if (a.getAStarPriority() > b.getAStarPriority()) {
				return 1;
			}
			else if (a.getAStarPriority() < b.getAStarPriority()) {
				return -1;
			}
			return 0;
		}
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// DONE: Implement this method in WEEK 3
		// Initialize: Translate GeographicPoints to MapNodes to work with
		MapNode startNode = theMap.get(start);
		MapNode goalNode = theMap.get(goal);
		
		// Check to see if the points exist
		if (startNode == null || goalNode == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return null;
		}
		
		// Initialize:  queue, visited HashSet, and parent HashMap with MapNodes
		HashSet<MapNode> visited = new HashSet<MapNode>();
		Queue<MapNode> toExplore = new LinkedList<MapNode>();
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();

		// Enqueue start onto the queue and add to visited
		toExplore.add(startNode);
		visited.add(startNode);
		boolean found = false;
		
		// While queue is not empty:
		while (!toExplore.isEmpty()) {		
			// Dequeue MapNode curr from front, and nodeSearched.accept(next);
			MapNode currNode = toExplore.remove();
			GeographicPoint next = currNode.getLocation();
			nodeSearched.accept(next);
			// if currNode == goalNode, return parent HashMap
			if (currNode == goalNode) {
				found = true;
				break;
			}

			// for each of curr's edges, n, not in visited set
			for (MapEdge n : currNode.getEdges()) {
				// add n to visited set
				MapNode nextNode = theMap.get(n.getEndPoint());
				visited.add(nextNode);
				// add curr as n's parent in parent map
				parentMap.put(nextNode, currNode);
				// enqueue n onto the queue
				toExplore.add(nextNode);
			}
		}
		
		// if we get here, then there's no path to goal
		if (!found) {
			return null;
		}
		
		// Reconstruct the path
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		MapNode current = goalNode;
		while (current != startNode) {
			path.addFirst(current.getLocation());
			current = parentMap.get(current);
		}
		path.addFirst(start);
		System.out.println(path.toString());
		return path;
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// DONE: Implement this method in WEEK 4
		// Initialize: Translate GeographicPoints to MapNodes to work with
		MapNode startNode = theMap.get(start);
		MapNode goalNode = theMap.get(goal);
		int counter = 0;
		
		// Check to see if the points exist
		if (startNode == null || goalNode == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return null;
		}
		
		// Initialize:  Priority queue, visited HashSet, and parent HashMap with MapNodes
		HashSet<MapNode> visited = new HashSet<MapNode>();
		Comparator<? super MapNode> comparator = new DistanceComparator();
		PriorityQueue<MapNode> toExplore = new PriorityQueue<MapNode>(10, comparator);
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		for (GeographicPoint p : theMap.keySet()) {
			theMap.get(p).setPriority(Double.POSITIVE_INFINITY);
		}

		// Enqueue {start, 0} onto the priority queue and add to visited
		startNode.setPriority(0);
		toExplore.add(startNode);
		boolean found = false;
		
		// While priority queue is not empty:
		while (!toExplore.isEmpty()) {		
			// Dequeue MapNode curr from front, and nodeSearched.accept(next);
			MapNode currNode = toExplore.remove();
			GeographicPoint next = currNode.getLocation();
			nodeSearched.accept(next);
			
			// add curr to visited set
			visited.add(currNode);
			counter++;
			
			// if currNode == goalNode, return parent HashMap
			if (currNode == goalNode) {
				found = true;
				break;
			}

			// for each of curr's edges, n, not in visited set
			for (MapEdge n : currNode.getEdges()) {
				MapNode nextNode = theMap.get(n.getEndPoint());
				MapNode selectedNode = theMap.get(n.getStartPoint());
								
				// if path through currNode to nextNode is shorter
				if (selectedNode.getPriority() + n.getDistance() < nextNode.getPriority()) {
					// update nextNode's priority
					nextNode.setPriority(selectedNode.getPriority() + n.getDistance());
					// update curr as nextNode's parent in parent map
					parentMap.put(nextNode, currNode);
					// enqueue {nextNode, distance} onto the priority queue
					toExplore.add(nextNode);
				}
			}
		}
		
		// if we get here, then there's no path to goal
		if (!found) {
			return null;
		}
		
		// Reconstruct the path
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		MapNode current = goalNode;
		while (current != startNode) {
			path.addFirst(current.getLocation());
			current = parentMap.get(current);
		}
		path.addFirst(start);
		System.out.println("djikstra: " + counter);
		return path;
	}


	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		// Initialize: Translate GeographicPoints to MapNodes to work with
		MapNode startNode = theMap.get(start);
		MapNode goalNode = theMap.get(goal);
		int counter = 0;
//				System.out.println(startNode.toString());
//				System.out.println(goalNode.toString());
		
		// Check to see if the points exist
		if (startNode == null || goalNode == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return null;
		}
		
		// Initialize:  Priority queue, visited HashSet, and parent HashMap with MapNodes
		HashSet<MapNode> visited = new HashSet<MapNode>();
		Comparator<? super MapNode> comparator = new aStarComparator();
		PriorityQueue<MapNode> toExplore = new PriorityQueue<MapNode>(10, comparator);
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		for (GeographicPoint p : theMap.keySet()) {
			theMap.get(p).setCurrDistance(Double.POSITIVE_INFINITY);
		}

		// Enqueue {start, 0} onto the priority queue and add to visited
		startNode.setCurrDistance(0);
		toExplore.add(startNode);
//		System.out.println("BEGIN: toExplore: " + toExplore.toString());
//		System.out.println("visited: " + visited.toString());
		boolean found = false;
		
		// While priority queue is not empty:
		while (!toExplore.isEmpty()) {		
			// Dequeue MapNode curr from front, and nodeSearched.accept(next);
			MapNode currNode = toExplore.remove();
			double currNodeToGoal = currNode.getLocation().distance(goal);
//			System.out.println("While loop: " + currNode.toString());
			GeographicPoint next = currNode.getLocation();
			nodeSearched.accept(next);
			
			// if (curr is not visited)
			if (!visited.contains(currNode)) {
				// add curr to visited set
				visited.add(currNode);
				counter++;
//				System.out.println("visited: " + visited.toString());		
				
				// if currNode == goalNode, return parent HashMap
				if (currNode == goalNode) {
					found = true;
					break;
				}

				// for each of curr's edges, n, not in visited set
				for (MapEdge n : currNode.getEdges()) {
					MapNode nextNode = theMap.get(n.getEndPoint());
					double nextNodeToGoal = nextNode.getLocation().distance(goal);
//					System.out.println("selectedNodeToGoal: " + selectedNodeToGoal);
//					System.out.println("nextNodeToGoal: " + nextNodeToGoal);
//					System.out.println("selectedNode distance: " + (currNode.getCurrDistance() + currNodeToGoal));
//					System.out.println("nextNode distance: " + (currNode.getCurrDistance() + n.getDistance() + nextNodeToGoal));
					if (!visited.contains(nextNode)) {
						// if path through currNode to nextNode is shorter
						if ((currNode.getCurrDistance() + n.getDistance()) < nextNode.getCurrDistance()) {
							// update nextNode's distance
							nextNode.setAStarPriority(currNode.getCurrDistance() + n.getDistance() + nextNodeToGoal);
							nextNode.setCurrDistance(currNode.getCurrDistance() + n.getDistance());
//							System.out.println("In if nextNode Priority: " + nextNode.getAStarPriority());
							// update curr as nextNode's parent in parent map
							parentMap.put(nextNode, currNode);
							// enqueue {nextNode, distance} onto the priority queue
							toExplore.add(nextNode);
//							System.out.println("toExplore: " + toExplore.toString());
						}
					}
				}
			}
			
		}
		
		// if we get here, then there's no path to goal
		if (!found) {
			//System.out.println("No path exists.");
			return null;
		}
		
		// Reconstruct the path
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		MapNode current = goalNode;
		while (current != startNode) {
			path.addFirst(current.getLocation());
			current = parentMap.get(current);
		}
		path.addFirst(start);
		System.out.println("a*: " + counter);
		return path;
	}


	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();

		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		// Personal Monitor Values and Function Checks
//		System.out.println();
//		System.out.println("Print Loaded Roads:");
//		firstMap.printIntersections();
//		System.out.println();
//		System.out.println("Number of Vertices: ");
//		System.out.println(firstMap.getNumVertices());
//		System.out.println();
//		System.out.println("Check for addVertex functionality...");
//		GeographicPoint testPoint = new GeographicPoint(2.2, 3.3);
//		GeographicPoint duplicatePoint = new GeographicPoint(1.0, 1.0);
//		firstMap.addVertex(testPoint);
//		firstMap.addVertex(duplicatePoint);
//		firstMap.addVertex(null);
//		System.out.println();
//		System.out.println("Updated Loaded Roads:");
//		firstMap.printIntersections();
//		System.out.println();
//		System.out.println("Updated Number of Vertices: ");
//		System.out.println(firstMap.getNumVertices());
//		System.out.println();
//		System.out.println("Check for getVertices functionality: ");
//		System.out.println(firstMap.getVertices());
//		System.out.println();
//		System.out.println("Check for addEdge functionality: ");
//		firstMap.addEdge(testPoint, duplicatePoint, "Pusheen Road", "A Cute One", 99.00);
//		firstMap.printIntersections();
//		System.out.println();
//		System.out.println("Check for bfs functionality: ");
//		GeographicPoint testPointA = new GeographicPoint(4.0, -1.0);
//		GeographicPoint testPointB = new GeographicPoint(6.5, 0.0);
//		System.out.println(firstMap.bfs(testPointA, testPointB).toString());
		System.out.println();
		System.out.println("Check for djikstra functionality (should be 9): ");
		GeographicPoint testPointC = new GeographicPoint(1.0, 1.0);
		GeographicPoint testPointD = new GeographicPoint(8.0, -1.0);
		System.out.println(firstMap.dijkstra(testPointC, testPointD).toString());
		System.out.println();
		System.out.println("Check for a* functionality (should be 5): ");
		GeographicPoint testPointE = new GeographicPoint(1.0, 1.0);
		GeographicPoint testPointF = new GeographicPoint(8.0, -1.0);
		System.out.println(firstMap.aStarSearch(testPointE, testPointF).toString());
		System.out.println();
		System.out.println();
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		
		/* Use this code in Week 3 End of Week Quiz */
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		
	}
	
}
