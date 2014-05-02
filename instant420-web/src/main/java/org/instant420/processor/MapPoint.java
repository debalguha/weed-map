package org.instant420.processor;

public class MapPoint {
	private double longitude;
	private double latitude;
	
	public static MapPoint newmapPoint(double latitude, double longitude){
		return new MapPoint(longitude, latitude);
	}
	public MapPoint(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
}
