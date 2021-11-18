package com.eventer.paymentservice.exception;

public class PaymentTransactionProcessingException extends ApplicationException{

	private static final long serialVersionUID = 1841959962200193050L;

	public PaymentTransactionProcessingException() {
		super();
	}
	
	public PaymentTransactionProcessingException(Throwable cause) {
		super(cause);
	}
	
	public PaymentTransactionProcessingException(ErrorType errorType) {
		super(errorType);
	}

	
}
