import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryEntity;
import org.progressivelifestyle.weedmap.persistence.domain.SearchQueryEntity;
import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JPAConfigurationTest {
	@Test
	public void shouldBeAbleToStartSpringApplicationContextandInitializeJPA(){
		//Resource
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		Assert.assertNotNull(ctx);
		DispensaryService service = (DispensaryService)ctx.getBean(DispensaryService.class);
		List<DispensaryEntity> dispensaries = service.loadAllDispensaryForCache();
		Assert.assertEquals(1758, dispensaries.size());
		dispensaries = service.loadAllDispensaryForCache();
		Assert.assertEquals(1758, dispensaries.size());
		List<SearchQueryEntity> mostPopularSearchTerms = service.findMostPopularSearchTerms();
		Assert.assertFalse(mostPopularSearchTerms.isEmpty());
		System.out.println(mostPopularSearchTerms);
	}
	
}
