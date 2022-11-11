package cn.tuyucheng.taketoday.lazy;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class LazyAnnotationUnitTest {

	@Test
	void givenLazyAnnotation_whenConfigClass_thenLazyAll() {
		// Add @Lazy to AppConfig.class while testing
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();
		ctx.getBean(Region.class);
		ctx.getBean(Country.class);
	}

	@Test
	void givenLazyAnnotation_whenAutowire_thenLazyBean() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();
		Region region = ctx.getBean(Region.class);
		region.getCityInstance();
	}

	@Test
	void givenLazyAnnotation_whenBeanConfig_thenLazyBean() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();
		ctx.getBean(Region.class);
	}
}