package com.talend.rb;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

import com.talend.rb.cli.ExecutionMode;
import com.talend.rb.cli.SearchBounds;
import com.talend.rb.data.xml.FieldExtractor;
import com.talend.rb.data.xml.NistXmlData;

public class Main {

	public static void main(String[] args) {
		// retrieve --from and --to values from arguments 
		SearchBounds bounds = ExecutionMode.retrieveSearchParameters(args);
		
		Map<Character, Integer> summary = new HashMap<Character, Integer>();
		
		int timestamp = bounds.getFrom();
		// process in loop all records which fall into period between 'from' and 'to'  
		// for each (or next closest) record corresponding to timestamp 
		do {
			// get the whole record
			Document record = NistXmlData.fetchRecord(timestamp);
			
			// extract the value for 'outputValue' 
			String outputValue = FieldExtractor.extractFieldValue(record, "outputValue");
			
			// for each character in the 'outputValue'
			for (Character currChar : outputValue.toCharArray()) {
				// if there is a map entry for current character
				if (summary.containsKey(currChar)) {
					// get current count of occurrences 
					Integer currCount = summary.get(currChar);
					// put the count incremented by 1
					summary.put(currChar, ++currCount);
				} else
					// it is the first occurrence of the character  
					summary.put(currChar, 1);
			}
			
			// records are generated with the step of 60 seconds
			// add them to timestamp 
			timestamp += 60;
		// loop exits when timestamp exceeds the 'to' timestamp	
		} while (timestamp <= bounds.getTo());
		
		// print the summary of characters occurrences 
		summary.forEach((k,v)->System.out.println(k + "," + v));
	}

}
