package com.gcw_rome_2014.saveme.calendar;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Luigi Mortaro on 22/12/2014.
 * This class provide the basics CRUD operations for using the Android's Calendars.
 * It is mainly based on the CalendarProvider Class. So it require to be used at
 * least with Android 4.0 ICS (api 14+).
 */
public class AndroidCalendarProvider implements CalendarInterface {
    // Projection array. Creating indices for this array instead of doing
    // dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the event projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int EVENT_PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int EVENT_PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int EVENT_PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    private static final String DEBUG_TAG = "MyActivity";   //Only for debug purpose

    public static final String[] INSTANCE_PROJECTION = new String[] {
            CalendarContract.Instances.EVENT_ID,      // 0
            CalendarContract.Instances.BEGIN,         // 1
            CalendarContract.Instances.TITLE          // 2
    };

    // The indices for the instance projection array above.
    private static final int INSTANCE_PROJECTION_BEGIN_INDEX = 1;
    private static final int INSTANCE_PROJECTION_TITLE_INDEX = 2;

    /**
     *  This function gets the calendars that are owned by a particular user.
     * @param contentResolver The activity content resolver.
     */
    public void queryCalendar(ContentResolver contentResolver) {
        // Run query
        Cursor cur = null;
        String userName = "luigi.mortaro@gmail.com"; //TODO: Replace this with an automatic way to get the user.
        String userOwnerName = "luigi.mortaro@gmail.com";   //TODO: Replace this with an automatic way to get the user.

        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[] {userName, "com.google",
                userOwnerName};

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
            displayName = cur.getString(EVENT_PROJECTION_DISPLAY_NAME_INDEX);
            accountName = cur.getString(EVENT_PROJECTION_ACCOUNT_NAME_INDEX);
            ownerName = cur.getString(EVENT_PROJECTION_OWNER_ACCOUNT_INDEX);

            // Do something with the values...
            //TODO: To Remove later.
            System.out.println("ID: " + calID);
            System.out.println("Display: " + displayName);
            System.out.println("Account: " + accountName);
            System.out.println("owner: " + ownerName);
        }
    }

    /**
     * Return an array containing all accounts on the phone.
     * @param context The Application context.
     */
    public Account[] getAccounts(Context context) {
        AccountManager m = AccountManager.get(context);
        Account[] accounts = m.getAccounts();

        //TODO: To Remove later.
        for(Account a : accounts) {
            System.out.println("Name: " + a.name);
            System.out.println("Type: " + a.type);
        }

        return accounts;
    }

    /**
     * Get all available timezones.
     * @return An array containing timezones.
     */
    public String[] getAvailableTimezones() {
        return TimeZone.getAvailableIDs();
    }

    /**
     * Add an event to the calendar.
     * @param contentResolver The activity content resolver.
     * @return Returns the added event ID.
     */
    public long addEvent(ContentResolver contentResolver) {
        long calID = 1;
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        //Remember that months go from 0 to 11
        beginTime.set(2014, 11, 22, 16, 30);    //TODO: It should be automatically selected by the user.
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2014, 11, 22, 16, 45);      //TODO: It should be automatically selected by the user.
        endMillis = endTime.getTimeInMillis();

        //...

        //TODO: All the field must be automatically inserted by the users.
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, "Jazzercise");
        values.put(CalendarContract.Events.DESCRIPTION, "Group workout");
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "Europe/Rome");
        Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);

        // get the event ID that is the last element in the Uri
        long eventID = Long.parseLong(uri.getLastPathSegment());
        //
        // ... do something with event ID
        //
        //

        System.out.println("EventID: " + eventID);
        return eventID;
    }

    /**
     * Read instances from calendars.
     * @param contentResolver The activity content resolver.
     */
    public void queryInstance(ContentResolver contentResolver) {
        // Specify the date range you want to search for recurring
        // event instances
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2014, 12, 20, 8, 0);
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2014, 12, 23, 8, 0);
        long endMillis = endTime.getTimeInMillis();

        Cursor cur = null;

        // The ID of the recurring event whose instances you are searching
        // for in the Instances table
        String selection = CalendarContract.Instances.EVENT_ID + " = ?";
        String[] selectionArgs = new String[] {"160"};

        // Construct the query with the desired date range.
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        // Submit the query
        cur =  contentResolver.query(builder.build(),
                INSTANCE_PROJECTION,
                selection,
                selectionArgs,
                null);

        while (cur.moveToNext()) {
            String title = null;
            long eventID = 0;
            long beginVal = 0;

            // Get the field values
            eventID = cur.getLong(PROJECTION_ID_INDEX);
            beginVal = cur.getLong(INSTANCE_PROJECTION_BEGIN_INDEX);
            title = cur.getString(INSTANCE_PROJECTION_TITLE_INDEX);

            // Do something with the values.
            Log.i(DEBUG_TAG, "EventID:  " + eventID);
            Log.i(DEBUG_TAG, "Event:  " + title);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(beginVal);
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Log.i(DEBUG_TAG, "Date: " + formatter.format(calendar.getTime()));

            System.out.println("Enter at least one time.");
        }
    }
}
