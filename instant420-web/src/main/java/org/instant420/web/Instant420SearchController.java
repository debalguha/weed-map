package org.instant420.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.progressivelifestyle.weedmap.persistence.domain.MenuItemEntity;
import org.progressivelifestyle.weedmap.persistence.domain.SearchQueryEntity;
import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/search")
public class Instant420SearchController {
	private static final Log logger = LogFactory.getLog(Instant420SearchController.class);
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
	
	private ObjectMapper mapper  = new ObjectMapper();;
	
	@RequestMapping(value = "/popular/{type}", method = RequestMethod.GET)
	public @ResponseBody ArrayNode doPopularSearch(@RequestParam(value="start", required = false) int start, 
			@RequestParam(value="rows", required = false) int rows, @PathVariable SearchType type,
			@RequestParam(value="category", required = false) String categoryParam) throws SolrServerException{
		ArrayNode arrNode = JsonNodeFactory.instance.arrayNode();
		if(type.equals(SearchType.DISPENSARY) || type.equals(SearchType.ALL))
			populateArrayNodeFromDispensarySearchResult(arrNode, SolrHelper.simpleSearchWithSorting(solrServerForDispensary, "hitCount", start, rows, null, null, SearchType.DISPENSARY));
		if(type.equals(SearchType.MEDICINE) || type.equals(SearchType.ALL)){
			logger.info("Search Type Medicine: "+categoryParam);
			populateArrayNodeFromMedicineSearchResult(arrNode, SolrHelper.simpleSearchWithSorting(solrServerForMedicines, "hitCount", start, rows, null, categoryParam, SearchType.MEDICINE));
		}
		return arrNode;
	}
	
	public SearchType doesExistSearchTerm(String term) throws SolrServerException{
		SolrDocumentList searcheResult = SolrHelper.simpleSearchWithSorting(solrServerForDispensary, "hitCount", 0, 10, term, null, SearchType.DISPENSARY);
		if(searcheResult.getNumFound()>0)
			return SearchType.DISPENSARY;
		searcheResult = SolrHelper.simpleSearchWithSorting(solrServerForMedicines, "hitCount", 0, 10, term, null, SearchType.MEDICINE);
		if(searcheResult.getNumFound()>0)
			return SearchType.MEDICINE;
		return null;
	}
	
	@RequestMapping(value = "/medicines", method = RequestMethod.GET)
	public @ResponseBody ResultMeta searchRegularForMedicines(@RequestParam(value="searchText", required=true) String searchText, 
			@RequestParam(value="category", required = false) String categoryParam,
			@RequestParam(value="subCategory", required = false) String subCategoryParam,
			@RequestParam(value="start", required = false) int start, @RequestParam(value="rows", required = false) int rows, 
			@RequestParam(value="lat", required = true) Double latitude, @RequestParam(value="long", required = true) Double longitude,
			@RequestParam(value="region", required = false) String region) throws SolrServerException, UnsupportedEncodingException{
		
		SolrDocumentList results = SolrHelper.doSearch(solrServerForMedicines, URLEncoder.encode(region, "UTF-8"), searchText, categoryParam, subCategoryParam, start, rows, MapPoint.newmapPoint(latitude, longitude), SearchType.MEDICINE);
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
		SolrDocumentList results = SolrHelper.doSearch(solrServerForDispensary, URLEncoder.encode(region, "UTF-8"), searchText, null, null, start, rows, MapPoint.newmapPoint(latitude, longitude), SearchType.DISPENSARY);
		long numFound = results.getNumFound();
		long startFromResult = results.getStart();
		ResultMeta result = new ResultMeta(numFound, startFromResult, rows);
		if(numFound>0){
			for(SolrDocument doc : results)
				result.getSearchResults().add(convertSolrDocumentToDispensarySearchObject(doc, latitude, longitude));
		}
		return result;		
	}	
	


	@RequestMapping(value = "/advance", method = RequestMethod.GET)
	public @ResponseBody Collection<SearchQueryEntity> findAdvancedSearchTerms(@RequestParam(value = "recordNum", required = false) int recordNum,
			@RequestParam(value="searchText", required=true) String searchText){
		return service.findMostPopularSearchTerms(recordNum==0?10:recordNum, "%".concat(searchText).concat("%"));
	}
	
	@RequestMapping(value = "/dispensary/byId", method = RequestMethod.GET)
	public @ResponseBody DispensaryEntity getDispensaryDetails(@RequestParam(value="id") Long id) throws SolrServerException{
		return service.findDispensary(id);
	}
	@RequestMapping(value = "/medicines/byName", method = RequestMethod.GET)
	public @ResponseBody ResultMeta getDispensaryListForMedicine(@RequestParam(value="name") String medicineName, 
			@RequestParam(value="start", required = false) int start, @RequestParam(value="rows", required = false) int rows,
			@RequestParam(value="lat", required = true) Double latitude, @RequestParam(value="long", required = true) Double longitude,
			@RequestParam(value="region", required = false) String region) throws SolrServerException, UnsupportedEncodingException{
		Map<String, MenuItemEntity> dispensariesForMedicine = service.findDispensariesForMedicine(medicineName);
		logger.info("Dispensaries obtained for "+medicineName+":: "+dispensariesForMedicine);
		SolrDocumentList results = SolrHelper.doSearch(solrServerForDispensary, "id", URLEncoder.encode(region, "UTF-8"), dispensariesForMedicine.keySet(), start, rows, MapPoint.newmapPoint(latitude, longitude));
		long numFound = results.getNumFound();
		long startFromResult = results.getStart();
		ResultMeta result = new ResultMeta(numFound, startFromResult, rows);
		if(numFound>0){
			for(SolrDocument doc : results){
				DispensarySearchObject dispensarySearchObject = convertSolrDocumentToDispensarySearchObject(doc, latitude, longitude);
				dispensarySearchObject.getMedicines().add(convertmenuItemEntityToSearchObject(dispensariesForMedicine.get(dispensarySearchObject.getId())));
				dispensarySearchObject.setPictures(dispensariesForMedicine.get(dispensarySearchObject.getId()).getDispensary().getImages());
				result.getSearchResults().add(dispensarySearchObject);
			}
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
		Long id = Long.parseLong(doc.getFieldValue("id").toString());
		String name = doc.getFieldValue("name").toString();
		int priceEighth = Integer.parseInt(doc.getFieldValue("priceEighth").toString());
		int priceQuarter = Integer.parseInt(doc.getFieldValue("priceQuarter").toString());
		int priceHalfGram = Integer.parseInt(doc.getFieldValue("priceHalfGram").toString());
		int priceGram = Integer.parseInt(doc.getFieldValue("priceGram").toString());
		int priceHalfOunce = Integer.parseInt(doc.getFieldValue("priceHalfOunce").toString());
		int priceOunce = Integer.parseInt(doc.getFieldValue("priceOunce").toString());
		int priceUnit = Integer.parseInt(doc.getFieldValue("priceUnit").toString());
		String pictureUrl = doc.getFieldValue("pictureUrl")!=null?doc.getFieldValue("pictureUrl").toString():"";
		String category = doc.getFieldValue("category").toString();
		String subCategory = doc.getFieldValue("subCategoryName").toString();
		int numberOfDispensary = Integer.parseInt(doc.getFieldValue("numberOfDispensary").toString());
		
		Long strainId = Long.parseLong(doc.getFieldValue("strainId")!=null?doc.getFieldValue("strainId").toString():"0");
		String description = doc.getFieldValue("description")!=null?doc.getFieldValue("description").toString():"";
		return new MenuItemSearchObject(id, name, priceEighth, priceGram, priceHalfGram, priceHalfOunce, priceOunce, priceQuarter, priceUnit, pictureUrl, category, subCategory, numberOfDispensary, strainId, description);
	}
	
	private MenuItemSearchObject convertmenuItemEntityToSearchObject(MenuItemEntity menu) {
		return new MenuItemSearchObject(menu.getId(), menu.getName(), menu.getPriceEighth(), menu.getPriceGram(), menu.getPriceHalfGram(), menu.getPriceHalfOunce(), 
				menu.getPriceOunce(), menu.getPriceQuarter(), menu.getPriceUnit(), menu.getPictureURL(), menu.getCategoryName(), menu.getMenuItemCategory().getSubCategoryName(), 
				menu.getNumberOfDispensary(), Long.parseLong(menu.getStrainId()), menu.getDescription());
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
		String lat = doc.getFieldValue("lat_coordinate").toString();
		String lng = doc.getFieldValue("lang_coordinate").toString();
		return new DispensarySearchObject(id, name, street, city, state, zip, phone, email, website, facebookURL, twitterURL, instagramURL, 
				sundayOpen, sundayClose, mondayOpen, mondayClose, tuesdayOpen, tuesdayClose, wednesdayOpen, wednesdayClose, thursdayOpen, thursdayClose, fridayOpen, 
				fridayClose, saturdayOpen, saturdayClose, dispensaryURL, dispensaryImageURL,
				GeoCodingHelper.calculateDistanceBetweenTwoPoints(MapPoint.newmapPoint(latOfDispensary, longOfDispensary), MapPoint.newmapPoint(latitude, longitude), 'K'),
				GeoCodingHelper.calculateDistanceBetweenTwoPoints(MapPoint.newmapPoint(latOfDispensary, longOfDispensary), MapPoint.newmapPoint(latitude, longitude), 'M'), lat, lng);
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
	
	private void populateArrayNodeFromDispensarySearchResult(ArrayNode arrNode, SolrDocumentList results) {
		long numFound = results.getNumFound();
		if(numFound<=0)
			return;
		ObjectNode oNode = null;
		for(SolrDocument doc : results){
			oNode = mapper.createObjectNode();
			oNode.put("id", Integer.parseInt(doc.getFieldValue("id").toString()));
			oNode.put("name", doc.getFieldValue("name").toString());
			oNode.put("imageURL", doc.getFieldValue("dispensaryImageURL").toString());
			oNode.put("type", SearchType.DISPENSARY.name());
			arrNode.add(oNode);
		}
	}
	
	private void populateArrayNodeFromMedicineSearchResult(ArrayNode arrNode, SolrDocumentList results) {
		long numFound = results.getNumFound();
		if(numFound<=0)
			return;
		ObjectNode oNode = null;
		for(SolrDocument doc : results){
			oNode = mapper.createObjectNode();
			oNode.put("id", Integer.parseInt(doc.getFieldValue("id").toString()));
			oNode.put("name", doc.getFieldValue("name").toString());
			oNode.put("pictureUrl", doc.getFieldValue("pictureUrl")!=null?doc.getFieldValue("pictureUrl").toString():"");
			oNode.put("category", doc.getFieldValue("category")!=null?doc.getFieldValue("category").toString():"");
			oNode.put("type", SearchType.MEDICINE.name());
			arrNode.add(oNode);
		}
	}	
}
