package main.java.com.excilys.cdb.app;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import main.java.com.excilys.cdb.SpringConfig;
import main.java.com.excilys.cdb.view.View;

public class Main {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		View view = context.getBean(View.class);
		view.launch();
		((ConfigurableApplicationContext) context).close();
	}
}