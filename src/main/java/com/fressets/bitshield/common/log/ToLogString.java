package com.fressets.bitshield.common.log;

/**
 * Interface for returning object's log output string.
 *
 * Since it is necessary to mask specific items (personal infomation etc.) at log output,
 * Implement this when you use an object that contains masked items for audit log.
 *
 * @author koike0909
 */
public interface ToLogString {

	default String toLogString() {
		return toString();
	}
}
