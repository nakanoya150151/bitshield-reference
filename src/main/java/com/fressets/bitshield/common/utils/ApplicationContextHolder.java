package com.fressets.bitshield.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHolder {

	private static ApplicationContext context;

	public static ApplicationContext get() {
		return context;
	}

	@Autowired
	public void setApplicationContext(ApplicationContext ac) {
		context = ac;
	}
}
