package org.cisco.spadeportal.exceptions;

public class SystemException extends BaseAPIException {

	private static final long serialVersionUID = 1L;

	public SystemException() {
		super();
	}
	
	public SystemException(String errorCode) {
		super(errorCode);
	}
	
	public SystemException(String errorCode, Throwable cause ) {
		super(errorCode, cause);
	}

}
