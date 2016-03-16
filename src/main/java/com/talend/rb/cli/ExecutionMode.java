package com.talend.rb.cli;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

public class ExecutionMode {

    public static SearchBounds retrieveSearchParameters(String[] args) {

        Options options = new Options();
        options.addOption(new Option("from", "from", true, "from time"));
        options.addOption(new Option("to", "to", true, "to time"));

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (cmd.hasOption("from") && cmd.hasOption("to")) {
            int from = prettyDateToTimestamp(cmd.getOptionValue("from"));
            int to = prettyDateToTimestamp(cmd.getOptionValue("to"));
            return new SearchBounds(from, to);
        } else {
            // fetching record for current time results in 404
            // new record is generated each 60 seconds
            // subtracting 59 seconds from current time
            // to ensure we get "Current Record (or next closest)"
            int current = (int) (System.currentTimeMillis() / 1000 - 59);
            return new SearchBounds(current, current);
        }
    }

    private static int prettyDateToTimestamp(String value) {
        List<Date> parse = new PrettyTimeParser().parse(value);

        Calendar parsedDate = Calendar.getInstance();
        parsedDate.setTime(parse.get(0));

        int timestamp = (int) (parsedDate.getTimeInMillis()/1000);
        
        Calendar earliestDate = Calendar.getInstance();
        earliestDate.setTimeInMillis(1378395540);
        
        if (parsedDate.before(earliestDate)) {
            String message = "One of the arguments pre-dates 05/Sep/2013";
            throw new IllegalArgumentException(message);
        }
        
        return timestamp;

    }

}
