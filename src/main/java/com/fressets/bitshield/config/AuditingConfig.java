package com.fressets.bitshield.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {

	@Bean
	public AuditorAware<String> createAuditorProvider() {
		return new SecurityAuditor();
	}

	public static class SecurityAuditor implements AuditorAware<String> {

		@Override
		public Optional<String> getCurrentAuditor() {
			return Optional.of("testUser");
		}
	}
}
