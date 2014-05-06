package org.instant420.web;

import org.instant420.web.domain.ResultMeta;
import org.instant420.web.domain.SearchType;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Instant420ControllerTestWithApplicationContext extends BaseTestCase{
	@Test
	@Ignore
	public void shouldBeAbleToSearchMedicines() throws Exception{
		Instant420SearchController controller =  (Instant420SearchController)childCtx.getBean(Instant420SearchController.class);
		ResultMeta searchResult = controller.searchRegularForMedicines("rump wax", null, 0, 10, 34.036889d, -118.255182d, "West Hollywood");
		System.out.println(new ObjectMapper().writeValueAsString(searchResult));
		Assert.assertNotNull(searchResult);
		Assert.assertFalse(searchResult.getSearchResults().isEmpty());
	}
	
	
	@Test
	@Ignore
	public void shouldBeAbleToSearchDispensaries() throws Exception{
		Instant420SearchController controller =  (Instant420SearchController)childCtx.getBean(Instant420SearchController.class);
		ResultMeta searchResult = controller.searchRegularForDispensary("me", 0, 10, 34.036889d, -118.255182d, "West Hollywood");
		System.out.println(new ObjectMapper().writeValueAsString(searchResult));
		Assert.assertNotNull(searchResult);
		Assert.assertFalse(searchResult.getSearchResults().isEmpty());
	}
	
	@Test
	@Ignore
	public void shouldBeAbleToSearchWithinRegion() throws Exception{
		Instant420SearchController controller =  (Instant420SearchController)childCtx.getBean(Instant420SearchController.class);
		ResultMeta searchResult = controller.getDispensaryListForMedicine("Blue Dream", 0, 10, 00.00, 00.00, "West Hollywood");
		System.out.println(new ObjectMapper().writeValueAsString(searchResult));
		Assert.assertNotNull(searchResult);
		Assert.assertFalse(searchResult.getSearchResults().isEmpty());
	}
	
	@Test
	public void shouldBeAbleToGetDispensaryDetail() throws Exception{
		Instant420SearchController controller =  (Instant420SearchController)childCtx.getBean(Instant420SearchController.class);
		DispensaryEntity dispensaryEntity = controller.getDispensaryDetails(new Long(31646));
		System.out.println(new ObjectMapper().writeValueAsString(dispensaryEntity));
		Assert.assertNotNull(dispensaryEntity);
		Assert.assertFalse(dispensaryEntity.getMenuItems().isEmpty());
	}
	
	@Test
	@Ignore
	public void shouldBeAbleToIncreaseHitCount() throws Exception{
		Instant420SearchController controller =  (Instant420SearchController)childCtx.getBean(Instant420SearchController.class);
		DispensaryEntity dispensary = controller.getDispensaryDetails(new Long(19231));
		int prevHitCount = dispensary.getHitCount()==null?0:dispensary.getHitCount();
		controller.increaseHitCount(SearchType.DISPENSARY.name(), new Long(19231));
		dispensary = controller.getDispensaryDetails(new Long(19231));
		Assert.assertEquals(prevHitCount+1, dispensary.getHitCount().intValue());
	}
}
