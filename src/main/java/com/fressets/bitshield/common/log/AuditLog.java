package com.fressets.bitshield.common.log;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class AuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String requestId;

	private LocalDateTime eventDateTime;

	private String sourceIpAddress;

	private Boolean isSuccess;

	private String operationName;

	private String moduleName;
}
