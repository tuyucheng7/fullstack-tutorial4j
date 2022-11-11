package cn.tuyucheng.taketoday.ioccontainer;

import cn.tuyucheng.taketoday.ioccontainer.bean.CustomBeanFactoryPostProcessor;
import cn.tuyucheng.taketoday.ioccontainer.bean.CustomBeanPostProcessor;
import cn.tuyucheng.taketoday.ioccontainer.bean.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IOCContainerAppUnitTest {

	@BeforeEach
	@AfterEach
	void resetInstantiationFlag() {
		Student.setBeanInstantiated(false);
		CustomBeanPostProcessor.setBeanPostProcessorRegistered(false);
		CustomBeanFactoryPostProcessor.setBeanFactoryPostProcessorRegistered(false);
	}

	@Test
	void whenBFInitialized_thenStudentNotInitialized() {
		Resource res = new ClassPathResource("ioc-container-difference-example.xml");
		BeanFactory factory = new XmlBeanFactory(res);
		assertFalse(Student.isBeanInstantiated());
	}

	@Test
	void whenBFInitialized_thenStudentInitialized() {
		Resource res = new ClassPathResource("ioc-container-difference-example.xml");
		BeanFactory factory = new XmlBeanFactory(res);
		Student student = (Student) factory.getBean("student");
		assertTrue(Student.isBeanInstantiated());
	}

	@Test
	void whenAppContInitialized_thenStudentInitialized() {
		ApplicationContext context = new ClassPathXmlApplicationContext("ioc-container-difference-example.xml");
		assertTrue(Student.isBeanInstantiated());
	}

	@Test
	void whenBFInitialized_thenBFPProcessorAndBPProcessorNotRegAutomatically() {
		Resource res = new ClassPathResource("ioc-container-difference-example.xml");
		ConfigurableListableBeanFactory factory = new XmlBeanFactory(res);
		assertFalse(CustomBeanFactoryPostProcessor.isBeanFactoryPostProcessorRegistered());
		assertFalse(CustomBeanPostProcessor.isBeanPostProcessorRegistered());
	}

	@Test
	void whenBFPostProcessorAndBPProcessorRegisteredManually_thenReturnTrue() {
		Resource res = new ClassPathResource("ioc-container-difference-example.xml");
		ConfigurableListableBeanFactory factory = new XmlBeanFactory(res);

		CustomBeanFactoryPostProcessor beanFactoryPostProcessor = new CustomBeanFactoryPostProcessor();
		beanFactoryPostProcessor.postProcessBeanFactory(factory);
		assertTrue(CustomBeanFactoryPostProcessor.isBeanFactoryPostProcessorRegistered());

		CustomBeanPostProcessor beanPostProcessor = new CustomBeanPostProcessor();
		factory.addBeanPostProcessor(beanPostProcessor);
		Student student = (Student) factory.getBean("student");
		assertTrue(CustomBeanPostProcessor.isBeanPostProcessorRegistered());
	}

	@Test
	void whenAppContInitialized_thenBFPostProcessorAndBPostProcessorRegisteredAutomatically() {
		ApplicationContext context = new ClassPathXmlApplicationContext("ioc-container-difference-example.xml");
		assertTrue(CustomBeanFactoryPostProcessor.isBeanFactoryPostProcessorRegistered());
		assertTrue(CustomBeanPostProcessor.isBeanPostProcessorRegistered());
	}
}