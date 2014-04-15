package org.progressivelifestyle.weedmaps.scraper;

import java.io.StringReader;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.ProxyConfiguration;
import org.webharvest.runtime.Scraper;
import org.xml.sax.InputSource;

public class DispensaryLocationScraper extends BaseScraper implements Callable<Set<String>>{
	private String scraperConfigContent;
	private String urlToScrape;
	private String prefixURL;
	private static final Log logger = LogFactory.getLog(DispensaryLocationScraper.class);
	public DispensaryLocationScraper(ProxyConfiguration proxyConfig, String scraperConfigContent, String urlToScrape, String prefixURL) {
		super(proxyConfig);
		this.scraperConfigContent = scraperConfigContent;
		this.urlToScrape = urlToScrape;
		this.prefixURL = prefixURL;
	}
	
	public Set<String> call() throws Exception {
		Set<String> findLocationOfDispensaries = new HashSet<String>(1);
		try {
			findLocationOfDispensaries = findLocationOfDispensaries(urlToScrape, prefixURL);
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return findLocationOfDispensaries;
	}
	
	public Set<String> findLocationOfDispensaries(String URL, String prefixURL){
		logger.info("Scrape request arrived for: "+URL);
		ScraperConfiguration config = new ScraperConfiguration(new InputSource(
				new StringReader(scraperConfigContent.replaceAll("#URL#", URL))));
		Scraper scraper = buildScraper(config, System.getProperty("java.io.tmpdir"));
		logger.info("Scrapping execution will start!");
		scraper.execute();
		logger.info("Scrapping execution Ended! Start extracting the variables.");
		Object []locationURLs = scraper.getContext().getVar("urls").toArray();
		Set<String> retSet = new TreeSet<String>(new Comparator<String>() {
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		for(Object locationUrl : locationURLs)
			retSet.add(prefixURL.concat(locationUrl.toString()));
		return retSet;
	}
}
