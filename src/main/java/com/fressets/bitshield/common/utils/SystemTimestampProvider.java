package com.fressets.bitshield.common.utils;

import java.time.LocalDateTime;

/**
 * TimestampProvider implementation using system time.
 *
 * @author koike0909
 */
public class SystemTimestampProvider implements TimestampProvider {

	@Override
	public LocalDateTime currentDateTime() {
		return LocalDateTime.now();
	}

	@Override
	public long currentTimeMillis() {
		return System.currentTimeMillis();
	}
}
