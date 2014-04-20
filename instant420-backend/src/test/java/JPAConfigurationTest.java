import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JPAConfigurationTest {
	@Test
	public void shouldBeAbleToStartSpringApplicationContextandInitializeJPA(){
		//Resource
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		Assert.assertNotNull(ctx);
	}
}
