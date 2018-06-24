package com.spelexander.bsl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spelexander.bsl.model.BslEntry;
import com.spelexander.bsl.model.FileReadException;
import com.spelexander.bsl.util.ConsoleLogger;

public class BslCsvSorter {

	private final Integer verbose;
	
	private final boolean progress;
	
	private final boolean debug;
	
	private final boolean cache;

	// Taken from averaged row sizes in bytes
	private static final Long lineSize = 48L;
	
	/**
	 * Constructor
	 * @param verbose
	 * @param progress
	 * @param debug
	 * @param dontCache
	 */
	public BslCsvSorter(Integer verbose, boolean progress, boolean debug, boolean cache) {
		this.verbose = verbose;
		this.progress = progress;
		this.debug = debug;
		this.cache = cache;
	}

	private Long lineNumber = 0L;
	
	private List<BslEntry> cachedEntries = new ArrayList<>();
	
	/**
	 * Main sorting method. Compares each entry on the basis of division and then points. 
	 * Will remove cache (not storing all entries in memory) if requested.
	 * Outputs a response object with the <length> number of records sorted.
	 * @param file
	 * @param length
	 * @return 
	 * @throws IOException 
	 */
	public List<BslEntry> readCsv(File file, int length) throws FileReadException, IOException {
		lineNumber = 0L;
		
		cachedEntries.clear();
		List<BslEntry> sortedEntries = new ArrayList<>();
		
		Map<String,Integer> headingIndex = new HashMap<>();

		InputStream inputFS = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

		// Get file length to make estimate for read time (don't want to call anything that will increase complexity such as lines)
		Long bytes = file.length();
		Long expectedLines = bytes / lineSize;
		
		// Chose this instead of Java 8 for better exception throwing ability.
		String line;
		while ((line = br.readLine()) != null) {
			
			if (lineNumber == 0) {
				populateHeadings(line, headingIndex);
			} else {
				BslEntry entry = new BslEntry(file, headingIndex, line, lineNumber);
				sortFunction(entry, sortedEntries, length);
				
				if (cache) {
					// For additional functionality that may be needed later - lookups etc.
					cachedEntries.add(entry);
				}
			}
			
			ConsoleLogger.updateProgress((Double) lineNumber.doubleValue() / expectedLines.doubleValue());
			lineNumber++;
		}

		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				// Ignore this resource exception
			}
		}

		return sortedEntries;
	}

	/**
	 * Checks whether it should add the entry to the list of return objects - on the basis of sort.
	 * @param entry
	 * @param sortedEntries
	 * @param length
	 */
	private void sortFunction(BslEntry entry, List<BslEntry> sortedEntries, int length) {
		sortedEntries.add(entry);
		Collections.sort(sortedEntries);

		// We want to restrict the number of entries we have to what we want to return
		if (sortedEntries.size() > length) {
			sortedEntries.remove(length);
		}
	}

	/**
	 * Get our heading index for later parsing (We don't know what order they will be given to us as!)
	 * @param headingLine
	 * @param headingIndex
	 */
	private void populateHeadings(String headingLine, final Map<String, Integer> headingIndex) {
		String[] parts = headingLine.split(",");
		int index = 0;

		for (String heading : parts) {
			headingIndex.put(heading, index);
			index++;
		}
	}



}
