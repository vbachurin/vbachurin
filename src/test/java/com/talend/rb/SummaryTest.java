package com.talend.rb;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SummaryTest {

	@Test
	public final void testSummarize() {
		// given
		String testString = "1C1960A50B624E6497E9FCAD6A09B90ACE841E6A429C4CF2B553CA53CCA239859CB19DD1E56F39DF4A9A0B37A364F18CF47FAAFA3E18B9843DBA981626F3EF1F";
		Map<Character, Integer> expected = new HashMap<Character, Integer>();
		expected.put('0', 5);
		expected.put('1', 9);
		expected.put('2', 5);
		expected.put('3', 9);
		expected.put('4', 9);
		expected.put('5', 6);
		expected.put('6', 9);
		expected.put('7', 3);
		expected.put('8', 6);
		expected.put('9', 13);
		expected.put('A', 14);
		expected.put('B', 7);
		expected.put('C', 10);
		expected.put('D', 5);
		expected.put('E', 7);
		expected.put('F', 11);

		// when
		Map<Character, Integer> actual = Summary.summarize(testString, new HashMap<Character, Integer>());

		// then
		assertTrue(actual.equals(expected));
	}

}
