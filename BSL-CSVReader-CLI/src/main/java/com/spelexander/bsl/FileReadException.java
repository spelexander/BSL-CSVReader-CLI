package com.spelexander.bsl;

import java.io.File;

/**
 * Exception returned when a parsing error occurs within a CSV files line.
 * This exception will cause failure of the process.
 * @author alexs
 *
 */
public class FileReadException extends Exception {

	/**
	 * Input file where exception occurred
	 * @return
	 */
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Line where the exception occurred
	 * @return
	 */
	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}

	private File file;
	private String message;
	private Integer line;

	public FileReadException(File file, String message, Integer line) {
		super(message);
		
		this.file = file;
		this.message = message;
		this.line = line;
	}
	
	public String getFileName() {
		return file == null ? "<File Missing>" : file.getName();
	}
}
