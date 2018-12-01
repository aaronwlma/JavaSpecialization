package roadgraph;

import geography.GeographicPoint;

public class MapEdge {

	// Member Variables
	GeographicPoint start;
	GeographicPoint end;
	String streetName;
	String streetType;
	double distance;
	double speedLimit;
	
	// Constructor with full inputs
	public MapEdge(GeographicPoint start, GeographicPoint end, String streetName, String streetType, double distance, double speedLimit) {
		this.start = start;
		this.end = end;
		this.streetName = streetName;
		this.streetType = streetType;
		this.distance = distance;
		this.speedLimit = speedLimit;
	}
	
	public MapEdge(GeographicPoint start, GeographicPoint end, String streetName, String streetType, double distance) {
		this.start = start;
		this.end = end;
		this.streetName = streetName;
		this.streetType = streetType;
		this.distance = distance;
	}
	
	// Getters and setters
	public GeographicPoint getStartPoint() {
		return this.start;
	}
	
	public GeographicPoint getEndPoint() {
		return this.end;
	}
	
	public String getStreetName() {
		return this.streetName;
	}
	
	public String getStreetType() {
		return this.streetType;
	}
	
	public double getDistance() {
		return this.distance;
	}
	
	public double getSpeedLimit() {
		return this.speedLimit;
	}
	
	public void setStartPoint(GeographicPoint start) {
		this.start = start;
	}
	
	public void setEndPoint(GeographicPoint end) {
		this.end = end;
	}
	
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	
	public void setStreetType(String streetType) {
		this.streetType = streetType;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public void setSpeedLimit(double speedLimit) {
		this.speedLimit = speedLimit;
	}
	
	// Useful Methods
	
}
