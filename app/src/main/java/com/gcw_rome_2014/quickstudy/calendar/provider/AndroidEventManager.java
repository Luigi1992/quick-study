package com.gcw_rome_2014.quickstudy.calendar.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import com.gcw_rome_2014.quickstudy.model.events.Event;

import java.util.Calendar;

/**
 * Created by Luigi on 03/01/2015.
 * Class that handle all the CRUD operations on Events Table.
 */
public class AndroidEventManager implements EventManager {
    //Activity Content resolver
    ContentResolver contentResolver;

    public AndroidEventManager(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    /**
     * Add an event to the calendar.
     * Remember that months start from zero.
     *
     * @param event        The event.
     * @return Returns the added event ID.
     */
    @Override
    public long addEvent(Event event) {
        Calendar dateAndTime = event.getDateAndTime();

        long calID = 1;
        long startMillis = this.getStartMillis(dateAndTime);
        long endMillis = getEndMillis(dateAndTime, event.getDuration());

        //...

        //TODO: All the field must be automatically inserted by the users.
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, event.getName());
        values.put(CalendarContract.Events.DESCRIPTION, event.getDescription());
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "Europe/Rome");  //TODO: This should be automatic.
        Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);

        // get the event ID that is the last element in the Uri
        long eventID = Long.parseLong(uri.getLastPathSegment());
        //
        // ... do something with event ID
        //
        //

        Log.i("AndroidEventManager","EventID: " + eventID);

        return eventID;
    }

    /**
     * Update the event
     * @param event the event to update in calendar
     * @return The number of update row. It should be 0 or 1
     */
    @Override
    public int updateEvent(Event event) {
        String DEBUG_TAG = "MyActivity";

        long startMillis = this.getStartMillis(event.getDateAndTime());
        long endMillis = getEndMillis(event.getDateAndTime(), event.getDuration());

        ContentValues values = new ContentValues();
        Uri updateUri = null;

        // The new title for the event
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, event.getName());
        values.put(CalendarContract.Events.DESCRIPTION, event.getDescription());
        updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, event.getId());

        int rows = contentResolver.update(updateUri, values, null, null);
        Log.i(DEBUG_TAG, "Rows updated: " + rows);

        return rows;
    }

    @Override
    public int updateEventName(long eventID, String newName) {
        String DEBUG_TAG = "MyActivity";

        ContentValues values = new ContentValues();
        Uri updateUri = null;

        // The new title for the event
        values.put(CalendarContract.Events.TITLE, "Study Session for " + newName);
        updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);

        int rows = contentResolver.update(updateUri, values, null, null);
        Log.i(DEBUG_TAG, "Rows updated: " + rows);

        return rows;
    }

    /**
     * Delete the event providing the id.
     * @param eventID The event id.
     * @return The number of rows deleted. It should be 0 or 1.
     */
    @Override
    public int deleteEvent(long eventID) {
        String DEBUG_TAG = "Deleting Event";

        ContentValues values = new ContentValues();
        Uri deleteUri = null;
        deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        int rows = contentResolver.delete(deleteUri, null, null);
        Log.i(DEBUG_TAG, "Rows deleted: " + rows);
        return rows;
    }

    private long getStartMillis(Calendar dateAndTime) {
        long startMillis = 0;

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH), dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE));
        startMillis = beginTime.getTimeInMillis();

        return startMillis;
    }

    private long getEndMillis(Calendar dateAndTime, int duration) {
        long endMillis = 0;

        Calendar endTime = Calendar.getInstance();
        endTime.set(dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH), dateAndTime.get(Calendar.HOUR_OF_DAY) + duration,
                dateAndTime.get(Calendar.MINUTE));
        endMillis = endTime.getTimeInMillis();

        return endMillis;
    }
}
