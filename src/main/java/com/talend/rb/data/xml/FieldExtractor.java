package com.talend.rb.data.xml;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class FieldExtractor {
	
	public static String extractFieldValue(Document beaconXml, String fieldName) {
		XPath xPath = XPathFactory.newInstance().newXPath();
		// construct the XPath
		String expression = "/record/"+fieldName;
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xPath.compile(expression).evaluate(beaconXml, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		// return text of the node
		return nodeList.item(0).getTextContent();
	}

}
