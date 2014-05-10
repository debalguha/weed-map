package org.instant420.web;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryEntity;
import org.progressivelifestyle.weedmaps.processor.WeedmapProcessor;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DispensaryControllertestWithHttp {
	@Test
	@Ignore
	public void shouldBeAbleToCreateDispensaryFromJsonWithoutMenuItems() throws Exception{
		String dispensaryJson = WeedmapProcessor.readFile("dispensary-without-menuitems.json");
		DispensaryEntity entity = new ObjectMapper().readValue(dispensaryJson, DispensaryEntity.class);
		RestTemplate template = new RestTemplate();
		String url = "http://localhost:9080/instant420-web/rest/operation/dispensary/create?key=instant420.rest.api";
		APIResponse response = template.postForObject(url, entity, APIResponse.class);
		Assert.assertNotNull(response.getId());
		Assert.assertNull(response.getCause());
	}	
	
	@Test
	@Ignore
	public void shouldBeAbleToCreateDispensaryFromJsonWithMenuItems() throws Exception{
		String dispensaryJson = WeedmapProcessor.readFile("dispensary-with-menuitems.json");
		DispensaryEntity entity = new ObjectMapper().readValue(dispensaryJson, DispensaryEntity.class);
		RestTemplate template = new RestTemplate();
		String url = "http://localhost:9080/instant420-web/rest/operation/dispensary/create?key=instant420.rest.api";
		APIResponse response = template.postForObject(url, entity, APIResponse.class);
		Assert.assertNotNull(response.getId());
		Assert.assertNull(response.getCause());
	}	
	
	@Test
	@Ignore
	public void shouldBeAbleToUpdateDispensaryWithMenuItems() throws Exception{
		String dispensaryJson = WeedmapProcessor.readFile("update-dispensary-with-menuitems.json");
		DispensaryEntity entity = new ObjectMapper().readValue(dispensaryJson, DispensaryEntity.class);
		RestTemplate template = new RestTemplate();
		String url = "http://localhost:9080/instant420-web/rest/operation/dispensary/update?key=instant420.rest.api";
		APIResponse response = template.postForObject(url, entity, APIResponse.class);
		Assert.assertNotNull(response.getId());
		Assert.assertNull(response.getCause());
	}	
	
	@Test
	
	public void shouldBeAbleToUpdateDispensaryWithoutMenuItems() throws Exception{
		String dispensaryJson = WeedmapProcessor.readFile("update-dispensary-without-menuitems.json");
		DispensaryEntity entity = new ObjectMapper().readValue(dispensaryJson, DispensaryEntity.class);
		RestTemplate template = new RestTemplate();
		String url = "http://localhost:9080/instant420-web/rest/operation/dispensary/update/less?key=instant420.rest.api";
		APIResponse response = template.postForObject(url, entity, APIResponse.class);
		Assert.assertNotNull(response.getId());
		Assert.assertNull(response.getCause());
	}		
	
	@Test
	@Ignore
	public void shouldBeAbletoDeleteDispensary() throws Exception{
		RestTemplate template = new RestTemplate();
		String url = "http://localhost:9080/instant420-web/rest/operation/dispensary/delete/31644";
		APIResponse response = template.getForObject(url, APIResponse.class);
		Assert.assertNotNull(response.getId());
		Assert.assertNull(response.getCause());
	}	
}
