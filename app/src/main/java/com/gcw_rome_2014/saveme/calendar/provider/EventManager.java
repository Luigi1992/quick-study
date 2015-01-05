package com.gcw_rome_2014.saveme.calendar.provider;

import android.content.ContentValues;
import android.net.Uri;
import android.provider.CalendarContract;

import java.util.Calendar;

/**
 * Created by Luigi on 03/01/2015.
 */
public interface EventManager {
    public long addEvent(String eventName, String eventDescription, Calendar dateAndTime, int HoursPerDay);
}
