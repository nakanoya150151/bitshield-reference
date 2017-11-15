package com.fressets.bitshield.common.log;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fressets.bitshield.common.exception.ApplicationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuditLogger {

	private static List<AuditLogDelegate> delegates;

	private static ApplicationContext context;

	@Autowired
	public void setApplicationContext(ApplicationContext ac) {
		AuditLogger.delegates = ac.getBeansOfType(AuditLogDelegate.class).values().stream().collect(Collectors.toList());
		AuditLogger.context = ac;
	}


	public static void log(String code, Object detail, Object requestBody) {
		log(code, requestBody, detail, null);
	}

	public static void log(String code, Object requestBody) {
		log(code, requestBody, null, null);
	}

	public static void log(String code) {
		log(code, null, null, null);
	}

	private static void log(String code, Object requestBody, Object detail, ApplicationException exception) {
		try {
			AuditLog auditLog = auditLogBuilder()
					.exception(exception)
					.detail(detail)
					.requestBody(requestBody)
					.build(code);
			if (!CollectionUtils.isEmpty(delegates)) {
				delegates.forEach(delegate -> delegate.log(auditLog));
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
	}

	private static AuditLogBuilder auditLogBuilder() {
		return context != null ? context.getBean(AuditLogBuilder.class) : new DefaultAuditLogBuilder();
	}
}
