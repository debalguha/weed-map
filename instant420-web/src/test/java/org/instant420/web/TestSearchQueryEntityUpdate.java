package org.instant420.web;

import java.util.List;

import org.junit.Test;
import org.progressivelifestyle.weedmap.persistence.domain.SearchQueryEntity;
import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;

public class TestSearchQueryEntityUpdate extends BaseTestCase{

	@Test
	public void shouldBeAbleToUpdateTheTypeOfSearchQueryObject(){
		DispensaryService service = childCtx.getBean(DispensaryService.class);
		@SuppressWarnings("unchecked")
		List<SearchQueryEntity> findAll = (List<SearchQueryEntity>)service.findAll(SearchQueryEntity.class);
	}
	
}
