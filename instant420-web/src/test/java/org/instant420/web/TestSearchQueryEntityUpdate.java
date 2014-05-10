package org.instant420.web;

import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.instant420.web.domain.SearchType;
import org.junit.Test;
import org.progressivelifestyle.weedmap.persistence.domain.EntityType;
import org.progressivelifestyle.weedmap.persistence.domain.SearchQueryEntity;
import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;

public class TestSearchQueryEntityUpdate extends BaseTestCase{

	@Test
	public void shouldBeAbleToUpdateTheTypeOfSearchQueryObject() throws SolrServerException{
		DispensaryService service = childCtx.getBean(DispensaryService.class);
		Instant420SearchController controller = childCtx.getBean(Instant420SearchController.class);
		@SuppressWarnings("unchecked")
		List<SearchQueryEntity> findAll = (List<SearchQueryEntity>)service.findAll(SearchQueryEntity.class);
		for(SearchQueryEntity entity : findAll){
			SearchType searchType = controller.doesExistSearchTerm(entity.getQueryStr());
			if(searchType == null)
				continue;
			switch(searchType){
				case DISPENSARY:
					entity.setType(EntityType.DISPENSARY);
					break;
				case MEDICINE:
					entity.setType(EntityType.MEDICINE);
					break;
				default:
					break;
			}
			if(entity.getType()!=null)
				service.updateEntity(entity);
		}
	}
	
}
