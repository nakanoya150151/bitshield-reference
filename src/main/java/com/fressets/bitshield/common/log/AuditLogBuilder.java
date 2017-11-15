package com.fressets.bitshield.common.log;

import org.springframework.beans.factory.annotation.Value;

import com.fressets.bitshield.common.exception.ApplicationException;
import com.fressets.bitshield.common.utils.Timestamper;

/**
 * AuditLogのBuilder基底クラス。
 * 各AuditLogクラスの共通項目を設定。
 *
 * @author koike0909
 */
//@ConfigurationProperties(prefix = "spring.application.name")
//@Setter
public abstract class AuditLogBuilder {

	@Value("${spring.application.name}")
	private String applicationName;

	private ApplicationException exception;

	private Object requestBody;

	private Object detail;

	public AuditLog build(String code) {
		AuditLog log = newInstance();
		log.setEventDateTime(Timestamper.currentDateTime());
		log.setIsSuccess(this.exception == null);
		log.setModuleName(applicationName);
		applyRequestBody(log, this.requestBody);
		// and so on..
		return log;
	}

	protected abstract AuditLog newInstance();

	protected abstract AuditLog applyRequestBody(AuditLog log, Object requestBody);

	public AuditLogBuilder exception(ApplicationException exception) {
		this.exception = exception;
		return this;
	}

	public AuditLogBuilder detail(Object detail) {
		this.detail = detail;
		return this;
	}

	public AuditLogBuilder requestBody(Object requestBody) {
		this.requestBody = requestBody;
		return this;
	}
}
