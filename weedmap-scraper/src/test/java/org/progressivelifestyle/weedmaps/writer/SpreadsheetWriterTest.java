package org.progressivelifestyle.weedmaps.writer;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.progressivelifestyle.weedmaps.objects.Dispensary;
import org.progressivelifestyle.weedmaps.objects.DispensaryObject;
import org.progressivelifestyle.weedmaps.scraper.DispensaryDetailScraper;
import org.progressivelifestyle.weedmaps.scraper.DispensaryDetailScraperTest;

public class SpreadsheetWriterTest {
	@Test
	public void shouldBeAbleToScrapeAndWriteToSpreadsheet() throws Exception {
		String scraperConfigContent = DispensaryDetailScraperTest.readFile("dispensary-info-scraper.xml");
		DispensaryDetailScraper scraper = new DispensaryDetailScraper(null, scraperConfigContent);
		DispensaryObject dispensary = scraper.scrapeDispensaryDetailsWithMenuItem("https://weedmaps.com/dispensaries/california/downtown-la/harmony-herbal-caregivers-lab-tested-meds?c=search");
		List<Dispensary> dispensaries = new ArrayList<Dispensary>();
		dispensaries.add(dispensary);
		//SpreadsheetWorker.writeSpreadSheet("C:/logs/test-sheet.xls", dispensaries);
		SpreadsheetWorker.addDispensariesToSpreadsheetInGoogleDrive("instant420", dispensaries);
	}
	
	@Test
	@Ignore
	public void shouldBeAbleToReadSpreadsheetFromGoogleDrive() throws Exception{
		Collection<DispensaryObject> readSpreadsheetFromGoogleDrive = SpreadsheetWorker.readSpreadsheetFromGoogleDrive("instant420");
		assertNotNull(readSpreadsheetFromGoogleDrive);
		assertFalse(readSpreadsheetFromGoogleDrive.isEmpty());
	}
	
	
}
