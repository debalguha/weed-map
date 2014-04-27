package org.instant420.web;

import org.instant420.processor.Instant420ApplicationEvent;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GeoCodingProcessorTest {
	@Test
	public void shouldBeAbleToPrintGeoCodeLatAndLang(){
		ClassPathXmlApplicationContext rootCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ClassPathXmlApplicationContext childCtx = new ClassPathXmlApplicationContext(new String[]{"child-application-context.xml"}, rootCtx);
		Instant420ApplicationEvent event = new Instant420ApplicationEvent(this, Instant420ApplicationEvent.Event.START_GEO_CODING);
		childCtx.publishEvent(event);
	}
}
