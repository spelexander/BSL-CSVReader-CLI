package com.spelexander.bsl.model;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

// Static import of sort methods
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparingInt;

/**
 * Entry class representing each row of input CSV (and used for YAML output).
 * @author alexs
 *
 */
public class BslEntry implements Comparable<BslEntry> {
	
	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public Date getDate() {
		return date;
	}

	public Integer getDivision() {
		return division;
	}

	public Integer getPoints() {
		return points;
	}

	public String getSummary() {
		return summary;
	}

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
			field.setAccessible(true);
			
			if (field.isAccessible() && Modifier.isPublic(field.getModifiers())) {
				Integer index = headingIndex.get(field.getName());
				
				if (index != null) {
					// Set the value from the input line
					try {
						if (field.getType().isAssignableFrom(Date.class)) {
							// Date field
							field.set(this, sdf.parse(parts[index]));
							
						} else if (field.getType().isAssignableFrom(Integer.class)) {
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
	
	/**
	 * Compares first division ascending then points descending.
	 */
	@Override
	public int compareTo(BslEntry entry){	
	    return Comparator.comparingInt(BslEntry::getDivision)
	              .thenComparing(reverseOrder(comparingInt(BslEntry::getPoints)))
	              .compare(this, entry);
	}

	public String getDateAsString() {
		return sdf.format(date);
	}
	
}
