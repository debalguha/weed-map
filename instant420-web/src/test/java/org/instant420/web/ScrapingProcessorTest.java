package org.instant420.web;

import org.instant420.processor.ScrapingProcessor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScrapingProcessorTest {
	//private static final Log logger = LogFactory.getLog(ScrapingProcessorTest.class);
	private static ApplicationContext childCtx;
	@BeforeClass
	public static void preProcess(){
		ClassPathXmlApplicationContext rootCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
		childCtx = new ClassPathXmlApplicationContext(new String[]{"child-application-context.xml"}, rootCtx);
	}
	@AfterClass
	public static void postProcess(){
		((ClassPathXmlApplicationContext)childCtx).close();
	}
	@Test
	public void shouldBeAbleToScrapeAndStoreDispensaryAndMedicines() throws Exception{
		ScrapingProcessor processor = (ScrapingProcessor)childCtx.getBean(ScrapingProcessor.class);
		/*Set<String> readLines = new HashSet<String>();
		readLines.addAll(FileUtils.readLines(new File("C:/logs/URLList.txt")));
		logger.error("Total "+readLines.size()+" urls to process");
		processor.scrapeDispensaryDetailsForDispensaries(readLines);*/
		processor.startScraping();
	}
}
