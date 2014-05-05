package org.instant420.web;

import org.instant420.processor.GeoCodingProcessor;
import org.instant420.processor.Instant420ApplicationEvent;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GeoCodingProcessorTest {
	private static ApplicationContext childCtx;
	@BeforeClass
	public static void preProcess(){
		ClassPathXmlApplicationContext rootCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
		childCtx = new ClassPathXmlApplicationContext(new String[]{"child-application-context.xml"}, rootCtx);
	}
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
}
