package org.progressivelifestyle.weedmaps.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.progressivelifestyle.weedmap.persistence.domain.Dispensary;
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryEntity;
import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;
import org.progressivelifestyle.weedmaps.objects.DispensaryObject;
import org.progressivelifestyle.weedmaps.scraper.DispensaryDetailScraper;
import org.progressivelifestyle.weedmaps.scraper.DispensaryLocationScraper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WeedmapProcessor {
	private CompletionService<Set<String>> locationService;
	private CompletionService<DispensaryObject> dispensaryDetailService;
	private Executor locationExecutor = new ThreadPoolExecutor(10, 20, Long.MAX_VALUE, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());
	private Executor dispensaryDetailExecutor = new ThreadPoolExecutor(10, 20, Long.MAX_VALUE, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());
	private Executor spreadSheetWriterExecutor = Executors.newFixedThreadPool(10);
	private static final Log logger = LogFactory.getLog(WeedmapProcessor.class);
	private final LinkedBlockingQueue<Dispensary> dispensariesSuccessfullyPersisted = new LinkedBlockingQueue<Dispensary>();
	private final LinkedBlockingQueue<Dispensary> dispensariesFailedToPersist = new LinkedBlockingQueue<Dispensary>();
	private final LinkedBlockingQueue<Dispensary> dispensariesFailedToScrape = new LinkedBlockingQueue<Dispensary>();
	// private WeedmapScraperCache cache;
	private AtomicInteger submittedTasks;
	private static ApplicationContext ctx;
	private String finishedDispensariesFileName;
	private String failedInPersistenceDispensariesFileName;
	private String failedInScrapingDispensariesFileName;

	public WeedmapProcessor() {
		locationService = new ExecutorCompletionService<Set<String>>(locationExecutor);
		dispensaryDetailService = new ExecutorCompletionService<DispensaryObject>(dispensaryDetailExecutor);
		submittedTasks = new AtomicInteger(0);
		finishedDispensariesFileName = "C:/logs/dispnesary-scraped.txt";
		failedInPersistenceDispensariesFileName = "C:/logs/dispnesary-failed-persistence.txt";
		failedInScrapingDispensariesFileName = "C:/logs/dispnesary-failed-scrape.txt";
	}

	public static void main(String[] args) throws Exception {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		new WeedmapProcessor().initScraping((DispensaryService) ctx.getBean(DispensaryService.class));
	}

	@SuppressWarnings("unchecked")
	public void initScraping(DispensaryService service) throws Exception {
		logger.info("Begining to cache all present data in google doc");
		// cacheAllObjectsFromSpreasheet();
		logger.info("Begining scraping");
		((ThreadPoolExecutor) spreadSheetWriterExecutor).prestartAllCoreThreads();
		// String dispensaryBaseScraperConfig =
		// WeedmapProcessor.readFile("dispensary-base-scraper.xml");
		// String dispensaryLocationScraperConfig =
		// WeedmapProcessor.readFile("dispensary-location-scraper.xml");
		String dispensaryDetaisScraperConfig = WeedmapProcessor.readFile("dispensary-info-scraper.xml");
		/*
		 * logger.info("Finished caching all config files."); DispensaryScraper
		 * levelOneScraper = new DispensaryScraper(null,
		 * dispensaryBaseScraperConfig);
		 * logger.info("Begining level one scraping"); Set<String>
		 * urlOfDispensaryLocations =
		 * levelOneScraper.findURLOfDispensaries("https://weedmaps.com/");
		 * logger.info("URLs obtained from level one scraping. Size "+
		 * urlOfDispensaryLocations.size()); Set<String> urlOfDispensaries =
		 * scrapeForDispensaryURLs
		 * (urlOfDispensaryLocations,dispensaryLocationScraperConfig );
		 */
		List<String> urlOfDispensariesFromFile = FileUtils.readLines(new File("C:/logs/URLList.txt"));
		Set<String> urlOfDispensaries = new HashSet<String>();
		urlOfDispensaries.addAll(urlOfDispensariesFromFile);
		logger.info("Dispensaries obtained " + urlOfDispensaries.size());
		ScrapingUtility.serializeToDisk(urlOfDispensaries);
		startThreadForReport(dispensariesSuccessfullyPersisted, finishedDispensariesFileName);
		startThreadForReport(dispensariesFailedToPersist, failedInPersistenceDispensariesFileName);
		startThreadForReport(dispensariesFailedToScrape, failedInScrapingDispensariesFileName);
		scrapeDispensaryDetailsForDispensaries(urlOfDispensaries, dispensaryDetaisScraperConfig, service);
	}

	private void startThreadForReport(final LinkedBlockingQueue<Dispensary> dispensaryQueue, final String fileName) {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					Collection<Dispensary> dispensaries = new ArrayList<Dispensary>();
					if (!dispensaryQueue.isEmpty()) {
						((LinkedBlockingQueue<Dispensary>) dispensaryQueue).drainTo(dispensaries);
						try {
							writeDispensaryURLsToFile(dispensaries, fileName);
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}

		}).start();
	}

	private void scrapeDispensaryDetailsForDispensaries(Collection<String> urlOfDispensaries, String dispensaryDetaisScraperConfig, final DispensaryService service) throws Exception {
		final Set<Dispensary> dispensaries = new HashSet<Dispensary>();
		Queue<Future<DispensaryObject>> dispensaryFutQueue = new LinkedList<Future<DispensaryObject>>();
		int counter = 0;
		for (String dispensaryURL : urlOfDispensaries) {
			if(dispensaryURL.trim().isEmpty())
				continue;
			dispensaryFutQueue.offer(dispensaryDetailService.submit(new DispensaryDetailScraper(null, dispensaryDetaisScraperConfig, dispensaryURL)));
			counter++;
			if (counter % 10 == 0) {
				counter = 0;
				logger.error("Going to process 10 dispensaries;");
				processDispensary(dispensaryFutQueue, dispensaries, service);
			}
			logger.error("Processed 10 dispensaries;");
		}

		if (!dispensaryFutQueue.isEmpty()) {
			logger.error("Dispensary queue not empty yet");
			processDispensary(dispensaryFutQueue, dispensaries, service);
		}
		logger.error("All dispensaries processed!!");
	}

	private void processDispensary(Queue<Future<DispensaryObject>> dispensaryFutQueue, Set<Dispensary> dispensaries, DispensaryService service) throws Exception {
		DispensaryObject obj = null;
		while (!dispensaryFutQueue.isEmpty()) {
			obj = getFromDispensaryQueue(dispensaryFutQueue);
			if (obj == null)
				continue;
			try {
				logger.info("Task response got from future");
				if (obj != null) {
					obj = dispensaryFutQueue.poll().get();
					dispensaries.add(obj);
					if (dispensaries.size() >= 5) {
						final Set<Dispensary> dispensariesToWorkWith = new HashSet<Dispensary>();
						dispensariesToWorkWith.addAll(dispensaries);
						dispensaries.clear();
						persistDispensariesInThread(dispensariesToWorkWith, service);
					}
					System.out.println(obj.getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void persistDispensariesInThread(Set<Dispensary> dispensariesToWorkWith, DispensaryService service) throws Exception {
		while (submittedTasks.intValue() >= 5) {
			logger.warn("Going to wait as activie count is " + submittedTasks.intValue());
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Submitting task " + dispensariesToWorkWith.size());
		spreadSheetWriterExecutor.execute(new DispensaryPersister(dispensariesToWorkWith, service));
	}

	private DispensaryObject getFromDispensaryQueue(Queue<Future<DispensaryObject>> dispensaryFutQueue) throws InterruptedException {
		DispensaryObject obj = null;
		try {
			obj = dispensaryFutQueue.peek().get(1, TimeUnit.SECONDS);
			if (obj != null && obj.getDispensaryId() == null) {
				dispensariesFailedToScrape.offer(dispensaryFutQueue.remove().get());
				return null;
			}
		} catch (TimeoutException e) {
			Thread.sleep(2000);
		} catch (ExecutionException ee) {
			dispensaryFutQueue.remove();
			ee.printStackTrace();
		}
		return obj;
	}

	public Set<String> scrapeForDispensaryURLs(Set<String> urlOfDispensaryLocations, String dispensaryLocationScraperConfig) throws InterruptedException, ExecutionException {
		List<Future<Set<String>>> futureList = new ArrayList<Future<Set<String>>>();
		for (String locationURL : urlOfDispensaryLocations)
			futureList.add(locationService.submit(new DispensaryLocationScraper(null, dispensaryLocationScraperConfig, locationURL, "https://weedmaps.com")));
		Set<String> restaurantURLs = new HashSet<String>();
		for (Future<Set<String>> aFuture : futureList)
			restaurantURLs.addAll(aFuture.get());
		return restaurantURLs;
	}

	public static String readFile(String fileName) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(DispensaryDetailScraper.class.getClassLoader().getResourceAsStream(fileName)));
		String line = "";
		StringBuilder builder = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}
		return builder.toString();
	}

	private synchronized void writeDispensaryURLsToFile(Collection<Dispensary> dispensaries, String fileName) throws Exception {
		StringBuilder builder = new StringBuilder(System.getProperty("line.separator"));
		for (Dispensary dispensary : dispensaries)
			builder.append(dispensary.getDispensaryURL()).append(System.getProperty("line.separator"));
		FileWriter writer = new FileWriter(new File(fileName), true);
		try {
			writer.append(builder);
		} catch (Exception e) {
			throw e;
		} finally {
			writer.close();
		}
	}

	class DispensaryPersister implements Runnable {
		private Set<Dispensary> dispensariesToPersist;
		private DispensaryService service;

		public DispensaryPersister(Set<Dispensary> dispensariesToPersist, DispensaryService service) {
			super();
			this.dispensariesToPersist = dispensariesToPersist;
			this.service = service;
		}

		public void run() {
			submittedTasks.incrementAndGet();
			try {
				persistDispensaries();
				//dispensariesSuccessfullyPersisted.addAll(dispensariesToPersist);
			} catch (Exception e) {
				e.printStackTrace();
				//dispensariesFailedToPersist.addAll(dispensariesToPersist);
			} finally {
				submittedTasks.decrementAndGet();
			}
		}

		private void persistDispensaries() throws Exception {
			logger.info("Proceeding to write into DB.");
			//Set<DispensaryEntity> entities = new HashSet<DispensaryEntity>(dispensariesToPersist.size());
			for (Dispensary dispensary : dispensariesToPersist) {
				DispensaryEntity dispensaryToPersist = ScrapingUtility.convertDispensaryIntoPersistenceObject(dispensary, service);
				if (dispensaryToPersist != null){
					try {
						service.createDispensary(dispensaryToPersist);
						dispensariesSuccessfullyPersisted.addAll(dispensariesToPersist);
					} catch (Exception e) {
						logger.error("Failed to persist dispensary wih id: "+dispensary.getDispensaryId(), e);
						logger.info("trying persisting dispensary and menu items seperately.");
						service.createDispensaryAndMenuItemSeperately(dispensary);
						dispensariesFailedToPersist.addAll(dispensariesToPersist);
					}
					//entities.add(dispensaryToPersist);
				}
			}
			/*logger.info("Going to persist " + entities.size() + " dispensaries in DB");
			service.storeDispensaries(entities);
			logger.info("Written to DB.");*/
		}

	}
}
