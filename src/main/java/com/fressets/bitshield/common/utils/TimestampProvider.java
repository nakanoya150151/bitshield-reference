package com.fressets.bitshield.common.utils;

import java.time.LocalDateTime;

public interface TimestampProvider {

	public LocalDateTime currentDateTime();

	public long currentTimeMillis();
}
