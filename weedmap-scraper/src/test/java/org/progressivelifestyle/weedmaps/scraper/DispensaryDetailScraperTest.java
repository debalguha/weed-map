package org.progressivelifestyle.weedmaps.scraper;

import static junit.framework.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;


import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.progressivelifestyle.weedmaps.objects.DispensaryObject;

public class DispensaryDetailScraperTest {
	@Test
	public void shouldBeAbleToScrapeDispensaryDetailsWithMenuItem() throws Exception{
		//BufferedReader reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("dispensary-info-scraper.xml")));
		String scraperConfigContent = DispensaryDetailScraperTest.readFile("dispensary-info-scraper.xml");
		DispensaryDetailScraper scraper = new DispensaryDetailScraper(null, scraperConfigContent);
		DispensaryObject dispensary = scraper.scrapeDispensaryDetailsWithMenuItem("https://weedmaps.com/dispensaries/washington/washington-state/green-piece?c=dispensaries");
		assertEquals(192, dispensary.getMenuItems().size());
		assertEquals("greenpiece@comcast.net", dispensary.getEmail());
		assertEquals("", dispensary.getInstagramURL());
		assertEquals("10:00am", dispensary.getFridayOpen());
		new ObjectMapper().writeValue(System.out, dispensary);
	}
	
	private static String readFile(String fileName) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(DispensaryDetailScraperTest.class.getClassLoader().getResourceAsStream(fileName)));
		String line = "";
		StringBuilder builder = new StringBuilder();
		while ((line = reader.readLine()) != null)
			builder.append(line);
		return builder.toString();
	}
}
