package org.instant420.web.http;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.progressivelifestyle.weedmap.persistence.domain.SearchQueryEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class Instant420SearchControllerTestWithHttp extends GenericTest{

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void shouldBeAbleToSearchForDispensaries() throws JsonGenerationException, JsonMappingException, IOException, SolrServerException {
		RestTemplate template = new RestTemplate();
		String url = "http://"+getTargetHost()+":9080/instant420-web/rest/search/dispensaries?key=instant420.rest.api&searchText=the&start=0&rows=10&lat=34.036889&long=-118.255182&region=CA";
		@SuppressWarnings("unchecked")
		Map<String, Object> resultMeta = template.getForObject(url, Map.class);
		Assert.assertNotNull(resultMeta);
		Assert.assertTrue(Integer.parseInt(resultMeta.get("numFound").toString()) > 0);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldBeAbleToSearchForMedicines() throws JsonGenerationException, JsonMappingException, IOException, SolrServerException {
		RestTemplate template = new RestTemplate();
		String url = "http://"+getTargetHost()+":9080/instant420-web/rest/search/medicines?key=instant420.rest.api&searchText=dream&start=0&rows=10&lat=34.036889&long=-118.255182&region=CA";
		Map<String, Object> resultMeta = template.getForObject(url, Map.class);
		Assert.assertNotNull(resultMeta);
		Assert.assertTrue(Integer.parseInt(resultMeta.get("numFound").toString()) > 0);
		Assert.assertNotNull(((Collection<Map<String, Object>>)resultMeta.get("searchResults")).iterator().next().get("subCategoryName"));
	}

	@Test
	public void shouldBeAbleToGetDispensariesForMedicine() throws Exception {
		RestTemplate template = new RestTemplate();
		String url = "http://"+getTargetHost()+":9080/instant420-web/rest/search/medicines/byName?key=instant420.rest.api&name=Dream Queen&start=0&rows=10&lat=00.00&long=-00.00&region=West%20Hollywood";
		@SuppressWarnings("unchecked")
		Map<String, Object> resultMeta = template.getForObject(url, Map.class);
		Assert.assertNotNull(resultMeta);
		Assert.assertTrue(Integer.parseInt(resultMeta.get("numFound").toString()) > 0);
	}

	@Test
	public void shouldBeAbleToGetDispensaryDetails() throws JsonGenerationException, JsonMappingException, IOException {
		RestTemplate template = new RestTemplate();
		String url = "http://"+getTargetHost()+":9080/instant420-web/rest/search/dispensary/byId?key=instant420.rest.api&id=19231";
		@SuppressWarnings("unchecked")
		Map<String, Object> resultMeta = template.getForObject(url, Map.class);
		Assert.assertNotNull(resultMeta);
		Assert.assertEquals(19231L, Long.parseLong(resultMeta.get("id").toString()));
	}

	@SuppressWarnings({ "unchecked" })
	@Test
	public void shouldBeAbleToIncreaseHitCount() throws Exception {
		RestTemplate template = new RestTemplate();
		String url = "http://"+getTargetHost()+":9080/instant420-web/rest/search/hit?key=instant420.rest.api&id=19231&type=DISPENSARY";
		Map<String, String> retMap = template.getForObject(url, Map.class);
		Assert.assertEquals("0", retMap.get("SUCCESS"));
	}

	@Test
	public void shouldBeAbleToDoPopularSearchForDispensary() throws Exception {
		RestTemplate template = new RestTemplate();
		String url = "http://"+getTargetHost()+":9080/instant420-web/rest/search/popular/DISPENSARY?key=instant420.rest.api&start=0&rows=10";
		ArrayNode arrayNode = template.getForObject(url, ArrayNode.class);
		Assert.assertNotNull(arrayNode);
		Assert.assertEquals(10, arrayNode.size());
	}

	@Test
	public void shouldBeAbleToDoPopularSearchForMedicine() throws Exception {
		RestTemplate template = new RestTemplate();
		String url = "http://"+getTargetHost()+":9080/instant420-web/rest/search/popular/MEDICINE?key=instant420.rest.api&start=0&rows=10";
		ArrayNode arrayNode = template.getForObject(url, ArrayNode.class);
		Assert.assertNotNull(arrayNode);
		Assert.assertEquals(10, arrayNode.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldBeAbleToDoAdvancedSearch() throws Exception {
		RestTemplate template = new RestTemplate();
		String url = "http://"+getTargetHost()+":9080/instant420-web/rest/search/advance?key=instant420.rest.api&recordNum=10";
		List<SearchQueryEntity> result = template.getForObject(url, List.class);
		Assert.assertNotNull(result);
		Assert.assertEquals(10, result.size());
	}
}

class MyJsonMessageConverter extends MappingJackson2HttpMessageConverter {
	private Class<?> myClass;
	public MyJsonMessageConverter(Class<?> myClass){
		this.myClass = myClass;
	}
	@Override
	protected JavaType getJavaType(Type type, Class<?> contextClass) {
		if (contextClass!=null && List.class.isAssignableFrom(contextClass)) {
			final TypeFactory typeFactory = getObjectMapper().getTypeFactory();
			return typeFactory.constructCollectionType(ArrayList.class, this.myClass);
		}
		return super.getJavaType(type, contextClass);
	}
}