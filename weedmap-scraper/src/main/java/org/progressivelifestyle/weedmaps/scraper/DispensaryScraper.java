package org.progressivelifestyle.weedmaps.scraper;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.ProxyConfiguration;
import org.webharvest.runtime.Scraper;
import org.xml.sax.InputSource;

public class DispensaryScraper extends BaseScraper {
	private String scraperConfigContent;
	private static final Log logger = LogFactory.getLog(DispensaryScraper.class);
	public DispensaryScraper(ProxyConfiguration proxyConfig, String scraperConfigContent) {
		super(proxyConfig);
		this.scraperConfigContent = scraperConfigContent;
	}
	
	public Set<String> findURLOfDispensaries(String URL){
		logger.info("Scrape request arrived for: "+URL);
		ScraperConfiguration config = new ScraperConfiguration(new InputSource(
				new StringReader(scraperConfigContent.replaceAll("#URL#", URL))));
		Scraper scraper = buildScraper(config, System.getProperty("java.io.tmpdir"));
		logger.info("Scrapping execution will start!");
		scraper.execute();
		logger.info("Scrapping execution Ended! Start extracting the variables.");
		Object []locationURLs = scraper.getContext().getVar("urls").toArray();
		Set<String> retSet = new HashSet<String>();
		int counter=0;
		for(Object locationUrlNode : locationURLs){
/*			if(counter==1)
				break;*/
		
			retSet.add(URL.concat(locationUrlNode.toString()));
			counter++;
		}
		return retSet;
	}
}
