package main.java.com.excilys.cdb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceException.class);

	public ServiceException() {
		super();
	}

	public ServiceException(String msg) {
		super(msg);
	}
	
	public void wrongID() {
		LOGGER.info("The ID entered is not in the database");
	}
	
	public void wrongPageNumber() {
		LOGGER.info("The page number entered is not good");
	}
}
