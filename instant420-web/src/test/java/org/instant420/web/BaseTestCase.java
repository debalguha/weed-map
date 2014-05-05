package org.instant420.web;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseTestCase {
	protected static ApplicationContext childCtx;
	@BeforeClass
	public static void preProcess(){
		ClassPathXmlApplicationContext rootCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
		childCtx = new ClassPathXmlApplicationContext(new String[]{"child-application-context.xml"}, rootCtx);
	}
	
	@AfterClass
	public static void postProcess(){
		((ClassPathXmlApplicationContext)childCtx).close();
	}	
}
