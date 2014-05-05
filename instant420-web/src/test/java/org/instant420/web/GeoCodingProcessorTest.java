package org.instant420.web;

import org.instant420.processor.GeoCodingProcessor;
import org.instant420.processor.Instant420ApplicationEvent;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

public class GeoCodingProcessorTest extends BaseTestCase{
	@Test
	public void shouldBeAbleToPrintGeoCodeLatAndLang(){
		Instant420ApplicationEvent event = new Instant420ApplicationEvent(this, Instant420ApplicationEvent.Event.START_GEO_CODING);
		childCtx.publishEvent(event);
	}
	@Test
	@Ignore
	public void shouldBeAbleToGeoCodeAddress() throws Exception{
		GeoCodingProcessor.geoCodeAddress("1621 Almaden Road, San Jose");
	}
	
	@AfterClass
	public static void postProcess(){
		return;
	}
}
