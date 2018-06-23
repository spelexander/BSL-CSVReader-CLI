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
	public Long getLine() {
		return line;
	}

	public void setLine(Long line) {
		this.line = line;
	}

	private File file;
	private String message;
	private Long line;

	public FileReadException(File file, String message, Long line) {
		super(message);
		
		this.file = file;
		this.message = message;
		this.line = line;
	}
	
	public String getFileName() {
		return file == null ? "<File Missing>" : file.getName();
	}
}
