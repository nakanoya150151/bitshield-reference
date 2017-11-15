package com.fressets.bitshield.common.log;

public enum CustomerAuditLogCode {

	ADDRESS_READ("auditlog.api.address.read"),

	ADDRESS_CREATE("auditlog.api.address.create"),

	ADDRESS_UPDATE("auditlog.api.address.update"),

	ADDRESS_DELETE("auditlog.api.address.delete");

	private String codePrefix;

	private CustomerAuditLogCode(String codePrefix) {
		this.codePrefix = codePrefix;
	}

	public String getCode(boolean success) {
		return buildCode(this.codePrefix, success);
	}

	private String buildCode(String prefix, boolean success) {
		return prefix + "." + (success ? "success" : "fail");
	}
}
