package com.fressets.bitshield.common.exception;

import org.springframework.http.HttpStatus;

public interface HttpErrors {

	HttpStatus getStatus();

	String getMessage();

	String name();
}
