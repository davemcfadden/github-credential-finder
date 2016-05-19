package org.credential.finder;

import org.credential.finder.api.FinderInializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
		public static void main(String [] args){
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
			context.getBean(FinderInializer.class).run();
			((ConfigurableApplicationContext)context).close();
		}
}
