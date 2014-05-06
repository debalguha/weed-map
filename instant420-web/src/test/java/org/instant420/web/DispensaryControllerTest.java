package org.instant420.web;

import org.junit.Ignore;
import org.junit.Test;
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryEntity;
import org.progressivelifestyle.weedmaps.processor.WeedmapProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;


public class DispensaryControllerTest extends BaseTestCase {
	
	@Test
	@Ignore
	public void shouldBeAbleToCreateDispensaryFromJsonWithNoMenuItems() throws Exception{
		String dispensaryJson = WeedmapProcessor.readFile("dispensary-without-menuitems.json");
		DispensaryEntity entity = new ObjectMapper().readValue(dispensaryJson, DispensaryEntity.class);
		DispensaryController controller = childCtx.getBean(DispensaryController.class);
		System.out.println(controller.createDispensary(entity));
	}
	
	@Test
	public void shouldBeAbleToCreateDispensaryFromJsonWithMenuItems() throws Exception{
		String dispensaryJson = WeedmapProcessor.readFile("dispensary-with-menuitems.json");
		DispensaryEntity entity = new ObjectMapper().readValue(dispensaryJson, DispensaryEntity.class);
		DispensaryController controller = childCtx.getBean(DispensaryController.class);
		System.out.println(controller.createDispensary(entity));
	}
}
