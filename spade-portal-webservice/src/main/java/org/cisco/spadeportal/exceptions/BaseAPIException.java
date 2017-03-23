package org.cisco.spadeportal.exceptions;

import java.util.ResourceBundle;

public class BaseAPIException extends Exception {

	private static final long serialVersionUID = 1L;

	private static final ResourceBundle errMsgBundle = ResourceBundle.getBundle("ErrorMessage");
	
	public BaseAPIException() {
		// default constructor
	}

	public BaseAPIException(String errorCode) {
		super(errMsgBundle.getString(errorCode));
	}
	
	public BaseAPIException(String errorCode, Throwable cause) {
		super(errMsgBundle.getString(errorCode), cause);
	}

}
