package com.fressets.bitshield.common.utils;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationContext;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The only class to retrieve the current date and time.
 *
 * @author koike0909
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Timestamper {

	public static LocalDateTime currentDateTime() {
		ApplicationContext ac = ApplicationContextHolder.get();
		return (ac== null) ? LocalDateTime.now() : ac.getBean(TimestampProvider.class).currentDateTime();
	}

	public static long currentTimeMillis() {
		ApplicationContext ac = ApplicationContextHolder.get();
		return (ac== null) ? System.currentTimeMillis() : ac.getBean(TimestampProvider.class).currentTimeMillis();
	}
}
