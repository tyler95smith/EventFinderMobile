package com.eventfinder.www.eventfindermobile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by redre on 4/4/2018.
 */

public class DateValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String DATE_PATTERN =
            "(0[1-9]|1[012])/(0[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)";



    public DateValidator(){
        pattern = Pattern.compile(DATE_PATTERN);
    }

    public boolean validate(final String date) {
        matcher = pattern.matcher(date);
        if(matcher.matches()) {
            matcher.reset();
            if(matcher.find()) {
                String month = matcher.group(1);
                String day = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));

                if(day.equals("31") && (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11"))) {
                    return false;
                } else if(month.equals("02")) {
                    if(year%4 != 0) {
                        if(day.equals("29")) {
                            return false;
                        }
                    }
                    if(day.equals("30") || day.equals("31")) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Date returnDate(String string) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public boolean inFuture(Date date) {
        Date now = Calendar.getInstance().getTime();
        return date.after(now);
    }

    public Date getDateTime(String date) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        Date dateTime = new Date();
        try {
            dateTime = format.parse(date);
            return dateTime;
        } catch (ParseException e) {
            return null;
        }
    }
}
