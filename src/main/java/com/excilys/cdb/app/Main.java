package main.java.com.excilys.cdb.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import main.java.com.excilys.cdb.SpringConfig;
import main.java.com.excilys.cdb.view.View;

@Component
public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		View view = context.getBean(View.class);

		view.launch();
	}
}