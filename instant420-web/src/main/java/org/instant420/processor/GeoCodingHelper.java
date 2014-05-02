package org.instant420.processor;

public class GeoCodingHelper {

	private static double WGS84_a = 6378137.0;
	private static double WGS84_b = 6356752.3;

	public static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	public static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	

	public static BoundingBox GetBoundingBox(MapPoint point, double halfSideInKm) {
	    // Bounding box surrounding the point at given coordinates,
	    // assuming local approximation of Earth surface as a sphere
	    // of radius given by WGS84
		double lat = deg2rad(point.getLatitude());
		double lang = deg2rad(point.getLongitude());
		double halfSide = 1000 * halfSideInKm;

	    // Radius of Earth at given latitude
		double radius = WGS84EarthRadius(lat);
	    // Radius of the parallel at given latitude
		double pradius = radius * Math.cos(lat);
		
		
		double latMin = lat - halfSide / radius;
		double latMax = lat + halfSide / radius;
		double lonMin = lang - halfSide / pradius;
		double lonMax = lang + halfSide / pradius;
		return new BoundingBox(MapPoint.newmapPoint(rad2deg(lonMin), rad2deg(latMin)), MapPoint.newmapPoint(rad2deg(lonMax), rad2deg(latMax)));
	}

	private static double WGS84EarthRadius(double lat) {
		// http://en.wikipedia.org/wiki/Earth_radius
		double An = WGS84_a * WGS84_a * Math.cos(lat);
		double Bn = WGS84_b * WGS84_b * Math.sin(lat);
		double Ad = WGS84_a * Math.cos(lat);
		double Bd = WGS84_b * Math.sin(lat);
		return Math.sqrt((An * An + Bn * Bn) / (Ad * Ad + Bd * Bd));
	}
	
	public static double calculateDistanceBetweenTwoPoints(MapPoint pointFrom, MapPoint pointTo, char unit) {
		double theta = pointFrom.getLongitude() - pointTo.getLongitude();
		double dist = Math.sin(deg2rad(pointFrom.getLatitude())) * Math.sin(deg2rad(pointTo.getLatitude())) + Math.cos(deg2rad(pointFrom.getLatitude())) * Math.cos(deg2rad(pointTo.getLatitude())) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == 'K') {
			dist = dist * 1.609344;
		} else if (unit == 'N') {
			dist = dist * 0.8684;
		}
		return (dist);
	}

}
