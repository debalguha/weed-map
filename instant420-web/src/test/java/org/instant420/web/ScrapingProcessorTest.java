package org.instant420.web;

import org.instant420.processor.ScrapingProcessor;
import org.junit.AfterClass;
import org.junit.Test;

public class ScrapingProcessorTest extends BaseTestCase{
	//private static final Log logger = LogFactory.getLog(ScrapingProcessorTest.class);
	@AfterClass
	public static void postProcess(){
		return;
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
