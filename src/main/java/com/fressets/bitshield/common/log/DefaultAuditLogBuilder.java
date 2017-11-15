package com.fressets.bitshield.common.log;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultAuditLogBuilder extends AuditLogBuilder {

	public DefaultAuditLogBuilder() {
		log.info("default log mode.");
	}

	@Override
	protected AuditLog newInstance() {
		return new DefaultAuditLog();
	}

	@Override
	protected AuditLog applyRequestBody(AuditLog log, Object requestBody) {
		return log;
	}
}
