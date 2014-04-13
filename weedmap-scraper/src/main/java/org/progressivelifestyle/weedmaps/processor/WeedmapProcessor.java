package org.progressivelifestyle.weedmaps.processor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.progressivelifestyle.weedmaps.objects.DispensaryObject;
import org.progressivelifestyle.weedmaps.scraper.DispensaryDetailScraper;
import org.progressivelifestyle.weedmaps.scraper.DispensaryLocationScraper;
import org.progressivelifestyle.weedmaps.scraper.DispensaryScraper;
import org.progressivelifestyle.weedmaps.writer.SpreadsheetWriter;

public class WeedmapProcessor {
	private CompletionService<Set<String>> locationService;
	private CompletionService<DispensaryObject> dispensaryDetailService;
	private Executor locationExecutor = new ThreadPoolExecutor(10, 20, Long.MAX_VALUE, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());
	private Executor dispensaryDetailExecutor = new ThreadPoolExecutor(10, 20, Long.MAX_VALUE, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());
	private static final Log logger = LogFactory.getLog(WeedmapProcessor.class);
	public WeedmapProcessor(){
		locationService = new ExecutorCompletionService<Set<String>>(locationExecutor);
		dispensaryDetailService = new ExecutorCompletionService<DispensaryObject>(dispensaryDetailExecutor);
	}
	
	public static void main(String []args) throws Exception{
		new WeedmapProcessor().initScraping();
	}
	public void initScraping() throws Exception{
		logger.info("Begining scraping");
		String dispensaryBaseScraperConfig = WeedmapProcessor.readFile("dispensary-base-scraper.xml");
		String dispensaryLocationScraperConfig = WeedmapProcessor.readFile("dispensary-location-scraper.xml");
		String dispensaryDetaisScraperConfig = WeedmapProcessor.readFile("dispensary-info-scraper.xml");
		logger.info("Finished caching all config files.");
		DispensaryScraper levelOneScraper = new DispensaryScraper(null, dispensaryBaseScraperConfig);
		logger.info("Begining level one scraping");
		Set<String> urlOfDispensaryLocations = levelOneScraper.findURLOfDispensaries("https://weedmaps.com/");
		logger.info("URLs obtained from level one scraping. Size "+urlOfDispensaryLocations.size());
		Set<String> urlOfDispensaries = scrapeForDispensaryURLs(urlOfDispensaryLocations,dispensaryLocationScraperConfig );
		logger.info("Dispensaries obtained "+urlOfDispensaries.size());
		Set<DispensaryObject> dispensaries = scrapeDispensaryDetailsForDispensaries(urlOfDispensaries, dispensaryDetaisScraperConfig);
		logger.info("Dispensary objects created "+dispensaries.size());
		SpreadsheetWriter.writeSpreadSheet("C:/logs/WeedsMapDB.xls", dispensaries);
	}
	
	private Set<DispensaryObject> scrapeDispensaryDetailsForDispensaries(Set<String> urlOfDispensaries, String dispensaryDetaisScraperConfig) throws InterruptedException, ExecutionException {
		Set<DispensaryObject> dispensaries = new HashSet<DispensaryObject>();
		List<Future<DispensaryObject>> dispensaryFutList = new ArrayList<Future<DispensaryObject>>(); 
		for(String dispensaryURL : urlOfDispensaries) 
			dispensaryFutList.add(dispensaryDetailService.submit(new DispensaryDetailScraper(null, dispensaryDetaisScraperConfig, dispensaryURL)));
		DispensaryObject obj = null;
		for(Future<DispensaryObject> aFuture : dispensaryFutList){
			try {
				obj = aFuture.get();
				if(obj!=null){
					dispensaries.add(obj);
					System.out.println(obj.getName());
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dispensaries;
	}

	private Set<String> scrapeForDispensaryURLs(Set<String> urlOfDispensaryLocations, String dispensaryLocationScraperConfig) throws InterruptedException, ExecutionException {
		List<Future<Set<String>>> futureList = new ArrayList<Future<Set<String>>>();
		for(String locationURL : urlOfDispensaryLocations)
			futureList.add(locationService.submit(new DispensaryLocationScraper(null, dispensaryLocationScraperConfig, locationURL, "https://weedmaps.com")));
		Set<String> restaurantURLs = new HashSet<String>();
		for(Future<Set<String>> aFuture : futureList)
				restaurantURLs.addAll(aFuture.get());
		return restaurantURLs;
	}

	private static String readFile(String fileName) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(DispensaryDetailScraper.class.getClassLoader().getResourceAsStream(fileName)));
		String line = "";
		StringBuilder builder = new StringBuilder();
		while ((line = reader.readLine()) != null)
			builder.append(line);
		return builder.toString();
	}
}
