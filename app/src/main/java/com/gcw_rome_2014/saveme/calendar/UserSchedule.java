package com.gcw_rome_2014.saveme.calendar;

import android.content.ContentResolver;
import android.content.Context;

import com.gcw_rome_2014.saveme.calendar.android.AndroidCalendar;

import java.util.Calendar;

/**
 * Created by Luigi on 03/01/2015.
 * This class represents the user schedule.
 * It implements all the CRUD operation of the user schedule.
 */
public class UserSchedule {
    private CalendarProvider calendarProvider;

    public UserSchedule(Context context, ContentResolver contentResolver) {
        this.calendarProvider = new AndroidCalendar(context, contentResolver);
    }

    /**
     * Add all the events
     * @param examName      The exam name.
     * @param dateAndTime   The date of the exam.
     * @param hoursOfStudy  An integer representing the number of hours the student want to study per day.
     */
    public void addExam(String examName, Calendar dateAndTime, int hoursOfStudy) {
        long eventID = this.calendarProvider.addEvent(examName, "SAVE ME Automatic Planner", dateAndTime, 0);
    }

    private void addHourOfStudy() {

    }
}
