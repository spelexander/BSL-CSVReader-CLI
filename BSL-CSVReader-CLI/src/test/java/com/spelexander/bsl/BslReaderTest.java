package com.spelexander.bsl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;

import com.spelexander.bsl.model.BslEntry;
import com.spelexander.bsl.model.FileReadException;
import com.spelexander.bsl.util.ConsoleLogger;

public class BslReaderTest {

	@Test 
	public void reportsMissingColumns() {
		int returnLength = 3;
		String inputName = "test_input_error.csv";

		File input;
		try {
			input = new File(BslReaderTest.class.getResource(inputName).toURI());

			BslCsvSorter sorter = new BslCsvSorter(1, false, false, false);
			sorter.readCsv(input, returnLength);		
		} catch (URISyntaxException | FileReadException | IOException e) {
			// Should not pass here as column is missing from file
			assert e instanceof FileReadException;			
		}
	}
	
	@Test 
	public void reportsInvalidDataFormat() {
		int returnLength = 3;
		String inputName = "test_input_error_2.csv";

		File input;
		try {
			input = new File(BslReaderTest.class.getResource(inputName).toURI());

			BslCsvSorter sorter = new BslCsvSorter(1, false, false, false);
			sorter.readCsv(input, returnLength);		
		} catch (URISyntaxException | FileReadException | IOException e) {
			// Should not pass here as column is missing from file
			assert e instanceof FileReadException;			
		}
	}
	
	@Test
	public void sortsCorrectlyTest() {
		int returnLength = 3;
		String inputName = "test_input.csv";

		File input;
		try {
			input = new File(BslReaderTest.class.getResource(inputName).toURI());

			BslCsvSorter sorter = new BslCsvSorter(1, false, false, false);
			List<BslEntry> entries = sorter.readCsv(input, returnLength);
			
			// Was the output correct?
			assert entries.size() == 3;
			
			// Was the output in the correct order?
			assert entries.get(0).firstname.equals("Zelma");
			assert entries.get(1).firstname.equals("Terza");
			assert entries.get(2).firstname.equals("Zedekiah");
			
		} catch (URISyntaxException | FileReadException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void outputRecordsCorrectLength() {

		int returnLength = 4;
		String inputName = "test_input.csv";

		File input;
		try {
			input = new File(BslReaderTest.class.getResource(inputName).toURI());

			BslCsvSorter sorter = new BslCsvSorter(1, false, false, false);
			List<BslEntry> entries = sorter.readCsv(input, returnLength);
			
			// Was the output correct?
			assert entries.size() == 4;
			
		} catch (URISyntaxException | FileReadException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void yamlTranslationTest() {

		int returnLength = 3;
		String inputName = "test_input.csv";

		File input;
		try {
			input = new File(BslReaderTest.class.getResource(inputName).toURI());

			BslCsvSorter sorter = new BslCsvSorter(1, false, false, false);
			List<BslEntry> entries = sorter.readCsv(input, returnLength);
			
			// Was the output correct?
			assert entries.size() == 3;
			
			String yaml = ConsoleLogger.getEntriesAsYaml(entries);
			
			// Check output is not null
			assert yaml != null && ! yaml.isEmpty();
			
			// Check output is the correct format length (TODO - Should have used a regex here)
			String[] lines = yaml.split("\n");
			assert lines.length == 7;

			
		} catch (URISyntaxException | FileReadException | IOException e) {
			e.printStackTrace();
		}
	}

}
