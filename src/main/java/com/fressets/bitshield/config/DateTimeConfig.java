package com.fressets.bitshield.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fressets.bitshield.common.utils.SystemTimestampProvider;
import com.fressets.bitshield.common.utils.TimestampProvider;

@Configuration
public class DateTimeConfig {

	@Bean
	public TimestampProvider timestampProvider() {
		return new SystemTimestampProvider();
	}
}
