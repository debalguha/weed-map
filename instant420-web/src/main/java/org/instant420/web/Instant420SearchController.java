package org.instant420.web;

import java.util.Collection;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.apache.solr.common.params.CommonParams;
import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Maps;

@Controller
@RequestMapping("/search")
public class Instant420SearchController {
	@Autowired
	private SolrServer solrServer;
	@Autowired
	private DispensaryService service;
	
	@RequestMapping(value = "/suggest", method = RequestMethod.GET)
	public Map<>
	
	@RequestMapping(value = "/suggest", method = RequestMethod.GET)
	public Map<String, Collection<String>> getSuggestion(@RequestParam(value="searchText", required=true) String searchText){
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/suggest");
		query.setParam(CommonParams.Q, new String[]{searchText});
		QueryResponse response = null;
		try {
			response = solrServer.query(query);
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
	}
	public void setSolrServer(SolrServer solrServer) {
		this.solrServer = solrServer;
	}
	public void setService(DispensaryService service) {
		this.service = service;
	}
}
