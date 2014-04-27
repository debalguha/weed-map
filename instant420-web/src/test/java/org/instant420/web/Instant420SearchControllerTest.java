package org.instant420.web;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.instant420.web.domain.PopularSearchTermObject;
import org.instant420.web.domain.ResultMeta;
import org.instant420.web.httpclient.PreemptiveHttpClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Instant420SearchControllerTest {

	@Before
	public void setUp() throws Exception {
	}

	/*@Test
	@Ignore
	public void shouldBeAbleToProvideSuggestionFromCombinedIndex() {
		Instant420SearchController controller = new Instant420SearchController();
		controller.setSolrServerCombined(new HttpSolrServer("http://localhost:8080/apache-solr-4.0.0/collection1", new PreemptiveHttpClient("tomcat", "s3cret", 1000)));
		Map<String, Collection<String>> suggestion = controller.getSuggestion("dic");
		assertNotNull(suggestion);
		System.out.println(suggestion);
		
	}*/
	
	@Test
	public void shouldBeAbleToSearchForDispensaries() throws JsonGenerationException, JsonMappingException, IOException, SolrServerException{
		Instant420SearchController controller = new Instant420SearchController();
		HttpClient httpClient = new PreemptiveHttpClient("tomcat", "s3cret", 1000);
		controller.setSolrServerForDispensary(new HttpSolrServer("http://localhost:8080/solr/dispensary", httpClient));
		ResultMeta result = controller.searchRegularForDispensary("me", 51, 100);
		assertNotNull(result);
		assertTrue(result.getSearchResults().size()==100);
		System.out.println(new ObjectMapper().writeValueAsString(result));
	}
	
	@Test
	public void shouldBeAbleToSearchForMedicines() throws JsonGenerationException, JsonMappingException, IOException, SolrServerException{
		Instant420SearchController controller = new Instant420SearchController();
		HttpClient httpClient = new PreemptiveHttpClient("tomcat", "s3cret", 1000);
		controller.setSolrServerForMedicines(new HttpSolrServer("http://localhost:8080/solr/medicine", httpClient));
		ResultMeta result = controller.searchRegularForMedicines("kush", null, 0, 10);
		assertNotNull(result);
		assertTrue(result.getSearchResults().size()==10);
		System.out.println(new ObjectMapper().writeValueAsString(result));
	}
	
	@Test
	public void shouldBeAbleToGetPopularSearchTerms() throws JsonGenerationException, JsonMappingException, IOException{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		DispensaryService service = (DispensaryService)ctx.getBean(DispensaryService.class);
		Instant420SearchController controller = new Instant420SearchController();
		controller.setService(service);
		Collection<PopularSearchTermObject> popularSearchTerms = controller.findPopularSearchTerms(10);
		Assert.assertNotNull(popularSearchTerms);
		Assert.assertTrue(!popularSearchTerms.isEmpty());
		System.out.println(new ObjectMapper().writeValueAsString(popularSearchTerms));
		ctx.close();
	}

}
