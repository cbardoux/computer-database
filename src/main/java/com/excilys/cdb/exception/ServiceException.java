package main.java.com.excilys.cdb.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(String msg) {
		super(msg);
	}
}
