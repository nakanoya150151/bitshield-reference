package com.fressets.bitshield.common.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ApplicationException extends RuntimeException {

	private ErrorInfo errorInfo;
	
	private HttpErrors error;
	private Throwable cause;
	private Object[] args;

	public ApplicationException(HttpErrors error) {
		super(error.getMessage());
		this.error = error;
//		this.cause =
//		this.args =
	}
}
