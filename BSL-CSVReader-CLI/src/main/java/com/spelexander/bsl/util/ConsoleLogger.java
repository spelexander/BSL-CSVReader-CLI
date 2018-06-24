package com.spelexander.bsl.util;

import java.util.List;
import java.util.StringJoiner;

import com.spelexander.bsl.model.BslEntry;

public class ConsoleLogger {

	// TODO - Add colours and key word.

	public static void error(String message) {
		System.out.println("ERROR: " + message);	
	}

	public static void warn(String message) {
		System.out.println("WARNING: " + message);
	}

	public static void info(String message) {
		System.out.println("INFO: " + message);
	}

	public static void print(String message) {
		System.out.println(message);
	}

	private static final String start = "|";
	private static final String end = "|";

	public static void updateProgress(Double progress) {
		Double progressBetter = (progress * 100);
		
		System.out.print("Working ... " + start + calculateFillChars(progress) + end + " " 
				+ (progress >= 1 ? "done!" : progressBetter.intValue()  + " %") + " - Memory Usage: " + getMemoryInMB() + "\r");
	}

	private static String calculateFillChars(double progress) {
		int barsNeeded = 20;
		Double bars = barsNeeded * progress;
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < barsNeeded; i++) {
			sb.append(i <= bars.intValue() ? "=" : " ");
		}

		return sb.toString();
	}

	private static String getMemoryInMB() {
		return (((double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024) / 1000) + " MB";
	}

	private final static String RECORDS_TAG = "records:";
	private final static String DETAILS_TAG = "  details: In division";
	private final static String NAME_TAG = "- name: ";
	private final static String FROM_TAG = " from ";
	private final static String PERFORMING_TAG = " performing ";

	public static String getEntriesAsYaml(List<BslEntry> entries) {
		StringJoiner sj = new StringJoiner("\n");

		sj.add(RECORDS_TAG);

		for (BslEntry entry : entries) {
			sj.add(NAME_TAG + entry.getFirstname() + " " + entry.lastname);
			sj.add(DETAILS_TAG + entry.getDivision() + FROM_TAG + entry.getDateAsString() + PERFORMING_TAG + entry.getSummary());
		}

		return sj.toString();
	}

}
