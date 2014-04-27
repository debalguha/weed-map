package org.instant420.web;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.instant420.processor.ScrapingProcessor;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScrapingProcessorTest {
	private static final Log logger = LogFactory.getLog(ScrapingProcessorTest.class);
	@SuppressWarnings("unchecked")
	@Test
	public void shouldBeAbleToScrapeAndStoreDispensaryAndMedicines() throws Exception{
		ClassPathXmlApplicationContext rootCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ClassPathXmlApplicationContext childCtx = new ClassPathXmlApplicationContext(new String[]{"child-application-context.xml"}, rootCtx);
		ScrapingProcessor processor = (ScrapingProcessor)childCtx.getBean(ScrapingProcessor.class);
		Set<String> readLines = new HashSet<String>();
		readLines.addAll(FileUtils.readLines(new File("C:/logs/URLList.txt")));
		logger.error("Total "+readLines.size()+" urls to process");
		processor.scrapeDispensaryDetailsForDispensaries(readLines);
	}
}
