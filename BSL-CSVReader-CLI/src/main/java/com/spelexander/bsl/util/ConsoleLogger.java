package com.spelexander.bsl.util;

public class ConsoleLogger {

	// TODO - Add colours and key word.
	
	public static void error(String message) {
		System.out.println(message);	
	}

	public static void warn(String message) {
		System.out.println(message);
	}

	public static void info(String message) {
		System.out.println(message);
	}

	public static void print(String message) {
		System.out.println(message);
	}
	
	private static final String start = "|";
	private static final String end = "\r";
	
	public static void updateProgress(double progress, String memory) {
		System.out.print(start + String.valueOf(progress) + end);
	}
	
}
