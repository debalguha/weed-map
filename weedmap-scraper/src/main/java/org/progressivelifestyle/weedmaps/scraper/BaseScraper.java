package org.progressivelifestyle.weedmaps.scraper;

import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.ProxyConfiguration;
import org.webharvest.runtime.Scraper;

public class BaseScraper {
	protected boolean isProxyEnabled;
	protected ProxyConfiguration proxyConfig;
	protected String paramSuffix;
	
	public BaseScraper(ProxyConfiguration proxyConfig) {
		this();
		if(proxyConfig==null)
			return;
		this.proxyConfig = proxyConfig;
		if(proxyConfig.getProxyHost()!=null && !proxyConfig.getProxyHost().isEmpty())
			this.isProxyEnabled = true;		
	}

	public BaseScraper() {
		super();
		this.isProxyEnabled = false;
	}

	protected Scraper buildScraper(ScraperConfiguration config, String workingDir) {
		if(isProxyEnabled)
			return new Scraper(config, workingDir, proxyConfig);
		else
			return new Scraper(config, workingDir);
	}	
}
