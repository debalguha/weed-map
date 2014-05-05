package org.instant420.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.instant420.processor.GeoCodingHelper;
import org.instant420.processor.MapPoint;
import org.instant420.web.domain.DispensarySearchObject;
import org.instant420.web.domain.MenuItemSearchObject;
import org.instant420.web.domain.ResultMeta;
import org.instant420.web.domain.SearchType;
import org.progressivelifestyle.weedmap.persistence.domain.BaseEntity;
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryEntity;
import org.progressivelifestyle.weedmap.persistence.domain.EntityType;
import org.progressivelifestyle.weedmap.persistence.domain.SearchQueryEntity;
import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

@Controller
@RequestMapping("/search")
public class Instant420SearchController {
	@Autowired
	@Qualifier("solrServerForDispensary")
	private SolrServer solrServerForDispensary;
	
	@Autowired
	@Qualifier("solrServerForMedicines")
	private SolrServer solrServerForMedicines;
	
	/*@Autowired
	@Qualifier("solrServerCombined")
	private SolrServer solrServerCombined;*/
	
	@Autowired
	private DispensaryService service;
	
	@RequestMapping(value = "/medicines", method = RequestMethod.GET)
	public @ResponseBody ResultMeta searchRegularForMedicines(@RequestParam(value="searchText", required=true) String searchText, @RequestParam(value="category", required = false) String categoryParam,
			@RequestParam(value="start", required = false) int start, @RequestParam(value="rows", required = false) int rows, 
			@RequestParam(value="lat", required = true) Double latitude, @RequestParam(value="long", required = true) Double longitude,
			@RequestParam(value="region", required = false) String region) throws SolrServerException, UnsupportedEncodingException{
		SolrDocumentList results = SolrHelper.doSearch(solrServerForMedicines, URLEncoder.encode(region, "UTF-8"), categoryParam, searchText, start, rows, MapPoint.newmapPoint(latitude, longitude), SearchType.DISPENSARY);
		long numFound = results.getNumFound();
		long startFromResult = results.getStart();
		ResultMeta result = new ResultMeta(numFound, startFromResult, rows);
		if(numFound>0){
			for(SolrDocument doc : results)
				result.getSearchResults().add(convertSolrDocumentToMenuItemSearchObject(doc));
		}
		return result;
	}
	
	@RequestMapping(value = "/dispensaries", method = RequestMethod.GET)
	public @ResponseBody ResultMeta searchRegularForDispensary(@RequestParam(value="searchText", required=true) String searchText, 
			@RequestParam(value="start", required = false) int start, @RequestParam(value="rows", required = false) int rows,
			@RequestParam(value="lat", required = true) Double latitude, @RequestParam(value="long", required = true) Double longitude,
			@RequestParam(value="region", required = false) String region) throws SolrServerException, UnsupportedEncodingException{
		SolrDocumentList results = SolrHelper.doSearch(solrServerForDispensary, URLEncoder.encode(region, "UTF-8"), null, searchText, start, rows, MapPoint.newmapPoint(latitude, longitude), SearchType.DISPENSARY);
		long numFound = results.getNumFound();
		long startFromResult = results.getStart();
		ResultMeta result = new ResultMeta(numFound, startFromResult, rows);
		if(numFound>0){
			for(SolrDocument doc : results)
				result.getSearchResults().add(convertSolrDocumentToDispensarySearchObject(doc, latitude, longitude));
		}
		return result;		
	}	
	


	@RequestMapping(value = "/popular", method = RequestMethod.GET)
	public @ResponseBody Collection<SearchQueryEntity> findPopularSearchTerms(@RequestParam(value = "recordNum", required = false) int recordNum){
		return service.findMostPopularSearchTerms(recordNum==0?10:recordNum);
	}
	
	@RequestMapping(value = "/dispensary/byId", method = RequestMethod.GET)
	public @ResponseBody DispensaryEntity getDispensaryDetails(@RequestParam(value="id") Long id) throws SolrServerException{
		return service.findDispensary(id);
	}
	@RequestMapping(value = "/medicines/byName", method = RequestMethod.GET)
	public @ResponseBody ResultMeta getDispensaryListForMedicine(@RequestParam(value="name") String medicineName, 
			@RequestParam(value="start", required = false) int start, @RequestParam(value="rows", required = false) int rows,
			@RequestParam(value="lat", required = true) Double latitude, @RequestParam(value="long", required = true) Double longitude,
			@RequestParam(value="region", required = false) String region) throws SolrServerException{
		List<String> findDispensariesForMedicine = service.findDispensariesForMedicine(medicineName);
		SolrDocumentList results = SolrHelper.doSearch(solrServerForDispensary, "id", region.replaceAll("\\s", "-").toLowerCase(), findDispensariesForMedicine, start, rows, MapPoint.newmapPoint(latitude, longitude));
		long numFound = results.getNumFound();
		long startFromResult = results.getStart();
		ResultMeta result = new ResultMeta(numFound, startFromResult, rows);
		if(numFound>0){
			for(SolrDocument doc : results)
				result.getSearchResults().add(convertSolrDocumentToDispensarySearchObject(doc, latitude, longitude));
		}
		return result;
	}	
	
	@RequestMapping(value = "/hit", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> increaseHitCount(@RequestParam(value="type") String type, @RequestParam(value="id") Long id){
		Map<String, String> retMap = Maps.newHashMap();
		EntityType searchType = EntityType.fromName(type);
		if(searchType == null){
			retMap.put("SUCCESS", "1");
			retMap.put("ERROR", "Search Type not found: Valid values are [dispensary, medicine, entertainment, Accessory, Flower, Concentrate, Edible]");
			return retMap;
		}
		switch(searchType){
			case DISPENSARY :
				increaseHitCountForEntity(service.findDispensary(id));
				retMap.put("SUCCESS", "0");
				break;
			case MEDICINE :
				increaseHitCountForEntity(service.findMenuItem(id));
				retMap.put("SUCCESS", "0");
				break;
			default :
				retMap.put("SUCCESS", "1");
				retMap.put("ERROR", searchType.name().concat(" -- Not supported yet."));
				break;
		}
		return retMap;
	}
	
	private void increaseHitCountForEntity(BaseEntity entity){
		entity.setHitCount(entity.getHitCount()!=null?new Integer(entity.getHitCount()+1):new Integer(1));
		service.updateEntity(entity);
	}
	
/*	@RequestMapping(value = "/suggest", method = RequestMethod.GET)
	public @ResponseBody Map<String, Collection<String>> getSuggestion(@RequestParam(value="searchText", required=true) String searchText){
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/suggest");
		query.setParam(CommonParams.Q, new String[]{searchText});
		QueryResponse response = null;
		try {
			response = solrServerCombined.query(query);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		Map<String, Collection<String>> resp = Maps.newHashMap();
		SpellCheckResponse spellCheckResponse = response.getSpellCheckResponse();
		for (Suggestion suggestion : spellCheckResponse.getSuggestions()) {
			String originalToken = suggestion.getToken();
			resp.put(originalToken, suggestion.getAlternatives());
		}
		return resp;
	}*/
	
	private static MenuItemSearchObject convertSolrDocumentToMenuItemSearchObject(SolrDocument doc){
		String id = doc.getFieldValue("id").toString();
		String name = doc.getFieldValue("name").toString();
		String priceEighth = doc.getFieldValue("priceEighth").toString();
		String priceQuarter = doc.getFieldValue("priceQuarter").toString();
		String priceHalfGram = doc.getFieldValue("priceHalfGram").toString();
		String priceGram = doc.getFieldValue("priceGram").toString();
		String priceHalfOunce = doc.getFieldValue("priceHalfOunce").toString();
		String priceOunce = doc.getFieldValue("priceOunce").toString();
		String priceUnit = doc.getFieldValue("priceUnit").toString();
		String pictureUrl = doc.getFieldValue("pictureUrl")!=null?doc.getFieldValue("pictureUrl").toString():"";
		String category = doc.getFieldValue("category").toString();
		String numberOfDispensary = doc.getFieldValue("numberOfDispensary").toString();
		return new MenuItemSearchObject(id, name, priceEighth, priceGram, priceHalfGram, priceHalfOunce, priceOunce, priceQuarter, priceUnit, pictureUrl, category, numberOfDispensary);
	}
	
	private static DispensarySearchObject convertSolrDocumentToDispensarySearchObject(SolrDocument doc, Double latitude, Double longitude) {
		String id = doc.getFieldValue("id").toString();
		String name = doc.getFieldValue("name").toString();
		String street = doc.getFieldValue("street").toString();
		String city = doc.getFieldValue("city").toString();
		String state = doc.getFieldValue("state").toString();
		String zip = doc.getFieldValue("zip").toString();
		
		String phone = doc.getFieldValue("phone").toString();
		String email = doc.getFieldValue("email").toString();
		String website = doc.getFieldValue("website").toString();
		String facebookURL = doc.getFieldValue("facebookURL").toString();
		String twitterURL = doc.getFieldValue("twitterURL").toString();
		String instagramURL = doc.getFieldValue("instagramURL").toString();
		
		Double latOfDispensary = Double.parseDouble(doc.getFieldValue("lat_coordinate").toString());
		Double longOfDispensary = Double.parseDouble(doc.getFieldValue("lang_coordinate").toString());
		
		
		String sundayOpen = doc.getFieldValue("sundayOpen").toString();
		String sundayClose = doc.getFieldValue("sundayClose").toString();
		String mondayOpen = doc.getFieldValue("mondayOpen").toString();
		String mondayClose = doc.getFieldValue("mondayClose").toString();
		String tuesdayOpen = doc.getFieldValue("tuesdayOpen").toString();
		String tuesdayClose = doc.getFieldValue("tuesdayClose").toString();
		String wednesdayOpen = doc.getFieldValue("wednesdayOpen").toString();
		String wednesdayClose = doc.getFieldValue("wednesdayClose").toString();
		String thursdayOpen = doc.getFieldValue("thursdayOpen").toString();
		String thursdayClose = doc.getFieldValue("thursdayClose").toString();
		String fridayOpen = doc.getFieldValue("fridayOpen").toString();
		String fridayClose = doc.getFieldValue("fridayClose").toString();
		String saturdayOpen = doc.getFieldValue("saturdayOpen").toString();
		String saturdayClose = doc.getFieldValue("saturdayClose").toString();
		String dispensaryURL = doc.getFieldValue("dispensaryURL").toString();
		String dispensaryImageURL = doc.getFieldValue("dispensaryImageURL").toString();
		return new DispensarySearchObject(id, name, street, city, state, zip, phone, email, website, facebookURL, twitterURL, instagramURL, 
				sundayOpen, sundayClose, mondayOpen, mondayClose, tuesdayOpen, tuesdayClose, wednesdayOpen, wednesdayClose, thursdayOpen, thursdayClose, fridayOpen, 
				fridayClose, saturdayOpen, saturdayClose, dispensaryURL, dispensaryImageURL,
				GeoCodingHelper.calculateDistanceBetweenTwoPoints(MapPoint.newmapPoint(latOfDispensary, longOfDispensary), MapPoint.newmapPoint(latitude, longitude), 'K'),
				GeoCodingHelper.calculateDistanceBetweenTwoPoints(MapPoint.newmapPoint(latOfDispensary, longOfDispensary), MapPoint.newmapPoint(latitude, longitude), 'M'));
	}


	public void setSolrServerForDispensary(SolrServer solrServerForDispensary) {
		this.solrServerForDispensary = solrServerForDispensary;
	}

	public void setSolrServerForMedicines(SolrServer solrServerForMedicines) {
		this.solrServerForMedicines = solrServerForMedicines;
	}

	public void setService(DispensaryService service) {
		this.service = service;
	}
	
}
