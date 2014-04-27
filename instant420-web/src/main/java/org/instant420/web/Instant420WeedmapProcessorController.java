package org.instant420.web;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.instant420.processor.ScrapingProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/scraper")
public class Instant420WeedmapProcessorController {
	@Autowired
	private ScrapingProcessor processor;
	@Value("${scraper.static.url.file.path}")
	private String staticFilePath;
	@RequestMapping("/weedmap")
	public @ResponseBody String processWeedMapScrapingRequest(){
		new Thread(new Runnable(){
			@SuppressWarnings("unchecked")
			public void run() {
				try {
					processor.scrapeDispensaryDetailsForDispensaries(FileUtils.readLines(new File(staticFilePath)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		return "Scraping started!!";
	}
	@RequestMapping("/weedmap/static")
	public @ResponseBody String processWeedMapScrapingRequestForStaticURLs(){
		new Thread(new Runnable(){
			public void run() {
				try {
					processor.startScraping();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		return "Scraping started!!";
	}
	public void setProcessor(ScrapingProcessor processor) {
		this.processor = processor;
	}
	public void setStaticFilePath(String staticFilePath) {
		this.staticFilePath = staticFilePath;
	}
}
