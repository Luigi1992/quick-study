package com.gcw_rome_2014.quickstudy.calendar.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

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
     * @param eventName         The event name.
     * @param eventDescription  The event description.
     * @param dateAndTime       A Calendar Object, containing both Date and Time of the Exam
     * @param duration          The duration of the event, expressed in hours.
     * @return Returns the added event ID.
     */
    public long addEvent(String eventName, String eventDescription, Calendar dateAndTime, int duration) {
        long calID = 1;
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH), dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE));
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH), dateAndTime.get(Calendar.HOUR_OF_DAY) + duration,
                dateAndTime.get(Calendar.MINUTE));
        endMillis = endTime.getTimeInMillis();

        //...

        //TODO: All the field must be automatically inserted by the users.
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, eventName);
        values.put(CalendarContract.Events.DESCRIPTION, eventDescription);
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "Europe/Rome");  //TODO: This should be automatic.
        Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);

        // get the event ID that is the last element in the Uri
        long eventID = Long.parseLong(uri.getLastPathSegment());
        //
        // ... do something with event ID
        //
        //

        //TODO: remove in the final version.
        Log.i("AndroidEventManager","EventID: " + eventID);
        return eventID;
    }

    //TODO: Finish this method.
    public void updateEvent(long eventID) {
        String DEBUG_TAG = "MyActivity";
        //long eventID = 188;
        //...

        ContentValues values = new ContentValues();
        Uri updateUri = null;
        // The new title for the event
        values.put(CalendarContract.Events.TITLE, "Kickboxing");
        updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        int rows = contentResolver.update(updateUri, values, null, null);
        Log.i(DEBUG_TAG, "Rows updated: " + rows);
    }

    //TODO: Finish this method.
    public void deleteEvent(long eventId) {
        String DEBUG_TAG = "Deleting Event";

        ContentValues values = new ContentValues();
        Uri deleteUri = null;
        deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId);
        int rows = contentResolver.delete(deleteUri, null, null);
        Log.i(DEBUG_TAG, "Rows deleted: " + rows);
    }
}
