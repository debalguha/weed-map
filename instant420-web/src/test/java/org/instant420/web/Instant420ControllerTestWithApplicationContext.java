package org.instant420.web;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Instant420ControllerTestWithApplicationContext {
	
	@Test
	public void shouldBeAbleToGetTheReferenceOfController() throws Exception{
		ClassPathXmlApplicationContext rootCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ClassPathXmlApplicationContext childCtx = new ClassPathXmlApplicationContext(new String[]{"child-application-context.xml"}, rootCtx);
		Instant420SearchController controller =  (Instant420SearchController)childCtx.getBean(Instant420SearchController.class);
		Assert.assertNotNull(controller);
		childCtx.close();
		
	}
}
