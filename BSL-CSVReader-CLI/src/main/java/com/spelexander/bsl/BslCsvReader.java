package com.spelexander.bsl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

public class BslCsvReader {

	private static final String PARAMETER_ERROR = "Command Arguments entered were not valid or malformed. See Usage:";
	
	private static final String PARSING_ERROR = "One or more input file provided had errors. See Errors:";
	
	private static final String FILE_ERROR = "One or more input files could not be used. See Errors:";
	
	private static final String EMAIL = "support@fakemail.com";
	private static final String UNCAUGHT_ERROR = "An unexplained error has occured please contact support at " + EMAIL;
	
	@Parameter
	private List<String> parameters = new ArrayList<>();

	@Parameter(names = { "-h", "-help" }, description = "Show usage/help information for tool")
	private boolean help = false;
	
	@Parameter(names = { "-log", "-verbose" }, description = "Level of verbosity")
	private Integer verbose = 1;
	
	@Parameter(names = { "-p", "-progress" }, description = "Show progress of reading")
	private Integer progress = 1;

	@Parameter(names = "-debug", hidden = true, description = "Debug mode")
	private boolean debug = false;

	@Parameter(names={"-length", "-l"}, description = "Top -l records to display when sorting (defaults to 3)")
	int length = 3;

	@Parameter(names = "-file", converter = FileConverter.class, description = "Input csv file containing entries to be sorted")
	File file;
	
	@Parameter(names = "-output", converter = FileConverter.class, description = "Destination file of sorted entries (defaults to printf)")
	File output;

	/*
	 * Converts input file argument into File object
	 */
	public class FileConverter implements IStringConverter<File> {
		@Override
		public File convert(String value) {
			return new File(value);
		}
	}
	
	public static void main(String ... argv) {
		try {
			BslCsvReader main = new BslCsvReader();
			JCommander.newBuilder()
			.addObject(main)
			.build()
			.parse(argv);
			main.run();
		} catch (Exception e) {
			// If we ever get here an actual error has been found in the code.
			e.printStackTrace();
		}
	}

	/**
	 * Although not main, for all intents using JCommander this can be thought of as main when our
	 * Previously defined Arg variables have been populated depending on String arguments.
	 */
	public void run() {
		// Sorting code runs here
		try {
			
			
			
			
		} catch (Exception e) {
			if (this.verbose > 1) {
				e.printStackTrace();
			}
			
			// We want to catch all exceptions gracefully
			if (e instanceof ParameterException) {
				// Parameter error.
				
			} else if (e instanceof FileReadException) {
				// Parsing Error.
				
			} else if (e instanceof IOException) {
				// File finding/reading/writing error.
				
			}
		}
	}
	
}
