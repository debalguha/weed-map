package org.instant420.web;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Map;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.junit.Before;
import org.junit.Test;

public class Instant420SearchControllerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Instant420SearchController controller = new Instant420SearchController();
		controller.setSolrServer(new HttpSolrServer("http://localhost:8080/apache-solr-4.0.0"));
		Map<String, Collection<String>> suggestion = controller.getSuggestion("dic");
		assertNotNull(suggestion);
		System.out.println(suggestion);
		
	}

}
