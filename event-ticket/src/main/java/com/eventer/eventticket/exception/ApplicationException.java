package com.eventer.eventticket.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationException extends RuntimeException{

	private static final long serialVersionUID = -1324036379770825489L;

	private final ErrorType errorType;
	
	/**
	 * Constructs new exception.
	 */
	public ApplicationException() {
		super();
		
		this.errorType = null;
	}
	
	/**
	 * Constructs new exception.
	 * 
	 * @param cause - the cause of exception
	 */
	public ApplicationException(Throwable cause) {
		super(cause);
		
		this.errorType = null;
	}
	
	public ApplicationException(ErrorType errorType) {
		super(errorType.toString());
		
		this.errorType = errorType;
	}
	
	/**
	 * Constructs new exception.
	 * 
	 * @param cause - the cause of exception
	 * @param errorType - error type
	 */
	public ApplicationException(ErrorType errorType, Throwable cause) {
		super(errorType.toString(), cause);
		
		this.errorType = errorType;
	}

	public enum ErrorType {
		NO_USER_FOR_PARAMETERS("Ne postoji korisnik za prosledjene parametre");

		private ErrorType(String description) {
			this.description = description;

		}

		private final String description;
	}

}
