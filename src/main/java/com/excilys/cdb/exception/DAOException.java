package main.java.com.excilys.cdb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ServiceException.class);

	public DAOException() {
		super();
	}

	public DAOException(String msg) {
		super(msg);
	}
	
	public void WrongPageNumber() {
		logger.info("The page number entered is not good");
	}
}
