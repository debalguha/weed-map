package org.progressivelifestyle.weedmaps.processor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import org.progressivelifestyle.weedmaps.objects.Dispensary;
import org.progressivelifestyle.weedmaps.objects.DispensaryObject;
import org.progressivelifestyle.weedmaps.scraper.DispensaryDetailScraper;
import org.progressivelifestyle.weedmaps.scraper.DispensaryLocationScraper;
import org.progressivelifestyle.weedmaps.scraper.DispensaryScraper;
import org.progressivelifestyle.weedmaps.writer.SpreadsheetWorker;

public class WeedmapProcessor {
	private CompletionService<Set<String>> locationService;
	private CompletionService<DispensaryObject> dispensaryDetailService;
	private Executor locationExecutor = new ThreadPoolExecutor(10, 20, Long.MAX_VALUE, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());
	private Executor dispensaryDetailExecutor = new ThreadPoolExecutor(10, 20, Long.MAX_VALUE, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());
	private Executor spreadSheetWriterExecutor = new ThreadPoolExecutor(10, 20, Long.MAX_VALUE, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());
	private static final Log logger = LogFactory.getLog(WeedmapProcessor.class);
	private WeedmapScraperCache cache;
	public WeedmapProcessor(){
		locationService = new ExecutorCompletionService<Set<String>>(locationExecutor);
		dispensaryDetailService = new ExecutorCompletionService<DispensaryObject>(dispensaryDetailExecutor);
	}
	
	public static void main(String []args) throws Exception{
		new WeedmapProcessor().initScraping();
	}
	public void initScraping() throws Exception{
		logger.info("Begining to cache all present data in google doc");
		cacheAllObjectsFromSpreasheet();
		logger.info("Begining scraping");
		((ThreadPoolExecutor)spreadSheetWriterExecutor).prestartAllCoreThreads();
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
		SerializationUtil.serializeToDisk(urlOfDispensaries);
		Set<Dispensary> dispensaries = scrapeDispensaryDetailsForDispensaries(urlOfDispensaries, dispensaryDetaisScraperConfig);
		logger.info("Dispensary objects created "+dispensaries.size());
		//SpreadsheetWriter.writeSpreadSheet("C:/logs/WeedsMapDB.xls", dispensaries);
		SpreadsheetWorker.removeDispensariesAndMenuItemsFromSpreadsheet("instant420", WeedmapScraperCache.getDispensaryCache());
	}

	private void cacheAllObjectsFromSpreasheet() throws Exception {
		cache = WeedmapScraperCache.getInstance(SpreadsheetWorker.readSpreadsheetFromGoogleDrive("instant420"));
	}

	private Set<Dispensary> scrapeDispensaryDetailsForDispensaries(Set<String> urlOfDispensaries, String dispensaryDetaisScraperConfig) throws Exception {
		final Set<Dispensary> dispensaries = new HashSet<Dispensary>();
		Map<Future<DispensaryObject>, String> dispensaryFutMap = new HashMap<Future<DispensaryObject>, String>();
		for(String dispensaryURL : urlOfDispensaries){
				dispensaryFutMap.put(dispensaryDetailService.submit(new DispensaryDetailScraper(null, dispensaryDetaisScraperConfig, dispensaryURL)), dispensaryURL);
		}
		DispensaryObject obj = null;
		for(Future<DispensaryObject> aFuture : dispensaryFutMap.keySet()){
			try {
				obj = aFuture.get();
				if(obj!=null){
					dispensaries.add(obj);
					if(dispensaries.size()>10){
						Set<Dispensary> dispensariesToWorkWith = new HashSet<Dispensary>();
						dispensariesToWorkWith.addAll(dispensaries);
						dispensaries.clear();
						final Set<Dispensary> dispensariesToBeAdded = cache.findNewlyAddedDispensaries(dispensariesToWorkWith);
						//final Set<Dispensary> dispensariesToBeRemoved = cache.findDispensariesNoLongerAvailable(dispensariesToWorkWith);
						final Set<Dispensary> dispensariesToBeUpdated = cache.findUpdatedDispensaries(dispensariesToWorkWith);
						spreadSheetWriterExecutor.execute(new Runnable(){
							public void run() {
								try {
									SpreadsheetWorker.addDispensariesToSpreadsheetInGoogleDrive("instant420", dispensariesToBeAdded);
									//SpreadsheetWorker.removeDispensariesAndMenuItemsFromSpreadsheet("instant420", dispensariesToBeRemoved);
									SpreadsheetWorker.updateDispensariesAndMenuItemsInSpreadsheet("instant420", dispensariesToBeUpdated);
								} catch (Exception e) {
									logger.error("Failed to upload dispensaries!!"+dispensaries);
									e.printStackTrace();
								}
							}
						});
					}
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
