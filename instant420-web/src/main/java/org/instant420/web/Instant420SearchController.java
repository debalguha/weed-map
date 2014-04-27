package org.instant420.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;
import org.instant420.web.domain.DispensarySearchObject;
import org.instant420.web.domain.MenuItemSearchObject;
import org.instant420.web.domain.PopularSearchTermObject;
import org.instant420.web.domain.ResultMeta;
import org.instant420.web.domain.SearchType;
import org.progressivelifestyle.weedmap.persistence.domain.SearchQueryEntity;
import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
			@RequestParam(value="start", required = false) int start, @RequestParam(value="rows", required = false) int rows) throws SolrServerException{
		SolrDocumentList results = doSearch(solrServerForMedicines, searchText, categoryParam, start, rows, SearchType.MEDICINE);
		long numFound = results.getNumFound();
		long startFromResult = results.getStart();
		ResultMeta result = new ResultMeta(numFound, startFromResult, rows);
		if(numFound>0){
			for(SolrDocument doc : results){
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
				result.getSearchResults().add(new MenuItemSearchObject(id, name, priceEighth, priceGram, priceHalfGram, priceHalfOunce, priceOunce, priceQuarter, priceUnit, pictureUrl, category));
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/dispensaries", method = RequestMethod.GET)
	public @ResponseBody ResultMeta searchRegularForDispensary(@RequestParam(value="searchText", required=true) String searchText, 
			@RequestParam(value="start", required = false) int start, @RequestParam(value="rows", required = false) int rows) throws SolrServerException{
		SolrDocumentList results = doSearch(solrServerForDispensary, null, searchText, start, rows, SearchType.DISPENSARY);
		long numFound = results.getNumFound();
		long startFromResult = results.getStart();
		ResultMeta result = new ResultMeta(numFound, startFromResult, rows);
		if(numFound>0){
			for(SolrDocument doc : results){
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
				
				result.getSearchResults().add(new DispensarySearchObject(id, name, street, city, state, zip, phone, email, website, facebookURL, twitterURL, instagramURL, 
						sundayOpen, sundayClose, mondayOpen, mondayClose, tuesdayOpen, tuesdayClose, wednesdayOpen, wednesdayClose, thursdayOpen, thursdayClose, fridayOpen, 
						fridayClose, saturdayOpen, saturdayClose, dispensaryURL));
			}
		}
		return result;		
	}	
	
	private SolrDocumentList doSearch(SolrServer solrServer, String searchText, String category, int start, int rows, SearchType searchType) throws SolrServerException{
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/select");
		if(searchType.equals(SearchType.DISPENSARY))
			query.setParam(CommonParams.Q, new String[]{"name:".concat(searchText).concat("*")});
		else if(searchType.equals(SearchType.MEDICINE))
			query.setParam(CommonParams.Q, new String[]{"name:".concat(searchText).concat("*"), "category:".concat(category==null?"*":category.concat("*"))});
		query.setParam(CommonParams.START, String.valueOf(start));
		query.setParam(CommonParams.ROWS, String.valueOf(rows==0?10:rows));
		query.setParam(CommonParams.WT, "xml");
		query.setParam(CommonParams.START, String.valueOf(start));
		return solrServer.query(query).getResults();
		
	}
	
	@RequestMapping(value = "/popular", method = RequestMethod.GET)
	public @ResponseBody Collection<PopularSearchTermObject> findPopularSearchTerms(@RequestParam(value = "recordNum", required = false) int recordNum){
		List<SearchQueryEntity> mostPopularSearchTerms = service.findMostPopularSearchTerms(recordNum==0?10:recordNum);
		Collection<PopularSearchTermObject> terms = new ArrayList<PopularSearchTermObject>();
		for(SearchQueryEntity entity : mostPopularSearchTerms)
			terms.add(new PopularSearchTermObject(entity.getQueryStr(), String.valueOf(entity.getCount())));
		return terms;
	}
	
	@RequestMapping(value = "/GUID", method = RequestMethod.GET)
	public @ResponseBody String generateKey(){
		return UUID.randomUUID().toString();
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
