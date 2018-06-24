package com.spelexander.bsl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.spelexander.bsl.model.BslEntry;
import com.spelexander.bsl.model.FileReadException;

public class BslCsvSorter {

	private final Integer verbose;
	
	private final boolean progress;
	
	private final boolean debug;
	
	private final boolean cache;

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

	/**
	 * Main sorting method. Compares each entry on the basis of division and then points. 
	 * Will remove cache (not storing all entries in memory) if requested.
	 * Outputs a response object with the <length> number of records sorted.
	 * @param file
	 * @param length
	 * @throws IOException 
	 */
	public void readCsv(File file, int length) throws FileReadException, IOException {

		Map<String,Integer> headingIndex = new HashMap<>();

		InputStream inputFS = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

		// Chose this instead of Java 8 for better exception throwing ability.
		String line;
		while ((line = br.readLine()) != null) {
			if (lineNumber == 0) {
				populateHeadings(line, headingIndex);
			} else {
				BslEntry entry = new BslEntry(file, headingIndex, line, lineNumber);
				
			}
			lineNumber++;
		}

		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				// Ignore this resource exception
			}
		}


	}

	/**
	 * Get our heading index for later parsing (We don't know what order they will be given to us as!)
	 * @param t
	 * @param headingIndex
	 */
	private void populateHeadings(String t, final Map<String, Integer> headingIndex) {
		String[] parts = t.split(",");
		int index = 0;

		for (String heading : parts) {
			headingIndex.put(heading, index);
			index++;
		}
	}



}
