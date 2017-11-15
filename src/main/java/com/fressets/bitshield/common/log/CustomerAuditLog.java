package com.fressets.bitshield.common.log;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CustomerAuditLog extends AuditLog implements Serializable {

	private String loginId;

	private String requestBody;
}
