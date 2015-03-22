package com.gcwrome2014.quickstudy.calendar.provider;

import com.gcwrome2014.quickstudy.model.events.Event;

/**
 * Created by Luigi on 03/01/2015.
 */
public interface EventManager {
    //public long addEvent(String eventName, String eventDescription, Calendar dateAndTime, int HoursPerDay);
    public long addEvent(Event event);
    public int updateEvent(Event event);
    public int updateEventName(long eventID, String newName);
    public int deleteEvent(long eventID);
}
