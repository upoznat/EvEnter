package com.eventer.eventticket.exception;

public class CustomerException extends ApplicationException {

    public CustomerException() {
        super();
    }

    public CustomerException(Throwable cause) {
        super(cause);
    }

    public CustomerException(ErrorType errorType) {
        super(errorType);
    }
}
