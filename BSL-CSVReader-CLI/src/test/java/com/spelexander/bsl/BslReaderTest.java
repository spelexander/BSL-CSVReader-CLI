package com.spelexander.bsl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;

import com.spelexander.bsl.model.BslEntry;
import com.spelexander.bsl.model.FileReadException;

public class BslReaderTest {


	@Test
	public void sortedCorrectlyTest() {

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

}
