package roadgraph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import geography.GeographicPoint;

public class MapNode {

	// Member Variables
	GeographicPoint location;
	List<MapEdge> edges;
	double currDistance;
	double priority;
	double aStarPriority;
	double travelTime;
	
	// Constructor with full variables
	public MapNode(GeographicPoint location, List<MapEdge> edges, double priority, double aStarPriority, double currDistance, double travelTime) {
		this.location = location;
		this.edges = edges;
		this.priority = priority;
		this.aStarPriority = aStarPriority;
		this.currDistance = currDistance;
		this.travelTime = travelTime;
	}
	
	// Constructor without travel time
	public MapNode(GeographicPoint location, List<MapEdge> edges, double priority, double aStarPriority, double currDistance) {
		this.location = location;
		this.edges = edges;
		this.priority = priority;
		this.aStarPriority = aStarPriority;
		this.currDistance = currDistance;
	}
	
	// Constructor without current distance and travel time
	public MapNode(GeographicPoint location, List<MapEdge> edges, double priority, double aStarPriority) {
		this.location = location;
		this.edges = edges;
		this.priority = priority;
		this.aStarPriority = aStarPriority;
		this.currDistance = 0;
	}
	
	// Constructor without AStar
	public MapNode(GeographicPoint location, List<MapEdge> edges, double priority) {
		this.location = location;
		this.edges = edges;
		this.priority = priority;
		this.aStarPriority = Double.POSITIVE_INFINITY;
		this.currDistance = 0;
	}
	
	// Constructor without priority
	public MapNode(GeographicPoint location, List<MapEdge> edges) {
		this.location = location;
		this.edges = edges;
		this.priority = Double.POSITIVE_INFINITY;
		this.aStarPriority = Double.POSITIVE_INFINITY;
		this.currDistance = 0;
	}
	
	// Constructor with only location
	public MapNode(GeographicPoint location) {
		this.location = location;
		this.edges = new LinkedList<MapEdge>();
		this.priority = Double.POSITIVE_INFINITY;
		this.aStarPriority = Double.POSITIVE_INFINITY;
		this.currDistance = 0;
	}
	
	// Getters and Setters
	public GeographicPoint getLocation() {
		return this.location;
	}
	
	public List<MapEdge> getEdges() {
		return this.edges;
	}
	
	public double getPriority() {
		return this.priority;
	}
	
	public double getAStarPriority() {
		return this.aStarPriority;
	}
	
	public double getCurrDistance() {
		return this.currDistance;
	}
	
	public double getTravelTime() {
		return this.travelTime;
	}
	
	public void setLocation(GeographicPoint location) {
		this.location = location;
	}
	
	public void setEdgesList(List<MapEdge> edges) {
		this.edges = edges;
	}
	
	public void setPriority(double priority) {
		this.priority = priority;
	}
	
	public void setAStarPriority(double aStarPriority) {
		this.aStarPriority = aStarPriority;
	}
	
	public void setCurrDistance(double currDistance) {
		this.currDistance = currDistance;
	}
	
	public void setTravelTime(double travelTime) {
		this.travelTime = travelTime;
	}
	
	// Useful Methods
    public String toString()
    {
    	return "Location: " + getLocation() + ", Edges: " + getEdges();
    }
       
}
