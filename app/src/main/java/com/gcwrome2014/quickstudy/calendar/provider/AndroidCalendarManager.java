package com.gcwrome2014.quickstudy.calendar.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import java.util.TimeZone;

/**
 * Created by Luigi on 04/01/2015.
 * Class that handle all the CRUD operations on Calendars Table.
 */
public class AndroidCalendarManager implements CalendarManager {

    // Projection array. Creating indices for this array instead of doing
    // dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    //Content Resolver
    private ContentResolver contentResolver;

    public AndroidCalendarManager(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public void queryCalendar() {
        // Run query
        Cursor cur = null;
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[] {"sampleuser@gmail.com", "com.google",
                "sampleuser@gmail.com"};

        // Submit the query and get a Cursor object back.
        cur = contentResolver.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);

        // Use the cursor to step through the returned records
        while (cur.moveToNext()) {
            long calID = 0;
            String displayName = null;
            String accountName = null;
            String ownerName = null;

            // Get the field values
            calID = cur.getLong(PROJECTION_ID_INDEX);
            displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
            accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);

            // Do something with the values...
            //TODO: To Remove later.
            System.out.println("ID: " + calID);
            System.out.println("Display: " + displayName);
            System.out.println("AndroidAccount: " + accountName);
            System.out.println("owner: " + ownerName);
        }
    }

    public void modifyCalendar(long calendarID) {
        String DEBUG_TAG = "MyActivity";    //TODO: Remove later.

        ContentValues values = new ContentValues();

        // The new display name for the calendar
        values.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "Trevor's Calendar");
        Uri updateUri = ContentUris.withAppendedId(CalendarContract.Calendars.CONTENT_URI, calendarID);
        int rows = contentResolver.update(updateUri, values, null, null);
        Log.i(DEBUG_TAG, "Rows updated: " + rows);
    }

    public void insertCalendar() {
        //TODO: Create this method
    }

    /**
     * Get all available timezones.
     *
     * @return An array containing timezones.
     */
    public String[] getAvailableTimezones() {
        return TimeZone.getAvailableIDs();
    }

}
