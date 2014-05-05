package org.instant420.processor;

public class BoundingBox {
	private MapPoint minPoint;
	private MapPoint maxPoint;
	
	
	public BoundingBox(MapPoint minPoint, MapPoint maxPoint) {
		super();
		this.minPoint = minPoint;
		this.maxPoint = maxPoint;
	}
	public MapPoint getMinPoint() {
		return minPoint;
	}
	public void setMinPoint(MapPoint minPoint) {
		this.minPoint = minPoint;
	}
	public MapPoint getMaxPoint() {
		return maxPoint;
	}
	public void setMaxPoint(MapPoint maxPoint) {
		this.maxPoint = maxPoint;
	}
	@Override
	public String toString() {
		return "BoundingBox [minPoint=" + minPoint + ", maxPoint=" + maxPoint + "]";
	}
}
