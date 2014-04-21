package org.instant420.web;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.instant420.web.domain.ResultMeta;
import org.junit.Before;
import org.junit.Test;

public class Instant420SearchControllerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void shouldBeAbleToProvideSuggestionFromCombinedIndex() {
		Instant420SearchController controller = new Instant420SearchController();
		controller.setSolrServerCombined(new HttpSolrServer("http://localhost:8080/apache-solr-4.0.0/collection1"));
		Map<String, Collection<String>> suggestion = controller.getSuggestion("dic");
		assertNotNull(suggestion);
		System.out.println(suggestion);
		
	}
	
	@Test
	public void shouldBeAbleToSearchForDispensaries() throws JsonGenerationException, JsonMappingException, IOException, SolrServerException{
		Instant420SearchController controller = new Instant420SearchController();
		controller.setSolrServerForDispensary(new HttpSolrServer("http://localhost:8080/apache-solr-4.0.0/dispensary"));
		ResultMeta result = controller.searchRegularForDispensary("me", 51, 100);
		assertNotNull(result);
		assertTrue(result.getSearchResults().size()==100);
		System.out.println(new ObjectMapper().writeValueAsString(result));
	}
	
	@Test
	public void shouldBeAbleToSearchForMedicines() throws JsonGenerationException, JsonMappingException, IOException, SolrServerException{
		Instant420SearchController controller = new Instant420SearchController();
		controller.setSolrServerForMedicines(new HttpSolrServer("http://localhost:8080/apache-solr-4.0.0/medicine"));
		ResultMeta result = controller.searchRegularForMedicines("kush", 0, 10);
		assertNotNull(result);
		assertTrue(result.getSearchResults().size()==10);
		System.out.println(new ObjectMapper().writeValueAsString(result));
	}

}
