package main.java.com.excilys.cdb;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Configuration
@ComponentScan({ "main.java.com.excilys.cdb.dao", "main.java.com.excilys.cdb.service",
		"main.java.com.excilys.cdb.servlets", "main.java.com.excilys.cdb.view",
		"main.java.com.excilys.cdb.validator", "main.java.com.excilys.cdb.controller",
		"main.java.com.excilys.cdb.dto"})
public class SpringConfig extends AbstractContextLoaderInitializer {

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(SpringConfig.class);
		return context;
	}
	
	

}
