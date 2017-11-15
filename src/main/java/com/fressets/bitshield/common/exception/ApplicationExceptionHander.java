package com.fressets.bitshield.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHander extends ResponseEntityExceptionHandler {


	@ExceptionHandler
	public ResponseEntity<Object> handleException(ApplicationException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		return super.handleExceptionInternal(ex, ex.getError(), null, ex.getError().getStatus(), request);
	}
}
