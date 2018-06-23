package com.spelexander.bsl;

import java.io.File;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Entry class representing each row of input CSV (and used for YAML output).
 * @author alexs
 *
 */
public class BslEntry {
	
	public String firstname;
	
	public String lastname;
	
	public Date date;
	
	public Integer division;
	
	public Integer points;
	
	public String summary;
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
	
	/**
	 * Constructor
	 * @param headingIndex
	 * @param line
	 * @throws FileReadException 
	 */
	public BslEntry(File file, Map<String,Integer> headingIndex, String line, Long lineNum) 
			throws FileReadException 
	{
		
		String[] parts = line.split(",");
		
		Field[] fields = this.getClass().getFields();
		for (Field field : fields) {
			// Only using public fields for csv objects
			if (field.isAccessible()) {
				Integer index = headingIndex.get(field.getName());
				
				if (index != null) {
					// Set the value from the input line
					try {
						if (field.getType().isAssignableFrom(Date.class)) {
							// Date field
							field.set(this, sdf.parse(parts[index]));
							
						} else if (field.getType().isAssignableFrom(Date.class)) {
							// Integer field
							field.set(this, Integer.parseInt(parts[index]));
							
						} else {
							// String field
							field.set(this, parts[index]);
							
						}
					} catch (IllegalArgumentException | IllegalAccessException | ParseException e) {
						throw new FileReadException(file, 
								"Value in '" + field.getName() + "' column from input file could not be parsed.", lineNum);
					} 
				} else {
					throw new FileReadException(file, 
							"Missing '" + field.getName() + "' column from input file.", lineNum);
				}
			}
		}	
	}
	
}
