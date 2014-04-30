package org.progressivelifestyle.weedmaps.scraper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.webharvest.runtime.ProxyConfiguration;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MedicineCategoryScraper extends BaseScraper {
	private static final Log logger = LogFactory.getLog(DispensaryAvatarScraper.class);
	private String scraperConfigContent;
	public MedicineCategoryScraper(ProxyConfiguration proxyConfig, String scraperConfigContent) {
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
	
	public void scrapeDispensaryForMenuItemCategoryAndStoreIntoDB() throws Exception {
		ExecutorService service = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000));
		Map<Long, String> idAndURL = findDiepensaryIdAndURL();
		List<Future<String>> futs = Lists.newArrayList();
	}
}
