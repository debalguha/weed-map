package org.progressivelifestyle.weedmaps.scraper;

import static junit.framework.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.progressivelifestyle.weedmap.persistence.domain.Dispensary;
import org.progressivelifestyle.weedmap.persistence.domain.Menu;
import org.progressivelifestyle.weedmaps.objects.DispensaryObject;

public class DispensaryDetailScraperTest {
	@Test
	public void shouldBeAbleToScrapeDispensaryDetailsWithMenuItem() throws Exception{
		//BufferedReader reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("dispensary-info-scraper.xml")));
		String scraperConfigContent = DispensaryDetailScraperTest.readFile("dispensary-info-scraper.xml");
		DispensaryDetailScraper scraper = new DispensaryDetailScraper(null, scraperConfigContent);
		Dispensary dispensary = scraper.scrapeDispensaryDetailsWithMenuItem("https://weedmaps.com/dispensaries/california/downtown-la/harmony-herbal-caregivers-lab-tested-meds?c=search");
		for(Menu menuItem : ((DispensaryObject)dispensary).getMenuItems()){
			if(menuItem.getName().equals("Vape Pen WHITE DIESEL"))
				System.out.println("##.."+menuItem.getMenuItemCategoryId());
		}
		assertEquals(192, ((DispensaryObject)dispensary).getMenuItems().size());
		assertEquals("greenpiece@comcast.net", dispensary.getEmail());
		assertEquals("", dispensary.getInstagramURL());
		assertEquals("10:00am", dispensary.getFridayOpen());
		new ObjectMapper().writeValue(System.out, dispensary);
	}
	
	public static String readFile(String fileName) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(DispensaryDetailScraperTest.class.getClassLoader().getResourceAsStream(fileName)));
		String line = "";
		StringBuilder builder = new StringBuilder();
		while ((line = reader.readLine()) != null)
			builder.append(line);
		return builder.toString();
	}
}
