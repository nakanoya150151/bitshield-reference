package com.fressets.bitshield.common.log;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerAuditLogBuilder extends AuditLogBuilder {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	protected AuditLog applyRequestBody(AuditLog log, Object requestBody) {
		Optional.ofNullable(requestBody).ifPresent(body -> ((CustomerAuditLog) log).setRequestBody(requestBodyToString(requestBody)));
		return log;
	}

	@Override
	protected AuditLog newInstance() {
		return new CustomerAuditLog();
	}

	private String requestBodyToString(Object requestBody) {
		if (requestBody == null) {
			return null;
		}
		if (requestBody instanceof ToLogString) {
			return ((ToLogString) requestBody).toLogString();
		}
		if (requestBody instanceof String) {
			return (String) requestBody;
		}
		try {
			return this.objectMapper.writeValueAsString(requestBody);
		} catch (JsonProcessingException e) {
			return requestBody.toString();
		}
	}
}
