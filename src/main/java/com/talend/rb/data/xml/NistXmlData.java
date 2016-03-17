package com.talend.rb.data.xml;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class NistXmlData {

	private static NistXmlData nistXmlData;

	private NistXmlData() {

	}

	public static NistXmlData getInstance() {
		if (nistXmlData == null)
			nistXmlData = new NistXmlData();
		return nistXmlData;
	}

	/**
	 * Makes REST API calls to fetch record for timestamp
	 * 
	 * @param timestamp
	 * @return Document containing the record data
	 */
	public Document fetchRecord(int timestamp) {
		HttpURLConnection connection = createConnection(timestamp);
		StringBuilder xmlString = obtainRecord(connection);
		return constructDocument(xmlString);
	}

	HttpURLConnection createConnection(int timestamp) {
		HttpURLConnection connection = null;
		try {
			URL url  = new URL("https://beacon.nist.gov/rest/record/" + timestamp);
			connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return connection;
	}

	StringBuilder obtainRecord(HttpURLConnection conn) {
		StringBuilder xmlString = new StringBuilder();
		try {
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "text/xml");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String line;
			while ((line = br.readLine()) != null) {
				xmlString.append(line);
			}
			conn.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xmlString;
	}

	Document constructDocument(StringBuilder xmlString) {
		Document doc = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			ByteArrayInputStream input = new ByteArrayInputStream(xmlString.toString().getBytes("UTF-8"));
			doc = builder.parse(input);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

}
