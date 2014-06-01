package org.instant420.web;

import org.junit.Assert;
import org.junit.Test;
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryEntity;
import org.progressivelifestyle.weedmaps.processor.WeedmapProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;


public class DispensaryControllerTest extends BaseTestCase {
	
	@Test
	public void shouldBeAbleToCreateDispensaryFromJsonWithNoMenuItems() throws Exception{
		String dispensaryJson = WeedmapProcessor.readFile("dispensary-without-menuitems.json");
		DispensaryEntity entity = new ObjectMapper().readValue(dispensaryJson, DispensaryEntity.class);
		DispensaryController controller = childCtx.getBean(DispensaryController.class);
		APIResponse apiResponse = controller.createDispensary(entity);
		Assert.assertNull(apiResponse.getCause());
		Assert.assertNotNull(apiResponse.getId());
		Assert.assertTrue(apiResponse.getId() > 0);
		System.out.println(apiResponse);
	}
	
	@Test
	public void shouldBeAbleToCreateDispensaryFromJsonWithMenuItems() throws Exception{
		String dispensaryJson = WeedmapProcessor.readFile("dispensary-with-menuitems.json");
		DispensaryEntity entity = new ObjectMapper().readValue(dispensaryJson, DispensaryEntity.class);
		DispensaryController controller = childCtx.getBean(DispensaryController.class);
		APIResponse apiResponse = controller.createDispensary(entity);
		Assert.assertNull(apiResponse.getCause());
		Assert.assertNotNull(apiResponse.getId());
		Assert.assertTrue(apiResponse.getId() > 0);
		System.out.println(apiResponse);
	}
	
	@Test
	public void shouldBeAbleToUpdateDispensaryWithMenuItems() throws Exception{
		String dispensaryJson = WeedmapProcessor.readFile("update-dispensary-with-menuitems.json");
		DispensaryEntity entity = new ObjectMapper().readValue(dispensaryJson, DispensaryEntity.class);
		DispensaryController controller = childCtx.getBean(DispensaryController.class);
		APIResponse apiResponse = controller.updateDispensary(entity);
		Assert.assertNull(apiResponse.getCause());
		Assert.assertNotNull(apiResponse.getId());
		Assert.assertTrue(apiResponse.getId() > 0);
		System.out.println(apiResponse);		
	}
	
	@Test
	public void shouldBeAbleToUpdateDispensaryWithoutMenuItems() throws Exception{
		String dispensaryJson = WeedmapProcessor.readFile("update-dispensary-without-menuitems.json");
		DispensaryEntity entity = new ObjectMapper().readValue(dispensaryJson, DispensaryEntity.class);
		DispensaryController controller = childCtx.getBean(DispensaryController.class);
		entity.setMenuItems(null);
		entity.setPictures(null);
		APIResponse apiResponse = controller.updateDispensary(entity);
		Assert.assertNull(apiResponse.getCause());
		Assert.assertNotNull(apiResponse.getId());
		Assert.assertTrue(apiResponse.getId() > 0);
		System.out.println(apiResponse);	
	}	
	
	@Test
	public void shouldBeAbletoDeleteDispensary() throws Exception{
		DispensaryController controller = childCtx.getBean(DispensaryController.class);
		System.out.println(controller.deleteDispensary(31645L));
	}
}
