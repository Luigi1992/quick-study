package com.gcw_rome_2014.quickstudy.calendar.provider;

import java.util.Calendar;

/**
 * Created by Luigi on 03/01/2015.
 */
public interface EventManager {
    public long addEvent(String eventName, String eventDescription, Calendar dateAndTime, int HoursPerDay);
    public int deleteEvent(long eventId);
}
