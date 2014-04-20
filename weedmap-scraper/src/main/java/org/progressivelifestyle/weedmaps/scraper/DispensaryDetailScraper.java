package org.progressivelifestyle.weedmaps.scraper;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.progressivelifestyle.weedmap.persistence.domain.Menu;
import org.progressivelifestyle.weedmaps.objects.Address;
import org.progressivelifestyle.weedmaps.objects.DispensaryObject;
import org.progressivelifestyle.weedmaps.objects.MenuItem;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.ProxyConfiguration;
import org.webharvest.runtime.Scraper;
import org.xml.sax.InputSource;

public class DispensaryDetailScraper extends BaseScraper implements Callable<DispensaryObject>{
	private String scraperConfigContent;
	private String urlToScrape;
	private static Map<Integer, String> menuItemCategoryMap;
	private static final Log logger = LogFactory.getLog(DispensaryDetailScraper.class);
	private static ObjectMapper mapper = new ObjectMapper();
	private static Object lockObj = new Object();
	public DispensaryDetailScraper(ProxyConfiguration proxyConfig, String scraperConfigContent, String urlToScrape) {
		super(proxyConfig);
		this.scraperConfigContent = scraperConfigContent;
		this.urlToScrape = urlToScrape;
	}
	
	public DispensaryDetailScraper(ProxyConfiguration proxyConfig, String scraperConfigContent) {
		super(proxyConfig);
		this.scraperConfigContent = scraperConfigContent;
	}
	
	public DispensaryObject call() throws Exception {
		DispensaryObject scrapeDispensaryDetailsWithMenuItem = null;
		try {
			scrapeDispensaryDetailsWithMenuItem = scrapeDispensaryDetailsWithMenuItem(urlToScrape);
			Thread.sleep(1000);
			if(scrapeDispensaryDetailsWithMenuItem == null)
				throw new NullPointerException("Dispensary Object is null!!");
		} catch (Exception e) {
			logger.error("Exception while processing "+urlToScrape, e);
			DispensaryObject dispensaryObject = new DispensaryObject();
			dispensaryObject.setDispensaryURL(urlToScrape);
			return dispensaryObject;
		}
		return scrapeDispensaryDetailsWithMenuItem;
	}
	
	public DispensaryObject scrapeDispensaryDetailsWithMenuItem(String URL) throws Exception{
		logger.info("Scrape request arrived for: "+URL);
		ScraperConfiguration config = new ScraperConfiguration(new InputSource(
				new StringReader(scraperConfigContent.replaceAll("#URL#", URL))));
		Scraper scraper = buildScraper(config, System.getProperty("java.io.tmpdir"));
		logger.info("Scrapping execution will start!");
		try {
			scraper.execute();
		} catch (Exception e) {
			logger.warn("Scraper thrown error. Begining retry!");
			int retryCount=5,retry=0;
			boolean success = false;
			while(retry<retryCount){
				try{
					logger.warn("("+(retry++)+")th retry.");
					scraper.execute();
					logger.info("Retry succeeded.");
					success = true;
					break;
				}catch(Exception ee){
					logger.error("Error in retry!!");
				}
				Thread.sleep(5000);
			}
			if(!success)
				throw (e);
		}
		logger.info("Scrapping execution Ended! Start extracting the variables.");
		String name = scraper.getContext().getVar("name").toString();
		String phone = scraper.getContext().getVar("phone").toString();
		String website = scraper.getContext().getVar("website").toString();
		String email = scraper.getContext().getVar("email").toString();
		String street = scraper.getContext().getVar("street").toString();
		String city = scraper.getContext().getVar("city").toString();
		String state = scraper.getContext().getVar("state").toString();
		String zip = scraper.getContext().getVar("zip").toString();
		String facebook = scraper.getContext().getVar("facebook").toString();
		String twitter = scraper.getContext().getVar("twitter").toString();
		String instagram = scraper.getContext().getVar("instagram").toString();
		boolean creditCard = scraper.getContext().getVar("creditcard").toString().isEmpty()?false:true;
		boolean handicap = scraper.getContext().getVar("handicap").toString().isEmpty()?false:true;
		boolean security = scraper.getContext().getVar("security").toString().isEmpty()?false:true;
		boolean photos = scraper.getContext().getVar("photos").toString().isEmpty()?false:true;
		boolean delivery = scraper.getContext().getVar("delivery").toString().isEmpty()?false:true;
		boolean labTested = scraper.getContext().getVar("labTested").toString().isEmpty()?false:true;
		boolean adult = scraper.getContext().getVar("adult").toString().isEmpty()?false:true;
		String sunOpen = scraper.getContext().getVar("sunOpen").toString();
		String sunClose = scraper.getContext().getVar("sunClose").toString();
		String monOpen = scraper.getContext().getVar("monOpen").toString();
		String monClose = scraper.getContext().getVar("monClose").toString();
		String tueOpen = scraper.getContext().getVar("tueOpen").toString();
		String tueClose = scraper.getContext().getVar("tueClose").toString();
		String wedOpen = scraper.getContext().getVar("wedOpen").toString();
		String wedClose = scraper.getContext().getVar("wedClose").toString();
		String thuOpen = scraper.getContext().getVar("thuOpen").toString();
		String thuClose = scraper.getContext().getVar("thuClose").toString();
		String friOpen = scraper.getContext().getVar("friOpen").toString();
		String friClose = scraper.getContext().getVar("friClose").toString();
		String satOpen = scraper.getContext().getVar("satOpen").toString();
		String satClose = scraper.getContext().getVar("satClose").toString();
		String dataListing = scraper.getContext().getVar("data-listing").toString();
		if(menuItemCategoryMap == null){
			synchronized(lockObj){
				if(menuItemCategoryMap == null)
					menuItemCategoryMap = buildMenuItemCategoryMap(scraper.getContext().getVar("menu-items-category").toString());
			}
		}
		Set<Menu> menuItemsCollection = parseJsonsIntoMenuItems(scraper.getContext().getVar("menu-items-alt").toArray());
		logger.info("Menu Items retrieved: "+menuItemsCollection.size());
		Long dispensaryId = new Long(-1);
		try {
			dispensaryId = parseJsonToRetrieveDispensaryId(dataListing);
			logger.error("DispensaryId:: "+dispensaryId);
		} catch (Exception e) {
			throw e;
		}
		DispensaryObject dispensary = new DispensaryObject(dispensaryId, name, phone, email, website, new Address(street, null, city, state, zip), 
				facebook, twitter, instagram, creditCard, handicap, security, photos, labTested, adult, delivery, sunOpen, sunClose, monOpen, monClose, tueOpen, tueClose, wedOpen, wedClose, thuOpen, thuClose, friOpen, friClose, satOpen, satClose, urlToScrape);
		dispensary.setMenuItems(menuItemsCollection);
		return dispensary;
	}

	private Long parseJsonToRetrieveDispensaryId(String dataListing) throws JsonProcessingException, IOException {
		JsonNode jsonNode = mapper.readTree(dataListing);
		return jsonNode.get("id").getLongValue();
	}

	@SuppressWarnings("unchecked")
	private Map<Integer, String> buildMenuItemCategoryMap(String jsonObject) throws JsonParseException, JsonMappingException, IOException {
		Map<Integer, String> catgoryMapMaster = new HashMap<Integer, String>();
		jsonObject = jsonObject.replaceAll("&qquot;", "\"");
		List<Map<String, Object>> categoryMaps = (List<Map<String, Object>>)mapper.readValue(jsonObject, List.class);
		for(Map<String, Object> categoryMap : categoryMaps)
			catgoryMapMaster.put((Integer)categoryMap.get("id"), categoryMap.get("name").toString());
		return catgoryMapMaster;
	}

	private Set<Menu> parseJsonsIntoMenuItems(Object[] jsons) throws JsonParseException, JsonMappingException, IOException {
		Set<Menu> menuItems = new HashSet<Menu>();
		for(Object json : jsons)
			menuItems.add(mapper.readValue(json.toString(), MenuItem.class));
		return menuItems;
	}
}
