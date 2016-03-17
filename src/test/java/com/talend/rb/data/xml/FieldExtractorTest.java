package com.talend.rb.data.xml;

import static org.junit.Assert.*;

import org.junit.Test;
import org.w3c.dom.Document;

public class FieldExtractorTest {

	@Test
	public void testExtractFieldValue() {
		// given
		StringBuilder xmlString = new StringBuilder("<?xml version=\"1.0\"?><record><outputValue>test</outputValue></record>");
		Document document = NistXmlData.getInstance().constructDocument(xmlString);
		
		// when
		String actual = FieldExtractor.extractFieldValue(document, "outputValue");
		
		// then
		assertEquals("test", actual);
	}
	
	@Test(expected=NullPointerException.class)
	public void testExtractFieldValueFailure() {
		// given
		StringBuilder xmlString = new StringBuilder("<?xml version=\"1.0\"?><record><outputValue>test</outputValue></record>");
		Document document = NistXmlData.getInstance().constructDocument(xmlString);
		
		// when
		FieldExtractor.extractFieldValue(document, "previousValue");
		
		// then
		// exception thrown
	}

}
