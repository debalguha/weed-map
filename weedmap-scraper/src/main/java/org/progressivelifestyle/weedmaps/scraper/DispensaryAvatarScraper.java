package org.progressivelifestyle.weedmaps.scraper;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.progressivelifestyle.weedmap.persistence.domain.Menu;
import org.progressivelifestyle.weedmaps.objects.MenuItem;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.ProxyConfiguration;
import org.webharvest.runtime.Scraper;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DispensaryAvatarScraper extends BaseScraper {
	private static final Log logger = LogFactory.getLog(DispensaryAvatarScraper.class);
	private String scraperConfigContent;
	private static final ObjectMapper mapper = new ObjectMapper();
	public DispensaryAvatarScraper(ProxyConfiguration proxyConfig, String scraperConfigContent) {
		super(proxyConfig);
		this.scraperConfigContent = scraperConfigContent;
	}
	public Map<Long, String> findDiepensaryIdAndURL() throws Exception{
		DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/instant420", "instant420", "instant420");
		ResultSet rs = conn.createStatement().executeQuery("select d.id, d.dispensaryURL from dispensaryentity d, menuitementity m where d.id = m.dispensaryid and m.menuitemcategoryid is null and d.id in (31190,31208,31229,31263,31267,31376,31420,31432,31492,31504,31575,31577,31606,31607,31615,31626)");
		Map<Long, String> retMap = Maps.newHashMap();
		while(rs.next())
			retMap.put(rs.getLong(1), rs.getString(2));
		rs.close();
		conn.close();
		return retMap;
	}
	public void scrapeDispensaryForImagesAndStoreInDB() throws Exception{
		ExecutorService service = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000));
		Map<Long, String> idAndURL = findDiepensaryIdAndURL();
		List<Future<String>> futs = Lists.newArrayList();
		logger.error("Total "+idAndURL.size()+" dispensary to process");
		for(final Long id : idAndURL.keySet()){
			final String url = idAndURL.get(id);
			futs.add(service.submit(new AvatarScraperCallable(id, url)));
		}
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/instant420", "instant420", "instant420");
		Statement statement = conn.createStatement();
		logger.error("Going to check futures");
		for(Future<String> future : futs){
			String query = future.get();
			if(query!=null && !query.isEmpty())
				try {
					statement.addBatch(future.get(10, TimeUnit.SECONDS));
					statement.executeBatch();
				} catch (Exception e) {
					logger.error("Error in future: "+e.getMessage());
				}
		}
		logger.error("Adding batch statmenets");
		statement.executeBatch();
		statement.close();
		conn.close();
	}
	
	public void scrapeDispensaryForMenuItemCategoryAndStoreInDB() throws Exception{
		ExecutorService service = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000));
		Map<Long, String> idAndURL = findDiepensaryIdAndURL();
		logger.error("Total "+idAndURL.size()+" dispensary to process");
		List<Future<List<String>>> futs = Lists.newArrayList();
		for(final Long id : idAndURL.keySet()){
			final String url = idAndURL.get(id);
			futs.add(service.submit(new MenuItemScrapable(url)));
		}
/*		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/instant420", "instant420", "instant420");
		Statement statement = conn.createStatement();
		logger.error("Going to check futures");
		for(Future<List<String>> future : futs){
			List<String> queries = future.get();
			if(queries!=null && !queries.isEmpty()){
				for(String query : queries){
					statement.addBatch(query);
				}
				int[] executeBatch = statement.executeBatch();
				List<Integer> rets = Lists.newArrayList();
				for(int ret : executeBatch)
					rets.add(ret);
				logger.error("Batch executed:: "+rets);
			}
		}
		statement.executeBatch();
		logger.error("Adding batch statmenets");
		statement.executeBatch();
		statement.close();
		conn.close();*/
	}
	
	public static void main(String args[]) throws Exception{
		DispensaryAvatarScraper scraper = new DispensaryAvatarScraper(null, BaseScraper.readFile("dispensary-menu.xml"));
		scraper.scrapeDispensaryForMenuItemCategoryAndStoreInDB();
	}
	public class MenuItemScrapable implements Callable<List<String>>{
		private final String url;
		
		public MenuItemScrapable(String url) {
			super();
			this.url = url;
		}
		
		public List<String> call() throws Exception {
			List<String> retList = Lists.newArrayList();
			try {
				logger.info("Scrape request arrived for: " + url);
				ScraperConfiguration config = new ScraperConfiguration(new InputSource(new StringReader(scraperConfigContent.replaceAll("#URL#", url))));
				Scraper scraper = buildScraper(config, System.getProperty("java.io.tmpdir"));
				logger.info("Scrapping execution will start!");
				scraper.execute();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Set<Menu> menuItemsCollection = parseJsonsIntoMenuItems(scraper.getContext().getVar("menu-items-alt").toArray());
				logger.info("Menu Items retrieved: " + menuItemsCollection.size());
				int menuWithNoCategory = 0;
				for(Menu menu : menuItemsCollection){
					if(menu.getMenuItemCategoryId()==null || menu.getMenuItemCategoryId()==0)
						menuWithNoCategory++;
					retList.add("update menuitementity set menuItemCategoryId="+menu.getMenuItemCategoryId()+" where id="+menu.getId());
					logger.error("update menuitementity set menuItemCategoryId="+menu.getMenuItemCategoryId()+" where id="+menu.getId()+";");
				}
				logger.info("menu with null category:: "+menuWithNoCategory);
			} catch (Exception e) {
				logger.error("error while scraping"+e.getMessage());
			}
			return retList;
		}		
		private Set<Menu> parseJsonsIntoMenuItems(Object[] jsons) throws JsonParseException, JsonMappingException, IOException {
			Set<Menu> menuItems = new HashSet<Menu>();
			for (Object json : jsons)
				menuItems.add(mapper.readValue(json.toString(), MenuItem.class));
			return menuItems;
		}
	}
	public class AvatarScraperCallable implements Callable<String>{
		private final Long id;
		private final String url;
		
		public AvatarScraperCallable(Long id, String url) {
			super();
			this.id = id;
			this.url = url;
		}

		public String call() throws Exception {
			logger.error("Scrape request arrived for: " + url);
			ScraperConfiguration config = new ScraperConfiguration(new InputSource(new StringReader(scraperConfigContent.replaceAll("#URL#", url))));
			Scraper scraper = buildScraper(config, System.getProperty("java.io.tmpdir"));
			logger.error("Scrapping execution will start!");
			scraper.execute();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String imageURL = scraper.getContext().getVar("imgUrl")==null?null:scraper.getContext().getVar("imgUrl").toString();
			logger.error("Ïmage url: "+imageURL);
			if(imageURL!=null && !imageURL.isEmpty()){
				if(imageURL.startsWith("/"))
					imageURL = "https://weedmap.com".concat(imageURL);
				return "update dispensaryentity set dispensaryImageURL = '"+imageURL+"' where id = "+id;
			}
			return "";
		}
		
	}
}
