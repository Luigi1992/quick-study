package com.gcw_rome_2014.saveme.calendar;

import java.util.Calendar;

/**
 * Created by Luigi Mortaro on 19/12/2014.
 *
 * This Interface has the scope to decouple the technology used to
 */
public interface CalendarProvider {
    public long addEvent(String eventName, String eventDescription, Calendar dateAndTime, int HoursPerDay);
}