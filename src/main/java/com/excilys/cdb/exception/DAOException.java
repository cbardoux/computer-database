package main.java.com.excilys.cdb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceException.class);

	public DAOException() {
		super();
	}

	public DAOException(String msg) {
		super(msg);
	}

	public void wrongPageNumber() {
		LOGGER.info("The page number entered is not good");
	}

	public void companyListError() {
		LOGGER.info("No company list found");
	}
}
