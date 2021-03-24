package com.excilys.cdb.app;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.console.config.ConsoleSpringConfig;
import com.excilys.cdb.view.View;

public class Main {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsoleSpringConfig.class);
		View view = context.getBean(View.class);
		view.launch();
		((ConfigurableApplicationContext) context).close();
	}
}