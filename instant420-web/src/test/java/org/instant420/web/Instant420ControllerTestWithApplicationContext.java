package org.instant420.web;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Instant420ControllerTestWithApplicationContext {
	
	@Test
	public void shouldBeAbleToGetTheReferenceOfController() throws Exception{
		ApplicationContext rootCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ApplicationContext childCtx = new ClassPathXmlApplicationContext(new String[]{"child-application-context.xml"}, rootCtx);
		Instant420SearchController controller =  (Instant420SearchController)childCtx.getBean(Instant420SearchController.class);
		Assert.assertNotNull(controller);
	}
}
