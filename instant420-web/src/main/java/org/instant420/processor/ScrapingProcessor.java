package org.instant420.processor;

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
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.progressivelifestyle.weedmap.persistence.PersistenceUtil;
import org.progressivelifestyle.weedmap.persistence.domain.Dispensary;
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryEntity;
import org.progressivelifestyle.weedmap.persistence.domain.Menu;
import org.progressivelifestyle.weedmap.persistence.domain.MenuItemEntity;
import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;
import org.progressivelifestyle.weedmaps.objects.DispensaryObject;
import org.progressivelifestyle.weedmaps.processor.ScrapingUtility;
import org.progressivelifestyle.weedmaps.processor.WeedmapProcessor;
import org.progressivelifestyle.weedmaps.scraper.DispensaryDetailScraper;
import org.progressivelifestyle.weedmaps.scraper.DispensaryLocationScraper;
import org.progressivelifestyle.weedmaps.scraper.DispensaryScraper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScrapingProcessor implements InitializingBean{
	private String baseScraperFileName;
	private String locationScraperFileName;
	private String dispensaryScraperFileName;
	@Autowired
	private DispensaryService dispensaryService;
	private CompletionService<Set<String>> locationService;
	private CompletionService<DispensaryObject> dispensaryDetailService;
	private Executor locationExecutor = new ThreadPoolExecutor(10, 20, Long.MAX_VALUE, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());
	private Executor dispensaryDetailExecutor = new ThreadPoolExecutor(10, 20, Long.MAX_VALUE, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());
	//private Executor spreadSheetWriterExecutor = Executors.newFixedThreadPool(10);
	private static final Log logger = LogFactory.getLog(ScrapingProcessor.class);
	private final LinkedBlockingQueue<Dispensary> dispensariesSuccessful = new LinkedBlockingQueue<Dispensary>();
	private final LinkedBlockingQueue<Dispensary> dispensariesFailedToPersist = new LinkedBlockingQueue<Dispensary>();
	private final LinkedBlockingQueue<Dispensary> dispensariesFailedToScrape = new LinkedBlockingQueue<Dispensary>();	
	private AtomicInteger submittedTasks;
	public void loadAllDispensaryInCache(){
		dispensaryService.loadAllDispensaryForCache();
	}
	
	public void startScraping() throws Exception{
		logger.info("Begining scraping!!");
		DispensaryScraper l1Scraper = new DispensaryScraper(null, WeedmapProcessor.readFile(baseScraperFileName));
		Set<String> urlOfDispensaryLocations = l1Scraper.findURLOfDispensaries("https://weedmap.com");
		Set<String> urlOfDispensaries = scrapeForDispensaryURLs(urlOfDispensaryLocations,WeedmapProcessor.readFile(locationScraperFileName) );
		scrapeDispensaryDetailsForDispensaries(urlOfDispensaries, WeedmapProcessor.readFile(dispensaryScraperFileName), dispensaryService);
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
						compareDispensaryWithCache(dispensariesToWorkWith, service);
					}
					System.out.println(obj.getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}	
	
	private void compareDispensaryWithCache(Set<Dispensary> dispensariesToWorkWith, DispensaryService service) throws Exception{
		for(Dispensary dispensary : dispensariesToWorkWith){
			DispensaryEntity dispensaryEntity = ScrapingUtility.convertDispensaryIntoPersistenceObject(dispensary, service);
			DispensaryEntity cachedDispensaryEntity = service.findDispensary(dispensary.getDispensaryId());
			Set<Menu> menuItems = dispensaryEntity.getMenuItems();
			Set<Menu> menuItemsFromCachedDispensary = cachedDispensaryEntity.getMenuItems();
			if(!dispensaryEntity.isLogicallyEquals(cachedDispensaryEntity)){
				dispensaryEntity.setMenuItems(null);
				dispensaryService.updateEntity(dispensaryEntity);
			}
			Set<Menu> menuItemsToBeAdded = PersistenceUtil.findNewlyAddedMenuItems(menuItems, menuItemsFromCachedDispensary);
			Set<Menu> menuItemsToBeUpdated = PersistenceUtil.findUpdatedMenuItems(menuItemsFromCachedDispensary, menuItems);
			for(Menu menu : menuItemsToBeAdded){
				try{
					service.createEntityWithIndependentTransaction((MenuItemEntity)menu);
				} catch(Exception e){
					logger.error("Unable to create menu with id: "+menu.toString());
				}
			}
			for(Menu menu : menuItemsToBeUpdated){
				try{
					service.updateEntity((MenuItemEntity)menu);
				} catch(Exception e){
					logger.error("Unable to update menu with id: "+menu.toString());
				}
			}			
		}
	}

	/*private void persistDispensariesInThread(Set<Dispensary> dispensariesToWorkWith, DispensaryService service) throws Exception {
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
	}*/
	public Set<String> scrapeForDispensaryURLs(Set<String> urlOfDispensaryLocations, String dispensaryLocationScraperConfig) throws InterruptedException, ExecutionException {
		List<Future<Set<String>>> futureList = new ArrayList<Future<Set<String>>>();
		for (String locationURL : urlOfDispensaryLocations)
			futureList.add(locationService.submit(new DispensaryLocationScraper(null, dispensaryLocationScraperConfig, locationURL, "https://weedmaps.com")));
		Set<String> restaurantURLs = new HashSet<String>();
		for (Future<Set<String>> aFuture : futureList)
			restaurantURLs.addAll(aFuture.get());
		return restaurantURLs;
	}	
	
	public void setDispensaryService(DispensaryService dispensaryService) {
		this.dispensaryService = dispensaryService;
	}

	public void afterPropertiesSet() throws Exception {
		locationService = new ExecutorCompletionService<Set<String>>(locationExecutor);
		dispensaryDetailService = new ExecutorCompletionService<DispensaryObject>(dispensaryDetailExecutor);
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
						dispensariesSuccessful.addAll(dispensariesToPersist);
					} catch (Exception e) {
						logger.error("Failed to persist dispensary wih id: "+dispensary.getDispensaryId(), e);
						logger.info("trying persisting dispensary and menu items seperately.");
						service.createDispensaryAndMenuItemSeperately(dispensary);
						dispensariesFailedToPersist.addAll(dispensariesToPersist);
					}
				}
			}
		}

	}	
}
