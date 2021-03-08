package main.java.com.excilys.cdb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidatorException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ValidatorException.class);

	public ValidatorException() {
		super();
	}

	public ValidatorException(String msg) {
		super(msg);
	}

	public void WrongID() {
		logger.info("The ID entered is not in the database");
	}

	public void WrongPageNumber() {
		logger.info("The page number entered is not good");
	}

	public void NoNameEntered() {
		logger.info("You must enter a name");
	}
	
	public void IntroducedDateMustBeBeforeDiscontinued() {
		logger.info("Enter valid dates");
	}

}
