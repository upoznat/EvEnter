package com.eventer.eventticket.exception;

public class EventTicketProcessingException extends ApplicationException {

	private static final long serialVersionUID = 1841959962200193050L;

	public EventTicketProcessingException() {
		super();
	}
	
	public EventTicketProcessingException(Throwable cause) {
		super(cause);
	}
	
	public EventTicketProcessingException(ErrorType errorType) {
		super(errorType);
	}

	
}
