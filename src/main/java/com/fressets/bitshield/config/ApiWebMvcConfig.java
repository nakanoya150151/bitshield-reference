package com.fressets.bitshield.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fressets.bitshield.common.log.AuditLogBuilder;
import com.fressets.bitshield.common.log.CustomerAuditLogBuilder;

@Configuration
public class ApiWebMvcConfig implements WebMvcConfigurer {

	@Bean
	@Scope("prototype")
	public AuditLogBuilder auditLogBuilder() {
		return new CustomerAuditLogBuilder();
	}
}
