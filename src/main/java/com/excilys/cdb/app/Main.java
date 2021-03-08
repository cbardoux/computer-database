package main.java.com.excilys.cdb.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.com.excilys.cdb.view.View;

public class Main {
	//private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		//logger.info("Example log from {}", "Hello");
		View view = new View();
		view.launch();
	}
}