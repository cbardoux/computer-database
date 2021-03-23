package com.excilys.cdb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidatorException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorException.class);

	public ValidatorException() {
		super();
	}

	public ValidatorException(String msg) {
		super(msg);
	}

	public void wrongID() {
		LOGGER.info("The ID entered is not in the database");
	}

	public void wrongPageNumber() {
		LOGGER.info("The page number entered is not good");
	}

	public void noNameEntered() {
		LOGGER.info("You must enter a name");
	}
	
	public void introducedDateMustBeBeforeDiscontinued() {
		LOGGER.info("Enter valid dates");
	}

}
