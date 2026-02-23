package com.example.demo.util;

public final class HeaderConstants {
	private HeaderConstants() {}
	
	public static final String EMPLOYEE_KEY = "X-Employee-Key";
	public static final String CORRELATION_ID ="X-Correlation-ID"; 
}

// Correlation_ID = A unique ID attached to request so that entire 
// lifecycle of request can be traced across logs, services and systems. 
