package org.progressivelifestyle.weedmaps.scraper;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.ProxyConfiguration;
import org.webharvest.runtime.Scraper;
import org.xml.sax.InputSource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DispensaryAvatarScraper extends BaseScraper {
	private static final Log logger = LogFactory.getLog(DispensaryAvatarScraper.class);
	private String scraperConfigContent;
	public DispensaryAvatarScraper(ProxyConfiguration proxyConfig, String scraperConfigContent) {
		super(proxyConfig);
		this.scraperConfigContent = scraperConfigContent;
	}
	public Map<Long, String> findDiepensaryIdAndURL() throws Exception{
		DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/instant420", "instant420", "instant420");
		ResultSet rs = conn.createStatement().executeQuery("select id, dispensaryURL from dispensaryentity where dispensaryImageURL is null");
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
				statement.addBatch(future.get());
		}
		logger.error("Adding batch statmenets");
		statement.executeBatch();
		statement.close();
		conn.close();
	}
	public static void main(String args[]) throws Exception{
		DispensaryAvatarScraper scraper = new DispensaryAvatarScraper(null, BaseScraper.readFile("dispensary-avatar.xml"));
		scraper.scrapeDispensaryForImagesAndStoreInDB();
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
