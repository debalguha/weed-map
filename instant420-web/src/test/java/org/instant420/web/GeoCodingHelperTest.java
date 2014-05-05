package org.instant420.web;

import org.instant420.processor.GeoCodingHelper;
import org.instant420.processor.MapPoint;
import org.junit.Before;
import org.junit.Test;

public class GeoCodingHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetBoundingBoxMapPointDouble() {
		MapPoint point = MapPoint.newmapPoint(34.091007d, -118.337699d);
		System.out.println(GeoCodingHelper.GetBoundingBox(point, 10));
	}

	@Test
	public void testCalculateDistanceBetweenTwoPoints() {
		MapPoint point1 = MapPoint.newmapPoint(34.036889d, -118.255182d);
		MapPoint point2 = MapPoint.newmapPoint(34.032650d, -118.262068d);
		System.out.println(GeoCodingHelper.calculateDistanceBetweenTwoPoints(point1, point2, 'K'));
	}

	@Test
	public void testGetBoundingBoxMapPointDoubleChar() {
		MapPoint point = MapPoint.newmapPoint(34.091007d, -118.337699d);
		System.out.println(GeoCodingHelper.GetBoundingBox(point, 10, 'K'));
	}

}
