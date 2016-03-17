package com.talend.rb.data.xml;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;

@RunWith(MockitoJUnitRunner.class)
public class NistXmlDataTest {

	@Test
	public final void testObtainRecord() throws IOException {
		// given
		
		HttpURLConnection mockConnection = mock(HttpURLConnection.class);
		when(mockConnection.getResponseCode()).thenReturn(200);
		InputStream inputStream = mock(InputStream.class);
		when(mockConnection.getInputStream()).thenReturn(inputStream);
		
		// when
		StringBuilder actual = NistXmlData.getInstance().obtainRecord(mockConnection);
		
		// then
		assertNotNull(actual);
	}
	
	@Test(expected=RuntimeException.class)
	public final void testObtainRecordFail() throws IOException {
		// given
		HttpURLConnection mockConnection = mock(HttpURLConnection.class);
		when(mockConnection.getResponseCode()).thenReturn(404);
		
		// when
		NistXmlData.getInstance().obtainRecord(mockConnection);
		
		// then
		// exception thrown
	}
	
	@Test
	public void testConstructDocument() {
		// given
		StringBuilder xmlString = new StringBuilder("<?xml version=\"1.0\"?> <class> </class>");
		
		// when
		Document document = NistXmlData.getInstance().constructDocument(xmlString);
		
		// then
		assertNotNull(document);
	}

}
