package com.talend.rb.cli;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

public class ExecutionModeTest {

	@Test
	public void testFromAndTo() {
		// given
		String fromRelTime = "10 hours ago";
		String toRelTime = "9 hours ago";
		
		String[] args = new String[] {"--from", fromRelTime, "--to", toRelTime}; 
		
		int expectedFrom = relativeToSeconds(fromRelTime);
		int expectedTo = relativeToSeconds(toRelTime);
		
		// when
		SearchBounds actual = ExecutionMode.retrieveSearchParameters(args);
		
		//then
		assertEquals(expectedFrom, actual.getFrom());
		assertEquals(expectedTo, actual.getTo());
	}
	
	@Test
	public void testNoArgs() {
		// given
		String[] args = new String[] {}; 
		
		// get current time minus 59 seconds (it is have correct  
		int expectedTime = relativeToSeconds("") - 59;
		
		// when
		SearchBounds actual = ExecutionMode.retrieveSearchParameters(args);
		
		// then
		// bounds are equal to current time
		assertEquals(expectedTime, actual.getFrom());
		assertEquals(expectedTime, actual.getTo());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPreDated() {
		// given
		// the fromRelTime timestamp is below the earliest acceptable timestamp
		String fromRelTime = "10 years ago";
		String toRelTime = "9 months ago";
		String[] args = new String[] {"--from", fromRelTime, "--to", toRelTime}; 
		
		// when
		ExecutionMode.retrieveSearchParameters(args);
		
		// then
		// exception thrown
	}

	private int relativeToSeconds(String relTime) {
		Calendar calendar = Calendar.getInstance();
		if (!relTime.isEmpty()) {
			Date time = new PrettyTimeParser().parse(relTime).get(0);
			calendar.setTime(time);
		}
		int timeInMillis = (int) (calendar.getTimeInMillis()/1000);
		return timeInMillis;
	}

}
